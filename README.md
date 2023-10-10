# VoidChestAPI

This API won't work for VoidChest versions bellow 2.0

## Adding VoidChestAPI as a dependency to your build system

**Warning:**
**Do NOT shade the VoidChestAPI to your plugin but make your plugin depend/soft-depend to VoidChest**

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
            <groupId>com.georgev22.voidchest-shade</groupId>
            <artifactId>voidchestapi-shade</artifactId>
            <version>1.4.1</version>
            <classifier>shade</classifier>
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
    compileOnly "com.georgev22.voidchest-shade:voidchestapi-shade:1.4.1"
}
```

## Building VoidChestAPI

### Gradle

VoidChestAPI can be built by running the following: `gradle clean build shadowJar`. The resultant jar is built and
written
to `build/libs/voidchestapi-1.3.0-shade.jar`.

The build directories can be cleaned instead using the `gradle clean` command.

If you want to clean (install) and build the plugin use `gradle clean build shadowJar` command.

## Contributing

VoidChestAPI is an open source `GNU General Public License v3.0` licensed project. I accept contributions through pull
requests, and will make sure to credit you for your awesome contribution.
