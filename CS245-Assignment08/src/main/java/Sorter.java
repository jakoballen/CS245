import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import javax.swing.border.Border;

public class Sorter extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 400;
	private static final int HEIGHT = 300;

	private JPanel panel;
	private JTextArea display;
	public JButton sortButton;
	private JComboBox<String> sortingAlgorithm;
	private JLabel title;
	private JLabel loadingIcon;
	private String[] sortingAlgorithms = new String[]{"Selection Sort","Insertion Sort", "Merge Sort"};
	private Sorter sorter = this;

	public static final String SORT = "Sort";
	public static final String SELECTION_SORT_TEXT = "Selection Sort";
	public static final String INSERTION_SORT_TEXT = "Insertion Sort";
	public static final String MERGE_SORT_TEXT = "Merge Sort";
	private static final String DISPLAY_HEADER_TEXT = "N\t\tRuntime (ms)";

	public Sorter() {
		setTitle("Sorter");
		setSize(WIDTH,HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		createContents();
		setVisible(true);

	}

	private void createContents() {
		//TODO: Implement

		SortButtonListener listener = new SortButtonListener();

		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		display = new JTextArea();
		display.setEditable(false);
		display.setEnabled(false);
		display.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		sortButton = new JButton(SORT);
		sortButton.addActionListener(listener);
		sortingAlgorithm = new JComboBox<>(new String[]{SELECTION_SORT_TEXT,INSERTION_SORT_TEXT, MERGE_SORT_TEXT});
		sortingAlgorithm.addActionListener(listener);
		title = new JLabel("Sorting algorithm: ");
		loadingIcon = new JLabel(new ImageIcon("loading.gif"));
		loadingIcon.setVisible(false);
		loadingIcon.setPreferredSize(new Dimension(25,25));

		panel.add(title);
		panel.add(sortingAlgorithm);
		panel.add(sortButton);
		panel.add(loadingIcon);

		add(panel, BorderLayout.NORTH);
		add(display, BorderLayout.CENTER);
	}

	private class SortButtonListener implements ActionListener {
		private int[] arr;
		private SortRunnable sr;
		private int index;
		private String algorithm;



		public void actionPerformed(ActionEvent e) {
			ExecutorService es = Executors.newSingleThreadExecutor();

			//TODO: Finish Implementation
			if(e.getSource() == sortingAlgorithm){
				index = sortingAlgorithm.getSelectedIndex();
			}else{

				display.setText(DISPLAY_HEADER_TEXT);
				sortingAlgorithm.setEnabled(false);
				sortButton.setEnabled(false);
				loadingIcon.setVisible(true);

				/*es.execute(new Runnable() {
					@Override
					public void run() {
						for (int i =0; i<=8; i++){
							arr = new int[(int)Math.pow(2,i)*1000];
							fillArr();
							sr = new SortRunnable(sortingAlgorithms[index], arr, sorter);
							sr.run();


						}

					}
				});*/
				for(int i =0; i<=8; i++){
					arr = new int[(int)Math.pow(2,i)*1000];
					fillArr();
					sr = new SortRunnable(sortingAlgorithms[index], arr, sorter);
					es.execute(sr);
				}

			}
			es.shutdown();
	    }

		private void fillArr() {
			Random r = new Random();
			for(int i=0; i<arr.length; ++i) {
				arr[i] = r.nextInt();
			}
		}
	}

	public synchronized void displayResult(int n, long runtime) {
		display.append("\n"+n+"\t\t"+runtime);
		if(n==256000){
			loadingIcon.setVisible(false);
			sortingAlgorithm.setEnabled(true);
			sortButton.setEnabled(true);
		}
	}

	public static void main(String[] args) {
		new Sorter();
	}
}