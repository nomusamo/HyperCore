package HyperCore.Main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Set;

public class ClassScanner {

    public static void scanAndRegister(JavaPlugin plugin) {
        // 플러그인의 패키지 이름을 정확히 지정
        Reflections reflections = new Reflections("HyperCore", new SubTypesScanner(false));

        // Listener 타입의 클래스만 필터링하여 등록
        Set<Class<? extends org.bukkit.event.Listener>> listeners = reflections.getSubTypesOf(org.bukkit.event.Listener.class);
        for (Class<? extends org.bukkit.event.Listener> clazz : listeners) {
            try {
                // Listener 인스턴스 생성
                org.bukkit.event.Listener listenerInstance = clazz.getDeclaredConstructor().newInstance();

                // Listener 등록
                Bukkit.getPluginManager().registerEvents(listenerInstance, plugin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
