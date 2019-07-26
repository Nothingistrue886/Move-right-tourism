package com.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUI_Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private JedisCluster jedis;

//    @Autowired
//    private Jedis jedis;    // 哨兵redis

//    @Autowired
//    private ShardedJedis jedis; // 多态redis
//    @Autowired
//    private Jedis jedis;    // 单台redis

    @Override
    public String findItemCatNameById(Long itemCatId) {
        ItemCat itemCat = itemCatMapper.selectById(itemCatId);
        return itemCat.getName();
    }

    /**
     * List<EasyUI_Tree>  返回的是VO对象集合
     * EasyUI_Tree: id/text/state
     * List<ItemCat> 	  返回ItemCat集合对象
     * ItemCat: id/name/判断是否为父级
     * @param parentId
     * @return
     */
    // 从数据库查数据
    @Override
    public List<EasyUI_Tree> findItemCatByParentId(Long parentId) {
        List<EasyUI_Tree> treeList = new ArrayList<>();
        //1.获取数据库数据
        List<ItemCat> itemCatList = findItemCatList(parentId);
        for (ItemCat itemCat : itemCatList) {
            Long id = itemCat.getId();
            String text = itemCat.getName();
            //一级二级菜单 closed 三级菜单 open
            String state = itemCat.getIsParent()?"closed":"open";
            EasyUI_Tree tree = new EasyUI_Tree(id,text,state);
            treeList.add(tree);
        }
        return treeList;
    }
    // 从缓存查数据
//    @Override
//    public List<EasyUI_Tree> findItemCatByCache(Long parentId) {
//        List<EasyUI_Tree> treeList = new ArrayList<>();
//        String key = "ITEM_CAT_" + parentId;
//        String result = jedis.get(key);
//        if(StringUtils.isEmpty(result)){
//            //表示缓存中没有数据，查询数据库
//            treeList = findItemCatByParentId(parentId);
//            //将对象转换为json串
//            String json = ObjectMapperUtil.toJson(treeList);
//            //将数据保存到缓存中
//            jedis.set(key, json);
//            System.out.println("查询数据库！！！");
//        }else {
//            //表示缓存中有数据,将json串转换为对象
//            treeList = ObjectMapperUtil.toObject(result, treeList.getClass());
//            System.out.println("查询缓存！！！");
//        }
//        return treeList;
//    }

    public List<ItemCat> findItemCatList(Long parentId) {
        QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        return itemCatMapper.selectList(queryWrapper);
    }
}
