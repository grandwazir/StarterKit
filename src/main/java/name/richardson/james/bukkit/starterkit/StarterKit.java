/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * StarterKit.java is part of StarterKit.
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
package name.richardson.james.bukkit.starterkit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.serialization.ConfigurationSerialization;

import name.richardson.james.bukkit.starterkit.kit.ArmourKit;
import name.richardson.james.bukkit.starterkit.kit.InventoryKit;
import name.richardson.james.bukkit.starterkit.management.ListCommand;
import name.richardson.james.bukkit.starterkit.management.LoadCommand;
import name.richardson.james.bukkit.starterkit.management.SaveCommand;
import name.richardson.james.bukkit.utilities.command.CommandManager;
import name.richardson.james.bukkit.utilities.plugin.AbstractPlugin;

public class StarterKit extends AbstractPlugin {

	private StarterKitConfiguration configuration;

	public StarterKit() {
		ConfigurationSerialization.registerClass(ArmourKit.class);
		ConfigurationSerialization.registerClass(InventoryKit.class);
	}

	public String getArtifactID() {
		return "starter-kit";
	}

	public StarterKitConfiguration getStarterKitConfiguration() {
		return this.configuration;
	}

	public String getVersion() {
		return this.getDescription().getVersion();
	}

	@Override
	public void onEnable() {
		try {
			this.loadConfiguration();
			this.setPermissions();
			this.registerCommands();
			this.registerListeners();
			this.setupMetrics();
			this.updatePlugin();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void loadConfiguration() throws IOException {
		super.loadConfiguration();
		final File file = new File(this.getDataFolder().getAbsolutePath() + File.separatorChar + "config.yml");
		final InputStream defaults = this.getResource("config.yml");
		this.configuration = new StarterKitConfiguration(file, defaults);
	}

	protected void registerCommands() {
		final CommandManager commandManager = new CommandManager("sk");
		commandManager.addCommand(new ListCommand(this));
		commandManager.addCommand(new LoadCommand(this));
		commandManager.addCommand(new SaveCommand(this));
	}

	protected void registerListeners() {
		new PlayerListener(this);
	}

	@Override
	protected void setupMetrics() throws IOException {
		if (this.configuration.isCollectingStats()) {
			new MetricsListener(this);
		}
	}

}
