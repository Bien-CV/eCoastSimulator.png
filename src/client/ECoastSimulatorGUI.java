package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSeparator;

public class ECoastSimulatorGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ECoastSimulatorGUI window = new ECoastSimulatorGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ECoastSimulatorGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new MigLayout("", "[grow][][grow][][grow]", "[grow][grow][][grow]"));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, "cell 0 0,alignx left,growy");
		
		JSeparator separator = new JSeparator();
		panel_1.add(separator, "cell 1 0 1 4");
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, "cell 2 0,grow");
		
		JSeparator separator_2 = new JSeparator();
		panel_1.add(separator_2, "cell 3 0 1 2");
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5, "cell 4 1,grow");
		
		JSeparator separator_1 = new JSeparator();
		panel_1.add(separator_1, "cell 2 2 3 1");
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4, "cell 2 3,grow");
	}

}
