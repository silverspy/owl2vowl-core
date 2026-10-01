/*
 * ReturnExporter.java
 *
 */

package it.gov.innovazione.owl2vowl.export.types;

/**
 * Simple exporter that saves the given text.
 */
public class BackupExporter implements Exporter {
	protected String convertedJson;

	public String getConvertedJson() {
		return convertedJson;
	}

	@Override
	public void write(String text) throws Exception {
		convertedJson = text;
	}
}
