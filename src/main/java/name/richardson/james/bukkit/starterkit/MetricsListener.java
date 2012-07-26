package name.richardson.james.bukkit.starterkit;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import name.richardson.james.bukkit.utilities.metrics.AbstractMetricsListener;
import name.richardson.james.bukkit.utilities.metrics.Metrics;
import name.richardson.james.bukkit.utilities.metrics.Metrics.Graph;
import name.richardson.james.bukkit.utilities.metrics.Metrics.Plotter;

public class MetricsListener extends AbstractMetricsListener {
  
  private Metrics metrics;

  private int kitsAwarded = 0;
  
  public MetricsListener(JavaPlugin plugin) throws IOException {
    super(plugin);
  }
  
  protected void setupCustomMetrics() {
    // Create a graph to show the total amount of kits issued.
    Graph graph = this.metrics.createGraph("Usage Statistics");
    graph.addPlotter(new Plotter("Total kits issued") {
      @Override
      public int getValue() {
        int i = kitsAwarded;
        return i;
      }
    });
  }
  
  
  
}
