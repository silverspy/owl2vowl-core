package it.gov.innovazione.owl2vowl.model.entities.properties;

/**
 * @author Eduard
 */
public interface HasCardinality {
	Integer getMinCardinality();
	Integer getMaxCardinality();
	Integer getExactCardinality();

	void setMinCardinality(Integer value);
	void setMaxCardinality(Integer value);
	void setExactCardinality(Integer value);
}
