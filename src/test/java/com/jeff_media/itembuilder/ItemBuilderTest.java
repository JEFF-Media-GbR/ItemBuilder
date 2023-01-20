package com.jeff_media.itembuilder;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.jeff_media.itembuilder.ItemBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ItemBuilderTest {

    private ServerMock server;
    private ItemBuilder plugin;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(ItemBuilder.class);
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void test() {
        Assert.assertNotNull(plugin);
    }

}
