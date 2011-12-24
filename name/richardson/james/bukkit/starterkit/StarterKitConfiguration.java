/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * TimedMessagesConfiguration.java is part of TimedMessages.
 * 
 * TimedMessages is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * TimedMessages is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * TimedMessages. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.starterkit;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.configuration.AbstractConfiguration;

public class StarterKitConfiguration extends AbstractConfiguration {

  private final Set<ItemStack> items = new LinkedHashSet<ItemStack>();

  public StarterKitConfiguration(Plugin plugin) throws IOException {
    super(plugin, "config.yml");
    this.setItems();
  }

  public boolean getDebugging() {
    return configuration.getBoolean("debugging");
  }

  public Set<ItemStack> getItems() {
    return Collections.unmodifiableSet(items);
  }

  public void removeItem(Material material) throws IOException {
    final ConfigurationSection section = configuration.getConfigurationSection("kit");
    section.set(material.name(), null);
    this.save();
    this.setItems();
  }

  public void setDefaults() throws IOException {
    logger.debug(String.format("Apply default configuration."));
    final org.bukkit.configuration.file.YamlConfiguration defaults = this.getDefaults();
    this.configuration.setDefaults(defaults);
    this.configuration.options().copyDefaults(true);
    // set an example kit if necessary
    if (!configuration.isConfigurationSection("kit")) {
      logger.debug("Creating examples.");
      configuration.createSection("kit");
      final ConfigurationSection section = configuration.getConfigurationSection("kit");
      section.set(Material.WOOD_AXE.name(), 1);
      section.set(Material.APPLE.name(), 4);
    }
    this.save();
  }

  public void setItem(ItemStack stack) throws IOException {
    final ConfigurationSection section = configuration.getConfigurationSection("kit");
    section.set(stack.getType().name(), stack.getAmount());
    this.save();
    this.setItems();
  }

  public void setItems() {
    items.clear();
    final ConfigurationSection section = configuration.getConfigurationSection("kit");
    for (String key : section.getKeys(false)) {
      try {
        final Material item = Material.valueOf(key);
        final int amount = section.getInt(key);
        final ItemStack stack = new ItemStack(item, amount);
        items.add(stack);
        logger.debug(String.format("Adding %d %s to the kit", amount, key));
      } catch (IllegalArgumentException e) {
        logger.warning(String.format("%s is not a valid item type.", key));
      }
    }
  }

}
