package com.swiftydevs.projectz.Client.GUI;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.swiftydevs.projectz.Client.ClientEventHandler;
import com.swiftydevs.projectz.ProjectZ;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ProjectZ.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PlayerStatsOverlay {

    private static final ResourceLocation STATS_BACKGROUND = new ResourceLocation("projectz", "textures/gui/stats_background.png");

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getInstance();
            PoseStack poseStack = event.getMatrixStack();
            int width = 100;
            int height = 50;
            int x = 10;
            int y = 10;

            // Bind texture
            RenderSystem.setShaderTexture(0, STATS_BACKGROUND);

            // Draw background
            blit(poseStack, x, y, 0, 0, width, height, width, height);

            // Draw text
            UUID playerId = mc.player.getUUID();
            String playerKillsText = "Player Kills: " + getPlayerKills().getOrDefault(playerId, 0);
            String zombieKillsText = "Zombie Kills: " + getPlayerZombieKills().getOrDefault(playerId, 0);
            String deathsText = "Deaths: " + getPlayerDeaths().getOrDefault(playerId, 0);

            mc.font.draw(poseStack, Component.nullToEmpty(playerKillsText), x + 5, y + 5, 16777215);
            mc.font.draw(poseStack, Component.nullToEmpty(zombieKillsText), x + 5, y + 15, 16777215);
            mc.font.draw(poseStack, Component.nullToEmpty(deathsText), x + 5, y + 25, 16777215);
        }
    }

    public static Map<UUID, Integer> getPlayerDeaths() {
        return ClientEventHandler.playerDeaths;
    }

    public static Map<UUID, Integer> getPlayerKills() {
        return ClientEventHandler.playerKills;
    }

    public static Map<UUID, Integer> getPlayerZombieKills() {
        return ClientEventHandler.playerZombieKills;
    }

    private static void blit(PoseStack poseStack, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {
        Minecraft.getInstance().gui.blit(poseStack, x, y, u, v, width, height, textureWidth, textureHeight);
    }
}

