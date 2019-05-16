package me.infinityz.meetup.Utils;

import net.minecraft.server.v1_7_R4.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Bossbar{
    public class SharedBossbar extends BukkitRunnable{
        private String title;
        private Map<UUID, EntityWither> withers;

        public SharedBossbar(String title){
            this.title = title;
            this.withers = new HashMap<>();
        }

        @Override
        public void run() {
            if(withers.isEmpty()){
                cancel();
                return;
            }
            withers.forEach((uuid, entityWither) -> {
                final Player player = Bukkit.getPlayer(uuid);
                if(player == null){
                    withers.remove(uuid);
                }else{

                }
            });
            if(withers.isEmpty()){
                cancel();
            }

        }

        public void addPlayer(Player player){
            final CraftWorld craftWorld= (CraftWorld) player.getWorld();
            final WorldServer worldServer = craftWorld.getHandle();
            final EntityWither entityWither = new EntityWither(worldServer);
            final Location location = getWitherLocation(player.getLocation());

            entityWither.setCustomName(title);
            entityWither.setInvisible(true);
            entityWither.setLocation(location.getX(),
                    location.getY(),
                    location.getZ(),
                    0f,
                    0f);

            final PacketPlayOutSpawnEntityLiving packetPlayOutSpawnEntity = new PacketPlayOutSpawnEntityLiving(entityWither);
            getConnection(player).sendPacket(packetPlayOutSpawnEntity);

            withers.put(player.getUniqueId(), entityWither);
        }

        public void removePlayer(Player player){
            if(withers.containsKey(player.getUniqueId()))return;
            final PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(withers.get(player.getUniqueId()).getId());
            withers.remove(player.getUniqueId());
            getConnection(player).sendPacket(packetPlayOutEntityDestroy);
        }

        public boolean hasPlayer(Player player){
            return withers.containsKey(player.getUniqueId());
        }
        public void setProgress(float progress){
            withers.forEach((uuid, entityWither) -> {
                final Player player = Bukkit.getPlayer(uuid);
                if(player == null || !player.isOnline()){
                    withers.remove(uuid);
                }else{
                    entityWither.setHealth(progress * entityWither.getMaxHealth());
                    PacketPlayOutEntityMetadata packetPlayOutEntityMetadata = new PacketPlayOutEntityMetadata(entityWither.getId(),
                            entityWither.getDataWatcher(),
                            true);
                    getConnection(player).sendPacket(packetPlayOutEntityMetadata);
                }
            });

        }
        public void updateTitle(String title){
            this.title = title;
            withers.forEach((uuid, entityWither) -> {
                final Player player = Bukkit.getPlayer(uuid);
                if(player == null || !player.isOnline()){
                    withers.remove(uuid);
                }else{
                    entityWither.setCustomName(title);
                    PacketPlayOutEntityMetadata packetPlayOutEntityMetadata = new PacketPlayOutEntityMetadata(entityWither.getId(),
                            entityWither.getDataWatcher(),
                            true);
                    getConnection(player).sendPacket(packetPlayOutEntityMetadata);
                }
            });

        }
    }

    private Location getWitherLocation(Location location) {
        return location.add(location.getDirection().normalize().multiply(50).add(new Vector(0,
                15,
                0)));
    }

    private PlayerConnection getConnection(Player player){
        return ((CraftPlayer)player).getHandle().playerConnection;
    }
}
