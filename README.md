# VoidChestAPI

## Adding VoidChestAPI as a dependency to your build system

### Maven

You can have your project depend on VoidChestAPI as a dependency through the following code snippets:

```xml

<project>
    <repositories>
        <repository>
            <id>georgev22-repository</id>
            <name>GeorgeV22 Repository</name>
            <url>https://repo.georgev22.com/releases</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>com.georgev22.voidchest</groupId>
            <artifactId>voidchestapi</artifactId>
            <version>1.0.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
```

### Gradle

You can include VoidChestAPI into your gradle project using the following lines:

```groovy
repositories {
    maven {
        url "https://repo.georgev22.com/releases"
    }
}

dependencies {
    compileOnly "com.georgev22.voidchest:voidchestapi:1.0.0"
}
```

## Building VoidChestAPI

### Gradle
VoidChestAPI can be built by running the following: `gradle clean build shadowJar`. The resultant jar is built and written
to `build/libs/api-{version}.jar`.

The build directories can be cleaned instead using the `gradle clean` command.

If you want to clean (install) and build the plugin use `gradle clean build shadowJar` command.

## Contributing

VoidChestAPI is an open source `GNU General Public License v3.0` licensed project. I accept contributions through pull
requests, and will make sure to credit you for your awesome contribution.
