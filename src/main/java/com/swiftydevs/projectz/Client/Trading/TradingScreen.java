package com.swiftydevs.projectz.Client.Trading;

import com.mojang.blaze3d.vertex.PoseStack;
import com.swiftydevs.projectz.Client.PlayerMoney.PlayerMoneyManager;
import com.swiftydevs.projectz.Common.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TradingScreen extends Screen {
    private static final int TAB_HEIGHT = 20;
    private static final int GRID_COLUMNS = 5;
    private static final int ITEM_SIZE = 32;
    private static final int GRID_PADDING = 10;
    private static final int GRID_START_Y = 80; // Adjusted to accommodate tabs and spacing

    private List<TradeItem> buyItems;
    private List<TradeItem> sellItems;
    private TradeItem selectedItem;
    private boolean isBuyTab = true;
    private int playerBalance;
    private String message = "";
    private int messageTimer = 0;

    // Quantity Selector Components
    private EditBox quantityInput;
    private CustomButton minusButton;
    private CustomButton plusButton;
    private CustomButton actionButton;

    public TradingScreen() {
        super(new TextComponent("Trading"));
    }

    @Override
    protected void init() {
        super.init();

        // Initialize item lists
        buyItems = new ArrayList<>();
        sellItems = new ArrayList<>();
        addTradingEntries(buyItems);

        // Get the player's balance
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            this.playerBalance = PlayerMoneyManager.getPlayerBalance(player);
            populateSellItems(player);
        }

        // Initialize Quantity Selector and Action Button
        int selectorY = this.height - 60;
        int centerX = this.width / 2;

        int buttonSize = 20;
        int quantityInputWidth = 40;
        int totalWidth = buttonSize * 2 + quantityInputWidth + 20;

        int selectorStartX = centerX - totalWidth / 2;

        minusButton = new CustomButton(selectorStartX, selectorY, buttonSize, buttonSize, new TextComponent("-"), button -> decreaseQuantity());
        this.addRenderableWidget(minusButton);

        quantityInput = new EditBox(this.font, selectorStartX + buttonSize + 10, selectorY + 2, quantityInputWidth, buttonSize - 4, new TextComponent(""));
        quantityInput.setValue("1");
        quantityInput.setFilter(s -> s.matches("\\d*")); // Only allow numbers
        this.addRenderableWidget(quantityInput);

        plusButton = new CustomButton(selectorStartX + buttonSize + 10 + quantityInputWidth + 10, selectorY, buttonSize, buttonSize, new TextComponent("+"), button -> increaseQuantity());
        this.addRenderableWidget(plusButton);

        actionButton = new CustomButton(centerX - 60, selectorY + buttonSize + 10, 120, 20, new TextComponent("Buy"), button -> performAction());
        this.addRenderableWidget(actionButton);
    }

    private void addTradingEntries(List<TradeItem> tradeItems) {
        tradeItems.add(new TradeItem(new ItemStack(ModItems.MED_KIT.get()), 100));
        tradeItems.add(new TradeItem(new ItemStack(ModItems.DRESSING_PACKAGE.get()), 80));
        tradeItems.add(new TradeItem(new ItemStack(ModItems.BANDAGE.get()), 50));
        tradeItems.add(new TradeItem(new ItemStack(ModItems.PAIN_KILLERS.get()), 60));
        tradeItems.add(new TradeItem(new ItemStack(ModItems.RAG.get()), 30));
        // Add more items as needed
    }

    private void populateSellItems(Player player) {
        sellItems.clear();
        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty()) {
                int price = getPriceForItem(stack);
                if (price > 0) {
                    boolean alreadyAdded = false;
                    for (TradeItem item : sellItems) {
                        if (ItemStack.isSameItemSameTags(item.getItem(), stack)) {
                            alreadyAdded = true;
                            break;
                        }
                    }
                    if (!alreadyAdded) {
                        sellItems.add(new TradeItem(new ItemStack(stack.getItem()), price));
                    }
                }
            }
        }
    }

    private int getPriceForItem(ItemStack stack) {
        for (TradeItem tradeItem : buyItems) {
            if (ItemStack.isSameItemSameTags(stack, tradeItem.getItem())) {
                return tradeItem.getPrice();
            }
        }
        return 0;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        // Render background (no overlay)
        renderBackground(poseStack);

        // Render Shop Box
        renderShopBox(poseStack);

        // Render Tabs with hover detection
        renderTabs(poseStack, mouseX, mouseY);

        // Render Item Grid
        renderItemGrid(poseStack, mouseX, mouseY);

        // Render Quantity Selector and Action Button
        super.render(poseStack, mouseX, mouseY, partialTicks);

        // Render Balance
        // Balance text is on the UI background, render in white for visibility
        this.font.draw(poseStack, "Balance: $" + this.playerBalance, 10, this.height - 20, 0xFFFFFF);

        // Render Message
        if (!message.isEmpty() && messageTimer > 0) {
            int messageWidth = this.font.width(message);
            // Message text should be white for visibility
            this.font.draw(poseStack, message, (this.width - messageWidth) / 2, this.height - 40, 0xFFFFFF);
            messageTimer--;
        }

        // Render Tooltips
        renderTooltips(poseStack, mouseX, mouseY);
    }

    @Override
    public void renderBackground(PoseStack poseStack) {
        // No background overlay
        super.renderBackground(poseStack);
    }

    private void renderTabs(PoseStack poseStack, int mouseX, int mouseY) {
        int tabWidth = 50;
        int tabX;

        int gridWidth = GRID_COLUMNS * (ITEM_SIZE + GRID_PADDING) - GRID_PADDING;
        int boxX = this.width / 2 - gridWidth / 2 - 10;
        tabX = boxX;

        int spacingBetweenButtons = 10;
        int spacingBelowButtons = spacingBetweenButtons;

        int tabY = GRID_START_Y - 30 - spacingBelowButtons; // Adjusted for spacing

        // Determine if mouse is hovering over Buy or Sell tab
        boolean isHoveringBuy = isMouseOver(mouseX, mouseY, tabX, tabY, tabWidth, TAB_HEIGHT);
        boolean isHoveringSell = isMouseOver(mouseX, mouseY, tabX + tabWidth + spacingBetweenButtons, tabY, tabWidth, TAB_HEIGHT);

        // Buy Tab
        fill(poseStack, tabX, tabY, tabX + tabWidth, tabY + TAB_HEIGHT, 0x80000000);
        if (isBuyTab) {
            fill(poseStack, tabX, tabY, tabX + tabWidth, tabY + TAB_HEIGHT, 0x80FFFFFF);
        }
        // Apply hover animation: change background brightness or color
        if (isHoveringBuy && !isBuyTab) {
            fill(poseStack, tabX, tabY, tabX + tabWidth, tabY + TAB_HEIGHT, 0x60FFFFFF); // Slightly more opaque on hover
        }
        int buyTextWidth = this.font.width("Buy");
        // Render "Buy" text: black if active (on white background), white otherwise
        int buyTextColor = isBuyTab ? 0x000000 : 0xFFFFFF;
        this.font.draw(poseStack, "Buy", tabX + tabWidth / 2 - buyTextWidth / 2, tabY + (TAB_HEIGHT - 8) / 2, buyTextColor);

        // Sell Tab
        fill(poseStack, tabX + tabWidth + spacingBetweenButtons, tabY, tabX + tabWidth * 2 + spacingBetweenButtons, tabY + TAB_HEIGHT, 0x80000000);
        if (!isBuyTab) {
            fill(poseStack, tabX + tabWidth + spacingBetweenButtons, tabY, tabX + tabWidth * 2 + spacingBetweenButtons, tabY + TAB_HEIGHT, 0x80FFFFFF);
        }
        // Apply hover animation: change background brightness or color
        if (isHoveringSell && isBuyTab) {
            fill(poseStack, tabX + tabWidth + spacingBetweenButtons, tabY, tabX + tabWidth * 2 + spacingBetweenButtons, tabY + TAB_HEIGHT, 0x60FFFFFF); // Slightly more opaque on hover
        }
        int sellTextWidth = this.font.width("Sell");
        // Render "Sell" text: black if active (on white background), white otherwise
        int sellTextColor = !isBuyTab ? 0x000000 : 0xFFFFFF;
        this.font.draw(poseStack, "Sell", tabX + tabWidth + spacingBetweenButtons + tabWidth / 2 - sellTextWidth / 2, tabY + (TAB_HEIGHT - 8) / 2, sellTextColor);
    }

    private void renderShopBox(PoseStack poseStack) {
        int gridWidth = GRID_COLUMNS * (ITEM_SIZE + GRID_PADDING) - GRID_PADDING;
        int boxX = this.width / 2 - gridWidth / 2 - 10;
        int boxY = GRID_START_Y - 10;
        int boxWidth = gridWidth + 20;

        // Increase the bottom space of the item box
        int extraBottomSpace = 20; // Adjust this value to increase bottom spacing
        int boxHeight = getGridHeight() + 10 + extraBottomSpace;

        // Draw shop background box with semi-transparent white
        fill(poseStack, boxX, boxY, boxX + boxWidth, boxY + boxHeight, 0x80FFFFFF);
    }

    private void renderItemGrid(PoseStack poseStack, int mouseX, int mouseY) {
        List<TradeItem> items = isBuyTab ? buyItems : sellItems;
        int gridWidth = GRID_COLUMNS * (ITEM_SIZE + GRID_PADDING) - GRID_PADDING;
        int startX = this.width / 2 - gridWidth / 2;
        int startY = GRID_START_Y;

        int index = 0;
        for (TradeItem item : items) {
            int col = index % GRID_COLUMNS;
            int row = index / GRID_COLUMNS;

            int x = startX + col * (ITEM_SIZE + GRID_PADDING);
            int y = startY + row * (ITEM_SIZE + GRID_PADDING);

            boolean isMouseOverItem = isMouseOver(mouseX, mouseY, x, y, ITEM_SIZE, ITEM_SIZE);

            int itemBackgroundColor = 0x80000000; // Default semi-transparent black
            if (isMouseOverItem || item == selectedItem) {
                itemBackgroundColor = 0x80FFFFFF; // Semi-transparent white
            }
            fill(poseStack, x, y, x + ITEM_SIZE, y + ITEM_SIZE, itemBackgroundColor);

            // Render item icon
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.renderGuiItem(item.getItem(), x + (ITEM_SIZE - 16) / 2, y + (ITEM_SIZE - 16) / 2);

            // Render price under the item icon
            String priceText = "$" + item.getPrice();
            int priceWidth = this.font.width(priceText);
            int priceX = x + (ITEM_SIZE - priceWidth) / 2;
            int priceY = y + ITEM_SIZE + 5; // Moved price a little bit down

            // Set price text color to white regardless of hover or selection
            int priceTextColor = 0xFFFFFF;
            this.font.draw(poseStack, priceText, priceX, priceY, priceTextColor);

            index++;
        }
    }

    private int getGridHeight() {
        List<TradeItem> items = isBuyTab ? buyItems : sellItems;
        int rows = (int) Math.ceil((double) items.size() / GRID_COLUMNS);
        return rows * (ITEM_SIZE + GRID_PADDING) - GRID_PADDING + 12;
    }

    private void decreaseQuantity() {
        int quantity;
        try {
            quantity = Integer.parseInt(quantityInput.getValue());
        } catch (NumberFormatException e) {
            quantity = 1;
        }
        if (quantity > 1) {
            quantity--;
            quantityInput.setValue(String.valueOf(quantity));
        }
    }

    private void increaseQuantity() {
        int quantity;
        try {
            quantity = Integer.parseInt(quantityInput.getValue());
        } catch (NumberFormatException e) {
            quantity = 1;
        }
        quantity++;
        quantityInput.setValue(String.valueOf(quantity));
    }

    private void performAction() {
        if (selectedItem == null) {
            showMessage("No item selected.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityInput.getValue());
        } catch (NumberFormatException e) {
            showMessage("Invalid quantity.");
            return;
        }

        if (quantity <= 0) {
            showMessage("Invalid quantity.");
            return;
        }

        if (isBuyTab) {
            buyItem(selectedItem, quantity);
        } else {
            sellItem(selectedItem, quantity);
        }
    }

    private void showMessage(String msg) {
        this.message = msg;
        this.messageTimer = 100;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int tabWidth = 50;
        int tabX;

        int gridWidth = GRID_COLUMNS * (ITEM_SIZE + GRID_PADDING) - GRID_PADDING;
        int boxX = this.width / 2 - gridWidth / 2 - 10;
        tabX = boxX;
        int tabY = GRID_START_Y - 30 - 10; // Added spacing below tabs

        int spacingBetweenButtons = 10;

        // Check if Buy tab is clicked
        if (isMouseOver(mouseX, mouseY, tabX, tabY, tabWidth, TAB_HEIGHT)) {
            isBuyTab = true;
            actionButton.setMessage(new TextComponent("Buy"));
            selectedItem = null;
            return true;
        }
        // Check if Sell tab is clicked
        else if (isMouseOver(mouseX, mouseY, tabX + tabWidth + spacingBetweenButtons, tabY, tabWidth, TAB_HEIGHT)) {
            isBuyTab = false;
            actionButton.setMessage(new TextComponent("Sell"));
            selectedItem = null;
            return true;
        }

        List<TradeItem> items = isBuyTab ? buyItems : sellItems;
        int startX = this.width / 2 - gridWidth / 2;
        int startY = GRID_START_Y;

        int index = 0;
        for (TradeItem item : items) {
            int col = index % GRID_COLUMNS;
            int row = index / GRID_COLUMNS;

            int x = startX + col * (ITEM_SIZE + GRID_PADDING);
            int y = startY + row * (ITEM_SIZE + GRID_PADDING);

            if (isMouseOver(mouseX, mouseY, x, y, ITEM_SIZE, ITEM_SIZE)) {
                selectedItem = item;
                quantityInput.setValue("1");
                return true;
            }

            index++;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isMouseOver(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    private void buyItem(TradeItem tradeItem, int quantity) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            int totalPrice = tradeItem.getPrice() * quantity;
            int balance = PlayerMoneyManager.getPlayerBalance(player);
            if (balance >= totalPrice) {
                PlayerMoneyManager.subtractMoney(player, totalPrice);

                ItemStack itemToAdd = tradeItem.getItem().copy();
                itemToAdd.setCount(quantity);
                boolean added = player.getInventory().add(itemToAdd);

                if (!added) {
                    player.drop(itemToAdd, false);
                }

                this.playerBalance = PlayerMoneyManager.getPlayerBalance(player);
                showMessage("Purchased " + quantity + "x " + tradeItem.getItem().getHoverName().getString());

                populateSellItems(player);
            } else {
                showMessage("Not enough money.");
            }
        }
    }

    private void sellItem(TradeItem tradeItem, int quantity) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            ItemStack itemStack = new ItemStack(tradeItem.getItem().getItem(), quantity);
            int totalQuantity = 0;

            for (ItemStack stack : player.getInventory().items) {
                if (ItemStack.isSameItemSameTags(stack, itemStack)) {
                    totalQuantity += stack.getCount();
                }
            }

            if (totalQuantity >= quantity) {
                int remaining = quantity;
                for (int i = 0; i < player.getInventory().items.size(); i++) {
                    ItemStack stack = player.getInventory().items.get(i);
                    if (ItemStack.isSameItemSameTags(stack, itemStack)) {
                        int count = stack.getCount();
                        if (count <= remaining) {
                            player.getInventory().items.set(i, ItemStack.EMPTY);
                            remaining -= count;
                        } else {
                            stack.shrink(remaining);
                            remaining = 0;
                        }
                        if (remaining == 0) {
                            break;
                        }
                    }
                }

                int totalPrice = tradeItem.getPrice() * quantity;
                PlayerMoneyManager.addMoney(player, totalPrice);

                this.playerBalance = PlayerMoneyManager.getPlayerBalance(player);
                showMessage("Sold " + quantity + "x " + tradeItem.getItem().getHoverName().getString());

                populateSellItems(player);
            } else {
                showMessage("Not enough items to sell.");
            }
        }
    }

    private void renderTooltips(PoseStack poseStack, int mouseX, int mouseY) {
        List<TradeItem> items = isBuyTab ? buyItems : sellItems;
        int gridWidth = GRID_COLUMNS * (ITEM_SIZE + GRID_PADDING) - GRID_PADDING;
        int startX = this.width / 2 - gridWidth / 2;
        int startY = GRID_START_Y;

        int index = 0;
        for (TradeItem item : items) {
            int col = index % GRID_COLUMNS;
            int row = index / GRID_COLUMNS;

            int x = startX + col * (ITEM_SIZE + GRID_PADDING);
            int y = startY + row * (ITEM_SIZE + GRID_PADDING);

            if (isMouseOver(mouseX, mouseY, x, y, ITEM_SIZE, ITEM_SIZE)) {
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(item.getItem().getHoverName());
                renderComponentTooltip(poseStack, tooltip, mouseX, mouseY);
            }

            index++;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    class TradeItem {
        private final ItemStack item;
        private final int price;

        public TradeItem(ItemStack item, int price) {
            this.item = item;
            this.price = price;
        }

        public ItemStack getItem() {
            return item;
        }

        public int getPrice() {
            return price;
        }
    }

    // CustomButton class for consistent styling with hover animations
    class CustomButton extends Button {
        public CustomButton(int x, int y, int width, int height, Component message, OnPress onPress) {
            super(x, y, width, height, message, onPress);
        }

        @Override
        public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
            Minecraft minecraft = Minecraft.getInstance();

            // Determine fill color based on hover state
            int fillColor;
            if (this.isHoveredOrFocused()) {
                fillColor = 0xC0FFFFFF; // More opaque white on hover
            } else {
                fillColor = 0x80FFFFFF; // Default semi-transparent white
            }

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
