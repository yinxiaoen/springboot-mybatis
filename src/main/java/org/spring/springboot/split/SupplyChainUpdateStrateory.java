package org.spring.springboot.split;

import org.spring.springboot.dao.TableSplitParamsDao;
import org.spring.springboot.domain.TableSplitParams;
import org.spring.springboot.util.ThreadLocalManager;
import org.spring.springboot.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yinxiaoen on 2018/2/7.
 * update delete 分表操作
 */
@Component
public class SupplyChainUpdateStrateory implements Strategy {
    @Autowired
    private TableSplitParamsDao tableSplitParamsDao;
    @Override
    public String convert(Map<String, Object> params) throws Exception {
        Map<String,Object> map = ThreadLocalManager.getConsoleContext().getRequestHeader();
        TableSplitParams tableSplitParams = tableSplitParamsDao.findSplitWay(Long.valueOf(map.get("groupID").toString()),params.get(Strategy.TABLE_NAME).toString());
        StringBuilder sb=new StringBuilder(params.get(Strategy.TABLE_NAME).toString());
        if(null != params.get("execute_param_values")){
            SimpleDateFormat sdf = new SimpleDateFormat(tableSplitParams.getTimeSplitWay());
            if(null !=map.get("startDate") && null !=map.get("endDate")){
                Long startDate = Long.valueOf(map.get("startDate").toString());
                Long endDate = Long.valueOf(map.get("endDate").toString());
                if(null != tableSplitParams.getTimeSplitWay() && !"".equals(tableSplitParams.getTimeSplitWay())){
                    if(startDate.equals(endDate)){
                        String sql=params.get("sql").toString();
                        sb.append("_");
                        SimpleDateFormat sdfCover = new SimpleDateFormat("yyyyMMdd");
                        Calendar c1 = Calendar.getInstance();
                        c1.setTime(sdfCover.parse(startDate.toString()));
                        sb.append(sdf.format(c1.getTime()));
                        sql.replace(params.get(Strategy.TABLE_NAME).toString(),sb);
                        return sql;
                    }else{
                        sb=new StringBuilder();
                        List<String> list = TimeUtil.getMonthSpace(String.valueOf(startDate),String.valueOf(endDate));
                        String sql=params.get("sql").toString();
                        StringJoiner sqlCommon = new StringJoiner(",");
                        if(list!=null && list.size()>0){
                            Iterator<String> batchSql = list.iterator();
                            while(batchSql.hasNext()){
                                String replaceSql = sql;
                                String values = params.get(Strategy.TABLE_NAME).toString();
                                replaceSql=replaceSql.replace(params.get(Strategy.TABLE_NAME).toString(),params.get("placeholder").toString()+"_"+batchSql.next());
                                sqlCommon.add(replaceSql);
                            }
                        }
                        sb.append(sqlCommon.toString());
                    }
                }
            }else {
                if(null != tableSplitParams.getTimeSplitWay() && !"".equals(tableSplitParams.getTimeSplitWay())){
                    String sql=params.get("sql").toString();
                    sb.append("_");
                    sb.append(sdf.format(new Date()));
                    sql.replace(params.get(Strategy.TABLE_NAME).toString(),sb);
                }
            }
        }
        return sb.toString();
    }
}
