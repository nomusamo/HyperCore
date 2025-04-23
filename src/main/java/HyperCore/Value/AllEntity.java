package HyperCore.Value;

import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AllEntity {
    // 모든 라이빙 엔티티를 저장하는 정적 리스트
    public static final List<EntityType> LIVING_ENTITIES = new ArrayList<>();

    // 하나의 라이빙 엔티티를 저장하는 정적 변수
    public static EntityType ONE_LIVING_ENTITY;

    // 초기화 메서드
    static {
        for (EntityType entityType : EntityType.values()) {
            if (entityType.isAlive()) { // 라이빙 엔티티인지 확인
                LIVING_ENTITIES.add(entityType);
            }
        }
        // 무작위로 하나의 라이빙 엔티티 선택
        ONE_LIVING_ENTITY = getRandomLivingEntity();
    }

    // 무작위로 하나의 라이빙 엔티티를 반환하는 메서드
    public static EntityType getRandomLivingEntity() {
        if (LIVING_ENTITIES.isEmpty()) {
            return null; // 리스트가 비어있으면 null 반환
        }
        Random random = new Random();
        return LIVING_ENTITIES.get(random.nextInt(LIVING_ENTITIES.size()));
    }
}