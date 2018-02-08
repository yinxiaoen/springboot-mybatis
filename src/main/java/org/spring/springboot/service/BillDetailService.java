package org.spring.springboot.service;

import org.spring.springboot.domain.BillDetail;

import java.util.List;

/**
 * Created by yinxiaoen on 2018/2/5.
 */
public interface BillDetailService {
    List<BillDetail> queryBillDetail (Long groupID,Long startDate,Long endDate);
}
