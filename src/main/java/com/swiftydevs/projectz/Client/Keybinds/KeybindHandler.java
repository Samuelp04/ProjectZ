package com.swiftydevs.projectz.Client.Keybinds;

import com.swiftydevs.projectz.Common.Items.backpacks.BackpackItem;
import com.swiftydevs.projectz.ProjectZ;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = ProjectZ.MOD_ID, value = Dist.CLIENT)
public class KeybindHandler {
    private static final KeyMapping OPEN_BACKPACK_KEY = new KeyMapping(
            "key.backpackmod.open_backpack",
            GLFW.GLFW_KEY_B,
            "key.categories.inventory"
    );

    public static void register() {
        ClientRegistry.registerKeyBinding(OPEN_BACKPACK_KEY);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (OPEN_BACKPACK_KEY.consumeClick()) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;

            if (player != null) {
                ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);

                if (chestStack.getItem() instanceof BackpackItem) {

                } else {
                    player.displayClientMessage(new TextComponent("You must wear a backpack to open it!"), true);
                }
            }
        }
    }
}
