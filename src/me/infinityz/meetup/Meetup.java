package me.infinityz.meetup;

import me.infinityz.meetup.Utils.Scoreboard.ScoreboardManager;
import me.infinityz.meetup.Utils.Scoreboard.UHCScoreboard;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_7_R4.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Meetup extends JavaPlugin implements Listener {

    private static Meetup instance;
    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable(){
        instance = this;
        scoreboardManager = new ScoreboardManager(this);
        Bukkit.getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable(){
        
    }

    public static Meetup getInstance() {
        return instance;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        final Player p = e.getPlayer();
        UHCScoreboard sb = scoreboardManager.createScoreboard(p, "&e&lSerenix &7&o[UHC]",
                "&8&m------------------",
                "&eTimer: &f00:08",
                "&eBorder:&f 750&8(&c3m&8)",
                "  ",
                "&eRemaining:&f 41",
                "   ",
                "&eTeams Alive:&f 26",
                "    ",
                "&7www.serenix.us",
                "&8&r&8&m------------------");
        assert sb != null;
        sb.setTimer_line(8);
        sb.setPlayers_alive_line(6);
    }



    private void printHoverable(Player player, String ChatMsg, String ClickableMsg, String HoverMsg) {
        IChatBaseComponent base = new ChatMessage(ChatColor.translateAlternateColorCodes('&', ChatMsg));
        base.setChatModifier(new ChatModifier());
        base.setChatModifier(new ChatModifier());
        base.getChatModifier().setChatClickable(new ChatClickable(EnumClickAction.RUN_COMMAND, ChatColor.translateAlternateColorCodes('&', ClickableMsg)));
        base.getChatModifier().a(new ChatHoverable(EnumHoverAction.SHOW_TEXT, new ChatMessage(ChatColor.translateAlternateColorCodes('&', HoverMsg))));
        ((CraftPlayer)player).getHandle().sendMessage(base);
    }
}
