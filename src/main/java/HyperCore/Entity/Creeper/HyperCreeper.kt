package HyperCore.Entity.Creeper;

import HyperCore.Main.Hyper;
import HyperCore.Main.HyperCore;
import HyperCore.Event.Value.AllEntity;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static java.lang.Math.*;

public class HyperCreeper extends Hyper {

    private static Location ExplosionLoc = null;

    @EventHandler
    public void onCreeperSpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Creeper creeper && event.getEntity() instanceof LivingEntity) {
            creeper.getAttribute(Attribute.KNOCKBACK_RESISTANCE).setBaseValue(1);
            creeper.getAttribute(Attribute.MAX_HEALTH).setBaseValue(1000);
            creeper.setHealth(1000);
        }
    }

    @EventHandler
    public void onCreeperExplode(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.CREEPER) {
            event.setCancelled(true);
            Creeper creeper = (Creeper) event.getEntity();
            ExplosionLoc = event.getLocation();

            new BukkitRunnable() {
                int ticks = 0; // ticks 변수를 이곳으로 이동

                @Override
                public void run() {
                    if (ExplosionLoc != null) {
                        Location center = ExplosionLoc;
                        double radius = 0.5 * ticks; // ticks를 사용하여 반경 계산
                        double circum = 2.0 * Math.PI * radius;
                        int pointsCount = (int) (circum / 6.0);
                        double angle = 360.0 / pointsCount;

                        for (int i = 0; i < pointsCount; i++) {
                            double currentAngle = toRadians(i * angle);
                            double x = -sin(currentAngle);
                            double z = cos(currentAngle);
                            center.getWorld().createExplosion(center.getX() + x * radius, center.getY(), center.getZ() + z * radius, 4, false, true);
                        }

                        ticks++; // ticks 증가

                        if (ticks >= 100) { // 100 틱 이후 종료
                            ExplosionLoc = null;
                            this.cancel();
                        }
                    }
                }
            }.runTaskTimer(HyperCore.getInstance(), 0, 1);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if (projectile.getShooter() instanceof LivingEntity shooter) {
            if (event.getHitEntity() instanceof Creeper creeper) {
                creeper.teleport(shooter.getLocation());
                creeper.getLocation().getWorld().createExplosion(creeper.getLocation(), 127, false, false);
                Location loc = creeper.getLocation();
                loc.getWorld().strikeLightning(loc);

                for (int i = 0; i < 50; i++) {
                    EntityType randomEntity = AllEntity.getRandomLivingEntity();
                    // PLAYER와 CREEPER 엔티티를 제외
                    while (randomEntity == EntityType.PLAYER || randomEntity == EntityType.CREEPER) {
                        randomEntity = AllEntity.getRandomLivingEntity();
                    }
                    creeper.getWorld().spawnEntity(loc, randomEntity);
                }
                creeper.remove();
            }
        }
    }
}
