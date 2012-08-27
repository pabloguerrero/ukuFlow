package de.tudarmstadt.dvs.ukuflow.constant;

import java.util.HashMap;

public class ExpressionTypes {
	/* expression-types.h */
	public static final int OPERATOR_AND = 0;
	public static final int OPERATOR_OR = 1;
	public static final int OPERATOR_NOT = 2;
	
	public static final int PREDICATE_EQ = 3;
	public static final int PREDICATE_NEQ = 4;
	public static final int PREDICATE_LT = 5;
	public static final int PREDICATE_GT = 6;
	public static final int PREDICATE_LET = 7;
	public static final int PREDICATE_GET = 8;
	
	public static final int OPERATOR_ADD = 9;
	public static final int OPERATOR_SUB = 10;
	public static final int OPERATOR_DIV = 11;
	public static final int OPERATOR_MULT = 12;
	public static final int OPERATOR_MOD = 13;
	
	public static final int UINT8_VALUE = 14;
	public static final int UINT16_VALUE = 15;
	public static final int INT8_VALUE = 16;
	public static final int INT16_VALUE = 17;
	
	public static final int STRING_VALUE = 18;
	
	public static final int REPOSITORY_VALUE = 19;
	
	public static final int CUSTOM_INPUT_VALUE = 20;
	public static HashMap<String, Integer> mappingRules = new HashMap<String, Integer>();
	
	static{
		mappingRules.put("+", OPERATOR_ADD);
		mappingRules.put("-", OPERATOR_SUB);
		mappingRules.put("/", OPERATOR_DIV);
		mappingRules.put("%", OPERATOR_MOD);
		mappingRules.put("*", OPERATOR_MULT);
		
		mappingRules.put("&&", OPERATOR_AND);
		mappingRules.put("&", OPERATOR_AND);
		mappingRules.put("AND", OPERATOR_AND);
		//mappingRules.put("and", OPERATOR_AND);
		
		mappingRules.put("||", OPERATOR_OR);
		mappingRules.put("|", OPERATOR_OR);
		mappingRules.put("OR", OPERATOR_OR);
		//mappingRules.put("||", OPERATOR_OR);
		
		mappingRules.put("~", OPERATOR_NOT);
		mappingRules.put("NOT", OPERATOR_NOT);
	}
	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(mappingRules.get("*"));
	}
}
