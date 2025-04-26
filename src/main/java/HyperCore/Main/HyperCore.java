package HyperCore.Main;

import org.bukkit.plugin.java.JavaPlugin;

public final class HyperCore extends JavaPlugin {

    private static HyperCore instance;

    @Override
    public void onEnable() {
        ClassScanner.scanAndRegister(this);
        instance = this;
        getLogger().info("HyperCore is Enabled");
    }

    public static HyperCore getInstance() {
        return instance;
    }
    @Override
    public void onDisable() {
        getLogger().info("HyperCore is Disabled");
    }
}
