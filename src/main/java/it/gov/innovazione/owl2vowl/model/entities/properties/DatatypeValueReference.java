package it.gov.innovazione.owl2vowl.model.entities.properties;

import it.gov.innovazione.owl2vowl.constants.PropertyAllSomeValue;
import it.gov.innovazione.owl2vowl.constants.PropertyType;
import it.gov.innovazione.owl2vowl.constants.VowlAttribute;
import it.gov.innovazione.owl2vowl.model.entities.HasReference;
import org.semanticweb.owlapi.model.IRI;

public class DatatypeValueReference extends VowlDatatypeProperty implements HasReference {
	private PropertyAllSomeValue value;
	private IRI referenceIri;

	public DatatypeValueReference(IRI iri, IRI referencedIri) {
		super(iri);
		this.referenceIri = referencedIri;
	}

	public PropertyAllSomeValue getValue() {
		return value;
	}

	public void setValue(PropertyAllSomeValue value) {
		this.value = value;

		if (value == PropertyAllSomeValue.ALL) {
			addAttribute(VowlAttribute.ALL_VALUES);
			setType(PropertyType.ALL_VALUES);
		} else {
			addAttribute(VowlAttribute.SOME_VALUES);
			setType(PropertyType.SOME_VALUES);
		}
	}

	public IRI getReferenceIri() {
		return referenceIri;
	}

	public void setReferenceIri(IRI referenceIri) {
		this.referenceIri = referenceIri;
	}
}
