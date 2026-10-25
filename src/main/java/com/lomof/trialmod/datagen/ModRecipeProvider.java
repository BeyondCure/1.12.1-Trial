package com.lomof.trialmod.datagen;

import com.lomof.trialmod.TrialMod;
import com.lomof.trialmod.block.ModBlocks;
import com.lomof.trialmod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> URANIUM_SMELTABLE = List.of(ModItems.RAW_URANIUM, ModBlocks.URANIUM_ORE);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.URANIUM_BLOCK)
                .pattern("UUU")
                .pattern("UUU")
                .pattern("UUU")
                .define('U', ModItems.URANIUM)
                .unlockedBy("has_uranium", has(ModItems.URANIUM)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.URANIUM, 9)
                .requires(ModBlocks.URANIUM_BLOCK)
                .unlockedBy("has_uranium_block", has(ModBlocks.URANIUM_BLOCK)).save(recipeOutput);

        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(Items.BAMBOO),          // 原料：竹子
                        RecipeCategory.MISC,                  // 分类：杂项
                        ModItems.BAMBOO_CHARCOAL,       // 产物：竹炭
                        0.15f,                                // 经验值
                        200                                   // 烧炼时间（ticks）
                ).unlockedBy("has_bamboo", has(Items.BAMBOO)) // 配方书解锁条件
                .save(recipeOutput, "lomofstrialmod:bamboo_charcoal_from_smelting"); // 保存的路径名

        oreSmelting(recipeOutput, URANIUM_SMELTABLE, RecipeCategory.MISC, ModItems.URANIUM, 0.25f, 200, "uranium");
        oreBlasting(recipeOutput, URANIUM_SMELTABLE, RecipeCategory.MISC, ModItems.URANIUM, 0.25f, 100, "uranium");

        stairBuilder(ModBlocks.URANIUM_STAIRS, Ingredient.of(ModItems.URANIUM))
                .group("uranium")
                .unlockedBy("has_uranium", has(ModItems.URANIUM))
                .save(recipeOutput);

        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.URANIUM_SLAB.get(), ModItems.URANIUM.get());
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, TrialMod.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
