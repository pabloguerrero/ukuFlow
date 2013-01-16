<?xml version="1.0" encoding="UTF-8"?>
<simconf>
	<project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/mrm</project>
	<project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/mspsim</project>
	<project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/avrora</project>
	<project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/serial_socket</project>
	<project EXPORT="discard">[CONTIKI_DIR]/tools/cooja/apps/collect-view</project>
	<simulation>
		<title>Data Repository Tester</title>
		<delaytime>0</delaytime>
		<randomseed>123456</randomseed>
		<motedelay_us>1000000</motedelay_us>
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
			<identifier>sky1</identifier>
			<description>Sky Mote Type #sky1</description>
			<firmware EXPORT="copy">[CONFIG_DIR]/../tester.sky</firmware>
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
			<motetype_identifier>sky1</motetype_identifier>
		</mote>
		<mote>
			<breakpoints />
			<interface_config>
				se.sics.cooja.interfaces.Position
				<x>100.0</x>
				<y>0.0</y>
				<z>0.0</z>
			</interface_config>
			<interface_config>
				se.sics.cooja.mspmote.interfaces.MspMoteID
				<id>300</id>
			</interface_config>
			<motetype_identifier>sky1</motetype_identifier>
		</mote>
	</simulation>
	<plugin>
		se.sics.cooja.plugins.SimControl
		<width>259</width>
		<z>3</z>
		<height>199</height>
		<location_x>0</location_x>
		<location_y>0</location_y>
	</plugin>
	<plugin>
		se.sics.cooja.plugins.Visualizer
		<plugin_config>
			<skin>se.sics.cooja.plugins.skins.IDVisualizerSkin</skin>
			<viewport>0.9090909090909091 0.0 0.0 0.9090909090909091
				98.54545454545455 120.99999999999999</viewport>
		</plugin_config>
		<width>300</width>
		<z>0</z>
		<height>300</height>
		<location_x>980</location_x>
		<location_y>0</location_y>
	</plugin>
	<plugin>
		se.sics.cooja.plugins.LogListener
		<plugin_config>
			<filter>ID:1</filter>
		</plugin_config>
		<width>1280</width>
		<z>1</z>
		<height>446</height>
		<location_x>0</location_x>
		<location_y>300</location_y>
	</plugin>
	<plugin>
		se.sics.cooja.plugins.TimeLine
		<plugin_config>
			<mote>0</mote>
			<mote>1</mote>
			<showRadioRXTX />
			<showRadioHW />
			<showLEDs />
			<split>111</split>
			<zoomfactor>500.0</zoomfactor>
		</plugin_config>
		<width>1280</width>
		<z>2</z>
		<height>150</height>
		<location_x>0</location_x>
		<location_y>746</location_y>
	</plugin>
</simconf>

