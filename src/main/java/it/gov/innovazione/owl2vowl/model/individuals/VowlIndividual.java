package it.gov.innovazione.owl2vowl.model.individuals;

import it.gov.innovazione.owl2vowl.model.AbstractVowlObject;
import it.gov.innovazione.owl2vowl.model.visitor.VowlElementVisitor;
import org.semanticweb.owlapi.model.IRI;

/**
 *
 */
public class VowlIndividual extends AbstractVowlObject {
	public VowlIndividual(IRI iri) {
		super(iri);
	}

	@Override
	public void accept(VowlElementVisitor visitor) {
		visitor.visit(this);
	}
}
