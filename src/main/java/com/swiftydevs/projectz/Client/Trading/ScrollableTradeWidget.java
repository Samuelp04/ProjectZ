package com.swiftydevs.projectz.Client.Trading;

import com.mojang.blaze3d.vertex.PoseStack;
import com.swiftydevs.projectz.Client.GUI.main.CustomButton;
import com.swiftydevs.projectz.Client.PlayerMoney.PlayerMoneyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ScrollableTradeWidget extends AbstractWidget {
    private final List<TradingScreen.TradeItem> tradeItems;
    private int scrollOffset = 0;
    private static final int VISIBLE_ITEMS = 4; // Number of visible items
    private final TradingScreen tradingScreen;
    private static int playerBalance;

    public ScrollableTradeWidget(int x, int y, int width, int height, List<TradingScreen.TradeItem> tradeItems, TradingScreen tradingScreen) {
        super(x, y, width, height, TextComponent.EMPTY);
        this.tradeItems = tradeItems;
        this.tradingScreen = tradingScreen;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        // Render background
        fill(poseStack, x, y, x + width, y + height, 0xAA000000);

        // Render trade items with a border around each item
        for (int i = 0; i < VISIBLE_ITEMS && (i + scrollOffset) < tradeItems.size(); i++) {
            TradingScreen.TradeItem tradeItem = tradeItems.get(i + scrollOffset);

            // Calculate Y position for each item with extra space for the box
            int itemY = y + 10 + i * 50; // Adjust vertical spacing between items if needed

            // Adjust rectangle size and position
            int boxX = x + 10; // X position of the rectangle
            int boxY = itemY - 5; // Y position of the rectangle
            int boxWidth = width - 20; // Rectangle width
            int boxHeight = 40; // Rectangle height

            // Draw the background rectangle for the item entry
            fill(poseStack, boxX, boxY, boxX + boxWidth, boxY + boxHeight, 0xFF333333); // Dark gray background

            // Draw a white border around the rectangle
            fill(poseStack, boxX - 1, boxY - 1, boxX + boxWidth + 1, boxY + boxHeight + 1, 0xFFFFFFFF); // Border

            // Adjust item icon position (within the rectangle)
            Minecraft.getInstance().getItemRenderer().renderGuiItem(tradeItem.getItem(), boxX + 8, boxY + 10); // Position of the item icon

            // **Adjust item name position**
            int nameX = boxX + 30;  // X position of the item name
            int nameY = boxY + 10;  // Y position of the item name
            drawString(poseStack, Minecraft.getInstance().font, tradeItem.getItem().getDisplayName().getString(), nameX, nameY, 0xFFFFFF); // Render item name

            // **Adjust item price position**
            int priceX = boxX + boxWidth - 100;  // X position of the price
            int priceY = boxY + 10;  // Y position of the price
            drawString(poseStack, Minecraft.getInstance().font, "$" + tradeItem.getPrice(), priceX, priceY, 0xFFFFFF); // Render price

            // Button size and position
            int buttonWidth = 10; // Width of the buttons
            int buttonHeight = 5; // Height of the buttons

            // Position the "Buy" button
            CustomButton buyButton = new CustomButton(boxX + boxWidth - 130, boxY + 10, buttonWidth, buttonHeight, Component.nullToEmpty("Buy"), button -> buyItem(tradeItem));

            // Position the "Sell" button (right next to the "Buy" button)
            CustomButton sellButton = new CustomButton(boxX + boxWidth - 70, boxY + 10, buttonWidth, buttonHeight, Component.nullToEmpty("Sell"), button -> sellItem(tradeItem));

            // Render the buttons
            buyButton.render(poseStack, mouseX, mouseY, partialTicks);
            sellButton.render(poseStack, mouseX, mouseY, partialTicks);
        }

        // Render scrollbar (unchanged)
        int scrollbarHeight = Math.max((int) ((double) VISIBLE_ITEMS / tradeItems.size() * height), 32);
        int scrollbarY = (int) ((double) scrollOffset / tradeItems.size() * (height - scrollbarHeight)) + y;
        fill(poseStack, x + width - 5, scrollbarY, x + width, scrollbarY + scrollbarHeight, 0xFFAAAAAA);
        fill(poseStack, x + width - 5, scrollbarY, x + width, scrollbarY + scrollbarHeight, 0xFF555555);
    }


    public static void buyItem(TradingScreen.TradeItem tradeItem) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            int balance = PlayerMoneyManager.getPlayerBalance(player);
            if (balance >= tradeItem.getPrice()) {
                PlayerMoneyManager.subtractMoney(player, tradeItem.getPrice()); // Deduct buy price from balance

                ItemStack itemToAdd = tradeItem.getItem().copy();
                boolean added = player.getInventory().add(itemToAdd);

                if (!added) {
                    player.drop(itemToAdd, false);
                }

                // Update the screen's balance
                playerBalance = PlayerMoneyManager.getPlayerBalance(player);
            } else {
                player.sendMessage(Component.nullToEmpty("You do not have enough money to buy this item"), player.getUUID());
            }
        }
    }

    public static void sellItem(TradingScreen.TradeItem tradeItem) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            int slot = player.getInventory().findSlotMatchingItem(tradeItem.getItem());
            if (slot >= 0) {
                ItemStack stackInSlot = player.getInventory().getItem(slot);
                if (!stackInSlot.isEmpty() && stackInSlot.getCount() >= tradeItem.getItem().getCount()) {
                    stackInSlot.shrink(tradeItem.getItem().getCount());
                    PlayerMoneyManager.addMoney(player, tradeItem.getPrice()); // Add sell price to balance

                    // Update the screen's balance
                    playerBalance = PlayerMoneyManager.getPlayerBalance(player);
                }
            } else {
                player.sendMessage(Component.nullToEmpty("You do not have this item to sell"), player.getUUID());
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta > 0) {
            scrollOffset = Math.max(scrollOffset - 1, 0);
        } else if (delta < 0) {
            scrollOffset = Math.min(scrollOffset + 1, tradeItems.size() - VISIBLE_ITEMS);
        }
        return true;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        // Optional: Implement narration support here
    }
}
