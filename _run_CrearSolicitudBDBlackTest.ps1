$ErrorActionPreference = 'Stop'
$project = 'C:\Users\gorka\git\IS-IAller_JEcheveste_IGarcia'
$repo = Join-Path $env:USERPROFILE '.m2\repository'

$deps = @(
  (Join-Path $repo 'junit\junit\4.13.2\junit-4.13.2.jar'),
  (Join-Path $repo 'org\hamcrest\hamcrest-core\1.3\hamcrest-core-1.3.jar'),
  (Join-Path $repo 'com\objectdb\objectdb\2.8.1\objectdb-2.8.1.jar'),
  (Join-Path $repo 'org\eclipse\persistence\javax.persistence\2.1.0\javax.persistence-2.1.0.jar'),
  (Join-Path $repo 'javax\transaction\jta\1.1\jta-1.1.jar'),
  (Join-Path $repo 'jakarta\xml\bind\jakarta.xml.bind-api\2.3.2\jakarta.xml.bind-api-2.3.2.jar'),
  (Join-Path $repo 'org\glassfish\jaxb\jaxb-runtime\2.3.2\jaxb-runtime-2.3.2.jar'),
  (Join-Path $repo 'jakarta\xml\ws\jakarta.xml.ws-api\2.3.2\jakarta.xml.ws-api-2.3.2.jar'),
  (Join-Path $repo 'com\sun\xml\ws\jaxws-rt\2.3.5\jaxws-rt-2.3.5.jar'),
  (Join-Path $repo 'net\bytebuddy\byte-buddy-agent\1.10.10\byte-buddy-agent-1.10.10.jar'),
  (Join-Path $repo 'net\bytebuddy\byte-buddy\1.10.10\byte-buddy-1.10.10.jar'),
  (Join-Path $repo 'org\objenesis\objenesis-tck\3.0.1\objenesis-tck-3.0.1.jar'),
  (Join-Path $repo 'com\toedter\jcalendar\1.4\jcalendar-1.4.jar')
)

foreach ($dep in $deps) {
  if (-not (Test-Path $dep)) { throw "Missing dependency: $dep" }
}

$cp = @(
  (Join-Path $project 'target\classes'),
  (Join-Path $project 'target\test-classes')
) + $deps
$cpString = ($cp -join ';')

& javac -cp $cpString -d (Join-Path $project 'target\test-classes') `
  (Join-Path $project 'src\test\java\tests\TestDataAccess.java') `
  (Join-Path $project 'src\test\java\bdTest\CrearSolicitudBDBlackTest.java')

& java -cp $cpString org.junit.runner.JUnitCore bdTest.CrearSolicitudBDBlackTest
