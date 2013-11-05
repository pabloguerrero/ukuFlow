/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.core.adapters;

import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.modeler.core.ModelHandler;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * @author Bob Brodt
 *
 */
public class FeatureDescriptor<T extends EObject> extends ObjectDescriptor<T> {

	protected EStructuralFeature feature;
	protected int multiline = 0; // -1 = false, +1 = true, 0 = unset
	protected Hashtable<String, Object> choiceOfValues; // for static lists
	
	public FeatureDescriptor(AdapterFactory adapterFactory, T object, EStructuralFeature feature) {
		super(adapterFactory, object);
		this.feature = feature;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel(Object context) {
		EObject object = adopt(context);
		if (label==null) {
			IItemPropertyDescriptor propertyDescriptor = getPropertyDescriptor(feature);
			if (propertyDescriptor != null)
				label = propertyDescriptor.getDisplayName(object);
			else {
				if (feature instanceof EReference)
					label = ModelUtil.getLabel(feature.getEType());
				else
					label = ModelUtil.toDisplayName(feature.getName());
			}
		}
		return label;
	}
	
	@Override
	public void setDisplayName(String text) {
		this.name = text;
	}
	
	@Override
	public String getDisplayName(Object context) {
		EObject object = adopt(context);
		if (name==null) {
			String t = null;
			// derive text from feature's value: default behavior is
			// to use the "name" attribute if there is one;
			// if not, use the "id" attribute;
			// fallback is to use the feature's toString()
			EObject o = null;
			EStructuralFeature f = null;
			if (feature!=null) {
				Object value = object.eGet(feature); 
				if (value instanceof EObject) {
					o = (EObject)value;
				}
				else if (value!=null)
					t = value.toString();
			}
			if (t==null && o!=null) {
				f = o.eClass().getEStructuralFeature("name");
				if (f!=null) {
					String name = (String)o.eGet(f);
					if (name!=null && !name.isEmpty())
						t = name;
				}
			}
			if (t==null && o!=null) {
				f = o.eClass().getEStructuralFeature("id");
				if (f!=null) {
					Object id = o.eGet(f);
					if (id!=null && !id.toString().isEmpty())
						t = id.toString();
				}
			}
			return t == null ? "" /*ModelUtil.getLabel(object)*/ : t;
		}
		return name == null ? "" : name;
	}

	public void setChoiceOfValues(Hashtable<String, Object> choiceOfValues) {
		this.choiceOfValues = choiceOfValues;
	}

	/**
	 * Convenience method to set choice of values from an object list.
	 * @param values
	 */
	public void setChoiceOfValues(Collection values) {
		if (values!=null) {
			choiceOfValues = new Hashtable<String,Object>();
			Iterator iter = values.iterator();
			while (iter.hasNext()) {
				Object value = iter.next();
				if (value!=null) {
					String text = getChoiceString(value);
					while (choiceOfValues.containsKey(text))
						text += " ";
					choiceOfValues.put(text, value);
				}
			}
		}
	}
	
	/**
	 * Returns a list of name-value pairs for display in a combo box or selection list.
	 * The String is what gets displayed in the selection list, while the Object is
	 * implementation-specific: this can be a reference to an element, string or whatever.
	 * The implementation is responsible for interpreting this value by overriding the
	 * setValue() method, and must update the object feature accordingly.
	 * 
	 * @param context
	 * @return
	 */
	public Hashtable<String, Object> getChoiceOfValues(Object context) {
		EObject object = context instanceof EObject ? (EObject)context : this.object;
		if (choiceOfValues==null) {
			List<String> names = null;
			Collection values = null;
			
			try {
				IItemPropertyDescriptor propertyDescriptor = getPropertyDescriptor(feature);
				if (propertyDescriptor!=null) {
					values = propertyDescriptor.getChoiceOfValues(object);
				}
			}
			catch (Exception e) {
				// ignore exceptions if we fail to resolve proxies;
				// e.g. and instance of a DynamicEObjectImpl with a bogus
				// URI is used for ItemDefinition.structureRef
				// fallback is to do our own search
			}

			if (values==null)
				values = ModelUtil.getAllReachableObjects(object, feature);
			
			if (values!=null) {
				Hashtable<String,Object> choices = new Hashtable<String,Object>();
				Iterator iter = values.iterator();
				while (iter.hasNext()) {
					Object value = iter.next();
					if (value!=null) {
						String text = getChoiceString(value);
						if (text==null)
							text = "";
						while (choices.containsKey(text))
							text += " ";
						choices.put(text, value);
					}
				}
				return choices;
			}
		}
		return choiceOfValues;
	}
	
	// copied from PropertyUtil in UI plugin
	public String getChoiceString(Object value) {
		if (value instanceof EObject) {
			EObject eObject = (EObject)value;
			ExtendedPropertiesAdapter adapter = ExtendedPropertiesAdapter.adapt(eObject);
			if (adapter!=null)
				return adapter.getObjectDescriptor().getDisplayName(eObject);
			return ModelUtil.toDisplayName( eObject.eClass().getName() );
		}
		return value.toString();
	}

	public void setMultiLine(boolean multiline) {
		this.multiline = multiline ? 1 : -1;
	}
	
	public boolean isMultiLine(Object context) {
		EObject object = adopt(context);
		if (multiline==0) {
			IItemPropertyDescriptor propertyDescriptor = getPropertyDescriptor(feature);
			if (propertyDescriptor!=null)
				multiline = propertyDescriptor.isMultiLine(object) ? 1 : -1;
		}
		return multiline == 1;
	}
	
	public EObject createFeature(Object context) {
		EObject object = adopt(context);
		if (context instanceof EClass)
			return createFeature(object, (EClass)context);
		return createFeature(context, null);
	}		
	
	public EObject createFeature(Object context, EClass eclass) {
		EObject object = adopt(context);
		return createFeature(object.eResource(),context,eclass);
	}
	
	public EObject createFeature(Resource resource, Object context, EClass eclass) {
		T object = adopt(context);
		EObject newFeature = null;
		try {
			if (context instanceof EClass)
				eclass = (EClass)context;
			if (eclass==null)
				eclass = (EClass)feature.getEType();
			
			ExtendedPropertiesAdapter adapter = ExtendedPropertiesAdapter.adapt(eclass);
			if (adapter!=null) {
				if (resource==null)
					resource = object.eResource();
				newFeature = adapter.getObjectDescriptor().createObject(resource, eclass);
				// can we set the new object into the parent object?
				if (newFeature.eContainer()!=null || // the new object is contained somewhere
					feature instanceof EAttribute || // the new object is an attribute
					// the feature is a containment reference which means the this.object owns it
					(feature instanceof EReference && ((EReference)feature).isContainment()))
				{
					if (object.eGet(feature) instanceof List) {
						((List)object.eGet(feature)).add(newFeature);
					}
					else
						object.eSet(feature, newFeature);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newFeature;
	}

	// NOTE: getValue() and setValue() must be symmetrical; that is, setValue()
	// must be able to handle the object type returned by getValue(), although
	// setValue() may also know how to convert from other types, e.g. String,
	// Integer, etc.
	public Object getValue() {
		return getValue(object);
	}
	
	public Object getValue(Object context) {
		EObject object = adopt(context);
		return object.eGet(feature);
	}
	
	public void setValue(Object value) {
		setValue(object,value);
	}
	
	public void setValue(Object context, final Object value) {
		final T object = adopt(context);
		
		if (object.eGet(feature) instanceof EObjectEList) {
			// the feature is a reference list - user must have meant to insert
			// the value into this list...
			final EObjectEList list = (EObjectEList)object.eGet(feature);
			TransactionalEditingDomain editingDomain = getEditingDomain(object);
			if (editingDomain == null) {
				list.add(value);
				insertRootElementIfNeeded(value);
			} else {
				editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
					@Override
					protected void doExecute() {
						list.add(value);
						insertRootElementIfNeeded(value);
					}
				});
			}
		}
		else {
			IItemPropertyDescriptor propertyDescriptor = getPropertyDescriptor(object, feature);
			if (propertyDescriptor != null) {
				propertyDescriptor.setPropertyValue(object, value);
			}
			else {
				TransactionalEditingDomain editingDomain = getEditingDomain(object);
				if (editingDomain == null) {
					object.eSet(feature, value);
					insertRootElementIfNeeded(value);
				} else {
					editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
						@Override
						protected void doExecute() {
							object.eSet(feature, value);
							insertRootElementIfNeeded(value);
						}
					});
				}
			}
		}
	}

	public void unset() {
		unset(object);
	}
	
	public void unset(Object context) {
		final T object = adopt(context);
		setValue(object, feature.getDefaultValue());
	}
	
	private void insertRootElementIfNeeded(Object value) {
		if (value instanceof RootElement && ((RootElement)value).eContainer()==null) {
			// stuff all root elements into Definitions.rootElements
			final Definitions definitions = ModelUtil.getDefinitions(object);
			if (definitions!=null) {
				definitions.getRootElements().add((RootElement)value);
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		Object thisValue = object.eGet(feature);
		
		if (thisValue==null && obj==null)
			return true;
		
		if (thisValue instanceof EObject && obj instanceof EObject) {
			return compare((EObject)thisValue, (EObject)obj, false);
		}
		
		if (thisValue!=null && obj!=null)
			return thisValue.equals(obj);
		
		return false;
	}
	
	@Override
	public boolean similar(Object obj) {
		Object thisValue = object.eGet(feature);
		
		if (thisValue==null && obj==null)
			return true;
		
		if (thisValue instanceof EObject && obj instanceof EObject) {
			return compare((EObject)thisValue, (EObject)obj, true);
		}
		
		if (thisValue!=null && obj!=null)
			return thisValue.equals(obj);
		
		return false;
	}

}
