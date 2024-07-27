package com.swiftydevs.projectz.Common.init;

import com.swiftydevs.projectz.Common.entity.InfectedZombie;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, "projectz");

    public static final RegistryObject<EntityType<InfectedZombie>> INFECTED_ZOMBIE = ENTITY_TYPES.register(
            "infected_zombie",
            () -> EntityType.Builder.of(InfectedZombie::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)  // Set size for the entity (width, height)
                    .build(new ResourceLocation("projectz", "infected_zombie").toString())
    );

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
