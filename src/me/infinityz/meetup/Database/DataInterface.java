package me.infinityz.meetup.Database;

import java.util.UUID;

public interface DataInterface {
    void loadPlayer(UUID uuid);

    void savePlayer(UUID uuid);

    void getPlayer(UUID uuid);

    void cachePlayer(UUID uuid);

    boolean isCached(UUID uuid);
}
