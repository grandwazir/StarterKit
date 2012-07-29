package name.richardson.james.bukkit.starterkit;

import java.io.IOException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.metrics.AbstractMetricsListener;
import name.richardson.james.bukkit.utilities.metrics.Metrics.Graph;
import name.richardson.james.bukkit.utilities.metrics.Metrics.Plotter;

public class MetricsListener extends AbstractMetricsListener {

  /* Number of kits awarded since the server started */
  private int kitsAwarded = 0;
  
  /* Number of items awarded since the server started */
  private int itemsAwarded = 0;
  
  public MetricsListener(JavaPlugin plugin) throws IOException {
    super(plugin);
    this.setupUsageStatistics();
    this.metrics.start();
  }
  
  @EventHandler(priority = EventPriority.MONITOR)
  public void onKitAwarded(StarterKitGrantedEvent event) {
    kitsAwarded++;
    itemsAwarded+= event.getInventoryItemCount();
  }
  
  private void setupUsageStatistics() {
    // Create a graph to show the total amount of kits issued.
    Graph graph = this.metrics.createGraph("Usage Statistics");
    graph.addPlotter(new Plotter("Total kits issued") {
      @Override
      public int getValue() {
        int i = kitsAwarded;
        return i;
      }
    });
    graph.addPlotter(new Plotter("Total items issued") {
      @Override
      public int getValue() {
        int i = itemsAwarded;
        return i;
      }
    });
  }
  
}
