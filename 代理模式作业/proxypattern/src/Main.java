import com.gp.*;
import com.gp.w.CglibRequestProxy;
import com.gp.w.IRequest;
import com.gp.w.TcpRequest;
import com.gp.w.TcpRequestStaticProxy;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        TcpRequestStaticProxy proxy = new TcpRequestStaticProxy(new TcpRequest());
        proxy.read();


        Order order = new Order();
// Date today = new Date();
// order.setCreateTime(today.getTime());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse("2018/02/01");
            order.setCreateTime(date.getTime());
            IOrderService orderService = new OrderServiceStaticProxy(new OrderService());
            orderService.createOrder(order);
        }catch (Exception e) {e.printStackTrace();}

        JDKRequestProxy jdkRequestProxy = new JDKRequestProxy();
        IRequest request = (IRequest) jdkRequestProxy.proxy(new TcpRequest());
        request.read();

        try {
            Person obj = (Person)new JDKMeipo().getInstance(new Customer());
            obj.findLove();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("======================================");
        try {
            OrderService orderService = (OrderService) new CglibMeipo().getInstance(OrderService.class);
            orderService.createOrder(new Order());

           TcpRequest request1 = (TcpRequest) new CglibRequestProxy().getInstance(TcpRequest.class);
           request1.read();
        }catch (Exception e){e.printStackTrace();}

//        try {
//            //通过反编译工具可以查看源代码
//            byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
//            FileOutputStream os = new FileOutputStream("E://$Proxy0.class");
//            os.write(bytes);
//            os.close();
//        }catch (Exception e){e.printStackTrace();}
    }
}
