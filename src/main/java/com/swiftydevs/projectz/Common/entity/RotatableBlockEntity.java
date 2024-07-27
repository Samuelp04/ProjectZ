package com.swiftydevs.projectz.Common.entity;

import com.swiftydevs.projectz.Common.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class RotatableBlockEntity extends BlockEntity {

    private Direction direction = Direction.NORTH;

    public RotatableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROTATABLE_BLOCK_ENTITY.get(), pos, state);
    }

    public void rotate(Rotation rotation) {
        this.direction = rotation.rotate(this.direction);
        setChanged(); // Notify that the block entity has changed
    }

    public Direction getDirection() {
        return this.direction;
    }
}
