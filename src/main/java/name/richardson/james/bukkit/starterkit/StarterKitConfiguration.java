/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * StarterKitConfiguration.java is part of StarterKit.
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

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.PlayerInventory;

import name.richardson.james.bukkit.utilities.configuration.PluginConfiguration;
import name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin;

public class StarterKitConfiguration extends PluginConfiguration {

  private InventoryKit inventory;
  private ArmourKit armour;

  public StarterKitConfiguration(final SkeletonPlugin plugin) throws IOException {
    super(plugin);
    final ConfigurationSection section = this.configuration.getConfigurationSection("kit");
    this.armour = (ArmourKit) section.get("armour");
    this.inventory = (InventoryKit) section.get("backpack");
  }

  public ArmourKit getArmourKit() {
    return this.armour;
  }

  public InventoryKit getInventoryKit() {
    return this.inventory;
  }

  public int getItemCount() {
    return this.armour.getItemCount() + this.inventory.getItemCount();
  }

  @Override
  public void setDefaults() throws IOException {
    this.logger.debug(String.format("Apply default configuration."));
    final org.bukkit.configuration.file.YamlConfiguration defaults = this.getDefaults();
    this.configuration.setDefaults(defaults);
    this.configuration.options().copyDefaults(true);
    // set an example kit if necessary
    if (!this.configuration.isConfigurationSection("kit")) {
      final ConfigurationSection section = this.configuration.createSection("kit");
      section.set("backpack", new InventoryKit());
      section.set("armour", new ArmourKit());
    }
    this.save();
  }

  public void setInventory(final PlayerInventory inventory) throws IOException {
    final ConfigurationSection section = this.configuration.getConfigurationSection("kit");
    this.inventory = new InventoryKit(inventory);
    section.set("backpack", this.inventory);
    this.armour = new ArmourKit(inventory);
    section.set("armour", this.armour);
    this.save();
  }

}
