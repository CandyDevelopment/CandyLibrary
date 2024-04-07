# CandyLibrary

A powerful library for paper platform

**Updated at 2024.4.7**: This library will only support the latest version as a private library

There is only three important sections in this library: command, gui and visual  
visual includes tablist and scoreboard

## Usage

Get the library instance

```java
CandyLibrary library = CandyLibrary.getLibrary();
```

For example, you can get command service by the code below

```java
CommandService commandService = library.getService(CommandService.class);
/* or */ CommandService commandService = CommandService.getService(); // Actually, the implementation of this method just execute the code above
```

How to use command service? Please check: [CandyCommandAPI](https://github.com/CandyDevelopment/CandyCommandAPI)  
I just integrated the CandyCommandAPI into CandyLibrary however added a lot of new argument types

You can get gui service by similar code

```java
GuiService service = GuiService.getService();
```

Now we should get gui manager to create gui

```java
GuiManager manager = service.getGuiManager();
```

Now in the gui manager, there are only two methods (maybe more in the future) \
one is `normal(int)`, the other one is `anvil()`, they all return a GuiType object  
with the GuiType object, you can create a gui initializer and initialize the gui

```java
GuiType<NormalGui> normalType = manager.normal(4 /* This is how many lines the gui have, in [1,6] */);
NormalGuiInitializer initializer = normalType.create(plugin /* Requires a bukkit plugin object */);
NormalGui gui = initializer.title(Component.text("example title"))
        .initialize();
```

Now we can open this gui to player, but before, we should get a GuiAudience object

```java
GuiAudience audience = GuiAudience.getAudience(player /* bukkit player object */);
NormalGuiRenderer renderer = gui.prepare(player);
renderer.render(); // After invoking this, the player will see the gui
```