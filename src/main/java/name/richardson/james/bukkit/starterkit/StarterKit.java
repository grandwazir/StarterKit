/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * StarterKit.java is part of StarterKit.
 * 
 * StarterKit is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * StarterKit is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * StarterKit. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.starterkit;

import java.io.IOException;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import name.richardson.james.bukkit.starterkit.management.AddCommand;
import name.richardson.james.bukkit.starterkit.management.ListCommand;
import name.richardson.james.bukkit.starterkit.management.ReloadCommand;
import name.richardson.james.bukkit.starterkit.management.RemoveCommand;
import name.richardson.james.bukkit.util.Logger;
import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.command.CommandManager;

public class StarterKit extends Plugin {

  private StarterKitConfiguration configuration;
  private PlayerListener playerListener;
  private PluginManager pluginManager;
  private CommandManager commandManager;

  public void addItem(ItemStack item) throws IOException {
    configuration.setItem(item);
  }

  public Set<ItemStack> getKit() {
    return configuration.getItems();
  }

  @Override
  public void onDisable() {
    logger.info(String.format("%s is disabled.", this.getDescription().getName()));
  }

  @Override
  public void onEnable() {
    logger.setPrefix("[StarterKit] ");
    this.pluginManager = this.getServer().getPluginManager();

    try {
      this.loadConfiguration();
      this.loadListeners();
      this.setPermission();
      this.registerCommands();
      // this.registerCommands();
    } catch (IOException exception) {
      logger.severe("Unable to load configuration!");
    }

    logger.info(String.format("%s is enabled.", this.getDescription().getFullName()));
  }

  public void reload() throws IOException {
    this.loadConfiguration();
    this.loadListeners();
  }

  public void removeItem(Material material) throws IOException {
    configuration.removeItem(material);
  }

  private void loadConfiguration() throws IOException {
    this.configuration = new StarterKitConfiguration(this);
    if (this.configuration.getDebugging()) Logger.enableDebugging("starterkit");
  }

  private void loadListeners() {
    this.playerListener = new PlayerListener(this);
    pluginManager.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
  }

  private void registerCommands() {
    this.commandManager = new CommandManager(this.getDescription());
    this.getCommand("sk").setExecutor(commandManager);
    commandManager.registerCommand("add", new AddCommand(this));
    commandManager.registerCommand("list", new ListCommand(this));
    commandManager.registerCommand("reload", new ReloadCommand(this));
    commandManager.registerCommand("remove", new RemoveCommand(this));
  }

}
