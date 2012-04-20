package name.richardson.james.bukkit.starterkit.management;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.starterkit.StarterKit;
import name.richardson.james.bukkit.starterkit.StarterKitConfiguration;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.utilities.command.PluginCommand;

public class SaveCommand extends PluginCommand {

  private Player player;
  
  private final StarterKitConfiguration configuration;

  public SaveCommand(final StarterKit plugin) {
    super(plugin);
    this.configuration = plugin.getStarterKitConfiguration();
    this.registerPermissions();
  }

  private void registerPermissions() {
    final String prefix = this.plugin.getDescription().getName().toLowerCase() + ".";
    // create the base permission
    final Permission base = new Permission(prefix + this.getName(), this.plugin.getMessage("savecommand-permission-description"), PermissionDefault.OP);
    base.addParent(this.plugin.getRootPermission(), true);
    this.addPermission(base);
  }

  public void execute(CommandSender sender) throws CommandArgumentException, CommandPermissionException, CommandUsageException {
    final PlayerInventory inventory = player.getInventory();
    try {
      configuration.setInventory(inventory);
    } catch (IOException e) {
      throw new CommandUsageException(this.getMessage("unable-to-read-configuration"));
    }
    sender.sendMessage(ChatColor.GREEN + this.getMessage("inventory-saved"));
  }

  public void parseArguments(String[] arguments, CommandSender sender) throws CommandArgumentException {
    return;
  }
  
}
