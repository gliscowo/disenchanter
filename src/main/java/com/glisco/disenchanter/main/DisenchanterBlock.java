package com.glisco.disenchanter.main;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class DisenchanterBlock extends Block implements BlockEntityProvider {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public DisenchanterBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            //This will call the createScreenHandlerFactory method from blockWithEntity, which will return our blockEntity casted
            //to a namedScreenHandlerFactory
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            if (screenHandlerFactory != null) {
                //With this call the server will request the client to open the appropriate Screenhandler
                player.openHandledScreen(screenHandlerFactory);
            } else {
                player.sendMessage(Text.method_30163("noFactory"), true);
            }
        }
        return ActionResult.SUCCESS;
    }

    //@Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof DisenchanterBlockEntity) {
            return new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> new DisenchanterScreenHandler(i, playerInventory, ScreenHandlerContext.create(world, pos)), new TranslatableText("disenchanter.gui.title"));
        } else {
            return null;
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new DisenchanterBlockEntity();
    }
}
