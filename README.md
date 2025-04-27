HyperCore Remake 3.0
==

## 새롭게 추가 된것
    *리스너 자동 지원
    *config 파일 설정 가능 (추후 지원 예정)


---
기존 마인크래프트 하이퍼 코어는 매우 매우 복잡한 코드 형태를 지니고 있습니다.
https://github.com/seamomoza/New-HyperCore
라는 저의 하이퍼 코어 github에서도 나와있듯이 메인 클래스에서 모든 리스너 클래스를 상속 해줘야하는 불편함이 존재합니다.

---

그래서 저는 새로운 하이퍼 코어를 만들었습니다.

기존 하이퍼 코어의 코드를 봐봅시다.
```java
public class HyperCore extends JavaPlugin {
  @Override
  public void onEnable(){
    Bukkit.getPluginManager().registerEvents(//하이퍼 코어 클래스
);
    //다른 하이퍼 코어의 리스너 상속
  }
}
```
이런식으로 매우 불편한것을 알수있습니다.

---

제가 새롭게 개발한 하이퍼 코어의 예시 코드를 봐봅시다
```java
public class Sample extends Hyper {
  @Config
  //config로 설정해야할 능력치
  //하이퍼 코어의 능력 
}
```
이런식으로 매우 간단한 것을 알 수 있습니다

---
---
