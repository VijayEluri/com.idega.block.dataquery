package com.idega.block.dataquery.data.sql;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.idega.block.dataquery.data.xml.QuerySQLPart;
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
	
	static public final String UNIQUE_IDENTIFIER = "UNIQUE_IDENTIFIER"; 
	static public final int UNIQUE_IDENTIFIER_MAX_LENGTH = 15;
	public static final String USER_ACCESS_VARIABLE = "user_access_variable";
	public static final String GROUP_ACCESS_VARIABLE = "group_access_variable";
	public static final String USER_GROUP_ACCESS_VARIABLE = "user_group_access_variable";
	
	private String sqlStatement;
	
	private String uniqueIdentifier;
	
	private String postStatement; 
	
	private Map identifierValueMap = new LinkedHashMap(0);
  private Map identifierInputDescriptionMap = new LinkedHashMap(0); 
  
  private Set keys;
  
  public DirectSQLStatement(QuerySQLPart sqlPart, Object identifier, String uniqueIdentifier, SQLQuery sqlQuery)	{
  	this.sqlStatement = sqlPart.getStatement();
  	Map variableValueMap = sqlPart.getVariableValueMap();
  	this.identifierValueMap.putAll(variableValueMap);
  	this.identifierInputDescriptionMap.putAll(sqlPart.getInputDescriptionValueMap());
  	this.keys = variableValueMap.keySet();
  	this.uniqueIdentifier = StringHandler.shortenToLength(uniqueIdentifier, UNIQUE_IDENTIFIER_MAX_LENGTH);
  	this.postStatement = sqlPart.getPostStatement();
  }

	public void setSQLStatement(String sqlStatement) 	{
		this.sqlStatement = sqlStatement;
	}

	/* (non-Javadoc)
	 * @see com.idega.block.dataquery.data.sql.DynamicExpression#isDynamic()
	 */
	public boolean isDynamic() {
		return ! this.keys.isEmpty();
	}

	/* (non-Javadoc)
	 * @see com.idega.block.dataquery.data.sql.DynamicExpression#getIdentifierValueMap()
	 */
	public Map getIdentifierValueMap() {
		return this.identifierValueMap;
	}

	/* (non-Javadoc)
	 * @see com.idega.block.dataquery.data.sql.DynamicExpression#getIdentifierDescriptionMap()
	 */
	public Map getIdentifierInputDescriptionMap() {
		return this.identifierInputDescriptionMap;
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
		Iterator iterator = this.identifierValueMap.entrySet().iterator();
		String result = StringHandler.replace(this.sqlStatement, UNIQUE_IDENTIFIER, this.uniqueIdentifier);
		while (iterator.hasNext())	{
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			if (this.keys.contains(key)) {
				String value = (String) entry.getValue();
				result = StringHandler.replace(result, key, value);
			}
		}
		return result;
	}
	
	public String getPostStatement() {
		return (this.postStatement == null) ? null : StringHandler.replace(this.postStatement, UNIQUE_IDENTIFIER, this.uniqueIdentifier);
	}
		
	/* (non-Javadoc)
	 * @see com.idega.block.dataquery.data.sql.Expression#isValid()
	 */
	public boolean isValid() {
		// TODO finish implementation
		return true;
	}

}
