#!/usr/bin/env node

const { program } = require('commander');
const { run } = require('./lib/dependency-check');

program
  .requiredOption('--project <name>', 'the project name (required)')
  .option('-s, --scan [paths...]', 'the path to scan, multiple paths separated by space', ['.'])
  .option('-f, --format [formats...]', 'the output format, multiple formats separated by space (XML, HTML, CSV, JSON, JUNIT, ALL)', ['HTML', 'JSON'])
  .option('--failOnCVSS <score>', 'If the score set between 0 and 10 the exit code from dependency-check will indicate if a vulnerability with a CVSS score equal to or higher was identified.', 11)
  .option('--suppression <files>', 'The file paths to the suppression XML files; used to suppress false positives. This can be specified more than once to utilize multiple suppression files. The argument can be a local file path, a URL to a suppression file, or even a reference to a file on the class path (see https://github.com/jeremylong/DependencyCheck/issues/1878#issuecomment-487533799)')
  .option('-o, --out <path>', 'the folder to write reports to', './dependency-check-reports')
  .option('--bin <path>', 'directory to which the dependency-check CLI will be installed', './dependency-check-bin')
  .option('--force-install', 'install the dependency-check CLI even if there already is one (will be overwritten)');

program.parse(process.argv);

run();
