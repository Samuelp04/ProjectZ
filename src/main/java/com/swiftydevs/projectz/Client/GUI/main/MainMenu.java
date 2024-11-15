package com.swiftydevs.projectz.Client.GUI.main;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.gui.components.Button;
import net.minecraft.Util;

public class MainMenu extends Screen {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("projectz", "textures/gui/main_menu_background.png");
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 20;

    private static final Component MOD_NAME = new TextComponent("Project Z Mod - 0.0.4a");
    private static final Component DEVELOPMENT_DISCLAIMER = new TextComponent("DISCLAIMER: This mod is still in development");
    private NewsWidget newsWidget;

    public MainMenu() {
        super(Component.nullToEmpty("Project Z Main Menu"));
    }

    @Override
    protected void init() {
        int leftX = 50;
        int centerY = this.height / 2;

        this.addRenderableWidget(new CustomButton(leftX, centerY - 50, BUTTON_WIDTH, BUTTON_HEIGHT, Component.nullToEmpty("Single Player"), button -> {
            this.minecraft.setScreen(new SelectWorldScreen(this));
        }));

        this.addRenderableWidget(new CustomButton(leftX, centerY - 25, BUTTON_WIDTH, BUTTON_HEIGHT, Component.nullToEmpty("Multiplayer"), button -> {
            this.minecraft.setScreen(new JoinMultiplayerScreen(this));
        }));

        this.addRenderableWidget(new CustomButton(leftX, centerY, BUTTON_WIDTH, BUTTON_HEIGHT, Component.nullToEmpty("Options"), button -> {
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }));

        this.addRenderableWidget(new CustomButton(leftX, centerY + 28, BUTTON_WIDTH, BUTTON_HEIGHT, Component.nullToEmpty("Exit"), button -> {
            this.minecraft.stop();
        }));

        // Define the container dimensions and position
        int containerWidth = 200;
        int containerHeight = 150;
        int containerX = this.width - containerWidth - 10; // Position to the right side with a 10-pixel margin
        int containerY = (this.height - containerHeight) / 2;

        // Initialize the NewsWidget and add it as a widget
        newsWidget = new NewsWidget(containerX, containerY, containerWidth, containerHeight);
        this.addRenderableWidget(newsWidget);

        /*// Patreon Link
        this.addRenderableWidget(new Button(10, this.height - 40, 100, 20, Component.nullToEmpty("Support on Patreon"), button -> {
            // Open Patreon link
            Util.getPlatform().openUri("https://www.patreon.com/your_projectz");
        }));

        // Discord Link
        this.addRenderableWidget(new Button(120, this.height - 40, 100, 20, Component.nullToEmpty("Join Discord"), button -> {
            // Open Discord link
            Util.getPlatform().openUri("https://discord.gg/your_projectz");
        }));*/
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        // Render the background texture
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        blit(poseStack, 0, 0, 0, 0, this.width, this.height, this.width, this.height);
        
        // Render all widgets (buttons and news widget)
        super.render(poseStack, mouseX, mouseY, partialTicks);

        // Draw Mod Name (bottom left)
        drawString(poseStack, this.font, MOD_NAME, 5, this.height - 12, 0xFFFFFF);

        // Draw Development Disclaimer (bottom right)
        drawString(poseStack, this.font, DEVELOPMENT_DISCLAIMER, this.width - font.width(DEVELOPMENT_DISCLAIMER) - 5, this.height - 12, 0xAAAAAA);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        // Ensure newsWidget is not null before using it
        if (newsWidget != null) {
            return newsWidget.mouseScrolled(mouseX, mouseY, delta) || super.mouseScrolled(mouseX, mouseY, delta);
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // Inner class for CustomButton
    public static class CustomButton extends Button {
        public CustomButton(int x, int y, int width, int height, Component title, OnPress onPress) {
            super(x, y, width, height, title, onPress);
        }

        @Override
        public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
            Minecraft minecraft = Minecraft.getInstance();

            // Determine fill color based on hover state
            int fillColor = this.isHoveredOrFocused() ? 0xC0FFFFFF : 0x80FFFFFF;

            // Draw button background
            fill(poseStack, this.x, this.y, this.x + this.width, this.y + this.height, fillColor);

            // Draw button text centered
            int buttonTextWidth = minecraft.font.width(this.getMessage());
            int textX = this.x + (this.width - buttonTextWidth) / 2;
            int textY = this.y + (this.height - 8) / 2;

            // Buttons have black text for contrast
            minecraft.font.draw(poseStack, this.getMessage(), textX, textY, 0x000000);
        }
    }
}
