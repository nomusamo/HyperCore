package HyperCore.Entity.Creeper

import HyperCore.Event.Value.AllEntity
import HyperCore.Main.Hyper
import HyperCore.Main.HyperCore
import org.bukkit.Location
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Creeper
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.cos
import kotlin.math.sin

class HyperCreeper : Hyper() {
    @EventHandler
    fun onCreeperSpawn(event: EntitySpawnEvent) {
        if (event.entity is Creeper && event.entity is LivingEntity) {
            val creeper = event.entity as Creeper
            creeper.getAttribute(Attribute.KNOCKBACK_RESISTANCE)?.setBaseValue(1.0)
            creeper.getAttribute(Attribute.MAX_HEALTH)?.setBaseValue(1000.0)
            creeper.setHealth(1000.0)
        }
    }

    @EventHandler
    fun onCreeperExplode(event: EntityExplodeEvent) {
        if (event.entityType == EntityType.CREEPER) {
            event.isCancelled = true
            val creeper = event.entity as Creeper
            ExplosionLoc = event.location

            object : BukkitRunnable() {
                var ticks: Int = 0 // ticks 변수를 이곳으로 이동

                override fun run() {
                    if (ExplosionLoc != null) {
                        val center = ExplosionLoc
                        val radius = 0.5 * ticks // ticks를 사용하여 반경 계산
                        val circum = 2.0 * Math.PI * radius
                        val pointsCount = (circum / 6.0).toInt()
                        val angle = 360.0 / pointsCount

                        for (i in 0..<pointsCount) {
                            val currentAngle = Math.toRadians(i * angle)
                            val x = -sin(currentAngle)
                            val z = cos(currentAngle)
                            center!!.world.createExplosion(
                                center.x + x * radius,
                                center.y,
                                center.z + z * radius,
                                4f,
                                false,
                                true
                            )
                        }

                        ticks++ // ticks 증가

                        if (ticks >= 100) { // 100 틱 이후 종료
                            ExplosionLoc = null
                            this.cancel()
                        }
                    }
                }
            }.runTaskTimer(HyperCore.getInstance(), 0, 1)
        }
    }

    @EventHandler
    fun onProjectileHit(event: ProjectileHitEvent) {
        val projectile = event.entity
        if (projectile.shooter is LivingEntity) {
            if (event.hitEntity is Creeper) {
                val shooter = projectile.shooter as LivingEntity
                val creeper = event.hitEntity as Creeper
                creeper.teleport(shooter.getLocation())
                creeper.getLocation().getWorld().createExplosion(creeper.getLocation(), 127f, false, false)
                val loc: Location = creeper.getLocation()
                loc.world.strikeLightning(loc)

                for (i in 0..49) {
                    var randomEntity = AllEntity.getRandomLivingEntity()
                    // PLAYER와 CREEPER 엔티티를 제외
                    while (randomEntity == EntityType.PLAYER || randomEntity == EntityType.CREEPER) {
                        randomEntity = AllEntity.getRandomLivingEntity()
                    }
                    creeper.getWorld().spawnEntity(loc, randomEntity)
                }
                creeper.remove()
            }
        }
    }

    companion object {
        private var ExplosionLoc: Location? = null
    }
}