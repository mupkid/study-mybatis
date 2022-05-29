package org.ohx.studymybatisplus.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.ohx.studymybatisplus.dal.model.dataobject.User;

import java.util.Map;

/**
 * User表DAO接口
 *
 * @author mudkip
 * @date 2022/5/9
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    Map<String, Object> selectMapById(Long id);
}
