package org.spring.springboot.dao;

import org.apache.ibatis.annotations.Param;
import org.spring.springboot.Annotation.TableSplit;
import org.spring.springboot.Annotation.TableSplitUpdate;
import org.spring.springboot.domain.Bill;

import java.util.List;
import java.util.Map;

/**
 * Created by yinxiaoen on 2018/2/5.
 */

public interface BillDao {
     @TableSplit(value="tbl_chain_bill", strategy="supplychainSplitWay")
     List<Bill> queryBill (@Param("groupID") Long groupID,@Param("startDate") Long startDate,@Param("endDate") Long endDate);
     @TableSplitUpdate(value="tbl_chain_bill", strategy="supplyChainUpdate")
     void updateBill(Map<String,Object> map);
     @TableSplitUpdate(value="tbl_chain_bill", strategy="supplyChainUpdate")
     void deleteBill(Map<String,Object> map);
     @TableSplitUpdate(value="tbl_chain_bill", strategy="supplyChainUpdate")
     void insertBill(Bill bill);

}
