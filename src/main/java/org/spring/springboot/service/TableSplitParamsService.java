package org.spring.springboot.service;


import org.spring.springboot.domain.TableSplitParams;

/**
 * Created by yinxiaoen on 2018/2/2.
 */
public interface TableSplitParamsService {

    TableSplitParams findSplitWay( Long groupID,String tableName);
}
