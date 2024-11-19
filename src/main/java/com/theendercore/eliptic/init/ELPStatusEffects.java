package com.theendercore.eliptic.init;

import com.theendercore.eliptic.effect.EchoStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;

import static com.theendercore.eliptic.Eliptic.id;

public class ELPStatusEffects {
    public static final StatusEffect ECHO = register("echo", new EchoStatusEffect());

    public static void init() {
    }

    public static StatusEffect register(String name, StatusEffect item) {
        return Registry.register(Registry.STATUS_EFFECT, id(name), item);
    }

}
