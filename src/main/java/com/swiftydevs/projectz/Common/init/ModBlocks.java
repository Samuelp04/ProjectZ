package com.swiftydevs.projectz.Common.init;

import com.swiftydevs.projectz.Client.Tabs;
import com.swiftydevs.projectz.Common.Blocks.BlockBase;
import com.swiftydevs.projectz.Common.Blocks.LootBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "projectz");

    public static final RegistryObject<Block> TRASH_CAN = registerBlock("trashcan_1", () -> new LootBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.trashcan_1");
    public static final RegistryObject<Block> TRASH_CAN_2 = registerBlock("trashcan_2", () -> new LootBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.trashcan_2");
    public static final RegistryObject<Block> BARRICADE_1 = registerBlock("barricade_1", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.barricade_1");
    public static final RegistryObject<Block> BARRICADE_2 = registerBlock("barricade_2", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.barricade_2");
    public static final RegistryObject<Block> CHAIN_LINK_FENCE = registerBlock("chainlinkfence", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.chainlinkfence");
    public static final RegistryObject<Block> BIO_HAZARD_BARREL = registerBlock("biohazardbarrel", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.biohazardbarrel");
    public static final RegistryObject<Block> BRAIN_JAR = registerBlock("brainjar", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.brainjar");
    public static final RegistryObject<Block> CONE = registerBlock("cone", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.cone");
    public static final RegistryObject<Block> DANGER_SIGN = registerBlock("dangersign", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.dangersign");
    public static final RegistryObject<Block> DEBRISPILE = registerBlock("debrispile", () -> new LootBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.debrispile");
    public static final RegistryObject<Block> GARBAGE_1 = registerBlock("garbage_1", () -> new LootBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.garbage_1");
    public static final RegistryObject<Block> GARBAGE_2 = registerBlock("garbage_2", () -> new LootBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.garbage_2");
    public static final RegistryObject<Block> GRAFFITI_1 = registerBlock("graffiti_1", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.graffiti_1");
    public static final RegistryObject<Block> GRAFFITI_2 = registerBlock("graffiti_2", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.graffiti_2");
    public static final RegistryObject<Block> NEWS_PAPERS = registerBlock("newspapers", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.newspapers");
    public static final RegistryObject<Block> SIREN = registerBlock("siren", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.siren");
    public static final RegistryObject<Block> SWAY_FLAG = registerBlock("swayflag", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.swayflag");
    public static final RegistryObject<Block> TORN_FLAG = registerBlock("tornflag", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.tornflag");
    public static final RegistryObject<Block> WOOD_BOARDS_2 = registerBlock("woodboards_2", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.DIRT)), Tabs.PROPS_BLOCKS, "tooltip.projectz.woodboards_2");
    public static final RegistryObject<Block> AMMO_BOX = registerBlock("ammo_box", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.ammo_box");
    public static final RegistryObject<Block> BARREL_RED = registerBlock("barrel_red", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.barrel_red");
    public static final RegistryObject<Block> BARREL_GREEN = registerBlock("barrel_green", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.barrel_green");
    public static final RegistryObject<Block> BARREL_BLUE = registerBlock("barrel_blue", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.barrel_blue");
    public static final RegistryObject<Block> CAMPING_STOOL = registerBlock("campingstool", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.campingstool");
    public static final RegistryObject<Block> CAR_BATTERY = registerBlock("carbattery", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.carbattery");
    public static final RegistryObject<Block> CARDBOARD_BOX = registerBlock("cardboardbox", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.cardboardbox");
    public static final RegistryObject<Block> CARDBOARD_BOX2 = registerBlock("cardboardbox2", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.cardboardbox2");
    public static final RegistryObject<Block> CARDBOARD_BOX3 = registerBlock("cardboardbox3", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.cardboardbox3");
    public static final RegistryObject<Block> CASH_REGISTER = registerBlock("cash_register", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.cash_register");
    public static final RegistryObject<Block> CCTV = registerBlock("cctv", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.cctv");
    public static final RegistryObject<Block> CONSTRUCTION_LIGHT = registerBlock("constructionlight", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.constructionlight");
    public static final RegistryObject<Block> CONSTRUCTION_LIGHT2 = registerBlock("constructionlight2", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.constructionlight2");
    public static final RegistryObject<Block> ELECTRIC_BOX1 = registerBlock("electricbox1", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.electricbox1");
    public static final RegistryObject<Block> ELECTRIC_BOX2 = registerBlock("electricbox2", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.electricbox2");
    public static final RegistryObject<Block> HAZARD_BARRIER = registerBlock("hazardbarrier", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.hazardbarrier");
    public static final RegistryObject<Block> HAZARDPOLE = registerBlock("hazardpole", () -> new BlockBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), Tabs.PROPS_BLOCKS, "tooltip.projectz.hazardpole");

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blockSupplier, CreativeModeTab tab, String tooltipKey) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        registerBlockItem(name, block, tab, tooltipKey);
        return block;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab, String tooltipKey) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)) {
            @Override
            public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                tooltip.add(new TranslatableComponent(tooltipKey));
            }
        });
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blockSupplier, CreativeModeTab tab) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        registerBlockItem(name, block, tab);
        return block;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
