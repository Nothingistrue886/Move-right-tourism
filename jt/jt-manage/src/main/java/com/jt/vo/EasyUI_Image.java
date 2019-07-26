package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUI_Image {
    private Integer error = 0;  //表示用户上传文件是是否有错
    private String url;         //图片的虚拟路径
    private Integer width;      //宽度
    private Integer height;     //高度
}
/**
 * 多系统之间对象直接传递时必须序列化
 * manage.jt.com  后台系统  EasyUI_Image~~序列化~字节数组
 * www.jt.com 	  前台系统  EasyUI_Image~~反序列~~字节数组
 *
 * manage.jt.com  JSON~~String
 * www.jt.com 	  JSON~~String
 */

