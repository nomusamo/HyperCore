package HyperCore.Play.Block;

import HyperCore.Main.Hyper;
import HyperCore.Main.HyperCore;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class HyperBlock extends Hyper {


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        BlockDisplay fakeblock = player.getWorld().spawn(player.getLocation().add(0,2,0), BlockDisplay.class);
        fakeblock.setBlock(block.getBlockData());
        fakeblock.setMetadata("hyper", new FixedMetadataValue(HyperCore.getInstance(), true));


    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // 월드에 있는 모든 BlockDisplay 탐색
        player.getWorld().getEntitiesByClass(BlockDisplay.class).forEach(blockDisplay -> {
            if (blockDisplay.getBoundingBox() != null && blockDisplay.getBoundingBox().overlaps(player.getBoundingBox())) {
                // 플레이어가 BlockDisplay에 닿았을 때 처리
                player.damage(1.0); // 플레이어에게 1 데미지 부여
            }
        });
    }
}