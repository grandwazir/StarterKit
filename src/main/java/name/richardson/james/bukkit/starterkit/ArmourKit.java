/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * ArmourKit.java is part of StarterKit.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@SerializableAs("ArmourKit")
public class ArmourKit implements ConfigurationSerializable, Kit {

  public static ArmourKit deserialize(final Map<String, Object> map) {
    final List<ItemStack> items = new ArrayList<ItemStack>();
    // Ensure we have capacity in the list
    for (int i = 4; --i >= 0;) {
      items.add(null);
    }
    for (final String key : map.keySet()) {
      if (key.startsWith("==")) {
        continue;
      }
      items.add(Integer.parseInt(key), (ItemStack) map.get(key));
    }
    return new ArmourKit(items);
  }

  private final ItemStack[] items;

  public ArmourKit() {
    this.items = new ItemStack[0];
  }

  public ArmourKit(final List<ItemStack> items) {
    this.items = items.toArray(new ItemStack[items.size()]);
  }

  public ArmourKit(final PlayerInventory inventory) {
    this.items = inventory.getArmorContents();
  }

  public ItemStack[] getContents() {
    return this.items.clone();
  }

  public int getItemCount() {
    int n = 0;
    for (final ItemStack item : this.items) {
      if (item == null) {
        continue;
      }
      n++;
    }
    return n;
  }
  
  public Map<String, Object> serialize() {
    final Map<String, Object> map = new HashMap<String, Object>();
    int slot = -1;
    for (final ItemStack item : this.items) {
      slot++;
      if ((item == null) || (item.getType() == Material.AIR)) {
        continue;
      }
      map.put(Integer.toString(slot), item);
    }
    return map;
  }


}
