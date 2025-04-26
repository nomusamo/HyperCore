package HyperCore.Event.Launch;

import HyperCore.Main.HyperCore;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class Launch {

    public static void launch(LivingEntity skeleton, Location shooterLoc) {
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
                    // 사방으로 화살 발사
                    Vector direction = new Vector(
                            random.nextDouble() * 2 - 1, // X축 (-1 ~ 1)
                            random.nextDouble() * 2 - 1, // Y축 (-1 ~ 1)
                            random.nextDouble() * 2 - 1  // Z축 (-1 ~ 1)
                    ).normalize();

                    Arrow arrow = skeleton.launchProjectile(Arrow.class);
                    arrow.setVelocity(direction.multiply(1.5));
                    arrow.setShooter(skeleton);
                }
                ticks += 1;
            }
        }.runTaskTimer(HyperCore.getInstance(), 0L, 1L); // 1틱마다 실행
    }
}