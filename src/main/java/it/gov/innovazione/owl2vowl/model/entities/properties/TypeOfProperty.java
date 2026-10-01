package it.gov.innovazione.owl2vowl.model.entities.properties;

import it.gov.innovazione.owl2vowl.constants.PropertyType;
import it.gov.innovazione.owl2vowl.model.annotation.Annotation;
import it.gov.innovazione.owl2vowl.model.visitor.VowlElementVisitor;
import it.gov.innovazione.owl2vowl.model.visitor.VowlPropertyVisitor;
import org.semanticweb.owlapi.model.IRI;

/**
 * Special property in VOWL which is colored in purple color.
 */
public class TypeOfProperty extends AbstractProperty {
	public TypeOfProperty(IRI iri) {
		super(iri, PropertyType.TYPEOF);
		getAnnotations().addLabel(new Annotation("label", "is a"));
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
