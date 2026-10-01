/*
 * VowlObjectProperty.java
 *
 */

package it.gov.innovazione.owl2vowl.model.entities.properties;

import it.gov.innovazione.owl2vowl.constants.PropertyType;
import it.gov.innovazione.owl2vowl.constants.VowlAttribute;
import it.gov.innovazione.owl2vowl.model.visitor.VowlElementVisitor;
import it.gov.innovazione.owl2vowl.model.visitor.VowlPropertyVisitor;
import org.semanticweb.owlapi.model.IRI;

/**
 *
 */
public class VowlObjectProperty extends AbstractProperty {
	public VowlObjectProperty(IRI iri) {
		super(iri, PropertyType.OBJECT);
		addAttribute(VowlAttribute.OBJECT);
	}

	@Override
	public void accept(VowlElementVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void accept(VowlPropertyVisitor visitor) {
		visitor.visit(this);
	}
}
