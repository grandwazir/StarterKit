/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
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
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import name.richardson.james.bukkit.utilities.configuration.AbstractConfiguration;
import name.richardson.james.bukkit.utilities.plugin.SimplePlugin;

public class StarterKitConfiguration extends AbstractConfiguration {

  private final SimplePlugin plugin;
  private InventoryKit inventory;
  private ArmourKit armour;

  public StarterKitConfiguration(final SimplePlugin plugin) throws IOException {
    super(plugin, "config.yml");
    this.plugin = plugin;
    final ConfigurationSection section = this.configuration.getConfigurationSection("kit");
    this.armour = (ArmourKit) section.get("armour");
    this.inventory = (InventoryKit) section.get("inventory");
  }

  public boolean getDebugging() {
    return this.configuration.getBoolean("debugging");
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
      section.set("inventory", new InventoryKit());
      section.set("armour", new ArmourKit());
    }
    this.save();
  }
  

  public void setInventory(PlayerInventory inventory) throws IOException {
    final ConfigurationSection section = this.configuration.getConfigurationSection("kit");
    this.inventory = new InventoryKit(inventory);
    section.set("inventory", inventory);
    this.armour =  new ArmourKit(inventory);
    section.set("armour", armour);
  }
  
  public int getItemCount() {
    return armour.getContents().length + inventory.getContents().length;
  }

  public ArmourKit getArmourKit() {
    return this.armour;
  }
  
  public InventoryKit getInventoryKit() {
    return this.inventory;
  }

}
