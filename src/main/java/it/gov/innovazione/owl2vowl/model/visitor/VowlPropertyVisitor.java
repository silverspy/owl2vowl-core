/*
 * VowlPropertyVisitor.java
 *
 */

package it.gov.innovazione.owl2vowl.model.visitor;

import it.gov.innovazione.owl2vowl.model.entities.properties.TypeOfProperty;
import it.gov.innovazione.owl2vowl.model.entities.properties.VowlDatatypeProperty;
import it.gov.innovazione.owl2vowl.model.entities.properties.VowlObjectProperty;

/**
 *
 */
public interface VowlPropertyVisitor {
	void visit(VowlObjectProperty vowlObjectProperty);

	void visit(VowlDatatypeProperty vowlDatatypeProperty);

	void visit(TypeOfProperty typeOfProperty);
}
