package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item>{

    /**
     * 查询数据库时,要求最近的记录显示在最前方. 倒序排列
     * @param start
     * @param rows
     * @return
     */
    @Select("SELECT * FROM tb_item ORDER BY updated DESC LIMIT #{start},#{rows}")
    List<Item> findItemByPage(@Param("start") int start, @Param("rows") Integer rows);

    /**
     * 关于Mybatis传参问题
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 规则:mybaits中默认是不允许多值传参,
     * 需要用户将多值转化为单值
     * 1.本身数据就是一个参数
     * 2.将数据使用对象进行封装
     * 3.使用集合进行封装  list array
     * 4.无论参数是什么都可以封装为Map集合
     * @param ids
     */
    void deleteItems(Long[] ids);

    void updateStatus(
            @Param("ids")Long[] ids,
            @Param("status")int status);




}
