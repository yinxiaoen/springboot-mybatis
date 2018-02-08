package org.spring.springboot.split;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class StrategyManager{
	@Resource
	private YYYYStrategy yyyyStrategy;
	@Resource
	private SupplyChainStrateory supplyChainStrateory;
	@Resource
	private SupplyChainUpdateStrateory supplyChainUpdateStrateory;

	private Log log= LogFactory.getLog(StrategyManager.class);
	private  Map<String,Strategy> strategies = new ConcurrentHashMap<String,Strategy>(10);
	
	public  Strategy getStrategy(String key){
		//按照年份的分表策略用于查询
		strategies.put("YYYY",yyyyStrategy);
		//按照月份的分表策略用于查询
		strategies.put("supplychainSplitWay",supplyChainStrateory);
		//按照月份的分表策略用于修改
		strategies.put("supplyChainUpdate",supplyChainUpdateStrateory);
		return strategies.get(key);
	}

	public   Map<String, Strategy> getStrategies() {
		return strategies;
	}

	public  void setStrategies(Map<String, String> strategies) {
		for(Entry<String, String> entry : strategies.entrySet()){
			try {
				this.strategies.put(entry.getKey(),(Strategy)Class.forName(entry.getValue()).newInstance());
			} catch (Exception e) {
				log.error("实例化策略出错", e);
			}
		}
		printDebugInfo();
	}
	private void printDebugInfo(){
		StringBuffer msg= new StringBuffer("初始化了"+strategies.size()+"策略");
		for(String key: strategies.keySet()){
			msg.append("\n");
			msg.append(key);
			msg.append("  --->  ");
			msg.append(strategies.get(key));
		}
		log.debug(msg.toString());
	}
}
