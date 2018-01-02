package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
//import net.miginfocom.swing.MigLayout;
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
import java.awt.Font;
import javax.swing.AbstractListModel;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import commun.Message;
import commun.Objet;
import commun.SalleDeVente;
import commun.SalleDeVenteInfo;
import commun.DebugTools;
import commun.IHotelDesVentes;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import java.awt.Window.Type;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.UUID;


public class ECoastSimulatorGUI {

	private JFrame frmEcoastsimulatorpng;
	private JTextField saisieChat;
	private JTextField saisieIpServeur;
	
	private JTextField pseudonymeConnexion;
	private JTextField saisieEnchere;
	private JTextField txtNomDeLobjet;
	private JTextField txtDescriptionDeLobjet;
	private JTextField txtPrixDeBase;
	public static Client client;
	private UUID idSalleCourante=null;
	private JList<SalleDeVenteInfo> listeDesSalles = new JList<SalleDeVenteInfo>();
	private JList<SalleDeVenteInfo> listeDesSallesSuivies = new JList<SalleDeVenteInfo>();
	private JTextField saisiePortServeur;
	JTextArea affichageChat = new JTextArea();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		DebugTools.d("Lancement du client.");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DebugTools.d("Run");
					ECoastSimulatorGUI window = new ECoastSimulatorGUI();
					DebugTools.d("GUI initialized");
					window.frmEcoastsimulatorpng.setVisible(true);
					DebugTools.d("GUI visible");
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

	public void actualiserInterface() {
		updateListeDesSallesServeur();
		updateListeDesSallesSuivies();
		updateObjetSalleCourante();
		updateChat();
		DebugTools.d("Actualisation de toute l'interface");
	}

	private void updateChat() {
		//listesMessages.get(idSalle)
		if (affichageChat!= null) affichageChat.setText(client.getListesMessages().get(idSalleCourante).toString());
		affichageChat.repaint();
		// TODO Auto-generated method stub
		
	}

	private void updateListeDesSallesSuivies() {
		DebugTools.d("Actualisation des salles suivies");
		listeDesSallesSuivies.setListData(client.getTabVentesSuivies());
		listeDesSallesSuivies.repaint();


	}
	private void updateObjetSalleCourante() {
		DebugTools.d("Actualisation de l'objet courant");
		
		if (this.idSalleCourante!=null) {
			Objet objCourant=client.getVentesSuivies().get(this.idSalleCourante);
			txtNomDeLobjet.setText(objCourant.getNom());
			txtDescriptionDeLobjet.setText(objCourant.getDescription());
			String prixCourant=Float.toString(objCourant.getPrixCourant());
			txtPrixDeBase.setText(prixCourant);	
			
			txtPrixDeBase.repaint();
			txtDescriptionDeLobjet.repaint();
			txtNomDeLobjet.repaint();
		}
	}

	private void updateListeDesSallesServeur() {
		DebugTools.d("Actualisation des salles du serveur");
		DebugTools.d(Integer.toString(client.getTabInfosSalles().length));
		listeDesSalles.setListData(client.getTabInfosSalles());
		listeDesSalles.repaint();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		affichageChat.setLineWrap(true);
		affichageChat.setText("");
		saisieIpServeur = new JTextField();
		saisieIpServeur.setText("127.0.0.1");
		saisiePortServeur = new JTextField();
		saisiePortServeur.setText("1099");
		frmEcoastsimulatorpng = new JFrame();
		frmEcoastsimulatorpng.setIconImage(Toolkit.getDefaultToolkit().getImage(ECoastSimulatorGUI.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/vk-light.png")));
		frmEcoastsimulatorpng.setTitle("eCoastSimulator.png");
		frmEcoastsimulatorpng.getContentPane().setBackground(SystemColor.menu);
		frmEcoastsimulatorpng.setBounds(100, 100, 924, 700);
		frmEcoastsimulatorpng.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEcoastsimulatorpng.getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel panelConnexion = new JPanel();

		JPanel topBar = new JPanel();
		topBar.setBorder(null);
		frmEcoastsimulatorpng.getContentPane().add(topBar, BorderLayout.NORTH);

		JLabel lblAdresse = new JLabel("Adresse du serveur : ");
		topBar.add(lblAdresse);

		
		topBar.add(saisieIpServeur);
		saisieIpServeur.setColumns(10);
		
		
		topBar.add(saisiePortServeur);
		saisiePortServeur.setColumns(10);

		JButton btnSeConnecter = new JButton("se connecter");
		topBar.add(btnSeConnecter);
		
		JButton btnPing = new JButton("ping");
		btnPing.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					DebugTools.d("Envoi d'un ping au serveur");
					client.pingServeur();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		topBar.add(btnPing);

		JPanel globalLowPanel = new JPanel();
		frmEcoastsimulatorpng.getContentPane().add(globalLowPanel);
		globalLowPanel.setLayout(new BorderLayout(0, 0));

		JPanel sideBar = new JPanel();
		sideBar.setBorder(null);
		globalLowPanel.add(sideBar, BorderLayout.WEST);
		sideBar.setLayout(new BorderLayout(0, 0));

		JPanel commandesProfil = new JPanel();
		commandesProfil.setBorder(new LineBorder(new Color(0, 0, 0)));
		sideBar.add(commandesProfil, BorderLayout.NORTH);
		GridBagLayout gbl_commandesProfil = new GridBagLayout();
		gbl_commandesProfil.rowHeights = new int[] {0};
		gbl_commandesProfil.columnWidths = new int[] {150};
		gbl_commandesProfil.columnWeights = new double[]{1.0};
		gbl_commandesProfil.rowWeights = new double[]{1.0, 0.0};
		commandesProfil.setLayout(gbl_commandesProfil);

		JPanel panelDeconnexion = new JPanel();
		GridBagConstraints gbc_panelDeconnexion = new GridBagConstraints();
		gbc_panelDeconnexion.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelDeconnexion.anchor = GridBagConstraints.NORTH;
		gbc_panelDeconnexion.insets = new Insets(0, 0, 5, 0);
		gbc_panelDeconnexion.gridx = 0;
		gbc_panelDeconnexion.gridy = 0;
		commandesProfil.add(panelDeconnexion, gbc_panelDeconnexion);
		GridBagLayout gbl_panelDeconnexion = new GridBagLayout();
		gbl_panelDeconnexion.columnWidths = new int[] {174};
		gbl_panelDeconnexion.rowHeights = new int[]{29, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelDeconnexion.columnWeights = new double[]{1.0};
		gbl_panelDeconnexion.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelDeconnexion.setLayout(gbl_panelDeconnexion);

		JLabel lblConnectEnTemps = new JLabel("Connecté en tant que :");
		GridBagConstraints gbc_lblConnectEnTemps = new GridBagConstraints();
		gbc_lblConnectEnTemps.insets = new Insets(0, 0, 5, 0);
		gbc_lblConnectEnTemps.anchor = GridBagConstraints.WEST;
		gbc_lblConnectEnTemps.gridx = 0;
		gbc_lblConnectEnTemps.gridy = 0;
		panelDeconnexion.add(lblConnectEnTemps, gbc_lblConnectEnTemps);

		JLabel lblPseudoDeConnexion = new JLabel("Baptiste");
		lblPseudoDeConnexion.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_lblPseudoDeConnexion = new GridBagConstraints();
		gbc_lblPseudoDeConnexion.insets = new Insets(0, 0, 5, 0);
		gbc_lblPseudoDeConnexion.gridx = 0;
		gbc_lblPseudoDeConnexion.gridy = 1;
		panelDeconnexion.add(lblPseudoDeConnexion, gbc_lblPseudoDeConnexion);

		JButton btnSeDconnecter = new JButton("Se déconnecter");
		btnSeDconnecter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DebugTools.d("TODO:Click se déconnecter");
				client.deconnexion();
				
				//TODO: si ok rendre panelDeconnexion invisible et panelConnexion visible
				panelDeconnexion.setVisible(false);
				panelConnexion.setVisible(false);
			}
		});
		GridBagConstraints gbc_btnSeDconnecter = new GridBagConstraints();
		gbc_btnSeDconnecter.insets = new Insets(0, 0, 5, 0);
		gbc_btnSeDconnecter.gridx = 0;
		gbc_btnSeDconnecter.gridy = 2;
		panelDeconnexion.add(btnSeDconnecter, gbc_btnSeDconnecter);

		JButton btnNouvelleEnchre = new JButton("Nouvelle vente");
		btnNouvelleEnchre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					client.nouvelleSalle(txtNomDeLobjet.getText(), txtDescriptionDeLobjet.getText(), Float.parseFloat(txtPrixDeBase.getText()));
				} catch (NumberFormatException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		});
		GridBagConstraints gbc_btnNouvelleEnchre = new GridBagConstraints();
		gbc_btnNouvelleEnchre.insets = new Insets(0, 0, 5, 0);
		gbc_btnNouvelleEnchre.gridx = 0;
		gbc_btnNouvelleEnchre.gridy = 3;
		panelDeconnexion.add(btnNouvelleEnchre, gbc_btnNouvelleEnchre);

		txtNomDeLobjet = new JTextField();
		txtNomDeLobjet.setText("Nom de l'objet");
		GridBagConstraints gbc_txtNomDeLobjet = new GridBagConstraints();
		gbc_txtNomDeLobjet.insets = new Insets(0, 0, 5, 0);
		gbc_txtNomDeLobjet.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNomDeLobjet.gridx = 0;
		gbc_txtNomDeLobjet.gridy = 4;
		panelDeconnexion.add(txtNomDeLobjet, gbc_txtNomDeLobjet);
		txtNomDeLobjet.setColumns(10);

		txtDescriptionDeLobjet = new JTextField();
		txtDescriptionDeLobjet.setText("Description de l'objet");
		GridBagConstraints gbc_txtDescriptionDeLobjet = new GridBagConstraints();
		gbc_txtDescriptionDeLobjet.insets = new Insets(0, 0, 5, 0);
		gbc_txtDescriptionDeLobjet.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescriptionDeLobjet.gridx = 0;
		gbc_txtDescriptionDeLobjet.gridy = 5;
		panelDeconnexion.add(txtDescriptionDeLobjet, gbc_txtDescriptionDeLobjet);
		txtDescriptionDeLobjet.setColumns(10);

		txtPrixDeBase = new JTextField();
		txtPrixDeBase.setText("Prix de base de l'objet");
		GridBagConstraints gbc_txtPrixDeBase = new GridBagConstraints();
		gbc_txtPrixDeBase.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrixDeBase.gridx = 0;
		gbc_txtPrixDeBase.gridy = 6;
		panelDeconnexion.add(txtPrixDeBase, gbc_txtPrixDeBase);
		txtPrixDeBase.setColumns(10);

		
		GridBagConstraints gbc_panelConnexion = new GridBagConstraints();
		gbc_panelConnexion.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelConnexion.gridx = 0;
		gbc_panelConnexion.gridy = 1;
		commandesProfil.add(panelConnexion, gbc_panelConnexion);
		GridBagLayout gbl_panelConnexion = new GridBagLayout();
		gbl_panelConnexion.columnWidths = new int[] {106};
		gbl_panelConnexion.rowHeights = new int[]{29, 0, 0, 0};
		gbl_panelConnexion.columnWeights = new double[]{0.0};
		gbl_panelConnexion.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelConnexion.setLayout(gbl_panelConnexion);

		JLabel lblPseudonyme = new JLabel("Pseudonyme : ");
		GridBagConstraints gbc_lblPseudonyme = new GridBagConstraints();
		gbc_lblPseudonyme.insets = new Insets(0, 0, 0, 5);
		gbc_lblPseudonyme.gridx = 0;
		gbc_lblPseudonyme.gridy = 0;
		panelConnexion.add(lblPseudonyme, gbc_lblPseudonyme);

		pseudonymeConnexion = new JTextField();
		pseudonymeConnexion.setText("Entrez votre nom");
		GridBagConstraints gbc_pseudonymeConnexion = new GridBagConstraints();
		gbc_pseudonymeConnexion.fill = GridBagConstraints.HORIZONTAL;
		gbc_pseudonymeConnexion.insets = new Insets(0, 0, 0, 5);
		gbc_pseudonymeConnexion.gridx = 0;
		gbc_pseudonymeConnexion.gridy = 1;
		panelConnexion.add(pseudonymeConnexion, gbc_pseudonymeConnexion);
		pseudonymeConnexion.setColumns(10);

		JButton btnSeConnecter_1 = new JButton("Se connecter");
		btnSeConnecter_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DebugTools.d("Click se connecter");
				client = creerClient(
						pseudonymeConnexion.getText(),
						saisieIpServeur.getText(),
						saisiePortServeur.getText());
				DebugTools.d("Client créé");
				
				client.connexionServeur();
				client.bindClient();
				panelConnexion.setVisible(false);
				DebugTools.d("Panel de connexion caché");
				panelDeconnexion.setVisible(true);
				DebugTools.d("Panel de déconnexion rendu visible");
				client.myClientInfos.setNom(pseudonymeConnexion.getText());
				DebugTools.d("Pseudonyme client actualisé selon saisie");
				lblPseudoDeConnexion.setText(client.myClientInfos.getNom());
				DebugTools.d("Pseudonyme du client affiché en haut à gauche");

				updateListeDesSallesServeur();
				updateListeDesSallesSuivies();

				DebugTools.d("Actualisation de l'interface");
			}
		});
		GridBagConstraints gbc_btnSeConnecter_1 = new GridBagConstraints();
		gbc_btnSeConnecter_1.gridx = 0;
		gbc_btnSeConnecter_1.gridy = 2;
		panelConnexion.add(btnSeConnecter_1, gbc_btnSeConnecter_1);
		commandesProfil.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panelDeconnexion, lblConnectEnTemps, lblPseudoDeConnexion, btnSeDconnecter, panelConnexion, lblPseudonyme, pseudonymeConnexion, btnSeConnecter_1}));

		JPanel panelListeDesSallesSuivies = new JPanel();
		panelListeDesSallesSuivies.setBorder(new LineBorder(new Color(0, 0, 0)));
		sideBar.add(panelListeDesSallesSuivies, BorderLayout.SOUTH);
		panelListeDesSallesSuivies.setLayout(new BorderLayout(0, 0));

		listeDesSallesSuivies.setModel(new AbstractListModel<SalleDeVenteInfo>() {

			private static final long serialVersionUID = 6110811681481178698L;
			SalleDeVenteInfo[] values = {null};
			public int getSize() {
				return values.length;
			}
			public SalleDeVenteInfo getElementAt(int index) {
				return values[index];
			}
		});
		listeDesSallesSuivies.setSelectedIndex(0);
		panelListeDesSallesSuivies.add(listeDesSallesSuivies);

		JLabel lblSallesSuivies = new JLabel("Salles suivies");
		panelListeDesSallesSuivies.add(lblSallesSuivies, BorderLayout.NORTH);

		JPanel panelBoutonsObjetsSuivis = new JPanel();
		panelListeDesSallesSuivies.add(panelBoutonsObjetsSuivis, BorderLayout.SOUTH);
		GridBagLayout gbl_panelBoutonsObjetsSuivis = new GridBagLayout();
		gbl_panelBoutonsObjetsSuivis.columnWidths = new int[]{207, 0};
		gbl_panelBoutonsObjetsSuivis.rowHeights = new int[]{29, 0, 0};
		gbl_panelBoutonsObjetsSuivis.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelBoutonsObjetsSuivis.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelBoutonsObjetsSuivis.setLayout(gbl_panelBoutonsObjetsSuivis);

		JButton btnRejoindreSalleDeLObjet = new JButton("Rejoindre salle de l'objet");
		btnRejoindreSalleDeLObjet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				client.setIdSalleObservee(listeDesSalles.getSelectedValue().getId());
				updateObjetSalleCourante();
				updateListeDesSallesSuivies();
			}
		});
		GridBagConstraints gbc_btnRejoindreSalleDeLObjet = new GridBagConstraints();
		gbc_btnRejoindreSalleDeLObjet.insets = new Insets(0, 0, 5, 0);
		gbc_btnRejoindreSalleDeLObjet.gridx = 0;
		gbc_btnRejoindreSalleDeLObjet.gridy = 0;
		panelBoutonsObjetsSuivis.add(btnRejoindreSalleDeLObjet, gbc_btnRejoindreSalleDeLObjet);

		JButton btnQuitterSalleDe = new JButton("Quitter salle de l'objet");
		btnQuitterSalleDe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UUID idSalleAQuitter = listeDesSallesSuivies.getSelectedValue().getId();
				client.getVentesSuivies().remove(idSalleAQuitter);
				client.quitterSalle(idSalleAQuitter);
			}
		});
		GridBagConstraints gbc_btnQuitterSalleDe = new GridBagConstraints();
		gbc_btnQuitterSalleDe.gridx = 0;
		gbc_btnQuitterSalleDe.gridy = 1;
		panelBoutonsObjetsSuivis.add(btnQuitterSalleDe, gbc_btnQuitterSalleDe);

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		globalLowPanel.add(mainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{549, 0};
		gbl_mainPanel.rowHeights = new int[]{380, 100, 0};
		gbl_mainPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		mainPanel.setLayout(gbl_mainPanel);

		JPanel panelCentreEcran = new JPanel();
		GridBagConstraints gbc_panelCentreEcran = new GridBagConstraints();
		gbc_panelCentreEcran.anchor = GridBagConstraints.NORTH;
		gbc_panelCentreEcran.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelCentreEcran.insets = new Insets(0, 0, 5, 0);
		gbc_panelCentreEcran.gridx = 0;
		gbc_panelCentreEcran.gridy = 0;
		mainPanel.add(panelCentreEcran, gbc_panelCentreEcran);
		GridBagLayout gbl_panelCentreEcran = new GridBagLayout();
		gbl_panelCentreEcran.columnWidths = new int[]{283, 266, 0};
		gbl_panelCentreEcran.rowHeights = new int[]{390, 0};
		gbl_panelCentreEcran.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panelCentreEcran.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelCentreEcran.setLayout(gbl_panelCentreEcran);

		JPanel panelGlobalListeSalle = new JPanel();
		GridBagConstraints gbc_panelGlobalListeSalle = new GridBagConstraints();
		gbc_panelGlobalListeSalle.anchor = GridBagConstraints.WEST;
		gbc_panelGlobalListeSalle.fill = GridBagConstraints.VERTICAL;
		gbc_panelGlobalListeSalle.insets = new Insets(0, 0, 0, 5);
		gbc_panelGlobalListeSalle.gridx = 0;
		gbc_panelGlobalListeSalle.gridy = 0;
		panelCentreEcran.add(panelGlobalListeSalle, gbc_panelGlobalListeSalle);
		panelGlobalListeSalle.setLayout(new BorderLayout(0, 0));

		JPanel panelListeSalle = new JPanel();
		panelGlobalListeSalle.add(panelListeSalle, BorderLayout.CENTER);
		panelListeSalle.setLayout(new BorderLayout(0, 0));


		listeDesSalles.setModel(new AbstractListModel<SalleDeVenteInfo>() {
			private static final long serialVersionUID = -6762872273592088709L;


			SalleDeVenteInfo[] values={};
			public int getSize() {
				return values.length;
			}
			public SalleDeVenteInfo getElementAt(int index) {
				return values[index];
			}
		});
		panelListeSalle.add(listeDesSalles);

		JLabel lblSallesDenchres = new JLabel("Salles d'enchères :");
		lblSallesDenchres.setHorizontalAlignment(SwingConstants.CENTER);
		panelListeSalle.add(lblSallesDenchres, BorderLayout.NORTH);

		JPanel panelBoutonsPourListeSalles = new JPanel();
		panelGlobalListeSalle.add(panelBoutonsPourListeSalles, BorderLayout.EAST);
		GridBagLayout gbl_panelBoutonsPourListeSalles = new GridBagLayout();
		gbl_panelBoutonsPourListeSalles.columnWidths = new int[]{0, 0};
		gbl_panelBoutonsPourListeSalles.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelBoutonsPourListeSalles.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelBoutonsPourListeSalles.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		panelBoutonsPourListeSalles.setLayout(gbl_panelBoutonsPourListeSalles);

		JPanel panelNombrePersonnesDansSalle = new JPanel();
		@SuppressWarnings("unused")
		FlowLayout flowLayout = (FlowLayout) panelNombrePersonnesDansSalle.getLayout();
		GridBagConstraints gbc_panelNombrePersonnesDansSalle = new GridBagConstraints();
		gbc_panelNombrePersonnesDansSalle.anchor = GridBagConstraints.NORTH;
		gbc_panelNombrePersonnesDansSalle.insets = new Insets(0, 0, 5, 0);
		gbc_panelNombrePersonnesDansSalle.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelNombrePersonnesDansSalle.gridx = 0;
		gbc_panelNombrePersonnesDansSalle.gridy = 0;
		panelBoutonsPourListeSalles.add(panelNombrePersonnesDansSalle, gbc_panelNombrePersonnesDansSalle);

		JLabel lblPersonnesDansLa = new JLabel("Connectés : ");
		panelNombrePersonnesDansSalle.add(lblPersonnesDansLa);

		JLabel labelNombreConnectesParSalle = new JLabel("3");
		panelNombrePersonnesDansSalle.add(labelNombreConnectesParSalle);

		JLabel lblPersonnes = new JLabel("personnes");
		panelNombrePersonnesDansSalle.add(lblPersonnes);

		JPanel panelPlusHauteEnchere = new JPanel();
		GridBagConstraints gbc_panelPlusHauteEnchere = new GridBagConstraints();
		gbc_panelPlusHauteEnchere.anchor = GridBagConstraints.NORTH;
		gbc_panelPlusHauteEnchere.insets = new Insets(0, 0, 5, 0);
		gbc_panelPlusHauteEnchere.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelPlusHauteEnchere.gridx = 0;
		gbc_panelPlusHauteEnchere.gridy = 1;
		panelBoutonsPourListeSalles.add(panelPlusHauteEnchere, gbc_panelPlusHauteEnchere);

		JLabel lblEnchre = new JLabel("Enchère : ");
		panelPlusHauteEnchere.add(lblEnchre);

		JLabel labelPrixObjetSalle = new JLabel("500");
		panelPlusHauteEnchere.add(labelPrixObjetSalle);

		JLabel label_euro = new JLabel("€");
		panelPlusHauteEnchere.add(label_euro);

		JPanel panelRejoidreOuQuitterSalle = new JPanel();
		GridBagConstraints gbc_panelRejoidreOuQuitterSalle = new GridBagConstraints();
		gbc_panelRejoidreOuQuitterSalle.anchor = GridBagConstraints.SOUTH;
		gbc_panelRejoidreOuQuitterSalle.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelRejoidreOuQuitterSalle.gridx = 0;
		gbc_panelRejoidreOuQuitterSalle.gridy = 2;
		panelBoutonsPourListeSalles.add(panelRejoidreOuQuitterSalle, gbc_panelRejoidreOuQuitterSalle);
		GridBagLayout gbl_panelRejoidreOuQuitterSalle = new GridBagLayout();
		gbl_panelRejoidreOuQuitterSalle.columnWidths = new int[]{137, 0};
		gbl_panelRejoidreOuQuitterSalle.rowHeights = new int[] {29};
		gbl_panelRejoidreOuQuitterSalle.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelRejoidreOuQuitterSalle.rowWeights = new double[]{0.0};
		panelRejoidreOuQuitterSalle.setLayout(gbl_panelRejoidreOuQuitterSalle);

		JButton btnRejoindreSalleListe = new JButton("Rejoindre salle");
		btnRejoindreSalleListe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				idSalleCourante=listeDesSalles.getSelectedValue().getId();
				client.rejoindreSalle(idSalleCourante);
				updateObjetSalleCourante();


			}
		});
		GridBagConstraints gbc_btnRejoindreSalleListe = new GridBagConstraints();
		gbc_btnRejoindreSalleListe.insets = new Insets(0, 0, 5, 0);
		gbc_btnRejoindreSalleListe.gridx = 0;
		gbc_btnRejoindreSalleListe.gridy = 0;
		panelRejoidreOuQuitterSalle.add(btnRejoindreSalleListe, gbc_btnRejoindreSalleListe);

		JPanel panelGlobalObjet = new JPanel();
		panelGlobalObjet.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panelGlobalObjet = new GridBagConstraints();
		gbc_panelGlobalObjet.anchor = GridBagConstraints.WEST;
		gbc_panelGlobalObjet.gridx = 1;
		gbc_panelGlobalObjet.gridy = 0;
		panelCentreEcran.add(panelGlobalObjet, gbc_panelGlobalObjet);
		panelGlobalObjet.setLayout(new BorderLayout(0, 0));

		JPanel panelBoutonsObjet = new JPanel();
		panelGlobalObjet.add(panelBoutonsObjet, BorderLayout.SOUTH);
		GridBagLayout gbl_panelBoutonsObjet = new GridBagLayout();
		gbl_panelBoutonsObjet.columnWidths = new int[]{155, 91, 0};
		gbl_panelBoutonsObjet.rowHeights = new int[]{31, 0};
		gbl_panelBoutonsObjet.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelBoutonsObjet.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelBoutonsObjet.setLayout(gbl_panelBoutonsObjet);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_2.insets = new Insets(0, 0, 0, 5);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		panelBoutonsObjet.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{146, 0, 0};
		gbl_panel_2.rowHeights = new int[] {26};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0};
		panel_2.setLayout(gbl_panel_2);

		saisieEnchere = new JTextField();
		saisieEnchere.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(client.getIdSalleObservee()!=null) {
					
					if (e.getKeyChar()=='\n') {
	
						try {
							DebugTools.d("Touche entrée saisie, envoi de l'enchère de : "+saisieEnchere.getText());
							IHotelDesVentes serveur=client.getServeur();
							DebugTools.d("Serveur récupéré du client.");
							if( saisieEnchere.getText() != "") {
								float enchere=Float.parseFloat(saisieEnchere.getText());
								serveur.encherir(enchere, client.getId(), client.getIdSalleObservee());
		
								DebugTools.d("Réussi.");
								saisieEnchere.setText("");
							}
						} catch (RemoteException re) {
							re.printStackTrace();
						}
					}
				}
			}
		});
		saisieEnchere.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_saisieEnchere = new GridBagConstraints();
		gbc_saisieEnchere.fill = GridBagConstraints.HORIZONTAL;
		gbc_saisieEnchere.anchor = GridBagConstraints.EAST;
		gbc_saisieEnchere.insets = new Insets(0, 0, 5, 0);
		gbc_saisieEnchere.gridx = 0;
		gbc_saisieEnchere.gridy = 0;
		panel_2.add(saisieEnchere, gbc_saisieEnchere);
		saisieEnchere.setColumns(10);


		JLabel label = new JLabel("€");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		panel_2.add(label, gbc_label);

		JButton btnEnchrir = new JButton("Enchérir");
		btnEnchrir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(client.getIdSalleObservee()!=null) {
					IHotelDesVentes serveur=client.getServeur();
					DebugTools.d("Serveur récupéré du client.");
					if( saisieEnchere.getText() != "") {
						float enchere=Float.parseFloat(saisieEnchere.getText());
						try {
							serveur.encherir(enchere, client.getId(), client.getIdSalleObservee());
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						DebugTools.d("Réussi.");
						saisieEnchere.setText("");
					}else {
						DebugTools.d("Saisie vide");
					}
				}else {
					DebugTools.d("Aucune salle n'est sélectionnée comme salle courante");
				}
			}
		});
		GridBagConstraints gbc_btnEnchrir = new GridBagConstraints();
		gbc_btnEnchrir.anchor = GridBagConstraints.WEST;
		gbc_btnEnchrir.fill = GridBagConstraints.VERTICAL;
		gbc_btnEnchrir.gridx = 1;
		gbc_btnEnchrir.gridy = 0;
		panelBoutonsObjet.add(btnEnchrir, gbc_btnEnchrir);

		JPanel panel_4 = new JPanel();
		panelGlobalObjet.add(panel_4, BorderLayout.CENTER);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{0, 0};
		gbl_panel_4.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_4.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);

		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 5, 0);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 0;
		panel_4.add(panel_5, gbc_panel_5);

		JLabel lblNomDeLobjet = new JLabel("Nom de l'objet : ");
		panel_5.add(lblNomDeLobjet);

		JLabel lblPatate = new JLabel("patate");
		panel_5.add(lblPatate);

		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(0, 0, 5, 0);
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 1;
		panel_4.add(panel_6, gbc_panel_6);

		JLabel lblEchreCourante = new JLabel("Echère courante : ");
		panel_6.add(lblEchreCourante);

		JLabel label_1 = new JLabel("240");
		panel_6.add(label_1);

		JLabel label_2 = new JLabel("€");
		panel_6.add(label_2);

		JPanel panel_7 = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 2;
		panel_4.add(panel_7, gbc_panel_7);

		JLabel lblTempsRestantAvant = new JLabel("Fin dans : ");
		panel_7.add(lblTempsRestantAvant);

		JLabel lblSecondes = new JLabel("7 secondes");
		panel_7.add(lblSecondes);

		JPanel panelGlobalChat = new JPanel();
		GridBagConstraints gbc_panelGlobalChat = new GridBagConstraints();
		gbc_panelGlobalChat.fill = GridBagConstraints.BOTH;
		gbc_panelGlobalChat.anchor = GridBagConstraints.NORTHWEST;
		gbc_panelGlobalChat.gridx = 0;
		gbc_panelGlobalChat.gridy = 1;
		mainPanel.add(panelGlobalChat, gbc_panelGlobalChat);
		GridBagLayout gbl_panelGlobalChat = new GridBagLayout();
		gbl_panelGlobalChat.columnWidths = new int[]{549, 0};
		gbl_panelGlobalChat.rowHeights = new int[]{16, 48, 26, 0};
		gbl_panelGlobalChat.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelGlobalChat.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panelGlobalChat.setLayout(gbl_panelGlobalChat);

		JLabel lblDiscussionsDansLa = new JLabel("Discussions dans la salle : ");
		lblDiscussionsDansLa.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblDiscussionsDansLa = new GridBagConstraints();
		gbc_lblDiscussionsDansLa.anchor = GridBagConstraints.NORTH;
		gbc_lblDiscussionsDansLa.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDiscussionsDansLa.insets = new Insets(0, 0, 5, 0);
		gbc_lblDiscussionsDansLa.gridx = 0;
		gbc_lblDiscussionsDansLa.gridy = 0;
		panelGlobalChat.add(lblDiscussionsDansLa, gbc_lblDiscussionsDansLa);

		
		affichageChat.setLineWrap(true);
		affichageChat.setText("Mario: Je t'aime Baptiste\r\nJoachim: Je t'aime Baptiste omg\r\nBaptiste: Les gars stop c'est embarrassant *blush*");
		GridBagConstraints gbc_affichageChat = new GridBagConstraints();
		gbc_affichageChat.fill = GridBagConstraints.BOTH;
		gbc_affichageChat.insets = new Insets(0, 0, 5, 0);
		gbc_affichageChat.gridx = 0;
		gbc_affichageChat.gridy = 1;
		panelGlobalChat.add(affichageChat, gbc_affichageChat);

		JPanel panelSaisieChat = new JPanel();
		panelSaisieChat.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panelSaisieChat = new GridBagConstraints();
		gbc_panelSaisieChat.anchor = GridBagConstraints.SOUTH;
		gbc_panelSaisieChat.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelSaisieChat.gridx = 0;
		gbc_panelSaisieChat.gridy = 2;
		panelGlobalChat.add(panelSaisieChat, gbc_panelSaisieChat);
		panelSaisieChat.setLayout(new BorderLayout(0, 0));

		saisieChat = new JTextField();
		saisieChat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent key) {
				//TODO: Chat : Si on appuie sur entrée, envoyer saisie et vider le champ
				if(idSalleCourante!=null && saisieChat.getText()!="") {
					if (key.getKeyChar()=='\n') {
						Message messageAEnvoyer=new Message(client.myClientInfos.getNom(),saisieChat.getText());
						DebugTools.d("saisieChat déclenchée");
						DebugTools.d("message formulé par "+client.myClientInfos.getNom()+" : "+saisieChat.getText());
						try {
							client.getServeur().posterMessage(client.getIdSalleObservee(),messageAEnvoyer);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						saisieChat.setText("");
					}
				}else {
					DebugTools.d("Envoi de message non déclenché car aucune salle n'a été choisie ou le message est vide");
				}
				
			}
		});
		panelSaisieChat.add(saisieChat);
		saisieChat.setColumns(10);

		JButton btnEnvoyer = new JButton("Envoyer");
		btnEnvoyer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(idSalleCourante!=null) {
					if (saisieChat.getText()!="") {
						Message messageAEnvoyer=new Message(client.myClientInfos.getNom(),saisieChat.getText());
						DebugTools.d("saisieChat déclenchée");
						DebugTools.d("message formulé par "+client.myClientInfos.getNom()+" : "+saisieChat.getText());
						try {
							client.getServeur().posterMessage(client.getIdSalleObservee(),messageAEnvoyer);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						saisieChat.setText("");
					}
				}else {
					DebugTools.d("Envoi de message non déclenché car aucune salle n'a été choisie ou le message est vide");
				}
			}
		});
		panelSaisieChat.add(btnEnvoyer, BorderLayout.EAST);
	}

	protected Client creerClient(String nom, String ipServeur, String portServeur) {
		
		try {
			return new Client(this,nom,ipServeur,portServeur);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

}
