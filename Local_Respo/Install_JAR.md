# Install JAR

Install the JAR into your local Maven repository as follows:

```bash
mvn install:install-file \
   -Dfile=<path-to-file> \
   -DgroupId=<group-id> \
   -DartifactId=<artifact-id> \
   -Dversion=<version> \
   -Dpackaging=<packaging> \
   -DgeneratePom=true
```

Where each refers to:

- `<path-to-file>`: the path to the file to load e.g → c:\kaptcha-2.3.jar
- `<group-id>`: the group that the file should be registered under e.g → com.google.code
- `<artifact-id>`: the artifact name for the file e.g → kaptcha
- `<version>`: the version of the file e.g → 2.3
- `<packaging>`: the packaging of the file e.g. → jar

我的示例：

```bash
mvn install:install-file \
   -Dfile=/home/liusen/Downloads/pastry-2.1.jar \
   -DgroupId=org.freepastry \
   -DartifactId=pastry \
   -Dversion=2.1 \
   -Dpackaging=jar \
   -DgeneratePom=true
```
