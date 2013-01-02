/*
 * Copyright (c) 2011, Hien Quoc Dang, TU Darmstadt, dangquochien@gmail.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER(s) AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT HOLDER(s) OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */

package de.tudarmstadt.dvs.ukuflow.xml.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tudarmstadt.dvs.ukuflow.tools.exception.UnspecifiedGatewayException;
import de.tudarmstadt.dvs.ukuflow.xml.TypeClassifier;

public abstract class UkuGateway extends UkuElement {

	public class UkuEventGateway {

	}
	
	/** direction of ukuGateway specified by user <br /> 
	 *  1 = converging <br />
	 *  2 = diverging <br />
	 *  3 = mixed <br />
	 *  0 = unspecified <br />
	 */
	protected int direction = 0;
	protected String directionName;
	
	/* xml tag name of gateway */
	protected String typeGateway;
	
	/* default sequence flow id */
	private String defaultGway = null;	
	
	private boolean skip = false;

	public  UkuGateway(String id) {
		super(id);
	}

	public void setSkip() {
		skip = true;
	}

	public boolean isSkipped() {
		return skip;
	}

	public List<UkuGateway> getPreviousGateways() {
		Set<UkuGateway> result = new HashSet<UkuGateway>();
		for (UkuEntity flow : incomingEntities) {
			UkuEntity tmp = flow;
			while (!(tmp instanceof UkuGateway)
					|| ((UkuGateway) tmp).isSkipped()) {
				if (tmp instanceof UkuSequenceFlow) {
					tmp = ((UkuSequenceFlow) tmp).getSourceEntity();
				} else if (tmp instanceof UkuExecuteTask) {
					tmp = ((UkuExecuteTask) tmp).getIncomingEntity().get(0);
				} else if (tmp instanceof UkuEvent) {
					break;
				} else if (tmp instanceof UkuGateway
						&& ((UkuGateway) tmp).isSkipped()) {
					tmp = ((UkuGateway) tmp).getIncomingEntity().get(0);
				}
			}
			if (tmp instanceof UkuGateway) {
				result.add((UkuGateway) tmp);
			}
		}
		return new LinkedList<UkuGateway>(result);
	}

	public List<UkuGateway> getNextGateways() {
		Set<UkuGateway> result = new HashSet<UkuGateway>();
		for (UkuEntity flow : outgoingEntities) {
			UkuEntity tmp = flow;
			while (!(tmp instanceof UkuGateway)||((UkuGateway)tmp).isSkipped()) {
				System.out.println("getNextGateways() is looping");
				if (tmp instanceof UkuSequenceFlow) {
					tmp = ((UkuSequenceFlow) tmp).getTargetEntity();
				} else if (tmp instanceof UkuExecuteTask) {
					tmp = ((UkuExecuteTask) tmp).getOutgoingEntity().get(0);
				} else if (tmp instanceof UkuEvent) {
					break;
				} else if (tmp instanceof UkuGateway
						&& ((UkuGateway) tmp).isSkipped()) {
					tmp = ((UkuGateway) tmp).getOutgoingEntity().get(0);
				}
			}
			if (tmp instanceof UkuGateway) {
				result.add((UkuGateway) tmp);
			}
		}
		return new LinkedList<UkuGateway>(result);
	}

	/*
	 * (non-Javadoc) 0 x/x 0 1/>1 >1/1 >1/>1
	 * 
	 * @see
	 * de.tudarmstadt.dvs.ukuflow.xml.entity.UkuElement#setReference(java.util
	 * .Map)
	 */
	public void setElementType(String s) {
		typeGateway = s;
	}

	public void setDefaultGway(String d) {
		defaultGway = d;
	}
	/**
	 * gateway type specified by user
	 * @return
	 */
	public String getDefaultGway() {
		return defaultGway;
	}

	public String getElementType() {
		return typeGateway;
	}

	@Override
	public void setReference(Map<String, UkuEntity> ref) {
		super.setReference(ref);
		if (defaultGway != null) {
			UkuSequenceFlow defGateway = (UkuSequenceFlow) ref.get(defaultGway);
			defGateway.setDefaultGateway();
		}
	}
	
	public void setDirection(String d) {
		if (d != null)
			directionName = d;
		else
			directionName = "Unspecified";

		if (directionName.equalsIgnoreCase("Diverging")) {
			direction = 2;
		} else if (directionName.equalsIgnoreCase("Converging")) {
			direction = 1;
		} else if (directionName.equalsIgnoreCase("Mixed")) {
			direction = 3;
		} else if (directionName.equalsIgnoreCase("Unspecified")
				|| directionName == null) {
			direction = 0;
		} else { // ""
			direction = 0;
			addWarningMessage("the type of gateway is not specified");
		}
	}

	/**
	 * 
	 * 0 : still unknown <br/>
	 * 1 : converging (n-1)<br/>
	 * 2 : diverging (1-n)<br/>
	 * 3 : mixed (n-n)<br/>
	 * 
	 * @return type of the gateway
	 * 
	 */
	/*
	public int getType() {
		if (direction == 0)
			return calculateType();
		else
			return direction;
	}*/

	/**
	 * automatically find out which type of Gateway is this : <li>Mixed</li> <li>
	 * Converging</li><li>Deverging</li><li>Unspecified</li>
	 */
	/*
	public void selfValidate() {
		String tmpName = "";
		if (getOutgoingID().size() > 1 && getIncomingID().size() > 1) {
			addWarningMessage("a mixed gateway");
			setType(3);
			tmpName = "Mixed";
		} else if (getOutgoingID().size() == 0 || getIncomingID().size() == 0) {
			if (getOutgoingID().size() == 0)
				addWarningMessage("no outgoing sequence flow");
			if (getIncomingID().size() == 0)
				addWarningMessage("no incoming sequence flow");
			setType(-1);
			return;
		} else if (getIncomingID().size() == 1 && getOutgoingID().size() > 1) {
			setType(2);
			tmpName = "Diverging";
		} else if (getIncomingID().size() > 1 && getOutgoingID().size() == 1) {
			tmpName = "Converging";
			setType(1);
		}
		if (getIncomingID().size() == 1 && getOutgoingID().size() == 1) {
			tmpName = "UnKnown";
			setType(0);
		}
		if (getType() != direction) {
			addWarningMessage("was specified as '" + directionName
					+ "', but it was found as a '" + tmpName + "' gateway");
		}
		if (type == direction && direction == 0) {
			addErrorMessage("Please specify the direction of gateway (Converging,Deverging or Mixed)");
		}
		int tp = 0;
		try {
			tp = TypeClassifier.getInstance().getGatewayType(this);
			//ukuGatewayType = tp;
		} catch (UnspecifiedGatewayException e) {
			addErrorMessage(e.getMessage());
			return;
		}
	}
	*/
	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	public abstract int getUkuType();

	public UkuGateway clone(String newID) {
		UkuGateway ob = null;

		try {
			ob = (UkuGateway) getClass().getConstructor(String.class)
					.newInstance(newID);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		if (defaultGway != null)
			ob.defaultGway = defaultGway.trim();
		ob.direction = direction;
		if (directionName != null)
			ob.directionName = directionName.trim();
		// ob.type = type;
		if (typeGateway != null)
			ob.typeGateway = typeGateway.trim();
		return ob;
	}
	/**
	 * 
	 * @return 1 if converging, 2 if diverging, 3 if mixed and 0 if cannot be calculated
	 */
	public int calculateType(){		
		if(incomingEntities.size()>1 && outgoingEntities.size()==1)
			return 1;
		if(incomingEntities.size()==1 && outgoingEntities.size()>1)
			return 2;
		if(incomingEntities.size()>1 && outgoingEntities.size()>1)
			return 3;
		return 0;
	}

	public boolean equals(Object o) {
		if (o instanceof UkuGateway)
			return ((UkuGateway) o).id.equals(id);
		return false;

	}
}
