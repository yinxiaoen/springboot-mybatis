package org.spring.springboot.split;


import org.spring.springboot.dao.TableSplitParamsDao;
import org.spring.springboot.domain.TableSplitParams;
import org.spring.springboot.util.SqlUnion;
import org.spring.springboot.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yinxiaoen on 2018/2/1.
 * 主要解决联合查询
 */
@Component
public class SupplyChainStrateory implements Strategy{
    @Autowired
    private TableSplitParamsDao tableSplitParamsDao;

    /**
     * 这个策略是根据日期段来处理分表数据，即传入startDate 和 endDate 根据之间所岔开的月份来落点分表
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public String convert(Map<String, Object> params) throws Exception {
        TableSplitParams tableSplitParams = tableSplitParamsDao.findSplitWay(511L,params.get(Strategy.TABLE_NAME).toString());
        StringBuilder sb=new StringBuilder(params.get(Strategy.TABLE_NAME).toString());
        Map<String, Object> queryParamsMap = new HashMap<String, Object>();
        if(null != params.get("execute_param_values")){
            SimpleDateFormat sdf = new SimpleDateFormat(tableSplitParams.getTimeSplitWay());
            queryParamsMap = (Map<String, Object>) params.get("execute_param_values");
            if(null !=queryParamsMap.get("startDate") && null !=queryParamsMap.get("endDate")){
               Long startDate = Long.valueOf(queryParamsMap.get("startDate").toString());
               Long endDate = Long.valueOf(queryParamsMap.get("endDate").toString());
                if(null != tableSplitParams.getTimeSplitWay() && !"".equals(tableSplitParams.getTimeSplitWay())){
                    if(startDate.equals(endDate)){
                        sb.append("_");
                        sb.append(sdf.format(startDate));
                    }else{
                        sb=new StringBuilder();
                        List<String> list = TimeUtil.getMonthSpace(String.valueOf(startDate),String.valueOf(endDate));
                        String sql=new SqlUnion().getMap().get(params.get(Strategy.TABLE_NAME).toString());
                        StringJoiner sqlCommon = new StringJoiner("UNION");
                        if(list!=null && list.size()>0){
                            Iterator<String> batchSql = list.iterator();
                            while(batchSql.hasNext()){
                                String replaceSql = sql;
                                replaceSql=replaceSql.replace(params.get(Strategy.TABLE_NAME).toString(),params.get(Strategy.TABLE_NAME).toString()+"_"+batchSql.next());
                                sqlCommon.add("("+replaceSql+")");
                             }
                        }
                        sb.append("(");
                        sb.append(sqlCommon.toString());
                        sb.append(")");
                    }
                }
            }else {
                if(null != tableSplitParams.getTimeSplitWay() && !"".equals(tableSplitParams.getTimeSplitWay())){
                    sb.append("_");
                    sb.append(sdf.format(new Date()));
                }
            }
        }else{
            if(null != tableSplitParams.getTimeSplitWay() && !"".equals(tableSplitParams.getTimeSplitWay())){
                SimpleDateFormat sdf = new SimpleDateFormat(tableSplitParams.getTimeSplitWay());
                sb.append("_");
                sb.append(sdf.format(new Date()));
            }
        }
        return sb.toString();
    }
}
