package com.theendercore.eliptic.init;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import static com.theendercore.eliptic.Eliptic.LOGGER;

public class ELPLootInjection {
    public static void init() {
        LootTableEvents.MODIFY.register(ELPLootInjection::modifyLootTable);
    }

    private static void modifyLootTable(ResourceManager var1, LootManager var2, Identifier id, LootTable.Builder builder, LootTableSource _i) {
        if (!id.equals(LootTables.ANCIENT_CITY_CHEST)) return;
        LOGGER.info("Injecting loot table for Ancient City Chest");
        builder.pool(new LootPool.Builder().with(ItemEntry.builder(ELPItems.ECHO_CORE_FRAGMENT)
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0f, 2f)))));

    }
}
