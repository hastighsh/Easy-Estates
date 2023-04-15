package UI.UI;

import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYSeriesCollection;

public interface Charts {
    public String toString();
    public ChartPanel getChart();
    public void update(XYSeriesCollection x);
}
