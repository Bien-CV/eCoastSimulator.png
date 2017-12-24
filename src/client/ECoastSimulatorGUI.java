package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSeparator;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.JSplitPane;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.SwingConstants;

@SuppressWarnings("unused")
public class ECoastSimulatorGUI {

	private JFrame frame;
	private JTextField saisieChat;

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
		frame.setBounds(100, 100, 630, 456);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{428, 0};
		gridBagLayout.rowHeights = new int[]{34, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(panel, gbc_panel);
		
		JLabel lblJeSuisUne = new JLabel("Je suis une top bar !");
		panel.add(lblJeSuisUne);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridheight = 2;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		frame.getContentPane().add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_2.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_9 = new JPanel();
		panel_2.add(panel_9, BorderLayout.SOUTH);
		
		JLabel lblNewLabel = new JLabel("New label");
		panel_9.add(lblNewLabel);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panelGlobalChat = new JPanel();
		panel_3.add(panelGlobalChat, BorderLayout.SOUTH);
		panelGlobalChat.setLayout(new BorderLayout(0, 0));
		
		JTextArea affichageChat = new JTextArea();
		affichageChat.setText("Mario: Je t'aime Baptiste\r\nJoachim: Je t'aime Baptiste omg\r\nBaptiste: Les gars stop c'est embarrassant *blush*");
		panelGlobalChat.add(affichageChat, BorderLayout.CENTER);
		
		saisieChat = new JTextField();
		panelGlobalChat.add(saisieChat, BorderLayout.SOUTH);
		saisieChat.setColumns(10);
		
		JPanel panelCentreEcran = new JPanel();
		panel_3.add(panelCentreEcran, BorderLayout.CENTER);
		panelCentreEcran.setLayout(new BoxLayout(panelCentreEcran, BoxLayout.X_AXIS));
		
		JPanel panelGlobalListeSalle = new JPanel();
		panelCentreEcran.add(panelGlobalListeSalle);
		panelGlobalListeSalle.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBoutonsPourListeSalles = new JPanel();
		panelGlobalListeSalle.add(panelBoutonsPourListeSalles, BorderLayout.EAST);
		GridBagLayout gbl_panelBoutonsPourListeSalles = new GridBagLayout();
		gbl_panelBoutonsPourListeSalles.columnWidths = new int[]{115, 115, 0};
		gbl_panelBoutonsPourListeSalles.rowHeights = new int[]{29, 0, 0};
		gbl_panelBoutonsPourListeSalles.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelBoutonsPourListeSalles.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelBoutonsPourListeSalles.setLayout(gbl_panelBoutonsPourListeSalles);
		
		JButton btnNewButton = new JButton("New button");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		panelBoutonsPourListeSalles.add(btnNewButton, gbc_btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 1;
		panelBoutonsPourListeSalles.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JPanel panelListeSalle = new JPanel();
		panelGlobalListeSalle.add(panelListeSalle, BorderLayout.CENTER);
		panelListeSalle.setLayout(new BorderLayout(0, 0));
		
		JList listeDesSalles = new JList();
		panelListeSalle.add(listeDesSalles);
		
		JPanel panelGlobalObjet = new JPanel();
		panelCentreEcran.add(panelGlobalObjet);
		panelGlobalObjet.setLayout(new BorderLayout(0, 0));
		
		JPanel panelDescriptionObjet = new JPanel();
		panelGlobalObjet.add(panelDescriptionObjet, BorderLayout.CENTER);
		panelDescriptionObjet.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNomDeLobjet = new JLabel("Nom de l'objet");
		lblNomDeLobjet.setHorizontalAlignment(SwingConstants.CENTER);
		panelDescriptionObjet.add(lblNomDeLobjet, BorderLayout.NORTH);
		
		JLabel lblImagedelobjet = new JLabel("imageDeLobjet");
		lblImagedelobjet.setHorizontalAlignment(SwingConstants.CENTER);
		panelDescriptionObjet.add(lblImagedelobjet, BorderLayout.CENTER);
		
		JPanel panelBoutonsObjet = new JPanel();
		panelGlobalObjet.add(panelBoutonsObjet, BorderLayout.SOUTH);
	}

}
