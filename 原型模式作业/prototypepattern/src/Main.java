import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Order o = new Order("1","2",new BigDecimal(10));
        User u = new User(1,"jacky",o);
        User u0 = (User) u.clone();
        u0.setOrder(new Order("2","3",new BigDecimal(11)));
        System.out.println(u.getOrder().getOrderid()+" "+u0.getOrder().getOrderid());
    }
}
