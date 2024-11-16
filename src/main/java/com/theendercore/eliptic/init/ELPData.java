package com.theendercore.eliptic.init;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.minecraft.nbt.NbtCompound;

import static com.theendercore.eliptic.Eliptic.id;

public class ELPData {
    public static final ComponentKey<ElipticData> ELIPTIC_DATA =
            ComponentRegistry.getOrCreate(id("eliptic_data"), ElipticData.class);



    public static class ElipticData implements Component {


        @Override
        public void readFromNbt(NbtCompound nbtCompound) {

        }

        @Override
        public void writeToNbt(NbtCompound nbtCompound) {

        }
    }
}
