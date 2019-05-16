package me.infinityz.meetup.Player;

import java.util.UUID;

public class UHCPlayer {
    private int kills;
    private int wins;
    private int deaths;
    private int elo;
    private long played_time;
    private long connection_time;
    private UUID uuid;
    private boolean spectating = false;

    public UHCPlayer(UUID uuid) {
        this.uuid = uuid;
    }
}
