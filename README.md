# VoidChestAPI

This API won't work for VoidChest versions bellow 5.3.0

## Adding VoidChestAPI as a dependency to your build system

**Warning:**
**Do NOT shade the VoidChestAPI to your plugin but make your plugin depend/soft-depend to VoidChest**

### Maven

You can have your project depend on VoidChestAPI as a dependency through the following code snippets:

```xml

<project>
    <repositories>
        <repository>
            <id>shulkerlabs-repository</id>
            <name>ShulkerLabs Repository</name>
            <url>https://repo.shulkerlabs.com/releases</url>
        </repository>
        <repository>
            <id>shulkerlabs-repository-snapshots</id>
            <name>ShulkerLabs Repository Snapshots</name>
            <url>https://repo.shulkerlabs.com/snapshots</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>com.georgev22.voidchest</groupId>
            <artifactId>voidchestapi</artifactId>
            <version>5.3.0</version>
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
        url "https://repo.shulkerlabs.com/releases"
    }
    maven {
        url "https://repo.shulkerlabs.com/snapshots"
    }
}

dependencies {
    compileOnly "com.georgev22.voidchest:voidchestapi:5.3.0"
}
```

## Building VoidChestAPI

### Gradle

VoidChestAPI can be built by running the following: `gradle clean build jar`. The resultant jar is built and
written to `build/libs/voidchestapi-5.3.0.jar`.

The build directories can be cleaned instead using the `gradle clean` command.

If you want to clean (install) and build the plugin use `gradle clean build jar` command.

## Contributing

VoidChestAPI is an open source `GNU General Public License v3.0` licensed project. I accept contributions through pull
requests, and will make sure to credit you for your awesome contribution.

## Third-Party Code

This project contains modified code derived from the InvUI project:
https://github.com/NichtStudioCode/InvUI

InvUI is licensed under the MIT License.

The original `ItemProvider` / `ItemBuilder` implementations were adapted and modified for use in:

* VoidChest (closed source)
* VoidChestAPI (GPL-3.0)

A copy of the original MIT License is included in this repository
(see LICENSE-InvUI).