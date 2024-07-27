package com.swiftydevs.projectz.Common.init;

import com.swiftydevs.projectz.Client.Tabs;
import com.swiftydevs.projectz.Common.Items.BaseItem;
import com.swiftydevs.projectz.Common.Items.FoodBase;
import com.swiftydevs.projectz.Common.Items.MedKit;
import com.swiftydevs.projectz.Common.Items.RotateToolItem;
import com.swiftydevs.projectz.Common.Items.itemMelee;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "projectz");

    public static final RegistryObject<Item> MED_KIT = ITEMS.register("medkit", MedKit::new);
    public static final RegistryObject<Item> BANDAGE = ITEMS.register("bandage", MedKit::new);
    public static final RegistryObject<Item> PAIN_KILLERS = ITEMS.register("painkillers", MedKit::new);
    public static final RegistryObject<Item> RAG = ITEMS.register("rag", MedKit::new);
    public static final RegistryObject<Item> DRESSING_PACKAGE = ITEMS.register("dress_package", MedKit::new);

    public static final RegistryObject<Item> HATCHET = ITEMS.register("hatchet", () -> new itemMelee(Tiers.IRON, 4, -2.4F, new Item.Properties().stacksTo(1).tab(Tabs.MELEE_TAB)));
    public static final RegistryObject<Item> BATON = ITEMS.register("baton", () -> new itemMelee(Tiers.IRON, 6, -2.4F, new Item.Properties().stacksTo(1).tab(Tabs.MELEE_TAB)));
    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", () -> new itemMelee(Tiers.IRON, 5, -2.4F, new Item.Properties().stacksTo(1).tab(Tabs.MELEE_TAB)));
    public static final RegistryObject<Item> BATNAILS = ITEMS.register("batnails", () -> new itemMelee(Tiers.IRON, 7, -2.4F, new Item.Properties().stacksTo(1).tab(Tabs.MELEE_TAB)));

    public static final RegistryObject<Item> CAN_CORN = ITEMS.register("can_corn", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CAN_CORN)));
    public static final RegistryObject<Item> CAKE_PIECE = ITEMS.register("cakepiece", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CAKE_PIECE)));
    public static final RegistryObject<Item> CAN_CHICKEN = ITEMS.register("can_chicken", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CAN_CHICKEN)));
    public static final RegistryObject<Item> CAN_FISH = ITEMS.register("can_fish", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CAN_FISH)));
    public static final RegistryObject<Item> CAN_TUNA = ITEMS.register("can_tuna", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CAN_TUNA)));
    public static final RegistryObject<Item> JERKY = ITEMS.register("jerky", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.JERKY)));
    public static final RegistryObject<Item> NRG_BAR = ITEMS.register("nrgbar", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.NRG_BAR)));

    public static final RegistryObject<Item> CAN_CORN_OPENED = ITEMS.register("can_corn_opened", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CAN_CORN_OPENED)));
    public static final RegistryObject<Item> CAN_MUSHROOMS = ITEMS.register("can_mushrooms", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CAN_MUSHROOMS)));
    public static final RegistryObject<Item> CAN_MUSHROOMS_OPENED = ITEMS.register("can_mushrooms_opened", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CAN_MUSHROOMS_OPENED)));
    public static final RegistryObject<Item> CAN_OLIVES = ITEMS.register("can_olives", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CAN_OLIVES)));
    public static final RegistryObject<Item> CAN_OLIVES_OPEN = ITEMS.register("can_olives_opened", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CAN_OLIVES_OPEN)));
    public static final RegistryObject<Item> CAN_PEAS = ITEMS.register("can_peas", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CAN_PEAS)));
    public static final RegistryObject<Item> CAN_PINEAPPLE = ITEMS.register("can_pineapple", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CAN_PINEAPPLE)));
    public static final RegistryObject<Item> CAN_PINEAPPLE_OPEN = ITEMS.register("can_pineapple_opened", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CAN_PINEAPPLE_OPENED)));
    public static final RegistryObject<Item> BANANA = ITEMS.register("banana", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.BANANA)));
    public static final RegistryObject<Item> BEANS = ITEMS.register("beans", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.BEANS)));
    public static final RegistryObject<Item> BEANS_OPEN = ITEMS.register("beans_can", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.BEANS_CAN)));
    public static final RegistryObject<Item> BERRYS = ITEMS.register("berry_x", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.BERRYS)));
    public static final RegistryObject<Item> CEREALS = ITEMS.register("cereals", () -> new Item(new Item.Properties().stacksTo(16).tab(Tabs.FOODS_TAB).food(FoodBase.CEREALS)));

    public static final RegistryObject<Item> CLOTH = ITEMS.register("cloth", () -> new BaseItem(new Item.Properties().stacksTo(16).tab(Tabs.ITEMS_TAB)));
    public static final RegistryObject<Item> ROTATE_TOOL = ITEMS.register("rotate_tool", () -> new RotateToolItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MONEY = ITEMS.register("money", () -> new BaseItem(new Item.Properties().stacksTo(100000000).tab(Tabs.ITEMS_TAB)));

    public static final RegistryObject<Item> INFECTED_SPAWN_EGG = ITEMS.register("infected_spawn_egg", () -> new ForgeSpawnEggItem(() -> ModEntityTypes.INFECTED_ZOMBIE.get(), 9735821, 3880501, new Item.Properties().tab(Tabs.ITEMS_TAB)));

    public static void register() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
