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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.PlayerInventory;

import name.richardson.james.bukkit.starterkit.kit.ArmourKit;
import name.richardson.james.bukkit.starterkit.kit.InventoryKit;
import name.richardson.james.bukkit.utilities.listener.AbstractListener;
import name.richardson.james.bukkit.utilities.logging.PluginLogger;

public class PlayerListener extends AbstractListener {

	private static final Logger LOGGER = PluginLogger.getLogger(PlayerListener.class);

	/** The inventory to grant new players. */
	private final InventoryKit inventory;

	/** The armour to grant new players. */
	private final ArmourKit armour;

	/** Setting to decide if we are granting starter kits on death */
	private final boolean kitOnDeath;

	public PlayerListener(final StarterKit plugin) {
		super(plugin);
		this.inventory = plugin.getStarterKitConfiguration().getInventoryKit();
		this.armour = plugin.getStarterKitConfiguration().getArmourKit();
		this.kitOnDeath = plugin.getStarterKitConfiguration().isProvidingKitOnDeath();
	}

	/**
	 * Called when a player joins the server.
	 * 
	 * Checks to see if the player has played here before.
	 * 
	 * @param event
	 *          PlayerJoinEvent
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		if (!player.hasPlayedBefore()) {
			this.giveKit(player);
		}
	}

	/**
	 * Called when a player respawns in the world.
	 * 
	 * Checks to see if we are giving kits on death and if we are provides a kit.
	 * 
	 * @param event
	 *          PlayerRespawnEvent
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerRespawn(final PlayerRespawnEvent event) {
		if (this.kitOnDeath) {
			this.giveKit(event.getPlayer());
		}
	}

	/**
	 * Give a kit to the player who is currently logging in.
	 */
	private void giveKit(final Player player) {
		LOGGER.log(Level.FINE, "Granting kit: {0}", player.getName());
		final PlayerInventory inventory = player.getInventory();
		inventory.clear();
		inventory.setArmorContents(this.armour.getContents());
		inventory.setContents(this.inventory.getContents());
		final StarterKitGrantedEvent event = new StarterKitGrantedEvent(player.getName(), inventory);
		Bukkit.getServer().getPluginManager().callEvent(event);
	}

}
