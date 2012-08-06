/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
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

import name.richardson.james.bukkit.starterkit.kit.ArmourKit;
import name.richardson.james.bukkit.starterkit.kit.InventoryKit;
import name.richardson.james.bukkit.starterkit.management.ListCommand;
import name.richardson.james.bukkit.starterkit.management.LoadCommand;
import name.richardson.james.bukkit.starterkit.management.SaveCommand;
import name.richardson.james.bukkit.utilities.command.CommandManager;
import name.richardson.james.bukkit.utilities.plugin.AbstractPlugin;

public class StarterKit extends AbstractPlugin {

  private StarterKitConfiguration configuration;
  private PlayerJoinListener playerListener;
  
  public StarterKit() {
    ConfigurationSerialization.registerClass(ArmourKit.class);
    ConfigurationSerialization.registerClass(InventoryKit.class);
  }

  public StarterKitConfiguration getStarterKitConfiguration() {
    return this.configuration;
  }

  public void reload() throws IOException {
    this.loadConfiguration();
    this.registerEvents();
  }

  protected void loadConfiguration() throws IOException {
    this.configuration = new StarterKitConfiguration(this);
    this.getCustomLogger().info(this, "kit-summary", this.configuration.getItemCount());
  }

  protected void registerEvents() {
    this.playerListener = new PlayerJoinListener(this);
    this.getServer().getPluginManager().registerEvents(this.playerListener, this);
  }

  protected void registerCommands() {
    final CommandManager commandManager = new CommandManager(this);
    this.getCommand("sk").setExecutor(commandManager);
    commandManager.addCommand(new ListCommand(this));
    commandManager.addCommand(new LoadCommand(this));
    commandManager.addCommand(new SaveCommand(this));
  }
  
  protected void setupMetrics() throws IOException {
    if (configuration.isCollectingStats()) new MetricsListener(this);
  }
  
  public String getArtifactID() {
    return "starter-kit";
  }

}
