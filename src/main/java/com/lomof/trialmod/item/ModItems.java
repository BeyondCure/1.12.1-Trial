package com.lomof.trialmod.item;

import com.lomof.trialmod.TrialMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TrialMod.MODID);

    public static final DeferredItem<Item> MATERIAL_A = ITEMS.register("material_a",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_MATERIAL_A = ITEMS.register("raw_material_a",() -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
