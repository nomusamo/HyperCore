package HyperCore.Entity.Skeleton;

import HyperCore.Main.Hyper;
import HyperCore.Main.HyperCore;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class HyperSkeleton extends Hyper {

    @EventHandler
    public void onSkeletonTarget(EntityTargetEvent event) {
        if (event.getEntityType() == EntityType.SKELETON && event.getTarget() instanceof LivingEntity) {
            Skeleton skeleton = (Skeleton) event.getEntity();
            LivingEntity target = (LivingEntity) event.getTarget();
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (skeleton.isDead() || target.isDead() || skeleton.getTarget() != target) {
                        this.cancel();
                        return;
                    }

                    Location eyeLocation = skeleton.getEyeLocation();
                    Location targetLocation = target.getLocation().add(0, target.getHeight() / 2, 0);
                    for (int i = 0; i < 20; i++) {
                        Arrow arrow = skeleton.launchProjectile(Arrow.class);
                        arrow.setVelocity(targetLocation.toVector().subtract(eyeLocation.toVector()).normalize().multiply(3));
                    }
                }
            }.runTaskTimer(HyperCore.getInstance(), 0L, 1); // 1 tick = 0.05 seconds, 20 ticks = 1 second
        }
    }


    @EventHandler
    public void onSkeletonHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (!(event.getDamager() instanceof Projectile projectile)) return;

        if (!(projectile.getShooter() instanceof LivingEntity shooter)) return;

        // 스켈레톤을 shooter 위치로 TP
        Location shooterLoc = shooter.getLocation();
        skeleton.teleport(shooterLoc);

        // 3초간(60틱) 사방으로 화살 난사
        new BukkitRunnable() {
            int ticks = 0;
            final Random random = new Random();

            @Override
            public void run() {
                if (ticks >= 60 || skeleton.isDead()) {
                    cancel();
                    return;
                }
                for (int i = 0; i < 10; i++) {
                    // 랜덤한 위치로 화살 발사
                    Arrow arrow = skeleton.launchProjectile(Arrow.class);
                    arrow.setVelocity(shooterLoc.getDirection().add(new Vector(
                            random.nextDouble() * 2 - 1,
                            random.nextDouble() * 0.5 + 0.2,
                            random.nextDouble() * 2 - 1
                    )).normalize().multiply(1.5));
                    arrow.setShooter(skeleton);
                }
                // 랜덤 방향 화살 발사
                Vector direction = new Vector(
                        random.nextDouble() * 2 - 1,
                        random.nextDouble() * 0.5 + 0.2,
                        random.nextDouble() * 2 - 1
                ).normalize();

                Arrow arrow = skeleton.launchProjectile(Arrow.class);
                arrow.setVelocity(direction.multiply(1.5));
                arrow.setShooter(skeleton);

                ticks += 1;
            }
        }.runTaskTimer(HyperCore.getInstance(), 0L, 1L); // 5틱마다 발사 (0.25초 간격)
    }
}
