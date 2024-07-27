package com.swiftydevs.projectz.Client.Loot;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class LootEntry {
    private final RegistryObject<Item> item;

    private final double probability;

    private final int quantity;

    public LootEntry(RegistryObject<Item> item, double probability, int quantity) {
        this.item = item;
        this.probability = probability;
        this.quantity = quantity;
    }

    public RegistryObject<Item> getItem() {
        return this.item;
    }

    public double getProbability() {
        return this.probability;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
