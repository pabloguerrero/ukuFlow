------------------------
- Background:
  Standards:
  - WfMC 
  - UML (2.0) Activity Diagrams, from the OMG
  - BPMN Business Process Diagram, from the Business Process Management Initiative (BPMI) (Version 2 and Version 3 differ)
  - Workflow Patterns

------------------------

A workflow is composed of tasks and control flow elements.
Tasks are conceptual units of work. There are different types of tasks: 
* execute task,
* publish event task,
* subscribe event task, and
* subworkflows.

Execute tasks contain a script, which is a set of commands such as the activation of an actuator. 
A workflow has a set of predefined variables that can be read/written at runtime.
These tasks may be parameterized by reading from (receiving the value of) workflow-wide variables, as well as writing into them.

A control flow element can be a simple, direct transition between two tasks, or more complex constructs diverting and merging the execution through various paths.
There are different types of control flow elements:
* direct
* fork (AND-split) and its counterpart, join (AND-join)
* inclusive decision (OR-split) and its counterpart, inclusive merge (OR-join)
* exclusive decision (XOR-split) and its counterpart, exclusive merge (XOR-split)
* event-based exclusive decision (E-b-XOR-split) and its counterpart, exclusive merge (XOR-split).
Every workflow has a start event, and it can have zero or more end events. 



------------------------
Workflow Representation:

A key aspect for the representation of a workflow that runs in a network of resource-constrained devices is its compactness. This requires paying special attention to data redundancy. 
For this reason, ukuflow utilizes the following data structure to describe a workflow:

* Number of workflow elements (we), 8 bits
* Sequence of we:
  - wf_elem_id, 8 bits:
  - wf_elem_type, 8 bits: 
      start event (wf_start) [corresponds to the BPMN "None Start Event", pp. 240]
    | end event	 (wf_end)
    | execute task (ex_task)
    | publish event task (publ_task) [3]
    | subscribe event task (subs_task) [3]
    | subworkflow task (subwf_task)
	| fork gateway (fork_gw)
	| join gateway (join_gw)
	| inclusive decision gateway (i_dec_gw)
	| inclusive merge gateway (i_merge_gw)
    | exclusive decision gateway (x_dec_gw)
    | exclusive merge gateway (x_merge_gw)
    | event-based exclusive decision gateway (eb_x_dec_gw)
    - for start event: 
	  - wf_elem_id of next wf_elem (task or gateway), 8 bits
    - for end event
      - (empty)
    - for execute tasks (ex_task):
	  - wf_elem_id of next wf_elem (task or gateway), or null if it's an end event, 8 bits
      - Number of statements (8 bits)
      - (*) Sequence of statements:
      	- variable_id which gets assigned (or null) (8 bits) 
      	- id of command to execute (8 bits)
      	- Number of parameters (8 bits)
        - (*) Sequence of variable_ids
    - for publish event tasks (publ_task): 
	  - wf_elem_id of next wf_elem (task or gateway), 8 bits
      - Number of name-value-pairs (NVPs), 8 bits
      - Sequence of NVPs:
        (*) <variable_id (8 bits), variable_id (8 bits) >
    - for subscribe tasks (subs_task): 
	  - wf_elem_id of next wf_elem (task or gateway), 8 bits
      - event filter expression [3]
    - for subworkflow task (subwf_task):
	  - wf_elem_id of next wf_elem (task or gateway), 8 bits
      - wf_elem_id of initial wf_elem (8 bits)
    - for fork gateways (fork_gw):
      - number of outgoing flows to fork to (8 bits)
      (*) Sequence of wf_elem_ids to fork to (8 bits)
    - for join gateways (join_gw):
      - number of incoming flows that will be merged (8 bits)
      (*) Sequence of wf_elem_ids that will be merged
    - for inclusive decision gateways (i_dec_gw):
      - number of outgoing flows to which it will be potentially forked (8 bits)
      (*) Sequence of <wf_elem_id, data expression [2]>
    - for inclusive merge gateways (i_merge_gw):
      - number of incoming flows that will be merged (8 bits)
      (*) Sequence of wf_elem_ids that will be merged
    - for exclusive decision gateways (x_dec_gw):
      - number of outgoing flows (8 bits)
      (*) Sequence of <wf_elem_id (8 bits), data expression [2]>
    - for exclusive merge gateways (x_merge_gw):
      - number of incoming flows that will be merged (8 bits)
      (*) Sequence of wf_elem_ids that will be merged
    - for event-based exclusive gateways (eb_x_dec_gw):
      - number of outgoing flows (8 bits)
      (*) Sequence of <wf_elem_id, event expressions [3]>

   
[1] Task Statements
Three types:
* Calculation (formula)
* Local function call
* Scoped function call
   
[2] Data Expressions
Logical expressions accessing variables' data.

[3] Event Expressions
Refer to the event model.

Events filters are classified into simple and composite:
Simple filters include a set of constraints on an event's fields

--------
Example byte array
4,  1, 1, 2,  2, 5, 3, 1,   1, 1, 5, OPERATOR_ADD, REPOSITORY_VALUE, SENSOR_TEMPERATURE_FAHRENHEIT, UINT8_VALUE, 10,      3, 5, 4, 1,    2, 0, 12, 98 108 105 110 107 32 53,      3, 2

4	Number of workflow elements (we), 8 bits

0	wf_elem_id of this (first) wf_elem
1	wf_type (start event)
1	wf_elem_id of next wf_elem

1	wf_elem_id of this (second) wf_elem
5	wf_type (execute task)
2	wf_elem_id of next wf_elem
1	number of statements

	1	statement_type (computation)
	1	id of variable "var1"
	5	length of data-expression
	OPERATOR_ADD
		constant for addition
	REPOSITORY_VALUE
	SENSOR_TEMPERATURE_FAHRENHEIT
		retrieval of temperature
	UINT8_VALUE
	10
		constant for value 10
	
2	wf_elem_id of this (third) wf_elem
5	wf_type (execute task)
3	wf_elem_id of next wf_elem
1	number of statements

	2	statement_type (local function)
	0	id of variable that gets assigned result (0, since result is not assigned to any variable)
	12	length of shell command
	98 108 105 110 107 32 53 ('blink 5')

3	wf_elem_id of this (fourth) wf_elem
2	wf_type (end event)

--------
- Workflow Execution

Idea of token(s) that flow through diagram. Ids of splitting gateways get stacked (token gets bigger for paths that branch a lot, stay flat for direct paths.  

--------
Deepening a Manager

The manager function is best placed closer to the member nodes. A manager node can shift its role downstream to a child node iff task members are only reachable through that child node (considering the manager node possibly being member as well).

- Pattern:
  * Sequence: ordered sequence of activities
    Comparison: Both notations use rounded rectangles to denote activities, while arrows only indicate flow direction. In our notation, the arrows define the flow direction and the activities, while we use rectangles to denote "frozen" state.
  * Parallel split: allows activities to be executed concurrently, instead of serially 
    Comparison: Both notations differ here, BPMN being more complex. We adopt UML, which uses a black bar with one arrow going into it and several leaving it.
  * Synchronization: tokens from all previously split flows must be completed before the execution can continue (also called "AND-Join")
  * Exclusive Choice (also called "OR-Split"): expressions will be evaluated (in a non-determined order) and for the expression that is determined to be true, the corresponding control flow will be chosen and the Token will continue down that path. Only one Token will exit the decision node for each Token that arrives at the node.
  * Simple Merge (also called "OR-Join"): a set of alternative paths is joined into a single path

---------
Fairness in Subscriptions:
 * One issue with disseminating subscriptions is that of fairness.
 * If one of the subscriptions of an ebg is disseminated considerably before the others, it will
 * likely be the first to generate events that potentially match the overall
 * event operator expression, leading to unfairness among subscriptions.
 * For this reason, the subscription process could consist of two steps:
 * First, we need to calculate how long the subscription dissemination would take.
 * This is not a neglectable time since requires opening scopes and waiting for the scope
 * specifications to be evaluated.
 * Second, the actual subscription dissemination process is executed. This is done by
 * requesting the event manager to make the necessary subscriptions, taking into account the
 * delay that should be inserted, which was calculated in the first step.

 uint8_t calculate_event_operator_processing_delays(
		struct event_operator_flow *fl) {

	struct generic_event_operator *geo_iter;
	uint8_t delays = 0;
	data_len_t processed_len = 0;

	geo_iter = (struct generic_event_operator*) (((uint8_t*) fl)
			+ sizeof(struct event_operator_flow));
	while (processed_len < fl->total_event_operator_length) {

		if (geo_iter->ev_op_type <= DISTRIBUTED_E_GEN) {
			/** We have found an event operator that is an event generator, so send it to its associated scope */
			struct generic_egen *g_egen = (struct generic_egen*) geo_iter;

			/** We have found an event operator that is an event generator, so increment number of delays by 1 due to the message */
			delays++;

			/** increment number of delays by 1 further, if the associated scope is not the world scope */
			if (g_egen->scope_id != SCOPES_WORLD_SCOPE_ID)
				delays++;
		}

		data_len_t oper_len = event_operator_get_size(geo_iter);
		/** Decrease remaining_len: */
		processed_len += oper_len;
		/** Adjust pointer to next event operator:*/
		geo_iter = (struct generic_event_operator*) (((uint8_t*) geo_iter)
				+ oper_len);
	}

	return delays;

}
 