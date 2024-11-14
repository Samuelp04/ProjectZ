package com.swiftydevs.projectz.Client.Trading;

import com.mojang.blaze3d.vertex.PoseStack;
import com.swiftydevs.projectz.Client.GUI.main.CustomButton;
import com.swiftydevs.projectz.Client.PlayerMoney.PlayerMoneyManager;
import com.swiftydevs.projectz.Common.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TradingScreen extends Screen {
    private ScrollableTradeWidget scrollableTradeWidget;
    private static int playerBalance;

    public TradingScreen() {
        super(Component.nullToEmpty("Trading"));
    }

    @Override
    protected void init() {
        super.init();
        List<TradeItem> tradeItems = new ArrayList<>();
        addTradingEntries(tradeItems);
        this.scrollableTradeWidget = new ScrollableTradeWidget(this.width / 2 - 80, 40, 160, this.height - 80, tradeItems, this);
        this.addWidget(this.scrollableTradeWidget);

        // Get the player's balance
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            this.playerBalance = PlayerMoneyManager.getPlayerBalance(player);
        }
    }

    private void addTradingEntries(List<TradeItem> tradeItems) {
        tradeItems.add(new TradeItem(new ItemStack(ModItems.MED_KIT.get(), 1), 100));
        tradeItems.add(new TradeItem(new ItemStack(ModItems.DRESSING_PACKAGE.get(), 1), 100));
        tradeItems.add(new TradeItem(new ItemStack(ModItems.BANDAGE.get(), 1), 100));
        tradeItems.add(new TradeItem(new ItemStack(ModItems.PAIN_KILLERS.get(), 1), 100));
        tradeItems.add(new TradeItem(new ItemStack(ModItems.RAG.get(), 1), 100));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);
        drawCenteredString(poseStack, this.font, "Balance: " + this.playerBalance, this.width / 2, 10, 0xFFFFFF);
        this.scrollableTradeWidget.render(poseStack, mouseX, mouseY, partialTicks);
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
}
