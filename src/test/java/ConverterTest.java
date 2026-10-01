import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.innovazione.owl2vowl.Owl2Vowl;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import static org.junit.Assert.*;

public class ConverterTest {
    @Test public void convertsClassesPropertiesRestrictionsCardinalitiesAndLocalImports() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory f = manager.getOWLDataFactory();
        IRI base = IRI.create("https://example.org/main");
        IRI importedIri = IRI.create("https://example.org/imported");
        OWLOntology imported = manager.createOntology(importedIri);
        OWLClass external = f.getOWLClass(IRI.create(importedIri + "#External"));
        manager.addAxiom(imported, f.getOWLDeclarationAxiom(external));
        Path importedFile = Path.of("target/imported.owl").toAbsolutePath();
        manager.saveOntology(imported, IRI.create(importedFile.toUri()));
        manager.removeOntology(imported);
        manager.getIRIMappers().add(new SimpleIRIMapper(importedIri, IRI.create(importedFile.toUri())));
        manager.loadOntology(importedIri);
        OWLOntology ontology = manager.createOntology(base);
        manager.applyChange(new AddImport(ontology, f.getOWLImportsDeclaration(importedIri)));
        assertEquals(2, ontology.getImportsClosure().size());
        OWLClass person = f.getOWLClass(IRI.create(base + "#Person"));
        OWLClass child = f.getOWLClass(IRI.create(base + "#Child"));
        OWLObjectProperty knows = f.getOWLObjectProperty(IRI.create(base + "#knows"));
        OWLObjectProperty exact = f.getOWLObjectProperty(IRI.create(base + "#exact"));
        OWLObjectProperty min = f.getOWLObjectProperty(IRI.create(base + "#min"));
        OWLObjectProperty max = f.getOWLObjectProperty(IRI.create(base + "#max"));
        OWLDataProperty name = f.getOWLDataProperty(IRI.create(base + "#name"));
        manager.addAxiom(ontology, f.getOWLDeclarationAxiom(person));
        manager.addAxiom(ontology, f.getOWLSubClassOfAxiom(child, person));
        for (OWLObjectProperty p : new OWLObjectProperty[]{knows, exact, min, max}) {
            manager.addAxiom(ontology, f.getOWLObjectPropertyDomainAxiom(p, person));
            manager.addAxiom(ontology, f.getOWLObjectPropertyRangeAxiom(p, external));
        }
        manager.addAxiom(ontology, f.getOWLDataPropertyDomainAxiom(name, person));
        manager.addAxiom(ontology, f.getOWLDataPropertyRangeAxiom(name, f.getStringOWLDatatype()));
        manager.addAxiom(ontology, f.getOWLSubClassOfAxiom(child, f.getOWLObjectSomeValuesFrom(knows, external)));
        manager.addAxiom(ontology, f.getOWLSubClassOfAxiom(child, f.getOWLObjectAllValuesFrom(knows, external)));
        manager.addAxiom(ontology, f.getOWLSubClassOfAxiom(person, f.getOWLObjectExactCardinality(2, exact)));
        manager.addAxiom(ontology, f.getOWLSubClassOfAxiom(person, f.getOWLObjectMinCardinality(1, min)));
        manager.addAxiom(ontology, f.getOWLSubClassOfAxiom(person, f.getOWLObjectMaxCardinality(3, max)));
        String json = new Owl2Vowl(ontology).getJsonAsString();
        assertSame("Conversion must preserve the caller's ontology manager", manager, ontology.getOWLOntologyManager());
        assertEquals(2, ontology.getImportsClosure().size());
        Files.createDirectories(Path.of("target/webvowl/data"));
        Files.writeString(Path.of("target/webvowl/data/ontology.json"), json, StandardCharsets.UTF_8);
        JsonNode root = new ObjectMapper().readTree(json);
        assertTrue(root.get("class").isArray());
        assertTrue(root.get("property").isArray());
        assertAttribute(root.get("classAttribute"), base + "#Person");
        assertAttribute(root.get("classAttribute"), base + "#Child");
        assertAttribute(root.get("classAttribute"), importedIri + "#External");
        assertAttribute(root.get("propertyAttribute"), base + "#name");
        assertAttribute(root.get("propertyAttribute"), base + "#knows");
        assertEquals("2", attribute(root.get("propertyAttribute"), base + "#exact").get("cardinality").asText());
        assertEquals("1", attribute(root.get("propertyAttribute"), base + "#min").get("minCardinality").asText());
        assertEquals("3", attribute(root.get("propertyAttribute"), base + "#max").get("maxCardinality").asText());
        assertTrue(json.contains("owl:someValuesFrom"));
        assertTrue(json.contains("owl:allValuesFrom"));
        Path output = Path.of("target/webvowl/data/ontology.json");
        Files.createDirectories(output.getParent());
        Files.writeString(output, json, StandardCharsets.UTF_8);
        manager.saveOntology(ontology, new org.semanticweb.owlapi.formats.RDFXMLDocumentFormat(), IRI.create(Path.of("target/main.owl").toAbsolutePath().toUri()));
    }
    private static JsonNode attribute(JsonNode array, String iri) {
        for (JsonNode node : array) if (iri.equals(node.path("iri").asText())) return node;
        fail("Missing entity " + iri); return null;
    }
    private static void assertAttribute(JsonNode array, String iri) { assertNotNull(attribute(array, iri)); }
}
