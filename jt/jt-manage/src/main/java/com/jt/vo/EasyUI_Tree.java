package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

// VO:是服务器数据与页面交互的对象 一般都需要转化为JSON
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUI_Tree {
    private Long id;	//分类Id号
    private String text;//分类名称
    private String state; //open 节点打开  closed 节点关闭
}
