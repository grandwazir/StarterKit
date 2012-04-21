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

import org.bukkit.configuration.serialization.ConfigurationSerialization;

import name.richardson.james.bukkit.starterkit.management.ListCommand;
import name.richardson.james.bukkit.starterkit.management.LoadCommand;
import name.richardson.james.bukkit.starterkit.management.SaveCommand;
import name.richardson.james.bukkit.utilities.command.CommandManager;
import name.richardson.james.bukkit.utilities.internals.Logger;
import name.richardson.james.bukkit.utilities.plugin.SimplePlugin;

public class StarterKit extends SimplePlugin {

  private StarterKitConfiguration configuration;
  private PlayerJoinListener playerListener;

  public StarterKitConfiguration getStarterKitConfiguration() {
    return this.configuration;
  }
  
  public StarterKit() {
    ConfigurationSerialization.registerClass(ArmourKit.class);
    ConfigurationSerialization.registerClass(InventoryKit.class);
  }

  @Override
  public void onEnable() {
    this.logger.setPrefix("[StarterKit] ");

    try {
      this.setResourceBundle();
      this.loadConfiguration();
      this.setRootPermission();
      this.loadListeners();
      this.registerCommands();
    } catch (final IOException exception) {
      this.logger.severe(this.getMessage("unable-to-read-configuration"));
      this.setEnabled(false);
    } finally {
      if (!this.isEnabled()) {
        this.logger.severe(this.getMessage("panic"));
        return;
      }
    }

    this.logger.info(this.getSimpleFormattedMessage("plugin-enabled", this.getDescription().getName()));
  }

  public void reload() throws IOException {
    this.loadConfiguration();
    this.loadListeners();
  }

  private void loadConfiguration() throws IOException {
    this.configuration = new StarterKitConfiguration(this);
    if (this.configuration.getDebugging()) {
      Logger.setDebugging(this, true);
    }
    this.logger.info(getFormattedKitCount());
  }

  private void loadListeners() {
    this.playerListener = new PlayerJoinListener(this);
    this.getServer().getPluginManager().registerEvents(this.playerListener, this);
  }

  private void registerCommands() {
    final CommandManager commandManager = new CommandManager(this);
    this.getCommand("sk").setExecutor(commandManager);
    commandManager.addCommand(new ListCommand(this));
    commandManager.addCommand(new LoadCommand(this));
    commandManager.addCommand(new SaveCommand(this));
  }
  
  public String getFormattedKitCount() {
    final Object[] arguments = { this.configuration.getItemCount() };
    final double[] limits = { 0, 1, 2 };
    final String[] formats = { this.getMessage("no-entries"), this.getMessage("one-entry"), this.getMessage("many-entries") };
    return this.getChoiceFormattedMessage("kit-summary", arguments, formats, limits);
  }

}
