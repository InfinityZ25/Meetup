package me.infinityz.meetup.Utils.Scoreboard;

import org.bukkit.entity.Player;

public class UHCScoreboard extends ScoreboardNMS {
    /**
     * Create a scoreboard sign for a given player and using a specifying objective name
     *
     * @param player        the player viewing the scoreboard sign
     * @param objectiveName the name of the scoreboard sign (displayed at the top of the scoreboard)
     */
    public UHCScoreboard(Player player, String objectiveName) {
        super(player, objectiveName);
    }

    //Add all the methods that allow the server to tell what scoreboard lines are doing and how to update them.
}
