package com.lomof.trialmod.util;

import com.lomof.trialmod.TrialMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks{
        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(TrialMod.MODID, name));
        }
    }

    public static class Items{
        public static final TagKey<Item> SOLID_FUEL = createTag("solid_fuel");
        public static final TagKey<Item> WOOD = createTag("wood");
        public static final TagKey<Item> COAL = createTag("coal");
        public static final TagKey<Item> PROCESSED_COAL = createTag("processed_coal");
        public static final TagKey<Item> NUCLEAR_FUEL = createTag("nuclear_fuel");


        private static TagKey<Item> createTag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(TrialMod.MODID, name));
        }
    }
}
