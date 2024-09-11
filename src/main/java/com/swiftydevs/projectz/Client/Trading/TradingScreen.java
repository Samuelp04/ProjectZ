package com.swiftydevs.projectz.Client.Trading;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.swiftydevs.projectz.Client.GUI.main.CustomButton;
import com.swiftydevs.projectz.Client.PlayerMoney.PlayerMoneyManager;
import com.swiftydevs.projectz.Common.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TradingScreen extends Screen {
    private int guiLeft, guiTop;
    private int xSize = 256; // Width of the GUI
    private int ySize = 160; // Height of the GUI

    private List<TradeItem> buyItems;
    private List<TradeItem> sellItems;
    private int selectedQuantity = 1;
    private TradeItem selectedItem = null;
    private boolean isBuying = true;
    private Button selectedButton = null;
    private CustomButton itemButton;

    public TradingScreen() {
        super(new TextComponent("Trader"));
    }

    // Item Trading List with Custom Image Path
    private static class TradeItem {
        private final Item item;
        private final int price;
        private final String customImagePath; // Path to the custom image

        public TradeItem(Item item, int price, String customImagePath) {
            this.item = item;
            this.price = price;
            this.customImagePath = customImagePath;
        }

        public Item getItem() {
            return item;
        }

        public int getPrice() {
            return price;
        }

        public String getCustomImagePath() {
            return customImagePath;
        }
    }

    @Override
    protected void init() {
        super.init();

        // Set the top-left corner of the GUI
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        // Initialize trading items
        initializeTradeItems();

        // Create buttons for categories (Buy, Sell)
        this.addRenderableWidget(new CustomButton(this.guiLeft + 20, this.guiTop + 30, 50, 20, new TextComponent("Buy"), button -> {
            isBuying = true;
            updateItemButtons();
        }));
        this.addRenderableWidget(new CustomButton(this.guiLeft + 80, this.guiTop + 30, 50, 20, new TextComponent("Sell"), button -> {
            isBuying = false;
            updateItemButtons();
        }));

        // Create quantity controls
        this.addRenderableWidget(new CustomButton(this.guiLeft + 190, this.guiTop + 120, 20, 20, new TextComponent("-"), button -> {
            if (selectedQuantity > 1) {
                selectedQuantity--;
            }
        }));
        this.addRenderableWidget(new CustomButton(this.guiLeft + 230, this.guiTop + 120, 20, 20, new TextComponent("+"), button -> {
            selectedQuantity++;
        }));

        // Create the buy/sell button
        this.addRenderableWidget(new CustomButton(this.guiLeft + 190, this.guiTop + 150, 60, 20, new TextComponent("Confirm"), button -> {
            if (selectedItem != null) {
                Player player = Minecraft.getInstance().player;
                if (isBuying) {
                    handleBuy(player);
                } else {
                    handleSell(player);
                }
            }
        }));

        // Initial item buttons setup
        updateItemButtons();
    }

    private void initializeTradeItems() {
        buyItems = new ArrayList<>();
        sellItems = new ArrayList<>();

        // Add some example items for buying and selling, with custom images
        buyItems.add(new TradeItem(ModItems.MED_KIT.get(), 100, "med_kit.png"));
        buyItems.add(new TradeItem(ModItems.BANDAGE.get(), 50, "bandage.png"));
        buyItems.add(new TradeItem(ModItems.PAIN_KILLERS.get(), 25, "pain_killers.png"));

        sellItems.add(new TradeItem(ModItems.MED_KIT.get(), 80, "med_kit.png"));
        sellItems.add(new TradeItem(ModItems.BANDAGE.get(), 40, "bandage.png"));
        sellItems.add(new TradeItem(ModItems.PAIN_KILLERS.get(), 20, "pain_killers.png"));
    }

    private void updateItemButtons() {
        // Clear existing item buttons
        this.clearWidgets();

        // Recreate buttons for categories
        this.addRenderableWidget(new CustomButton(this.guiLeft + 20, this.guiTop + 30, 50, 20, new TextComponent("Buy"), button -> {
            isBuying = true;
            updateItemButtons();
        }));
        this.addRenderableWidget(new CustomButton(this.guiLeft + 80, this.guiTop + 30, 50, 20, new TextComponent("Sell"), button -> {
            isBuying = false;
            updateItemButtons();
        }));

        // Create item grid buttons
        List<TradeItem> currentItems = isBuying ? buyItems : getSellableItems();
        int itemGridStartX = this.guiLeft + 20;
        int itemGridStartY = this.guiTop + 60;
        int buttonSize = 36; // Increased button size
        int spacing = 10; // Spacing between buttons

        for (int i = 0; i < currentItems.size(); i++) {
            TradeItem tradeItem = currentItems.get(i);
            int x = itemGridStartX + (i % 3) * (buttonSize + spacing);
            int y = itemGridStartY + (i / 3) * (buttonSize + spacing);

            // Create item button
            itemButton = new CustomButton(x, y, buttonSize, buttonSize, TextComponent.EMPTY, button -> {
                selectedItem = tradeItem;
                selectedButton = itemButton; // Set the selected button
                updateItemButtons(); // Re-render to highlight the selected button
            }) {
                @Override
                public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
                    // Bind the custom texture
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderTexture(0, new ResourceLocation("modid", "textures/items/" + tradeItem.getCustomImagePath()));

                    // Draw the custom image
                    blit(poseStack, this.x + 8, this.y + 8, 0, 0, buttonSize, buttonSize, buttonSize, buttonSize);

                    // Render item name and price below the icon
                    int textColor = (selectedButton == this) ? 0xFFFF00 : 0xFFFFFF; // Highlight color if selected
                    TradingScreen.this.font.draw(poseStack, tradeItem.getItem().getDescription().getString(), this.x + 8, this.y + buttonSize + 4, textColor);
                    TradingScreen.this.font.draw(poseStack, "$" + tradeItem.getPrice(), this.x + 8, this.y + buttonSize + 16, textColor);

                    super.renderButton(poseStack, mouseX, mouseY, partialTicks); // Call the superclass render method
                }
            };

            // Add the button to the screen
            this.addRenderableWidget(itemButton);
        }

        // Recreate quantity controls and confirm button
        this.addRenderableWidget(new CustomButton(this.guiLeft + 190, this.guiTop + 120, 20, 20, new TextComponent("-"), button -> {
            if (selectedQuantity > 1) {
                selectedQuantity--;
            }
        }));
        this.addRenderableWidget(new CustomButton(this.guiLeft + 230, this.guiTop + 120, 20, 20, new TextComponent("+"), button -> {
            selectedQuantity++;
        }));

        // Confirm button
        this.addRenderableWidget(new CustomButton(this.guiLeft + 190, this.guiTop + 150, 60, 20, new TextComponent("Confirm"), button -> {
            if (selectedItem != null) {
                Player player = Minecraft.getInstance().player;
                if (isBuying) {
                    handleBuy(player);
                } else {
                    handleSell(player);
                }
            }
        }));
    }

    private List<TradeItem> getSellableItems() {
        Player player = Minecraft.getInstance().player;
        List<TradeItem> sellableItems = new ArrayList<>();
        for (TradeItem item : sellItems) {
            if (player.getInventory().countItem(item.getItem()) > 0) {
                sellableItems.add(item);
            }
        }
        return sellableItems;
    }

    private void handleBuy(Player player) {
        int totalPrice = selectedItem.getPrice() * selectedQuantity;
        if (PlayerMoneyManager.getPlayerBalance(player) >= totalPrice) {
            boolean addedSuccessfully = player.getInventory().add(new ItemStack(selectedItem.getItem(), selectedQuantity));
            if (addedSuccessfully) {
                PlayerMoneyManager.addMoney(player, -totalPrice);
                player.displayClientMessage(new TextComponent("Bought " + selectedQuantity + " " + selectedItem.getItem().getDescription().getString()), true);
            } else {
                player.displayClientMessage(new TextComponent("Not enough space in inventory!"), true);
            }
        } else {
            player.displayClientMessage(new TextComponent("Not enough money!"), true);
        }
    }

    private void handleSell(Player player) {
        int totalPrice = selectedItem.getPrice() * selectedQuantity;

        // Check if the player has enough items to sell
        if (player.getInventory().countItem(selectedItem.getItem()) >= selectedQuantity) {
            // Remove the items from the player's inventory manually
            player.getInventory().removeItem(new ItemStack(selectedItem.getItem(), selectedQuantity));

            // Add the money to the player's balance
            PlayerMoneyManager.addMoney(player, totalPrice);

            // Display a message to the player
            player.displayClientMessage(new TextComponent("Sold " + selectedQuantity + " " + selectedItem.getItem().getDescription().getString()), true);
        } else {
            // Display a message if the player doesn't have enough items
            player.displayClientMessage(new TextComponent("Not enough items to sell!"), true);
        }
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        // Render the background and GUI
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);

        // Draw the GUI window
        this.fillGradient(poseStack, guiLeft, guiTop, guiLeft + xSize, guiTop + ySize, 0xC0101010, 0xD0101010);

        // Draw the title
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, this.guiTop + 10, 0xFFFFFF);

        // Draw selected quantity
        drawString(poseStack, this.font, "Quantity: " + selectedQuantity, guiLeft + 190, guiTop + 100, 0xFFFFFF);
    }
}
