package org.spring.springboot.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yinxiaoen on 2018/2/5.
 */
public class SqlUnion {
    private  static final String BILLSQL ="select billID, groupID, demandType, demandID,orgCode,demandName,billDate,actionTime,createTime from tbl_chain_bill";
    private  static final String BILLDETAILSQL ="select billDetailID, groupID, demandType, demandID, orgCode,demandName,billID,billDate,actionTime,createTime from tbl_chain_bill_detail";
    public  Map<String,String> map =new HashMap();
    public SqlUnion(){
        map.put("tbl_chain_bill",BILLSQL);
        map.put("tbl_chain_bill_detail",BILLDETAILSQL);
    }

    public  Map<String, String> getMap() {
        return map;
    }
}
