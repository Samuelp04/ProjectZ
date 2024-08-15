package com.swiftydevs.projectz.Client.Trading;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.swiftydevs.projectz.Client.GUI.main.CustomButton;
import com.swiftydevs.projectz.Client.PlayerMoney.PlayerMoneyManager;
import com.swiftydevs.projectz.Common.init.ModItems;
import com.swiftydevs.projectz.ProjectZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

public class TradingScreen extends Screen {
    private final int xSize = 176;
    private final int ySize = 166;
    private TradingList tradingList;
    private int playerBalance;

    public TradingScreen() {
        super(Component.nullToEmpty("Trading"));
    }

    @Override
    protected void init() {
        super.init();
        this.tradingList = new TradingList(this.minecraft, this.width, this.height, 40, this.height - 40, 40);
        this.addWidget(this.tradingList);

        // Add items to the trading list here
        addTradingEntries();

        // Get the player's balance
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            this.playerBalance = PlayerMoneyManager.getPlayerBalance(player);
        }
    }

    private void addTradingEntries() {
        tradingList.addEntry(new TradingEntry(new ItemStack(ModItems.MED_KIT.get(), 1), 100, 50));
        tradingList.addEntry(new TradingEntry(new ItemStack(ModItems.DRESSING_PACKAGE.get(), 1), 100, 50));
        tradingList.addEntry(new TradingEntry(new ItemStack(ModItems.BANDAGE.get(), 1), 100, 50));
        tradingList.addEntry(new TradingEntry(new ItemStack(ModItems.PAIN_KILLERS.get(), 1), 100, 50));
        tradingList.addEntry(new TradingEntry(new ItemStack(ModItems.RAG.get(), 1), 100, 50));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        // Render the trading list and other components
        this.tradingList.render(poseStack, mouseX, mouseY, partialTicks);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        fill(poseStack, centerX - (this.width / 2) + 20, centerY - (this.height / 2) + 20, centerX + (this.width / 2) - 20, centerY + (this.height / 2) - 20, 0x30303030);

        // Render the player's balance
        drawCenteredString(poseStack, this.font, "Balance: " + this.playerBalance, this.width / 2, 10, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Do not pause the game
    }

    class TradingList extends AbstractSelectionList<TradingEntry> {
        public TradingList(Minecraft mc, int width, int height, int top, int bottom, int itemHeight) {
            super(mc, width, height, top, bottom, itemHeight);
        }

        @Override
        protected int getScrollbarPosition() {
            return this.width - 10;
        }

        @Override
        public int getRowWidth() {
            return this.width - 20;
        }

        public int addEntry(TradingEntry entry) {
            super.addEntry(entry);
            return 0;
        }

        @Override
        public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
            // Render the trading list
            super.render(poseStack, mouseX, mouseY, partialTicks);

            // Render a simple scrollbar
            int scrollbarPosition = this.getScrollbarPosition();
            int maxScroll = this.getMaxScroll();
            int scrollbarHeight = Math.max((this.getBottom() - this.getTop()) * (this.getBottom() - this.getTop()) / maxScroll, 32);
            int scrollbarY = (int) (this.getScrollAmount() * (this.getBottom() - this.getTop() - scrollbarHeight) / maxScroll + this.getTop());

            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            fill(poseStack, scrollbarPosition, scrollbarY, scrollbarPosition + 6, scrollbarY + scrollbarHeight, 0xFFAAAAAA);
            fill(poseStack, scrollbarPosition, scrollbarY, scrollbarPosition + 6, scrollbarY + scrollbarHeight, 0xFF555555);
        }

        @Override
        public void updateNarration(NarrationElementOutput narrationElementOutput) {
        }
    }

    class TradingEntry extends AbstractSelectionList.Entry<TradingEntry> {
        private final ItemStack itemStack;
        private final int buyPrice;
        private final int sellPrice;
        private final Button buyButton;
        private final Button sellButton;

        public TradingEntry(ItemStack itemStack, int buyPrice, int sellPrice) {
            this.itemStack = itemStack;
            this.buyPrice = buyPrice;
            this.sellPrice = sellPrice;
            this.buyButton = new CustomButton(0, 0, 50, 20, Component.nullToEmpty("Buy"), button -> buyItem());
            this.sellButton = new CustomButton(0, 0, 50, 20, Component.nullToEmpty("Sell"), button -> sellItem());
        }

        @Override
        public void render(PoseStack poseStack, int index, int y, int x, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
            Minecraft mc = Minecraft.getInstance();
            mc.getItemRenderer().renderGuiItem(this.itemStack, x, y);
            mc.font.draw(poseStack, "Buy: " + this.buyPrice, x + 20, y + 5, 0xFFFFFF);
            mc.font.draw(poseStack, "Sell: " + this.sellPrice, x + 20, y + 20, 0xFFFFFF);

            this.buyButton.x = x + 100;
            this.buyButton.y = y;
            this.buyButton.render(poseStack, mouseX, mouseY, partialTicks);

            this.sellButton.x = x + 100;
            this.sellButton.y = y + 20;
            this.sellButton.render(poseStack, mouseX, mouseY, partialTicks);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0) {
                if (this.buyButton.mouseClicked(mouseX, mouseY, button)) {
                    return true;
                }
                if (this.sellButton.mouseClicked(mouseX, mouseY, button)) {
                    return true;
                }
            }
            return false;
        }

        private void buyItem() {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                int balance = PlayerMoneyManager.getPlayerBalance(player);
                if (balance >= this.buyPrice) {
                    PlayerMoneyManager.addMoney(player, -this.buyPrice); // Deduct buy price from balance

                    ItemStack itemToAdd = this.itemStack.copy();
                    boolean added = Minecraft.getInstance().player.getInventory().add(itemToAdd);


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

        private void sellItem() {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                int slot = player.getInventory().findSlotMatchingItem(this.itemStack);
                if (slot >= 0) {
                    ItemStack stackInSlot = player.getInventory().getItem(slot);
                    if (!stackInSlot.isEmpty() && stackInSlot.getCount() >= this.itemStack.getCount()) {
                        stackInSlot.shrink(this.itemStack.getCount());
                        PlayerMoneyManager.addMoney(player, this.sellPrice); // Add sell price to balance

                        // Update the screen's balance
                        playerBalance = PlayerMoneyManager.getPlayerBalance(player);
                    } else {
                        player.sendMessage(Component.nullToEmpty("You do not have enough of the item to sell"), player.getUUID());
                    }
                } else {
                    player.sendMessage(Component.nullToEmpty("You do not have the item to sell"), player.getUUID());
                }
            }
        }
    }
}