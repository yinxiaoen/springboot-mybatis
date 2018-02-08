package org.spring.springboot.controller;

import org.spring.springboot.domain.Bill;
import org.spring.springboot.domain.BillDetail;
import org.spring.springboot.domain.ConsoleContext;
import org.spring.springboot.service.BillDetailService;
import org.spring.springboot.service.BillService;
import org.spring.springboot.util.ThreadLocalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yinxiaoen on 2018/2/5.
 */
@RestController
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private BillDetailService billDetailService;


    @PostMapping(value = "/api/queryBill")
    public List<Bill> queryBill(@RequestBody Bill bill) {
        return billService.queryBill(bill.getGroupID(),bill.getStartDate(),bill.getEndDate());
    }


    @PostMapping(value = "/api/queryBillDetail")
    public List<BillDetail> queryBillDetail(@RequestBody BillDetail detail) {
        return billDetailService.queryBillDetail(detail.getGroupID(),detail.getStartDate(),detail.getEndDate());
    }


    @PostMapping(value = "/api/updateBill")
    public void updateBillDetail(@RequestBody BillDetail detail) {
        ConsoleContext consoleContext = new ConsoleContext();
        Map<String,Object> map =new HashMap<String, Object>();
        map.put("startDate",detail.getStartDate());
        map.put("endDate",detail.getEndDate());
        map.put("groupID",detail.getGroupID());
        consoleContext.setRequestHeader(map);
        ThreadLocalManager.setConsoleContext(consoleContext);
        Map<String,Object> mapToQuery = new HashMap();
        map.put("groupID",detail.getGroupID());
        billService.updateBill(mapToQuery);
    }


    @PostMapping(value = "/api/deleteBill")
    public void deleteBill(@RequestBody Bill bill) {
        ConsoleContext consoleContext = new ConsoleContext();
        Map<String,Object> map =new HashMap<String, Object>();
        map.put("startDate",bill.getStartDate());
        map.put("endDate",bill.getEndDate());
        map.put("groupID",bill.getGroupID());
        consoleContext.setRequestHeader(map);
        ThreadLocalManager.setConsoleContext(consoleContext);
        Map<String,Object> mapToQuery = new HashMap();
        map.put("groupID",bill.getGroupID());
        billService.deleteBill(mapToQuery);
    }


    @PostMapping(value = "/api/insertBill")
    public void insertBill(@RequestBody Bill bill) {
        ConsoleContext consoleContext = new ConsoleContext();
        Map<String,Object> map =new HashMap<String, Object>();
        map.put("startDate",bill.getStartDate());
        map.put("endDate",bill.getEndDate());
        map.put("groupID",bill.getGroupID());
        consoleContext.setRequestHeader(map);
        ThreadLocalManager.setConsoleContext(consoleContext);
        billService.insertBill(bill);
    }

}
