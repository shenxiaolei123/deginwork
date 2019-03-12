import java.io.Serializable;
import java.math.BigDecimal;

public class Order implements Serializable {

    private String orderno;
    private String orderid;
    private BigDecimal rmb;

    public Order(String orderno, String orderid, BigDecimal rmb) {
        this.orderno = orderno;
        this.orderid = orderid;
        this.rmb = rmb;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public BigDecimal getRmb() {
        return rmb;
    }

    public void setRmb(BigDecimal rmb) {
        this.rmb = rmb;
    }
}
