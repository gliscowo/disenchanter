package com.glisco.disenchanter;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class DisenchanterBlock extends Block {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public DisenchanterBlock() {
        super(FabricBlockSettings.of(Material.METAL).hardness(5f));
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
        if (!world.isClient) player.openHandledScreen(new Factory(pos));
        return ActionResult.SUCCESS;
    }

    @Override
    public void randomDisplayTick(BlockState blockState, World world, BlockPos pos, Random random) {
        if (random.nextFloat() > 0.5f) {
            for (int i = 0; i < 2; i++) {
                spawnEnchantParticle(world, pos, pos.add(random.nextInt(3) - 1, 2, random.nextInt(3) - 1), 0f, 0.5f, 0f, 0f);
            }
        } else {
            for (int i = 0; i < 2; i++) {
                world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5f + (random.nextFloat() - 0.5) * 0.25f, pos.getY() + 0.8f, pos.getZ() + 0.5f + (random.nextFloat() - 0.5) * 0.25f, (random.nextFloat() - 0.5) * 0.01f, (random.nextFloat() - 0.5) * 0.01f, (random.nextFloat() - 0.5) * 0.01f);
            }
        }
    }

    public static void spawnEnchantParticle(World world, BlockPos origin, BlockPos destination, float offsetX, float offsetY, float offsetZ, float deviation) {
        Random r = world.getRandom();
        BlockPos particleVector = origin.subtract(destination);

        double originX = particleVector.getX() + offsetX + (r.nextDouble() - 0.5) * deviation;
        double originY = particleVector.getY() + offsetY + (r.nextDouble() - 0.5) * deviation;
        double originZ = particleVector.getZ() + offsetZ + (r.nextDouble() - 0.5) * deviation;

        world.addParticle(ParticleTypes.ENCHANT, destination.getX() + 0.5f, destination.getY(), destination.getZ() + 0.5f, originX, originY, originZ);
    }

    private static class Factory implements NamedScreenHandlerFactory {

        private final BlockPos pos;

        private Factory(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        public Text getDisplayName() {
            return new TranslatableText("disenchanter.gui.title");
        }

        @Nullable
        @Override
        public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
            return new DisenchanterScreenHandler(syncId, inv, ScreenHandlerContext.create(player.world, pos));
        }
    }

}
