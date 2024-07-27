package com.swiftydevs.projectz.Client.GUI.main;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CustomButton extends Button {
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("projectz", "textures/gui/bgblack10.png");

    public CustomButton(int x, int y, int width, int height, Component title, OnPress onPress) {
        super(x, y, width, height, title, onPress);
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();

        // Determine the color based on whether the button is hovered
        int fillColor = this.isHoveredOrFocused() ? 0x80FFFFFF : 0x80000000; // 50% opacity white for hover, 50% opacity black for normal

        // Draw the button background
        fill(poseStack, this.x, this.y, this.x + this.width, this.y + this.height, fillColor);

        // Render the button's text
        drawCenteredString(poseStack, minecraft.font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xFFFFFF);
    }
}