package name.richardson.james.bukkit.starterkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import name.richardson.james.bukkit.utilities.internals.Logger;

@SerializableAs("InventoryKit")
public class InventoryKit implements ConfigurationSerializable {

  private static final Logger logger = new Logger(InventoryKit.class);
  
  private ItemStack[] items;
  
  public static InventoryKit deserialize(Map<String, Object> map) {
    List<ItemStack> items = new ArrayList<ItemStack>(44);
    // Ensure we have capacity in the list
    for (int i = 44; --i >= 0;) items.add(null);
    for (String key :  map.keySet()) {
      try {
      // to get around the fact that the class description appears first in the map.
      if (key.startsWith("==")) continue;
        items.set(Integer.parseInt(key), (ItemStack) map.get(key));
      } catch (ClassCastException e) {
        logger.warning("Unable to deserialize object in slot " + key);
      }
    }
    return new InventoryKit(items);
  }
  
  public int getItemCount() {
    int n = 0;
    for (int i = 0; i < this.items.length; i++) {
      if (this.items[i] == null) continue;
      n++;
    }
    return n;
  }
  
  public InventoryKit() {
    this.items = new ItemStack[0];
  }
  
  public InventoryKit(PlayerInventory inventory) {
    this.items = inventory.getContents();
  }
  
  public InventoryKit(List<ItemStack> items) {
    this.items = items.toArray(new ItemStack[items.size()]);
  }

  public ItemStack[] getContents() {
    logger.debug(this.items.toString());
    return items;
  }
  
  public Map<String, Object> serialize() {
    Map<String, Object> map = new HashMap<String, Object>();
    int slot = -1;
    for (ItemStack item : items) {
      slot++;
      if(item == null) continue;
      map.put(Integer.toString(slot), item);
    }
    return map;
  }  
  
}
