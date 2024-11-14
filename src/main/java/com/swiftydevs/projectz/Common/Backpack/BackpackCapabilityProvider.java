package com.swiftydevs.projectz.Common.Backpack;

import com.swiftydevs.projectz.ProjectZ;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class BackpackCapabilityProvider implements ICapabilityProvider {
    private final LazyOptional<BackpackInventory> instance;

    public BackpackCapabilityProvider(int slotCount) {
        this.instance = LazyOptional.of(() -> new BackpackInventory(slotCount));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == ProjectZ.BACKPACK_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }
}
