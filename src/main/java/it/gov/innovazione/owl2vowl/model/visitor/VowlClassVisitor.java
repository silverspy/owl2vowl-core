/*
 * VowlClassVisitor.java
 *
 */

package it.gov.innovazione.owl2vowl.model.visitor;

import it.gov.innovazione.owl2vowl.model.entities.nodes.classes.VowlClass;
import it.gov.innovazione.owl2vowl.model.entities.nodes.classes.VowlThing;
import it.gov.innovazione.owl2vowl.model.entities.nodes.datatypes.VowlDatatype;
import it.gov.innovazione.owl2vowl.model.entities.nodes.datatypes.VowlLiteral;

/**
 *
 */
public interface VowlClassVisitor {
	void visit(VowlThing vowlThing);

	void visit(VowlClass vowlClass);

	void visit(VowlLiteral vowlLiteral);

	void visit(VowlDatatype vowlDatatype);
}
