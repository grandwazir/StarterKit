/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * ListCommand.java is part of StarterKit.
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

import org.bukkit.ChatColor;
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
public class ListCommand extends PluginCommand {

  private final StarterKitConfiguration configuration;

  public ListCommand(final StarterKit plugin) {
    super(plugin);
    this.configuration = plugin.getStarterKitConfiguration();
    this.registerPermissions();
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {

    sender.sendMessage(ChatColor.LIGHT_PURPLE + this.getFormattedListHeader());
    if (this.configuration.getArmourKit().getContents().length != 0) {
      sender.sendMessage(ChatColor.YELLOW + plugin.getSimpleFormattedMessage("armour-list", this.buildKitList(this.configuration.getInventoryKit().getContents())));
    }
    if (this.configuration.getInventoryKit().getContents().length != 0) {
      sender.sendMessage(ChatColor.YELLOW + plugin.getSimpleFormattedMessage("backpack-list", this.buildKitList(this.configuration.getInventoryKit().getContents())));
    }
  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {
    return;
  }

  private String buildKitList(ItemStack[] items) {
    final StringBuilder message = new StringBuilder();
    for (final ItemStack item : items) {
      message.append(item.getAmount());
      message.append(" ");
      message.append(item.getType().name());
      message.append(", ");
    }
    message.delete(message.length() - 2, message.length());
    message.append(".");
    return message.toString();
  }

  private String getFormattedListHeader() {
    final Object[] arguments = { this.configuration.getItemCount() };
    final double[] limits = { 0, 1, 2 };
    final String[] formats = { this.getMessage("no-entries"), this.getMessage("one-entry"), this.getMessage("many-entries") };
    return this.getChoiceFormattedMessage("kit-summary", arguments, formats, limits);
  }

  private void registerPermissions() {
    final String prefix = this.plugin.getDescription().getName().toLowerCase() + ".";
    // create the base permission
    final Permission base = new Permission(prefix + this.getName(), this.plugin.getMessage("listcommand-permission-description"), PermissionDefault.OP);
    base.addParent(this.plugin.getRootPermission(), true);
    this.addPermission(base);
  }

}
