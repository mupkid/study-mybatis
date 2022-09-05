package org.ohx.studymybatisplus.dal.model.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * User表实体类
 *
 * @author mudkip
 * @date 2022/5/9
 */
@Data
public class User {

    @TableId
    private Long id;

    private String username;

    private String password;

    private Integer age;

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
