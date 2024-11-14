package com.swiftydevs.projectz.Common.Backpack;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface IBackpackInventory {
    NonNullList<ItemStack> getItems();
    int getSlots();
}

