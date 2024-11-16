package com.theendercore.eliptic.effect;

import com.theendercore.eliptic.init.ELPConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class EchoStatusEffect extends StatusEffect {
    public EchoStatusEffect() {
        super(StatusEffectCategory.NEUTRAL, -1);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
////            if (((ElipticModVariables.PlayerVariables) player.getCapability(ElipticModVariables.PLAYER_VARIABLES_CAPABILITY, (Direction) null).orElse(new ElipticModVariables.PlayerVariables())).tridentUse) {
            player.setVelocity(new Vec3d(player.getVelocity().getX(), ELPConfig.SINK_SPEED.get(), player.getVelocity().getZ()));
//            }
        }
    }
}
