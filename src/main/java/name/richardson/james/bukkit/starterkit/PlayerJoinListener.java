/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * PlayerJoinListener.java is part of StarterKit.
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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;

import name.richardson.james.bukkit.starterkit.kit.ArmourKit;
import name.richardson.james.bukkit.starterkit.kit.InventoryKit;
import name.richardson.james.bukkit.utilities.internals.Logger;

/**
 * The listener interface for receiving player events.
 * The class that is interested in processing a player
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addPlayerListener<code> method. When
 * the player event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see PlayerEvent
 */
public class PlayerJoinListener implements Listener {

  /** The logger used for this class. */
  private static final Logger logger = new Logger(PlayerJoinListener.class);

  /** The inventory to grant new players. */
  private final InventoryKit inventory;

  /** The armour to grant new players. */
  private final ArmourKit armour;

  public PlayerJoinListener(final StarterKit plugin) {
    this.inventory = plugin.getStarterKitConfiguration().getInventoryKit();
    this.armour = plugin.getStarterKitConfiguration().getArmourKit();
    Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Called when a player joins the server.
   * 
   * Checks to see if the player has played here before.
   * 
   * @param event PlayerJoinEvent
   */
  @EventHandler(priority = EventPriority.NORMAL)
  public void onPlayerJoin(final PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (!player.hasPlayedBefore()) {
      this.giveKit(player);
    }
  }

  /**
   * Give a kit to the player who is currently logging in.
   */
  private void giveKit(Player player) {
    logger.debug("Granting kit to " + player.getName());
    final PlayerInventory inventory = player.getInventory();
    inventory.clear();
    inventory.setArmorContents(this.armour.getContents());
    inventory.setContents(this.inventory.getContents());
    new StarterKitGrantedEvent(player.getName(), inventory);
  }

}
