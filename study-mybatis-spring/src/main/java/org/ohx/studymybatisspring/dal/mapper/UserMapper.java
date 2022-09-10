package org.ohx.studymybatisspring.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ohx.studymybatisspring.dal.model.dataobject.UserDO;

import java.util.List;

/**
 * User表DAO接口
 *
 * @author mudkip
 * @date 2022/5/9
 */
@Mapper
public interface UserMapper {
    UserDO getUserById(@Param("id") int id);

    List<UserDO> listUser();
}
