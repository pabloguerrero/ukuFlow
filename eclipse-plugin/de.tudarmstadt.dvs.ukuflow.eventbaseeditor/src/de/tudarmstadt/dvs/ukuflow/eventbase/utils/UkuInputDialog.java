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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;

import de.tudarmstadt.dvs.ukuflow.features.generic.RequestContainer;

/**
 * A simple input dialog for soliciting an input string from the user.
 * <p>
 * This concrete dialog class can be instantiated as is, or further subclassed
 * as required.
 * </p>
 */
public class UkuInputDialog extends Dialog {
	/**
	 * The title of the dialog.
	 */
	private String title;

	Map<Integer, RequestContainer> requests;
	/**
	 * The input validator, or <code>null</code> if none.
	 */
	private IInputValidator validator;

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
	private String errorMessage;

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
			String dialogMessage, String initialValue, IInputValidator validator) {
		super(parentShell);
		this.title = dialogTitle;

		// message = dialogMessage;
		// if (initialValue == null) {
		//	value = "";//$NON-NLS-1$
		// } else {
		// value = initialValue;
		// }
		this.validator = validator;
	}

	public UkuInputDialog(Shell parentShell, String dialogTitle,
			Map<Integer, RequestContainer> requests, IInputValidator validator) {
		super(parentShell);
		this.title = dialogTitle;
		this.validator = validator;
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
					CCombo combo = ((CCombo) tmp);
					req.result = combo.getItem(combo.getSelectionIndex());
					System.out.println("result >"+combo.getSelectionIndex()+">" + req.result);

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
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		// do this here because setting the text will set enablement on the ok
		// button

		// texts.get("").setFocus();
		/*
		for (Integer key : requests.keySet())
			if (requests.get(key) != null)
				if (requests.get(key).requests instanceof String) {

					((Text) texts.get(key)).setText(requests.get(key).requests
							.toString());
					((Text) texts.get(key)).selectAll();
				} else {
					List<?> l = (List<?>) requests.get(key).requests;
					String[] a = new String[l.size()];
					for (int i = 0; i < l.size(); i++) {
						a[i] = l.get(i).toString();
					}
					System.out.println(key);
					((CCombo) texts.get(key)).setItems(a);
					//((CCombo) texts.get(key)).select(1);
					texts.get(key).setFocus();
				}
	*/			
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected Control createDialogArea(Composite parent) {
		// create composite
		Composite composite = (Composite) super.createDialogArea(parent);
		// create message
		for (Integer message : requests.keySet()) {
			if (message != null) {
				Label label = new Label(composite, SWT.WRAP);
				label.setText(requests.get(message).title);
				GridData data = new GridData(GridData.GRAB_HORIZONTAL
						| GridData.GRAB_VERTICAL
						| GridData.HORIZONTAL_ALIGN_FILL
						| GridData.VERTICAL_ALIGN_CENTER);
				data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
				label.setLayoutData(data);
				label.setFont(parent.getFont());
			}
			Control text = null;
			if (requests.get(message).requests instanceof String) {
				text = new Text(composite, getInputTextStyle());
				text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
						| GridData.HORIZONTAL_ALIGN_FILL));
				((Text)text).setText(requests.get(message).requests.toString());
				((Text) text).addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						validateInput();
					}
				});
			} else  {
				CCombo combo = new CCombo(composite, getInputTextStyle());
				combo.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
						| GridData.HORIZONTAL_ALIGN_FILL));
				List l = (List<?>) requests.get(message).requests;
				System.out.println("debug "+message + ">"+l);
				String[] a = new String[l.size()];
				int selectIndex = 0;
				for (int j = 0; j < l.size(); j++) {
					a[j] = l.get(j).toString();
					if(a[j].equals(requests.get(message).currentValue))
						selectIndex = j;
				}
				combo.setItems(a);
				combo.select(selectIndex);
				text = combo;
			}
			texts.put(message, text);
		}
		errorMessageText = new Text(composite, SWT.READ_ONLY | SWT.WRAP);
		errorMessageText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL));
		errorMessageText.setBackground(errorMessageText.getDisplay()
				.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		// Set the error message text
		// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=66292
		setErrorMessage(errorMessage);

		applyDialogFont(composite);
		return composite;
	}

	/**
	 * Returns the error message label.
	 * 
	 * @return the error message label
	 * @deprecated use setErrorMessage(String) instead
	 */
	protected Label getErrorMessageLabel() {
		return null;
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
	 * Returns the validator.
	 * 
	 * @return the validator
	 */
	protected IInputValidator getValidator() {
		return validator;
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
		String errorMessage = null;
		if (validator != null) {
			// errorMessage = validator.isValid(text.getText());
		}
		for (Integer key : requests.keySet()) {
			RequestContainer req = requests.get(key);
			if (texts.get(key) instanceof Text
					&& requests.get(key).validator != null) {
				errorMessage = requests.get(key).validator
						.isValid(((Text) texts.get(key)).getText());
				setErrorMessage(errorMessage);
			}
		}
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
		this.errorMessage = errorMessage;
		if (errorMessageText != null && !errorMessageText.isDisposed()) {
			errorMessageText
					.setText(errorMessage == null ? " \n " : errorMessage); //$NON-NLS-1$
			// Disable the error message text control if there is no error, or
			// no error text (empty or whitespace only). Hide it also to avoid
			// color change.
			// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=130281
			boolean hasError = errorMessage != null
					&& (StringConverter.removeWhiteSpaces(errorMessage))
							.length() > 0;
			errorMessageText.setEnabled(hasError);
			errorMessageText.setVisible(hasError);
			errorMessageText.getParent().update();
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
		/*
		for (String key : texts.keySet()) {
			RequestContainer req = requests.get(key);
			// if (buttonId == IDialogConstants.OK_ID) {
			Control tmp = texts.get(key);
			if (tmp instanceof Text)
				req.result = ((Text) tmp).getText();
			// requests.put(key, request.get);//
			else if (tmp instanceof CCombo) {
				CCombo combo = ((CCombo) tmp);
				req.result = combo.getItem(combo.getSelectionIndex());
				System.out.println("result > " + req.result);

			} else {
				req.result = "";
			}
			requests.put(key, req);
		}*/
		return requests;
	}
}
