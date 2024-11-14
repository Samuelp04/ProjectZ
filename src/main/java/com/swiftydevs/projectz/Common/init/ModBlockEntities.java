package com.swiftydevs.projectz.Common.init;

import com.swiftydevs.projectz.Common.entity.LootBlockEntity;
import com.tac.guns.init.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, "projectz");

    public static final RegistryObject<BlockEntityType<LootBlockEntity>> LOOT_BLOCK_ENTITY = BLOCK_ENTITIES.register("loot_block_entity",
            () -> BlockEntityType.Builder.of(LootBlockEntity::new, ModBlocks.WORKBENCH.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
