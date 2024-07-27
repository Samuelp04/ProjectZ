package com.swiftydevs.projectz.Client;

import com.swiftydevs.projectz.Common.init.ModBlocks;
import com.swiftydevs.projectz.Common.init.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class Tabs {
    public static final CreativeModeTab ITEMS_TAB = new CreativeModeTab("items_tab") {
        public ItemStack makeIcon() {
            return new ItemStack((ItemLike)ModItems.MONEY.get());
        }
    };

    public static final CreativeModeTab MEDICAL_TAB = new CreativeModeTab("medical_tab") {
        public ItemStack makeIcon() {
            return new ItemStack((ItemLike)ModItems.MED_KIT.get());
        }
    };

    public static final CreativeModeTab PROPS_BLOCKS = new CreativeModeTab("props_tab") {
        public ItemStack makeIcon() {
            return new ItemStack((ItemLike)ModBlocks.TRASH_CAN.get());
        }
    };

    public static final CreativeModeTab FOODS_TAB = new CreativeModeTab("food_tab") {
        public ItemStack makeIcon() {
            return new ItemStack((ItemLike)ModItems.CAN_MUSHROOMS_OPENED.get());
        }
    };

    public static final CreativeModeTab MELEE_TAB = new CreativeModeTab("melee_tab") {
        public ItemStack makeIcon() {
            return new ItemStack((ItemLike)ModItems.HATCHET.get());
        }
    };
}
