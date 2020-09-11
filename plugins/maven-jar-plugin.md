# The jar plugin

<!-- TOC -->

- [1. goal](#1-goal)
- [2. Apache Maven Archiver](#2-apache-maven-archiver)
  - [2.1. addMavenDescriptor](#21-addmavendescriptor)
- [3. No main manifest attribute in jar](#3-no-main-manifest-attribute-in-jar)
- [4. Maven: Exclude “META-INF/maven” folder from JAR](#4-maven-exclude-meta-infmaven-folder-from-jar)
- [5. Use Your Own Manifest File](#5-use-your-own-manifest-file)
- [6. 拷贝依赖的jar包，并将新生成的classpath添加到新生成的jar的manifest文件中](#6-%e6%8b%b7%e8%b4%9d%e4%be%9d%e8%b5%96%e7%9a%84jar%e5%8c%85%e5%b9%b6%e5%b0%86%e6%96%b0%e7%94%9f%e6%88%90%e7%9a%84classpath%e6%b7%bb%e5%8a%a0%e5%88%b0%e6%96%b0%e7%94%9f%e6%88%90%e7%9a%84jar%e7%9a%84manifest%e6%96%87%e4%bb%b6%e4%b8%ad)
- [7. Reference](#7-reference)

<!-- /TOC -->

## 1. goal

The `jar` plugin creates a JAR file from your Maven project. The `jar` goal of the `jar` plugin is bound to the `package` phase of the Maven `default` lifecycle.

When you type `mvn clean install` , Maven will execute all the phases in the `default` lifecycle up to and including the `install` phase, which also includes the `package` phase.

The following command shows how to execute the `jar` goal of the `jar` plugin by itself:

```bash
mvn jar:jar
```

All the Maven projects inherit the `jar` plugin from **the super POM file**. As shown in the following configuration, **the super POM** defines the `jar` plugin. It associates the `jar` goal with the `package` phase of the Maven `default` lifecycle:

```xml
<plugin>
    <artifactId>maven-jar-plugin</artifactId>
    <version>3.1.0</version>
    <executions>
        <execution>
            <id>default-jar</id>
            <phase>package</phase>
            <goals>
                <goal>jar</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

In most of the cases, you do not need to override the `jar` plugin configuration, except in a case, where you need to create **a self-executable jar file**.

Details on how to create **a self-executable JAR** file with `maven-jar-plugin` can be found at http://maven.apache.org/shared/maven-archiver/examples/classpath.html.

## 2. Apache Maven Archiver

The Maven Archiver is mainly used by plugins to handle packaging.

```xml
<archive>
  <addMavenDescriptor/>
  <compress/>
  <forced/>
  <index/>
  <manifest>
    <addClasspath/>
    <addDefaultEntries/>
    <addDefaultImplementationEntries/>
    <addDefaultSpecificationEntries/>
    <addBuildEnvironmentEntries/>
    <addExtensions/>
    <classpathLayoutType/>
    <classpathPrefix/>
    <customClasspathLayout/>
    <mainClass/>
    <packageName/>
    <useUniqueVersions/>
  </manifest>
  <manifestEntries>
    <key>value</key>
  </manifestEntries>
  <manifestFile/>
  <manifestSections>
    <manifestSection>
      <name/>
      <manifestEntries>
        <key>value</key>
      </manifestEntries>
    <manifestSection/>
  </manifestSections>
  <pomPropertiesFile/>
</archive>
```

### 2.1. addMavenDescriptor

Whether the created archive will contain these two Maven files:

- The pom file, located in the archive in `META-INF/maven/${groupId}/${artifactId}/pom.xml`
- A `pom.properties` file, located in the archive in `META-INF/maven/${groupId}/${artifactId}/pom.properties`

The default value is `true`.

## 3. No main manifest attribute in jar

Sometimes you face the following error while runing an executable jar<sub>注：提出问题</sub>:

```txt
No main manifest attribute, in “<APP_NAME>.jar”
```

`Main-class` property is missing on your jars `META-INF/MANIFEST.MF`.

Correct it by adding the following lines to your `pom.xml`<sub>注：解决方法</sub>:

```xml
<build>
    <plugins>
        <plugin>
            <!-- Build an executable JAR -->
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.1.0</version>
            <configuration>
                <archive>
                    <manifest>
                        <mainClass>lsieun.mvn.App</mainClass>
                    </manifest>
                </archive>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Consider that the the fully qualified name of your Main class is `com.roufid.tutorials.AppTest`.

Launch a clean install on your application

```bash
mvn clean install
```

Run your executable jar using the following command :

```bash
java -jar AppTest-0.0.1-SNAPSHOT.jar
```

## 4. Maven: Exclude “META-INF/maven” folder from JAR

URL: https://stackoverflow.com/questions/46959965/maven-exclude-meta-inf-maven-folder-from-jar/46960549#46960549

The `maven-jar-plugin` uses the `maven-archiver` to handle packaging. It provides the configuration `addMavenDescriptor`, which is `true` by default. Setting it to `false` should remove the `META-INF/maven` directory.

```xml
<archive>
   <addMavenDescriptor>false</addMavenDescriptor>
   ....
</archive>
```

## 5. Use Your Own Manifest File

URL: https://maven.apache.org/shared/maven-archiver/examples/manifestFile.html

By default, **Maven Archiver** creates the manifest file for you. It is sometimes useful to use your own hand crafted manifest file. Say that you want to use the manifest file `src/main/resources/META-INF/MANIFEST.MF`. This is done with the `<manifestFile>` configuration element by setting the value to the location of your file.

```txt
Manifest-Version: 1.0
Built-By: ${user.name}
Build-Jdk: ${java.version}
Created-By: Apache Maven
Archiver-Version: Plexus Archiver
```

The content of your own manifest file will be merged with the entries created by Maven Archiver. If you specify an entry in your own manifest file it will override the value created by Maven Archiver.

Note: As with all the examples here, this configuration can be used in all plugins that use **Maven Archiver**, not just `maven-jar-plugin` as in this example.

```xml
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        ...
        <configuration>
          <archive>
            <manifestFile>src/main/resources/META-INF/MANIFEST.MF</manifestFile>
          </archive>
        </configuration>
        ...
      </plugin>
    </plugins>
  </build>
  ...
</project>
```

If you need to define your own `MANIFEST.MF` file you can simply achieve that via **Maven Archiver** configuration like in the following example:

```xml
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.1.0</version>
        <configuration>
          <archive>
            <manifestFile>${project.build.outputDirectory}/META-INF/MANIFEST.MF</manifestFile>
          </archive>
        </configuration>
        ...
      </plugin>
    </plugins>
  </build>
  ...
</project>
```

## 6. 拷贝依赖的jar包，并将新生成的classpath添加到新生成的jar的manifest文件中

```xml
    <build>
        <finalName>agent</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <!-- 关注点 -->
                <configuration>
                    <source>1.7</source>
                    <target>1.7</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>lsieun.javaagent.gui.GUIMain</mainClass>
                            <addClasspath>true</addClasspath>
                            <classpathPrefix>lib/</classpathPrefix>
                            <addDefaultImplementationEntries>true</addDefaultImplementationEntries>
                            <addDefaultSpecificationEntries>true</addDefaultSpecificationEntries>
                        </manifest>
                        <manifestEntries>
                            <Premain-Class>lsieun.javaagent.SimpleAgent</Premain-Class>
                            <Agent-Class>lsieun.javaagent.SimpleAgent</Agent-Class>
                            <Can-Redefine-Classes>false</Can-Redefine-Classes>
                            <Can-Retransform-Classes>true</Can-Retransform-Classes>
                            <Can-Set-Native-Method-Prefix>false</Can-Set-Native-Method-Prefix>
                        </manifestEntries>
                    </archive>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <executions>
                    <execution>
                        <id>lib-copy-dependencies</id>
                        <phase>package</phase>
                        <goals>
                            <goal>copy-dependencies</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>${project.build.directory}/lib</outputDirectory>
                            <overWriteReleases>false</overWriteReleases>
                            <overWriteSnapshots>false</overWriteSnapshots>
                            <overWriteIfNewer>true</overWriteIfNewer>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```


## 7. Reference

- [Apache Maven JAR Plugin](https://maven.apache.org/plugins/maven-jar-plugin/)
- [jar-mojo](https://maven.apache.org/plugins/maven-jar-plugin/jar-mojo.html)
- [Apache Maven Archiver](http://maven.apache.org/shared/maven-archiver/index.html)
