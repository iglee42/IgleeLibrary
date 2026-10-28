# Iglee's Library

The library for all mods of iglee42.

## Release

Run the `Build` GitHub workflow manually with `is-release` enabled (and `dry-run` disabled for a real release).
Required secrets: `MAVEN_USERNAME`, `MAVEN_PASSWORD`, `MODRINTH_TOKEN`, `CURSEFORGE_TOKEN`, `DISCORD_WEBHOOK`, `DISCORD_WEBHOOK_DRY_RUN`.
Project IDs are set in `gradle.properties` (`publish_*`). The changelog comes from `CHANGELOG.md`.
