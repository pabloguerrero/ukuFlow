<?xml version="1.0" encoding="UTF-8"?>
<simconf>
	<project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/mrm</project>
	<project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/mspsim</project>
	<project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/avrora</project>
	<project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/serial_socket</project>
	<project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/collect-view</project>
	<simulation>
	<title>Data Repository</title>
	<delaytime>0</delaytime>
	<randomseed>123456</randomseed>
	<randomseed>123456</randomseed>
<!--	<motedelay_us>1000000</motedelay_us>-->
	<motedelay_us>0</motedelay_us>
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
		se.sics.cooja.mspmote.SkyMoteType
		<identifier>DataRepositorySkyNode</identifier>
		<description>Data Repository Node</description>
		<source>../data-repository/tester.c</source>
		<commands>make TARGET=sky tester.sky
		</commands>
<!--		<commands>make TARGET=sky clean
make TARGET=sky tester.sky
		</commands>
-->
		<firmware>../data-repository/tester.sky</firmware>

		<moteinterface>se.sics.cooja.interfaces.Position</moteinterface>
		<moteinterface>se.sics.cooja.interfaces.RimeAddress</moteinterface>
		<moteinterface>se.sics.cooja.interfaces.IPAddress</moteinterface>
		<moteinterface>se.sics.cooja.interfaces.Mote2MoteRelations</moteinterface>
		<moteinterface>se.sics.cooja.interfaces.MoteAttributes</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.MspClock</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.MspMoteID</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.SkyButton</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.SkyFlash</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.SkyCoffeeFilesystem</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.SkyByteRadio</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.MspSerial</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.SkyLED</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.MspDebugOutput</moteinterface>
		<moteinterface>se.sics.cooja.mspmote.interfaces.SkyTemperature</moteinterface>
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
		<motetype_identifier>DataRepositorySkyNode</motetype_identifier>
	</mote>
	</simulation>
	<plugin>
    	se.sics.cooja.plugins.ScriptRunner
	    <plugin_config>
		<script>
TIMEOUT(100000, log.log("last msg: " + msg + "\n")); /* print last msg at timeout */

var ttl, repo_id;

/* Wait for node to boot */
mote1 = null;
node1 = null;
while (mote1 == null) {
  if (id == 1) {
    mote1 = mote;
    node1 = node;
  }
  YIELD();
}
log.log(time + " - node booted!\n");


//----------------------------------
// 1st: Create repo
//----------------------------------
YIELD_THEN_WAIT_UNTIL(id == 1 &amp;&amp; msg.contains("enter input"));

command = "010"; // 0 (create repo) 10 (with ttl = 10)
write(mote1, command);

YIELD_THEN_WAIT_UNTIL(id == 1 &amp;&amp; msg.contains("ok!"));
ttl = msg.substring(msg.indexOf("ttl=[",0)+5,msg.indexOf("]",msg.indexOf("ttl=[",0)+5));
repo_id = msg.substring(msg.indexOf("repo_id=[",0)+9,msg.indexOf("]",msg.indexOf("repo_id=[",0)+9));
log.log("Repo creation ok, ttl='" + ttl + "', repo_id='"+repo_id+"'\n");
//----------------------------------


log.testOK(); /* Report test success and quit */

		</script>
		<active>true</active>
		</plugin_config>
		<width>640</width>
		<z>0</z>
		<height>480</height>
		<location_x>518</location_x>
		<location_y>28</location_y>
	</plugin>
</simconf>

