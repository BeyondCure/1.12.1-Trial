package com.lomof.trialmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VerticalSlabBlock extends HorizontalDirectionalBlock {
    private static final VoxelShape NORTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.box(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_SHAPE  = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_SHAPE  = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);

    public static final MapCodec<VerticalSlabBlock> CODEC = simpleCodec(VerticalSlabBlock::new);
    public static final EnumProperty<VerticalSlabType> TYPE = EnumProperty.create("type", VerticalSlabType.class);

    public enum VerticalSlabType implements StringRepresentable {
        SINGLE("single"),
        DOUBLE("double");

        private final String name;

        VerticalSlabType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public VerticalSlabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, VerticalSlabType.SINGLE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        BlockState existingState = context.getLevel().getBlockState(pos);
        Direction facing = context.getHorizontalDirection();

        if (existingState.is(this) && existingState.getValue(TYPE) == VerticalSlabType.SINGLE) {
            Direction existingFacing = existingState.getValue(FACING);
            if (existingFacing == facing.getOpposite()) {
                return existingState.setValue(TYPE, VerticalSlabType.DOUBLE);
            }
        }

        return this.defaultBlockState().setValue(FACING, facing).setValue(TYPE, VerticalSlabType.SINGLE);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    private VoxelShape getShapeForDirection(Direction direction) {
        return switch (direction) {
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        SoundType soundType = state.getSoundType(level, pos, player);
        level.playSound(null, pos, soundType.getBreakSound(), SoundSource.BLOCKS,
                (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);

        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state),
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    20, 0.25, 0.25, 0.25, 0.0);
        }

        if (state.getValue(TYPE) == VerticalSlabType.DOUBLE) {
            if (!level.isClientSide && !player.isCreative()) {
                ItemStack drop = new ItemStack(this);
                popResource(level, pos, drop);
            }
            return state.setValue(TYPE, VerticalSlabType.SINGLE);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(TYPE) == VerticalSlabType.DOUBLE) {
            return Shapes.block();
        }
        return getShapeForDirection(state.getValue(FACING));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(TYPE) == VerticalSlabType.DOUBLE) {
            return Shapes.block();
        }
        return getShapeForDirection(state.getValue(FACING));
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        ItemStack itemstack = context.getItemInHand();
        if (itemstack.is(this.asItem())) {
            if (state.getValue(TYPE) == VerticalSlabType.SINGLE) {
                Direction facing = context.getHorizontalDirection();
                if (state.getValue(FACING) == facing.getOpposite()) {
                    return true;
                }
            }
        }
        return super.canBeReplaced(state, context);
    }

}
