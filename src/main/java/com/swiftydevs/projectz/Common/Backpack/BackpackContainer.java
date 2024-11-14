package com.swiftydevs.projectz.Common.Backpack;

import com.swiftydevs.projectz.Common.Items.backpacks.BackpackItem;
import com.swiftydevs.projectz.Common.init.ModContainers;
import com.swiftydevs.projectz.ProjectZ;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BackpackContainer extends AbstractContainerMenu implements Container {
    private final IBackpackInventory inventory;

    public BackpackContainer(int id, Inventory playerInventory, ItemStack backpackStack) {
        super(ModContainers.BACKPACK_CONTAINER.get(), id);

        // Retrieve the backpack's slot count and initialize inventory
        int slotCount = ((BackpackItem) backpackStack.getItem()).getSlotCount();
        this.inventory = backpackStack.getCapability(ProjectZ.BACKPACK_CAPABILITY)
                .orElseThrow(() -> new IllegalStateException("Missing backpack inventory"));

        // Add backpack slots dynamically based on slot count
        int columns = 9; // or you can make this adjustable as well
        for (int i = 0; i < slotCount; i++) {
            int x = (i % columns) * 18 + 8;   // Calculate x-position
            int y = (i / columns) * 18 + 18;  // Calculate y-position
            this.addSlot(new SlotItemHandler((IItemHandler) inventory, i, x, y));
        }

        // Add player inventory slots
        addPlayerInventorySlots(playerInventory);
    }

    private void addPlayerInventorySlots(Inventory playerInventory) {
        // Implementation for adding player inventory slots goes here
    }

    @Override
    public int getContainerSize() {
        return 0;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int i) {
        return null;
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return null;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {

    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void clearContent() {

    }

    public double getSlotCount() {
        return 0;
    }
}

