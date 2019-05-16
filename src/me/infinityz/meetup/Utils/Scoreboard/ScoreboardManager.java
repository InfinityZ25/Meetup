package me.infinityz.meetup.Utils.Scoreboard;

import me.infinityz.meetup.Meetup;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.server.v1_7_R4.Scoreboard;
import net.minecraft.server.v1_7_R4.ScoreboardTeam;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.infinityz.meetup.Utils.Scoreboard.ScoreboardBase.ScoreboardNMS_v1_7_R4.setField;

public class ScoreboardManager {
    Map<UUID, UHCScoreboard> scoreboardMap;
    private Meetup instance;

    public ScoreboardManager(Meetup instance){
        this.instance = instance;
        scoreboardMap = new HashMap<>();
    }

    public Map<UUID, UHCScoreboard> getScoreboardMap() {
        return scoreboardMap;
    }
    public UHCScoreboard createScoreboard(Player player, String objectiveName, @Nonnull String... strings){
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
    //Used by the UHC Scoreboard method to set user's name green
    private void createTeamAndAdd(Player player, String teamName){
        //Create a fake team creation packet
        final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "a", teamName);
        setField(packet, "b", "");
        setField(packet, "c", ChatColor.translateAlternateColorCodes('&', "&a"));// Team Prefix
        setField(packet, "d", ChatColor.translateAlternateColorCodes('&', "&c"));//Team Suffix
        setField(packet, "g", 0);
        setField(packet, "f", 0);
        //Create a fake user join team packet
        final PacketPlayOutScoreboardTeam packet2 = new PacketPlayOutScoreboardTeam(new ScoreboardTeam(new Scoreboard(), teamName), Collections.singleton(player.getName()), 3);

        //Obtain the user's connection and send the packet
        final PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
        connection.sendPacket(packet);
        connection.sendPacket(packet2);
    }
    //Self explanatory function
    public void addUserToFakeTeam(Player player, String playerToBeAdded, String teamName){
        final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(new ScoreboardTeam(new Scoreboard(), teamName), Collections.singleton(playerToBeAdded), 3);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }
    //Names are self explanatory
    public void removeUserFromFakeTeam(Player player, String toBeRemoved, String teamName){
        final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(new ScoreboardTeam(new Scoreboard(), teamName), Collections.singleton(toBeRemoved), 4);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }
}
