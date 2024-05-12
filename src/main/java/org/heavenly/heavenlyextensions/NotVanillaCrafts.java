package org.heavenly.heavenlyextensions;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class NotVanillaCrafts {
    private final HeavenlyExtension plugin;
    private final Server server;
    public NamespacedKey invisframercipekey;
    public NamespacedKey glowframercipekey;

    public NotVanillaCrafts(HeavenlyExtension plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
        this.invisframercipekey = new NamespacedKey(plugin, "invisible_frame_recipe");
        this.glowframercipekey = new NamespacedKey(plugin, "invisible_glowframe_recipe");
    }


    public void addRecipes() {
        addRecipeFrame();
        addRecipeGlowFrame();
    }

    public void removeRecipes() {
        removeRecipeFrame();
        removeRecipeGlowFrame();
    }

    public void addRecipeGlowFrame() {
        ItemStack resultGlowItem = new ItemStack(Material.GLOW_ITEM_FRAME, 8);
        resultGlowItem = addInvisTag(resultGlowItem);

        ShapedRecipe glowframerecipe = new ShapedRecipe(glowframercipekey, resultGlowItem);

        // Задаем форму рецепта
        glowframerecipe.shape("RRR", "RPR", "RRR");
        glowframerecipe.setIngredient('R', Material.GLOW_ITEM_FRAME); // Рамки
        glowframerecipe.setIngredient('P', getInvisPotion()); // Зелье невидимости
        glowframerecipe.setCategory(CraftingBookCategory.MISC);
        
        // Регистрируем рецепт в сервере
        server.addRecipe(glowframerecipe);
    }

    public void addRecipeFrame() {
        // Создаем экземпляр крафта
        ItemStack resultItem = new ItemStack(Material.ITEM_FRAME, 8);
        resultItem = addInvisTag(resultItem); // Добавляем NBT тег

        // Создаем новый рецепт
        ShapedRecipe framerecipe = new ShapedRecipe(invisframercipekey, resultItem);

        // Задаем форму рецепта
        framerecipe.shape("RRR", "RPR", "RRR");
        framerecipe.setIngredient('R', Material.ITEM_FRAME); // Рамки
        framerecipe.setIngredient('P', getInvisPotion()); // Зелье невидимости

        // Регистрируем рецепт в сервере
        server.addRecipe(framerecipe);
    }


    public void removeRecipeFrame() {
        server.removeRecipe(invisframercipekey);
    }

    public void removeRecipeGlowFrame() {
        server.removeRecipe(glowframercipekey);
    }



    private ItemStack addInvisTag(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName("§r§bНевидимая рамка");
        itemMeta.addEnchant(Enchantment.DURABILITY, (byte) 1, true);
        itemStack.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(itemStack);

        if (nbtItem.getCompound("EntityTag") != null) {
            NBTCompound entityTagComp = nbtItem.getCompound("EntityTag");
            assert entityTagComp != null;
            entityTagComp.setByte("Invisible", (byte) 1);
            nbtItem.mergeCompound(entityTagComp);
        } else {
            nbtItem.addCompound("EntityTag");
            NBTCompound entityTagComp = nbtItem.getCompound("EntityTag");
            assert entityTagComp != null;
            entityTagComp.setByte("Invisible", (byte) 1);
            nbtItem.mergeCompound(entityTagComp);
        }
        nbtItem.setInteger("HideFlags", 1);
        return nbtItem.getItem();
    }

    private RecipeChoice getInvisPotion() {
        ItemStack potionStack = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) potionStack.getItemMeta();

        assert potionMeta != null;
        PotionType potionEffect = PotionType.INVISIBILITY;

        potionMeta.setBasePotionData(new PotionData(
                potionEffect,
                potionEffect.isExtendable(),
                potionEffect.isUpgradeable()
        ));


        potionStack.setItemMeta(potionMeta);

        return new RecipeChoice.ExactChoice(potionStack);
    }
}
