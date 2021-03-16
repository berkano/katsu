### Katsu - Java 2D Game Framework based on LibGDX

This is my personal game framework for Ludum Dare (http://ludumdare.com/compo/)
which I have been extending over time based on my needs during the compo.
It's based on LibGDX and contains the source code and assets for my latest LD entry.

#### Dependencies

The project was originally generated by LibGDX and requires Gradle to build.

#### Running

- Gradle: `katsu:desktop [run]`
- Directly: run the class `KLauncher`

#### Producing a build for distribution

Gradle: `katsu:desktop [dist]`

#### Game implementation

The game library itself is under `core/src/katsu`.

Each game has its own:
  - implementation package e.g. `core/src/ld32` is my LD32 entry
  - resources folder e.g. `core/assets/ld32/...`
  - desktop launcher e.g. `desktop/src/ld32/LD32Runner`
      - this is set as the main class in build.gradle
      - the launcher must pass an instance of your implementation of KGame which is responsible for implementation specific details (level manager, sounds, settings and so on - the LD32 implementation hopefully shows what's needed here).

#### Testing

The code supports a `-DdevMode=true` parameter (add this to your run configuration) to enable some shortcuts/hacks/debug features while developing.

#### Credits & License

- The katsu.spatial.pathfinding package is adapted from http://www.gudradain.byethost12.com/Pathfinding.html, all that has changed is the package name (licensing terms are unclear).
- The Minecraftia font was obtained from http://www.dafont.com/minecraftia.font. Copyright Andrew Tyler. Visit that page for requirements around commercial usage.
- All other code and assets are hereby released into the public domain.