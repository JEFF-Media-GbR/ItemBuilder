package com.jeff_media.itembuilder;

import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ItemBuilder {

    static {
        ConfigurationSerialization.registerClass(GenericMeta.class, "GenericMeta");
    }

    private static final PersistentDataType<?, ?>[] DEFAULT_PERSISTENT_DATA_TYPES = new PersistentDataType<?, ?>[]{PersistentDataType.BYTE, PersistentDataType.SHORT, PersistentDataType.INTEGER, PersistentDataType.LONG, PersistentDataType.FLOAT, PersistentDataType.DOUBLE, PersistentDataType.STRING, PersistentDataType.BYTE_ARRAY, PersistentDataType.INTEGER_ARRAY, PersistentDataType.LONG_ARRAY, PersistentDataType.TAG_CONTAINER_ARRAY, PersistentDataType.TAG_CONTAINER,};
    @NotNull
    private Material material;
    private int amount = 1;
    @NotNull
    private ItemMeta meta;
    private FailSafeMode failSafeMode = FailSafeMode.IGNORE;

    public ItemBuilder(@NotNull Material material, int amount) {
        this.material = material;
        this.amount = amount;
        this.meta = Objects.requireNonNull(Bukkit.getItemFactory().getItemMeta(material));
    }

    public ItemBuilder(@NotNull Material material) {
        this.material = material;
        this.meta = Objects.requireNonNull(Bukkit.getItemFactory().getItemMeta(material));
    }

    public ItemBuilder(@NotNull ItemStack itemStack) {
        this.material = itemStack.getType();
        this.amount = itemStack.getAmount();
        this.meta = Objects.requireNonNull(itemStack.getItemMeta());
    }

    @NotNull
    public Material getMaterial() {
        return material;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setMaterial(@NotNull Material material) {
        this.material = material;
        this.meta = Objects.requireNonNull(Bukkit.getItemFactory().asMetaFor(this.meta, material));
        return this;
    }

    public int getAmount() {
        return amount;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setAmount(int amount) {
        if (amount < 1 || amount > 64) {
            failsafe("Amount must be between 1 and 64");
        } else {
            this.amount = amount;
        }
        return this;
    }

    private void failsafe(@NotNull String message) {
        switch (failSafeMode) {
            case IGNORE:
                return;
            case WARN:
                Bukkit.getLogger().warning("[ItemBuilder] " + message);
                return;
            case THROW:
                throw new IllegalArgumentException(message);
        }
    }

    @NotNull
    public List<String> getLore() {
        return meta.getLore() == null ? new ArrayList<>() : meta.getLore();
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setLore(@Nullable List<String> lore) {
        meta.setLore(lore == null ? new ArrayList<>() : lore);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setLore(@Nullable String... lore) {
        List<String> list = new ArrayList<>();
        if (lore != null) {
            Collections.addAll(list, lore);
        }
        meta.setLore(list);
        return this;
    }

    @Nullable
    public String getDisplayName() {
        return meta.getDisplayName();
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setDisplayName(@Nullable String name) {
        meta.setDisplayName(name);
        return this;
    }

    @NotNull
    public ItemMeta getItemMeta() {
        return meta;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setItemMeta(@Nullable ItemMeta meta) {
        this.meta = meta == null ? Objects.requireNonNull(Bukkit.getItemFactory().getItemMeta(material)) : meta;
        return this;
    }

    @Contract(value = "_, _ -> this", mutates = "this")
    public ItemBuilder addAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier) {
        meta.addAttributeModifier(attribute, modifier);
        return this;
    }

    @Nullable
    public Collection<AttributeModifier> getAttributeModifiers(@NotNull Attribute attribute) {
        return meta.getAttributeModifiers(attribute);
    }

    @Nullable
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        return meta.getAttributeModifiers();
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setAttributeModifiers(@Nullable Multimap<Attribute, AttributeModifier> attributeModifiers) {
        meta.setAttributeModifiers(attributeModifiers);
        return this;
    }

    public boolean isUnbreakable() {
        return meta.isUnbreakable();
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    @Nullable
    public String getLocalizedName() {
        return meta.getLocalizedName();
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setLocalizedName(@Nullable String name) {
        meta.setLocalizedName(name);
        return this;
    }

    @Nullable
    public Integer getCustomModelData() {
        return meta.getCustomModelData();
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setCustomModelData(@Nullable Integer data) {
        meta.setCustomModelData(data);
        return this;
    }

    @Contract(value = "_, _, _ -> this", mutates = "this")
    public ItemBuilder addEnchantment(@NotNull Enchantment ench, int level, boolean ignoreLevelRestriction) {
        meta.addEnchant(ench, level, ignoreLevelRestriction);
        return this;
    }

    public int getEnchantmentLevel(@NotNull Enchantment ench) {
        return meta.getEnchantLevel(ench);
    }

    @Contract(value = "_, _, _ -> this", mutates = "this")
    public ItemBuilder addStoredEnchant(@NotNull Enchantment ench, int level, boolean ignoreLevelRestriction) {
        if (meta instanceof EnchantmentStorageMeta) {
            ((EnchantmentStorageMeta) meta).addStoredEnchant(ench, level, ignoreLevelRestriction);
        } else {
            failsafe("ItemMeta is not an instance of EnchantmentStorageMeta");
        }
        return this;
    }

    public int getStoredEnchantmentLevel(@NotNull Enchantment ench) {
        if (meta instanceof EnchantmentStorageMeta) {
            return ((EnchantmentStorageMeta) meta).getStoredEnchantLevel(ench);
        } else {
            failsafe("ItemMeta is not an instance of EnchantmentStorageMeta");
            return 0;
        }
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder addItemFlags(@NotNull ItemFlag... itemFlags) {
        meta.addItemFlags(itemFlags);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder removeItemFlags(@NotNull ItemFlag... itemFlags) {
        meta.removeItemFlags(itemFlags);
        return this;
    }

    @NotNull
    public Set<ItemFlag> getItemFlags() {
        return meta.getItemFlags();
    }

    public boolean hasItemFlag(@NotNull ItemFlag itemFlag) {
        return meta.hasItemFlag(itemFlag);
    }

    @Contract(value = "_, _, _ -> this", mutates = "this")
    public <T, Z> ItemBuilder setPdcData(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> dataType, @NotNull Z value) {
        meta.getPersistentDataContainer().set(key, dataType, value);
        return this;
    }

    @Nullable
    public <T, Z> Z getPdcData(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> dataType) {
        return meta.getPersistentDataContainer().get(key, dataType);
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder removePdcData(@NotNull NamespacedKey key) {
        meta.getPersistentDataContainer().remove(key);
        return this;
    }

    public boolean hasPdcData(@NotNull NamespacedKey key, @NotNull PersistentDataType<?, ?> dataType) {
        return meta.getPersistentDataContainer().has(key, dataType);
    }

    public boolean hasPdcData(@NotNull NamespacedKey key) {
        for (PersistentDataType<?, ?> dataType : DEFAULT_PERSISTENT_DATA_TYPES) {
            if (meta.getPersistentDataContainer().has(key, dataType)) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    public PersistentDataContainer getPdc() {
        return meta.getPersistentDataContainer();
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setPdc(@NotNull PersistentDataContainer pdc) {
        for (NamespacedKey key : meta.getPersistentDataContainer().getKeys()) {
            meta.getPersistentDataContainer().remove(key);
        }
        mergePdc(pdc);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    @SuppressWarnings("unchecked")
    public ItemBuilder mergePdc(@NotNull PersistentDataContainer pdc) {
        outer:
        for (NamespacedKey key : pdc.getKeys()) {
            for (PersistentDataType<?, ?> dataType : DEFAULT_PERSISTENT_DATA_TYPES) {
                if (pdc.has(key, dataType)) {
                    meta.getPersistentDataContainer().set(key, (PersistentDataType<Object, Object>) dataType, Objects.requireNonNull(pdc.get(key, dataType)));
                    continue outer;
                }
            }
        }
        return this;
    }

    @Nullable
    public Color getDyeColor() {
        if (meta instanceof LeatherArmorMeta) {
            return ((LeatherArmorMeta) meta).getColor();
        } else {
            failsafe("ItemMeta is not an instance of LeatherArmorMeta");
            return null;
        }
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setDyeColor(Color color) {
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) meta).setColor(color);
        } else {
            failsafe("ItemMeta is not an instance of LeatherArmorMeta");
        }
        return this;
    }

    @Nullable
    public Color getPotionColor() {
        if (meta instanceof PotionMeta) {
            return ((PotionMeta) meta).getColor();
        } else {
            failsafe("ItemMeta is not an instance of PotionMeta");
            return null;
        }
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setPotionColor(Color color) {
        if (meta instanceof PotionMeta) {
            ((PotionMeta) meta).setColor(color);
        } else {
            failsafe("ItemMeta is not an instance of PotionMeta");
        }
        return this;
    }

    @Nullable
    public PotionData getPotionData() {
        if (meta instanceof PotionMeta) {
            return ((PotionMeta) meta).getBasePotionData();
        } else {
            failsafe("ItemMeta is not an instance of PotionMeta");
            return null;
        }
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setPotionData(PotionData data) {
        if (meta instanceof PotionMeta) {
            ((PotionMeta) meta).setBasePotionData(data);
        } else {
            failsafe("ItemMeta is not an instance of PotionMeta");
        }
        return this;
    }

    @Contract(value = "_, _ -> this", mutates = "this")
    public ItemBuilder addPotionEffect(PotionEffect effect, boolean overwrite) {
        if (meta instanceof PotionMeta) {
            ((PotionMeta) meta).addCustomEffect(effect, overwrite);
        } else {
            failsafe("ItemMeta is not an instance of PotionMeta");
        }
        return this;
    }

    @NotNull
    public List<PotionEffect> getPotionEffects() {
        if (meta instanceof PotionMeta) {
            return ((PotionMeta) meta).getCustomEffects();
        } else {
            failsafe("ItemMeta is not an instance of PotionMeta");
            return new ArrayList<>();
        }
    }

    @NotNull
    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount);
        item.setItemMeta(meta);
        return item;
    }

    @NotNull
    public FailSafeMode getFailSafeMode() {
        return failSafeMode;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public ItemBuilder setFailSafeMode(@NotNull FailSafeMode failSafeMode) {
        this.failSafeMode = failSafeMode;
        return this;
    }


    /**
     * Indicates what do wo when an unsupported operation is attempted, for example adding storage enchantments to an Item that doesn't have EnchantmentStorageMeta, or setting the amount of an item to &lt; 1 or &gt; 64
     */
    public enum FailSafeMode {
        /**
         * Unsupported operations will be silently ignored. The ItemStack will not change.
         */
        IGNORE,
        /**
         * Unsupported operations will print a warning to console. The ItemStack will not change.
         */
        WARN,
        /**
         * Unsupported operations will throw an exception. The ItemStack will not change.
         */
        THROW
    }

}
