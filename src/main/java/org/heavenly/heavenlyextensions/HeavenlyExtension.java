package org.heavenly.heavenlyextensions;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;



public final class HeavenlyExtension extends JavaPlugin {

    NotVanillaCrafts crafts = new NotVanillaCrafts(this);

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Загружаем свои рецепты крафтов
        crafts.addRecipes();
        CommandNickstate.register(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // Выгружаем свои загруженные рецепты крафтов
        crafts.removeRecipes();
    }



}
