# Standalone OWL2VOWL core

Convert an OWLAPI ontology into the JSON used by WebVOWL. This Java library
contains no Spring Boot, HTTP server or frontend assets.

The source was extracted from [Team Digitale's WebVOWL fork at
`5cdef0946423a8a813a58f5c9b478467f68cbd9d`](https://github.com/teamdigitale/dati-semantic-WebVOWL/tree/5cdef0946423a8a813a58f5c9b478467f68cbd9d).
The original MIT license and source headers are retained. This repository is an
independent maintenance fork, not an official Team Digitale release.

The extraction updates dependencies, excludes server code, preserves caller-owned
ontologies during cleanup, and restricts annotation pre-parsing to annotation
assertions. Its own POM aligns HTTP bundles from OWLAPI/jsonld-java, without
depending on WIDOCO's version overrides.

## Use

Java 11 or newer and OWLAPI 5.1.18 are supported. Add the JitPack repository and
released dependency to your Maven project:

```xml
<repositories>
    <repository>
        <id>jitpack</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.silverspy</groupId>
        <artifactId>owl2vowl-core</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

```java
import it.gov.innovazione.owl2vowl.Owl2Vowl;

String json = new Owl2Vowl(ontology).getJsonAsString();
```

The original Java package is kept for compatibility. The JAR is a normal Maven
library: its dependencies are described by its POM, not bundled inside it.
Use released versions rather than a moving branch.

## Build and validate

```sh
mvn --batch-mode clean install
mvn --batch-mode dependency:tree -DoutputFile=dependency-tree.txt
mkdir -p target/security
node scripts/security/tree-inventory.mjs dependency-tree.txt target/security/dependencies.json
node scripts/security/scan-osv.mjs target/security/dependencies.json target/security/osv.json --fail-on-vulnerability
```

CI tests Java 11, 17 and 21 and retains the library, sources, dependency tree,
OSV and unfiltered Trivy reports. Conversion tests cover classes, object/data
properties, named restrictions, positive unqualified cardinalities and imports.

## Limits and maintenance

Qualified cardinalities, zero max/exact cardinalities, anonymous intersection
fillers and the tested plain URN form remain incomplete, as in the original
converter. This library is not a reasoner or a lossless OWL representation.

The repository owner maintains releases and dependency updates. Moving the code
out of WIDOCO does not remove this responsibility. See [SECURITY.md](SECURITY.md).
Consumers can select different transitive versions, so scan the final application
JAR and any deployment image as well as this library.
