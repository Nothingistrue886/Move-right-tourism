package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUI_Table;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item/")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 1.根据分页信息实现查询
     *
     * SpringMVC取赋值过程
     * 	http://localhost:8091/item/query?page=1&rows=50
     *
     *  String page = request.getParameter("page");
     * 	String rows = request.getParameter("rows");
     * 	int intPage = Integer.parseInt(page);
     * 	int intRows = Integer.parseInt(rows);
     *
     * 	注意:SpringMVC中方法的参数名称必须与提交的参数一致!!!!
     */
    @RequestMapping("query")
    public EasyUI_Table findItemByPage(Integer page,Integer rows){
        return  itemService.findItemByPage(page, rows);
    }

    /**
     * 实现商品新增
     *
     * 准备AOP 统一异常处理机制.
     */
    @RequestMapping("save")
    public SysResult saveItem(Item item, ItemDesc itemDesc) {
//        try {
//            itemService.saveItem(item);
//            return SysResult.success();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return SysResult.fail();
//        }
        itemService.saveItem(item,itemDesc);
        return SysResult.success();
    }

    /**
     * 实现商品修改
     */
    @RequestMapping("update")
    public SysResult updateItem(Item item, ItemDesc itemDesc) {

        itemService.updateItem(item ,itemDesc);
        return SysResult.success();
        //由于已经编辑统一异常处理.
        //所以不需要考虑异常
    }

    /**
     * 删除商品信息
     * var params = {"ids":1,2,3,4,5};
     * 如果参数是通过,号分割.
     * 则接收时可以使用数组
     */
    @RequestMapping("delete")
    public SysResult deleteItems(Long[] ids) {

        itemService.deleteItems(ids);
        return SysResult.success();
    }

    /**
     * 商品下架
     */
    @RequestMapping("instock")
    public SysResult itemInstock(Long[] ids) {
        int status = 2;	//表示下架
        itemService.updateStatus(ids,status);
        return SysResult.success();
    }

    /**
     * 商品上架
     */
    @RequestMapping("reshelf")
    public SysResult itemReshelf(Long[] ids) {
        int status = 1;	//表示上架
        itemService.updateStatus(ids,status);
        return SysResult.success();
    }

    /**
     * 根据商品id号,查询商品详情信息
     */
    @RequestMapping("query/item/desc/{itemId}")
    public SysResult findItemDescById(@PathVariable Long itemId) {
        //需要将返回值传给页面进行数据展现
        ItemDesc itemDesc = itemService.findItemDescById(itemId);
        return SysResult.success(itemDesc);
    }
}
