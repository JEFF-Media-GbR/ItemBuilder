package com.jeff_media.itembuilder;

import lombok.Data;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class GenericMeta implements ConfigurationSerializable {

    private String displayName;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments;


    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String,Object> map = new HashMap<>();
        map.put("displayName",displayName);
        map.put("lore",lore);
        map.put("enchantments",enchantments);
        return map;
    }
}
