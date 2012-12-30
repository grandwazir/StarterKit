
StarterKit - give items to new players.
====================================

StarterKit is plugin for the Minecraft wrapper [Bukkit](http://bukkit.org/) that provides a method for administrators to give items to new players automatically and seemlessly. You could use this to make your server a more welcoming place to start without running the risk of players exploiting it by claiming their kit more than once.

## Features

- Simple and easy to configure - create your ideal inventory then save it.
- Assign items by slot, including armour.
- Supports complex items such as maps. 
- Can use [any item that Bukkit supports](http://jd.bukkit.org/apidocs/org/bukkit/Material.html) in your kit.
- Ensures that only new players will receive the kit.

## License

StarterKit is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

StarterKit is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

## Documentation

If you are a server administrator, many of the features specific to StarterKit are documented [on the wiki](https://github.com/grandwazir/StarterKit/wiki). If you are looking to change the messages used in StarterKit or localise the plugin into your own language you will want to look at [this page](https://github.com/grandwazir/BukkitUtilities/wiki/Localisation) instead.

If you are a developer you may find the [JavaDocs](http://grandwazir.github.com/StarterKit/apidocs/index.html) and a [Maven website](http://grandwazir.github.com/StarterKit/) useful to you as well.

## Installation

Before installing, you need to make sure you are running at least the latest [recommended build](http://dl.bukkit.org/latest-rb/craftbukkit.jar) for Bukkit. Support is only given for problems when using a recommended build. This does not mean that the plugin will not work on other versions of Bukkit, the likelihood is it will, but it is not supported.

### Getting the latest version

The best way to install StarterKit is to use the [symbolic link](http://repository.james.richardson.name/symbolic/StarterKit.jar) to the latest version. This link always points to the latest version of StarterKit, so is safe to use in scripts or update plugins. A [feature changelog](https://github.com/grandwazir/StarterKit/wiki/changelog) is also available.

### Getting older versions

Alternatively [older versions](http://repository.james.richardson.name/releases/name/richardson/james/bukkit/starter-kit/) are available as well, however they are not supported. If you are forced to use an older version for whatever reason, please let me know why by [opening a issue](https://github.com/grandwazir/StarterKit/issues/new) on GitHub.

### Building from source

You can also build from the source if you would prefer to do so. This is useful for those who wish to modify the plugin before using it. Note it is no longer necessary to do this to alter messages in the plugin. Instead you should read the documentation on how to localise the plugin instead. This assumes that you have Maven and git installed on your computer.

    git clone git://github.com/grandwazir/StarterKit.git
    cd StarterKit
    mvn install

## Reporting issues

If you are a server administrator and you are requesting support in installing or using the plugin you should [make a post](http://dev.bukkit.org/server-mods/starterkit/forum/create-thread/) in the forum on BukkitDev. If you want to make a bug report or feature request please do so using the [issue tracking](https://github.com/grandwazir/StarterKit/issues) on GitHub.
