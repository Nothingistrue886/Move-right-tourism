package com.jt.service;

import com.jt.vo.EasyUI_Tree;

import java.util.List;

public interface ItemCatService {
    String findItemCatNameById(Long itemCatId);

    List<EasyUI_Tree> findItemCatByParentId(Long parentId);

    //List<EasyUI_Tree> findItemCatByCache(Long parentId);
}
