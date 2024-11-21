package com.theendercore.eliptic.mixin;

import com.theendercore.eliptic.init.ELPPlayerTick;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	@Inject(at = @At("TAIL"), method = "tick")
	private void eliptic$tickEnd(CallbackInfo info) {
		ELPPlayerTick.init((PlayerEntity)(Object)this);
	}
}