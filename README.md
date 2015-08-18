# Katsu - Game Framework built on LibGDX

This is my personal library used to get a quick start for my Ludum Dare entries.
It's evolved over time based on my needs during the compos. The project structure was originally generated by LibGDX (Gradle).

Enjoy, and good luck! ~ berkano (http://ludumdare.com/compo/author/berkano/)

# Dependencies / Running

- The project was originally generated by LibGDX and relies on Gradle (of which I am a true beginner)
- In theory things should be IDE independent (I don't commit any IDE settings etc) but I exclusively work with IntelliJ IDEA so YMMV.
- Invoke the katsu \[run\] goal to launch the game.
- The code supports a -DdevMode=true parameter (add this to your run configuration) to enable some shortcuts/hacks/debug features while developing.

# Mac development with IntelliJ IDEA

- Tested under Yosemite + IntelliJ IDEA 14 + JDK 7 (YMMV)
- To get it working I had to install JDK 7 from http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
- Then edit the IntelliJ config file at /Applications/IntelliJ IDEA 14.app/Contents/Info.plist, setting:
```
<key>JVMVersion</key>
<string>1.7</string>
```

# Implementing a game

- The game library itself is under core/src/katsu.
- Each game has its own:
    - implementation package e.g. core/src/ld32 is my LD32 entry
    - resources folder e.g. core/assets/ld32/...
    - desktop launcher e.g. desktop/src/ld32/LD32Runner
        - this is set as the main class in build.gradle
        - the launcher must pass an instance of your implementation of KGame which is responsible for implementation specific details (level manager, sounds, settings and so on - the LD32 implementation hopefully shows what's needed here).

# Credits & License

- The ext.pathfinding package is adapted from http://www.gudradain.byethost12.com/Pathfinding.html, all that has changed is the package name (licensing terms are unclear).
- The Minecraftia font was obtained from http://www.dafont.com/minecraftia.font. Copyright Andrew Tyler. Visit that page for requirements around commercial usage.
- All other code and assets are hereby released into the public domain.
