package org.spring.springboot.service;

import org.spring.springboot.domain.Bill;

import java.util.List;
import java.util.Map;

/**
 * Created by yinxiaoen on 2018/2/5.
 */

public interface BillService {
    List<Bill> queryBill (Long groupID,Long startDate,Long endDate);

    void updateBill(Map<String,Object> map);


    void deleteBill(Map<String,Object> map);


    void insertBill(Bill bill);
}
