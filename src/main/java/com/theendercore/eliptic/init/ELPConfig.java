package com.theendercore.eliptic.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class ELPConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Double> TP_HEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Double> SINK_SPEED;
    public static final ForgeConfigSpec.ConfigValue<Integer> ECHO_TRIDENT_COOLDOWN;

    public ELPConfig() {
    }


    static {
        BUILDER.push("Config");
        TP_HEIGHT = BUILDER.comment("The height in blocks when the player uses the echo trident").define("tp_height", 15.0);
        SINK_SPEED = BUILDER.comment("the speed of the player sinking when using the echo trident").define("sink_speed", -0.05);
        ECHO_TRIDENT_COOLDOWN = BUILDER.comment("the cooldown of the trident in ticks").define("echo_trident_cooldown", 200);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
