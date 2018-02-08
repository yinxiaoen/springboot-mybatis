package org.spring.springboot.domain;

/**
 * Created by yinxiaoen on 2018/2/2.
 */
public class TableSplitParams {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 分组方式
     */
    private String splitNum;
    /**
     * 分组日期格式
     */
    private String timeSplitWay;
    /**
     * 创建日期
     */
    private Long createTime;

    private Long groupID;

    private String tableName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSplitNum() {
        return splitNum;
    }

    public void setSplitNum(String splitNum) {
        this.splitNum = splitNum;
    }

    public String getTimeSplitWay() {
        return timeSplitWay;
    }

    public void setTimeSplitWay(String timeSplitWay) {
        this.timeSplitWay = timeSplitWay;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getGroupID() {
        return groupID;
    }

    public void setGroupID(Long groupID) {
        this.groupID = groupID;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

