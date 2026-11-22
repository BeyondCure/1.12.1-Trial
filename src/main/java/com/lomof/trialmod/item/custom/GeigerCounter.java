package com.lomof.trialmod.item.custom;

import com.lomof.trialmod.attachment.ModAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class GeigerCounter extends Item {
    private static final int ACTION_BAR_DURATION_TICKS = 20;
    private static final int SOUND_CHECK_INTERVAL_TICKS = 2;
    private static final double SOUND_CHANCE_DIVISOR = 100.0D;

    private static final Map<UUID, Integer> ACTION_BAR_CLEAR_TICKS = new HashMap<>();

    public GeigerCounter(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            double dose = serverPlayer.getData(ModAttachments.RADIATION_DOSE);
            double rate = serverPlayer.getData(ModAttachments.RADIATION_RATE);

            serverPlayer.displayClientMessage(
                    Component.literal(String.format(Locale.ROOT,
                            "辐照值: %.2f  速度: %.2f/s", dose, rate)),
                    true
            );
            ACTION_BAR_CLEAR_TICKS.put(serverPlayer.getUUID(),
                    serverPlayer.tickCount + ACTION_BAR_DURATION_TICKS);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide || !(entity instanceof ServerPlayer player)) {
            return;
        }
        if (!isSelected && slotId != Inventory.SLOT_OFFHAND) {
            return;
        }
        if (player.tickCount % SOUND_CHECK_INTERVAL_TICKS != 0) {
            return;
        }

        double rate = player.getData(ModAttachments.RADIATION_RATE);
        if (rate <= 0.0D) {
            return;
        }

        float chance = (float) Math.min(1.0D, rate / SOUND_CHANCE_DIVISOR);
        if (player.getRandom().nextFloat() >= chance) {
            return;
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 0.4F, 1.0F);
    }

    public static void tickActionBar(ServerPlayer player) {
        Integer clearAt = ACTION_BAR_CLEAR_TICKS.get(player.getUUID());
        if (clearAt != null && player.tickCount >= clearAt) {
            player.displayClientMessage(Component.empty(), true);
            ACTION_BAR_CLEAR_TICKS.remove(player.getUUID());
        }
    }
}
