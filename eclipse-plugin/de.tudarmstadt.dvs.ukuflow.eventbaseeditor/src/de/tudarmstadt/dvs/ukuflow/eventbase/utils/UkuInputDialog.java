package de.tudarmstadt.dvs.ukuflow.eventbase.utils;

/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.tudarmstadt.dvs.ukuflow.eventmodel.eventbase.EventbasePackage;
import de.tudarmstadt.dvs.ukuflow.features.generic.RequestContainer;
import de.tudarmstadt.dvs.ukuflow.tools.debugger.BpmnLog;

/**
 * A simple input dialog for soliciting an input string from the user.
 * <p>
 * This concrete dialog class can be instantiated as is, or further subclassed
 * as required.
 * </p>
 */
public class UkuInputDialog extends Dialog {
	BpmnLog log = BpmnLog.getInstance(this.getClass().getSimpleName());
	/**
	 * The title of the dialog.
	 */
	private String title;

	Map<Integer, RequestContainer> requests;

	/**
	 * Ok button widget.
	 */
	private Button okButton;

	/**
	 * Input text widget.
	 */
	private Map<Integer, Control> texts;

	/**
	 * Error message label widget.
	 */
	private Text errorMessageText;

	/**
	 * Error message string.
	 */
	// private String errorMessage;

	/**
	 * Creates an input dialog with OK and Cancel buttons. Note that the dialog
	 * will have no visual representation (no widgets) until it is told to open.
	 * <p>
	 * Note that the <code>open</code> method blocks for input dialogs.
	 * </p>
	 * 
	 * @param parentShell
	 *            the parent shell, or <code>null</code> to create a top-level
	 *            shell
	 * @param dialogTitle
	 *            the dialog title, or <code>null</code> if none
	 * @param dialogMessage
	 *            the dialog message, or <code>null</code> if none
	 * @param initialValue
	 *            the initial input value, or <code>null</code> if none
	 *            (equivalent to the empty string)
	 * @param validator
	 *            an input validator, or <code>null</code> if none
	 */
	public UkuInputDialog(Shell parentShell, String dialogTitle,
			String dialogMessage, String initialValue) {
		super(parentShell);
		this.title = dialogTitle;

		// message = dialogMessage;
		// if (initialValue == null) {
		//	value = "";//$NON-NLS-1$
		// } else {
		// value = initialValue;
		// }
	}

	public UkuInputDialog(Shell parentShell, String dialogTitle,
			Map<Integer, RequestContainer> requests) {
		super(parentShell);
		//setShellStyle(getShellStyle()|SWT.RESIZE);
		this.title = dialogTitle;
		this.requests = requests;
		texts = new HashMap<Integer, Control>();		
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	@Override
	protected void buttonPressed(int buttonId) {
		for (Integer key : texts.keySet()) {
			RequestContainer req = requests.get(key);
			if (buttonId == IDialogConstants.OK_ID) {
				Control tmp = texts.get(key);
				if (tmp instanceof Text)
					req.result = ((Text) tmp).getText();
				// requests.put(key, request.get);//
				if (tmp instanceof CCombo) {
					if (combo_function != null)
						function_changed();
					CCombo combo = ((CCombo) tmp);
					req.result = combo.getItem(combo.getSelectionIndex());
					log.debug("result >" + combo.getSelectionIndex() + ">"
							+ req.result);

				}
			} else {
				req.result = "";
			}
			requests.put(key, req);
		}
		super.buttonPressed(buttonId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets
	 * .Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (title != null) {
			shell.setText(title);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse
	 * .swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		okButton.setEnabled(true);
		if (errorMessageText.getText().length() > 1) {
			System.out.println("set Button enabled = false:'"
					+ errorMessageText.getText() + "'");
			okButton.setEnabled(false);
		}

	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected Control createDialogArea(Composite parent) {
		// create composite
		Composite composite = (Composite) super.createDialogArea(parent);

		GridData data = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);		
		data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
		// create message
		for (Integer message : requests.keySet()) {
			// ignore a special one
			if (message == -1)
				continue;
			if (message != null) {
				Label label = new Label(composite, SWT.READ_ONLY | SWT.WRAP);
				label.setText(requests.get(message).title);

				label.setLayoutData(data);
				label.setFont(parent.getFont());
			}
			Control text = null;
			log.debug("requests of " + message + "["+requests.get(message).title+"] : "
					+ requests.get(message).requests);

			if (requests.get(message).requests == null){
				// to deal with processes which are created using older version
				requests.get(message).requests = "";
			}
			
			if (requests.get(message).requests instanceof String) {
				GridData gdata= new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL);
				if("Filter's constraints".equals(requests.get(message).title)){
					text = new Text(composite, SWT.WRAP|SWT.MULTI | SWT.BORDER | SWT.V_SCROLL|SWT.RESIZE);
					gdata.heightHint = 100;
					gdata.widthHint = 200;
				}
				else 
					text = new Text(composite, getInputTextStyle());
				text.setLayoutData(gdata);
				((Text) text)
						.setText(requests.get(message).requests.toString());
				((Text) text).addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						validateInput();
					}
				});
			} else {
				CCombo combo = new CCombo(composite, getInputTextStyle());
				combo.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
						| GridData.HORIZONTAL_ALIGN_FILL));
				// System.out.println("map: "+requests);
				// System.out.println("mesg:"+message +
				// "/ req:"+requests.get(message).requests);
				List l = (List<?>) requests.get(message).requests;
				String[] a = new String[l.size()];
				int selectIndex = 0;
				for (int j = 0; j < l.size(); j++) {
					a[j] = l.get(j).toString();
					if (a[j].equals(requests.get(message).currentValue))
						selectIndex = j;
				}
				combo.setItems(a);
				combo.select(selectIndex);
				combo.setEditable(false);
				if (message == EventbasePackage.EG_DISTRIBUTION__FUNCTION) {
					combo_function = combo;
					init_function_parameters(composite, parent);

					combo.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event event) {
							UkuInputDialog.this.function_changed();
						}
					});
				}
				text = combo;
			}

			texts.put(message, text);
		}

		// Set the error message text
		// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=66292
		// setErrorMessage(errorMessage);
		// create error message erea:
		errorMessageText = new Text(composite, SWT.READ_ONLY | SWT.WRAP);

		errorMessageText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL));
		errorMessageText.setBackground(errorMessageText.getDisplay()
				.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

		applyDialogFont(composite);
		return composite;
	}

	CCombo combo_function = null;
	int choosen = -1;
	Map<Integer, Tupel> parameters = new HashMap<Integer, Tupel>();

	private void init_function_parameters(Composite composite, Composite parent) {
		String val = requests.get(-1).currentValue.trim();
		String[] vals = val.split("[a-z ]+");

		Composite subComp = new Composite(composite, SWT.NONE);
		subComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout(7, false);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd.horizontalSpan = 7;
		subComp.setLayoutData(gd);
		subComp.setLayout(layout);
		// label1:
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		Label lb = new Label(subComp, SWT.WRAP);
		lb.setText("Parameters: ");
		lb.setLayoutData(data);
		subComp.setVisible(true);
		Label label;
		label = createLabel(" mean", subComp);
		Text t = new Text(subComp, getInputTextStyle());
		t.setText(vals[1]);
		GridData textGrid = new GridData(GridData.FILL_HORIZONTAL
				| GridData.GRAB_HORIZONTAL);
		t.setLayoutData(textGrid);
		parameters.put(1, new Tupel(label, t));

		label = createLabel(" variance", subComp);
		t = new Text(subComp, getInputTextStyle());
		t.setLayoutData(textGrid);
		if (vals.length > 2)
			t.setText(vals[2]);
		parameters.put(2, new Tupel(label, t));

		label = createLabel("  peak", subComp);

		t = new Text(subComp, getInputTextStyle());
		t.setLayoutData(textGrid);
		if (vals.length > 3)
			t.setText(vals[3]);
		parameters.put(3, new Tupel(label, t));

		function_changed();
	}

	private Label createLabel(String label, Composite subComp) {
		Label result = new Label(subComp, SWT.WRAP);
		result.setText(label);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		// data.widthHint = 450;
		result.setLayoutData(data);
		return result;
	}

	public void function_changed() {
		if (combo_function == null)
			return;
		log.info("changed: " + combo_function.getSelectionIndex() + "/"
				+ combo_function.getText());
		choosen = combo_function.getSelectionIndex();
		String params = "";

		switch (combo_function.getSelectionIndex()) {
		case 0: // Gausian
			parameters.get(1).setText(" mean");
			for (int i = 1; i < 4; i++) {
				Tupel t = parameters.get(i);
				// if (!t.lb.isVisible())
				t.setVisible(true);
				params += " "
						+ t.lb.getText().trim().replace("mean", "m")
								.replace("variance", "v").replace("peak", "a")
						+ t.getValue();
			}
			break;
		case 1: // Chi_square
			parameters.get(2).setVisible(false);
			parameters.get(3).setVisible(false);
			parameters.get(1).setText(" degree");
			params = " k" + parameters.get(1).getValue();
			break;
		case 2: // Pareto
			parameters.get(2).setVisible(false);
			parameters.get(3).setVisible(false);
			parameters.get(1).setText(" shape");
			params = " a" + parameters.get(1).getValue();
			break;
		}
		validateInput();
		combo_function.getParent().update();
		RequestContainer rc = new RequestContainer(null, null, null);
		rc.result = params;
		requests.put(-1, rc);
	}

	/**
	 * Returns the ok button.
	 * 
	 * @return the ok button
	 */
	protected Button getOkButton() {
		return okButton;
	}

	/**
	 * Returns the text area.
	 * 
	 * @return the text area
	 */
	protected Map<Integer, Control> getTexts() {
		return texts;
	}

	/**
	 * Validates the input.
	 * <p>
	 * The default implementation of this framework method delegates the request
	 * to the supplied input validator object; if it finds the input invalid,
	 * the error message is displayed in the dialog's message line. This hook
	 * method is called whenever the text changes in the input field.
	 * </p>
	 */
	protected void validateInput() {
		String errorMessage = "";
		for (Integer key : requests.keySet()) {
			if (texts.get(key) instanceof Text
					&& requests.get(key).validator != null) {
				String tmp = requests.get(key).validator.isValid(((Text) texts
						.get(key)).getText());
				if (tmp != null) {
					String meta = requests.get(key).title;
					meta = meta.split("[(]")[0];
					tmp = meta + ":  " + tmp;
					errorMessage += tmp + "\n";

				}
			}
		}
		if (combo_function != null) {
			for (int i = 1; i < 4; i++) {
				try {
					Integer.parseInt(parameters.get(i).getValue());
				} catch (Exception e) {
					errorMessage += "parameters must be numbers";
					break;
				}
				if (combo_function.getSelectionIndex() != 0)
					break;
			}
		}
		setErrorMessage(errorMessage.equals("") ? null : errorMessage);
	}

	/**
	 * Sets or clears the error message. If not <code>null</code>, the OK button
	 * is disabled.
	 * 
	 * @param errorMessage
	 *            the error message, or <code>null</code> to clear
	 * @since 3.0
	 */
	public void setErrorMessage(String errorMessage) {
		log.debug("set error message: !" + errorMessage + "!");
		if (errorMessageText != null && !errorMessageText.isDisposed()) {
			errorMessageText
					.setText(errorMessage == null ? "\n" : errorMessage);
			// Disable the error message text control if there is no error, or
			// no error text (empty or whitespace only). Hide it also to avoid
			// color change.
			// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=130281
			boolean hasError = errorMessage != null
					&& (StringConverter.removeWhiteSpaces(errorMessage))
							.length() > 0;
			if (errorMessage != null)
				errorMessageText.setSize(errorMessageText.getSize().x, 13 * errorMessage.split("\n").length);			
			errorMessageText.setEnabled(hasError);
			errorMessageText.setVisible(hasError);
			errorMessageText.getParent().update();
			errorMessageText.setForeground(new Color(null, 255, 0, 0));

			// Access the ok button by id, in case clients have overridden
			// button creation.
			// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=113643
			Control button = getButton(IDialogConstants.OK_ID);
			if (button != null) {
				button.setEnabled(errorMessage == null);
			}
		}
	}

	/**
	 * Returns the style bits that should be used for the input text field.
	 * Defaults to a single line entry. Subclasses may override.
	 * 
	 * @return the integer style bits that should be used when creating the
	 *         input text
	 * 
	 * @since 3.4
	 */
	protected int getInputTextStyle() {
		return SWT.SINGLE | SWT.BORDER;
	}

	public Map<Integer, RequestContainer> getValues() {
		return requests;
	}

	class Tupel {
		private Label lb;
		private Text text;

		public Tupel(Label l, Text t) {
			this.lb = l;
			this.text = t;
			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					validateInput();
				}
			});
		}

		public void setText(String newText) {
			lb.setText(newText);
			lb.setEnabled(true);
		}

		public void setValue(Serializable value) {
			text.setText(value.toString());
		}

		public String getValue() {
			return text.getText();
		}

		public void setVisible(boolean visible) {
			lb.setVisible(visible);
			text.setVisible(visible);
		}
	}

}
