package com.lomof.trialmod.item;

import com.lomof.trialmod.TrialMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TrialMod.MODID);

    public static final DeferredItem<Item> URANIUM = ITEMS.register("uranium",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_URANIUM = ITEMS.register("raw_uranium",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> R_ICON = ITEMS.register("r_icon",() -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
