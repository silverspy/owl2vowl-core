package it.gov.innovazione.owl2vowl.model.entities.nodes.datatypes;

import it.gov.innovazione.owl2vowl.model.entities.nodes.AbstractNode;
import org.semanticweb.owlapi.model.IRI;

/**
 *
 */
public abstract class AbstractDatatype extends AbstractNode {

	protected AbstractDatatype(IRI iri, String type) {
		super(iri, type);
	}
}
