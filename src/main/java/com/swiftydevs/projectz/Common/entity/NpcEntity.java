package com.swiftydevs.projectz.Common.entity;

import com.swiftydevs.projectz.Client.Packets.OpenTraderGuiPacket;
import com.swiftydevs.projectz.Common.init.ModNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;

import java.util.Collections;

public class NpcEntity extends Mob {

    public NpcEntity(EntityType<? extends Mob> type, Level world) {
        super(type, world);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList(); // Return an empty iterable instead of null
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 5.0);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            return;
        }

        this.setDeltaMovement(Vec3.ZERO); // Prevent movement
        this.setYRot(0); // Optional: Prevent rotation changes
        this.setXRot(0); // Optional: Prevent rotation changes
    }

    @Override
    public HumanoidArm getMainArm() {
        return null;
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public void setCustomName(Component name) {
        super.setCustomName(name);
    }

    // Override travel method to disable movement
    @Override
    public void travel(Vec3 travelVector) {
        // Do nothing to disable movement
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    // Override to prevent adding default AI goals that might cause movement
    @Override
    protected void registerGoals() {
        // No goals
    }

    // Implement getDisplayName method from MenuProvider
    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    // Implement createMenu method from MenuProvider
    @OnlyIn(Dist.CLIENT)
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level.isClientSide) {
            if (player instanceof ServerPlayer) {
                // Send a packet to the client to open the trading screen
                openTraderGui((ServerPlayer) player);
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }

    private void openTraderGui(ServerPlayer player) {
        // Example title
        Component title = new TranslatableComponent("Trading");
        ModNetworking.CHANNEL.sendTo(new OpenTraderGuiPacket(title), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}