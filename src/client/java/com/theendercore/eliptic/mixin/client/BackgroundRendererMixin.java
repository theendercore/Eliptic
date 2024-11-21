package com.theendercore.eliptic.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.theendercore.eliptic.init.ELPStatusEffects;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Shadow
    private static float red;

    @Shadow
    private static float green;

    @Shadow
    private static float blue;

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", ordinal = 1), method = "render", cancellable = true)
    private static void eliptic$customFog(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
        if (camera != null && camera.getFocusedEntity() instanceof PlayerEntity player && player.hasStatusEffect(ELPStatusEffects.ECHO)) {
            setColor(-16777041);
            RenderSystem.clearColor(red, green, blue, 0.0F);
            ci.cancel();
        }
    }

    @Unique
    private static void setColor(int color) {
        red = (color >> 16 & 255) / 255.0F;
        green = (color >> 8 & 255) / 255.0F;
        blue = (color & 255) / 255.0F;
    }

    @Inject(at = @At("HEAD"), method = "getFogModifier", cancellable = true)
    private static void eliptic$customStatusEffectModifier(Entity entity, float tickDelta, CallbackInfoReturnable<BackgroundRenderer.StatusEffectFogModifier> cir) {
        if (entity instanceof PlayerEntity player && player.hasStatusEffect(ELPStatusEffects.ECHO)) {
            cir.setReturnValue(ELIPTIC$ECHO_FOG_MODIFIER);
        }
    }

    @Unique
    private static final BackgroundRenderer.StatusEffectFogModifier ELIPTIC$ECHO_FOG_MODIFIER = new BackgroundRenderer.StatusEffectFogModifier() {
        @Override
        public StatusEffect getStatusEffect() {
            return ELPStatusEffects.ECHO;
        }

        @Override
        public float applyColorModifier(LivingEntity entity, StatusEffectInstance effect, float f, float tickDelta) {
            return 1f;
        }

        @Override
        public void applyStartEndModifier(BackgroundRenderer.FogData fogData, LivingEntity entity, StatusEffectInstance effect, float viewDistance, float tickDelta) {
            if (fogData.fogType == BackgroundRenderer.FogType.FOG_TERRAIN) {
                fogData.fogStart = 0f;
                fogData.fogEnd = 20f;
            }
        }
    };
}
