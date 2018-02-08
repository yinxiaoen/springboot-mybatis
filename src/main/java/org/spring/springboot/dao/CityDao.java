package org.spring.springboot.dao;

import org.apache.ibatis.annotations.Param;
import org.spring.springboot.Annotation.TableSplit;
import org.spring.springboot.domain.City;

/**
 * 城市 DAO 接口类
 *
 * Created by 殷效恩 on 07/02/2017.
 */
@TableSplit(value="city", strategy="supplychainSplitWay")
public interface CityDao {

    /**
     * 根据城市名称，查询城市信息
     *
     * @param cityName 城市名
     */
    City findByName(@Param("name") String cityName);
}
