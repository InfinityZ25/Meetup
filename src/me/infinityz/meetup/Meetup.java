package me.infinityz.meetup;

import me.infinityz.meetup.Utils.Scoreboard.ScoreboardNMS;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_7_R4.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

import static me.infinityz.meetup.Utils.Scoreboard.ScoreboardNMS.setField;

public class Meetup extends JavaPlugin implements Listener {

    @Override
    public void onEnable(){

        Bukkit.getPluginManager().registerEvents(this, this);


    }

    @Override
    public void onDisable(){
        
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        final Player p = e.getPlayer();
        createScoreboard(p, "&e&lSerenix &7&o[UHC]", "&8&m------------------", "&eTimer: &f00:08", "&eBorder:&f 750&8(&c3m&8)", " ", "&eRemaining:&f 41", "  ", "&eTeams Alive:&f 26", "   ", "&7www.serenix.us", "&8&r&8&m------------------");
        printHoverable(p, "&cWelcome to Serenix UHCMeetup! \n &6Click here to edit your kit", "/vote random", "Vote randomly");
        p.sendMessage("Hola\n que hace");


    }

    private ScoreboardNMS createScoreboard(Player player, String objectiveName, String... strings){
        ScoreboardNMS scoreboard = new ScoreboardNMS(player, ChatColor.translateAlternateColorCodes('&', objectiveName));
        scoreboard.create();
        int e = strings.length;
        for(String s : strings){
            scoreboard.setLine(e, ChatColor.translateAlternateColorCodes('&', s));
            e--;
        }
        createTeamAndAdd(player, "own");


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
