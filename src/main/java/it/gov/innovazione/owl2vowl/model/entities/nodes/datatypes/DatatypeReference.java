/*
 * DatatypeReference.java
 *
 */

package it.gov.innovazione.owl2vowl.model.entities.nodes.datatypes;

import org.semanticweb.owlapi.model.IRI;

/**
 *
 */
public class DatatypeReference extends VowlDatatype {
	protected IRI referencedIri;

	public DatatypeReference(IRI iri, IRI referencedIri) {
		super(iri);
		this.referencedIri = referencedIri;
	}

	public IRI getReferencedIri() {
		return referencedIri;
	}
}
