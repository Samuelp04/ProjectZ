package com.swiftydevs.projectz.Common.init;

import com.swiftydevs.projectz.Common.entity.LootBlockEntity;
import com.swiftydevs.projectz.Common.entity.RotatableBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, "projectz");

    public static final RegistryObject<BlockEntityType<LootBlockEntity>> LOOT_BLOCK_ENTITY = BLOCK_ENTITIES.register("loot_block_entity",
            () -> BlockEntityType.Builder.of(LootBlockEntity::new, ModBlocks.TRASH_CAN.get()).build(null));

    public static final RegistryObject<BlockEntityType<RotatableBlockEntity>> ROTATABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("rotatable_block_entity",
            () -> BlockEntityType.Builder.of(RotatableBlockEntity::new,
                    ModBlocks.BARRICADE_1.get(), ModBlocks.BARRICADE_2.get(), ModBlocks.CHAIN_LINK_FENCE.get(), ModBlocks.BIO_HAZARD_BARREL.get(),
                    ModBlocks.BRAIN_JAR.get(), ModBlocks.CONE.get(), ModBlocks.DANGER_SIGN.get(), ModBlocks.GRAFFITI_1.get(),
                    ModBlocks.GRAFFITI_2.get(), ModBlocks.NEWS_PAPERS.get(), ModBlocks.SIREN.get(), ModBlocks.SWAY_FLAG.get(),
                    ModBlocks.TORN_FLAG.get(), ModBlocks.WOOD_BOARDS_2.get(), ModBlocks.AMMO_BOX.get(), ModBlocks.BARREL_RED.get(),
                    ModBlocks.BARREL_GREEN.get(), ModBlocks.BARREL_BLUE.get(), ModBlocks.CAMPING_STOOL.get(), ModBlocks.CAR_BATTERY.get(),
                    ModBlocks.CARDBOARD_BOX.get(), ModBlocks.CARDBOARD_BOX2.get(), ModBlocks.CARDBOARD_BOX3.get(),
                    ModBlocks.CASH_REGISTER.get(), ModBlocks.CCTV.get(), ModBlocks.CONSTRUCTION_LIGHT.get(),
                    ModBlocks.CONSTRUCTION_LIGHT2.get(), ModBlocks.ELECTRIC_BOX1.get(), ModBlocks.ELECTRIC_BOX2.get(),
                    ModBlocks.HAZARD_BARRIER.get(), ModBlocks.HAZARDPOLE.get()
            ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
