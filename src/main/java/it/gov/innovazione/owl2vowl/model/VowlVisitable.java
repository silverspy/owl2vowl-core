/*
 * VowlVisitable.java
 *
 */

package it.gov.innovazione.owl2vowl.model;

import it.gov.innovazione.owl2vowl.model.visitor.VowlElementVisitor;

/**
 *
 */
public interface VowlVisitable {

	void accept(VowlElementVisitor visitor);
}
