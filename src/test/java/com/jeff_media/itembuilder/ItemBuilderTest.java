package com.jeff_media.itembuilder;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.jeff_media.itembuilder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

public class ItemBuilderTest {

    private ServerMock server;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void test() {
        ItemBuilder builder = new ItemBuilder(Material.DIAMOND_SWORD, 3);
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 3);
        ItemMeta meta = item.getItemMeta();

        builder.addEnchantment(Enchantment.DAMAGE_ALL, 3, true);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);

        builder.setDisplayName("asd");
        meta.setDisplayName("asd");

        builder.setLore("asd", "asd");
        meta.setLore(Arrays.asList("asd","asd"));

        item.setItemMeta(meta);

        Assertions.assertEquals(item, builder.build());

    }

}
