/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * ReloadCommand.java is part of StarterKit.
 * 
 * StarterKit is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * StarterKit is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with StarterKit.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.starterkit.management;

import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.starterkit.StarterKit;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.utilities.command.PluginCommand;

public class ReloadCommand extends PluginCommand {

  private final StarterKit plugin;

  public ReloadCommand(StarterKit plugin) {
    super(plugin);
    this.plugin = plugin;
    this.registerPermissions();
  }


  public void execute(CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    
    try {
      this.plugin.reload();
    } catch (IOException e) {
      throw new CommandUsageException(this.getMessage("unable-to-read-configuration"));
    }
    
    sender.sendMessage(this.getSimpleFormattedMessage("plugin-reloaded", plugin.getDescription().getName()));
    
  }
  
  private void registerPermissions() {
    final String prefix = plugin.getDescription().getName().toLowerCase() + ".";
    // create the base permission
    Permission base = new Permission(prefix + this.getName(), plugin.getMessage("reloadcommand-permission-description"), PermissionDefault.OP);
    base.addParent(plugin.getRootPermission(), true);
    this.addPermission(base);
  }
  

  public void parseArguments(String[] arguments, CommandSender sender) throws CommandArgumentException {
    return;
  }

}
