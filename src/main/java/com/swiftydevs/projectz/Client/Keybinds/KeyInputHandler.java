package org.sammyp.dayz.Client.Keybinds;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.sammyp.dayz.Common.Items.guns.itemGun;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KeyInputHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;

            if (KeyBindings.reloadKey.consumeClick()) {
                if (mc.player.getMainHandItem().getItem() instanceof itemGun) {
                    itemGun gun = (itemGun) mc.player.getMainHandItem().getItem();

                    gun.reload(mc.player, mc.player.getMainHandItem());
                }else {
                    mc.player.sendMessage(new TextComponent("§cYou don't have a gun in your hand"), mc.player.getUUID());
                }
            }
        }
    }
}
