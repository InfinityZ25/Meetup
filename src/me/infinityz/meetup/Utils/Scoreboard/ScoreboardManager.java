package me.infinityz.meetup.Utils.Scoreboard;

import me.infinityz.meetup.Meetup;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
}
