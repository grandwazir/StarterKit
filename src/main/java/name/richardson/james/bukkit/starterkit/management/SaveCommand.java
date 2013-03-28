/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * SaveCommand.java is part of StarterKit.
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import name.richardson.james.bukkit.starterkit.StarterKit;
import name.richardson.james.bukkit.starterkit.StarterKitConfiguration;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;

public class SaveCommand extends AbstractCommand {

  /** The inventory of the player we are using as a template */
  private PlayerInventory inventory;

  private final StarterKitConfiguration configuration;

  public SaveCommand(final StarterKit plugin) {
    super(plugin);
    this.configuration = plugin.getStarterKitConfiguration();
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    try {
      this.configuration.setInventory(this.inventory);
    } catch (final IOException e) {
      throw new CommandUsageException(this.getLocalisation().getMessage(this, "unable-to-read-configuration"));
    }
    sender.sendMessage(this.getLocalisation().getMessage(this, "saved"));
  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {
    final Player player = (Player) sender;
    this.inventory = player.getInventory();
  }
  
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] arguments) {
    return new ArrayList<String>();
  }

}
