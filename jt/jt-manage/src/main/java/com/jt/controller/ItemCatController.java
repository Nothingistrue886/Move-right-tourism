package com.jt.controller;

import com.jt.anno.Cache_Find;
import com.jt.enu.KEY_ENUM;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUI_Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item/cat/")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    /**
     *  业务说明:
     * 	  根据页面商品分类id号查询商品分类名称
     * 	  利用ajax进行异步提交
     * 	  url:"/item/cat/queryItemName"
     * 	  参数:  itemCatId:val
     * 	  返回值类型:   返回具体分类名称
     * @return
     */

    @RequestMapping("queryItemName")
    public String findItemCatNameById(Long itemCatId){
        return itemCatService.findItemCatNameById(itemCatId);
    }

    /**
     * 实现商品分类树形结构查询
     */
    @RequestMapping("list")
    @Cache_Find(keyType = KEY_ENUM.AUTO)
    public List<EasyUI_Tree> findItemCatByParentId(@RequestParam(name="id",defaultValue="0")Long parentId){
        //return itemCatService.findItemCatByCache(parentId);
        return itemCatService.findItemCatByParentId(parentId);
    }
}
