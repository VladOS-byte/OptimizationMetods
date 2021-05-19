package lab1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;


/**
 * Main class for minimization one-dimensional <code>Function</code> by different methods
 * @author Vladislav Pavlov
 * @author Danila Kuriabov
 * @author Daniil Monahov
 *
 */
public class Main {
	
	private static Function<Double, Double> function = new Function<>() {

		@Override
		public Double apply(Double x) {
			return 0.2 * x * Math.log(x) + Math.pow(x - 2.3, 2);
		}
		
		@Override
		public String toString() {
			return "f(x) = 0.2xlgx + (x - 2.3)^2";
		}
		
	};
	
	private static XYPlot plot;
	private final static double eps = 0.00004;
	
	private static OneDimensionMethod[] methods = {new DichotomyMethod(function), new GoldenCrossSection(function), 
			new FibonacciMethod(function), new ParabolicMethod(function), new CombineBrentMethod(function)};
	
	private static JPanel graphPanel;
	
	private static double leftBorder = 0.5, rightBorder = 2.5;

	private static XYSeries mainSeries;
	
	public static void main(String[] args) {
		
		if (args != null && args.length == 2) {
			try {
				leftBorder = Double.parseDouble(args[0]);
				rightBorder = Double.parseDouble(args[1]);
			} catch(NumberFormatException | NullPointerException ignored) {}
		}
		
		initComponents();
	}
	
	/**
	 * Initial components of GUI
	 */
	private static void initComponents() {
		
		JFrame frame = new JFrame("Одномерная минимизация функции");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		JPanel epsPanel = new JPanel(new GridLayout(4, 2));
		epsPanel.setMaximumSize(new Dimension(100, 60));
		
		JLabel epsilonLabel = new JLabel("ε (0, 1], ε >= 4δ");
		epsilonLabel.setMaximumSize(new Dimension(100, 20));
		epsPanel.add(epsilonLabel);
		
		JTextField epsilon = new JTextField(Double.toString(eps)); 
		epsilon.setPreferredSize(new Dimension(120, 20));
		epsPanel.add(epsilon);
		
		JLabel sigmaLabel = new JLabel("σ (0, 1]");
		sigmaLabel.setMaximumSize(new Dimension(100, 20));
		epsPanel.add(sigmaLabel);
		
		JTextField sigma = new JTextField("0.000000001"); 
		sigma.setPreferredSize(new Dimension(120, 20));
		epsPanel.add(sigma);
		
		JLabel deltaLabel = new JLabel("δ (0, 1], δ <= ε/4");
		deltaLabel.setMaximumSize(new Dimension(100, 20));
		epsPanel.add(deltaLabel);
		
		JTextField delta = new JTextField(Double.toString(eps / 4)); 
		delta.setPreferredSize(new Dimension(120, 20));
		epsPanel.add(delta);
		
		JLabel graphLabel = new JLabel("График");
		graphLabel.setMaximumSize(new Dimension(100, 20));
		epsPanel.add(graphLabel);
		
		JButton graphButton = new JButton("выкл");
		graphButton.setMaximumSize(new Dimension(120, 20));
		graphButton.setBackground(Color.GRAY);
		graphButton.addActionListener((event) -> {
			graphButton.setBackground(Color.DARK_GRAY);
			if (graphButton.getText() == "вкл") {
				graphButton.setText("выкл");
				plot.setDataset(0, new XYSeriesCollection(mainSeries));
			} else {
				graphButton.setText("вкл");
				plot.setDataset(0, new XYSeriesCollection());
			}
			graphButton.setBackground(Color.GRAY);
		});
		epsPanel.add(graphButton);
		
		JPanel buttonPanel = new JPanel(new GridLayout(methods.length, 1));
		buttonPanel.setMaximumSize(new Dimension(500, methods.length * 20));
		

		
		mainSeries = new XYSeries(function.toString());
		createGraph(mainSeries, function, eps);

		graphPanel = new JPanel();
		graphPanel.setMaximumSize(new Dimension(500, 600));
		
		JFreeChart chart = ChartFactory.createXYLineChart("Выберите метод", "x", "f(x)", 
				new XYSeriesCollection(), PlotOrientation.VERTICAL, true, true, false);
		
		JButton[] buttons = new JButton[methods.length];
		
		for (int i = 0; i < methods.length; i++) {
			final int buttonIndex = i;
			buttons[buttonIndex] = new JButton(methods[buttonIndex].getName());
			buttons[buttonIndex].setPreferredSize(new Dimension(300, 20));
			buttons[buttonIndex].setBackground(Color.GRAY);
			buttons[buttonIndex].addActionListener((event) -> {
				buttons[buttonIndex].setBackground(Color.DARK_GRAY);
				if (isValidArgs(epsilon, sigma, delta)) {
					chart.setTitle(methods[buttonIndex].getName());
					Settings sets = new Settings(Double.parseDouble(epsilon.getText()),
							Double.parseDouble(sigma.getText()), 
							Double.parseDouble(delta.getText()));
					onClick(buttonIndex, sets);
				}
				buttons[buttonIndex].setBackground(Color.GRAY);
			});
			
			buttonPanel.add(buttons[buttonIndex]);
		}
		
		plot = chart.getXYPlot();
		plot.setDataset(new XYSeriesCollection(mainSeries));
		
		graphPanel.add(new ChartPanel(chart));

		panel.add(epsPanel);
		panel.add(buttonPanel);
		panel.add(graphPanel);
		
		frame.setContentPane(panel);
		frame.setMaximumSize(new Dimension(700, 700));
		frame.setMinimumSize(new Dimension(700, 700));
		frame.show();
	}
	
	
	private static void createGraph(XYSeries series, Function<Double, Double> func, double step) {
		for (double i = leftBorder; i <= rightBorder; i += step) {
			series.add(i, func.apply(i));
		}
	}

	/**
	 * Check {@link #isValidDouble(double)} for <code>Parameters</code>
	 * @param epsilon
	 * @param sigma
	 * @param delta
	 * @return
	 */
	private static boolean isValidArgs(JTextField epsilon, JTextField sigma, JTextField delta) {
		try {
			boolean valid = true;
			
			epsilon.setBackground(Color.RED);
			double value = Double.parseDouble(epsilon.getText());
			valid &= isValidDouble(value);
			epsilon.setBackground(isValidDouble(value) ? Color.WHITE : Color.RED);
			double eps = value;
			
			sigma.setBackground(Color.RED);
			value = Double.parseDouble(sigma.getText());
			valid &= isValidDouble(value);
			sigma.setBackground(isValidDouble(value) ? Color.WHITE : Color.RED);
			
			delta.setBackground(Color.RED);
			value = Double.parseDouble(delta.getText());
			valid &= isValidDouble(value);
			delta.setBackground(isValidDouble(value) ? Color.WHITE : Color.RED);
			
			if (value * 4 > eps) {
				epsilon.setBackground(Color.RED);
				delta.setBackground(Color.RED);
				valid = false;
			}
			
			return valid;
		} catch (Exception ignored) {}
		
		return false;
	}
	

	/** check value in range(0,1]
	 * @param value
	 * @return
	 */
	private static boolean isValidDouble(double value) {
		return 0 < value && value <= 1;
	}

	/**
	 * Minimize <code>function</code> by <code>i</code> method
	 * @param i - <code>index</code> of method (and method's button)
	 * @param settings - <code>Settings</code> of epsilon, sigma and delta
	 */
	private static void onClick(int i, Settings sets) {
		XYSeries series = new XYSeries("Opt." + function.toString());
		
		List<StepFrame<Double>> frameList = new ArrayList<>();
		List<Point<Double>> list = new ArrayList<>();

		ValueMarker marker = new ValueMarker(methods[i].minimize(frameList, leftBorder, rightBorder, sets));

		DecimalFormat df = new DecimalFormat("0.000000000");
		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(methods[i].toString() + ".txt"))) {
			for (StepFrame<Double> frame : frameList) {
				list.add(frame.x);
				bufferedWriter.write(
						frame.x.key + " "
								+ df.format(frame.x.x) + " "
								+ df.format(frame.x.y) + " "
								+ df.format(frame.leftBorder) + " "
								+ df.format(frame.rightBorder));
				bufferedWriter.newLine();
			}
		} catch (IOException ignored) {
			// No operations.
		}
		marker.setPaint(Color.BLUE);
		
		double minX = marker.getValue();
		double minF = function.apply(minX);
		
		marker.setLabelBackgroundColor(Color.WHITE);
		marker.setLabel("Minimum: (" + String.format("%.4f", minX) + ", " + 
				String.format("%.4f", minF) + ")");
		marker.setLabelPaint(Color.BLUE);
		marker.setLabelFont(new Font("Dialog", Font.PLAIN, 15));
		marker.setLabelAnchor(RectangleAnchor.CENTER);

		list.forEach((point) -> {
			series.add(point.x, point.y);
		});
		
		series.add(minX, minF);
		
//		graphPanel.removeAll();
		
//		JFreeChart chart = ChartFactory.createXYLineChart(methods[i].getName(), "x", "f(x)", 
//				new XYSeriesCollection(), PlotOrientation.VERTICAL, true, true, false);
//		plot = chart.getXYPlot();
		plot.clearDomainMarkers();
		
		plot.addDomainMarker(marker);
		
		for (int k = 1; plot.getDataset(k) != null; ++k) {
			plot.setDataset(k, new XYSeriesCollection());
		}
		
//		plot.setDataset(0, new XYSeriesCollection(mainSeries));
//		plot.setRenderer(0, new StandardXYItemRenderer());
		plot.setDataset(1, new XYSeriesCollection(series));
		plot.setRenderer(1, new StandardXYItemRenderer());
		
		int maxKey = 0;
		
		int parabol = 2;
		for (int j = 0; j < list.size(); j++) {
			marker = new ValueMarker(list.get(j).x);
			int c = (256 / (list.get(list.size() - 1).key + 1) * list.get(j).key) % 256;
			Color color = new Color((128 + c) % 256, c, 255 - c);
			marker.setPaint(color);
			marker.setLabel(Integer.toString(list.get(j).key));
			maxKey = Math.max(list.get(j).key, maxKey);
			marker.setLabelPaint(color);
			marker.setLabelFont(new Font("Dialog", Font.PLAIN, 12));
			marker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
			plot.addDomainMarker(marker);
			List<Double> abc;
			if (methods[i] instanceof ParabolicMethod && 
					(abc = ((ParabolicMethod) methods[i]).parabols.get(list.get(j).key)) != null) {
				System.out.println(abc);
				XYSeries parabolicSeries = new XYSeries("");
				createGraph(parabolicSeries, new Function<>() {

					@Override
					public Double apply(Double x) {
						return abc.get(0) * x * x + abc.get(1) * x + abc.get(2);
					}
					
				}, eps);
				
				plot.setDataset(parabol, new XYSeriesCollection(parabolicSeries));
				plot.setRenderer(parabol++, new StandardXYItemRenderer());
			}
		}
		
		System.out.print("(" + Math.round(Math.abs(Math.log(sets.epsilon))) + ";" + (maxKey + 1) + ") ");
		
//		graphPanel.add(new ChartPanel(chart));
		graphPanel.updateUI();
	}
}
