package com.idega.block.dataquery.data.sql;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.idega.block.dataquery.business.QuerySQLPart;
import com.idega.util.StringHandler;

/**
 * <p>Title: idegaWeb</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: idega Software</p>
 * @author <a href="thomas@idega.is">Thomas Hilbig</a>
 * @version 1.0
 * Created on Nov 4, 2003
 */
public class DirectSQLStatement implements DynamicExpression {
	
	private String sqlStatement;
	
	private Map identifierValueMap = new HashMap();
  private Map identifierDescriptionMap = new HashMap(); 
  
  public DirectSQLStatement(QuerySQLPart sqlPart, Object identifier, QuerySQL querySQL)	{
  	sqlStatement = sqlPart.getStatement();
  	identifierValueMap.putAll(sqlPart.getVariableValueMap());
  	identifierDescriptionMap.putAll(sqlPart.getDescriptionValueMap());
  }

	public void setSQLStatement(String sqlStatement) 	{
		this.sqlStatement = sqlStatement;
	}

	/* (non-Javadoc)
	 * @see com.idega.block.dataquery.data.sql.DynamicExpression#isDynamic()
	 */
	public boolean isDynamic() {
		// TODO finish implementation
		return true;
	}

	/* (non-Javadoc)
	 * @see com.idega.block.dataquery.data.sql.DynamicExpression#getIdentifierValueMap()
	 */
	public Map getIdentifierValueMap() {
		return identifierValueMap;
	}

	/* (non-Javadoc)
	 * @see com.idega.block.dataquery.data.sql.DynamicExpression#getIdentifierDescriptionMap()
	 */
	public Map getIdentifierDescriptionMap() {
		return identifierDescriptionMap;
	}

	/* (non-Javadoc)
	 * @see com.idega.block.dataquery.data.sql.DynamicExpression#setIdentifierValueMap(java.util.Map)
	 */
	public void setIdentifierValueMap(Map identifierValueMap) {
		this.identifierValueMap = identifierValueMap;
	}

	/* (non-Javadoc)
	 * @see com.idega.block.dataquery.data.sql.Expression#toSQLString()
	 */
	public String toSQLString() {
		Iterator iterator = identifierValueMap.entrySet().iterator();
		String result = sqlStatement;
		while (iterator.hasNext())	{
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			result = StringHandler.replace(result, key, value);
		}
		return result;
	}
		
	/* (non-Javadoc)
	 * @see com.idega.block.dataquery.data.sql.Expression#isValid()
	 */
	public boolean isValid() {
		// TODO finish implementation
		return true;
	}

}