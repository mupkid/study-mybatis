package org.ohx.sutdymybatisplus.dal.mapper;

import org.ohx.sutdymybatisplus.dal.model.dataobject.Country;
import io.mybatis.mapper.Mapper;

/**
 * @author haoxian, ou
 * @date 2022/1/11 22:13
 */
@org.apache.ibatis.annotations.Mapper
public interface CountryDao extends Mapper<Country, Long> {
}
