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
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import name.richardson.james.bukkit.starterkit.StarterKit;
import name.richardson.james.bukkit.starterkit.StarterKitConfiguration;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandPermissions;

@CommandPermissions(permissions = { "starterkit.save" })
public class SaveCommand extends AbstractCommand {

	/** The inventory of the player we are using as a template */
	private PlayerInventory inventory;

	private final StarterKitConfiguration configuration;

	public SaveCommand(final StarterKit plugin) {
		super();
		this.configuration = plugin.getStarterKitConfiguration();
	}

	public void execute(final List<String> arguments, final CommandSender sender) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(this.getMessage("error.player-command-sender-required"));
		} else {
			try {
				final Player player = (Player) sender;
				this.inventory = player.getInventory();
				this.configuration.setInventory(this.inventory);
				sender.sendMessage(this.getMessage("notice.kit-saved"));
			} catch (final IOException e) {
				sender.sendMessage(this.getMessage("error.unable-to-save-kit"));
			}
		}
	}
}
