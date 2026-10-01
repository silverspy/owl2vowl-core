/*
 * OntologyInformationEnum.java
 *
 */

package it.gov.innovazione.owl2vowl.constants;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 */
public enum OntologyInformationEnum {
	TITLE("title"),
	VERSION("versionInfo"),
	AUTHOR("creator");

	private final String value;

	OntologyInformationEnum(String value) {

		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}
}
