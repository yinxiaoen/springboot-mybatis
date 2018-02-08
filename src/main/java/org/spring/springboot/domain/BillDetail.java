package org.spring.springboot.domain;

/**
 * Created by yinxiaoen  on 2018/2/5.
 */
public class BillDetail {
    private Long billDetailID ;
    private Long groupID;
    private Integer demandType;
    private Long demandID;//billDetailID
    private String orgCode;//集团ID
    private String demandName;//类型:0-店铺，1-配送中心
    private Long billID;
    private Long actionTime;//配送中心的ID
    private Long createTime;//配送中心的名称
    private Long startDate;//配送中心的ID
    private Long endDate;//配送中心的名称


    public Long getBillDetailID() {
        return billDetailID;
    }

    public void setBillDetailID(Long billDetailID) {
        this.billDetailID = billDetailID;
    }

    public Long getGroupID() {
        return groupID;
    }

    public void setGroupID(Long groupID) {
        this.groupID = groupID;
    }

    public Integer getDemandType() {
        return demandType;
    }

    public void setDemandType(Integer demandType) {
        this.demandType = demandType;
    }

    public Long getDemandID() {
        return demandID;
    }

    public void setDemandID(Long demandID) {
        this.demandID = demandID;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    public Long getBillID() {
        return billID;
    }

    public void setBillID(Long billID) {
        this.billID = billID;
    }

    public Long getActionTime() {
        return actionTime;
    }

    public void setActionTime(Long actionTime) {
        this.actionTime = actionTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }
}
