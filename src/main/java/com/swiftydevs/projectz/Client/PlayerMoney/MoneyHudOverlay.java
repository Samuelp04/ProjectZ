package com.swiftydevs.projectz.Client.PlayerMoney;

import com.mojang.blaze3d.systems.RenderSystem;
import com.swiftydevs.projectz.ProjectZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProjectZ.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class MoneyHudOverlay extends Gui {

    private static final ResourceLocation MONEY_ICON = new ResourceLocation("projectz", "textures/items/baseitems/money.png");
    private final Minecraft mc;

    public MoneyHudOverlay(Minecraft mc) {
        super(mc);
        this.mc = mc;
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            int balance = PlayerMoneyManager.getPlayerBalance(player);
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();

            // Render the balance text
            String balanceText = String.valueOf(balance);
            int textWidth = mc.font.width(balanceText);
            int iconSize = 16; // Smaller size for the money icon
            int iconX = width - textWidth - iconSize - 10;
            int iconY = 10;

            // Render the money icon
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, MONEY_ICON);
            blit(event.getMatrixStack(), iconX, iconY, 0, 0, iconSize, iconSize, iconSize, iconSize);

            // Render the balance text next to the money icon
            mc.font.draw(event.getMatrixStack(), balanceText, iconX + iconSize + 2, iconY + (iconSize / 2) - (mc.font.lineHeight / 2), 0xFFFFFF);
        }
    }
}