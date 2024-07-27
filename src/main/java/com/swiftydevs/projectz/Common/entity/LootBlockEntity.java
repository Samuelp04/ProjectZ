package com.swiftydevs.projectz.Common.entity;

import com.swiftydevs.projectz.Common.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LootBlockEntity extends BlockEntity {
    private int respawnTimer;

    private static final String RESPAWN_TIMER_KEY = "RespawnTimer";

    public LootBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LOOT_BLOCK_ENTITY.get(), pos, state);
    }

    public void setRespawnTimer(int ticks) {
        this.respawnTimer = ticks;
        setChanged();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LootBlockEntity entity) {
        if (!level.isClientSide && entity.respawnTimer > 0) {
            entity.respawnTimer--;
            if (entity.respawnTimer <= 0) {
                entity.respawnBlock();
            }
        }
    }

    private void respawnBlock() {
        if (this.level instanceof net.minecraft.server.level.ServerLevel) {
            BlockState state = this.getBlockState();
            this.level.setBlock(this.getBlockPos(), state, 3);
            this.respawnTimer = 0;
            setChanged();
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(RESPAWN_TIMER_KEY, this.respawnTimer);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.respawnTimer = tag.getInt(RESPAWN_TIMER_KEY);
    }
}
