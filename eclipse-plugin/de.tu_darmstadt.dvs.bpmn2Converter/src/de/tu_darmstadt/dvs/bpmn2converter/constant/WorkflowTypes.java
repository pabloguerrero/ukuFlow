package de.tu_darmstadt.dvs.bpmn2converter.constant;

public class WorkflowTypes {
	/* workflow-types.h */
	public static final int START_EVENT = 0;
	public static final int END_EVENT = 1;
	public static final int EXECUTE_TASK = 2;
	public static final int PUBLISH_TASK = 3;
	public static final int SUBSCRIBE_TASK = 4;
	public static final int SUBWORKFLOW_TASK = 5;
	public static final int FORK_GATEWAY = 6;
	public static final int JOIN_GATEWAY = 7;
	public static final int INCLUSIVE_DECISION_GATEWAY = 8;
	public static final int INCLUSIVE_JOIN_GATEWAY = 9;
	public static final int EXCLUSIVE_DECISION_GATEWAY = 10;
	public static final int EXCLUSIVE_MERGE_GATEWAY = 11;
	public static final int EVENT_BASED_EXCLUSIVE_DECISION_GATEWAY = 12;
	public static final int COMPUTATION_STATEMENT = 0;
	public static final int LOCAL_FUNCTION_STATEMENT = 1;
	public static final int SCOPED_FUNCTION_STATEMENT = 2;
}
