package com.lomof.trialmod.datamap;

import com.lomof.trialmod.TrialMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.dimension.LevelStem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

public final class ModDataMaps {
    /**
     * 每个世界（LevelStem，即维度 ID）的本底辐射值，单位为“每 20 tick（1 秒）”。
     */
    public static final DataMapType<LevelStem, Double> BACKGROUND_RADIATION =
            DataMapType.builder(
                    ResourceLocation.fromNamespaceAndPath(TrialMod.MODID, "background_radiation"),
                    Registries.LEVEL_STEM,
                    Codec.DOUBLE
            ).build();

    /**
     * 每件物品每秒贡献的辐照速率。
     */
    public static final DataMapType<Item, Double> ITEM_RADIATION =
            DataMapType.builder(
                    ResourceLocation.fromNamespaceAndPath(TrialMod.MODID, "item_radiation"),
                    Registries.ITEM,
                    Codec.DOUBLE
            ).build();

    private ModDataMaps() {
    }

    public static void register(IEventBus modBus) {
        modBus.addListener(ModDataMaps::onRegisterDataMapTypes);
    }

    private static void onRegisterDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(BACKGROUND_RADIATION);
        event.register(ITEM_RADIATION);
    }
}
