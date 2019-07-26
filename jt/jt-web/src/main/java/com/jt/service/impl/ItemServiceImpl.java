package com.jt.service.impl;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private HttpClientService httpClientService;

    @Override
    public Item findItemById(Long itemId) {
        String url = "http://manage.jt.com/web/item/findItemById/"+itemId;
        String resultJson = httpClientService.doGet(url);
        Item item = ObjectMapperUtil.toObject(resultJson, Item.class);
        return item;
    }

    @Override
    public ItemDesc findItemDescById(Long itemId) {
        String url = "http://manage.jt.com/web/item/findItemDescById/"+itemId;
        String resultJson = httpClientService.doGet(url);
        ItemDesc itemDesc = ObjectMapperUtil.toObject(resultJson, ItemDesc.class);
        return itemDesc;
    }
}
