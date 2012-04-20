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

  private final Set<ItemStack> items = new LinkedHashSet<ItemStack>();
  private final SimplePlugin plugin;

  public StarterKitConfiguration(final SimplePlugin plugin) throws IOException {
    super(plugin, "config.yml");
    this.plugin = plugin;
    this.setItems();
  }

  public boolean getDebugging() {
    return this.configuration.getBoolean("debugging");
  }

  public Set<ItemStack> getItems() {
    return Collections.unmodifiableSet(this.items);
  }

  public void removeItem(final Material material) throws IOException {
    final ConfigurationSection section = this.configuration.getConfigurationSection("kit");
    section.set(material.name(), null);
    this.save();
    this.setItems();
  }

  @Override
  public void setDefaults() throws IOException {
    this.logger.debug(String.format("Apply default configuration."));
    final org.bukkit.configuration.file.YamlConfiguration defaults = this.getDefaults();
    this.configuration.setDefaults(defaults);
    this.configuration.options().copyDefaults(true);
    // set an example kit if necessary
    if (!this.configuration.isConfigurationSection("kit")) {
      this.logger.debug("Creating examples.");
      this.configuration.createSection("kit");
      final ConfigurationSection section = this.configuration.getConfigurationSection("kit");
      section.set(Material.WOOD_AXE.name(), 1);
      section.set(Material.APPLE.name(), 4);
    }
    this.save();
  }

  public void setItem(final ItemStack stack) throws IOException {
    final ConfigurationSection section = this.configuration.getConfigurationSection("kit");
    section.set(stack.getType().name(), stack.getAmount());
    this.save();
    this.setItems();
  }

  public void setItems() {
    this.items.clear();
    final ConfigurationSection section = this.configuration.getConfigurationSection("kit");
    for (final String key : section.getKeys(false)) {
      try {
        final Material item = Material.valueOf(key);
        final int amount = section.getInt(key);
        final ItemStack stack = new ItemStack(item, amount);
        this.items.add(stack);
        this.logger.debug(String.format("Adding %d %s to the kit", amount, key));
      } catch (final IllegalArgumentException e) {
        this.logger.debug(this.plugin.getSimpleFormattedMessage("not-a-valid-item-material", key));
      }
    }
  }
  

  public void setInventory(PlayerInventory inventory) {
    // TODO Auto-generated method stub
    
  }

  public PlayerInventory getInventory() {
    // TODO Auto-generated method stub
    return null;
  }

}
