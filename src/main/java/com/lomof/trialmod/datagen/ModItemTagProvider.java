package com.lomof.trialmod.datagen;

import com.lomof.trialmod.TrialMod;
import com.lomof.trialmod.item.ModItems;
import com.lomof.trialmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {

    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, TrialMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.WOOD)
                .addTag(ItemTags.LOGS);

        tag(ModTags.Items.COAL)
                .add(Items.COAL, Items.CHARCOAL)
                .add(ModItems.BAMBOO_CHARCOAL.get());

        tag(ModTags.Items.SOLID_FUEL)
                .addTag(ModTags.Items.WOOD)
                .addTag(ModTags.Items.COAL);

    }
}
