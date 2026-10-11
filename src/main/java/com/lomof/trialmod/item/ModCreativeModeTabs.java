package com.lomof.trialmod.item;

import com.lomof.trialmod.TrialMod;
import com.lomof.trialmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TrialMod.MODID);

    public static final Supplier<CreativeModeTab> TRIAL_ITEMS_TAB = CREATIVE_MODE_TAB.register("trial_items_tab",
            () -> CreativeModeTab.builder()
                    .icon(()->new ItemStack(ModItems.MATERIAL_A.get()))
                    .title(Component.translatable("creativetab.lomofstrialmod.trial_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.MATERIAL_A);
                        output.accept(ModItems.RAW_MATERIAL_A);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> TRIAL_BLOCKS_TAB = CREATIVE_MODE_TAB.register("trial_blocks_tab",
            () -> CreativeModeTab.builder()
                    .icon(()->new ItemStack(ModBlocks.MATERIAL_A_BLOCK.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(TrialMod.MODID, "trial_items_tab"))
                    .title(Component.translatable("creativetab.lomofstrialmod.trial_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.MATERIAL_A_BLOCK);
                        output.accept(ModBlocks.MATERIAL_A_ORE);
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
