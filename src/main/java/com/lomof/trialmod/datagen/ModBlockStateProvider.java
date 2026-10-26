package com.lomof.trialmod.datagen;

import com.lomof.trialmod.TrialMod;
import com.lomof.trialmod.block.ModBlocks;
import com.lomof.trialmod.block.custom.VerticalSlabBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TrialMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.MAGIC_BLOCK);
        blockWithItem(ModBlocks.URANIUM_BLOCK);
        blockWithItem(ModBlocks.URANIUM_ORE);

        stairsBlock(ModBlocks.URANIUM_STAIRS.get(), blockTexture(ModBlocks.URANIUM_BLOCK.get()));
        slabBlock(ModBlocks.URANIUM_SLAB.get(), blockTexture(ModBlocks.URANIUM_BLOCK.get()), blockTexture(ModBlocks.URANIUM_BLOCK.get()));

        blockItem(ModBlocks.URANIUM_STAIRS);
        blockItem(ModBlocks.URANIUM_SLAB);

        verticalSlabBlock(ModBlocks.URANIUM_VERTICAL_SLAB, blockTexture(ModBlocks.URANIUM_BLOCK.get()));
        verticalSlabItem(ModBlocks.URANIUM_VERTICAL_SLAB);

    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + deferredBlock.getId().getPath())));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + deferredBlock.getId().getPath() + appendix)));
    }

    private void verticalSlabBlock(DeferredBlock<Block> block, ResourceLocation texture) {
        String name = block.getId().getPath();

        models().getBuilder(name)
                .texture("particle", texture)
                .texture("0", texture)
                .element()
                .from(0, 0, 0)
                .to(16, 16, 8)
                .face(Direction.NORTH).uvs(0, 0, 16, 16).texture("#0").end()
                .face(Direction.EAST).uvs(8, 0, 16, 16).texture("#0").end()
                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#0").end()
                .face(Direction.WEST).uvs(0, 0, 8, 16).texture("#0").end()
                .face(Direction.UP).uvs(0, 0, 16, 8).texture("#0").end()
                .face(Direction.DOWN).uvs(0, 8, 16, 16).texture("#0").end()
                .end();

        getVariantBuilder(block.get()).forAllStates(state -> {
            if (state.getValue(VerticalSlabBlock.TYPE) == VerticalSlabBlock.VerticalSlabType.DOUBLE) {
                return ConfiguredModel.builder()
                        .modelFile(models().cubeAll(name + "_double", texture))
                        .build();
            } else {
                Direction facing = state.getValue(VerticalSlabBlock.FACING);
                int yRot = switch (facing) {
                    case NORTH -> 0;
                    case EAST -> 90;
                    case SOUTH -> 180;
                    case WEST -> 270;
                    default -> 0;
                };
                return ConfiguredModel.builder()
                        .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/" + name)))
                        .rotationY(yRot)
                        .build();
            }
        });
    }

    private void verticalSlabItem(DeferredBlock<Block> block) {
        String modelPath = block.getId().getPath();

        itemModels().getBuilder(modelPath)
                .parent(new ModelFile.UncheckedModelFile(modLoc("block/" + modelPath)))
                .transforms()

                    .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                    .rotation(0, 0, 0)
                    .scale(0.6f)
                    .translation(0, 0, 0)
                    .end()

                    .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                    .rotation(0, 0, 0)
                    .scale(0.6f)
                    .translation(0, 0, 0)
                    .end()

                    .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                    .rotation(0, 0, 0)
                    .scale(0.6f)
                    .translation(0, 0, 0)
                    .end()

                    .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                    .rotation(0, 0, 0)
                    .scale(0.6f)
                    .translation(0, 0, 0)
                    .end()

                    .transform(ItemDisplayContext.GUI)
                    .rotation(30, 225, 0)
                    .scale(0.625f)
                    .translation(0, 0, 0)
                    .end()

                    .transform(ItemDisplayContext.GROUND)
                    .rotation(0, 0, 0)
                    .scale(0.5f)
                    .translation(0, 3, 0)
                    .end()
                .end();
    }
}
