/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * AddCommand.java is part of StarterKit.
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

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.starterkit.StarterKit;
import name.richardson.james.bukkit.starterkit.StarterKitConfiguration;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.utilities.command.ConsoleCommand;
import name.richardson.james.bukkit.utilities.command.PluginCommand;

@ConsoleCommand
public class AddCommand extends PluginCommand {

  public static int MAX_ITEM_STACK_SIZE = 64;

  private final StarterKitConfiguration configuration;

  /** The of material we are adding to the kit */
  private int amount;

  /** The material we are adding to the kit */
  private Material material;

  public AddCommand(final StarterKit plugin) {
    super(plugin);
    this.configuration = plugin.getStarterKitConfiguration();
    this.registerPermissions();
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {

    if ((this.amount > AddCommand.MAX_ITEM_STACK_SIZE) || (this.amount <= 0)) {
      throw new CommandArgumentException(this.getMessage("must-specify-valid-amount"), this.getSimpleFormattedMessage("valid-amount-range", String.valueOf(AddCommand.MAX_ITEM_STACK_SIZE)));
    }

    final ItemStack item = new ItemStack(this.material, this.amount);

    try {
      this.configuration.setItem(item);
    } catch (final IOException e) {
      throw new CommandUsageException(this.getMessage("unable-to-read-configuration"));
    }

    final Object[] tokens = { this.material.name(), this.amount };
    sender.sendMessage(ChatColor.GREEN + this.getSimpleFormattedMessage("item-added-to-kit", tokens));

  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {
    this.amount = 1;

    if (arguments.length >= 1) {
      try {
        this.material = Material.valueOf(arguments[0].toUpperCase());
      } catch (final IllegalArgumentException exception) {
        throw new CommandArgumentException(this.getMessage("must-specify-valid-material"), this.getMessage("material-type-examples"));
      }
    } else if (arguments.length == 2) {
      try {
        this.amount = Integer.parseInt(arguments[1]);
      } catch (final NumberFormatException exception) {
        throw new CommandArgumentException(this.getMessage("must-specify-valid-amount"), this.getSimpleFormattedMessage("maximum-stack-size", String.valueOf(AddCommand.MAX_ITEM_STACK_SIZE)));
      }
    } else {
      throw new CommandArgumentException(this.getMessage("must-specify-valid-material"), this.getMessage("material-type-examples"));
    }

  }

  private void registerPermissions() {
    final String prefix = this.plugin.getDescription().getName().toLowerCase() + ".";
    // create the base permission
    final Permission base = new Permission(prefix + this.getName(), this.plugin.getMessage("addcommand-permission-description"), PermissionDefault.OP);
    base.addParent(this.plugin.getRootPermission(), true);
    this.addPermission(base);
  }

}
