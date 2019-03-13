package com.gp;

public class OrderService implements IOrderService {
    private OrderDao orderDao = orderDao = new OrderDao();

    @Override
    public int createOrder(Order order) {
        System.out.println("OrderService 调用 orderDao 创建订单");
        return orderDao.insert(order);
    }
}