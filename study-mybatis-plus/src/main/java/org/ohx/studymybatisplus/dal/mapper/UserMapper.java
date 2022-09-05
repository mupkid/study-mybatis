package org.ohx.studymybatisplus.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ohx.studymybatisplus.dal.model.dataobject.User;

import java.util.List;
import java.util.Map;

/**
 * User表DAO接口
 *
 * @author mudkip
 * @date 2022/5/9
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    Map<String, User> getMapById(Long id);

    IPage<User> pageUserOverAge(@Param("page") IPage<User> page, @Param("age") Integer age);

    List<User> pageUserBelowAge(@Param("page") IPage<User> page, @Param("age") Integer age);
}
