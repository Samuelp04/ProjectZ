package com.swiftydevs.projectz.Common.init;

import com.swiftydevs.projectz.Common.Backpack.BackpackContainer;
import com.swiftydevs.projectz.ProjectZ;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ProjectZ.MOD_ID);

    public static final RegistryObject<MenuType<BackpackContainer>> BACKPACK_CONTAINER = CONTAINERS.register("backpack_container",
            () -> IForgeMenuType.create((windowId, inv, data) -> new BackpackContainer(windowId, inv, data.readItem())));


    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}