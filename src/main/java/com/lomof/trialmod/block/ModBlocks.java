package com.lomof.trialmod.block;

import com.lomof.trialmod.TrialMod;
import com.lomof.trialmod.block.custom.MagicBlock;
import com.lomof.trialmod.block.custom.VerticalSlabBlock;
import com.lomof.trialmod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.http.conn.util.PublicSuffixList;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TrialMod.MODID);

    public static final DeferredBlock<Block> URANIUM_ORE = registerBlock("uranium_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
            )
    );

    public static final DeferredBlock<Block> URANIUM_BLOCK = registerBlock("uranium_block",
            () -> new Block(
                    BlockBehaviour.Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
            )
    );

    public static final DeferredBlock<Block> MAGIC_BLOCK = registerBlock("magic_block",
            () -> new MagicBlock(
                    BlockBehaviour.Properties.of()
                            .strength(2f)
                            .requiresCorrectToolForDrops()
            )
    );

    public static final DeferredBlock<StairBlock> URANIUM_STAIRS = registerBlock("uranium_stairs",
            () -> new StairBlock(
                    ModBlocks.URANIUM_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
            )
    );

    public static final DeferredBlock<SlabBlock> URANIUM_SLAB = registerBlock("uranium_slab",
            () -> new SlabBlock(
                    BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
            )
    );

    public static final DeferredBlock<Block> URANIUM_VERTICAL_SLAB = registerBlock("uranium_vslab",
            () -> new VerticalSlabBlock(
                    BlockBehaviour.Properties.of()
                            .strength(2f)
                            .requiresCorrectToolForDrops()
            )
    );

    //    工具函数，用于注册Block时能顺便把Block和Block Item绑定
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
