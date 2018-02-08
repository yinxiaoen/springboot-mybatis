package org.spring.springboot.dao;

import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.TableSplitParams;

/**
 * Created by yinxiaoen on 2018/2/2.
 */
public interface TableSplitParamsDao {

    /**
     * 集团ID
     *
     * @param groupID 集团ID
     */
    TableSplitParams findSplitWay(@Param("groupID") Long groupID,@Param("tableName") String  tableName);
}
