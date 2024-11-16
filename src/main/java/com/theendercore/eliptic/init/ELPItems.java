package com.theendercore.eliptic.init;

import com.theendercore.eliptic.item.GlintItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import java.util.function.Function;

import static com.theendercore.eliptic.Eliptic.id;

public class ELPItems {
    public static final Item ECHO_CORE_FRAGMENT = register("echo_core_fragment", glint(it -> it.rarity(Rarity.UNCOMMON)));
    public static final Item ECHO_CORE = register("echo_core", glint(it -> it.rarity(Rarity.RARE)));
    public static final Item ECHO_TRIDENT = register("echo_trident", new Item(new Item.Settings().group(ItemGroup.COMBAT)));

    public static void init() {
      }

    public static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, id(name), item);
    }

    public static Item glint(Function<Item.Settings, Item.Settings> c) {
        return new GlintItem(c.apply(new Item.Settings().group(ItemGroup.MISC)));
    }
}
