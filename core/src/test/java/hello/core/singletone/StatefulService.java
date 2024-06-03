package hello.core.singletone;

public class StatefulService {
    private int price; // 싱글톤에서 전역 변수를 공유하게 되면 상태 관리가 되지 않기 떄문에 이슈가 발생할 확률이 높다.

    // 싱글톤으로 설계할 때는 가급적 전역 변수는 지양하고, 로컬 변수나 파라미터 등으로 처리하는 게 안전하다.
    public void order(String name, int price) {
        System.out.println("name = " + name + ", price = " + price);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
