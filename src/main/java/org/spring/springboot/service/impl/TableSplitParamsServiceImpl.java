package org.spring.springboot.service.impl;

import org.spring.springboot.dao.TableSplitParamsDao;
import org.spring.springboot.domain.TableSplitParams;
import org.spring.springboot.service.TableSplitParamsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yinxiaoen on 2018/2/2.
 */

public class TableSplitParamsServiceImpl implements TableSplitParamsService{
    @Autowired
    private TableSplitParamsDao tableSplitParamsDao;


    @Override
    public TableSplitParams findSplitWay(Long groupID,String tableName) {
        return tableSplitParamsDao.findSplitWay(groupID,tableName);
    }
}
