package com.glisco.disenchanter;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class DisenchanterBlock extends Block {

    private static final VoxelShape BASE_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D);
    private static final VoxelShape CENTER_SHAPE = Block.createCuboidShape(3.0D, 11.0D, 3.0D, 13.0D, 12.0D, 13.0D);
    private static final VoxelShape ARM_1_SHAPE = Block.createCuboidShape(4.0D, 11.0D, 0.0D, 12.0D, 12.0D, 16.0D);
    private static final VoxelShape ARM_2_SHAPE = Block.createCuboidShape(0.0D, 11.0D, 4.0D, 16.0D, 12.0D, 12.0D);

    private static final VoxelShape SHAPE = VoxelShapes.union(BASE_SHAPE, CENTER_SHAPE, ARM_1_SHAPE, ARM_2_SHAPE);

    public DisenchanterBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) player.openHandledScreen(new Factory(pos, world));
        return ActionResult.SUCCESS;
    }

    @Override
    public void randomDisplayTick(BlockState blockState, World world, BlockPos pos, Random random) {
        // TODO: Fix particle API for 1.21.9 - the addParticle method signature has changed
        // if (random.nextFloat() > 0.5f) {
        //     for (int i = 0; i < 2; i++) {
        //         spawnEnchantParticle(world, pos, pos.add(random.nextInt(3) - 1, 2, random.nextInt(3) - 1), 0f, 0.5f, 0f, 0f);
        //     }
        // } else {
        //     for (int i = 0; i < 2; i++) {
        //         world.addParticle(ParticleTypes.SMOKE, true,
        //             pos.getX() + 0.5 + (random.nextFloat() - 0.5) * 0.25, 
        //             pos.getY() + 0.8, 
        //             pos.getZ() + 0.5 + (random.nextFloat() - 0.5) * 0.25, 
        //             (random.nextFloat() - 0.5) * 0.01, 
        //             (random.nextFloat() - 0.5) * 0.01, 
        //             (random.nextFloat() - 0.5) * 0.01);
        //     }
        // }
    }

    public static void spawnEnchantParticle(World world, BlockPos origin, BlockPos destination, float offsetX, float offsetY, float offsetZ, float deviation) {
        // TODO: Fix particle API for 1.21.9
        // Random r = world.getRandom();
        // BlockPos particleVector = origin.subtract(destination);
        //
        // double originX = particleVector.getX() + offsetX + (r.nextDouble() - 0.5) * deviation;
        // double originY = particleVector.getY() + offsetY + (r.nextDouble() - 0.5) * deviation;
        // double originZ = particleVector.getZ() + offsetZ + (r.nextDouble() - 0.5) * deviation;
        //
        // world.addParticle(ParticleTypes.ENCHANT, true,
        //     destination.getX() + 0.5, 
        //     destination.getY(), 
        //     destination.getZ() + 0.5, 
        //     originX, originY, originZ);
    }

    private record Factory(BlockPos pos, World world) implements NamedScreenHandlerFactory {

        @Override
        public Text getDisplayName() {
            return Text.translatable("disenchanter.gui.title");
        }

        @Override
        public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
            return new DisenchanterScreenHandler(syncId, inv, ScreenHandlerContext.create(world, pos));
        }
    }

}
