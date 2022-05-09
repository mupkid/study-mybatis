package com.example.study.dao;

import com.example.study.entity.Country;
import io.mybatis.mapper.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author haoxian, ou
 * @date 2022/1/11 22:13
 */
@org.apache.ibatis.annotations.Mapper
@Repository
public interface CountryDao extends Mapper<Country, Long> {
}
