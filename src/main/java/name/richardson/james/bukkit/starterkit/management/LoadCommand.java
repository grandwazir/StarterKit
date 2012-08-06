/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * LoadCommand.java is part of StarterKit.
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
package name.richardson.james.bukkit.starterkit.management;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import name.richardson.james.bukkit.starterkit.StarterKit;
import name.richardson.james.bukkit.starterkit.StarterKitConfiguration;
import name.richardson.james.bukkit.starterkit.kit.ArmourKit;
import name.richardson.james.bukkit.starterkit.kit.InventoryKit;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;

public class LoadCommand extends AbstractCommand {

  private final StarterKitConfiguration configuration;

  private PlayerInventory inventory;

  public LoadCommand(final StarterKit plugin) {
    super(plugin, false);
    this.configuration = plugin.getStarterKitConfiguration();
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    final InventoryKit inventoryKit = this.configuration.getInventoryKit();
    final ArmourKit armourKit = this.configuration.getArmourKit();
    this.inventory.setContents(inventoryKit.getContents());
    this.inventory.setArmorContents(armourKit.getContents());
    sender.sendMessage(this.getLocalisation().getMessage(this, "loaded"));
  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {
    final Player player = (Player) sender;
    this.inventory = player.getInventory();
  }


}
