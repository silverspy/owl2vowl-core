import fs from 'node:fs';

const [output, ...reports] = process.argv.slice(2);
if (!output || !reports.length) throw new Error('Usage: node summarize-trivy.mjs OUTPUT REPORT...');
const summary = reports.map(file => {
  const report = JSON.parse(fs.readFileSync(file, 'utf8'));
  const counts = { CRITICAL: 0, HIGH: 0, MEDIUM: 0, LOW: 0, UNKNOWN: 0 };
  const findings = (report.Results || []).flatMap(result =>
    (result.Vulnerabilities || []).map(v => ({
      target: result.Target, id: v.VulnerabilityID, package: v.PkgName,
      installed: v.InstalledVersion, fixed: v.FixedVersion || '',
      severity: v.Severity, severitySource: v.SeveritySource,
      status: v.Status, url: v.PrimaryURL
    })));
  for (const finding of findings) counts[finding.severity in counts ? finding.severity : 'UNKNOWN']++;
  return {
    file, artifact: report.ArtifactName, artifactType: report.ArtifactType,
    results: (report.Results || []).map(r => ({
      target: r.Target, type: r.Type, packages: (r.Packages || []).length,
      findings: (r.Vulnerabilities || []).length
    })),
    findingInstances: findings.length,
    uniqueAdvisories: new Set(findings.map(v => v.id)).size,
    counts, findings
  };
});
fs.writeFileSync(output, JSON.stringify({ generatedAt: new Date().toISOString(), scans: summary }, null, 2));
for (const row of summary) console.log(JSON.stringify({ file: row.file, uniqueAdvisories: row.uniqueAdvisories, ...row.counts }));
if (summary.some(row => row.counts.HIGH || row.counts.CRITICAL || row.counts.UNKNOWN)) process.exitCode = 1;
