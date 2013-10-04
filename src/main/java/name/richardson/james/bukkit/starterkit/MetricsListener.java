package name.richardson.james.bukkit.starterkit;

import java.io.IOException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.mcstats.Metrics;

import name.richardson.james.bukkit.utilities.listener.AbstractListener;

public class MetricsListener extends AbstractListener {

	/* Number of kits awarded since the server started */
	private int kitsAwarded = 0;

	/* Number of items awarded since the server started */
	private int itemsAwarded = 0;

	private final Metrics metrics;

	public MetricsListener(final JavaPlugin plugin, PluginManager pluginManager) throws IOException {
		super(plugin, pluginManager);
		this.metrics = new Metrics(plugin);
		this.setupUsageStatistics();
		this.metrics.start();
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onKitAwarded(final StarterKitGrantedEvent event) {
		this.kitsAwarded++;
		this.itemsAwarded += event.getInventoryItemCount();
	}

	private void setupUsageStatistics() {
		// Create a graph to show the total amount of kits issued.
		final Metrics.Graph graph = this.metrics.createGraph("Usage Statistics");
		graph.addPlotter(new Metrics.Plotter("Total kits issued") {
			@Override
			public int getValue() {
				final int i = MetricsListener.this.kitsAwarded;
				return i;
			}
		});
		graph.addPlotter(new Metrics.Plotter("Total items issued") {
			@Override
			public int getValue() {
				final int i = MetricsListener.this.itemsAwarded;
				return i;
			}
		});
	}

}
