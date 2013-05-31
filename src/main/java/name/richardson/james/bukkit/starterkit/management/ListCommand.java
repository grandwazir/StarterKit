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

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import name.richardson.james.bukkit.starterkit.StarterKit;
import name.richardson.james.bukkit.starterkit.StarterKitConfiguration;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandPermissions;
import name.richardson.james.bukkit.utilities.formatters.ChoiceFormatter;

@CommandPermissions(permissions = { "starterkit.list" })
public class ListCommand extends AbstractCommand {

	private final StarterKitConfiguration configuration;

	private final ChoiceFormatter formatter;

	public ListCommand(final StarterKit plugin) {
		super();
		this.configuration = plugin.getStarterKitConfiguration();
		this.formatter = new ChoiceFormatter();
		this.formatter.setLimits(0, 1, 2);
		this.formatter.setMessage("notice.list-header");
		this.formatter.setArguments(this.configuration.getItemCount());
		this.formatter.setFormats(this.getMessage("shared.choice.no-entries"), this.getMessage("shared.choice.one-entry"),
			this.getMessage("shared.choice.many-entries"));
	}

	public void execute(final List<String> arguments, final CommandSender sender) {
		sender.sendMessage(this.formatter.getMessage());
		if (this.configuration.getArmourKit().getItemCount() != 0) {
			sender.sendMessage(this.getMessage("notice.armour-list", this.buildKitList(this.configuration.getArmourKit().getContents())));
		}
		if (this.configuration.getInventoryKit().getItemCount() != 0) {
			sender.sendMessage(this.getMessage("notice.backpack-list", this.buildKitList(this.configuration.getInventoryKit().getContents())));
		}
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
