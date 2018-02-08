package org.spring.springboot.dao;

import org.apache.ibatis.annotations.Param;
import org.spring.springboot.Annotation.TableParentsSplit;
import org.spring.springboot.domain.BillDetail;

import java.util.List;

/**
 * Created by yinxiaoen on 2018/2/5.
 */
@TableParentsSplit(value="tbl_chain_bill_detail,tbl_chain_bill", strategy="supplychainSplitWay")
public interface BillDetailDao {
    List<BillDetail> queryBillDetail (@Param("groupID") Long groupID,@Param("startDate") Long startDate,@Param("endDate") Long endDate);
}
