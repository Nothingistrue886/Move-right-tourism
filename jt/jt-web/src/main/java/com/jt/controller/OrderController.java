package com.jt.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController {

    @Reference(timeout = 3000, check = false)
    private DubboCartService dubboCartService;

    @Reference(timeout = 3000, check = false)
    private DubboOrderService dubboOrderService;

    @RequestMapping("/create")
    public String create(Model model){
        Long userId = UserThreadLocal.getThreadLocal().getId();
        List<Cart> cartList = dubboCartService.findCartListByUserId(userId);
        model.addAttribute("carts", cartList);
        return "order-cart";
    }

    /**
     * 实现订单入库操作
     * url地址:http://www.jt.com/order/submit
     */
    @RequestMapping("/submit")
    @ResponseBody
    public SysResult saveOrder(Order order){
        Long userId = UserThreadLocal.getThreadLocal().getId();
        order.setUserId(userId);
        //1.业务要求返回orderId号
        String orderId = dubboOrderService.saveOrder(order);
        //2.校验orderId是否有值
        if(StringUtils.isEmpty(orderId)){
            return SysResult.fail();
        }
        return SysResult.success(orderId);
    }

    /**
     * ${order.orderId}
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/success")
    public String findOrderById(String id, Model model){
        Order order = dubboOrderService.findOrderById(id);
        model.addAttribute("order", order);
        return "success";
    }

}
