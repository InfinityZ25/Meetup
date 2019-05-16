package me.infinityz.meetup.Player;

import me.infinityz.meetup.Meetup;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    private Meetup instance;
    private Map<UUID, UHCPlayer> uuiduhcPlayerMap;

    public PlayerManager(Meetup instance){
        this.instance = instance;
        this.uuiduhcPlayerMap = new HashMap<>();

    }
}
