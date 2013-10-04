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

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permissible;

import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.context.CommandContext;
import name.richardson.james.bukkit.utilities.formatters.ColourFormatter;
import name.richardson.james.bukkit.utilities.formatters.DefaultColourFormatter;
import name.richardson.james.bukkit.utilities.localisation.Localisation;
import name.richardson.james.bukkit.utilities.localisation.ResourceBundleByClassLocalisation;

import name.richardson.james.bukkit.starterkit.StarterKitConfiguration;

public class LoadCommand extends AbstractCommand {

	public static final String PERMISSION_ALL = "starterkit.load";

	private static final String PLAYER_REQUIRED_KEY = "player-command-sender-required";
	private static final String KIT_LOADED = "kit-loaded";
	private static final String NO_PERMISSION_KEY = "no-permission";

	private final StarterKitConfiguration configuration;
	private final Localisation localisation = new ResourceBundleByClassLocalisation(LoadCommand.class);
	private final ColourFormatter colourFormatter = new DefaultColourFormatter();

	public LoadCommand(final StarterKitConfiguration configuration) {
		this.configuration = configuration;
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
			if (!(commandContext.getCommandSender() instanceof Player)) {
				commandContext.getCommandSender().sendMessage(colourFormatter.format(localisation.getMessage(PLAYER_REQUIRED_KEY), ColourFormatter.FormatStyle.ERROR));
			} else {
				final Player player = (Player) commandContext.getCommandSender();
				final ItemStack[] inventory = this.configuration.getInventoryKit().getContents();
				final ItemStack[] armour = this.configuration.getArmourKit().getContents();
				player.getInventory().setContents(inventory);
				player.getInventory().setArmorContents(armour);
				player.sendMessage(colourFormatter.format(localisation.getMessage(KIT_LOADED), ColourFormatter.FormatStyle.INFO));
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

}
