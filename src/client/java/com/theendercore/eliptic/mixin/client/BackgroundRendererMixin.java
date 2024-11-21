package com.theendercore.eliptic.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import com.theendercore.eliptic.init.ELPStatusEffects;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", ordinal = 1), method = "render")
    private static void eliptic$customFog(float red, float green, float blue, float alpha, @Local(argsOnly = true) Camera camera) {
        if (camera != null && camera.getFocusedEntity() instanceof PlayerEntity player && player.hasStatusEffect(ELPStatusEffects.ECHO)) {
            setColor(-16777041);
        } else RenderSystem.clearColor(red, green, blue, 0.0F);
    }
    @Unique
    private static void setColor(int color) {
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        RenderSystem.clearColor(red, green, blue, 0.0F);
    }
}
