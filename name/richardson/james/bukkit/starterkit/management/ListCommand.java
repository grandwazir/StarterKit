/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * ListCommand.java is part of StarterKit.
 * 
 * StarterKit is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * StarterKit is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with StarterKit.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.starterkit.management;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.starterkit.StarterKit;
import name.richardson.james.bukkit.util.command.CommandUsageException;
import name.richardson.james.bukkit.util.command.PlayerCommand;

public class ListCommand extends PlayerCommand {

  public static final String NAME = "list";
  public static final String DESCRIPTION = "List items to the starting kit.";
  public static final String PERMISSION_DESCRIPTION = "Allow users to list items to the starting kit.";
  public static final String USAGE = "";

  public static final Permission PERMISSION = new Permission("starterkit.list", PERMISSION_DESCRIPTION, PermissionDefault.OP);

  private final StarterKit plugin;

  public ListCommand(StarterKit plugin) {
    super(plugin, NAME, DESCRIPTION, USAGE, PERMISSION_DESCRIPTION, PERMISSION);
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, Map<String, Object> arguments) throws CommandUsageException {
    String list = buildKitList();
    sender.sendMessage(ChatColor.YELLOW + list);
  }

  private String buildKitList() {
    StringBuilder message = new StringBuilder();
    message.append("Currently contains: ");
    for (ItemStack item : plugin.getKit()) {
      message.append(item.getAmount());
      message.append(" ");
      message.append(item.getType().name());
      message.append(", ");
    }
    message.delete(message.length() - 2, message.length());
    message.append(".");
    return message.toString();
  }

}
