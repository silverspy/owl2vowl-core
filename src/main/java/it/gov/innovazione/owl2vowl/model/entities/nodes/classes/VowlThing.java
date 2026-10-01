/*
 * VowlThing.java
 *
 */

package it.gov.innovazione.owl2vowl.model.entities.nodes.classes;

import it.gov.innovazione.owl2vowl.constants.NodeType;
import it.gov.innovazione.owl2vowl.constants.Standard_Iris;

import it.gov.innovazione.owl2vowl.model.annotation.Annotation;
import it.gov.innovazione.owl2vowl.model.visitor.VowlElementVisitor;
import org.semanticweb.owlapi.model.IRI;

/**
 *
 */
public class VowlThing extends AbstractClass {
	public static final String THING_IRI = Standard_Iris.OWL_THING_CLASS_URI;
	public static final IRI GENERIC_THING_IRI = IRI.create(THING_IRI);

	protected VowlThing(IRI iri, String type) {
		super(iri, type);
	}

	public VowlThing(IRI iri) {
		super(iri, NodeType.TYPE_THING);
		getAnnotations().addLabel(new Annotation("label", "Thing"));
	}

	@Override
	public void accept(VowlElementVisitor visitor) {
		visitor.visit(this);
	}
}
