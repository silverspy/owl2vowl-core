package it.gov.innovazione.owl2vowl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collection;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import it.gov.innovazione.owl2vowl.converter.Converter;
import it.gov.innovazione.owl2vowl.converter.IRIConverter;
import it.gov.innovazione.owl2vowl.converter.InputStreamConverter;
import it.gov.innovazione.owl2vowl.converter.OntologyConverter;
import it.gov.innovazione.owl2vowl.export.types.BackupExporter;

/**
 * Global class for easy to use of this library to include in other projects.
 */
public class Owl2Vowl {
	protected Converter converter;

	public Owl2Vowl(OWLOntology ontology) {
		converter = new OntologyConverter(ontology);
		converter.clearLoadingMsg();
	}

	public Owl2Vowl(OWLOntology ontology, String ontologyIri) {
		converter = new OntologyConverter(ontology, ontologyIri);
	}

	public Owl2Vowl(IRI ontologyIri) {
		converter = new IRIConverter(ontologyIri);
		converter.clearLoadingMsg();
	}

	public Owl2Vowl(IRI ontologyIri, Collection<IRI> dependencies) {
		converter = new IRIConverter(ontologyIri, dependencies);
		converter.clearLoadingMsg();
	}

	public Owl2Vowl(InputStream ontology) {
		converter = new InputStreamConverter(ontology);
		converter.clearLoadingMsg();
	}
	public Owl2Vowl(String ontologyAsString) {
		InputStream ontologyStream = new ByteArrayInputStream(ontologyAsString.getBytes(Charset.defaultCharset()));
		converter = new InputStreamConverter(ontologyStream);
		converter.clearLoadingMsg();
	}

	public Owl2Vowl(InputStream ontology, Collection<InputStream> dependencies) {
		converter = new InputStreamConverter(ontology, dependencies);
		converter.clearLoadingMsg();
	}

	public String getLoadingMsg() {
		return converter.getLoadingInfoString();
	}
	public void appendLoadingIndicatorPoint() {
		if (converter.getCurrentlyLoadingFlag()) {
			converter.addLoadingInfoToParentLine(".");
		}
	}

	
	/**
	 * Converts the ontology to the webvowl compatible format and returns the usable json as string.
	 *
	 * @return The webvowl compatible json format.
	 */
	public String getJsonAsString() {
		BackupExporter exporter = new BackupExporter();

		try {
			converter.export(exporter);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

		return exporter.getConvertedJson();
	}

}
