package org.spring.springboot.service.impl;

import org.spring.springboot.dao.BillDao;
import org.spring.springboot.domain.Bill;
import org.spring.springboot.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yinxiaoen on 2018/2/5.
 */
@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillDao billDao;
    @Override
    public List<Bill> queryBill(Long groupID,Long startDate,Long endDate) {
        return billDao.queryBill(groupID,startDate,endDate);
    }

    @Override
    public void updateBill(Map<String,Object> map) {
         billDao.updateBill(map);
    }

    @Override
    public void deleteBill(Map<String,Object> map) {
        billDao.deleteBill(map);
    }

    @Override
    public void insertBill(Bill bill) {
        billDao.insertBill(bill);
    }
}
