import fs from 'node:fs';
const [input, output] = process.argv.slice(2);
const rows = [];
for (const line of fs.readFileSync(input,'utf8').split(/\r?\n/)) {
  const match = line.match(/([\w.\-]+):([\w.\-]+):jar:([^:\s]+):(compile|runtime|test|provided)/);
  if (match) rows.push({group:match[1],artifact:match[2],version:match[3],scope:match[4]});
}
if (!rows.length) throw new Error('No dependency records found');
fs.writeFileSync(output, JSON.stringify(rows,null,2));
console.log(`${rows.length} resolved dependencies`);
