package me.infinityz.meetup.Database.Types;

import me.infinityz.meetup.Database.DataInterface;

import java.util.UUID;

public class MongoDB implements DataInterface {
    @Override
    public void loadPlayer(UUID uuid) {
    }

    @Override
    public void savePlayer(UUID uuid) {

    }

    @Override
    public void getPlayer(UUID uuid) {

    }

    @Override
    public void cachePlayer(UUID uuid) {

    }

    @Override
    public boolean isCached(UUID uuid) {
        return false;
    }
}
