package com.swiftydevs.projectz.Common.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class InfectedZombieRenderer extends ZombieRenderer {

    private static final ResourceLocation CUSTOM_ZOMBIE_TEXTURE = new ResourceLocation("projectz", "textures/entity/zombie1.png");

    public InfectedZombieRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Zombie entity) {
        return CUSTOM_ZOMBIE_TEXTURE;
    }
}
