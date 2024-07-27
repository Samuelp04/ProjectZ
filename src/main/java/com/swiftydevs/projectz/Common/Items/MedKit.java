package com.swiftydevs.projectz.Common.Items;

import com.swiftydevs.projectz.Client.Tabs;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * A MedKit item that heals the player when used.
 */
public class MedKit extends Item {
    private static final int USE_DURATION = 60;

    public MedKit() {
        super(new Item.Properties()
                .tab(Tabs.MEDICAL_TAB)
                .stacksTo(6)
        );
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity entityLiving, int ticksRemaining) {
        if (entityLiving instanceof Player) {
            Player player = (Player) entityLiving;
            if (!player.level.isClientSide) {
                int useDuration = getUseDuration(stack) - ticksRemaining;
                if (useDuration >= USE_DURATION) {
                    player.heal(10.0F);
                    stack.shrink(1);
                    player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            }
        }
    }


}
