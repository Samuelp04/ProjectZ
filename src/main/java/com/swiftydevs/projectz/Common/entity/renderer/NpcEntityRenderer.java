package com.swiftydevs.projectz.Common.entity.renderer;

import com.swiftydevs.projectz.Common.entity.NpcEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;


public class NpcEntityRenderer extends MobRenderer<NpcEntity, HumanoidModel<NpcEntity>> {
    private static final ResourceLocation NPC_TEXTURE = new ResourceLocation("projectz", "textures/entity/player/steve.png");

    public NpcEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
    }

    @Override
    public ResourceLocation getTextureLocation(NpcEntity entity) {
        return NPC_TEXTURE;
    }
}