package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart/")
public class CartController {

    @Reference(timeout = 3000,check = true)
    private DubboCartService dubboCartService;

    /**
     * 当用户点击购物车按钮时.应该展现用户的购物记录
     * 业务实现:
     * 	查询用户购物行为  userId 暂时写死
     * @return
     */
    @RequestMapping("show")
    public String show(Model model){
        //Long userId = 7L;
        Long userId = UserThreadLocal.getThreadLocal().getId();
        List<Cart> cartList = dubboCartService.findCartListByUserId(userId);
        model.addAttribute("cartList", cartList);
        return "cart";//跳转购物车页面
    }

    /**
     * 如果rest风格接收参数时与对象的属性名称一致
     * 则可以使用对象接收
     * @param
     * @return
     */
    @RequestMapping("delete/{itemId}")
    public String delete(Cart cart){
        Long userId = UserThreadLocal.getThreadLocal().getId();
        dubboCartService.deleteCart(cart);
        return "redirect:/cart/show.html";//应该采用重定向方式获取数据
    }

    /**
     * 该方法利用页面的表单提交获取参数.
     * 之后应该跳转购物车展现页面
     * @return
     */
    @RequestMapping("add/{itemId}")
    public String saveCart(Cart cart){
        Long userId = UserThreadLocal.getThreadLocal().getId();
        cart.setUserId(userId);
        dubboCartService.saveCart(cart);
        //只能重定向
        return "redirect:/cart/show.html";
    }

    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public SysResult updateCartNum(Cart cart, HttpServletRequest request){
        //User user = (User) request.getAttribute("JT_USER");
        //cart.setUserId(user.getId());
        Long userId = UserThreadLocal.getThreadLocal().getId();
        dubboCartService.updateCartNum(cart);
        return SysResult.success();
    }
}
