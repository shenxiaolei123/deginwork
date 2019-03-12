import java.io.*;

public class User implements Serializable,Cloneable {
    private int userid;
    private String username;
    private Order order;

    public User(int userid, String username, Order order) {
        this.userid = userid;
        this.username = username;
        this.order = order;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Object clone(){
        return deepClone();
    }
    private Object deepClone(){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object o = ois.readObject();
            return o;
        }catch (Exception e){e.printStackTrace();}
        return null;
    }
}
