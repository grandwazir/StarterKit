/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
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

import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import name.richardson.james.bukkit.starterkit.StarterKit;
import name.richardson.james.bukkit.starterkit.StarterKitConfiguration;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.utilities.command.ConsoleCommand;
import name.richardson.james.bukkit.utilities.formatters.ChoiceFormatter;

@ConsoleCommand
public class ListCommand extends AbstractCommand {

  private final StarterKitConfiguration configuration;
  
  private final ChoiceFormatter formatter;

  public ListCommand(final StarterKit plugin) {
    super(plugin, false);
    this.configuration = plugin.getStarterKitConfiguration();
    this.formatter = new ChoiceFormatter(this.getLocalisation());
    this.formatter.setLimits(0, 1, 2);
    this.formatter.setMessage(this, "header");
    this.formatter.setArguments(this.configuration.getItemCount());
    this.formatter.setFormats(
        this.getLocalisation().getMessage(this, "no-entries"), 
        this.getLocalisation().getMessage(this, "one-entry"), 
        this.getLocalisation().getMessage(this, "many-entries")
    );
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    sender.sendMessage(this.formatter.getMessage());
    if (this.configuration.getArmourKit().getItemCount() != 0) {
      sender.sendMessage(this.getLocalisation().getMessage(this, "armour-list", this.buildKitList(this.configuration.getArmourKit().getContents())));
    }
    if (this.configuration.getInventoryKit().getItemCount() != 0) {
      sender.sendMessage(this.getLocalisation().getMessage(this, "backpack-list", this.buildKitList(this.configuration.getInventoryKit().getContents())));
    }
  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {
    return;
  }

  private String buildKitList(final ItemStack[] items) {
    final StringBuilder message = new StringBuilder();
    for (final ItemStack item : items) {
      if (item == null) {
        continue;
      }
      if (item.getAmount() == 0) {
        message.append(1);
      } else {
        message.append(item.getAmount());
      }
      message.append(" ");
      message.append(item.getType().name());
      message.append(", ");
    }
    message.delete(message.length() - 2, message.length());
    message.append(".");
    return message.toString();
  }

}
