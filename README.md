# Iglee's Library

The library for all mods of iglee42.

## Developpers

Add this repository to your build.gradle
```gradle
repository {
    name = "Iglee's Repository"
    url = "https://maven.iglee.fr/releases/"
}
```

And then add the dependency to your build.gradle
```gradle
implementation "fr.iglee42:IgleeLib:${minecraft_version}-${iglee_lib_version}"
```

The latest version of the library is : [![Latest Lib Version](https://flat.badgen.net/maven/v/metadata-url/https/maven.iglee.fr/releases/fr/iglee42/IgleeLib/maven-metadata.xml?color=cf9555&label=)](https://maven.iglee.fr/#/releases/fr/iglee42/IgleeLib)
