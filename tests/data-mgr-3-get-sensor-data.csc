<?xml version="1.0" encoding="UTF-8"?>
<simconf>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/mrm</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/mspsim</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/avrora</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/serial_socket</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/collect-view</project>
  <project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/powertracker</project>
  <simulation>
	<title>Data Manager</title>
	<delaytime>0</delaytime>
	<randomseed>123456</randomseed>
<!--	<motedelay_us>1000000</motedelay_us>-->
	<radiomedium>
		se.sics.cooja.radiomediums.UDGM
		<transmitting_range>50.0</transmitting_range>
		<interference_range>100.0</interference_range>
		<success_ratio_tx>1.0</success_ratio_tx>
		<success_ratio_rx>1.0</success_ratio_rx>
	</radiomedium>
	<events>
		<logoutput>40000</logoutput>
	</events>
	<motetype>
		se.sics.cooja.mspmote.Z1MoteType
		<identifier>DataMgrZ1Node</identifier>
		<description>Data Manager Node</description>
		<firmware>../data-mgr/tester/tester.z1</firmware>
		<moteinterface>se.sics.cooja.interfaces.Position</moteinterface>
		<moteinterface>se.sics.cooja.interfaces.RimeAddress</moteinterface>
		<moteinterface>se.sics.cooja.interfaces.IPAddress</moteinterface>
		<moteinterface>se.sics.cooja.interfaces.Mote2MoteRelations</moteinterface>
		<moteinterface>se.sics.cooja.interfaces.MoteAttributes</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.MspClock</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.MspMoteID</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.MspButton</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.Msp802154Radio</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.MspDefaultSerial</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.MspLED</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.MspDebugOutput</moteinterface>
	</motetype>
	<mote>
		<breakpoints />
		<interface_config>
			se.sics.cooja.interfaces.Position
			<x>0.0</x>
			<y>0.0</y>
			<z>0.0</z>
		</interface_config>
		<interface_config>
			se.sics.cooja.mspmote.interfaces.MspMoteID
			<id>1</id>
		</interface_config>
		<motetype_identifier>DataMgrZ1Node</motetype_identifier>
	</mote>
	<mote>
		<breakpoints />
		<interface_config>
			se.sics.cooja.interfaces.Position
			<x>0.0</x>
			<y>0.0</y>
			<z>0.0</z>
		</interface_config>
		<interface_config>
			se.sics.cooja.mspmote.interfaces.MspMoteID
			<id>2</id>
		</interface_config>
		<motetype_identifier>DataMgrZ1Node</motetype_identifier>
	</mote>
	</simulation>
	<plugin>
    	se.sics.cooja.plugins.ScriptRunner
	    <plugin_config>
		<script>
TIMEOUT(100000, log.log("last msg: " + msg + "\n")); /* print last msg at timeout */

var ttl, repo_id;

/* Wait for node to boot */
testmote = null;
testnode = null;
testmote_id = 2;
while (testmote == null) {
  YIELD();
  if (id == testmote_id) {
    testmote = mote;
    testnode = node;
  }
}
log.log(time + " - node '" + testmote + "' booted!\n");
//----------------------------------
// 1st: Create repo
//----------------------------------
YIELD_THEN_WAIT_UNTIL(id == testmote_id &amp;&amp; msg.contains("enter input"));

command = "010"; // 0 (create repo) 10 (with ttl = 10)
write(testmote, command);

YIELD_THEN_WAIT_UNTIL(id == testmote_id &amp;&amp; msg.contains("ok!"));
ttl = msg.substring(msg.indexOf("ttl=[",0)+5,msg.indexOf("]",msg.indexOf("ttl=[",0)+5));
repo_id = msg.substring(msg.indexOf("repo_id=[",0)+9,msg.indexOf("]",msg.indexOf("repo_id=[",0)+9));
log.log("Repo creation ok, ttl='" + ttl + "', repo_id='"+repo_id+"'\n");
//----------------------------------


//----------------------------------
// 2nd: Wait
//----------------------------------
YIELD_THEN_WAIT_UNTIL(id == testmote_id &amp;&amp; msg.contains("enter input"));

command = "2" + "3"; // 2 (wait) + time: 3 seconds
write(testmote, command);

YIELD_THEN_WAIT_UNTIL(id == testmote_id &amp;&amp; msg.contains("ok!"));
wait = msg.substring(msg.indexOf("wait=[",0)+6,msg.indexOf("]",msg.indexOf("wait=[",0)+6));
log.log("Waiting ok, wait='"+wait+"'\n");
//----------------------------------

//----------------------------------
// 3rd: Get data
//----------------------------------
YIELD_THEN_WAIT_UNTIL(id == testmote_id &amp;&amp; msg.contains("enter input"));

command = "3 " + repo_id + " 4"; // 3 (get data) + repo_id + field (SENSOR_TEMPERATURE_FAHRENHEIT)
write(testmote, command);

//YIELD_THEN_WAIT_UNTIL(id == testmote_id &amp;&amp; msg.contains("got"));
//log.log("Get, msg is '"+ msg +"'\n");

YIELD_THEN_WAIT_UNTIL(id == testmote_id &amp;&amp; msg.contains("ok!"));
repo_id = msg.substring(msg.indexOf("repo_id=[",0)+9,msg.indexOf("]",msg.indexOf("repo_id=[",0)+9));
entry_id = msg.substring(msg.indexOf("entry_id=[",0)+10,msg.indexOf("]",msg.indexOf("entry_id=[",0)+10));
dr_data = msg.substring(msg.indexOf("dr_data=[",0)+9,msg.indexOf("]",msg.indexOf("dr_data=[",0)+9));
dr_data_len = msg.substring(msg.indexOf("dr_data_len=[",0)+13,msg.indexOf("]",msg.indexOf("dr_data_len=[",0)+13));
log.log("Get data ok, repo_id='"+repo_id+"', entry_id='"+entry_id+"', dr_data='"+dr_data+"', dr_data_len='"+dr_data_len+"'\n");
//----------------------------------

//----------------------------------
// 4th: Wait
//----------------------------------
YIELD_THEN_WAIT_UNTIL(id == testmote_id &amp;&amp; msg.contains("enter input"));

command = "2" + "10"; // 2 (wait) + time: 10 seconds
write(testmote, command);

YIELD_THEN_WAIT_UNTIL(id == testmote_id &amp;&amp; msg.contains("ok!"));
wait = msg.substring(msg.indexOf("wait=[",0)+6,msg.indexOf("]",msg.indexOf("wait=[",0)+6));
log.log("Waiting ok, wait='"+wait+"'\n");
//----------------------------------


//----------------------------------
// 5th: Get data
//----------------------------------
YIELD_THEN_WAIT_UNTIL(id == testmote_id &amp;&amp; msg.contains("enter input"));

command = "3 " + repo_id + " 4"; // 3 (get data) + repo_id + field (SENSOR_TEMPERATURE_FAHRENHEIT)
write(testmote, command);

//YIELD_THEN_WAIT_UNTIL(id == testmote_id &amp;&amp; msg.contains("got"));
//log.log("Get, msg is '"+ msg +"'\n");

YIELD_THEN_WAIT_UNTIL(id == testmote_id &amp;&amp; msg.contains("ok!"));
repo_id = msg.substring(msg.indexOf("repo_id=[",0)+9,msg.indexOf("]",msg.indexOf("repo_id=[",0)+9));
entry_id = msg.substring(msg.indexOf("entry_id=[",0)+10,msg.indexOf("]",msg.indexOf("entry_id=[",0)+10));
dr_data = msg.substring(msg.indexOf("dr_data=[",0)+9,msg.indexOf("]",msg.indexOf("dr_data=[",0)+9));
dr_data_len = msg.substring(msg.indexOf("dr_data_len=[",0)+13,msg.indexOf("]",msg.indexOf("dr_data_len=[",0)+13));
log.log("Get data ok, repo_id='"+repo_id+"', entry_id='"+entry_id+"', dr_data='"+dr_data+"', dr_data_len='"+dr_data_len+"'\n");
//----------------------------------

log.testOK(); /* Report test success and quit */
		</script>
		<active>true</active>
		</plugin_config>
	</plugin>
</simconf>

