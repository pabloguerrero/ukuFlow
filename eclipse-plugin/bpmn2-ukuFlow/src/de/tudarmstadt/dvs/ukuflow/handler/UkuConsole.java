package de.tudarmstadt.dvs.ukuflow.handler;

import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

public class UkuConsole {
	private static UkuConsole instance;
	public static final String CONSOLE_NAME = "ukuflow console";

	public static final boolean outBoolean = true;
	public static boolean errBoolean = true;
	public static boolean infoBoolean = true;

	static MessageConsole myConsole = null;

	static MessageConsoleStream out = null;
	static MessageConsoleStream err = null;
	static MessageConsoleStream info = null;
	static MessageConsoleStream warn = null;

	private UkuConsole() {
		myConsole = findConsole(CONSOLE_NAME);

		out = myConsole.newMessageStream();
		err = myConsole.newMessageStream();
		info = myConsole.newMessageStream();
		warn = myConsole.newMessageStream();

		err.setColor(new Color(MonitorUlti.getDisplay(), 255, 0, 0));
		warn.setColor(new Color(MonitorUlti.getDisplay(), 210, 105, 30));
		out.setColor(new Color(MonitorUlti.getDisplay(), 0, 0, 255));

		out.setActivateOnWrite(true);
		info.setActivateOnWrite(true);
		warn.setActivateOnWrite(true);
		err.setActivateOnWrite(true);

	}

	public void println() {
		info.println();
	}

	public static UkuConsole getConsole() {
		if (instance == null) {
			instance = new UkuConsole();
		}
		return instance;
	}

	public void warn(Object... objs) {
		String msg = generateMessage(false, objs);
		if (msg != null) {
			warn.println(msg);
		}
	}

	public void error(Object... objs) {
		if (!errBoolean)
			return;
		String msg = generateMessage(false, objs);
		if (msg != null) {
			err.println(msg);
		}
	}

	public void out(Object... objs) {
		if (!outBoolean)
			return;
		String msg = generateMessage(true, objs);
		if (msg != null) {
			out.println(msg);
		}
	}

	public void info(Object... objs) {
		if (!infoBoolean)
			return;
		String msg = generateMessage(false, objs);
		if (msg != null) {
			info.println(msg);
		}
	}

	private String generateMessage(boolean time, Object... objs) {
		if (objs.length == 0)
			return null;
		StringBuilder sb = new StringBuilder();
		if (time)
			sb.append(BpmnLog.timeNow());
		for (int i = 0; i < objs.length - 1; i++) {
			sb.append("[" + objs[i].toString() + "]");
		}
		while (sb.length() < 24) {
			sb.append(" ");
		}
		sb.append(objs[objs.length - 1].toString());

		return sb.toString();

	}

	private static MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		// no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}
}
