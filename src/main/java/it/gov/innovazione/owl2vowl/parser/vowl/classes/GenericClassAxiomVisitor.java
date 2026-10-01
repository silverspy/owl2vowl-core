package it.gov.innovazione.owl2vowl.parser.vowl.classes;

import it.gov.innovazione.owl2vowl.model.data.VowlData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * Class which handles the correct parsing for generic axioms which are not directly related to any class.
 * The referencing classes have to be retrieved here.
 */
public class GenericClassAxiomVisitor implements OWLObjectVisitor {

	private VowlData vowlData;
	private Logger logger = LogManager.getLogger(GenericClassAxiomVisitor.class);

	public GenericClassAxiomVisitor(VowlData vowlData) {
		this.vowlData = vowlData;
	}

	@Override
	public void doDefault(Object object) {
		logger.info("Unsupported generic class axiom: " + object);
	}

	@Override
	public void visit(OWLSubClassOfAxiom axiom) {
		if (!axiom.isGCI()) {
			logger.info("Generic axiom subclass is not anonym -> currently not supported: " + axiom);
			return;
		}

		if (axiom.getSuperClass().isAnonymous()) {
			// Retrieve concrete superclass to use it in the subclass — not yet implemented
			return;
		}

		axiom.getSubClass().accept(new OwlClassAxiomVisitor(vowlData, axiom.getSuperClass().asOWLClass()));
	}
}
