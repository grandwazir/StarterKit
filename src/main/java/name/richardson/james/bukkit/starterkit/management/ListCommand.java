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

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permissible;

import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.formatters.ChoiceFormatter;
import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.formatters.DefaultColourFormatter;
import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;

import name.richardson.james.bukkit.starterkit.StarterKitConfiguration;
import name.richardson.james.bukkit.starterkit.utilities.formatters.ItemCountChoiceFormatter;

public class ListCommand extends AbstractCommand {

	public static final String PERMISSION_ALL = "starterkit.list";

	private static final String ARMOUR_LIST_KEY = "armour-list";
	private static final String BACKPACK_LIST_KEY = "backpack-list";
	private static final String HEADER_KEY = "header";
	private static final String NO_PERMISSION_KEY = "no-permission";

	private final StarterKitConfiguration configuration;
	private final ChoiceFormatter choiceFormatter = new ItemCountChoiceFormatter();
	private final Localisation localisation = new ResourceBundleByClassLocalisation(ListCommand.class);
	private final ColourFormatter colourFormatter = new DefaultColourFormatter();

	public ListCommand(final StarterKitConfiguration configuration) {
		this.configuration = configuration;
		this.choiceFormatter.setMessage(colourFormatter.format(localisation.getMessage(HEADER_KEY), ColourFormatter.FormatStyle.HEADER));
	}

	/**
	 * Execute a command using the provided {@link name.richardson.james.bukkit.utilities.command.context.CommandContext}.
	 *
	 * @param commandContext the command context to execute this command within.
	 * @since 6.0.0
	 */
	@Override
	public void execute(CommandContext commandContext) {
		if (isAuthorised(commandContext.getCommandSender())) {
			this.choiceFormatter.setArguments(configuration.getItemCount());
			commandContext.getCommandSender().sendMessage(this.choiceFormatter.getMessage());
			if (this.configuration.getArmourKit().getItemCount() != 0) {
				commandContext.getCommandSender().sendMessage(ChatColor.YELLOW + localisation.getMessage(ARMOUR_LIST_KEY, this.buildKitList(this.configuration.getArmourKit().getContents())));
			}
			if (this.configuration.getInventoryKit().getItemCount() != 0) {
				commandContext.getCommandSender().sendMessage(ChatColor.YELLOW + localisation.getMessage(BACKPACK_LIST_KEY, this.buildKitList(this.configuration.getInventoryKit().getContents())));
			}
		} else {
			commandContext.getCommandSender().sendMessage(colourFormatter.format(localisation.getMessage(NO_PERMISSION_KEY), ColourFormatter.FormatStyle.ERROR));
		}
	}

	/**
	 * Returns {@code true} if the user is authorised to use this command.
	 * <p/>
	 * Authorisation does not guarantee that the user may use all the features associated with a command.
	 *
	 * @param permissible the permissible requesting authorisation
	 * @return {@code true} if the user is authorised; {@code false} otherwise
	 * @since 6.0.0
	 */
	@Override
	public boolean isAuthorised(Permissible permissible) {
		if (permissible.hasPermission(PERMISSION_ALL)) return true;
		return false;
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
