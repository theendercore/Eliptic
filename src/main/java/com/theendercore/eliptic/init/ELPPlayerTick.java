package com.theendercore.eliptic.init;

import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import static com.theendercore.eliptic.init.ELPData.*;

public class ELPPlayerTick {
    public static void init(PlayerEntity player) {
        var world = player.world;
        if (world instanceof ServerWorld serverWorld) {
            execute(serverWorld, player);
        }
    }

    private static void execute(ServerWorld world, PlayerEntity player) {
        ItemStack stack = player.getMainHandStack();

        if (stack.isOf(ELPItems.ECHO_TRIDENT)) {
            setTridentUse(player, !player.getItemCooldownManager().isCoolingDown(stack.getItem()));
        }
        if (world.getRegistryKey() == World.END || !getEcho(player)) return;

        if (world.getBlockState(player.getBlockPos().down()).getBlock() != Blocks.AIR) {
            player.fallDistance = 0.0F;
            player.removeStatusEffect(ELPStatusEffects.ECHO);
            setEcho(player, false);
            setOverlay(player, false);

            if (stack.isOf(ELPItems.ECHO_TRIDENT) && getCooldownReset(player)) {
                player.getItemCooldownManager().set(stack.getItem(), ELPConfig.ECHO_TRIDENT_COOLDOWN.get());
                setCooldownReset(player, false);
            }

        } else {
            player.addStatusEffect(new StatusEffectInstance(ELPStatusEffects.ECHO, 2, 0, false, false));
            setOverlay(player, true);
            setCooldownReset(player, true);
            setEcho(player, true);
        }

    }
}
