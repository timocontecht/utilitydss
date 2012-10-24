package org.visico.utilitydss.server.processsim;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

public class MyGanttRenderer extends GanttRenderer {

    private static final int PASS = 2; // assumes two passes
    private final List<Color> clut = new ArrayList<Color>();
    private final TaskSeriesCollection model;
    private int row;
    private int col;
    private int index;

    public MyGanttRenderer(TaskSeriesCollection model) {
        this.model = model;
    }

    @Override
    public Paint getItemPaint(int row, int col) {
        if (clut.isEmpty() || this.row != row || this.col != col) {
            initClut(row, col);
            this.row = row;
            this.col = col;
            index = 0;
        }
        int clutIndex = index++ / PASS;
        return clut.get(clutIndex);
    }

    private void initClut(int row, int col) {
        clut.clear();
        Color c = (Color) super.getItemPaint(row, col);
        float[] a = new float[3];
        Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), a);
        TaskSeries series = (TaskSeries) model.getRowKeys().get(row);
        List<Task> tasks = series.getTasks(); // unchecked
        Iterator<Task> taskIt = tasks.iterator();
        while (taskIt.hasNext())
        {
        	Task t = taskIt.next();
        	if (t.getDescription().contains("Section"))
        	{
        		clut.add(Color.BLUE);
        	}
        	else
        		clut.add(Color.GREEN);
        }
    }
}