package com.swiftydevs.projectz.Common.Effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ZombieInfectionEffect extends MobEffect {
    public ZombieInfectionEffect() {
        super(MobEffectCategory.HARMFUL, 0x98D982); // Adjust color as needed
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide) {
            entity.hurt(DamageSource.MAGIC, 1.0F * (amplifier + 1));
        }
        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0; // Apply effect every second
    }
}
