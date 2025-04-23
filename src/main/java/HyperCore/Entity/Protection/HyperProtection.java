package HyperCore.Entity.Protection;

import HyperCore.Main.Hyper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityCombustEvent;

public class HyperProtection extends Hyper {

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if ((event.getEntityType() == EntityType.ZOMBIE)) {
            event.setCancelled(true);
        }
        if ((event.getEntityType() == EntityType.SKELETON)) {
            event.setCancelled(true);

        }
    }
}
