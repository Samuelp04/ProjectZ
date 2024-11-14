package com.swiftydevs.projectz.Common.Backpack;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BackpackScreen extends AbstractContainerScreen<BackpackContainer> {
    private static final ResourceLocation BACKPACK_GUI_TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");

    public BackpackScreen(BackpackContainer container, Inventory inv, Component title) {
        super(container, inv, title);

        // Calculate rows based on slot count, assuming each row has 9 slots
        int rows = (int) Math.ceil(container.getSlotCount() / 9.0);
        this.imageWidth = 176; // Fixed width for the generic container
        this.imageHeight = 114 + rows * 18; // 114 for base height + rows
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        // Bind the generic Minecraft inventory texture
        Minecraft.getInstance().getTextureManager().bindForSetup(BACKPACK_GUI_TEXTURE);

        // Render the background
        blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, 17); // Top part of the container
        for (int row = 0; row < (this.imageHeight - 114) / 18; row++) {
            blit(poseStack, this.leftPos, this.topPos + 17 + row * 18, 0, 17, this.imageWidth, 18); // Middle rows
        }
        blit(poseStack, this.leftPos, this.topPos + this.imageHeight - 97, 0, 215, this.imageWidth, 97); // Bottom part
    }
}

