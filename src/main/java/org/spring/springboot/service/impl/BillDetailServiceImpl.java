package org.spring.springboot.service.impl;

import org.spring.springboot.dao.BillDetailDao;
import org.spring.springboot.domain.BillDetail;
import org.spring.springboot.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yinxiaoen on 2018/2/5.
 */
@Service
public class BillDetailServiceImpl implements BillDetailService {
    @Autowired
    private BillDetailDao billDetailDao;

    @Override
    public List<BillDetail> queryBillDetail(Long groupID,Long startDate,Long endDate) {
        return billDetailDao.queryBillDetail(groupID,startDate,endDate);
    }
}
