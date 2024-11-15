package com.swiftydevs.projectz.Client.GUI.main;

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

public class NewsWidget extends AbstractWidget {
    private List<FormattedCharSequence> wrappedText;

    // Padding constants in pixels
    private static final int PADDING_LEFT = 15;
    private static final int PADDING_RIGHT = 15;
    private static final int PADDING_TOP = 15;
    private static final int PADDING_BOTTOM = 15;
    private static final int BUFFER_TEXT_SCROLLBAR = 5; // Buffer between text and scrollbar

    private final int lineHeight = 14;
    private final int scrollBarWidth = 6;
    private final int scrollBarColor = 0xFFAAAAAA;
    private final int scrollBarHoverColor = 0xFFFFFFFF;
    private boolean isScrollBarHovered = false;
    private boolean isDragging = false;
    private int scrollOffset = 0;
    private final float textScale = 0.8f;

    public NewsWidget(int x, int y, int width, int height) {
        super(x, y, width, height, TextComponent.EMPTY);
        String gistContent = fetchGistContent("https://gist.githubusercontent.com/Samuelp04/085ee88dceb6c8709e8a5f39d95b1f7d/raw/9f3da79e43863054422f1d61cd381beaf775149a/gistfile1.txt");
        wrapText(gistContent);
    }

    /**
     * Fetches the content from the provided Gist URL.
     *
     * @param gistUrl The URL of the GitHub Gist.
     * @return The content of the Gist as a String.
     */
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
            content.append("Failed to load news content.");
        }
        return content.toString();
    }

    /**
     * Wraps the fetched text content into lines that fit within the widget's width,
     * considering the left and right padding, scrollbar width, and buffer.
     *
     * @param textContent The raw text content to wrap.
     */
    private void wrapText(String textContent) {
        wrappedText = new ArrayList<>();
        // Calculate the effective width for text wrapping
        int wrapWidth = (int) ((this.width - PADDING_LEFT - PADDING_RIGHT - scrollBarWidth - BUFFER_TEXT_SCROLLBAR) / textScale);
        for (String line : textContent.split("\n")) {
            wrappedText.addAll(Minecraft.getInstance().font.split(new TextComponent(line), wrapWidth));
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        // Render the news container background with semi-transparent dark gray
        fill(poseStack, this.x, this.y, this.x + this.width, this.y + this.height, 0xCC000000);

        // Render wrapped news text with scaling and white color
        int textX = this.x + PADDING_LEFT;
        int textY = this.y + PADDING_TOP - scrollOffset;

        poseStack.pushPose();
        poseStack.scale(textScale, textScale, textScale);

        for (FormattedCharSequence line : wrappedText) {
            // Only render text within the padded top and bottom boundaries
            if (textY >= this.y + PADDING_TOP && textY <= this.y + this.height - PADDING_BOTTOM - (int) (lineHeight * textScale)) {
                int scaledX = (int) (textX / textScale);
                int scaledY = (int) (textY / textScale);
                Minecraft.getInstance().font.draw(poseStack, line, scaledX, scaledY, 0xFFFFFFFF);
            }
            textY += lineHeight;
        }

        poseStack.popPose();

        // Calculate and render scroll bar
        int contentHeight = wrappedText.size() * lineHeight;
        int drawableHeight = this.height - PADDING_TOP - PADDING_BOTTOM;

        if (contentHeight > drawableHeight) { // Only show scrollbar if content exceeds container
            // Calculate scrollbar height proportionally
            int scrollBarHeight = Math.max(20, (int) (((double) drawableHeight / contentHeight) * drawableHeight));

            // Position scrollbar on the far right within the right padding
            int scrollBarX = this.x + this.width - PADDING_RIGHT - scrollBarWidth;
            int scrollBarY = this.y + PADDING_TOP + (int) (((double) scrollOffset / (contentHeight - drawableHeight)) * (drawableHeight - scrollBarHeight));

            isScrollBarHovered = mouseX >= scrollBarX && mouseX <= scrollBarX + scrollBarWidth &&
                                 mouseY >= scrollBarY && mouseY <= scrollBarY + scrollBarHeight;

            int currentColor = isScrollBarHovered ? scrollBarHoverColor : scrollBarColor;
            fill(poseStack, scrollBarX, scrollBarY, scrollBarX + scrollBarWidth, scrollBarY + scrollBarHeight, currentColor);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isScrollBarHovered && button == 0) { // Left mouse button
            isDragging = true;
            int contentHeight = wrappedText.size() * lineHeight;
            int drawableHeight = this.height - PADDING_TOP - PADDING_BOTTOM;
            scrollOffset = (int) (((mouseY - this.y - PADDING_TOP) / (double) drawableHeight) * (contentHeight - drawableHeight));
            scrollOffset = Math.max(0, Math.min(scrollOffset, contentHeight - drawableHeight));
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
            int drawableHeight = this.height - PADDING_TOP - PADDING_BOTTOM;
            int newScrollOffset = (int) (((mouseY - this.y - PADDING_TOP) / (double) drawableHeight) * (contentHeight - drawableHeight));
            scrollOffset = Math.max(0, Math.min(newScrollOffset, contentHeight - drawableHeight));
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int contentHeight = wrappedText.size() * lineHeight;
        int drawableHeight = this.height - PADDING_TOP - PADDING_BOTTOM;

        if (contentHeight > drawableHeight) { // Only scroll if content exceeds container
            scrollOffset = (int) (scrollOffset - delta * lineHeight);
            scrollOffset = Math.max(0, Math.min(scrollOffset, contentHeight - drawableHeight));
            return true;
        }
        return false;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        // No narration
    }
}
