### Requirements

- [Java 11](https://adoptium.net/temurin/releases/?version=11) _(Can also be installed via IntelliJ)_
- [IntelliJ Idea Community Edition](https://www.jetbrains.com/idea/download/) or [VSCode](https://code.visualstudio.com/) / [VSCodium](https://vscodium.com/)

### Manually Testing

- Add `-ea` to your JVM Args (Skip for VSCode / VSCodium)
- Run the test within [GoalTrackerPluginTest](src/test/java/com/toofifty/goaltracker/GoalTrackerPluginTest.java)
- RuneLite should launch, login with a non Jagex Account (basic Runescape Account).

### Updating on Plugin Hub

- [Edit the goal-tracker plugin on runelite/plugin-hub](https://github.com/runelite/plugin-hub/blob/master/plugins/goal-tracker)
- Update the commit hash to the relevant hash on [master](https://github.com/Toofifty/rl-goal-tracker/commits/master/)