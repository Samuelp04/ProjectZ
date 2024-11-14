package com.swiftydevs.projectz.Client.Layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.swiftydevs.projectz.Common.Items.backpacks.BackpackItem;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import top.theillusivec4.curios.api.CuriosApi;

public class BackpackLayer<T extends Player, M extends PlayerModel<T>> extends RenderLayer<T, M> {

    private final ItemRenderer itemRenderer;

    public BackpackLayer(RenderLayerParent<T, M> renderer, ItemRenderer itemRenderer) {
        super(renderer);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource source, int light, T player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float headYaw, float headPitch) {
        // Check if the player has any backpack equipped in the "back" slot
        CuriosApi.getCuriosHelper().findEquippedCurio(item -> item.getItem() instanceof BackpackItem, player).ifPresent(curio -> {
            ItemStack stack = curio.getRight();

            // Check if player has an elytra equipped in the chest slot, hide backpack if so
            ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
            if (chestStack.getItem() == Items.ELYTRA) {
                return;
            }

            pose.pushPose();
            this.getParentModel().body.translateAndRotate(pose); // Position backpack on player's body

            // Apply basic transformations to fit the backpack onto the player's back
            pose.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            pose.scale(1.05F, -1.05F, -1.05F);
            pose.translate(0, -0.1, 0.125);

            // Render the model for the specific backpack item
            BakedModel model = this.getBackpackModel(stack);
            this.itemRenderer.render(stack, ItemTransforms.TransformType.NONE, false, pose, source, light, OverlayTexture.NO_OVERLAY, model);

            pose.popPose();
        });
    }

    private BakedModel getBackpackModel(ItemStack stack) {
        // Retrieve a unique model based on the item registry name or custom identifier
        String itemRegistryName = stack.getItem().getRegistryName().toString();
        ModelResourceLocation modelLocation = new ModelResourceLocation(itemRegistryName, "inventory");
        return this.itemRenderer.getItemModelShaper().getModelManager().getModel(modelLocation);
    }
}
