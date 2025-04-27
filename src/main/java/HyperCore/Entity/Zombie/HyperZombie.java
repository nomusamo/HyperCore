package HyperCore.Entity.Zombie;

import HyperCore.Main.Hyper;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

public class HyperZombie extends Hyper {

    @EventHandler
    public void onZombieSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            Zombie zombie = (Zombie) event.getEntity();
            zombie.getAttribute(Attribute.MAX_HEALTH).setBaseValue(100);
            zombie.setHealth(100);
            zombie.getAttribute(Attribute.SCALE).setBaseValue(0.000001);
        }
    }
}
