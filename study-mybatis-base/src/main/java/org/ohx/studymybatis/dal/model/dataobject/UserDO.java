package org.ohx.studymybatis.dal.model.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User表实体类
 *
 * @author mudkip
 * @date 2022/5/9
 */
@Data
public class UserDO implements Serializable {
    private Long id;

    private String username;

    private String password;

    private Integer age;

    private Integer sex;

    private String mobile;

    private String email;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer deleted;

    private Integer version;
}
