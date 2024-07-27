package com.swiftydevs.projectz.Client.GUI.main;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.FormattedCharSequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsContainer extends AbstractWidget {
    private List<FormattedCharSequence> wrappedText;
    private final int lineHeight = 12;
    private final int scrollBarWidth = 5;
    private final int scrollBarColor = 0xFF888888; // Dark gray
    private final int scrollBarHoverColor = 0xFFAAAAAA; // Light gray
    private boolean isScrollBarHovered = false;
    private boolean isDragging = false;
    private int dragOffset = 0;
    private int scrollOffset = 0;
    private final float textScale = 0.7f;

    public NewsContainer(int x, int y, int width, int height) {
        super(x, y, width, height, TextComponent.EMPTY);

        // Fetch news content from GitHub Gist
        String gistContent = fetchGistContent("https://gist.githubusercontent.com/Samuelp04/085ee88dceb6c8709e8a5f39d95b1f7d/raw/81afe11091d4f341f07df41115840e1804cabd64/gistfile1.txt");
        parseAndWrapText(gistContent);
    }

    private String fetchGistContent(String gistUrl) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(gistUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private void parseAndWrapText(String gistContent) {
        wrappedText = new ArrayList<>();
        String[] lines = gistContent.split("\n");
        for (String line : lines) {
            wrappedText.addAll(Minecraft.getInstance().font.split(new TextComponent(line), (int) (this.width / textScale) - 20));
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        // Render the news container background
        fill(poseStack, this.x, this.y, this.x + this.width, this.y + this.height, 0x30303030); // Very transparent dark gray background

        // Render wrapped news text with scaling and color
        int textX = this.x + 10;
        int textY = this.y + 10 - scrollOffset;
        int maxVisibleLines = this.height / lineHeight;

        poseStack.pushPose();
        poseStack.scale(textScale, textScale, textScale);

        for (int i = 0; i < wrappedText.size(); i++) {
            if (textY >= this.y && textY <= this.y + this.height - lineHeight) {
                int scaledX = (int) (textX / textScale);
                int scaledY = (int) (textY / textScale);
                Minecraft.getInstance().font.draw(poseStack, wrappedText.get(i), scaledX, scaledY, 0xFFFFFF);
            }
            textY += lineHeight;
        }

        poseStack.popPose();

        // Calculate and render scroll bar
        int contentHeight = wrappedText.size() * lineHeight;
        int scrollBarHeight = Math.max(20, (int) ((float) this.height / contentHeight * this.height));
        int scrollBarX = this.x + this.width - scrollBarWidth - 2;
        int scrollBarY = this.y + (int) ((float) scrollOffset / contentHeight * this.height);

        isScrollBarHovered = mouseX >= scrollBarX && mouseX <= scrollBarX + scrollBarWidth &&
                mouseY >= scrollBarY && mouseY <= scrollBarY + scrollBarHeight;

        int currentColor = isScrollBarHovered ? scrollBarHoverColor : scrollBarColor;
        fill(poseStack, scrollBarX, scrollBarY, scrollBarX + scrollBarWidth, scrollBarY + scrollBarHeight, currentColor);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isScrollBarHovered && button == 0) { // Left mouse button
            isDragging = true;
            dragOffset = (int) mouseY - (this.y + (int) ((float) scrollOffset / (wrappedText.size() * lineHeight) * this.height));
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (isDragging && button == 0) { // Left mouse button
            isDragging = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (isDragging) {
            int contentHeight = wrappedText.size() * lineHeight;
            int newScrollOffset = (int) ((mouseY - this.y - dragOffset) / this.height * contentHeight);
            scrollOffset = Math.max(0, Math.min(newScrollOffset, contentHeight - this.height));
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int contentHeight = wrappedText.size() * lineHeight;
        scrollOffset = Math.max(0, Math.min(scrollOffset - (int) (delta * lineHeight), contentHeight - this.height));
        return true;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {

    }
}

