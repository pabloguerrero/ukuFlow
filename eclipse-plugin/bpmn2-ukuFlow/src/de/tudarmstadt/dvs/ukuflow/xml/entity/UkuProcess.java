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

import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.dvs.ukuflow.script.UkuConstants;

/**
 * 
 * @author Hien Quoc Dang
 * 
 */

public class UkuProcess implements VisitableElement {

	private List<UkuEntity> entities;
	public UkuEvent startEvent = null;
	// private HashMap<String,UkuEntity> ref = new HashMap<String, UkuEntity>();

	public List<UkuScope> scopes = new LinkedList<UkuScope>();
	/**
	 * unique id
	 */
	public String id;

	public String name;

	public UkuProcess(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public void addEntities(List<UkuEntity> newEntities) {
		for (UkuEntity e : newEntities) {
			addEntity(e);
		}
	}

	public void addEntity(UkuEntity newEntity) {
		if (newEntity instanceof UkuScope) {
			if (scopes == null)
				scopes = new LinkedList<UkuScope>();
			scopes.add((UkuScope) newEntity);
			return;
		} else if (entities.contains(newEntity)) {
			System.err.println("double element " + newEntity.getID());
			return;
		}
		if (startEvent == null && newEntity instanceof UkuEvent
				&& ((UkuEvent) newEntity).getType() == UkuConstants.START_EVENT) {
			startEvent = (UkuEvent) newEntity;
		}
		entities.add(newEntity);
	}

	public void removeEntity(UkuEntity e) {
		entities.remove(e);
		scopes.remove(e);
	}

	public void removeEntities(List<UkuEntity> es) {
		entities.removeAll(es);
		scopes.removeAll(es);
	}

	public void setEntities(List<UkuEntity> entities) {
		this.entities = new LinkedList<UkuEntity>();
		this.scopes = new LinkedList<UkuScope>();
		for (UkuEntity e : entities) {
			if (e instanceof UkuScope)
				scopes.add((UkuScope) e);
			else
				addEntity(e);
		}

	}

	public void setScopes(List<UkuScope> scopes) {
		this.scopes = scopes;
	}

	public void addScope(UkuScope scope) {
		this.scopes.add(scope);
	}

	public List<UkuElement> getElements() {
		final List<UkuElement> elements = new LinkedList<UkuElement>();
		for (UkuEntity e : entities) {
			// ref.put(e.getID(), e);
			if (e instanceof UkuElement) {
				elements.add((UkuElement) e);
			}
		}
		return elements;
	}

	public List<UkuEntity> getEntities() {
		return new LinkedList<UkuEntity>(entities);
	}

	public byte getNumberOfScope() {
		if (scopes != null)
			return (byte) scopes.size();
		return 0;
	}

	public byte getWorkflowID() {
		if (name == null)
			return (byte) (Math.abs(id.hashCode()) % 256);
		return (byte) (Math.abs(name.hashCode()) % 256);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		visitor.visit(this);
	}

	public List<UkuScope> getScope() {
		return scopes;
	}

}
