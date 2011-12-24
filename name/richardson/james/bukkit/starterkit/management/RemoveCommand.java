/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * ReloadCommand.java is part of TimedMessages.
 * 
 * TimedMessages is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * TimedMessages is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * TimedMessages. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.starterkit.management;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.starterkit.StarterKit;
import name.richardson.james.bukkit.util.command.CommandArgumentException;
import name.richardson.james.bukkit.util.command.CommandUsageException;
import name.richardson.james.bukkit.util.command.PlayerCommand;

public class RemoveCommand extends PlayerCommand {

  public static final String NAME = "remove";
  public static final String DESCRIPTION = "Remove items to the starting kit.";
  public static final String PERMISSION_DESCRIPTION = "Allow users to remove items to the starting kit.";
  public static final String USAGE = "<item>";

  public static final Permission PERMISSION = new Permission("starterkit.remove", PERMISSION_DESCRIPTION, PermissionDefault.OP);

  private final StarterKit plugin;

  public RemoveCommand(StarterKit plugin) {
    super(plugin, NAME, DESCRIPTION, USAGE, PERMISSION_DESCRIPTION, PERMISSION);
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, Map<String, Object> arguments) throws CommandUsageException {
    final Material item = (Material) arguments.get("item");
    try {
      plugin.removeItem(item);
    } catch (IOException exception) {
      // assume we are broken at this point and disable ourselves.
      this.plugin.getPluginLoader().disablePlugin(plugin);
      throw new CommandUsageException("Unable to reload configuration!");
    }
    sender.sendMessage(String.format(ChatColor.GREEN + "%s have been removed from the kit.", item.name()));
  }

  @Override
  public Map<String, Object> parseArguments(final List<String> arguments) throws CommandArgumentException {
    HashMap<String, Object> map = new HashMap<String, Object>();

    try {
      final Material item = Material.valueOf(arguments.remove(0).toUpperCase());
      map.put("item", item);
    } catch (IllegalArgumentException exception) {
      throw new CommandArgumentException("You must specify a valid item type!", "For example WOOD_AXE or TORCH.");
    } catch (IndexOutOfBoundsException exception) {
      throw new CommandArgumentException("You must specify a type of item!", "For example WOOD_AXE or TORCH.");
    }

    return map;
  }

}
