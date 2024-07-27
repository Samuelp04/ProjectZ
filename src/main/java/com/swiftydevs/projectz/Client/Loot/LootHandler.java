package com.swiftydevs.projectz.Client.Loot;

import com.swiftydevs.projectz.Common.init.ModItems;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "projectz")
public class LootHandler {
    private static final Random RANDOM = new Random();

    private static final List<LootEntry> CIVILIAN_LOOT = new ArrayList<>();

    private static final List<LootEntry> MILITARY_LOOT = new ArrayList<>();

    private static final List<LootEntry> POLICE_LOOT = new ArrayList<>();

    private static final List<LootEntry> MEDICAL_LOOT = new ArrayList<>();

    static {
        CIVILIAN_LOOT.add(new LootEntry(ModItems.MED_KIT, 0.87D, 1));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.BANDAGE, 0.98D, 1));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CLOTH, 0.78D, 1));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.DRESSING_PACKAGE, 0.87D, 1));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.PAIN_KILLERS, 0.98D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.RAG, 0.99D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.MONEY, 0.89D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.HATCHET, 0.78D, 1));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.BATON, 0.83D, 1));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.WRENCH, 0.94D, 1));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.BATNAILS, 0.89D, 1));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CAN_CORN_OPENED, 0.98D, 1));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CAN_MUSHROOMS_OPENED, 0.84D, 2));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CAN_MUSHROOMS, 0.83D, 2));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CAN_PEAS, 0.85D, 2));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CAN_CORN, 0.86D, 2));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CAN_OLIVES, 0.73D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CAN_OLIVES_OPEN, 0.78D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CAN_PINEAPPLE, 0.74D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CAN_PINEAPPLE_OPEN, 0.75D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CAKE_PIECE, 0.85D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CAN_CHICKEN, 0.86D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CAN_FISH, 0.87D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.JERKY, 0.78D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CAN_TUNA, 0.79D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.NRG_BAR, 0.78D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.BERRYS, 0.89D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.BANANA, 0.93D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.BEANS_OPEN, 0.75D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.BEANS, 0.78D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.CEREALS, 0.95D, 8));
        CIVILIAN_LOOT.add(new LootEntry(ModItems.NRG_BAR, 0.95D, 8));
    }

    public static void spawnLoot(ServerLevel world, BlockPos pos) {
        int itemsToSpawn = 1 + RANDOM.nextInt(2);
        for (int i = 0; i < itemsToSpawn; i++) {
            LootEntry selectedEntry = CIVILIAN_LOOT.get(RANDOM.nextInt(CIVILIAN_LOOT.size()));
            Item item = selectedEntry.getItem().get(); // Make sure getItem() returns the correct Item
            int quantity = selectedEntry.getQuantity(); // Make sure getQuantity() returns the correct quantity
            ItemStack stack = new ItemStack(item, quantity);
            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            world.addFreshEntity(itemEntity);
        }
    }
}
