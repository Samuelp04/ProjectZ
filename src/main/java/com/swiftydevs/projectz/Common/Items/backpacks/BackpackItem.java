package com.swiftydevs.projectz.Common.Items.backpacks;

import com.swiftydevs.projectz.Common.Backpack.BackpackCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;


public class BackpackItem extends Item implements ICurio {

    private final int slotCount;

    public BackpackItem(Properties properties, int slotCount) {
        super(properties);
        this.slotCount = slotCount;
    }

    public int getSlotCount() {
        return slotCount;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new BackpackCapabilityProvider(slotCount);  // Pass the slot count to the capability provider
    }


    private void openBackpackScreen(ServerPlayer player, ItemStack stack) {
        // Placeholder for screen handling logic
       // NetworkHooks.openGui(player, new BackpackMenuProvider(stack));
    }

    @Override
    public boolean canEquip(String identifier, LivingEntity entity) {
        // Allow equipping only in the "back" slot
        return "back".equals(identifier);
    }



    @Override
    public ItemStack getStack() {
        // Returns the stack if equipped, empty otherwise
        return isEquipped() ? new ItemStack(this) : ItemStack.EMPTY;
    }

    private boolean isEquipped() {

        return true; // Replace with actual logic
    }
}
