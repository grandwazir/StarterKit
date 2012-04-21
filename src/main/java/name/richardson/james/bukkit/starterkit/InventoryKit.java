package name.richardson.james.bukkit.starterkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@SerializableAs("InventoryKit")
public class InventoryKit implements ConfigurationSerializable {

  private final ItemStack[] items;
  
  public static InventoryKit deserialize(Map<String, Object> map) {
    List<ItemStack> items = new ArrayList<ItemStack>(4);
    for (String key :  map.keySet()) {
      items.add(Integer.parseInt(key), (ItemStack) map.get(key));
    }
    return new InventoryKit(items);
  }
  
  public InventoryKit(PlayerInventory inventory) {
    this.items = inventory.getContents();
  }
  
  public InventoryKit(List<ItemStack> items) {
    this.items = (ItemStack[]) items.toArray();
  }

  public ItemStack[] getContents() {
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
