
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
