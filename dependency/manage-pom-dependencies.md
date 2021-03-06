# Managing POM dependencies

In a large-scale development project with hundreds of Maven modules, managing dependencies could be a hazardous task. There are two effective ways to manage dependencies: **POM inheritance** and **dependency grouping**.  

> 对于大型的项目来说，管理dependencies有两种有效的方式：  
> （1）POM inheritance  
> （2）dependency grouping

1\.  [POM inheritance](#pominheritance)  
1.1\.  [parent POM](#parentpom)  
1.2\.  [child POM](#childpom)  
2\.  [Dependency Grouping](#dependencygrouping)  
2.1\.  [single POM](#singlepom)  
2.2\.  [引用single POM](#引用singlepom)  
3\.  [Refer](#refer)  

<a name="pominheritance"></a>

## 1\. POM inheritance

<a name="parentpom"></a>

### 1.1\. parent POM

With **POM inheritance**, **the parent POM file** has to define all the common dependencies used by **its child modules** under the `<dependencyManagement>` section. 

> **使用的方式**就是：在parent POM的`<dependencyManagement>`当中添加依赖，在child POM中进行引用。

例如：

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-transport-mail</artifactId>
            <version>${axis2-transports.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.ws.commons.axiom.wso2</groupId>
            <artifactId>axiom</artifactId>
            <version>${axiom.wso2.version}</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

This way we can avoid any duplicate dependencies. Also, if we have to update the `version` of a given dependency, then we only have to make a change in one place.

> 这里是讲使用`<dependencyManagement>`的好处，主要两点：  
> （1）避免重复的依赖 avoid any duplicate dependencies  
> （2）更改version的时候，只需要修改一个地方。

> 上面是讲Parent POM的配置  
> 下面是讲Child POM的配置

<a name="childpom"></a>

### 1.2\. child POM

Here, you will see only `groupId` and `artifactId` of a given dependency but not `version` . The `version` of each dependency is managed through the `dependencyManagement` section of **the parent POM file**. If any child Maven module wants to override the version of an inherited dependency, it can simply add the `version` element:

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.axis2.wso2</groupId>
        <artifactId>axis2</artifactId>
    </dependency>
    <dependency>
        <groupId>org.apache.ws.commons.axiom.wso2</groupId>
        <artifactId>axiom</artifactId>
    </dependency>
</dependencies>
```

<a name="dependencygrouping"></a>

## 2\. Dependency Grouping

The second approach to manage dependencies is through **dependency grouping**. **All the common dependencies** can be grouped into **a single POM file**. This approach is much better than **POM inheritance**. Here, you do not need to add references to individual dependencies.

> Dependency的思想就是，将多个common dependencies封装到一个pom文件中，然后再引用这个单独的pom文件。

<a name="singlepom"></a>

### 2.1\. single POM

To avoid the dependency duplication, we can create a Maven module with all the previously mentioned five dependencies as shown in the following code. Make sure to set the value of the `<packaging>` element to `pom` :

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.packt</groupId>
    <artifactId>axis2-client</artifactId>
    <version>1.0.0</version>
    <!-- 注意：这里的类型是pom -->
    <packaging>pom</packaging>

    <dependencies>
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-kernel</artifactId>
            <version>1.6.2</version>
        </dependency>
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-adb</artifactId>
            <version>1.6.2</version>
        </dependency>
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-transport-http</artifactId>
            <version>1.6.2</version>
        </dependency>
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-transport-local</artifactId>
            <version>1.6.2</version>
        </dependency>
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2-xmlbeans</artifactId>
            <version>1.6.2</version>
        </dependency>
    </dependencies>
</project>
```

<a name="引用singlepom"></a>

### 2.2\. 引用single POM

Now, you only need to add a dependency to the `com.packt.axis2-client` module, as follows:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.packt</groupId>
    <artifactId>my-axis2-client</artifactId>
    <version>1.0.0</version>

    <dependencies>
        <!-- 这里是引用上面的pom文件 -->
        <dependency>
            <groupId>com.packt</groupId>
            <artifactId>axis2-client</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
</project>
```

<a name="refer"></a>

## 3\. Refer

- To know more about dependency management, refer to **Introduction to the Dependency Mechanism** at [Link](http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html)
