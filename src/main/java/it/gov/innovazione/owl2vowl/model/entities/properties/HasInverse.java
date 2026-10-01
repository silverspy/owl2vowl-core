package it.gov.innovazione.owl2vowl.model.entities.properties;

import org.semanticweb.owlapi.model.IRI;

/**
 * @author Eduard
 */
public interface HasInverse {
	void addInverse(IRI iri);

	// Note: OWL allows multiple inverses but only one is tracked here
	IRI getInverse();
}
