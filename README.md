# Cobblemon Spawn Alerts (UNOFFICIAL 1.20.1 Fabric Backport)

An unofficial Fabric compatibility backport of Cobblemon Spawn Alerts 1.13.2 for Minecraft 1.20.1 and Cobblemon 1.5.2. It brings the upstream configurable spawn-alert feature set to this older Minecraft release.

This community backport is not affiliated with or endorsed by the upstream author.

## Features

- Configure alerts per Pokémon, including shiny, legendary, mythical, Ultra Beast, Paradox, and Pokédex-registration conditions.
- Customize spawn and despawn messages and their formatting with [Ember's Text API](https://modrinth.com/mod/embers-text-api).
- Optionally broadcast rare Pokémon alerts to players on a server.
- Create and remove JourneyMap waypoints for Pokémon alerts when JourneyMap is installed.
- Send configurable Discord webhook messages from the client or server.
- Optionally report Pokémon spawned by commands with the server setting enableSpawnCommandAlerts. This option is disabled by default and can conflict with other spawn-related mods.

## Compatibility

The target release is Minecraft 1.20.1 with Fabric Loader 0.15.11 or newer, Fabric API for 1.20.1, Cobblemon 1.5.2 for 1.20.1, and Ember's Text API 3.0.4 or newer. Other Cobblemon versions have not been verified.

The mod can run on the client by itself. If a server installs it, every connecting client must also install it. Some Pokémon details may be unavailable when the server does not have the mod.

JourneyMap is optional and is needed only for waypoint integration.

## Configuration and help

Configuration files are created under config/cobblemon-spawn-alerts. Use /csa openconfig to open that directory and /csa reload to reload the client configuration. Operators can use /csa-common reload to reload the server configuration.

For the upstream configuration guide, see the [Cobblemon Spawn Alerts documentation](https://stainlessstasis.github.io/CSA-Docs/config). Some instructions may refer to newer Minecraft releases.

## Build

Use JDK 17. On Windows run:

~~~bat
gradlew.bat :fabric:build
~~~

On macOS or Linux run:

~~~sh
./gradlew :fabric:build
~~~

The remapped Fabric JAR is produced in fabric/build/libs.

## Attribution and license

Based on Cobblemon Spawn Alerts by Stasis, the Shattered. The upstream source is available at [StainlessStasis/CobblemonSpawnAlerts](https://github.com/StainlessStasis/CobblemonSpawnAlerts) and is licensed under the MIT License. The upstream copyright and license notice are retained in LICENSE. The original radar-style icon in this backport is also licensed under MIT as listed there.

AI assistance was used for the 1.20.1 compatibility port, API adaptation, build configuration, project icon, and release documentation.
