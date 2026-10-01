# Security maintenance

Report vulnerabilities through GitHub's private vulnerability reporting for
this repository when available. Otherwise open an issue without exploit details
or sensitive ontology data so that a private reporting channel can be arranged.

The maintained line is 1.0.x. CI scans resolved dependencies with OSV and Trivy
without suppressions. Consumers must scan their final packaged application too;
these checks cannot guarantee the absence of unknown vulnerabilities.

Maintenance is provided by the repository owner on a best-effort basis, without
a guaranteed response time. Dependency and Java/OWLAPI changes require passing
conversion tests before a release. Published tags must not be moved or reused.
Release artifacts include SHA-256 checksums and sources.
