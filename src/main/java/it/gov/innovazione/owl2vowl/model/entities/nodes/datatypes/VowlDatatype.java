/*
 * VowlDatatype.java
 *
 */

package it.gov.innovazione.owl2vowl.model.entities.nodes.datatypes;

import it.gov.innovazione.owl2vowl.constants.NodeType;
import it.gov.innovazione.owl2vowl.model.visitor.VowlElementVisitor;
import org.semanticweb.owlapi.model.IRI;

/**
 *
 */
public class VowlDatatype extends AbstractDatatype {
	public VowlDatatype(IRI iri) {
		super(iri, NodeType.TYPE_DATATYPE);
	}

	@Override
	public void accept(VowlElementVisitor visitor) {
		visitor.visit(this);
	}
}
