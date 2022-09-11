package org.ohx.sutdymybatisplus.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ohx.studymybatisplus.dal.model.dataobject.UserDO;

import java.util.List;
import java.util.Map;

/**
 * User表DAO接口
 *
 * @author mudkip
 * @date 2022/5/9
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
    @MapKey("id")
    Map<String, UserDO> getMapById(@Param("id") Long id);

    IPage<UserDO> pageUserOverAge(@Param("page") IPage<UserDO> page, @Param("age") Integer age);

    List<UserDO> pageUserBelowAge(@Param("page") IPage<UserDO> page, @Param("age") Integer age);
}
