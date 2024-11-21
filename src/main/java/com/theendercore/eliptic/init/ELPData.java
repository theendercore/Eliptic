package com.theendercore.eliptic.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import static com.theendercore.eliptic.Eliptic.id;

public class ELPData implements EntityComponentInitializer {
    public static final ComponentKey<ElipticData> ELIPTIC_DATA =
            ComponentRegistry.getOrCreate(id("eliptic_data"), ElipticData.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry factory) {
        factory.registerFor(PlayerEntity.class, ELIPTIC_DATA, ElipticData::new);
    }

    public static void setEcho(Entity entity, boolean value) {
        var data = ELIPTIC_DATA.get(entity);
        data.echo = value;
        ELIPTIC_DATA.sync(entity);
    }

    public static boolean getEcho(Entity entity) {
        return ELIPTIC_DATA.get(entity).echo;
    }

    public static boolean getOverlay(Entity entity) {
        return ELIPTIC_DATA.get(entity).overlay;
    }

    public static void setOverlay(Entity entity, boolean val) {
        var data = ELIPTIC_DATA.get(entity);
        data.overlay = val;
        ELIPTIC_DATA.sync(entity);
    }

    public static void setCooldownReset(Entity entity, boolean val) {
        var data = ELIPTIC_DATA.get(entity);
        data.cooldownReset = val;
        ELIPTIC_DATA.sync(entity);
    }

    public static boolean getCooldownReset(Entity entity) {
        return ELIPTIC_DATA.get(entity).cooldownReset;
    }

    public static void setTridentUse(Entity entity, boolean val) {
        var data = ELIPTIC_DATA.get(entity);
        data.tridentUse = val;
        ELIPTIC_DATA.sync(entity);
    }

    public static boolean getTridentUse(Entity entity) {
        return ELIPTIC_DATA.get(entity).tridentUse;
    }


    @SuppressWarnings("UnstableApiUsage")
    public static class ElipticData implements PlayerComponent<ElipticData>, AutoSyncedComponent {
        public ElipticData(Entity entity) {
        }

        public boolean echo = false;
        public boolean overlay = false;
        public boolean cooldownReset = false;
        public boolean tridentUse = false;

        @Override
        public void readFromNbt(NbtCompound nbt) {
            if (nbt.contains("echo")) echo = nbt.getBoolean("echo");
            if (nbt.contains("overlay")) overlay = nbt.getBoolean("overlay");
            if (nbt.contains("cooldownReset")) cooldownReset = nbt.getBoolean("cooldownReset");
            if (nbt.contains("tridentUse")) tridentUse = nbt.getBoolean("tridentUse");
        }

        @Override
        public void writeToNbt(NbtCompound nbt) {
            nbt.putBoolean("echo", echo);
            nbt.putBoolean("overlay", overlay);
            nbt.putBoolean("cooldownReset", cooldownReset);
            nbt.putBoolean("tridentUse", tridentUse);
        }
    }
}
