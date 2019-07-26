package com.jt.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;
import com.jt.service.DubboOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DubboOrderServiceImpl implements DubboOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderShippingMapper orderShippingMapper;

    /**
     * 1.要求返回orderId号
     * 2.要求同时入库
     * 3.三张表分别入库
     */

    @Override
    @Transactional  // 进行事物控制
    public String saveOrder(Order order) {
        String orderId = System.currentTimeMillis() + "" + order.getUserId();
        Date date = new Date();
        //1.入库订单信息
        order.setOrderId(orderId);
        order.setStatus(1);//表示未付款
        order.setCreated(date);
        order.setUpdated(date);
        orderMapper.insert(order);
        System.out.println("订单入库成功!!!");

        //2.订单物流入库
        OrderShipping shipping = order.getOrderShipping();
        shipping.setOrderId(orderId);
        shipping.setCreated(date);
        shipping.setUpdated(date);
        orderShippingMapper.insert(shipping);
        System.out.println("订单物流入库成功!!!!");

        //3.订单商品入库
        List<OrderItem> items = order.getOrderItems();
        for (OrderItem item : items) {
            item.setOrderId(orderId);
            item.setCreated(date);
            item.setUpdated(date);
            orderItemMapper.insert(item);
        }
        System.out.println("订单入库成功!!!");

        return orderId;
    }

    @Override
    public Order findOrderById(String id) {
        Order order = orderMapper.selectById(id);
        OrderShipping shipping = orderShippingMapper.selectById(id);
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        List<OrderItem> items = orderItemMapper.selectList(queryWrapper);
        order.setOrderShipping(shipping)
                .setOrderItems(items);
        return order;
    }
}