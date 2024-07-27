package com.swiftydevs.projectz.Client.GUI.main;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.List;

public class ScrollableNewsWidget extends AbstractWidget {

    final List<String> newsItems;
    int scrollOffset = 0;
    static final int VISIBLE_ITEMS = 5;

    public ScrollableNewsWidget(int x, int y, int width, int height, List<String> newsItems) {
        super(x, y, width, height, TextComponent.EMPTY); // Use TextComponent.EMPTY for an empty label
        this.newsItems = newsItems;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        // Render background
        fill(poseStack, x, y, x + width, y + height, 0xAA000000);

        // Render news items
        for (int i = 0; i < VISIBLE_ITEMS && (i + scrollOffset) < newsItems.size(); i++) {
            String newsItem = newsItems.get(i + scrollOffset);
            drawString(poseStack, Minecraft.getInstance().font, newsItem, x + 5, y + 5 + i * 10, 0xFFFFFF);
        }

        // Render scrollbar
        int scrollbarHeight = (int) ((double) VISIBLE_ITEMS / newsItems.size() * height);
        int scrollbarY = y + (int) ((double) scrollOffset / newsItems.size() * height);
        fill(poseStack, x + width - 5, scrollbarY, x + width, scrollbarY + scrollbarHeight, 0xFFAAAAAA);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta > 0) {
            scrollOffset = Math.max(scrollOffset - 1, 0);
        } else if (delta < 0) {
            scrollOffset = Math.min(scrollOffset + 1, newsItems.size() - VISIBLE_ITEMS);
        }
        return true;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {

    }
}