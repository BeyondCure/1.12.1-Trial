package com.lomof.trialmod.item;

import com.lomof.trialmod.TrialMod;
import com.lomof.trialmod.item.custom.ChiselItem;
import com.lomof.trialmod.item.custom.FuelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.swing.*;
import java.util.Objects;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TrialMod.MODID);

    public static final DeferredItem<Item> URANIUM = ITEMS.register("uranium",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_URANIUM = ITEMS.register("raw_uranium",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> R_ICON = ITEMS.register("r_icon",() -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel",
            () -> new ChiselItem(
                    new Item.Properties()
                            .durability(32)
            )
    );

    public static final DeferredItem<Item> RADISH = ITEMS.register("radish",
            () -> new Item(new Item.Properties().food(ModFoodProperties.RADISH)));

    public static final DeferredItem<Item> BAMBOO_CHARCOAL = ITEMS.register("bamboo_charcoal",
            () -> new FuelItem(new Item.Properties(), 1600));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
