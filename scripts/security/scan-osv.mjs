import fs from 'node:fs';
const [input, output] = process.argv.slice(2);
const inventory = JSON.parse(fs.readFileSync(input, 'utf8'));
const rows = [];
for (let start = 0; start < inventory.length; start += 25) {
  const batch = inventory.slice(start, start + 25);
  const response = await fetch('https://api.osv.dev/v1/querybatch', {
    method: 'POST', headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({queries: batch.map(a => ({package: {ecosystem:'Maven', name: `${a.group}:${a.artifact}`}, version:a.version}))})
  });
  if (!response.ok) throw new Error(`OSV ${response.status}`);
  const data = await response.json();
  for (let i = 0; i < batch.length; i++) rows.push({...batch[i], vulnerabilities: data.results[i].vulns || []});
}
const ids = [...new Set(rows.flatMap(r => r.vulnerabilities.map(v => v.id)))];
const details = {};
for (const id of ids) {
  const response = await fetch(`https://api.osv.dev/v1/vulns/${id}`);
  if (!response.ok) throw new Error(`OSV detail ${id}: ${response.status}`);
  details[id] = await response.json();
}
fs.writeFileSync(output, JSON.stringify({queriedAt: new Date().toISOString(), source: 'https://api.osv.dev', inventory: rows, details}, null, 2));
console.log(`${inventory.length} artifacts scanned; ${rows.filter(r => r.vulnerabilities.length).length} affected; ${ids.length} unique advisories`);
for (const r of rows.filter(r => r.vulnerabilities.length)) console.log(`${r.group}:${r.artifact}:${r.version}: ${r.vulnerabilities.map(v => v.id).join(', ')}`);
if (process.argv.includes('--fail-on-vulnerability') && ids.length) process.exitCode = 1;
