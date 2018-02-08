package org.spring.springboot.split;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.spring.springboot.Annotation.TableParentsSplit;
import org.spring.springboot.Annotation.TableSplit;
import org.spring.springboot.Annotation.TableSplitUpdate;
import org.spring.springboot.util.ContextHelper;


@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class,Integer.class}) })
public class TableSplitInterceptor implements Interceptor {
	private Log log = LogFactory.getLog(getClass());

	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	/**
	 * 默认ObjectWrapperFactory
	 */
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	/**
	 * 默认ReflectorFactory
	 */
	private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();


	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,DEFAULT_REFLECTOR_FACTORY);

		BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
		// Configuration configuration = (Configuration) metaStatementHandler
		// .getValue("delegate.configuration");
		Object parameterObject = metaStatementHandler.getValue("delegate.boundSql.parameterObject");
		doSplitTable(metaStatementHandler,parameterObject);
		// 传递给下一个拦截器处理
		return invocation.proceed();

	}

	@Override
	public Object plugin(Object target) {
		// 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {

	}

	private void doSplitTable(MetaObject metaStatementHandler, Object param ) throws Exception {
		String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		if (originalSql != null && !originalSql.equals("")) {
			log.info("分表前的SQL：\n" + originalSql);
			MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
			String id = mappedStatement.getId();
			String className = id.substring(0, id.lastIndexOf("."));
			String methodName = id.substring(id.lastIndexOf(".") + 1);
			Class<?> clazz = Class.forName(className);
			ParameterMap paramMap = mappedStatement.getParameterMap();
			Method method = findMethod(clazz.getDeclaredMethods(), methodName);
			// 根据注解TableSplit配置自动生成分表SQL
			TableSplit tableSplit = null;
			if (method != null) {
				tableSplit = method.getAnnotation(TableSplit.class);
			}

			if (tableSplit == null) {
				tableSplit = clazz.getAnnotation(TableSplit.class);
			}

			if (tableSplit != null && tableSplit.split() && StringUtils.isNotBlank(tableSplit.strategy())) {
				StrategyManager strategyManager = ContextHelper.getBean(StrategyManager.class);
				String convertedSql = "";
				String[] strategies = tableSplit.strategy().split(",");
				for (String str : strategies) {
					Strategy strategy = strategyManager.getStrategy(str);
					Map<String,Object> params =new HashMap<String,Object>();
					params.put(Strategy.TABLE_NAME, tableSplit.value());
					params.put(Strategy.SPLIT_FIELD, tableSplit.field());
					params.put(Strategy.EXECUTE_PARAM_DECLARE, paramMap);
					params.put(Strategy.EXECUTE_PARAM_VALUES, param);
					convertedSql = originalSql.replaceAll(tableSplit.value(), strategy.convert(params));
				}
				metaStatementHandler.setValue("delegate.boundSql.sql", convertedSql);
				log.info("分表后的SQL：\n" + convertedSql);
			}

			TableParentsSplit tableParentsSplit = null;
			if (method != null) {
				tableParentsSplit = method.getAnnotation(TableParentsSplit.class);
			}

			if (tableParentsSplit == null) {
				tableParentsSplit = clazz.getAnnotation(TableParentsSplit.class);
			}

			if (tableParentsSplit != null && tableParentsSplit.split() && StringUtils.isNotBlank(tableParentsSplit.strategy())) {
				StrategyManager strategyManager = ContextHelper.getBean(StrategyManager.class);
				String[] strategies = tableParentsSplit.strategy().split(",");
				for (String str : strategies) {
					Strategy strategy = strategyManager.getStrategy(str);
					Map<String,Object> params =new HashMap<String,Object>();
					params.put(Strategy.TABLE_NAME, tableParentsSplit.value());
					params.put(Strategy.SPLIT_FIELD, tableParentsSplit.field());
					params.put(Strategy.EXECUTE_PARAM_DECLARE, paramMap);
					params.put(Strategy.EXECUTE_PARAM_VALUES, param);

					String[] tables = tableParentsSplit.value().split(",");
					Integer i = 0;
					for(String table : tables){
						i++;
						originalSql = originalSql.replaceAll(table,"#"+i);
					}
					for(int j=0;j<i;j++){
						params.put(Strategy.TABLE_NAME, tables[j]);
						String replaceSql = strategy.convert(params);
						Integer num = j+1;
						String title = "#"+num;
						originalSql = originalSql.replaceAll(title, replaceSql);
					}
				}
				metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
				log.info("分表后的SQL：\n" + originalSql);
			}


			TableSplitUpdate tableUpdateSplit = null;
			if (method != null) {
				tableUpdateSplit = method.getAnnotation(TableSplitUpdate.class);
			}

			if (tableUpdateSplit == null) {
				tableUpdateSplit = clazz.getAnnotation(TableSplitUpdate.class);
			}

			if (tableUpdateSplit != null && tableUpdateSplit.split() && StringUtils.isNotBlank(tableUpdateSplit.strategy())) {
				StrategyManager strategyManager = ContextHelper.getBean(StrategyManager.class);
				String[] strategies = tableUpdateSplit.strategy().split(",");
				for (String str : strategies) {
					Strategy strategy = strategyManager.getStrategy(str);
					Map<String,Object> params =new HashMap<String,Object>();
					params.put(Strategy.TABLE_NAME, tableUpdateSplit.value());
					params.put(Strategy.SPLIT_FIELD, tableUpdateSplit.field());
					params.put(Strategy.EXECUTE_PARAM_DECLARE, paramMap);
					params.put(Strategy.EXECUTE_PARAM_VALUES, param);
					params.put("sql",originalSql);
					String[] tables = tableUpdateSplit.value().split(",");
					Integer i = 0;
					for(String table : tables){
					    params.put("placeholder", "#"+i);
						originalSql = strategy.convert(params);
						i++;
					}
					for(int j=0;j<i;j++){
						String title = "#"+j;
						originalSql = originalSql.replaceAll(title, tables[j]);
					}
				}
				metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
				log.info("分表后的SQL：\n" + originalSql);
			}
		}
	}

	private Method findMethod(Method[] methods, String methodName) {
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}

}
