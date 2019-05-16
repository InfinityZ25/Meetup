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

import javax.annotation.Nonnull;
import java.util.Collections;

import static me.infinityz.meetup.Utils.Scoreboard.ScoreboardNMS.setField;

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
        UHCScoreboard sb = createScoreboard(p, "&e&lSerenix &7&o[UHC]",
                "&8&m------------------",
                "&eTimer: &f00:08", "&eBorder:&f 750&8(&c3m&8)",
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

    private UHCScoreboard createScoreboard(Player player, String objectiveName, @Nonnull String... strings){
        if(strings.length<=0)return null;
        //Create an instance of UHC Scoreboard
        UHCScoreboard scoreboard = new UHCScoreboard(player, ChatColor.translateAlternateColorCodes('&', objectiveName));
        scoreboard.create();
        //Loop on each of the string values, substract the number of the line from the max length to obtain line number
        for (int i = 0; i < strings.length; i++) {
            scoreboard.setLine(strings.length - i, ChatColor.translateAlternateColorCodes('&', strings[i]));
        }
        //Call the "Own" team packet to set player's name green
        createTeamAndAdd(player, "own");
        //Return the scoreboard, there should be nulls
        return scoreboard;
    }


    private void createTeamAndAdd(Player player, String teamName){

        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "a", teamName);
        setField(packet, "b", "");
        setField(packet, "c", ChatColor.translateAlternateColorCodes('&', "&a"));// Team Prefix
        setField(packet, "d", ChatColor.translateAlternateColorCodes('&', "&c"));//Team Suffix
        setField(packet, "g", 0);
        setField(packet, "f", 0);

        PacketPlayOutScoreboardTeam packet2 = new PacketPlayOutScoreboardTeam(new ScoreboardTeam(new Scoreboard(), teamName),Collections.singleton(player.getName()), 3);

        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        connection.sendPacket(packet);
        connection.sendPacket(packet2);
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
