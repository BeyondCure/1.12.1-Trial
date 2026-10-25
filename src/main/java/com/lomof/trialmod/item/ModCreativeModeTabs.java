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
                    .icon(()->new ItemStack(ModItems.R_ICON.get()))
                    .title(Component.translatable("creativetab.lomofstrialmod.trial_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.URANIUM);
                        output.accept(ModItems.RAW_URANIUM);
                        output.accept(ModItems.CHISEL);
                        output.accept(ModItems.BAMBOO_CHARCOAL);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> TRIAL_BLOCKS_TAB = CREATIVE_MODE_TAB.register("trial_blocks_tab",
            () -> CreativeModeTab.builder()
                    .icon(()->new ItemStack(ModBlocks.URANIUM_BLOCK.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(TrialMod.MODID, "trial_items_tab"))
                    .title(Component.translatable("creativetab.lomofstrialmod.trial_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.URANIUM_BLOCK);
                        output.accept(ModBlocks.URANIUM_ORE);
                        output.accept(ModBlocks.MAGIC_BLOCK);
                        output.accept(ModBlocks.URANIUM_SLAB);
                        output.accept(ModBlocks.URANIUM_STAIRS);
                        output.accept(ModBlocks.URANIUM_VERTICAL_SLAB);
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
