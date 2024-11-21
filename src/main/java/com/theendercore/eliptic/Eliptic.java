package com.theendercore.eliptic;

import com.theendercore.eliptic.init.ELPConfig;
import com.theendercore.eliptic.init.ELPItems;
import com.theendercore.eliptic.init.ELPLootInjection;
import com.theendercore.eliptic.init.ELPStatusEffects;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Eliptic implements ModInitializer {
    public static final String MOD_ID = "eliptic";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }

    @Override
    public void onInitialize() {
        ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.COMMON, ELPConfig.SPEC);
        ELPItems.init();
        ELPLootInjection.init();
        ELPStatusEffects.init();

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, entity) -> !player.hasStatusEffect(ELPStatusEffects.ECHO));
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (player.hasStatusEffect(ELPStatusEffects.ECHO)) return ActionResult.FAIL;
            else return ActionResult.PASS;
        });
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player.hasStatusEffect(ELPStatusEffects.ECHO)) return ActionResult.FAIL;
            else return ActionResult.PASS;
        });
    }
}