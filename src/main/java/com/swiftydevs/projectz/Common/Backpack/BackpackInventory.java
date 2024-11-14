package com.swiftydevs.projectz.Common.Backpack;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public class BackpackInventory implements IBackpackInventory {
    private final NonNullList<ItemStack> items;

    public BackpackInventory(int slotCount) {
        this.items = NonNullList.withSize(slotCount, ItemStack.EMPTY);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public int getSlots() {
        return items.size();
    }
}

