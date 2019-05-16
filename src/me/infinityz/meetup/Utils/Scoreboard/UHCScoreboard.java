package me.infinityz.meetup.Utils.Scoreboard;

import me.infinityz.meetup.Utils.Scoreboard.ScoreboardBase.ScoreboardNMS_v1_7_R4;
import org.bukkit.entity.Player;

public class UHCScoreboard extends ScoreboardNMS_v1_7_R4 {

    //All the lines are equals to 0 to set them as null
    private int timer_line=0;
    private int teams_alive_line=0;
    private int players_alive_line=0;
    private int border_line=0;
    private int kills_line=0;
    private int team_kills_line=0;

    public int getTimer_line() {
        return timer_line;
    }

    public void setTimer_line(int timer_line) {
        this.timer_line = timer_line;
    }

    public int getTeams_alive_line() {
        return teams_alive_line;
    }

    public void setTeams_alive_line(int teams_alive_line) {
        this.teams_alive_line = teams_alive_line;
    }

    public int getPlayers_alive_line() {
        return players_alive_line;
    }

    public void setPlayers_alive_line(int players_alive_line) {
        this.players_alive_line = players_alive_line;
    }

    public int getBorder_line() {
        return border_line;
    }

    public void setBorder_line(int border_line) {
        this.border_line = border_line;
    }

    public int getKills_line() {
        return kills_line;
    }

    public void setKills_line(int kills_line) {
        this.kills_line = kills_line;
    }

    public int getTeam_kills_line() {
        return team_kills_line;
    }

    public void setTeam_kills_line(int team_kills_line) {
        this.team_kills_line = team_kills_line;
    }

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
