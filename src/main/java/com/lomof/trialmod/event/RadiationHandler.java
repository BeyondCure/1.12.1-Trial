package com.lomof.trialmod.event;

import com.lomof.trialmod.TrialMod;
import com.lomof.trialmod.attachment.ModAttachments;
import com.lomof.trialmod.attribute.ModAttributes;
import com.lomof.trialmod.datamap.ModDataMaps;
import com.lomof.trialmod.item.custom.GeigerCounter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.dimension.LevelStem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = TrialMod.MODID)
public final class RadiationHandler {
    private static final int RATE_UPDATE_INTERVAL_TICKS = 10;
    private static final int DOSE_UPDATE_INTERVAL_TICKS = 20;

    private RadiationHandler() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        GeigerCounter.tickActionBar(player);

        if (player.tickCount % RATE_UPDATE_INTERVAL_TICKS == 0) {
            double rate = calculateRate(player);
            if (Double.compare(player.getData(ModAttachments.RADIATION_RATE), rate) != 0) {
                player.setData(ModAttachments.RADIATION_RATE, rate);
            }
        }

        if (player.tickCount % DOSE_UPDATE_INTERVAL_TICKS == 0) {
            double clearance = player.getAttributeValue(ModAttributes.RADIATION_CLEARANCE);
            double dose = player.getData(ModAttachments.RADIATION_DOSE);
            player.setData(ModAttachments.RADIATION_DOSE,
                    Math.max(0.0D, dose + player.getData(ModAttachments.RADIATION_RATE) - clearance));
        }
    }

    private static double calculateRate(ServerPlayer player) {
        double rawRate = backgroundRadiation(player.serverLevel()) + inventoryRadiation(player);
        double resistance = player.getAttributeValue(ModAttributes.RADIATION_RESISTANCE);
        return Math.max(0.0D, rawRate - resistance);
    }

    private static double backgroundRadiation(ServerLevel level) {
        ResourceKey<LevelStem> stemKey = ResourceKey.create(
                Registries.LEVEL_STEM,
                level.dimension().location()
        );
        Double background = level.registryAccess()
                .registryOrThrow(Registries.LEVEL_STEM)
                .getHolderOrThrow(stemKey)
                .getData(ModDataMaps.BACKGROUND_RADIATION);
        return background != null ? background : 0.0D;
    }

    private static double inventoryRadiation(ServerPlayer player) {
        double total = 0.0D;
        Inventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }

            Double perItem = stack.getItemHolder().getData(ModDataMaps.ITEM_RADIATION);
            if (perItem != null) {
                total += perItem * stack.getCount();
            }
        }

        return total;
    }
}
