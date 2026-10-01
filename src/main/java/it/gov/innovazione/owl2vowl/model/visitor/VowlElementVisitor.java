package it.gov.innovazione.owl2vowl.model.visitor;

import it.gov.innovazione.owl2vowl.model.individuals.VowlIndividual;

/**
 *
 */
public interface VowlElementVisitor extends VowlClassVisitor, VowlPropertyVisitor {
	void visit(VowlIndividual vowlIndividual);
}