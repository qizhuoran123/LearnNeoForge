package com.learn.neoforge.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ChairBlocks extends Block {
    // 1. 把不需要旋转的中心对称部分（底座/座位）单独拎出来
    private static final VoxelShape SEAT = Block.box(2, 0, 2, 14, 12, 14);

    // 2. 根据原点 (0,0,0) 到 (16,16,16) 的坐标系，手动算出 4 个方向的靠背位置
    // 假设朝北 (NORTH) 时，靠背在南边 (Z=14 到 16)
    private static final VoxelShape SHAPE_NORTH = Shapes.or(SEAT, Block.box(0, 12, 14, 16, 24, 16));
    // 朝南 (SOUTH) 时，靠背转到了北边 (Z=0 到 2)
    private static final VoxelShape SHAPE_SOUTH = Shapes.or(SEAT, Block.box(0, 12, 0, 16, 24, 2));
    // 朝西 (WEST) 时，靠背转到了东边 (X=14 到 16)
    private static final VoxelShape SHAPE_WEST  = Shapes.or(SEAT, Block.box(14, 12, 0, 16, 24, 16));
    // 朝东 (EAST) 时，靠背转到了西边 (X=0 到 2)
    private static final VoxelShape SHAPE_EAST  = Shapes.or(SEAT, Block.box(0, 12, 0, 2, 24, 16));
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = Shapes.or(
            // 座位部分
            Block.box(2, 0, 2, 14, 12, 14),
            // 靠背部分（如果需要）
            Block.box(0, 12, 14, 16, 24, 16)
    );

    public ChairBlocks(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block,BlockState> builder){
        builder.add(FACING);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        return this.defaultBlockState().setValue(FACING,context.getHorizontalDirection().getOpposite());
    }
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return switch (facing){
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
            default -> SHAPE_NORTH;
        };
    }
}
