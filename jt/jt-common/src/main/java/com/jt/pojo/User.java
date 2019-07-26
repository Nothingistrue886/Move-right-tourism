package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_user")
public class User extends BasePojo {
    @TableId(type = IdType.AUTO)
    private Long   id;
    private String username;
    private String password;
    private String phone;
    private String email;
}
