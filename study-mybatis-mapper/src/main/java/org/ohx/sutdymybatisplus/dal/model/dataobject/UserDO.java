package org.ohx.sutdymybatisplus.dal.model.dataobject;

import lombok.Data;
import org.ohx.studymybatisplus.dal.common.enums.SexEnum;

import java.time.LocalDateTime;

/**
 * User表实体类
 *
 * @author mudkip
 * @date 2022/5/9
 */
@Data
@TableName("user")
public class UserDO {

    @TableId
    private Long id;

    private String username;

    private String password;

    private Integer age;

    private SexEnum sex;

    private String mobile;

    private String email;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField("is_deleted")
    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;
}
