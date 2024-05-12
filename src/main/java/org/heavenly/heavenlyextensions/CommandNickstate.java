package org.heavenly.heavenlyextensions;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public class CommandNickstate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        // Проверяем, является ли отправитель игроком
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эту команду могут выполнять только игроки.");
            return true;
        }

        // Получаем игрока, который отправил команду
        Player player = (Player) sender;

        // В этом примере, команда будет выводить сообщение в чат
        player.getServer().getLogger().info(strings.toString());
        return true;

    }

    public static void register(HeavenlyExtension plugin) {
        if (plugin.getCommand("nickstate") == null) return;
        Objects.requireNonNull(plugin.getCommand("nickstate")).setExecutor(new CommandNickstate());
    }
}
