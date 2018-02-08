package org.spring.springboot.split;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 按年分表
 * @author dushangkui
 *
 */
@Component
public class YYYYStrategy implements Strategy{

	@Override
	public String convert(Map<String, Object> params) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
		StringBuilder sb=new StringBuilder(params.get(Strategy.TABLE_NAME).toString());
		sb.append("_");
		sb.append(sdf.format(new Date()));
		return sb.toString();
	}
	
}