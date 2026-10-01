"""Inventory actual shaded JAR metadata and forbidden bytecode namespaces."""
import hashlib
import json
import re
import sys
import zipfile
from pathlib import Path

jar, output = map(Path, sys.argv[1:3])
with zipfile.ZipFile(jar) as archive:
    names = archive.namelist()
    artifacts = []
    for name in names:
        if name.startswith('META-INF/maven/') and name.endswith('/pom.properties'):
            props = {key: value.strip() for key, value in re.findall(r'^([^#!\s=]+)\s*=\s*(.*)$', archive.read(name).decode('utf-8', errors='replace'), re.M)}
            if all(key in props for key in ('groupId', 'artifactId', 'version')):
                artifacts.append({'group':props['groupId'], 'artifact':props['artifactId'], 'version':props['version'], 'metadata':name})
    prefixes = ['org/codehaus/jackson/', 'org/apache/commons/collections15/', 'org/apache/commons/collections/',
                'org/springframework/', 'org/apache/catalina/', 'org/apache/coyote/',
                'org/apache/logging/log4j/core/', 'de/uni_stuttgart/vis/vowl/owl2vowl/']
    forbidden = {prefix: [n for n in names if n.startswith(prefix) and n.endswith('.class')] for prefix in prefixes}
    class_versions = {}
    for name in names:
        if name.endswith('.class') and not name.startswith('META-INF/versions/') and not name.endswith('module-info.class'):
            major = int.from_bytes(archive.read(name)[6:8], 'big')
            class_versions[major] = class_versions.get(major, 0) + 1
    result = {'jar':jar.name, 'sha256':hashlib.sha256(jar.read_bytes()).hexdigest(),
              'artifacts':artifacts, 'forbiddenNamespaces':forbidden, 'classMajorVersions':class_versions,
              'nestedJars':[n for n in names if n.endswith('.jar')]}
output.write_text(json.dumps(result, indent=2), encoding='utf-8')
output.with_name(output.stem + '-artifacts.json').write_text(json.dumps(artifacts, indent=2), encoding='utf-8')
print(json.dumps({'jar':jar.name, 'sha256':result['sha256'], 'artifacts':len(artifacts),
                  'forbiddenClassCounts':{k:len(v) for k,v in forbidden.items()}, 'classMajorVersions':class_versions}))
if '--assert-clean' in sys.argv and any(forbidden.values()):
    raise SystemExit('Forbidden bytecode remains in the packaged JAR')
