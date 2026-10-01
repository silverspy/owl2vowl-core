package it.gov.innovazione.owl2vowl.parser.vowl;

import it.gov.innovazione.owl2vowl.constants.Vowl_Lang;
import it.gov.innovazione.owl2vowl.model.AbstractVowlObject;
import it.gov.innovazione.owl2vowl.model.annotation.Annotation;
import it.gov.innovazione.owl2vowl.model.data.VowlData;
import it.gov.innovazione.owl2vowl.parser.helper.IriFormatText;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * @author Eduard
 */
public class AnnotationParser {
	private final VowlData vowlData;
	private final OWLOntologyManager manager;

	public AnnotationParser(VowlData vowlData, OWLOntologyManager manager) {
		this.vowlData = vowlData;
		this.manager = manager;
	}

	public void parse() {
		vowlData.getEntityMap().values().stream().forEach(this::parseForEntity);
	}

	public void parse(AbstractVowlObject vowlObject) {
		parseForEntity(vowlObject);
	}

	protected void parseForEntity(AbstractVowlObject entity) {
		entity.accept(new AnnotationVisitor(vowlData, manager));

		String iriLabel = IriFormatText.cutQuote(IriFormatText.extractNameFromIRI(entity.getIri().toString()));
		Annotation iriAnnotationLabel = new Annotation("label", iriLabel);
		iriAnnotationLabel.setLanguage(Vowl_Lang.LANG_DEFAULT);
		entity.getAnnotations().addLabel(iriAnnotationLabel);
	}
}
