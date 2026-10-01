package it.gov.innovazione.owl2vowl.parser.owlapi;

import it.gov.innovazione.owl2vowl.model.data.VowlData;
import it.gov.innovazione.owl2vowl.model.entities.nodes.classes.AbstractClass;
import it.gov.innovazione.owl2vowl.model.entities.nodes.classes.VowlClass;
import it.gov.innovazione.owl2vowl.model.entities.nodes.datatypes.VowlDatatype;
import it.gov.innovazione.owl2vowl.model.entities.nodes.datatypes.VowlLiteral;
import it.gov.innovazione.owl2vowl.model.entities.properties.VowlDatatypeProperty;
import it.gov.innovazione.owl2vowl.model.entities.properties.VowlObjectProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectVisitor;

public class EntityCreationVisitor implements OWLObjectVisitor {

	private VowlData vowlData;
	private Logger logger = LogManager.getLogger(EntityCreationVisitor.class);

	public EntityCreationVisitor(VowlData vowlData) {
		this.vowlData = vowlData;
	}

	@Override
	public void visit(OWLClass ce) {
		AbstractClass clazz;

		if (ce.isOWLThing() || ce.isOWLNothing()) {
			// General class do not create here
			return;
		} else if (!ce.isAnonymous()) {
			clazz = new VowlClass(ce.getIRI());
		} else {
			// KNOWN LIMITATION: anonymous OWL class handling not fully specified
			logger.info("Anonymous OWLClass " + ce);
			return;
		}

		vowlData.addClass(clazz);
	}

	@Override
	public void visit(OWLLiteral node) {
	}

	@Override
	public void visit(OWLDatatype node) {
		if (node.getIRI().toString().equals(VowlLiteral.LITERAL_IRI)) {
			// Skip generic literal. Already included in the data as default.
			return;
		}

		vowlData.addDatatype(new VowlDatatype(node.getIRI()));
	}

	@Override
	public void visit(OWLObjectProperty property) {
		VowlObjectProperty prop;

		if (!property.isAnonymous()) {
			prop = new VowlObjectProperty(property.getIRI());
		} else {
			// KNOWN LIMITATION: anonymous OWL class handling not fully specified
			logger.info("Anonymous OWLObjectProperty " + property);
			return;
		}

		vowlData.addObjectProperty(prop);
	}

	@Override
	public void visit(OWLDataProperty property) {
		VowlDatatypeProperty prop;

		if (!property.isAnonymous()) {
			prop = new VowlDatatypeProperty(property.getIRI());
		} else {
			// KNOWN LIMITATION: anonymous OWL class handling not fully specified
			logger.info("Anonymous OWLDataProperty " + property);
			return;
		}

		vowlData.addDatatypeProperty(prop);
	}
}
