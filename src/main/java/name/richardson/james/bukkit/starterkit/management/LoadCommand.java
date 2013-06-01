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

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import name.richardson.james.bukkit.starterkit.StarterKit;
import name.richardson.james.bukkit.starterkit.StarterKitConfiguration;
import name.richardson.james.bukkit.utilities.command.AbstractCommand;
import name.richardson.james.bukkit.utilities.command.CommandPermissions;

@CommandPermissions(permissions = { "starterkit.load" })
public class LoadCommand extends AbstractCommand {

	private final StarterKitConfiguration configuration;

	public LoadCommand(final StarterKit plugin) {
		super();
		this.configuration = plugin.getStarterKitConfiguration();
	}

	public void execute(final List<String> arguments, final CommandSender sender) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(this.getMessage("error.player-command-sender-required"));
		} else {
			final Player player = (Player) sender;
			final ItemStack[] inventory = this.configuration.getInventoryKit().getContents();
			final ItemStack[] armour = this.configuration.getArmourKit().getContents();
			player.getInventory().setContents(inventory);
			player.getInventory().setArmorContents(armour);
			sender.sendMessage(this.getMessage("notice.kit-loaded"));
		}
	}

}
