package com.lomof.trialmod.attribute;

import com.lomof.trialmod.TrialMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(Registries.ATTRIBUTE, TrialMod.MODID);

    /**
     * 玩家每秒自然清除的累计辐照值。略高于主世界本底（1.0/s）。
     */
    public static final DeferredHolder<Attribute, Attribute> RADIATION_CLEARANCE =
            ATTRIBUTES.register("radiation_clearance", () -> new RangedAttribute(
                    "attribute.lomofstrialmod.radiation_clearance",
                    1.1D, 0.0D, 1024.0D
            ).setSyncable(true));

    /**
     * 玩家每秒抵挡的外来辐照速率，由护甲等装备提供。
     */
    public static final DeferredHolder<Attribute, Attribute> RADIATION_RESISTANCE =
            ATTRIBUTES.register("radiation_resistance", () -> new RangedAttribute(
                    "attribute.lomofstrialmod.radiation_resistance",
                    0.0D, 0.0D, 1024.0D
            ).setSyncable(true));

    private ModAttributes() {
    }

    public static void register(IEventBus modBus) {
        ATTRIBUTES.register(modBus);
        modBus.addListener(ModAttributes::addPlayerAttributes);
    }

    private static void addPlayerAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, RADIATION_CLEARANCE);
        event.add(EntityType.PLAYER, RADIATION_RESISTANCE);
    }
}
