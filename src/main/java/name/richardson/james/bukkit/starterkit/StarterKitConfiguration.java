/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * StarterKitConfiguration.java is part of StarterKit.
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

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.PlayerInventory;

import name.richardson.james.bukkit.starterkit.kit.ArmourKit;
import name.richardson.james.bukkit.starterkit.kit.InventoryKit;
import name.richardson.james.bukkit.utilities.persistence.configuration.SimplePluginConfiguration;

public class StarterKitConfiguration extends SimplePluginConfiguration {

	private InventoryKit inventory;
	private ArmourKit armour;

	public StarterKitConfiguration(final File file, final InputStream defaults) throws IOException {
		super(file, defaults);
		this.setDefaultKit();
		final ConfigurationSection section = this.getConfiguration().getConfigurationSection("kit");
		this.armour = (ArmourKit) section.get("armour");
		this.inventory = (InventoryKit) section.get("backpack");
	}

	public ArmourKit getArmourKit() {
		return this.armour;
	}

	public InventoryKit getInventoryKit() {
		return this.inventory;
	}

	public int getItemCount() {
		return this.armour.getItemCount() + this.inventory.getItemCount();
	}

	public boolean isProvidingKitOnDeath() {
		return this.getConfiguration().getBoolean("provide-kit-on-death");
	}

	public void setInventory(final PlayerInventory inventory) throws IOException {
		final ConfigurationSection section = this.getConfiguration().getConfigurationSection("kit");
		this.inventory = new InventoryKit(inventory);
		section.set("backpack", this.inventory);
		this.armour = new ArmourKit(inventory);
		section.set("armour", this.armour);
		this.save();
	}

	private void setDefaultKit()
	throws IOException {
		if (!this.getConfiguration().isConfigurationSection("kit")) {
			final ConfigurationSection section = this.getConfiguration().createSection("kit");
			section.set("backpack", new InventoryKit());
			section.set("armour", new ArmourKit());
		}
		this.save();
	}

}
