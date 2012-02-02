/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * PlayerListener.java is part of StarterKit.
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

import java.io.File;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import name.richardson.james.bukkit.util.Logger;

public class PlayerListener extends org.bukkit.event.player.PlayerListener {

  private final Logger logger = new Logger(this.getClass());
  private final StarterKit plugin;
  private final Set<ItemStack> items;

  private File playerDirectory;

  public PlayerListener(StarterKit plugin) {
    this.plugin = plugin;
    this.items = plugin.getKit();
    this.setPlayerDirectory();
    logger.debug("Using player directory: " + playerDirectory.getPath());
  }

  public boolean isNewPlayer(Player player) {
    final File playerData = new File(playerDirectory + File.separator + player.getName() + ".dat");
    return !playerData.exists();
  }

  public void onPlayerJoin(PlayerJoinEvent event) {
    if (isNewPlayer(event.getPlayer())) {
      this.giveKit(event.getPlayer());
    }
  }

  private void giveKit(Player player) {
    logger.debug("Granting kit to " + player.getName());
    for (ItemStack item : items) {
      logger.debug(item.toString());
      player.getInventory().addItem(item);
    }
  }

  private void setPlayerDirectory() {
    final File worldDirectory = plugin.getServer().getWorldContainer();
    final String mainWorldName = plugin.getServer().getWorlds().get(0).getName();
    this.playerDirectory = new File(worldDirectory + File.separator + mainWorldName + File.separator + "players");
  }

}
