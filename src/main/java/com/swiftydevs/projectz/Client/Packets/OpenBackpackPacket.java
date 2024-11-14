package com.swiftydevs.projectz.Client.Packets;

import com.swiftydevs.projectz.Common.Items.backpacks.BackpackItem;
import com.tac.guns.inventory.gear.backpack.BackpackContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenBackpackPacket {
    public OpenBackpackPacket() {
        // Constructor logic if needed
    }

    public static void encode(OpenBackpackPacket msg, FriendlyByteBuf buf) {
        // Encode data into the buffer if needed
    }

    public static OpenBackpackPacket decode(FriendlyByteBuf buf) {
        // Decode data from the buffer if needed
        return new OpenBackpackPacket();
    }

    public static void handle(OpenBackpackPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player player = ctx.get().getSender();
            ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);

            if (chestStack.getItem() instanceof BackpackItem) {
                player.openMenu(new SimpleMenuProvider((id, inventory, p) -> new BackpackContainer(id, inventory, chestStack), Component.nullToEmpty("container.projectz.backpack")));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}

