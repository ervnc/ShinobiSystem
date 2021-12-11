package principal;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.MaskFormatter;
import java.awt.event.FocusEvent;

import com.k33ptoo.components.KButton;
import com.k33ptoo.components.KGradientPanel;

import bean.Missao;
import bean.Ninja;
import bean.NinjaParticipaTime;
import bean.Personagem;
import bean.Time;
import dao.ConsultasSQL;
import dao.MissaoDAO;
import dao.NinjaDAO;
import dao.NinjaParticipaTimeDAO;
import dao.PersonagemDAO;
import dao.TimeDAO;

import org.apache.commons.text.*;
import org.apache.commons.lang3.*;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;

@SuppressWarnings("serial")
public class Interface extends JFrame {

	private JPanel contentPane;

	// ==================== //
	// VARIÁVEIS PERSONAGEM //
	// ==================== //
	private JTextField tfNome, tfOcupacao, tfPeso, tfAltura;
	private KButton btnAdicionarPersonagem;
	private JTextField tfNome_1, tfOcupacao_1, tfPeso_1, tfAltura_1;

	
	// =============== //
	// VARIÁVEIS NINJA //
	// =============== //
	private JTextField tfTitulo;
	private JTextField tfTitulo_1;

	
	// ================ //
	// VARIÁVEIS MISSÃO //
	// ================ //
	private JTextField tfObjetivo, tfEpInicio, tfEpFim;
	private JTextField tfObjetivoEditar, tfEpInicioEditar, tfEpFimEditar;
	private JTextField tfObjetivo_1, tfEpInicio_1, tfEpFim_1;
	private JTextField tfObjetivoExcluir, tfEpInicioExcluir, tfEpFimExcluir;

	
	// ============== //
	// VARIÁVEIS TIME //
	// ============== //
	private JTextField tfNumero, tfQuantMembros, tfNomeTime, tfEpCriacao;
	private JTextField tfQuantMembros_1, tfNomeTime_1, tfEpCriacao_1;

	private JTable tablePersonagem, tableNinja, tableMissao, tableTime;
	private JTextField tfTexto;
	private JTextField tfOcasiao;
	private JTable tableFrase;
	private JTextField tfTextoExcluir;
	private JTextField tfOcasiaoExcluir;
	private JTable tableTransformacaoNatureza;
	private JTextField tfObjetivoMissao;
	private JTextField tfEpInicioMissao;
	private JTextField tfEpFimMissao;
	private JTextField tfObstaculoMissao;
	private JTable tableObstaculoMissao;
	private JTextField tfObjetivoMissaoExcluir;
	private JTextField tfEpInicioMissaoExcluir;
	private JTextField tfEpFimMissaoExcluir;
	private JTextField tfObstaculoMissaoExcluir;
	private JTextField tfSelecionarEpInicio;
	private JTextField tfSelecionarEpFim;
	private JTextField tfEpIngressoParticipacao;
	private JTextField tfEpSaidaParticipacao;
	private JTable tableParticipacao;
	private JTextField tfEpIngressoEditarParticipacao_1;
	private JTextField tfEpSaidaEditarParticipacao_1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	// ==================== //
	// Validações numéricas //
	// ==================== //
	public static boolean eDoublePositivo(String str) {
		boolean pode = true;
		if (!str.equals("")) {
			String auxiliar = str;
			if (str.substring(0, 1).equals("-")) {
				pode = false;
			} else if (str.substring(0, 1).equals("+")) {
				auxiliar = str.substring(1, str.length());
			}
			if (auxiliar.length() - auxiliar.replace(".", "").length() > 1) {
				pode = false;
			}
			if (!StringUtils.isNumeric(auxiliar.replace(".", ""))) {
				pode = false;
			} else {
				if (Double.parseDouble(auxiliar) == 0) {
					pode = false;
				}
			}
		} else {
			pode = false;
		}
		return pode;
	}

	private static boolean eIntPositivo(String str) {
		boolean pode = true;
		if (!str.equals("")) {
			String auxiliar = str;
			if (str.substring(0, 1).equals("-")) {
				pode = false;
			} else if (str.substring(0, 1).equals("+")) {
				auxiliar = str.substring(1, str.length());
			}
			if (str.contains(".")) {
				pode = false;
			}
			if (!StringUtils.isNumeric(auxiliar)) {
				pode = false;
			} else {
				if (Integer.parseInt(auxiliar) == 0) {
					pode = false;
				}
			}
		} else {
			pode = false;
		}
		return pode;
	}

    private boolean checaData(String data, String formato) {
        if (data.trim().equals("")) {
            return true;
        } else {
            SimpleDateFormat dateformat = new SimpleDateFormat(formato);
            dateformat.setLenient(false);
            try {
                dateformat.parse(data);
            } catch (ParseException e) {
                return false;
            }
            return true;
        }
    }
	
	private void formatarCampo(JFormattedTextField textField) {
		try {
			MaskFormatter mask = new MaskFormatter("####-##-##");
			mask.install(textField);
		} catch (ParseException e) {
			Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public Interface() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Interface.class.getResource("/img/naruto_icon_top.png")));

		setResizable(false);
		setTitle("ShinobiSystem");
		setBackground(new Color(32, 33, 36));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 450);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(new Color(32, 33, 36));
		setJMenuBar(menuBar);
		
		
		// === DAOs === //
		PersonagemDAO personagemDAO = new PersonagemDAO();
		NinjaDAO ninjaDAO = new NinjaDAO();
		MissaoDAO missaoDAO = new MissaoDAO();
		TimeDAO timeDAO = new TimeDAO();
		ConsultasSQL consultasSQL = new ConsultasSQL();
		NinjaParticipaTimeDAO NPTDAO = new NinjaParticipaTimeDAO();

		
		// === MENU PERSONAGENS === //
		JMenu mnPersonagens = new JMenu("Personagens");
		mnPersonagens.setForeground(Color.WHITE);
		mnPersonagens.setBackground(new Color(32, 33, 36));
		mnPersonagens.setIcon(new ImageIcon(Interface.class.getResource("/img/character.png")));
		menuBar.add(mnPersonagens);

		JMenuItem mntmAdicionarPersonagem = new JMenuItem("Adicionar personagem");
		mntmAdicionarPersonagem.setBackground(Color.WHITE);
		mntmAdicionarPersonagem.setIcon(new ImageIcon(Interface.class.getResource("/img/add.png")));
		mnPersonagens.add(mntmAdicionarPersonagem);

		JMenuItem mntmVerPersonagens = new JMenuItem("Ver personagens");
		mntmVerPersonagens.setBackground(Color.WHITE);
		mntmVerPersonagens.setIcon(new ImageIcon(Interface.class.getResource("/img/search.png")));
		mnPersonagens.add(mntmVerPersonagens);
		
		JMenuItem mntmEditarPersonagem = new JMenuItem("Editar personagem");
		mntmEditarPersonagem.setBackground(Color.WHITE);
		mntmEditarPersonagem.setIcon(new ImageIcon(Interface.class.getResource("/img/edit.png")));
		mnPersonagens.add(mntmEditarPersonagem);

		JMenuItem mntmExcluirPersonagem = new JMenuItem("Excluir personagem");
		mntmExcluirPersonagem.setBackground(Color.WHITE);
		mntmExcluirPersonagem.setIcon(new ImageIcon(Interface.class.getResource("/img/delete.png")));
		mnPersonagens.add(mntmExcluirPersonagem);
		
		JSeparator separator = new JSeparator();
		separator.setForeground( new Color(32, 33 ,36));
		mnPersonagens.add(separator);
		
		JMenuItem mntmAdicionarFrase = new JMenuItem("Adicionar frase");
		mntmAdicionarFrase.setBackground(Color.WHITE);
		mntmAdicionarFrase.setIcon(new ImageIcon(Interface.class.getResource("/img/add.png")));
		mnPersonagens.add(mntmAdicionarFrase);	
		
		JMenuItem mntmVerFrases = new JMenuItem("Ver frases");
		mntmVerFrases.setBackground(Color.WHITE);
		mntmVerFrases.setIcon(new ImageIcon(Interface.class.getResource("/img/search.png")));
		mnPersonagens.add(mntmVerFrases);
		
		JMenuItem mntmExcluirFrase = new JMenuItem("Excluir frase");
		mntmExcluirFrase.setBackground(Color.WHITE);
		mntmExcluirFrase.setIcon(new ImageIcon(Interface.class.getResource("/img/delete.png")));
		mnPersonagens.add(mntmExcluirFrase);

		
		// === MENU NINJAS === //
		JMenu mnNinjas = new JMenu("Ninjas");
		mnNinjas.setBackground(new Color(32, 33, 36));
		mnNinjas.setForeground(Color.WHITE);
		mnNinjas.setIcon(new ImageIcon(Interface.class.getResource("/img/ninja-blade_1.png")));
		menuBar.add(mnNinjas);

		JMenuItem mntmAdicionarNinja = new JMenuItem("Adicionar ninja");
		mntmAdicionarNinja.setBackground(Color.WHITE);
		mntmAdicionarNinja.setIcon(new ImageIcon(Interface.class.getResource("/img/add.png")));
		mnNinjas.add(mntmAdicionarNinja);
		
		JMenuItem mntmVerNinjas = new JMenuItem("Ver ninjas");
		mntmVerNinjas.setBackground(Color.WHITE);
		mntmVerNinjas.setIcon(new ImageIcon(Interface.class.getResource("/img/search.png")));
		mnNinjas.add(mntmVerNinjas);
				
		JMenuItem mntmEditarNinja = new JMenuItem("Editar ninja");
		mntmEditarNinja.setBackground(Color.WHITE);
		mntmEditarNinja.setIcon(new ImageIcon(Interface.class.getResource("/img/edit.png")));
		mnNinjas.add(mntmEditarNinja);
		
		JMenuItem mntmExcluirNinja = new JMenuItem("Excluir ninja");
		mntmExcluirNinja.setBackground(Color.WHITE);
		mntmExcluirNinja.setIcon(new ImageIcon(Interface.class.getResource("/img/delete.png")));
		mnNinjas.add(mntmExcluirNinja);	
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(32, 33, 36));
		mnNinjas.add(separator_1);
		
		JMenuItem mntmAdicionarTransformacaoNatureza = new JMenuItem("Adicionar transforma\u00E7\u00E3o natureza");
		mntmAdicionarTransformacaoNatureza.setBackground(Color.WHITE);
		mntmAdicionarTransformacaoNatureza.setIcon(new ImageIcon(Interface.class.getResource("/img/add.png")));
		mnNinjas.add(mntmAdicionarTransformacaoNatureza);
		
		JMenuItem mntmVerTransformacoesNatureza = new JMenuItem("Ver transforma\u00E7\u00F5es natureza");
		mntmVerTransformacoesNatureza.setBackground(Color.WHITE);
		mntmVerTransformacoesNatureza.setIcon(new ImageIcon(Interface.class.getResource("/img/search.png")));
		mnNinjas.add(mntmVerTransformacoesNatureza);
		
		JMenuItem mntmExcluirTransformacaoNatureza = new JMenuItem("Excluir transforma\u00E7\u00E3o natureza");
		mntmExcluirTransformacaoNatureza.setBackground(Color.WHITE);
		mntmExcluirTransformacaoNatureza.setIcon(new ImageIcon(Interface.class.getResource("/img/delete.png")));
		mnNinjas.add(mntmExcluirTransformacaoNatureza);

		
		// === MENU MISSÕES === //
		JMenu mnMissoes = new JMenu("Miss\u00F5es");
		mnMissoes.setBackground(new Color(32, 33, 36));
		mnMissoes.setForeground(Color.WHITE);
		mnMissoes.setIcon(new ImageIcon(Interface.class.getResource("/img/parchment_1.png")));
		menuBar.add(mnMissoes);

		JMenuItem mntmAdicionarMissao = new JMenuItem("Adicionar miss\u00E3o");
		mntmAdicionarMissao.setBackground(Color.WHITE);
		mntmAdicionarMissao.setIcon(new ImageIcon(Interface.class.getResource("/img/add.png")));
		mnMissoes.add(mntmAdicionarMissao);
		
		JMenuItem mntmVerMissoes = new JMenuItem("Ver miss\u00F5es");
		mntmVerMissoes.setBackground(Color.WHITE);
		mntmVerMissoes.setIcon(new ImageIcon(Interface.class.getResource("/img/search.png")));
		mnMissoes.add(mntmVerMissoes);	
		
		JMenuItem mntmEditarMissao = new JMenuItem("Editar miss\u00E3o");
		mntmEditarMissao.setBackground(Color.WHITE);
		mntmEditarMissao.setIcon(new ImageIcon(Interface.class.getResource("/img/edit.png")));
		mnMissoes.add(mntmEditarMissao);
		
		JMenuItem mntmExcluirMissao = new JMenuItem("Excluir miss\u00E3o");
		mntmExcluirMissao.setBackground(Color.WHITE);
		mntmExcluirMissao.setIcon(new ImageIcon(Interface.class.getResource("/img/delete.png")));
		mnMissoes.add(mntmExcluirMissao);
				
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(new Color(32, 33, 36));
		mnMissoes.add(separator_2);
		
		JMenuItem mntmAdicionarObstaculo = new JMenuItem("Adicionar obst\u00E1culo");
		mntmAdicionarObstaculo.setBackground(Color.WHITE);
		mntmAdicionarObstaculo.setIcon(new ImageIcon(Interface.class.getResource("/img/add.png")));
		mnMissoes.add(mntmAdicionarObstaculo);
		
		JMenuItem mntmVerObstaculos = new JMenuItem("Ver obst\u00E1culos");
		mntmVerObstaculos.setBackground(Color.WHITE);
		mntmVerObstaculos.setIcon(new ImageIcon(Interface.class.getResource("/img/search.png")));
		mnMissoes.add(mntmVerObstaculos);
		
		JMenuItem mntmExcluirObstaculo = new JMenuItem("Excluir obst\u00E1culo");
		mntmExcluirObstaculo.setBackground(Color.WHITE);
		mntmExcluirObstaculo.setIcon(new ImageIcon(Interface.class.getResource("/img/delete.png")));
		mnMissoes.add(mntmExcluirObstaculo);

		
		// === MENU TIMES === //
		JMenu mnTimes = new JMenu("Times");
		mnTimes.setBackground(new Color(32, 33, 36));
		mnTimes.setForeground(Color.WHITE);
		mnTimes.setIcon(new ImageIcon(Interface.class.getResource("/img/handshake.png")));
		menuBar.add(mnTimes);

		JMenuItem mntmAdicionarTime = new JMenuItem("Adicionar time");
		mntmAdicionarTime.setBackground(Color.WHITE);
		mntmAdicionarTime.setIcon(new ImageIcon(Interface.class.getResource("/img/add.png")));
		mnTimes.add(mntmAdicionarTime);

		JMenuItem mntmVerTimes = new JMenuItem("Ver times");
		mntmVerTimes.setBackground(Color.WHITE);
		mntmVerTimes.setIcon(new ImageIcon(Interface.class.getResource("/img/search.png")));
		mnTimes.add(mntmVerTimes);

		JMenuItem mntmEditarTime = new JMenuItem("Editar time");
		mntmEditarTime.setBackground(Color.WHITE);
		mntmEditarTime.setIcon(new ImageIcon(Interface.class.getResource("/img/edit.png")));
		mnTimes.add(mntmEditarTime);

		JMenuItem mntmExcluirTime = new JMenuItem("Excluir time");
		mntmExcluirTime.setBackground(Color.WHITE);
		mntmExcluirTime.setIcon(new ImageIcon(Interface.class.getResource("/img/delete.png")));
		mnTimes.add(mntmExcluirTime);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setForeground(new Color(32,33,36));
		mnTimes.add(separator_5);
		
		JMenuItem mntmAdicionarParticipacao = new JMenuItem("Adicionar participa\u00E7\u00E3o");
		mntmAdicionarParticipacao.setIcon(new ImageIcon(Interface.class.getResource("/img/add.png")));
		mnTimes.add(mntmAdicionarParticipacao);
		
		JMenuItem mntmVerParticipacoes = new JMenuItem("Ver participa\u00E7\u00F5es");
		mntmVerParticipacoes.setIcon(new ImageIcon(Interface.class.getResource("/img/search.png")));
		mnTimes.add(mntmVerParticipacoes);
		
		JMenuItem mntmEditarParticipacao = new JMenuItem("Editar participa\u00E7\u00E3o");
		mntmEditarParticipacao.setIcon(new ImageIcon(Interface.class.getResource("/img/edit.png")));
		mnTimes.add(mntmEditarParticipacao);
		
		JMenuItem mntmExcluirParticipacao = new JMenuItem("Excluir participa\u00E7\u00E3o");
		mntmExcluirParticipacao.setIcon(new ImageIcon(Interface.class.getResource("/img/delete.png")));
		mnTimes.add(mntmExcluirParticipacao);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(32, 33, 36));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(new Color(32, 33, 36));
		contentPane.add(layeredPane, "name_894341346780900");
		layeredPane.setLayout(new CardLayout(0, 0));

		
		// === TELA PRINCIPAL === //
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelPrincipal, "name_895282017409100");
		panelPrincipal.setLayout(null);

		JLabel lblShinobiSystem = new JLabel("ShinobiSystem");
		lblShinobiSystem.setForeground(Color.WHITE);
		lblShinobiSystem.setFont(new Font("Comic Sans MS", Font.PLAIN, 48));
		lblShinobiSystem.setBounds(70, 80, 352, 57);
		panelPrincipal.add(lblShinobiSystem);

		JLabel lblEvandro = new JLabel("Evandro Risso Ven\u00E2ncio");
		lblEvandro.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		lblEvandro.setForeground(Color.WHITE);
		lblEvandro.setBounds(70, 160, 197, 26);
		panelPrincipal.add(lblEvandro);

		JLabel lblLaura = new JLabel("Laura Pessine Teixeira");
		lblLaura.setForeground(Color.WHITE);
		lblLaura.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		lblLaura.setBounds(70, 197, 217, 14);
		panelPrincipal.add(lblLaura);

		KButton btnSaibaMais = new KButton();
		btnSaibaMais.setBorder(null);
		btnSaibaMais.setOpaque(false);
		btnSaibaMais.setText("Saiba mais");
		btnSaibaMais.setkHoverForeGround(Color.WHITE);
		btnSaibaMais.setkBackGroundColor(new Color(248, 54, 0));
		btnSaibaMais.setkPressedColor(new Color(179, 175, 168));
		btnSaibaMais.setkHoverStartColor(new Color(248, 49, 11));
		btnSaibaMais.setkHoverEndColor(new Color(254, 126, 30));
		btnSaibaMais.setkStartColor(new Color(248, 54, 0));
		btnSaibaMais.setkEndColor(new Color(248, 49, 11));
		btnSaibaMais.setBounds(70, 250, 150, 45);
		panelPrincipal.add(btnSaibaMais);

		JLabel capa = new JLabel("");
		capa.setIcon(new ImageIcon(Interface.class.getResource("/img/naruto_capa.png")));
		capa.setBounds(10, 11, 704, 357);
		panelPrincipal.add(capa);

		
		// === CPERSONAGEM === //
		JPanel panelCPersonagem = new JPanel();
		panelCPersonagem.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelCPersonagem, "name_895282035253600");
		panelCPersonagem.setLayout(null);

		KGradientPanel gradientPanelCPersonagem = new KGradientPanel();
		gradientPanelCPersonagem.setOpaque(false);
		gradientPanelCPersonagem.setkBorderRadius(20);
		gradientPanelCPersonagem.setkStartColor(new Color(48, 49, 52));
		gradientPanelCPersonagem.setkEndColor(new Color(48, 49, 52));
		gradientPanelCPersonagem.setBounds(10, 11, 704, 357);
		panelCPersonagem.add(gradientPanelCPersonagem);
		gradientPanelCPersonagem.setLayout(null);

		JPanel panelNome = new JPanel();
		panelNome.setBackground(new Color(48, 49, 52));
		panelNome.setBounds(10, 22, 233, 75);
		gradientPanelCPersonagem.add(panelNome);
		panelNome.setLayout(null);

		JLabel iconNome = new JLabel("");
		iconNome.setIcon(new ImageIcon(Interface.class.getResource("/img/character.png")));
		iconNome.setBounds(47, 42, 16, 16);
		panelNome.add(iconNome);

		JSeparator separatorNome = new JSeparator();
		separatorNome.setBounds(47, 66, 142, 2);
		panelNome.add(separatorNome);

		JLabel lblNome = new JLabel("Nome*");
		lblNome.setForeground(Color.WHITE);
		lblNome.setBounds(47, 11, 86, 14);
		panelNome.add(lblNome);

		tfNome = new JTextField();
		tfNome.setCaretColor(Color.WHITE);
		tfNome.setOpaque(false);
		tfNome.setBounds(71, 42, 118, 20);
		panelNome.add(tfNome);
		tfNome.setForeground(Color.WHITE);
		tfNome.setBorder(null);
		tfNome.setColumns(10);

		JPanel panelDataNascimento = new JPanel();
		panelDataNascimento.setLayout(null);
		panelDataNascimento.setBackground(new Color(48, 49, 52));
		panelDataNascimento.setBounds(10, 97, 233, 75);
		gradientPanelCPersonagem.add(panelDataNascimento);

		JLabel iconDataNascimento = new JLabel("");
		iconDataNascimento.setIcon(new ImageIcon(Interface.class.getResource("/img/character.png")));
		iconDataNascimento.setBounds(47, 42, 16, 16);
		panelDataNascimento.add(iconDataNascimento);

		JSeparator separatorDataNascimento = new JSeparator();
		separatorDataNascimento.setBounds(47, 66, 142, 2);
		panelDataNascimento.add(separatorDataNascimento);

		JLabel lblDataNascimento = new JLabel("Data de nascimento");
		lblDataNascimento.setForeground(Color.WHITE);
		lblDataNascimento.setBounds(47, 11, 144, 14);
		panelDataNascimento.add(lblDataNascimento);

		JFormattedTextField tfDataNascimento = new JFormattedTextField();
		tfDataNascimento.setCaretColor(Color.WHITE);
		tfDataNascimento.setForeground(Color.WHITE);
		tfDataNascimento.setBorder(null);
		tfDataNascimento.setOpaque(false);
		tfDataNascimento.setBounds(73, 42, 118, 20);
		tfDataNascimento.setText("aaaa-mm-dd");
		
		tfDataNascimento.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDataNascimento.setText("");
		        formatarCampo(tfDataNascimento);
			}
			@Override
		    public void focusLost(FocusEvent e) {
		        if (tfDataNascimento.getText().equals("    -  -  ")) {
		            try {
		                MaskFormatter mask = new MaskFormatter("****-**-**");
		                mask.install(tfDataNascimento);
		                tfDataNascimento.setText("aaaammdd");
		            } catch (ParseException e1) {
		                e1.printStackTrace();
		            }
		        }
		    }
		});
		panelDataNascimento.add(tfDataNascimento);

		JPanel panelOcupacao = new JPanel();
		panelOcupacao.setLayout(null);
		panelOcupacao.setBackground(new Color(48, 49, 52));
		panelOcupacao.setBounds(10, 171, 233, 75);
		gradientPanelCPersonagem.add(panelOcupacao);

		JLabel iconOcupacao = new JLabel("");
		iconOcupacao.setIcon(new ImageIcon(Interface.class.getResource("/img/character.png")));
		iconOcupacao.setBounds(47, 42, 16, 16);
		panelOcupacao.add(iconOcupacao);

		JSeparator separatorOcupacao = new JSeparator();
		separatorOcupacao.setBounds(47, 66, 142, 2);
		panelOcupacao.add(separatorOcupacao);

		JLabel lblOcupacao = new JLabel("Ocupa\u00E7\u00E3o");
		lblOcupacao.setForeground(Color.WHITE);
		lblOcupacao.setBounds(47, 11, 112, 14);
		panelOcupacao.add(lblOcupacao);

		tfOcupacao = new JTextField();
		tfOcupacao.setCaretColor(Color.WHITE);
		tfOcupacao.setForeground(Color.WHITE);
		tfOcupacao.setOpaque(false);
		tfOcupacao.setBorder(null);
		tfOcupacao.setBounds(71, 42, 118, 20);
		panelOcupacao.add(tfOcupacao);
		tfOcupacao.setColumns(10);

		JPanel panelSexo = new JPanel();
		panelSexo.setLayout(null);
		panelSexo.setBackground(new Color(48, 49, 52));
		panelSexo.setBounds(10, 246, 233, 75);
		gradientPanelCPersonagem.add(panelSexo);

		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setBounds(46, 11, 46, 14);
		panelSexo.add(lblSexo);
		lblSexo.setForeground(Color.WHITE);

		JRadioButton rdbtnM = new JRadioButton("M");
		rdbtnM.setFocusable(false);
		rdbtnM.setBounds(46, 32, 46, 23);
		panelSexo.add(rdbtnM);
		rdbtnM.setForeground(Color.WHITE);
		rdbtnM.setBackground(new Color(48, 49, 52));

		JRadioButton rdbtnF = new JRadioButton("F");
		rdbtnF.setFocusable(false);
		rdbtnF.setBounds(94, 32, 38, 23);
		panelSexo.add(rdbtnF);
		rdbtnF.setForeground(Color.WHITE);
		rdbtnF.setBackground(new Color(48, 49, 52));

		JLabel lblObrigatorio = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio.setBounds(47, 332, 75, 14);
		gradientPanelCPersonagem.add(lblObrigatorio);
		lblObrigatorio.setForeground(Color.WHITE);

		JPanel panelPeso = new JPanel();
		panelPeso.setLayout(null);
		panelPeso.setBackground(new Color(48, 49, 52));
		panelPeso.setBounds(461, 22, 233, 75);
		gradientPanelCPersonagem.add(panelPeso);

		JLabel iconPeso = new JLabel("");
		iconPeso.setIcon(new ImageIcon(Interface.class.getResource("/img/character.png")));
		iconPeso.setBounds(47, 42, 16, 16);
		panelPeso.add(iconPeso);

		JSeparator separatorPeso = new JSeparator();
		separatorPeso.setBounds(47, 66, 142, 2);
		panelPeso.add(separatorPeso);

		JLabel lblPeso = new JLabel("Peso (kg)");
		lblPeso.setForeground(Color.WHITE);
		lblPeso.setBounds(47, 11, 86, 14);
		panelPeso.add(lblPeso);

		tfPeso = new JTextField();
		tfPeso.setCaretColor(Color.WHITE);
		tfPeso.setBorder(null);
		tfPeso.setOpaque(false);
		tfPeso.setForeground(Color.WHITE);
		tfPeso.setBounds(71, 42, 118, 20);
		panelPeso.add(tfPeso);
		tfPeso.setColumns(10);

		JPanel panelAltura = new JPanel();
		panelAltura.setLayout(null);
		panelAltura.setBackground(new Color(48, 49, 52));
		panelAltura.setBounds(461, 97, 233, 75);
		gradientPanelCPersonagem.add(panelAltura);

		JLabel iconAltura = new JLabel("");
		iconAltura.setIcon(new ImageIcon(Interface.class.getResource("/img/character.png")));
		iconAltura.setBounds(47, 42, 16, 16);
		panelAltura.add(iconAltura);

		JSeparator separatorAltura = new JSeparator();
		separatorAltura.setBounds(47, 66, 142, 2);
		panelAltura.add(separatorAltura);

		JLabel lblAltura = new JLabel("Altura (m)");
		lblAltura.setForeground(Color.WHITE);
		lblAltura.setBounds(47, 11, 86, 14);
		panelAltura.add(lblAltura);

		tfAltura = new JTextField();
		tfAltura.setCaretColor(Color.WHITE);
		tfAltura.setBorder(null);
		tfAltura.setOpaque(false);
		tfAltura.setForeground(Color.WHITE);
		tfAltura.setBounds(71, 42, 118, 20);
		panelAltura.add(tfAltura);
		tfAltura.setColumns(10);

		JPanel panelTipoSanguineo = new JPanel();
		panelTipoSanguineo.setLayout(null);
		panelTipoSanguineo.setBackground(new Color(48, 49, 52));
		panelTipoSanguineo.setBounds(461, 171, 233, 75);
		gradientPanelCPersonagem.add(panelTipoSanguineo);

		JLabel lblTipoSanguineo = new JLabel("Tipo sangu\u00EDneo");
		lblTipoSanguineo.setBounds(47, 11, 86, 14);
		panelTipoSanguineo.add(lblTipoSanguineo);
		lblTipoSanguineo.setForeground(Color.WHITE);

		JComboBox<String> cbTipoSanguineo = new JComboBox<>();
		cbTipoSanguineo.setForeground(Color.WHITE);
		cbTipoSanguineo.setBackground(Color.GRAY);
		cbTipoSanguineo.setBounds(47, 35, 75, 22);
		panelTipoSanguineo.add(cbTipoSanguineo);
		cbTipoSanguineo.setModel(new DefaultComboBoxModel<String>(new String[] { "-", "A", "B", "AB", "O" }));

		btnAdicionarPersonagem = new KButton();
		btnAdicionarPersonagem.setBorder(null);
		btnAdicionarPersonagem.setOpaque(false);
		btnAdicionarPersonagem.setText("Adicionar");
		btnAdicionarPersonagem.setkHoverForeGround(Color.WHITE);
		btnAdicionarPersonagem.setkPressedColor(new Color(179, 175, 168));
		btnAdicionarPersonagem.setkHoverStartColor(new Color(248, 49, 11));
		btnAdicionarPersonagem.setkHoverEndColor(new Color(254, 126, 30));
		btnAdicionarPersonagem.setkStartColor(new Color(254, 140, 0));
		btnAdicionarPersonagem.setkEndColor(new Color(248, 54, 0));
		btnAdicionarPersonagem.setBounds(507, 272, 144, 32);
		gradientPanelCPersonagem.add(btnAdicionarPersonagem);

		JLabel iconNarutoC = new JLabel("");
		iconNarutoC.setIcon(new ImageIcon(Interface.class.getResource("/img/naruto_icon_top.png")));
		iconNarutoC.setBounds(306, 11, 92, 96);
		gradientPanelCPersonagem.add(iconNarutoC);

		
		// === RPERSONAGEM === //
		JPanel panelRPersonagem = new JPanel();
		panelRPersonagem.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelRPersonagem, "name_895295730336300");
		panelRPersonagem.setLayout(null);

		KGradientPanel gradientPanelRPersonagem = new KGradientPanel();
		gradientPanelRPersonagem.setOpaque(false);
		gradientPanelRPersonagem.setkBorderRadius(20);
		gradientPanelRPersonagem.setkStartColor(new Color(48, 49, 52));
		gradientPanelRPersonagem.setkEndColor(new Color(48, 49, 52));
		gradientPanelRPersonagem.setBounds(10, 11, 704, 357);
		panelRPersonagem.add(gradientPanelRPersonagem);
		gradientPanelRPersonagem.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBackground(Color.DARK_GRAY);
		scrollPane.setForeground(new Color(130, 130, 130));
		scrollPane.setBounds(10, 112, 684, 234);
		gradientPanelRPersonagem.add(scrollPane);
		
		tablePersonagem = new JTable();
		tablePersonagem.setBorder(null);
		tablePersonagem.setFocusable(false);
		tablePersonagem.setSelectionBackground(new Color(130, 130, 130));
		tablePersonagem.setBackground(new Color(130, 130, 130));
		tablePersonagem.setGridColor(new Color(151, 151, 151));
		tablePersonagem.setForeground(Color.WHITE);
		tablePersonagem.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "Sexo", "Data de nascimento", "Idade", "Tipo sangu\u00EDneo", "Peso", "Altura", "Ocupa\u00E7\u00E3o"
			}
		) {
			Class<?>[] columnTypes = new Class[] {
				String.class, String.class, String.class, Integer.class, String.class, String.class, String.class, String.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tablePersonagem.getColumnModel().getColumn(0).setPreferredWidth(110);
		tablePersonagem.getColumnModel().getColumn(1).setPreferredWidth(40);
		tablePersonagem.getColumnModel().getColumn(2).setPreferredWidth(110);
		tablePersonagem.getColumnModel().getColumn(3).setPreferredWidth(45);
		tablePersonagem.getColumnModel().getColumn(4).setPreferredWidth(95);
		tablePersonagem.setDefaultRenderer(Object.class, new cell());
		
		scrollPane.setViewportView(tablePersonagem);

		JLabel iconNarutoR = new JLabel("");
		iconNarutoR.setIcon(new ImageIcon(Interface.class.getResource("/img/naruto_icon_top.png")));
		iconNarutoR.setBounds(306, 11, 92, 96);
		gradientPanelRPersonagem.add(iconNarutoR);

		
		// === UPERSONAGEM === //
		JPanel panelUPersonagem = new JPanel();
		panelUPersonagem.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelUPersonagem, "name_1066628447034100");
		panelUPersonagem.setLayout(null);

		KGradientPanel gradientPanelUPersonagem = new KGradientPanel();
		gradientPanelUPersonagem.setOpaque(false);
		gradientPanelUPersonagem.setkBorderRadius(20);
		gradientPanelUPersonagem.setkStartColor(new Color(48, 49, 52));
		gradientPanelUPersonagem.setkEndColor(new Color(48, 49, 52));
		gradientPanelUPersonagem.setBounds(10, 11, 704, 357);
		panelUPersonagem.add(gradientPanelUPersonagem);
		gradientPanelUPersonagem.setLayout(null);

		JLabel iconNarutoU = new JLabel("");
		iconNarutoU.setIcon(new ImageIcon(Interface.class.getResource("/img/naruto_icon_top.png")));
		iconNarutoU.setBounds(306, 11, 92, 96);
		gradientPanelUPersonagem.add(iconNarutoU);

		JLabel lblNewLabel = new JLabel("Selecione o nome do personagem para edit\u00E1-lo:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(210, 127, 306, 17);
		gradientPanelUPersonagem.add(lblNewLabel);

		KButton btnOK_EditarPersonagem = new KButton();
		btnOK_EditarPersonagem.setBorder(null);
		btnOK_EditarPersonagem.setOpaque(false);
		btnOK_EditarPersonagem.setText("OK");
		btnOK_EditarPersonagem.setkHoverForeGround(Color.WHITE);
		btnOK_EditarPersonagem.setkPressedColor(new Color(179, 175, 168));
		btnOK_EditarPersonagem.setkHoverStartColor(new Color(248, 49, 11));
		btnOK_EditarPersonagem.setkHoverEndColor(new Color(254, 126, 30));
		btnOK_EditarPersonagem.setkStartColor(new Color(254, 140, 0));
		btnOK_EditarPersonagem.setkEndColor(new Color(248, 54, 0));
		btnOK_EditarPersonagem.setBounds(392, 195, 90, 30);
		gradientPanelUPersonagem.add(btnOK_EditarPersonagem);

		JComboBox<String> cbNomeEditar = new JComboBox<>();
		cbNomeEditar.setForeground(Color.WHITE);
		cbNomeEditar.setBackground(Color.GRAY);
		cbNomeEditar.setBounds(210, 199, 172, 22);
		gradientPanelUPersonagem.add(cbNomeEditar);

		
		// === UPERSONAGEM_1 === //
		JPanel panelUPersonagem_1 = new JPanel();
		panelUPersonagem_1.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelUPersonagem_1, "name_1067232308323200");
		panelUPersonagem_1.setLayout(null);

		KGradientPanel gradientPanelUPersonagem_1 = new KGradientPanel();
		gradientPanelUPersonagem_1.setOpaque(false);
		gradientPanelUPersonagem_1.setLayout(null);
		gradientPanelUPersonagem_1.setkStartColor(new Color(48, 49, 52));
		gradientPanelUPersonagem_1.setkEndColor(new Color(48, 49, 52));
		gradientPanelUPersonagem_1.setkBorderRadius(20);
		gradientPanelUPersonagem_1.setBounds(10, 11, 704, 357);
		panelUPersonagem_1.add(gradientPanelUPersonagem_1);

		JPanel panelNome_1 = new JPanel();
		panelNome_1.setLayout(null);
		panelNome_1.setBackground(new Color(48, 49, 52));
		panelNome_1.setBounds(10, 22, 233, 75);
		gradientPanelUPersonagem_1.add(panelNome_1);

		JLabel iconNome_1 = new JLabel("");
		iconNome_1.setIcon(new ImageIcon(Interface.class.getResource("/img/character.png")));
		iconNome_1.setBounds(47, 42, 16, 16);
		panelNome_1.add(iconNome_1);

		JSeparator separatorNome_1 = new JSeparator();
		separatorNome_1.setBounds(47, 66, 142, 2);
		panelNome_1.add(separatorNome_1);

		JLabel lblNome_1 = new JLabel("Nome*");
		lblNome_1.setForeground(Color.WHITE);
		lblNome_1.setBounds(47, 11, 86, 14);
		panelNome_1.add(lblNome_1);

		tfNome_1 = new JTextField();
		tfNome_1.setOpaque(false);
		tfNome_1.setForeground(Color.WHITE);
		tfNome_1.setColumns(10);
		tfNome_1.setCaretColor(Color.WHITE);
		tfNome_1.setBorder(null);
		tfNome_1.setBounds(71, 42, 118, 20);
		panelNome_1.add(tfNome_1);

		JPanel panelDataNascimento_1 = new JPanel();
		panelDataNascimento_1.setLayout(null);
		panelDataNascimento_1.setBackground(new Color(48, 49, 52));
		panelDataNascimento_1.setBounds(10, 97, 233, 75);
		gradientPanelUPersonagem_1.add(panelDataNascimento_1);

		JLabel iconDataNascimento_1 = new JLabel("");
		iconDataNascimento_1.setIcon(new ImageIcon(Interface.class.getResource("/img/character.png")));
		iconDataNascimento_1.setBounds(47, 42, 16, 16);
		panelDataNascimento_1.add(iconDataNascimento_1);

		JSeparator separatorDataNascimento_1 = new JSeparator();
		separatorDataNascimento_1.setBounds(47, 66, 142, 2);
		panelDataNascimento_1.add(separatorDataNascimento_1);

		JLabel lblDataNascimento_1 = new JLabel("Data de nascimento");
		lblDataNascimento_1.setForeground(Color.WHITE);
		lblDataNascimento_1.setBounds(47, 11, 144, 14);
		panelDataNascimento_1.add(lblDataNascimento_1);

		JFormattedTextField tfDataNascimento_1 = new JFormattedTextField();
		tfDataNascimento_1.setOpaque(false);
		tfDataNascimento_1.setForeground(Color.WHITE);
		tfDataNascimento_1.setCaretColor(Color.WHITE);
		tfDataNascimento_1.setBorder(null);
		tfDataNascimento_1.setBounds(73, 42, 118, 20);
		tfDataNascimento_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDataNascimento_1.setText("");
		        formatarCampo(tfDataNascimento_1);
			}
			@Override
		    public void focusLost(FocusEvent e) {
		        if (tfDataNascimento_1.getText().equals("    -  -  ")) {
		            try {
		                MaskFormatter mask = new MaskFormatter("****-**-**");
		                mask.install(tfDataNascimento_1);
		                tfDataNascimento_1.setText("aaaammdd");
		            } catch (ParseException e1) {
		                e1.printStackTrace();
		            }
		        }
		    }
		});
		panelDataNascimento_1.add(tfDataNascimento_1);

		JPanel panelOcupacao_1 = new JPanel();
		panelOcupacao_1.setLayout(null);
		panelOcupacao_1.setBackground(new Color(48, 49, 52));
		panelOcupacao_1.setBounds(10, 171, 233, 75);
		gradientPanelUPersonagem_1.add(panelOcupacao_1);

		JLabel iconOcupacao_1 = new JLabel("");
		iconOcupacao_1.setIcon(new ImageIcon(Interface.class.getResource("/img/character.png")));
		iconOcupacao_1.setBounds(47, 42, 16, 16);
		panelOcupacao_1.add(iconOcupacao_1);

		JSeparator separatorOcupacao_1 = new JSeparator();
		separatorOcupacao_1.setBounds(47, 66, 142, 2);
		panelOcupacao_1.add(separatorOcupacao_1);

		JLabel lblOcupacao_1 = new JLabel("Ocupa\u00E7\u00E3o");
		lblOcupacao_1.setForeground(Color.WHITE);
		lblOcupacao_1.setBounds(47, 11, 112, 14);
		panelOcupacao_1.add(lblOcupacao_1);

		tfOcupacao_1 = new JTextField();
		tfOcupacao_1.setOpaque(false);
		tfOcupacao_1.setForeground(Color.WHITE);
		tfOcupacao_1.setColumns(10);
		tfOcupacao_1.setCaretColor(Color.WHITE);
		tfOcupacao_1.setBorder(null);
		tfOcupacao_1.setBounds(71, 42, 118, 20);
		panelOcupacao_1.add(tfOcupacao_1);

		JPanel panelSexo_1 = new JPanel();
		panelSexo_1.setLayout(null);
		panelSexo_1.setBackground(new Color(48, 49, 52));
		panelSexo_1.setBounds(10, 246, 233, 75);
		gradientPanelUPersonagem_1.add(panelSexo_1);

		JLabel lblSexo_1 = new JLabel("Sexo");
		lblSexo_1.setForeground(Color.WHITE);
		lblSexo_1.setBounds(46, 11, 46, 14);
		panelSexo_1.add(lblSexo_1);

		JRadioButton rdbtnM_1 = new JRadioButton("M");
		rdbtnM_1.setForeground(Color.WHITE);
		rdbtnM_1.setBackground(new Color(48, 49, 52));
		rdbtnM_1.setBounds(46, 32, 46, 23);
		panelSexo_1.add(rdbtnM_1);

		JRadioButton rdbtnF_1 = new JRadioButton("F");
		rdbtnF_1.setForeground(Color.WHITE);
		rdbtnF_1.setBackground(new Color(48, 49, 52));
		rdbtnF_1.setBounds(94, 32, 38, 23);
		panelSexo_1.add(rdbtnF_1);

		JLabel lblObrigatorio_1 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_1.setForeground(Color.WHITE);
		lblObrigatorio_1.setBounds(47, 332, 75, 14);
		gradientPanelUPersonagem_1.add(lblObrigatorio_1);

		JPanel panelPeso_1 = new JPanel();
		panelPeso_1.setLayout(null);
		panelPeso_1.setBackground(new Color(48, 49, 52));
		panelPeso_1.setBounds(461, 22, 233, 75);
		gradientPanelUPersonagem_1.add(panelPeso_1);

		JLabel iconPeso_1 = new JLabel("");
		iconPeso_1.setIcon(new ImageIcon(Interface.class.getResource("/img/character.png")));
		iconPeso_1.setBounds(47, 42, 16, 16);
		panelPeso_1.add(iconPeso_1);

		JSeparator separatorPeso_1 = new JSeparator();
		separatorPeso_1.setBounds(47, 66, 142, 2);
		panelPeso_1.add(separatorPeso_1);

		JLabel lblPeso_1 = new JLabel("Peso (kg)");
		lblPeso_1.setForeground(Color.WHITE);
		lblPeso_1.setBounds(47, 11, 86, 14);
		panelPeso_1.add(lblPeso_1);

		tfPeso_1 = new JTextField();
		tfPeso_1.setOpaque(false);
		tfPeso_1.setForeground(Color.WHITE);
		tfPeso_1.setColumns(10);
		tfPeso_1.setCaretColor(Color.WHITE);
		tfPeso_1.setBorder(null);
		tfPeso_1.setBounds(71, 42, 118, 20);
		panelPeso_1.add(tfPeso_1);

		JPanel panelAltura_1 = new JPanel();
		panelAltura_1.setLayout(null);
		panelAltura_1.setBackground(new Color(48, 49, 52));
		panelAltura_1.setBounds(461, 97, 233, 75);
		gradientPanelUPersonagem_1.add(panelAltura_1);

		JLabel iconAltura_1 = new JLabel("");
		iconAltura_1.setIcon(new ImageIcon(Interface.class.getResource("/img/character.png")));
		iconAltura_1.setBounds(47, 42, 16, 16);
		panelAltura_1.add(iconAltura_1);

		JSeparator separatorAltura_1 = new JSeparator();
		separatorAltura_1.setBounds(47, 66, 142, 2);
		panelAltura_1.add(separatorAltura_1);

		JLabel lblAltura_1 = new JLabel("Altura (m)");
		lblAltura_1.setForeground(Color.WHITE);
		lblAltura_1.setBounds(47, 11, 86, 14);
		panelAltura_1.add(lblAltura_1);

		tfAltura_1 = new JTextField();
		tfAltura_1.setOpaque(false);
		tfAltura_1.setForeground(Color.WHITE);
		tfAltura_1.setColumns(10);
		tfAltura_1.setCaretColor(Color.WHITE);
		tfAltura_1.setBorder(null);
		tfAltura_1.setBounds(71, 42, 118, 20);
		panelAltura_1.add(tfAltura_1);

		JPanel panelTipoSanguineo_1 = new JPanel();
		panelTipoSanguineo_1.setLayout(null);
		panelTipoSanguineo_1.setBackground(new Color(48, 49, 52));
		panelTipoSanguineo_1.setBounds(461, 171, 233, 75);
		gradientPanelUPersonagem_1.add(panelTipoSanguineo_1);

		JLabel lblTipoSanguineo_1 = new JLabel("Tipo sangu\u00EDneo");
		lblTipoSanguineo_1.setForeground(Color.WHITE);
		lblTipoSanguineo_1.setBounds(47, 11, 86, 14);
		panelTipoSanguineo_1.add(lblTipoSanguineo_1);

		JComboBox<String> cbTipoSanguineo_1 = new JComboBox<String>();
		cbTipoSanguineo_1.setModel(new DefaultComboBoxModel<String>(new String[] { "-", "AB", "A", "B", "O" }));
		cbTipoSanguineo_1.setForeground(Color.WHITE);
		cbTipoSanguineo_1.setBackground(Color.GRAY);
		cbTipoSanguineo_1.setBounds(47, 35, 75, 22);
		panelTipoSanguineo_1.add(cbTipoSanguineo_1);

		KButton btnEditarPersonagem = new KButton();
		btnEditarPersonagem.setBorder(null);
		btnEditarPersonagem.setOpaque(false);
		btnEditarPersonagem.setText("Editar");
		btnEditarPersonagem.setkStartColor(new Color(254, 140, 0));
		btnEditarPersonagem.setkPressedColor(new Color(179, 175, 168));
		btnEditarPersonagem.setkHoverStartColor(new Color(248, 49, 11));
		btnEditarPersonagem.setkHoverForeGround(Color.WHITE);
		btnEditarPersonagem.setkHoverEndColor(new Color(254, 126, 30));
		btnEditarPersonagem.setkEndColor(new Color(248, 54, 0));
		btnEditarPersonagem.setBounds(507, 272, 144, 32);
		gradientPanelUPersonagem_1.add(btnEditarPersonagem);

		JLabel iconNarutoU_1 = new JLabel("");
		iconNarutoU_1.setIcon(new ImageIcon(Interface.class.getResource("/img/naruto_icon_top.png")));
		iconNarutoU_1.setBounds(306, 11, 92, 96);
		gradientPanelUPersonagem_1.add(iconNarutoU_1);

		
		// === DPERSONAGEM === //
		JPanel panelDPersonagem = new JPanel();
		panelDPersonagem.setBackground(new Color(32, 33, 36));
		panelDPersonagem.setLayout(null);
		layeredPane.add(panelDPersonagem, "name_1493380686480200");

		KGradientPanel gradientPanelDPersonagem = new KGradientPanel();
		gradientPanelDPersonagem.setLayout(null);
		gradientPanelDPersonagem.setOpaque(false);
		gradientPanelDPersonagem.setkBorderRadius(20);
		gradientPanelDPersonagem.setkStartColor(new Color(48, 49, 52));
		gradientPanelDPersonagem.setkEndColor(new Color(48, 49, 52));
		gradientPanelDPersonagem.setBounds(10, 11, 704, 357);
		panelDPersonagem.add(gradientPanelDPersonagem);

		JLabel iconNarutoU_2 = new JLabel("");
		iconNarutoU_2.setIcon(new ImageIcon(Interface.class.getResource("/img/naruto_icon_top.png")));
		iconNarutoU_2.setBounds(306, 11, 92, 96);
		gradientPanelDPersonagem.add(iconNarutoU_2);

		JLabel lblTituloUPersonagem = new JLabel("Selecione o nome do personagem para exclu\u00ED-lo:");
		lblTituloUPersonagem.setForeground(Color.WHITE);
		lblTituloUPersonagem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTituloUPersonagem.setBounds(210, 127, 306, 17);
		gradientPanelDPersonagem.add(lblTituloUPersonagem);

		KButton btnOK_ExcluirPersonagem = new KButton();
		btnOK_ExcluirPersonagem.setBorder(null);
		btnOK_ExcluirPersonagem.setOpaque(false);
		btnOK_ExcluirPersonagem.setText("OK");
		btnOK_ExcluirPersonagem.setkHoverForeGround(Color.WHITE);
		btnOK_ExcluirPersonagem.setkPressedColor(new Color(179, 175, 168));
		btnOK_ExcluirPersonagem.setkHoverStartColor(new Color(248, 49, 11));
		btnOK_ExcluirPersonagem.setkHoverEndColor(new Color(254, 126, 30));
		btnOK_ExcluirPersonagem.setkStartColor(new Color(254, 140, 0));
		btnOK_ExcluirPersonagem.setkEndColor(new Color(248, 54, 0));
		btnOK_ExcluirPersonagem.setBounds(392, 195, 90, 30);
		gradientPanelDPersonagem.add(btnOK_ExcluirPersonagem);

		JComboBox<String> cbNomeExcluir = new JComboBox<>();
		cbNomeExcluir.setForeground(Color.WHITE);
		cbNomeExcluir.setBackground(Color.GRAY);
		cbNomeExcluir.setBounds(210, 199, 172, 22);
		gradientPanelDPersonagem.add(cbNomeExcluir);

		
		// === CNINJA === //
		JPanel panelCNinja = new JPanel();
		panelCNinja.setBackground(new Color(32, 33, 36));
		panelCNinja.setLayout(null);
		layeredPane.add(panelCNinja, "name_1498134671425800");

		KGradientPanel gradientPanelCNinja = new KGradientPanel();
		gradientPanelCNinja.setBounds(0, 0, 724, 379);
		gradientPanelCNinja.setOpaque(false);
		gradientPanelCNinja.setkBorderRadius(20);
		gradientPanelCNinja.setkStartColor(new Color(48, 49, 52));
		gradientPanelCNinja.setkEndColor(new Color(48, 49, 52));
		gradientPanelCNinja.setBounds(10, 11, 704, 357);
		panelCNinja.add(gradientPanelCNinja);
		gradientPanelCNinja.setLayout(null);

		JPanel panelNomePersonagem = new JPanel();
		panelNomePersonagem.setLayout(null);
		panelNomePersonagem.setBackground(new Color(48, 49, 52));
		panelNomePersonagem.setBounds(10, 106, 233, 75);
		gradientPanelCNinja.add(panelNomePersonagem);

		JLabel lblNomePersonagem = new JLabel("Nome do personagem*");
		lblNomePersonagem.setForeground(Color.WHITE);
		lblNomePersonagem.setBounds(47, 11, 142, 14);
		panelNomePersonagem.add(lblNomePersonagem);
		
		JComboBox<String> cbNomeNinja = new JComboBox<String>();
		cbNomeNinja.setForeground(Color.WHITE);
		cbNomeNinja.setBackground(Color.GRAY);
		cbNomeNinja.setBounds(47, 36, 142, 28);
		panelNomePersonagem.add(cbNomeNinja);

		JPanel panelPatente = new JPanel();
		panelPatente.setLayout(null);
		panelPatente.setBackground(new Color(48, 49, 52));
		panelPatente.setBounds(10, 192, 233, 75);
		gradientPanelCNinja.add(panelPatente);

		JLabel lblPatente = new JLabel("Patente");
		lblPatente.setForeground(Color.WHITE);
		lblPatente.setBounds(47, 11, 86, 14);
		panelPatente.add(lblPatente);
		
		JComboBox<String> cbPatenteNinja = new JComboBox<String>();
		cbPatenteNinja.setForeground(Color.WHITE);
		cbPatenteNinja.setBackground(Color.GRAY);
		cbPatenteNinja.setModel(new DefaultComboBoxModel<String>(new String[] {"-", "Aluno(a) da academia", "Genin", "Chunin", "Jonin", "Kage", "Anbu", "Chubu"}));
		cbPatenteNinja.setBounds(47, 36, 143, 28);
		panelPatente.add(cbPatenteNinja);

		JPanel panelTitulo = new JPanel();
		panelTitulo.setLayout(null);
		panelTitulo.setBackground(new Color(48, 49, 52));
		panelTitulo.setBounds(461, 106, 233, 75);
		gradientPanelCNinja.add(panelTitulo);

		JLabel iconTitulo = new JLabel("");
		iconTitulo.setIcon(new ImageIcon(Interface.class.getResource("/img/ninja-blade_1.png")));
		iconTitulo.setBounds(47, 42, 16, 16);
		panelTitulo.add(iconTitulo);

		JSeparator separatorTitulo = new JSeparator();
		separatorTitulo.setBounds(47, 66, 142, 2);
		panelTitulo.add(separatorTitulo);

		JLabel lblTitulo = new JLabel("T\u00EDtulo");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(47, 11, 86, 14);
		panelTitulo.add(lblTitulo);

		tfTitulo = new JTextField();
		tfTitulo.setOpaque(false);
		tfTitulo.setForeground(Color.WHITE);
		tfTitulo.setColumns(10);
		tfTitulo.setCaretColor(Color.WHITE);
		tfTitulo.setBorder(null);
		tfTitulo.setBounds(71, 42, 118, 20);
		panelTitulo.add(tfTitulo);

		JLabel lblObrigatorio_2 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_2.setForeground(Color.WHITE);
		lblObrigatorio_2.setBounds(47, 332, 75, 14);
		gradientPanelCNinja.add(lblObrigatorio_2);

		KButton btnAdicionarNinja = new KButton();
		btnAdicionarNinja.setBorder(null);
		btnAdicionarNinja.setOpaque(false);
		btnAdicionarNinja.setText("Adicionar");
		btnAdicionarNinja.setkHoverForeGround(Color.WHITE);
		btnAdicionarNinja.setkPressedColor(new Color(179, 175, 168));
		btnAdicionarNinja.setkHoverStartColor(new Color(248, 49, 11));
		btnAdicionarNinja.setkHoverEndColor(new Color(254, 126, 30));
		btnAdicionarNinja.setkStartColor(new Color(254, 140, 0));
		btnAdicionarNinja.setkEndColor(new Color(248, 54, 0));
		btnAdicionarNinja.setBounds(509, 215, 144, 32);
		gradientPanelCNinja.add(btnAdicionarNinja);

		JLabel iconNarutoC_1 = new JLabel("");
		iconNarutoC_1.setIcon(new ImageIcon(Interface.class.getResource("/img/ninja.png")));
		iconNarutoC_1.setBounds(304, 11, 95, 90);
		gradientPanelCNinja.add(iconNarutoC_1);

		
		// === RNINJA === //
		JPanel panelRNinja = new JPanel();
		panelRNinja.setLayout(null);
		panelRNinja.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelRNinja, "name_143509142419000");

		KGradientPanel gradientPanelRNinja = new KGradientPanel();
		gradientPanelRNinja.setLayout(null);
		gradientPanelRNinja.setOpaque(false);
		gradientPanelRNinja.setkBorderRadius(20);
		gradientPanelRNinja.setkStartColor(new Color(48, 49, 52));
		gradientPanelRNinja.setkEndColor(new Color(48, 49, 52));
		gradientPanelRNinja.setBounds(10, 11, 704, 357);
		panelRNinja.add(gradientPanelRNinja);

		JScrollPane scrollPaneNinja = new JScrollPane();
		scrollPaneNinja.setForeground(new Color(130, 130, 130));
		scrollPaneNinja.setBorder(null);
		scrollPaneNinja.setBackground(Color.DARK_GRAY);
		scrollPaneNinja.setBounds(10, 112, 684, 234);
		gradientPanelRNinja.add(scrollPaneNinja);

		tableNinja = new JTable();
		tableNinja.setBackground(new Color(130,130,130));
		tableNinja.setBorder(null);
		tableNinja.setFocusable(false);
		tableNinja.setSelectionForeground(Color.WHITE);
		tableNinja.setSelectionBackground(Color.WHITE);
		tableNinja.setForeground(Color.WHITE);
		tableNinja.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Nome do(a) personagem", "Patente", "T\u00EDtulo" }) {
			Class<?>[] columnTypes = new Class[] { String.class, String.class, String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableNinja.getColumnModel().getColumn(0).setPreferredWidth(140);
		tableNinja.getColumnModel().getColumn(0).setHeaderRenderer(new header(scrollPane));
		tableNinja.getColumnModel().getColumn(1).setHeaderRenderer(new header(scrollPane));
		tableNinja.getColumnModel().getColumn(2).setHeaderRenderer(new header(scrollPane));
		tableNinja.setDefaultRenderer(Object.class, new cell());
		scrollPaneNinja.setViewportView(tableNinja);

		JLabel iconNarutoR_1 = new JLabel("");
		iconNarutoR_1.setIcon(new ImageIcon(Interface.class.getResource("/img/ninja.png")));
		iconNarutoR_1.setBounds(304, 11, 95, 90);
		gradientPanelRNinja.add(iconNarutoR_1);
		
		KButton btnSelecionarPatente = new KButton();
		btnSelecionarPatente.setOpaque(false);
		btnSelecionarPatente.setText("OK");
		btnSelecionarPatente.setBounds(582, 44, 73, 32);
		btnSelecionarPatente.setBorder(null);
		btnSelecionarPatente.setkHoverForeGround(Color.WHITE);
		btnSelecionarPatente.setkPressedColor(new Color(179, 175, 168));
		btnSelecionarPatente.setkHoverStartColor(new Color(248, 49, 11));
		btnSelecionarPatente.setkHoverEndColor(new Color(254, 126, 30));
		btnSelecionarPatente.setkStartColor(new Color(254, 140, 0));
		btnSelecionarPatente.setkEndColor(new Color(248, 54, 0));
		gradientPanelRNinja.add(btnSelecionarPatente);
		
		KButton btnRedefinirNinja = new KButton();
		btnRedefinirNinja.setIconTextGap(9);
		btnRedefinirNinja.setIcon(new ImageIcon(Interface.class.getResource("/img/reload.png")));
		btnRedefinirNinja.setOpaque(false);
		btnRedefinirNinja.setBounds(51, 44, 34, 32);
		btnRedefinirNinja.setBorder(null);
		btnRedefinirNinja.setkHoverForeGround(Color.WHITE);
		btnRedefinirNinja.setkPressedColor(new Color(179, 175, 168));
		btnRedefinirNinja.setkHoverStartColor(new Color(248, 49, 11));
		btnRedefinirNinja.setkHoverEndColor(new Color(254, 126, 30));
		btnRedefinirNinja.setkStartColor(new Color(254, 140, 0));
		btnRedefinirNinja.setkEndColor(new Color(248, 54, 0));
		gradientPanelRNinja.add(btnRedefinirNinja);
		
		JComboBox<String> cbSelecionarPatente = new JComboBox<String>();
		cbSelecionarPatente.setForeground(Color.WHITE);
		cbSelecionarPatente.setBackground(Color.GRAY);
		cbSelecionarPatente.setModel(new DefaultComboBoxModel<String>(new String[] {"-", "Aluno(a) da academia", "Genin", "Chunin", "Jonin", "Kage", "Anbu", "Chubu"}));
		cbSelecionarPatente.setBounds(460, 49, 112, 22);
		gradientPanelRNinja.add(cbSelecionarPatente);
		
		JLabel lblNewLabel_12 = new JLabel("Redefinir:");
		lblNewLabel_12.setForeground(Color.WHITE);
		lblNewLabel_12.setBounds(51, 24, 73, 14);
		gradientPanelRNinja.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("Ordenar por patente:");
		lblNewLabel_13.setForeground(Color.WHITE);
		lblNewLabel_13.setBounds(460, 24, 133, 14);
		gradientPanelRNinja.add(lblNewLabel_13);

		
		// === UNINJA === //
		JPanel panelUNinja = new JPanel();
		panelUNinja.setLayout(null);
		panelUNinja.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelUNinja, "name_145941053281200");

		KGradientPanel gradientPanelUNinja = new KGradientPanel();
		gradientPanelUNinja.setLayout(null);
		gradientPanelUNinja.setOpaque(false);
		gradientPanelUNinja.setkBorderRadius(20);
		gradientPanelUNinja.setkStartColor(new Color(48, 49, 52));
		gradientPanelUNinja.setkEndColor(new Color(48, 49, 52));
		gradientPanelUNinja.setBounds(10, 11, 704, 357);
		panelUNinja.add(gradientPanelUNinja);

		JLabel iconNarutoU_3 = new JLabel("");
		iconNarutoU_3.setIcon(new ImageIcon(Interface.class.getResource("/img/ninja.png")));
		iconNarutoU_3.setBounds(304, 11, 95, 90);
		gradientPanelUNinja.add(iconNarutoU_3);

		JLabel lblNewLabel_1 = new JLabel("Selecione o nome do personagem para edit\u00E1-lo:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(210, 127, 306, 17);
		gradientPanelUNinja.add(lblNewLabel_1);

		KButton btnOK_EditarNinja = new KButton();
		btnOK_EditarNinja.setBorder(null);
		btnOK_EditarNinja.setOpaque(false);
		btnOK_EditarNinja.setText("OK");
		btnOK_EditarNinja.setkHoverForeGround(Color.WHITE);
		btnOK_EditarNinja.setkPressedColor(new Color(179, 175, 168));
		btnOK_EditarNinja.setkHoverStartColor(new Color(248, 49, 11));
		btnOK_EditarNinja.setkHoverEndColor(new Color(254, 126, 30));
		btnOK_EditarNinja.setkStartColor(new Color(254, 140, 0));
		btnOK_EditarNinja.setkEndColor(new Color(248, 54, 0));
		btnOK_EditarNinja.setBounds(392, 195, 90, 30);
		gradientPanelUNinja.add(btnOK_EditarNinja);

		JComboBox<String> cbNomePersonagemEditar = new JComboBox<>();
		cbNomePersonagemEditar.setForeground(Color.WHITE);
		cbNomePersonagemEditar.setBackground(Color.GRAY);
		cbNomePersonagemEditar.setBounds(210, 199, 172, 22);
		gradientPanelUNinja.add(cbNomePersonagemEditar);

		JPanel panelUNinja_1 = new JPanel();
		panelUNinja_1.setLayout(null);
		panelUNinja_1.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelUNinja_1, "name_147230997947500");

		KGradientPanel gradientPanelUNinja_1 = new KGradientPanel();
		gradientPanelUNinja_1.setLayout(null);
		gradientPanelUNinja_1.setOpaque(false);
		gradientPanelUNinja_1.setkBorderRadius(20);
		gradientPanelUNinja_1.setkStartColor(new Color(48, 49, 52));
		gradientPanelUNinja_1.setkEndColor(new Color(48, 49, 52));
		gradientPanelUNinja_1.setBounds(10, 11, 704, 357);
		panelUNinja_1.add(gradientPanelUNinja_1);

		JPanel panelNomePersonagem_1 = new JPanel();
		panelNomePersonagem_1.setLayout(null);
		panelNomePersonagem_1.setBackground(new Color(48, 49, 52));
		panelNomePersonagem_1.setBounds(10, 106, 233, 75);
		gradientPanelUNinja_1.add(panelNomePersonagem_1);

		JLabel lblNomePersonagem_1 = new JLabel("Nome do personagem*");
		lblNomePersonagem_1.setForeground(Color.WHITE);
		lblNomePersonagem_1.setBounds(47, 11, 142, 14);
		panelNomePersonagem_1.add(lblNomePersonagem_1);
		
		JComboBox<String> cbNomeNinjaEditar = new JComboBox<String>();
		cbNomeNinjaEditar.setBorder(null);
		cbNomeNinjaEditar.setForeground(Color.WHITE);
		cbNomeNinjaEditar.setBackground(Color.GRAY);
		cbNomeNinjaEditar.setBounds(47, 36, 142, 28);
		panelNomePersonagem_1.add(cbNomeNinjaEditar);

		JPanel panelPatente_1 = new JPanel();
		panelPatente_1.setLayout(null);
		panelPatente_1.setBackground(new Color(48, 49, 52));
		panelPatente_1.setBounds(10, 192, 233, 75);
		gradientPanelUNinja_1.add(panelPatente_1);

		JLabel lblPatente_1 = new JLabel("Patente");
		lblPatente_1.setForeground(Color.WHITE);
		lblPatente_1.setBounds(47, 11, 86, 14);
		panelPatente_1.add(lblPatente_1);
		
		JComboBox<String> cbPatenteNinjaEditar = new JComboBox<String>();
		cbPatenteNinjaEditar.setForeground(Color.WHITE);
		cbPatenteNinjaEditar.setBackground(Color.GRAY);
		cbPatenteNinjaEditar.setModel(new DefaultComboBoxModel<String>(new String[] {"-", "Aluno(a) da academia", "Genin", "Chunin", "Jonin", "Kage", "Anbu", "Chubu"}));
		cbPatenteNinjaEditar.setBounds(47, 36, 141, 28);
		panelPatente_1.add(cbPatenteNinjaEditar);

		JPanel panelTitulo_1 = new JPanel();
		panelTitulo_1.setLayout(null);
		panelTitulo_1.setBackground(new Color(48, 49, 52));
		panelTitulo_1.setBounds(461, 106, 233, 75);
		gradientPanelUNinja_1.add(panelTitulo_1);

		JLabel iconTitulo_1 = new JLabel("");
		iconTitulo_1.setIcon(new ImageIcon(Interface.class.getResource("/img/ninja-blade_1.png")));
		iconTitulo_1.setBounds(47, 42, 16, 16);
		panelTitulo_1.add(iconTitulo_1);

		JSeparator separatorTitulo_1 = new JSeparator();
		separatorTitulo_1.setBounds(47, 66, 142, 2);
		panelTitulo_1.add(separatorTitulo_1);

		JLabel lblTitulo_1 = new JLabel("T\u00EDtulo");
		lblTitulo_1.setForeground(Color.WHITE);
		lblTitulo_1.setBounds(47, 11, 86, 14);
		panelTitulo_1.add(lblTitulo_1);

		tfTitulo_1 = new JTextField();
		tfTitulo_1.setOpaque(false);
		tfTitulo_1.setForeground(Color.WHITE);
		tfTitulo_1.setColumns(10);
		tfTitulo_1.setCaretColor(Color.WHITE);
		tfTitulo_1.setBorder(null);
		tfTitulo_1.setBounds(71, 42, 118, 20);
		panelTitulo_1.add(tfTitulo_1);

		JLabel lblObrigatorio_2_1 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_2_1.setForeground(Color.WHITE);
		lblObrigatorio_2_1.setBounds(47, 332, 75, 14);
		gradientPanelUNinja_1.add(lblObrigatorio_2_1);

		KButton btnEditarNinja = new KButton();
		btnEditarNinja.setBorder(null);
		btnEditarNinja.setOpaque(false);
		btnEditarNinja.setText("Editar");
		btnEditarNinja.setkStartColor(new Color(254, 140, 0));
		btnEditarNinja.setkPressedColor(new Color(179, 175, 168));
		btnEditarNinja.setkHoverStartColor(new Color(248, 49, 11));
		btnEditarNinja.setkHoverForeGround(Color.WHITE);
		btnEditarNinja.setkHoverEndColor(new Color(254, 126, 30));
		btnEditarNinja.setkEndColor(new Color(248, 54, 0));
		btnEditarNinja.setBounds(509, 215, 144, 32);
		gradientPanelUNinja_1.add(btnEditarNinja);

		JLabel iconNarutoU_1_2 = new JLabel("");
		iconNarutoU_1_2.setIcon(new ImageIcon(Interface.class.getResource("/img/ninja.png")));
		iconNarutoU_1_2.setBounds(304, 11, 95, 90);
		gradientPanelUNinja_1.add(iconNarutoU_1_2);

		JPanel panelDNinja = new JPanel();
		panelDNinja.setLayout(null);
		panelDNinja.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelDNinja, "name_164809532092700");

		KGradientPanel gradientPanelDNinja = new KGradientPanel();
		gradientPanelDNinja.setLayout(null);
		gradientPanelDNinja.setOpaque(false);
		gradientPanelDNinja.setkBorderRadius(20);
		gradientPanelDNinja.setkStartColor(new Color(48, 49, 52));
		gradientPanelDNinja.setkEndColor(new Color(48, 49, 52));
		gradientPanelDNinja.setBounds(10, 11, 704, 357);
		panelDNinja.add(gradientPanelDNinja);

		JLabel iconNarutoU_3_1 = new JLabel("");
		iconNarutoU_3_1.setIcon(new ImageIcon(Interface.class.getResource("/img/ninja.png")));
		iconNarutoU_3_1.setBounds(304, 11, 95, 90);
		gradientPanelDNinja.add(iconNarutoU_3_1);

		JLabel lblNewLabel_1_1 = new JLabel("Selecione o nome do personagem para exclu\u00ED-lo:");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(210, 127, 306, 17);
		gradientPanelDNinja.add(lblNewLabel_1_1);

		KButton btnOK_ExcluirNinja = new KButton();
		btnOK_ExcluirNinja.setBorder(null);
		btnOK_ExcluirNinja.setOpaque(false);
		btnOK_ExcluirNinja.setText("OK");
		btnOK_ExcluirNinja.setkHoverForeGround(Color.WHITE);
		btnOK_ExcluirNinja.setkPressedColor(new Color(179, 175, 168));
		btnOK_ExcluirNinja.setkHoverStartColor(new Color(248, 49, 11));
		btnOK_ExcluirNinja.setkHoverEndColor(new Color(254, 126, 30));
		btnOK_ExcluirNinja.setkStartColor(new Color(254, 140, 0));
		btnOK_ExcluirNinja.setkEndColor(new Color(248, 54, 0));
		btnOK_ExcluirNinja.setBounds(392, 195, 90, 30);
		gradientPanelDNinja.add(btnOK_ExcluirNinja);

		JComboBox<String> cbNomePersonagemExcluir = new JComboBox<>();
		cbNomePersonagemExcluir.setForeground(Color.WHITE);
		cbNomePersonagemExcluir.setBackground(Color.GRAY);
		cbNomePersonagemExcluir.setBounds(210, 199, 172, 22);
		gradientPanelDNinja.add(cbNomePersonagemExcluir);

		
		// === CMISSAO === //
		JPanel panelCMissao = new JPanel();
		panelCMissao.setBackground(new Color(32, 33, 36));
		panelCMissao.setLayout(null);
		layeredPane.add(panelCMissao, "name_1499961099706300");

		KGradientPanel gradientPanelCMissao = new KGradientPanel();
		gradientPanelCMissao.setOpaque(false);
		gradientPanelCMissao.setkBorderRadius(20);
		gradientPanelCMissao.setkStartColor(new Color(48, 49, 52));
		gradientPanelCMissao.setkEndColor(new Color(48, 49, 52));
		gradientPanelCMissao.setBounds(10, 11, 704, 357);
		panelCMissao.add(gradientPanelCMissao);
		gradientPanelCMissao.setLayout(null);

		JPanel panelNumeroTime = new JPanel();
		panelNumeroTime.setLayout(null);
		panelNumeroTime.setBackground(new Color(48, 49, 52));
		panelNumeroTime.setBounds(10, 11, 233, 75);
		gradientPanelCMissao.add(panelNumeroTime);

		JLabel lblNumeroTime = new JLabel("N\u00FAmero do time*");
		lblNumeroTime.setForeground(Color.WHITE);
		lblNumeroTime.setBounds(47, 11, 142, 14);
		panelNumeroTime.add(lblNumeroTime);
		
		JComboBox<Integer> cbNumeroTimeMissao = new JComboBox<Integer>();
		cbNumeroTimeMissao.setBounds(47, 36, 65, 28);
		panelNumeroTime.add(cbNumeroTimeMissao);

		JPanel panelObjetivo = new JPanel();
		panelObjetivo.setLayout(null);
		panelObjetivo.setBackground(new Color(48, 49, 52));
		panelObjetivo.setBounds(10, 97, 233, 75);
		gradientPanelCMissao.add(panelObjetivo);

		JLabel iconObjetivo = new JLabel("");
		iconObjetivo.setIcon(new ImageIcon(Interface.class.getResource("/img/parchment_1.png")));
		iconObjetivo.setBounds(47, 42, 16, 16);
		panelObjetivo.add(iconObjetivo);

		JSeparator separatorObjetivo = new JSeparator();
		separatorObjetivo.setBounds(47, 66, 142, 2);
		panelObjetivo.add(separatorObjetivo);

		JLabel lblObjetivo = new JLabel("Objetivo*");
		lblObjetivo.setForeground(Color.WHITE);
		lblObjetivo.setBounds(47, 11, 142, 14);
		panelObjetivo.add(lblObjetivo);

		tfObjetivo = new JTextField();
		tfObjetivo.setOpaque(false);
		tfObjetivo.setForeground(Color.WHITE);
		tfObjetivo.setColumns(10);
		tfObjetivo.setCaretColor(Color.WHITE);
		tfObjetivo.setBorder(null);
		tfObjetivo.setBounds(71, 42, 118, 20);
		panelObjetivo.add(tfObjetivo);

		JPanel panelEpInicio = new JPanel();
		panelEpInicio.setLayout(null);
		panelEpInicio.setBackground(new Color(48, 49, 52));
		panelEpInicio.setBounds(10, 183, 233, 75);
		gradientPanelCMissao.add(panelEpInicio);

		JLabel iconEpInicio = new JLabel("");
		iconEpInicio.setIcon(new ImageIcon(Interface.class.getResource("/img/parchment_1.png")));
		iconEpInicio.setBounds(47, 42, 16, 16);
		panelEpInicio.add(iconEpInicio);

		JSeparator separatorEpInicio = new JSeparator();
		separatorEpInicio.setBounds(47, 66, 142, 2);
		panelEpInicio.add(separatorEpInicio);

		JLabel lblEpInicio = new JLabel("Epis\u00F3dio de in\u00EDcio*");
		lblEpInicio.setForeground(Color.WHITE);
		lblEpInicio.setBounds(47, 11, 142, 14);
		panelEpInicio.add(lblEpInicio);

		tfEpInicio = new JTextField();
		tfEpInicio.setOpaque(false);
		tfEpInicio.setForeground(Color.WHITE);
		tfEpInicio.setColumns(10);
		tfEpInicio.setCaretColor(Color.WHITE);
		tfEpInicio.setBorder(null);
		tfEpInicio.setBounds(73, 42, 118, 20);
		panelEpInicio.add(tfEpInicio);

		JPanel panelEpFim = new JPanel();
		panelEpFim.setLayout(null);
		panelEpFim.setBackground(new Color(48, 49, 52));
		panelEpFim.setBounds(10, 269, 233, 75);
		gradientPanelCMissao.add(panelEpFim);

		JLabel iconEpFim = new JLabel("");
		iconEpFim.setIcon(new ImageIcon(Interface.class.getResource("/img/parchment_1.png")));
		iconEpFim.setBounds(47, 42, 16, 16);
		panelEpFim.add(iconEpFim);

		JSeparator separatorEpFim = new JSeparator();
		separatorEpFim.setBounds(47, 66, 142, 2);
		panelEpFim.add(separatorEpFim);

		JLabel lblEpFim = new JLabel("Epis\u00F3dio de fim*");
		lblEpFim.setForeground(Color.WHITE);
		lblEpFim.setBounds(47, 11, 142, 14);
		panelEpFim.add(lblEpFim);

		tfEpFim = new JTextField();
		tfEpFim.setOpaque(false);
		tfEpFim.setForeground(Color.WHITE);
		tfEpFim.setColumns(10);
		tfEpFim.setCaretColor(Color.WHITE);
		tfEpFim.setBorder(null);
		tfEpFim.setBounds(73, 42, 118, 20);
		panelEpFim.add(tfEpFim);

		JPanel panelRanking = new JPanel();
		panelRanking.setLayout(null);
		panelRanking.setBackground(new Color(48, 49, 52));
		panelRanking.setBounds(454, 11, 233, 75);
		gradientPanelCMissao.add(panelRanking);

		JLabel lblRanking = new JLabel("Ranking");
		lblRanking.setForeground(Color.WHITE);
		lblRanking.setBounds(47, 11, 86, 14);
		panelRanking.add(lblRanking);

		JComboBox<String> cbRanking = new JComboBox<>();
		cbRanking.setForeground(Color.BLACK);
		cbRanking.setBackground(Color.GRAY);
		cbRanking.setModel(new DefaultComboBoxModel<String>(new String[] { "-", "S", "A", "B", "C", "D" }));
		cbRanking.setBounds(47, 36, 50, 22);
		panelRanking.add(cbRanking);

		JPanel panelTipo = new JPanel();
		panelTipo.setLayout(null);
		panelTipo.setBackground(new Color(48, 49, 52));
		panelTipo.setBounds(454, 97, 233, 75);
		gradientPanelCMissao.add(panelTipo);

		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setForeground(Color.WHITE);
		lblTipo.setBounds(47, 11, 86, 14);
		panelTipo.add(lblTipo);

		JComboBox<String> cbTipo = new JComboBox<>();
		cbTipo.setBackground(Color.GRAY);
		cbTipo.setForeground(Color.BLACK);
		cbTipo.setBounds(47, 36, 142, 22);
		panelTipo.add(cbTipo);
		cbTipo.setModel(new DefaultComboBoxModel<String>(new String[] { "-", "Captura", "Escoltamento", "Espionagem",
				"Infiltra\u00E7\u00E3o", "Massacre", "Resgate" }));

		JPanel panelResultado = new JPanel();
		panelResultado.setLayout(null);
		panelResultado.setBackground(new Color(48, 49, 52));
		panelResultado.setBounds(454, 183, 233, 90);
		gradientPanelCMissao.add(panelResultado);

		JLabel lblResultado = new JLabel("Resultado");
		lblResultado.setForeground(Color.WHITE);
		lblResultado.setBounds(47, 11, 109, 14);
		panelResultado.add(lblResultado);

		JRadioButton rdbtnSucesso = new JRadioButton("Sucesso");
		rdbtnSucesso.setFocusable(false);
		rdbtnSucesso.setForeground(Color.WHITE);
		rdbtnSucesso.setBackground(new Color(48, 49, 52));
		rdbtnSucesso.setBounds(47, 32, 109, 23);
		panelResultado.add(rdbtnSucesso);

		JRadioButton rdbtnFracasso = new JRadioButton("Fracasso");
		rdbtnFracasso.setFocusable(false);
		rdbtnFracasso.setForeground(Color.WHITE);
		rdbtnFracasso.setBackground(new Color(48, 49, 52));
		rdbtnFracasso.setBounds(47, 58, 109, 23);
		panelResultado.add(rdbtnFracasso);

		KButton btnAdicionarMissao = new KButton();
		btnAdicionarMissao.setBorder(null);
		btnAdicionarMissao.setOpaque(false);
		btnAdicionarMissao.setText("Adicionar");
		btnAdicionarMissao.setkHoverForeGround(Color.WHITE);
		btnAdicionarMissao.setkPressedColor(new Color(179, 175, 168));
		btnAdicionarMissao.setkHoverStartColor(new Color(248, 49, 11));
		btnAdicionarMissao.setkHoverEndColor(new Color(254, 126, 30));
		btnAdicionarMissao.setkStartColor(new Color(254, 140, 0));
		btnAdicionarMissao.setkEndColor(new Color(248, 54, 0));
		btnAdicionarMissao.setBounds(507, 312, 144, 32);
		gradientPanelCMissao.add(btnAdicionarMissao);

		JLabel lblObrigatorio_3 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_3.setForeground(Color.WHITE);
		lblObrigatorio_3.setBounds(583, 284, 85, 14);
		gradientPanelCMissao.add(lblObrigatorio_3);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(Interface.class.getResource("/img/missao.png")));
		lblNewLabel_4.setBounds(304, 11, 95, 90);
		gradientPanelCMissao.add(lblNewLabel_4);

		
		// === RMISSAO === //
		JPanel panelRMissao = new JPanel();
		panelRMissao.setBackground(new Color(32, 33, 36));
		panelRMissao.setLayout(null);
		layeredPane.add(panelRMissao, "name_1503279009995100");

		KGradientPanel gradientPanelRMissao = new KGradientPanel();
		gradientPanelRMissao.setOpaque(false);
		gradientPanelRMissao.setkBorderRadius(20);
		gradientPanelRMissao.setkStartColor(new Color(48, 49, 52));
		gradientPanelRMissao.setkEndColor(new Color(48, 49, 52));
		gradientPanelRMissao.setBounds(10, 11, 704, 357);
		panelRMissao.add(gradientPanelRMissao);
		gradientPanelRMissao.setLayout(null);

		JScrollPane scrollPaneMissao = new JScrollPane();
		scrollPaneMissao.setBounds(10, 112, 684, 234);
		gradientPanelRMissao.add(scrollPaneMissao);

		tableMissao = new JTable();
		tableMissao.setBorder(null);
		tableMissao.setFocusable(false);
		tableMissao.setSelectionBackground(new Color(130, 130, 130));
		tableMissao.setBackground(new Color(130, 130, 130));
		tableMissao.setGridColor(new Color(151, 151, 151));
		tableMissao.setForeground(Color.WHITE);
		tableMissao.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "N\u00B0 do time", "Objetivo",
				"Ep. de in\u00EDcio", "Ep. de fim", "Ranking", "Tipo", "Resultado" }) {
			Class<?>[] columnTypes = new Class[] { Integer.class, String.class, Integer.class, Integer.class,
					String.class, String.class, String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableMissao.getColumnModel().getColumn(0).setPreferredWidth(60);
		tableMissao.getColumnModel().getColumn(1).setPreferredWidth(260);
		tableMissao.getColumnModel().getColumn(2).setPreferredWidth(65);
		tableMissao.getColumnModel().getColumn(3).setPreferredWidth(60);
		tableMissao.getColumnModel().getColumn(4).setPreferredWidth(50);
		tableMissao.getColumnModel().getColumn(6).setPreferredWidth(65);
		tableMissao.getColumnModel().getColumn(0).setHeaderRenderer(new header(scrollPane));
		tableMissao.getColumnModel().getColumn(1).setHeaderRenderer(new header(scrollPane));
		tableMissao.getColumnModel().getColumn(2).setHeaderRenderer(new header(scrollPane));
		tableMissao.getColumnModel().getColumn(3).setHeaderRenderer(new header(scrollPane));
		tableMissao.getColumnModel().getColumn(4).setHeaderRenderer(new header(scrollPane));
		tableMissao.getColumnModel().getColumn(5).setHeaderRenderer(new header(scrollPane));
		tableMissao.getColumnModel().getColumn(6).setHeaderRenderer(new header(scrollPane));
		tableMissao.setDefaultRenderer(Object.class, new cell());
		
		scrollPaneMissao.setViewportView(tableMissao);
		
		KButton btnVerEstatisticas = new KButton();
		btnVerEstatisticas.setText("Ver estat\u00EDsticas");
		btnVerEstatisticas.setBorder(null);
		btnVerEstatisticas.setOpaque(false);
		btnVerEstatisticas.setkHoverForeGround(Color.WHITE);
		btnVerEstatisticas.setkPressedColor(new Color(179, 175, 168));
		btnVerEstatisticas.setkHoverStartColor(new Color(248, 49, 11));
		btnVerEstatisticas.setkHoverEndColor(new Color(254, 126, 30));
		btnVerEstatisticas.setkStartColor(new Color(254, 140, 0));
		btnVerEstatisticas.setkEndColor(new Color(248, 54, 0));
		btnVerEstatisticas.setBounds(495, 40, 155, 32);
		gradientPanelRPersonagem.add(btnVerEstatisticas);
		
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(Interface.class.getResource("/img/missao.png")));
		lblNewLabel_5.setBounds(304, 11, 95, 90);
		gradientPanelRMissao.add(lblNewLabel_5);
		
		JComboBox<String> cbSelecionarTipo = new JComboBox<String>();
		cbSelecionarTipo.setForeground(Color.WHITE);
		cbSelecionarTipo.setBackground(Color.GRAY);
		cbSelecionarTipo.setModel(new DefaultComboBoxModel<String>(new String[] {"-", "Captura", "Escoltamento", "Espionagem", "Infiltra\u00E7\u00E3o", "Massacre", "Resgate"}));
		cbSelecionarTipo.setBounds(444, 36, 128, 22);
		gradientPanelRMissao.add(cbSelecionarTipo);
		
		KButton btnSelecionarTipo = new KButton();
		btnSelecionarTipo.setText("OK");
		btnSelecionarTipo.setBorder(null);
		btnSelecionarTipo.setOpaque(false);
		btnSelecionarTipo.setkHoverForeGround(Color.WHITE);
		btnSelecionarTipo.setkPressedColor(new Color(179, 175, 168));
		btnSelecionarTipo.setkHoverStartColor(new Color(248, 49, 11));
		btnSelecionarTipo.setkHoverEndColor(new Color(254, 126, 30));
		btnSelecionarTipo.setkStartColor(new Color(254, 140, 0));
		btnSelecionarTipo.setkEndColor(new Color(248, 54, 0));
		btnSelecionarTipo.setBounds(582, 36, 79, 22);
		gradientPanelRMissao.add(btnSelecionarTipo);
		
		tfSelecionarEpInicio = new JTextField();
		tfSelecionarEpInicio.setBounds(444, 80, 53, 20);
		tfSelecionarEpInicio.setOpaque(false);
		tfSelecionarEpInicio.setForeground(Color.WHITE);
		tfSelecionarEpInicio.setColumns(10);
		tfSelecionarEpInicio.setCaretColor(Color.WHITE);
		tfSelecionarEpInicio.setBorder(null);
		gradientPanelRMissao.add(tfSelecionarEpInicio);
		tfSelecionarEpInicio.setColumns(10);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(444, 99, 53, 2);
		gradientPanelRMissao.add(separator_3);
		
		JLabel lblNewLabel_11 = new JLabel("a");
		lblNewLabel_11.setForeground(Color.WHITE);
		lblNewLabel_11.setBounds(504, 83, 14, 14);
		gradientPanelRMissao.add(lblNewLabel_11);
		
		tfSelecionarEpFim = new JTextField();
		tfSelecionarEpFim.setOpaque(false);
		tfSelecionarEpFim.setForeground(Color.WHITE);
		tfSelecionarEpFim.setColumns(10);
		tfSelecionarEpFim.setCaretColor(Color.WHITE);
		tfSelecionarEpFim.setBorder(null);
		tfSelecionarEpFim.setBounds(519, 80, 53, 20);
		gradientPanelRMissao.add(tfSelecionarEpFim);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(519, 98, 53, 2);
		gradientPanelRMissao.add(separator_4);
		
		KButton btnSelecionarEpisodios = new KButton();
		btnSelecionarEpisodios.setText("OK");
		btnSelecionarEpisodios.setBorder(null);
		btnSelecionarEpisodios.setOpaque(false);
		btnSelecionarEpisodios.setkHoverForeGround(Color.WHITE);
		btnSelecionarEpisodios.setkPressedColor(new Color(179, 175, 168));
		btnSelecionarEpisodios.setkHoverStartColor(new Color(248, 49, 11));
		btnSelecionarEpisodios.setkHoverEndColor(new Color(254, 126, 30));
		btnSelecionarEpisodios.setkStartColor(new Color(254, 140, 0));
		btnSelecionarEpisodios.setkEndColor(new Color(248, 54, 0));
		btnSelecionarEpisodios.setBounds(582, 79, 79, 22);
		gradientPanelRMissao.add(btnSelecionarEpisodios);
		
		KButton btnRedefinirMissao = new KButton();
		btnRedefinirMissao.setIconTextGap(9);
		btnRedefinirMissao.setIcon(new ImageIcon(Interface.class.getResource("/img/reload.png")));
		btnRedefinirMissao.setBorder(null);
		btnRedefinirMissao.setOpaque(false);
		btnRedefinirMissao.setkHoverForeGround(Color.WHITE);
		btnRedefinirMissao.setkPressedColor(new Color(179, 175, 168));
		btnRedefinirMissao.setkHoverStartColor(new Color(248, 49, 11));
		btnRedefinirMissao.setkHoverEndColor(new Color(254, 126, 30));
		btnRedefinirMissao.setkStartColor(new Color(254, 140, 0));
		btnRedefinirMissao.setkEndColor(new Color(248, 54, 0));
		btnRedefinirMissao.setBounds(51, 44, 34, 32);
		gradientPanelRMissao.add(btnRedefinirMissao);
		
		JLabel lblNewLabel_14 = new JLabel("Ordenar por epis\u00F3dios:");
		lblNewLabel_14.setForeground(Color.WHITE);
		lblNewLabel_14.setBounds(444, 69, 151, 14);
		gradientPanelRMissao.add(lblNewLabel_14);
		
		JLabel lblNewLabel_15 = new JLabel("Ordenar por tipo:");
		lblNewLabel_15.setForeground(Color.WHITE);
		lblNewLabel_15.setBounds(444, 11, 112, 14);
		gradientPanelRMissao.add(lblNewLabel_15);
		
		JLabel lblNewLabel_16 = new JLabel("Redefinir:");
		lblNewLabel_16.setForeground(Color.WHITE);
		lblNewLabel_16.setBounds(51, 24, 66, 14);
		gradientPanelRMissao.add(lblNewLabel_16);

		
		// === UMISSAO === //
		JPanel panelUMissao = new JPanel();
		panelUMissao.setLayout(null);
		panelUMissao.setOpaque(false);
		panelUMissao.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelUMissao, "name_13320227798900");

		KGradientPanel gradientPanelUMissao = new KGradientPanel();
		gradientPanelUMissao.setLayout(null);
		gradientPanelUMissao.setOpaque(false);
		gradientPanelUMissao.setkStartColor(new Color(48, 49, 52));
		gradientPanelUMissao.setkEndColor(new Color(48, 49, 52));
		gradientPanelUMissao.setkBorderRadius(20);
		gradientPanelUMissao.setBounds(10, 11, 704, 357);
		panelUMissao.add(gradientPanelUMissao);

		JPanel panelNumeroTimeEditar = new JPanel();
		panelNumeroTimeEditar.setLayout(null);
		panelNumeroTimeEditar.setBackground(new Color(48, 49, 52));
		panelNumeroTimeEditar.setBounds(10, 106, 233, 75);
		gradientPanelUMissao.add(panelNumeroTimeEditar);

		JLabel lblNumeroTimeEditar = new JLabel("N\u00FAmero do time*");
		lblNumeroTimeEditar.setForeground(Color.WHITE);
		lblNumeroTimeEditar.setBounds(47, 11, 142, 14);
		panelNumeroTimeEditar.add(lblNumeroTimeEditar);
		
		JComboBox<Integer> cbNumeroTimeMissaoEditar = new JComboBox<Integer>();
		cbNumeroTimeMissaoEditar.setBounds(47, 36, 71, 28);
		panelNumeroTimeEditar.add(cbNumeroTimeMissaoEditar);

		JPanel panelObjetivoEditar = new JPanel();
		panelObjetivoEditar.setLayout(null);
		panelObjetivoEditar.setBackground(new Color(48, 49, 52));
		panelObjetivoEditar.setBounds(10, 192, 233, 75);
		gradientPanelUMissao.add(panelObjetivoEditar);

		JLabel iconObjetivoEditar = new JLabel("");
		iconObjetivoEditar.setIcon(new ImageIcon(Interface.class.getResource("/img/parchment_1.png")));
		iconObjetivoEditar.setBounds(47, 42, 16, 16);
		panelObjetivoEditar.add(iconObjetivoEditar);

		JSeparator separatorObjetivoEditar = new JSeparator();
		separatorObjetivoEditar.setBounds(47, 66, 142, 2);
		panelObjetivoEditar.add(separatorObjetivoEditar);

		JLabel lblObjetivoEditar = new JLabel("Objetivo*");
		lblObjetivoEditar.setForeground(Color.WHITE);
		lblObjetivoEditar.setBounds(47, 11, 142, 14);
		panelObjetivoEditar.add(lblObjetivoEditar);

		tfObjetivoEditar = new JTextField();
		tfObjetivoEditar.setOpaque(false);
		tfObjetivoEditar.setForeground(Color.WHITE);
		tfObjetivoEditar.setColumns(10);
		tfObjetivoEditar.setCaretColor(Color.WHITE);
		tfObjetivoEditar.setBorder(null);
		tfObjetivoEditar.setBounds(71, 38, 118, 20);
		panelObjetivoEditar.add(tfObjetivoEditar);

		JPanel panelEpInicioEditar = new JPanel();
		panelEpInicioEditar.setLayout(null);
		panelEpInicioEditar.setBackground(new Color(48, 49, 52));
		panelEpInicioEditar.setBounds(461, 106, 233, 75);
		gradientPanelUMissao.add(panelEpInicioEditar);

		JLabel iconEpInicioEditar = new JLabel("");
		iconEpInicioEditar.setIcon(new ImageIcon(Interface.class.getResource("/img/parchment_1.png")));
		iconEpInicioEditar.setBounds(47, 42, 16, 16);
		panelEpInicioEditar.add(iconEpInicioEditar);

		JSeparator separatorEpInicioEditar = new JSeparator();
		separatorEpInicioEditar.setBounds(47, 66, 142, 2);
		panelEpInicioEditar.add(separatorEpInicioEditar);

		JLabel lblEpInicioEditar = new JLabel("Epis\u00F3dio de in\u00EDcio*");
		lblEpInicioEditar.setForeground(Color.WHITE);
		lblEpInicioEditar.setBounds(47, 11, 142, 14);
		panelEpInicioEditar.add(lblEpInicioEditar);

		tfEpInicioEditar = new JTextField();
		tfEpInicioEditar.setOpaque(false);
		tfEpInicioEditar.setForeground(Color.WHITE);
		tfEpInicioEditar.setColumns(10);
		tfEpInicioEditar.setCaretColor(Color.WHITE);
		tfEpInicioEditar.setBorder(null);
		tfEpInicioEditar.setBounds(73, 38, 118, 20);
		panelEpInicioEditar.add(tfEpInicioEditar);

		JPanel panelEpFimEditar = new JPanel();
		panelEpFimEditar.setLayout(null);
		panelEpFimEditar.setBackground(new Color(48, 49, 52));
		panelEpFimEditar.setBounds(461, 192, 233, 75);
		gradientPanelUMissao.add(panelEpFimEditar);

		JLabel iconEpFimEditar = new JLabel("");
		iconEpFimEditar.setIcon(new ImageIcon(Interface.class.getResource("/img/parchment_1.png")));
		iconEpFimEditar.setBounds(47, 42, 16, 16);
		panelEpFimEditar.add(iconEpFimEditar);

		JSeparator separatorEpFimEditar = new JSeparator();
		separatorEpFimEditar.setBounds(47, 66, 142, 2);
		panelEpFimEditar.add(separatorEpFimEditar);

		JLabel lblEpFimEditar = new JLabel("Epis\u00F3dio de fim*");
		lblEpFimEditar.setForeground(Color.WHITE);
		lblEpFimEditar.setBounds(47, 11, 142, 14);
		panelEpFimEditar.add(lblEpFimEditar);

		tfEpFimEditar = new JTextField();
		tfEpFimEditar.setOpaque(false);
		tfEpFimEditar.setForeground(Color.WHITE);
		tfEpFimEditar.setColumns(10);
		tfEpFimEditar.setCaretColor(Color.WHITE);
		tfEpFimEditar.setBorder(null);
		tfEpFimEditar.setBounds(73, 38, 118, 20);
		panelEpFimEditar.add(tfEpFimEditar);

		JLabel iconNarutoU_1_1 = new JLabel("");
		iconNarutoU_1_1.setIcon(new ImageIcon(Interface.class.getResource("/img/missao.png")));
		iconNarutoU_1_1.setBounds(304, 11, 95, 90);
		gradientPanelUMissao.add(iconNarutoU_1_1);

		KButton btnOK_EditarMissao = new KButton();
		btnOK_EditarMissao.setBorder(null);
		btnOK_EditarMissao.setOpaque(false);
		btnOK_EditarMissao.setText("OK");
		btnOK_EditarMissao.setkHoverForeGround(Color.WHITE);
		btnOK_EditarMissao.setkPressedColor(new Color(179, 175, 168));
		btnOK_EditarMissao.setkHoverStartColor(new Color(248, 49, 11));
		btnOK_EditarMissao.setkHoverEndColor(new Color(254, 126, 30));
		btnOK_EditarMissao.setkStartColor(new Color(254, 140, 0));
		btnOK_EditarMissao.setkEndColor(new Color(248, 54, 0));
		btnOK_EditarMissao.setBounds(509, 295, 144, 32);
		gradientPanelUMissao.add(btnOK_EditarMissao);
		
		JLabel lblNewLabel_19 = new JLabel("*Obrigat\u00F3rio");
		lblNewLabel_19.setForeground(Color.WHITE);
		lblNewLabel_19.setBounds(47, 332, 95, 14);
		gradientPanelUMissao.add(lblNewLabel_19);

		
		// === UMISSAO_1 === //
		JPanel panelUMissao_1 = new JPanel();
		panelUMissao_1.setLayout(null);
		panelUMissao_1.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelUMissao_1, "name_15558482967900");

		KGradientPanel gradientPanelCMissao_1 = new KGradientPanel();
		gradientPanelCMissao_1.setLayout(null);
		gradientPanelCMissao_1.setOpaque(false);
		gradientPanelCMissao_1.setkStartColor(new Color(48, 49, 52));
		gradientPanelCMissao_1.setkEndColor(new Color(48, 49, 52));
		gradientPanelCMissao_1.setkBorderRadius(20);
		gradientPanelCMissao_1.setBounds(10, 11, 704, 357);
		panelUMissao_1.add(gradientPanelCMissao_1);

		JPanel panelNumeroTime_1 = new JPanel();
		panelNumeroTime_1.setLayout(null);
		panelNumeroTime_1.setBackground(new Color(48, 49, 52));
		panelNumeroTime_1.setBounds(10, 11, 233, 75);
		gradientPanelCMissao_1.add(panelNumeroTime_1);

		JLabel lblNumeroTime_1 = new JLabel("N\u00FAmero do time*");
		lblNumeroTime_1.setForeground(Color.WHITE);
		lblNumeroTime_1.setBounds(47, 11, 142, 14);
		panelNumeroTime_1.add(lblNumeroTime_1);
		
		JComboBox<Integer> cbNumeroTimeMissaoEditar_1 = new JComboBox<Integer>();
		cbNumeroTimeMissaoEditar_1.setBounds(47, 36, 64, 28);
		panelNumeroTime_1.add(cbNumeroTimeMissaoEditar_1);

		JPanel panelObjetivo_1 = new JPanel();
		panelObjetivo_1.setLayout(null);
		panelObjetivo_1.setBackground(new Color(48, 49, 52));
		panelObjetivo_1.setBounds(10, 97, 233, 75);
		gradientPanelCMissao_1.add(panelObjetivo_1);

		JLabel iconObjetivo_1 = new JLabel("");
		iconObjetivo_1.setIcon(new ImageIcon(Interface.class.getResource("/img/parchment_1.png")));
		iconObjetivo_1.setBounds(47, 42, 16, 16);
		panelObjetivo_1.add(iconObjetivo_1);

		JSeparator separatorObjetivo_1 = new JSeparator();
		separatorObjetivo_1.setBounds(47, 66, 142, 2);
		panelObjetivo_1.add(separatorObjetivo_1);

		JLabel lblObjetivo_1 = new JLabel("Objetivo*");
		lblObjetivo_1.setForeground(Color.WHITE);
		lblObjetivo_1.setBounds(47, 11, 142, 14);
		panelObjetivo_1.add(lblObjetivo_1);

		tfObjetivo_1 = new JTextField();
		tfObjetivo_1.setOpaque(false);
		tfObjetivo_1.setForeground(Color.WHITE);
		tfObjetivo_1.setColumns(10);
		tfObjetivo_1.setCaretColor(Color.WHITE);
		tfObjetivo_1.setBorder(null);
		tfObjetivo_1.setBounds(71, 38, 118, 20);
		panelObjetivo_1.add(tfObjetivo_1);

		JPanel panelEpInicio_1 = new JPanel();
		panelEpInicio_1.setLayout(null);
		panelEpInicio_1.setBackground(new Color(48, 49, 52));
		panelEpInicio_1.setBounds(10, 183, 233, 75);
		gradientPanelCMissao_1.add(panelEpInicio_1);

		JLabel iconEpInicio_1 = new JLabel("");
		iconEpInicio_1.setIcon(new ImageIcon(Interface.class.getResource("/img/parchment_1.png")));
		iconEpInicio_1.setBounds(47, 42, 16, 16);
		panelEpInicio_1.add(iconEpInicio_1);

		JSeparator separatorEpInicio_1 = new JSeparator();
		separatorEpInicio_1.setBounds(47, 66, 142, 2);
		panelEpInicio_1.add(separatorEpInicio_1);

		JLabel lblEpInicio_1 = new JLabel("Epis\u00F3dio de in\u00EDcio*");
		lblEpInicio_1.setForeground(Color.WHITE);
		lblEpInicio_1.setBounds(47, 11, 142, 14);
		panelEpInicio_1.add(lblEpInicio_1);

		tfEpInicio_1 = new JTextField();
		tfEpInicio_1.setOpaque(false);
		tfEpInicio_1.setForeground(Color.WHITE);
		tfEpInicio_1.setColumns(10);
		tfEpInicio_1.setCaretColor(Color.WHITE);
		tfEpInicio_1.setBorder(null);
		tfEpInicio_1.setBounds(73, 38, 118, 20);
		panelEpInicio_1.add(tfEpInicio_1);

		JPanel panelEpFim_1 = new JPanel();
		panelEpFim_1.setLayout(null);
		panelEpFim_1.setBackground(new Color(48, 49, 52));
		panelEpFim_1.setBounds(10, 269, 233, 75);
		gradientPanelCMissao_1.add(panelEpFim_1);

		JLabel iconEpFim_1 = new JLabel("");
		iconEpFim_1.setIcon(new ImageIcon(Interface.class.getResource("/img/parchment_1.png")));
		iconEpFim_1.setBounds(47, 42, 16, 16);
		panelEpFim_1.add(iconEpFim_1);

		JSeparator separatorEpFim_1 = new JSeparator();
		separatorEpFim_1.setBounds(47, 66, 142, 2);
		panelEpFim_1.add(separatorEpFim_1);

		JLabel lblEpFim_1 = new JLabel("Epis\u00F3dio de fim*");
		lblEpFim_1.setForeground(Color.WHITE);
		lblEpFim_1.setBounds(47, 11, 142, 14);
		panelEpFim_1.add(lblEpFim_1);

		tfEpFim_1 = new JTextField();
		tfEpFim_1.setOpaque(false);
		tfEpFim_1.setForeground(Color.WHITE);
		tfEpFim_1.setColumns(10);
		tfEpFim_1.setCaretColor(Color.WHITE);
		tfEpFim_1.setBorder(null);
		tfEpFim_1.setBounds(73, 38, 118, 20);
		panelEpFim_1.add(tfEpFim_1);

		JPanel panelRanking_1 = new JPanel();
		panelRanking_1.setLayout(null);
		panelRanking_1.setBackground(new Color(48, 49, 52));
		panelRanking_1.setBounds(454, 11, 233, 75);
		gradientPanelCMissao_1.add(panelRanking_1);

		JLabel lblRanking_1 = new JLabel("Ranking");
		lblRanking_1.setForeground(Color.WHITE);
		lblRanking_1.setBounds(47, 11, 86, 14);
		panelRanking_1.add(lblRanking_1);

		JComboBox<String> cbRanking_1 = new JComboBox<String>();
		cbRanking_1.setForeground(Color.BLACK);
		cbRanking_1.setBackground(Color.GRAY);
		cbRanking_1.setModel(new DefaultComboBoxModel<String>(new String[] { "-", "S", "A", "B", "C", "D" }));
		cbRanking_1.setBounds(47, 36, 50, 22);
		panelRanking_1.add(cbRanking_1);

		JPanel panelTipo_1 = new JPanel();
		panelTipo_1.setLayout(null);
		panelTipo_1.setBackground(new Color(48, 49, 52));
		panelTipo_1.setBounds(454, 97, 233, 75);
		gradientPanelCMissao_1.add(panelTipo_1);

		JLabel lblTipo_1 = new JLabel("Tipo");
		lblTipo_1.setForeground(Color.WHITE);
		lblTipo_1.setBounds(47, 11, 86, 14);
		panelTipo_1.add(lblTipo_1);

		JComboBox<String> cbTipo_1 = new JComboBox<String>();
		cbTipo_1.setForeground(Color.BLACK);
		cbTipo_1.setBackground(Color.GRAY);
		cbTipo_1.setModel(new DefaultComboBoxModel<String>(new String[] { "-", "Captura", "Escoltamento", "Espionagem",
				"Infiltra\u00E7\u00E3o", "Massacre", "Resgate" }));
		cbTipo_1.setBounds(47, 36, 142, 22);
		panelTipo_1.add(cbTipo_1);

		JPanel panelResultado_1 = new JPanel();
		panelResultado_1.setLayout(null);
		panelResultado_1.setBackground(new Color(48, 49, 52));
		panelResultado_1.setBounds(454, 183, 233, 90);
		gradientPanelCMissao_1.add(panelResultado_1);

		JLabel lblResultado_1 = new JLabel("Resultado");
		lblResultado_1.setForeground(Color.WHITE);
		lblResultado_1.setBounds(47, 11, 109, 14);
		panelResultado_1.add(lblResultado_1);

		JRadioButton rdbtnSucesso_1 = new JRadioButton("Sucesso");
		rdbtnSucesso_1.setFocusable(false);
		rdbtnSucesso_1.setForeground(Color.WHITE);
		rdbtnSucesso_1.setBackground(new Color(48, 49, 52));
		rdbtnSucesso_1.setBounds(47, 32, 109, 23);
		panelResultado_1.add(rdbtnSucesso_1);

		JRadioButton rdbtnFracasso_1 = new JRadioButton("Fracasso");
		rdbtnFracasso_1.setFocusable(false);
		rdbtnFracasso_1.setForeground(Color.WHITE);
		rdbtnFracasso_1.setBackground(new Color(48, 49, 52));
		rdbtnFracasso_1.setBounds(47, 58, 109, 23);
		panelResultado_1.add(rdbtnFracasso_1);

		KButton btnEditarMissao = new KButton();
		btnEditarMissao.setBorder(null);
		btnEditarMissao.setOpaque(false);
		btnEditarMissao.setText("Editar");
		btnEditarMissao.setkStartColor(new Color(254, 140, 0));
		btnEditarMissao.setkPressedColor(new Color(179, 175, 168));
		btnEditarMissao.setkHoverStartColor(new Color(248, 49, 11));
		btnEditarMissao.setkHoverForeGround(Color.WHITE);
		btnEditarMissao.setkHoverEndColor(new Color(254, 126, 30));
		btnEditarMissao.setkEndColor(new Color(248, 54, 0));
		btnEditarMissao.setBounds(507, 312, 144, 32);
		gradientPanelCMissao_1.add(btnEditarMissao);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon(Interface.class.getResource("/img/missao.png")));
		lblNewLabel_6.setBounds(304, 11, 95, 90);
		gradientPanelCMissao_1.add(lblNewLabel_6);
		
		JLabel lblObrigatorio_3_1_1_1 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_3_1_1_1.setForeground(Color.WHITE);
		lblObrigatorio_3_1_1_1.setBounds(584, 284, 85, 14);
		gradientPanelCMissao_1.add(lblObrigatorio_3_1_1_1);

		
		// === DMISSAO === //
		JPanel panelDMissao = new JPanel();
		panelDMissao.setLayout(null);
		panelDMissao.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelDMissao, "name_19757236406200");

		KGradientPanel gradientPanelDMissao = new KGradientPanel();
		gradientPanelDMissao.setLayout(null);
		gradientPanelDMissao.setOpaque(false);
		gradientPanelDMissao.setkStartColor(new Color(48, 49, 52));
		gradientPanelDMissao.setkEndColor(new Color(48, 49, 52));
		gradientPanelDMissao.setkBorderRadius(20);
		gradientPanelDMissao.setBounds(10, 11, 704, 357);
		panelDMissao.add(gradientPanelDMissao);

		JPanel panelNumeroTimeExcluir = new JPanel();
		panelNumeroTimeExcluir.setLayout(null);
		panelNumeroTimeExcluir.setBackground(new Color(48, 49, 52));
		panelNumeroTimeExcluir.setBounds(10, 106, 233, 75);
		gradientPanelDMissao.add(panelNumeroTimeExcluir);

		JLabel lblNumeroTimeExcluir = new JLabel("N\u00FAmero do time");
		lblNumeroTimeExcluir.setForeground(Color.WHITE);
		lblNumeroTimeExcluir.setBounds(47, 11, 142, 14);
		panelNumeroTimeExcluir.add(lblNumeroTimeExcluir);
		
		JComboBox<Integer> cbNumeroTimeMissaoExcluir = new JComboBox<Integer>();
		cbNumeroTimeMissaoExcluir.setBounds(47, 36, 72, 28);
		panelNumeroTimeExcluir.add(cbNumeroTimeMissaoExcluir);

		JPanel panelObjetivoExcluir = new JPanel();
		panelObjetivoExcluir.setLayout(null);
		panelObjetivoExcluir.setBackground(new Color(48, 49, 52));
		panelObjetivoExcluir.setBounds(10, 192, 233, 75);
		gradientPanelDMissao.add(panelObjetivoExcluir);

		JLabel iconObjetivoExcluir = new JLabel("");
		iconObjetivoExcluir.setIcon(new ImageIcon(Interface.class.getResource("/img/parchment_1.png")));
		iconObjetivoExcluir.setBounds(47, 42, 16, 16);
		panelObjetivoExcluir.add(iconObjetivoExcluir);

		JSeparator separatorObjetivoExcluir = new JSeparator();
		separatorObjetivoExcluir.setBounds(47, 66, 142, 2);
		panelObjetivoExcluir.add(separatorObjetivoExcluir);

		JLabel lblObjetivoExcluir = new JLabel("Objetivo");
		lblObjetivoExcluir.setForeground(Color.WHITE);
		lblObjetivoExcluir.setBounds(47, 11, 142, 14);
		panelObjetivoExcluir.add(lblObjetivoExcluir);

		tfObjetivoExcluir = new JTextField();
		tfObjetivoExcluir.setOpaque(false);
		tfObjetivoExcluir.setForeground(Color.WHITE);
		tfObjetivoExcluir.setColumns(10);
		tfObjetivoExcluir.setCaretColor(Color.WHITE);
		tfObjetivoExcluir.setBorder(null);
		tfObjetivoExcluir.setBounds(71, 38, 118, 20);
		panelObjetivoExcluir.add(tfObjetivoExcluir);

		JPanel panelEpInicioExcluir = new JPanel();
		panelEpInicioExcluir.setLayout(null);
		panelEpInicioExcluir.setBackground(new Color(48, 49, 52));
		panelEpInicioExcluir.setBounds(461, 106, 233, 75);
		gradientPanelDMissao.add(panelEpInicioExcluir);

		JLabel iconEpInicioExcluir = new JLabel("");
		iconEpInicioExcluir.setIcon(new ImageIcon(Interface.class.getResource("/img/parchment_1.png")));
		iconEpInicioExcluir.setBounds(47, 42, 16, 16);
		panelEpInicioExcluir.add(iconEpInicioExcluir);

		JSeparator separatorEpInicioExcluir = new JSeparator();
		separatorEpInicioExcluir.setBounds(47, 66, 142, 2);
		panelEpInicioExcluir.add(separatorEpInicioExcluir);

		JLabel lblEpInicioExcluir = new JLabel("Epis\u00F3dio de in\u00EDcio");
		lblEpInicioExcluir.setForeground(Color.WHITE);
		lblEpInicioExcluir.setBounds(47, 11, 142, 14);
		panelEpInicioExcluir.add(lblEpInicioExcluir);

		tfEpInicioExcluir = new JTextField();
		tfEpInicioExcluir.setOpaque(false);
		tfEpInicioExcluir.setForeground(Color.WHITE);
		tfEpInicioExcluir.setColumns(10);
		tfEpInicioExcluir.setCaretColor(Color.WHITE);
		tfEpInicioExcluir.setBorder(null);
		tfEpInicioExcluir.setBounds(73, 38, 118, 20);
		panelEpInicioExcluir.add(tfEpInicioExcluir);

		JPanel panelEpFimExcluir = new JPanel();
		panelEpFimExcluir.setLayout(null);
		panelEpFimExcluir.setBackground(new Color(48, 49, 52));
		panelEpFimExcluir.setBounds(461, 192, 233, 75);
		gradientPanelDMissao.add(panelEpFimExcluir);

		JLabel iconEpFimExcluir = new JLabel("");
		iconEpFimExcluir.setIcon(new ImageIcon(Interface.class.getResource("/img/parchment_1.png")));
		iconEpFimExcluir.setBounds(47, 42, 16, 16);
		panelEpFimExcluir.add(iconEpFimExcluir);

		JSeparator separatorEpFimExcluir = new JSeparator();
		separatorEpFimExcluir.setBounds(47, 66, 142, 2);
		panelEpFimExcluir.add(separatorEpFimExcluir);

		JLabel lblEpFimExcluir = new JLabel("Epis\u00F3dio de fim");
		lblEpFimExcluir.setForeground(Color.WHITE);
		lblEpFimExcluir.setBounds(47, 11, 142, 14);
		panelEpFimExcluir.add(lblEpFimExcluir);

		tfEpFimExcluir = new JTextField();
		tfEpFimExcluir.setOpaque(false);
		tfEpFimExcluir.setForeground(Color.WHITE);
		tfEpFimExcluir.setColumns(10);
		tfEpFimExcluir.setCaretColor(Color.WHITE);
		tfEpFimExcluir.setBorder(null);
		tfEpFimExcluir.setBounds(73, 38, 118, 20);
		panelEpFimExcluir.add(tfEpFimExcluir);

		JLabel iconNarutoU_1_1_1 = new JLabel("");
		iconNarutoU_1_1_1.setIcon(new ImageIcon(Interface.class.getResource("/img/missao.png")));
		iconNarutoU_1_1_1.setBounds(304, 11, 95, 90);
		gradientPanelDMissao.add(iconNarutoU_1_1_1);

		KButton btnOK_ExcluirMissao = new KButton();
		btnOK_ExcluirMissao.setBorder(null);
		btnOK_ExcluirMissao.setOpaque(false);
		btnOK_ExcluirMissao.setText("OK");
		btnOK_ExcluirMissao.setkHoverForeGround(Color.WHITE);
		btnOK_ExcluirMissao.setkPressedColor(new Color(179, 175, 168));
		btnOK_ExcluirMissao.setkHoverStartColor(new Color(248, 49, 11));
		btnOK_ExcluirMissao.setkHoverEndColor(new Color(254, 126, 30));
		btnOK_ExcluirMissao.setkStartColor(new Color(254, 140, 0));
		btnOK_ExcluirMissao.setkEndColor(new Color(248, 54, 0));
		btnOK_ExcluirMissao.setBounds(509, 295, 144, 32);
		gradientPanelDMissao.add(btnOK_ExcluirMissao);
		
		JLabel lblNewLabel_20 = new JLabel("*Obrigat\u00F3rio");
		lblNewLabel_20.setForeground(Color.WHITE);
		lblNewLabel_20.setBounds(47, 332, 95, 14);
		gradientPanelDMissao.add(lblNewLabel_20);

		JPanel panelCTime = new JPanel();
		panelCTime.setLayout(null);
		panelCTime.setOpaque(false);
		panelCTime.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelCTime, "name_248476447844300");

		KGradientPanel gradientPanelCTime = new KGradientPanel();
		gradientPanelCTime.setOpaque(false);
		gradientPanelCTime.setkBorderRadius(20);
		gradientPanelCTime.setkStartColor(new Color(48, 49, 52));
		gradientPanelCTime.setkEndColor(new Color(48, 49, 52));
		gradientPanelCTime.setLayout(null);
		gradientPanelCTime.setBounds(10, 11, 704, 357);
		panelCTime.add(gradientPanelCTime);

		JPanel panelNumero = new JPanel();
		panelNumero.setLayout(null);
		panelNumero.setBackground(new Color(48, 49, 52));
		panelNumero.setBounds(10, 106, 233, 75);
		gradientPanelCTime.add(panelNumero);

		JLabel iconNumero = new JLabel("");
		iconNumero.setIcon(new ImageIcon(Interface.class.getResource("/img/handshake.png")));
		iconNumero.setBounds(47, 42, 16, 16);
		panelNumero.add(iconNumero);

		JSeparator separatorNumero = new JSeparator();
		separatorNumero.setBounds(47, 66, 142, 2);
		panelNumero.add(separatorNumero);

		JLabel lblNumero = new JLabel("N\u00FAmero*");
		lblNumero.setForeground(Color.WHITE);
		lblNumero.setBounds(47, 11, 142, 14);
		panelNumero.add(lblNumero);

		tfNumero = new JTextField();
		tfNumero.setOpaque(false);
		tfNumero.setForeground(Color.WHITE);
		tfNumero.setColumns(10);
		tfNumero.setCaretColor(Color.WHITE);
		tfNumero.setBorder(null);
		tfNumero.setBounds(73, 38, 118, 20);
		panelNumero.add(tfNumero);

		JPanel panelQuantMembros = new JPanel();
		panelQuantMembros.setLayout(null);
		panelQuantMembros.setBackground(new Color(48, 49, 52));
		panelQuantMembros.setBounds(10, 192, 233, 75);
		gradientPanelCTime.add(panelQuantMembros);

		JLabel iconQuantMembros = new JLabel("");
		iconQuantMembros.setIcon(new ImageIcon(Interface.class.getResource("/img/handshake.png")));
		iconQuantMembros.setBounds(47, 42, 16, 16);
		panelQuantMembros.add(iconQuantMembros);

		JSeparator separatorQuantMembros = new JSeparator();
		separatorQuantMembros.setBounds(47, 66, 142, 2);
		panelQuantMembros.add(separatorQuantMembros);

		JLabel lblQuantMembros = new JLabel("Quantidade de membros");
		lblQuantMembros.setForeground(Color.WHITE);
		lblQuantMembros.setBounds(47, 11, 142, 14);
		panelQuantMembros.add(lblQuantMembros);

		tfQuantMembros = new JTextField();
		tfQuantMembros.setOpaque(false);
		tfQuantMembros.setForeground(Color.WHITE);
		tfQuantMembros.setColumns(10);
		tfQuantMembros.setCaretColor(Color.WHITE);
		tfQuantMembros.setBorder(null);
		tfQuantMembros.setBounds(71, 38, 118, 20);
		panelQuantMembros.add(tfQuantMembros);

		JPanel panelNomeTime = new JPanel();
		panelNomeTime.setLayout(null);
		panelNomeTime.setBackground(new Color(48, 49, 52));
		panelNomeTime.setBounds(461, 106, 233, 75);
		gradientPanelCTime.add(panelNomeTime);

		JLabel iconNomeTime = new JLabel("");
		iconNomeTime.setIcon(new ImageIcon(Interface.class.getResource("/img/handshake.png")));
		iconNomeTime.setBounds(47, 42, 16, 16);
		panelNomeTime.add(iconNomeTime);

		JSeparator separatorNomeTime = new JSeparator();
		separatorNomeTime.setBounds(47, 66, 142, 2);
		panelNomeTime.add(separatorNomeTime);

		JLabel lblNomeTime = new JLabel("Nome do time");
		lblNomeTime.setForeground(Color.WHITE);
		lblNomeTime.setBounds(47, 11, 142, 14);
		panelNomeTime.add(lblNomeTime);

		tfNomeTime = new JTextField();
		tfNomeTime.setOpaque(false);
		tfNomeTime.setForeground(Color.WHITE);
		tfNomeTime.setColumns(10);
		tfNomeTime.setCaretColor(Color.WHITE);
		tfNomeTime.setBorder(null);
		tfNomeTime.setBounds(73, 38, 118, 20);
		panelNomeTime.add(tfNomeTime);

		JPanel panelEpCriacao = new JPanel();
		panelEpCriacao.setLayout(null);
		panelEpCriacao.setBackground(new Color(48, 49, 52));
		panelEpCriacao.setBounds(461, 192, 233, 75);
		gradientPanelCTime.add(panelEpCriacao);

		JLabel iconEpCriacao = new JLabel("");
		iconEpCriacao.setIcon(new ImageIcon(Interface.class.getResource("/img/handshake.png")));
		iconEpCriacao.setBounds(47, 42, 16, 16);
		panelEpCriacao.add(iconEpCriacao);

		JSeparator separatorEpCriacao = new JSeparator();
		separatorEpCriacao.setBounds(47, 66, 142, 2);
		panelEpCriacao.add(separatorEpCriacao);

		JLabel lblEpCriacao = new JLabel("Epis\u00F3dio de cria\u00E7\u00E3o");
		lblEpCriacao.setForeground(Color.WHITE);
		lblEpCriacao.setBounds(47, 11, 142, 14);
		panelEpCriacao.add(lblEpCriacao);

		tfEpCriacao = new JTextField();
		tfEpCriacao.setOpaque(false);
		tfEpCriacao.setForeground(Color.WHITE);
		tfEpCriacao.setColumns(10);
		tfEpCriacao.setCaretColor(Color.WHITE);
		tfEpCriacao.setBorder(null);
		tfEpCriacao.setBounds(73, 38, 118, 20);
		panelEpCriacao.add(tfEpCriacao);

		JLabel iconTime_1 = new JLabel("");
		iconTime_1.setIcon(new ImageIcon(Interface.class.getResource("/img/time.png")));
		iconTime_1.setBounds(304, 11, 95, 90);
		gradientPanelCTime.add(iconTime_1);

		KButton btnAdicionarTime = new KButton();
		btnAdicionarTime.setBorder(null);
		btnAdicionarTime.setOpaque(false);
		btnAdicionarTime.setText("Adicionar");
		btnAdicionarTime.setkHoverForeGround(Color.WHITE);
		btnAdicionarTime.setkPressedColor(new Color(179, 175, 168));
		btnAdicionarTime.setkHoverStartColor(new Color(248, 49, 11));
		btnAdicionarTime.setkHoverEndColor(new Color(254, 126, 30));
		btnAdicionarTime.setkStartColor(new Color(254, 140, 0));
		btnAdicionarTime.setkEndColor(new Color(248, 54, 0));
		btnAdicionarTime.setBounds(509, 295, 144, 32);
		gradientPanelCTime.add(btnAdicionarTime);
		
		JLabel lblNewLabel_9 = new JLabel("*Obrigat\u00F3rio");
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setBounds(47, 332, 77, 14);
		gradientPanelCTime.add(lblNewLabel_9);

		JPanel panelRTime = new JPanel();
		panelRTime.setLayout(null);
		panelRTime.setOpaque(false);
		panelRTime.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelRTime, "name_248530261212800");

		KGradientPanel gradientPanelRTime = new KGradientPanel();
		gradientPanelRTime.setOpaque(false);
		gradientPanelRTime.setkBorderRadius(20);
		gradientPanelRTime.setkStartColor(new Color(48, 49, 52));
		gradientPanelRTime.setkEndColor(new Color(48, 49, 52));
		gradientPanelRTime.setLayout(null);
		gradientPanelRTime.setBounds(10, 11, 704, 357);
		panelRTime.add(gradientPanelRTime);

		JLabel iconTime_2 = new JLabel("");
		iconTime_2.setIcon(new ImageIcon(Interface.class.getResource("/img/time.png")));
		iconTime_2.setBounds(304, 11, 95, 90);
		gradientPanelRTime.add(iconTime_2);

		JScrollPane scrollPaneTime = new JScrollPane();
		scrollPaneTime.setBounds(10, 112, 684, 234);
		gradientPanelRTime.add(scrollPaneTime);

		tableTime = new JTable();
		tableTime.setBorder(null);
		tableTime.setFocusable(false);
		tableTime.setSelectionBackground(new Color(130, 130, 130));
		tableTime.setBackground(new Color(130, 130, 130));
		tableTime.setGridColor(new Color(151, 151, 151));
		tableTime.setForeground(Color.WHITE);
		tableTime.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N\u00FAmero", "Quantidade de membros", "Nome", "Ep. de cria\u00E7\u00E3o", "Sucessos"
			}
		) {
			Class<?>[] columnTypes = new Class[] {
				Integer.class, Integer.class, String.class, Integer.class, Object.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableTime.getColumnModel().getColumn(0).setPreferredWidth(52);
		tableTime.getColumnModel().getColumn(1).setPreferredWidth(133);
		tableTime.getColumnModel().getColumn(2).setPreferredWidth(118);
		tableTime.getColumnModel().getColumn(3).setPreferredWidth(83);
		
		tableTime.getColumnModel().getColumn(0).setHeaderRenderer(new header(scrollPaneTime));
		tableTime.getColumnModel().getColumn(1).setHeaderRenderer(new header(scrollPaneTime));
		tableTime.getColumnModel().getColumn(2).setHeaderRenderer(new header(scrollPaneTime));
		tableTime.getColumnModel().getColumn(3).setHeaderRenderer(new header(scrollPaneTime));
		tableTime.getColumnModel().getColumn(4).setHeaderRenderer(new header(scrollPaneTime));
		tableTime.setDefaultRenderer(Object.class, new cell());
		
		scrollPaneTime.setViewportView(tableTime);
		
		KButton btnOrdenarPorEpCriacao = new KButton();
		btnOrdenarPorEpCriacao.setText("Ordenar por ep. de cria\u00E7\u00E3o");
		btnOrdenarPorEpCriacao.setBorder(null);
		btnOrdenarPorEpCriacao.setOpaque(false);
		btnOrdenarPorEpCriacao.setkHoverForeGround(Color.WHITE);
		btnOrdenarPorEpCriacao.setkPressedColor(new Color(179, 175, 168));
		btnOrdenarPorEpCriacao.setkHoverStartColor(new Color(248, 49, 11));
		btnOrdenarPorEpCriacao.setkHoverEndColor(new Color(254, 126, 30));
		btnOrdenarPorEpCriacao.setkStartColor(new Color(254, 140, 0));
		btnOrdenarPorEpCriacao.setkEndColor(new Color(248, 54, 0));
		btnOrdenarPorEpCriacao.setBounds(470, 40, 180, 32);
		gradientPanelRTime.add(btnOrdenarPorEpCriacao);
		
		KButton btnRedefinirTime = new KButton();
		btnRedefinirTime.setIconTextGap(9);
		btnRedefinirTime.setIcon(new ImageIcon(Interface.class.getResource("/img/reload.png")));
		btnRedefinirTime.setBorder(null);
		btnRedefinirTime.setOpaque(false);
		btnRedefinirTime.setkHoverForeGround(Color.WHITE);
		btnRedefinirTime.setkPressedColor(new Color(179, 175, 168));
		btnRedefinirTime.setkHoverStartColor(new Color(248, 49, 11));
		btnRedefinirTime.setkHoverEndColor(new Color(254, 126, 30));
		btnRedefinirTime.setkStartColor(new Color(254, 140, 0));
		btnRedefinirTime.setkEndColor(new Color(248, 54, 0));
		btnRedefinirTime.setBounds(51, 44, 34, 32);
		gradientPanelRTime.add(btnRedefinirTime);
		
		JLabel lblNewLabel_17 = new JLabel("Redefinir:");
		lblNewLabel_17.setForeground(Color.WHITE);
		lblNewLabel_17.setBounds(51, 24, 67, 14);
		gradientPanelRTime.add(lblNewLabel_17);

		JPanel panelUTime = new JPanel();
		panelUTime.setLayout(null);
		panelUTime.setOpaque(false);
		panelUTime.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelUTime, "name_248505946790000");

		KGradientPanel gradientPanelUTime = new KGradientPanel();
		gradientPanelUTime.setOpaque(false);
		gradientPanelUTime.setkBorderRadius(20);
		gradientPanelUTime.setkStartColor(new Color(48, 49, 52));
		gradientPanelUTime.setkEndColor(new Color(48, 49, 52));
		gradientPanelUTime.setLayout(null);
		gradientPanelUTime.setBounds(10, 11, 704, 357);
		panelUTime.add(gradientPanelUTime);

		JLabel iconTime_3 = new JLabel("");
		iconTime_3.setIcon(new ImageIcon(Interface.class.getResource("/img/time.png")));
		iconTime_3.setBounds(304, 11, 95, 90);
		gradientPanelUTime.add(iconTime_3);

		KButton btnOK_EditarTime = new KButton();
		btnOK_EditarTime.setBorder(null);
		btnOK_EditarTime.setOpaque(false);
		btnOK_EditarTime.setText("OK");
		btnOK_EditarTime.setkHoverForeGround(Color.WHITE);
		btnOK_EditarTime.setkPressedColor(new Color(179, 175, 168));
		btnOK_EditarTime.setkHoverStartColor(new Color(248, 49, 11));
		btnOK_EditarTime.setkHoverEndColor(new Color(254, 126, 30));
		btnOK_EditarTime.setkStartColor(new Color(254, 140, 0));
		btnOK_EditarTime.setkEndColor(new Color(248, 54, 0));
		btnOK_EditarTime.setBounds(375, 195, 90, 30);
		gradientPanelUTime.add(btnOK_EditarTime);

		JComboBox<String> cbNumero = new JComboBox<>();
		cbNumero.setForeground(Color.WHITE);
		cbNumero.setBackground(Color.GRAY);
		cbNumero.setBounds(251, 199, 114, 22);
		gradientPanelUTime.add(cbNumero);
		
		JLabel lblNewLabel_7 = new JLabel("Selecione o n\u00FAmero do time para edit\u00E1-lo:");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setBounds(233, 127, 261, 14);
		gradientPanelUTime.add(lblNewLabel_7);

		JPanel panelUTime_1 = new JPanel();
		panelUTime_1.setLayout(null);
		panelUTime_1.setOpaque(false);
		panelUTime_1.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelUTime_1, "name_250650077108200");

		KGradientPanel gradientPanelUTime_1 = new KGradientPanel();
		gradientPanelUTime_1.setOpaque(false);
		gradientPanelUTime_1.setkBorderRadius(20);
		gradientPanelUTime_1.setkStartColor(new Color(48, 49, 52));
		gradientPanelUTime_1.setkEndColor(new Color(48, 49, 52));
		gradientPanelUTime_1.setLayout(null);
		gradientPanelUTime_1.setBounds(10, 11, 704, 357);
		panelUTime_1.add(gradientPanelUTime_1);

		JPanel panelNumero_1 = new JPanel();
		panelNumero_1.setLayout(null);
		panelNumero_1.setBackground(new Color(48, 49, 52));
		panelNumero_1.setBounds(10, 106, 233, 75);
		gradientPanelUTime_1.add(panelNumero_1);

		JLabel lblNumero_1 = new JLabel("N\u00FAmero*");
		lblNumero_1.setForeground(Color.WHITE);
		lblNumero_1.setBounds(47, 11, 142, 14);
		panelNumero_1.add(lblNumero_1);
		
		JComboBox<Integer> cbNumeroTime_1 = new JComboBox<Integer>();
		cbNumeroTime_1.setBounds(47, 36, 142, 28);
		panelNumero_1.add(cbNumeroTime_1);

		JPanel panelQuantMembros_1 = new JPanel();
		panelQuantMembros_1.setLayout(null);
		panelQuantMembros_1.setBackground(new Color(48, 49, 52));
		panelQuantMembros_1.setBounds(10, 192, 233, 75);
		gradientPanelUTime_1.add(panelQuantMembros_1);

		JLabel iconQuantMembros_1 = new JLabel("");
		iconQuantMembros_1.setIcon(new ImageIcon(Interface.class.getResource("/img/handshake.png")));
		iconQuantMembros_1.setBounds(47, 42, 16, 16);
		panelQuantMembros_1.add(iconQuantMembros_1);

		JSeparator separatorQuantMembros_1 = new JSeparator();
		separatorQuantMembros_1.setBounds(47, 66, 142, 2);
		panelQuantMembros_1.add(separatorQuantMembros_1);

		JLabel lblQuantMembros_1 = new JLabel("Quantidade de membros");
		lblQuantMembros_1.setForeground(Color.WHITE);
		lblQuantMembros_1.setBounds(47, 11, 142, 14);
		panelQuantMembros_1.add(lblQuantMembros_1);

		tfQuantMembros_1 = new JTextField();
		tfQuantMembros_1.setOpaque(false);
		tfQuantMembros_1.setForeground(Color.WHITE);
		tfQuantMembros_1.setColumns(10);
		tfQuantMembros_1.setCaretColor(Color.WHITE);
		tfQuantMembros_1.setBorder(null);
		tfQuantMembros_1.setBounds(71, 38, 118, 20);
		panelQuantMembros_1.add(tfQuantMembros_1);

		JPanel panelNomeTime_1 = new JPanel();
		panelNomeTime_1.setLayout(null);
		panelNomeTime_1.setBackground(new Color(48, 49, 52));
		panelNomeTime_1.setBounds(461, 106, 233, 75);
		gradientPanelUTime_1.add(panelNomeTime_1);

		JLabel iconNomeTime_1 = new JLabel("");
		iconNomeTime_1.setIcon(new ImageIcon(Interface.class.getResource("/img/handshake.png")));
		iconNomeTime_1.setBounds(47, 42, 16, 16);
		panelNomeTime_1.add(iconNomeTime_1);

		JSeparator separatorNomeTime_1 = new JSeparator();
		separatorNomeTime_1.setBounds(47, 66, 142, 2);
		panelNomeTime_1.add(separatorNomeTime_1);

		JLabel lblNomeTime_1 = new JLabel("Nome do time");
		lblNomeTime_1.setForeground(Color.WHITE);
		lblNomeTime_1.setBounds(47, 11, 142, 14);
		panelNomeTime_1.add(lblNomeTime_1);

		tfNomeTime_1 = new JTextField();
		tfNomeTime_1.setOpaque(false);
		tfNomeTime_1.setForeground(Color.WHITE);
		tfNomeTime_1.setColumns(10);
		tfNomeTime_1.setCaretColor(Color.WHITE);
		tfNomeTime_1.setBorder(null);
		tfNomeTime_1.setBounds(73, 38, 118, 20);
		panelNomeTime_1.add(tfNomeTime_1);

		JPanel panelEpCriacao_1 = new JPanel();
		panelEpCriacao_1.setLayout(null);
		panelEpCriacao_1.setBackground(new Color(48, 49, 52));
		panelEpCriacao_1.setBounds(461, 192, 233, 75);
		gradientPanelUTime_1.add(panelEpCriacao_1);

		JLabel iconEpCriacao_1 = new JLabel("");
		iconEpCriacao_1.setIcon(new ImageIcon(Interface.class.getResource("/img/handshake.png")));
		iconEpCriacao_1.setBounds(47, 42, 16, 16);
		panelEpCriacao_1.add(iconEpCriacao_1);

		JSeparator separatorEpCriacao_1 = new JSeparator();
		separatorEpCriacao_1.setBounds(47, 66, 142, 2);
		panelEpCriacao_1.add(separatorEpCriacao_1);

		JLabel lblEpCriacao_1 = new JLabel("Epis\u00F3dio de cria\u00E7\u00E3o");
		lblEpCriacao_1.setForeground(Color.WHITE);
		lblEpCriacao_1.setBounds(47, 11, 142, 14);
		panelEpCriacao_1.add(lblEpCriacao_1);

		tfEpCriacao_1 = new JTextField();
		tfEpCriacao_1.setOpaque(false);
		tfEpCriacao_1.setForeground(Color.WHITE);
		tfEpCriacao_1.setColumns(10);
		tfEpCriacao_1.setCaretColor(Color.WHITE);
		tfEpCriacao_1.setBorder(null);
		tfEpCriacao_1.setBounds(73, 38, 118, 20);
		panelEpCriacao_1.add(tfEpCriacao_1);

		JLabel iconTime_2_1 = new JLabel("");
		iconTime_2_1.setIcon(new ImageIcon(Interface.class.getResource("/img/time.png")));
		iconTime_2_1.setBounds(304, 11, 95, 90);
		gradientPanelUTime_1.add(iconTime_2_1);

		KButton btnEditarTime = new KButton();
		btnEditarTime.setBorder(null);
		btnEditarTime.setOpaque(false);
		btnEditarTime.setText("Editar");
		btnEditarTime.setkStartColor(new Color(254, 140, 0));
		btnEditarTime.setkPressedColor(new Color(179, 175, 168));
		btnEditarTime.setkHoverStartColor(new Color(248, 49, 11));
		btnEditarTime.setkHoverForeGround(Color.WHITE);
		btnEditarTime.setkHoverEndColor(new Color(254, 126, 30));
		btnEditarTime.setkEndColor(new Color(248, 54, 0));
		btnEditarTime.setBounds(509, 295, 144, 32);
		gradientPanelUTime_1.add(btnEditarTime);
		
		JLabel lblNewLabel_8 = new JLabel("*Obrigat\u00F3rio");
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setBounds(47, 332, 80, 14);
		gradientPanelUTime_1.add(lblNewLabel_8);

		JPanel panelDTime = new JPanel();
		panelDTime.setLayout(null);
		panelDTime.setOpaque(false);
		panelDTime.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelDTime, "name_248575408868300");

		KGradientPanel gradientPanelDTime = new KGradientPanel();
		gradientPanelDTime.setOpaque(false);
		gradientPanelDTime.setkBorderRadius(20);
		gradientPanelDTime.setkStartColor(new Color(48, 49, 52));
		gradientPanelDTime.setkEndColor(new Color(48, 49, 52));
		gradientPanelDTime.setLayout(null);
		gradientPanelDTime.setBounds(10, 11, 704, 357);
		panelDTime.add(gradientPanelDTime);

		JLabel iconTime_4 = new JLabel("");
		iconTime_4.setIcon(new ImageIcon(Interface.class.getResource("/img/time.png")));
		iconTime_4.setBounds(306, 11, 92, 89);
		gradientPanelDTime.add(iconTime_4);

		KButton btnOK_ExcluirTime = new KButton();
		btnOK_ExcluirTime.setBorder(null);
		btnOK_ExcluirTime.setOpaque(false);
		btnOK_ExcluirTime.setText("OK");
		btnOK_ExcluirTime.setkHoverForeGround(Color.WHITE);
		btnOK_ExcluirTime.setkPressedColor(new Color(179, 175, 168));
		btnOK_ExcluirTime.setkHoverStartColor(new Color(248, 49, 11));
		btnOK_ExcluirTime.setkHoverEndColor(new Color(254, 126, 30));
		btnOK_ExcluirTime.setkStartColor(new Color(254, 140, 0));
		btnOK_ExcluirTime.setkEndColor(new Color(248, 54, 0));
		btnOK_ExcluirTime.setBounds(375, 195, 90, 30);
		gradientPanelDTime.add(btnOK_ExcluirTime);

		JComboBox<String> cbNumeroExcluir = new JComboBox<>();
		cbNumeroExcluir.setForeground(Color.WHITE);
		cbNumeroExcluir.setBackground(Color.GRAY);
		cbNumeroExcluir.setBounds(251, 199, 114, 22);
		gradientPanelDTime.add(cbNumeroExcluir);
		
		JLabel lblNewLabel_10 = new JLabel("Selecione o n\u00FAmero do time para exclu\u00ED-lo:");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_10.setForeground(Color.WHITE);
		lblNewLabel_10.setBounds(233, 127, 259, 14);
		gradientPanelDTime.add(lblNewLabel_10);
		
		JPanel panelCFrase = new JPanel();
		panelCFrase.setLayout(null);
		panelCFrase.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelCFrase, "name_17724892613000");
		
		KGradientPanel gradientPanelCFrase = new KGradientPanel();
		gradientPanelCFrase.setLayout(null);
		gradientPanelCFrase.setOpaque(false);
		gradientPanelCFrase.kStartColor = new Color(48, 49, 52);
		gradientPanelCFrase.setkStartColor(new Color(48, 49, 52));
		gradientPanelCFrase.kEndColor = new Color(48, 49, 52);
		gradientPanelCFrase.setkEndColor(new Color(48, 49, 52));
		gradientPanelCFrase.kBorderRadius = 20;
		gradientPanelCFrase.setkBorderRadius(20);
		gradientPanelCFrase.setBounds(10, 11, 704, 357);
		panelCFrase.add(gradientPanelCFrase);
		
		JPanel panelNomePersonagem_2 = new JPanel();
		panelNomePersonagem_2.setLayout(null);
		panelNomePersonagem_2.setBackground(new Color(48, 49, 52));
		panelNomePersonagem_2.setBounds(10, 106, 233, 75);
		gradientPanelCFrase.add(panelNomePersonagem_2);
		
		JLabel lblNomePersonagem_2 = new JLabel("Nome do personagem*");
		lblNomePersonagem_2.setForeground(Color.WHITE);
		lblNomePersonagem_2.setBounds(47, 11, 142, 14);
		panelNomePersonagem_2.add(lblNomePersonagem_2);
		
		JComboBox<String> cbNomePersonagemFrase = new JComboBox<String>();
		cbNomePersonagemFrase.setBounds(47, 36, 142, 28);
		panelNomePersonagem_2.add(cbNomePersonagemFrase);
		
		JPanel panelOcasiao = new JPanel();
		panelOcasiao.setLayout(null);
		panelOcasiao.setBackground(new Color(48, 49, 52));
		panelOcasiao.setBounds(461, 106, 233, 75);
		gradientPanelCFrase.add(panelOcasiao);
		
		JLabel iconOcasiao = new JLabel("");
		iconOcasiao.setIcon(new ImageIcon(Interface.class.getResource("/img/pencil (1).png")));
		iconOcasiao.setBounds(47, 42, 16, 16);
		panelOcasiao.add(iconOcasiao);
		
		JSeparator separatorOcasiao = new JSeparator();
		separatorOcasiao.setBounds(47, 66, 142, 2);
		panelOcasiao.add(separatorOcasiao);
		
		JLabel lblOcasiao = new JLabel("Ocasi\u00E3o*");
		lblOcasiao.setForeground(Color.WHITE);
		lblOcasiao.setBounds(47, 11, 86, 14);
		panelOcasiao.add(lblOcasiao);
		
		tfOcasiao = new JTextField();
		tfOcasiao.setOpaque(false);
		tfOcasiao.setForeground(Color.WHITE);
		tfOcasiao.setColumns(10);
		tfOcasiao.setCaretColor(Color.WHITE);
		tfOcasiao.setBorder(null);
		tfOcasiao.setBounds(71, 42, 118, 20);
		panelOcasiao.add(tfOcasiao);
		
		JPanel panelTexto = new JPanel();
		panelTexto.setLayout(null);
		panelTexto.setBackground(new Color(48, 49, 52));
		panelTexto.setBounds(10, 192, 233, 75);
		gradientPanelCFrase.add(panelTexto);
		
		JLabel iconTexto = new JLabel("");
		iconTexto.setIcon(new ImageIcon(Interface.class.getResource("/img/pencil (1).png")));
		iconTexto.setBounds(47, 42, 16, 16);
		panelTexto.add(iconTexto);
		
		JSeparator separatorTexto = new JSeparator();
		separatorTexto.setBounds(47, 66, 142, 2);
		panelTexto.add(separatorTexto);
		
		JLabel lblTexto = new JLabel("Texto*");
		lblTexto.setForeground(Color.WHITE);
		lblTexto.setBounds(47, 11, 86, 14);
		panelTexto.add(lblTexto);
		
		tfTexto = new JTextField();
		tfTexto.setOpaque(false);
		tfTexto.setForeground(Color.WHITE);
		tfTexto.setColumns(10);
		tfTexto.setCaretColor(Color.WHITE);
		tfTexto.setBorder(null);
		tfTexto.setBounds(71, 42, 118, 20);
		panelTexto.add(tfTexto);
		
		JLabel lblObrigatorio_2_2 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_2_2.setForeground(Color.WHITE);
		lblObrigatorio_2_2.setBounds(47, 332, 75, 14);
		gradientPanelCFrase.add(lblObrigatorio_2_2);
		
		KButton btnAdicionarFrase = new KButton();
		btnAdicionarFrase.setText("Adicionar");
		btnAdicionarFrase.setOpaque(false);
		btnAdicionarFrase.kStartColor = new Color(254, 140, 0);
		btnAdicionarFrase.setkStartColor(new Color(254, 140, 0));
		btnAdicionarFrase.kPressedColor = new Color(179, 175, 168);
		btnAdicionarFrase.setkPressedColor(new Color(179, 175, 168));
		btnAdicionarFrase.kHoverStartColor = new Color(248, 49, 11);
		btnAdicionarFrase.setkHoverStartColor(new Color(248, 49, 11));
		btnAdicionarFrase.kHoverForeGround = Color.WHITE;
		btnAdicionarFrase.setkHoverForeGround(Color.WHITE);
		btnAdicionarFrase.kHoverEndColor = new Color(254, 126, 30);
		btnAdicionarFrase.setkHoverEndColor(new Color(254, 126, 30));
		btnAdicionarFrase.kEndColor = new Color(248, 54, 0);
		btnAdicionarFrase.setkEndColor(new Color(248, 54, 0));
		btnAdicionarFrase.setBorder(null);
		btnAdicionarFrase.setBounds(509, 215, 144, 32);
		gradientPanelCFrase.add(btnAdicionarFrase);
		
		JLabel iconNarutoC_1_1 = new JLabel("");
		iconNarutoC_1_1.setIcon(new ImageIcon(Interface.class.getResource("/img/final_61a141fe16c72e007eba0364_179864 (2).png")));
		iconNarutoC_1_1.setBounds(299, 11, 105, 79);
		gradientPanelCFrase.add(iconNarutoC_1_1);
		
		JPanel panelRFrase = new JPanel();
		panelRFrase.setLayout(null);
		panelRFrase.setOpaque(false);
		panelRFrase.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelRFrase, "name_18337037493900");
		
		KGradientPanel gradientPanelRFrase = new KGradientPanel();
		gradientPanelRFrase.setLayout(null);
		gradientPanelRFrase.setOpaque(false);
		gradientPanelRFrase.kStartColor = new Color(48, 49, 52);
		gradientPanelRFrase.setkStartColor(new Color(48, 49, 52));
		gradientPanelRFrase.kEndColor = new Color(48, 49, 52);
		gradientPanelRFrase.setkEndColor(new Color(48, 49, 52));
		gradientPanelRFrase.kBorderRadius = 20;
		gradientPanelRFrase.setkBorderRadius(20);
		gradientPanelRFrase.setBounds(10, 11, 704, 357);
		panelRFrase.add(gradientPanelRFrase);
		
		JLabel iconTime_2_2 = new JLabel("");
		iconTime_2_2.setIcon(new ImageIcon(Interface.class.getResource("/img/final_61a141fe16c72e007eba0364_179864 (2).png")));
		iconTime_2_2.setBounds(299, 11, 105, 79);
		gradientPanelRFrase.add(iconTime_2_2);
		
		JScrollPane scrollPaneFrase = new JScrollPane();
		scrollPaneFrase.setBounds(10, 112, 684, 234);
		gradientPanelRFrase.add(scrollPaneFrase);
		
		tableFrase = new JTable();
		
		tableFrase.setBorder(null);
        tableFrase.setFocusable(false);
        tableFrase.setSelectionBackground(new Color(130, 130, 130));
        tableFrase.setBackground(new Color(130, 130, 130));
        tableFrase.setGridColor(new Color(151, 151, 151));
        tableFrase.setForeground(Color.WHITE);
        
		tableFrase.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome do personagem", "Texto", "Ocasi\u00E3o"
			}
		));
		tableFrase.getColumnModel().getColumn(0).setPreferredWidth(133);
		tableFrase.getColumnModel().getColumn(1).setPreferredWidth(382);
		tableFrase.getColumnModel().getColumn(2).setPreferredWidth(104);
		
		tableFrase.getColumnModel().getColumn(0).setHeaderRenderer(new header(scrollPaneFrase));
		tableFrase.getColumnModel().getColumn(1).setHeaderRenderer(new header(scrollPaneFrase));
		tableFrase.getColumnModel().getColumn(2).setHeaderRenderer(new header(scrollPaneFrase));
		tableFrase.setDefaultRenderer(Object.class, new cell());
		        
		scrollPaneFrase.setViewportView(tableFrase);
		
		JPanel panelDFrase = new JPanel();
		panelDFrase.setLayout(null);
		panelDFrase.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelDFrase, "name_18932684132700");
		
		KGradientPanel gradientPanelDFrase = new KGradientPanel();
		gradientPanelDFrase.setLayout(null);
		gradientPanelDFrase.setOpaque(false);
		gradientPanelDFrase.kStartColor = new Color(48, 49, 52);
		gradientPanelDFrase.setkStartColor(new Color(48, 49, 52));
		gradientPanelDFrase.kEndColor = new Color(48, 49, 52);
		gradientPanelDFrase.setkEndColor(new Color(48, 49, 52));
		gradientPanelDFrase.kBorderRadius = 20;
		gradientPanelDFrase.setkBorderRadius(20);
		gradientPanelDFrase.setBounds(10, 11, 704, 357);
		panelDFrase.add(gradientPanelDFrase);
		
		JPanel panelNomerPersonagemExcluirFrase = new JPanel();
		panelNomerPersonagemExcluirFrase.setLayout(null);
		panelNomerPersonagemExcluirFrase.setBackground(new Color(48, 49, 52));
		panelNomerPersonagemExcluirFrase.setBounds(10, 106, 233, 75);
		gradientPanelDFrase.add(panelNomerPersonagemExcluirFrase);
		
		JLabel lblNomePersonagemExcluirFrase = new JLabel("Nome do personagem*");
		lblNomePersonagemExcluirFrase.setForeground(Color.WHITE);
		lblNomePersonagemExcluirFrase.setBounds(47, 11, 142, 14);
		panelNomerPersonagemExcluirFrase.add(lblNomePersonagemExcluirFrase);
		
		JComboBox<String> cbNomePersonagemFraseExcluir = new JComboBox<String>();
		cbNomePersonagemFraseExcluir.setBounds(47, 36, 142, 28);
		panelNomerPersonagemExcluirFrase.add(cbNomePersonagemFraseExcluir);
		
		JPanel panelTextoExcluir = new JPanel();
		panelTextoExcluir.setLayout(null);
		panelTextoExcluir.setBackground(new Color(48, 49, 52));
		panelTextoExcluir.setBounds(10, 192, 233, 75);
		gradientPanelDFrase.add(panelTextoExcluir);
		
		JLabel iconTextoExcluir = new JLabel("");
		iconTextoExcluir.setIcon(new ImageIcon(Interface.class.getResource("/img/pencil (1).png")));
		iconTextoExcluir.setBounds(47, 42, 16, 16);
		panelTextoExcluir.add(iconTextoExcluir);
		
		JSeparator separatorTextoExcluir = new JSeparator();
		separatorTextoExcluir.setBounds(47, 66, 142, 2);
		panelTextoExcluir.add(separatorTextoExcluir);
		
		JLabel lblTextoExcluir = new JLabel("Texto*");
		lblTextoExcluir.setForeground(Color.WHITE);
		lblTextoExcluir.setBounds(47, 11, 142, 14);
		panelTextoExcluir.add(lblTextoExcluir);
		
		tfTextoExcluir = new JTextField();
		tfTextoExcluir.setOpaque(false);
		tfTextoExcluir.setForeground(Color.WHITE);
		tfTextoExcluir.setColumns(10);
		tfTextoExcluir.setCaretColor(Color.WHITE);
		tfTextoExcluir.setBorder(null);
		tfTextoExcluir.setBounds(71, 38, 118, 20);
		panelTextoExcluir.add(tfTextoExcluir);
		
		JPanel panelOcasiaoExcluir = new JPanel();
		panelOcasiaoExcluir.setLayout(null);
		panelOcasiaoExcluir.setBackground(new Color(48, 49, 52));
		panelOcasiaoExcluir.setBounds(461, 106, 233, 75);
		gradientPanelDFrase.add(panelOcasiaoExcluir);
		
		JLabel iconOcasiaoExcluir = new JLabel("");
		iconOcasiaoExcluir.setIcon(new ImageIcon(Interface.class.getResource("/img/pencil (1).png")));
		iconOcasiaoExcluir.setBounds(47, 42, 16, 16);
		panelOcasiaoExcluir.add(iconOcasiaoExcluir);
		
		JSeparator separatorOcasiaoExcluir = new JSeparator();
		separatorOcasiaoExcluir.setBounds(47, 66, 142, 2);
		panelOcasiaoExcluir.add(separatorOcasiaoExcluir);
		
		JLabel lblOcasiaoExcluir = new JLabel("Ocasi\u00E3o*");
		lblOcasiaoExcluir.setForeground(Color.WHITE);
		lblOcasiaoExcluir.setBounds(47, 11, 142, 14);
		panelOcasiaoExcluir.add(lblOcasiaoExcluir);
		
		tfOcasiaoExcluir = new JTextField();
		tfOcasiaoExcluir.setOpaque(false);
		tfOcasiaoExcluir.setForeground(Color.WHITE);
		tfOcasiaoExcluir.setColumns(10);
		tfOcasiaoExcluir.setCaretColor(Color.WHITE);
		tfOcasiaoExcluir.setBorder(null);
		tfOcasiaoExcluir.setBounds(73, 38, 118, 20);
		panelOcasiaoExcluir.add(tfOcasiaoExcluir);
		
		JLabel iconNarutoU_1_1_1_1 = new JLabel("");
		iconNarutoU_1_1_1_1.setIcon(new ImageIcon(Interface.class.getResource("/img/final_61a141fe16c72e007eba0364_179864 (2).png")));
		iconNarutoU_1_1_1_1.setBounds(299, 11, 105, 79);
		gradientPanelDFrase.add(iconNarutoU_1_1_1_1);
		
		KButton btnOK_ExcluirFrase = new KButton();
		btnOK_ExcluirFrase.setText("OK");
		btnOK_ExcluirFrase.setOpaque(false);
		btnOK_ExcluirFrase.kStartColor = new Color(254, 140, 0);
		btnOK_ExcluirFrase.setkStartColor(new Color(254, 140, 0));
		btnOK_ExcluirFrase.kPressedColor = new Color(179, 175, 168);
		btnOK_ExcluirFrase.setkPressedColor(new Color(179, 175, 168));
		btnOK_ExcluirFrase.kHoverStartColor = new Color(248, 49, 11);
		btnOK_ExcluirFrase.setkHoverStartColor(new Color(248, 49, 11));
		btnOK_ExcluirFrase.kHoverForeGround = Color.WHITE;
		btnOK_ExcluirFrase.setkHoverForeGround(Color.WHITE);
		btnOK_ExcluirFrase.kHoverEndColor = new Color(254, 126, 30);
		btnOK_ExcluirFrase.setkHoverEndColor(new Color(254, 126, 30));
		btnOK_ExcluirFrase.kEndColor = new Color(248, 54, 0);
		btnOK_ExcluirFrase.setkEndColor(new Color(248, 54, 0));
		btnOK_ExcluirFrase.setBorder(null);
		btnOK_ExcluirFrase.setBounds(509, 215, 144, 32);
		gradientPanelDFrase.add(btnOK_ExcluirFrase);
		
		JLabel lblNewLabel_18 = new JLabel("*Obrigat\u00F3rio");
		lblNewLabel_18.setForeground(Color.WHITE);
		lblNewLabel_18.setBounds(47, 332, 106, 14);
		gradientPanelDFrase.add(lblNewLabel_18);
		
		JPanel panelCTransformacaoNatureza = new JPanel();
		panelCTransformacaoNatureza.setLayout(null);
		panelCTransformacaoNatureza.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelCTransformacaoNatureza, "name_27394747553600");
		
		KGradientPanel gradientPanelCTransformacaoNatureza = new KGradientPanel();
		gradientPanelCTransformacaoNatureza.setLayout(null);
		gradientPanelCTransformacaoNatureza.setOpaque(false);
		gradientPanelCTransformacaoNatureza.kStartColor = new Color(48, 49, 52);
		gradientPanelCTransformacaoNatureza.setkStartColor(new Color(48, 49, 52));
		gradientPanelCTransformacaoNatureza.kEndColor = new Color(48, 49, 52);
		gradientPanelCTransformacaoNatureza.setkEndColor(new Color(48, 49, 52));
		gradientPanelCTransformacaoNatureza.kBorderRadius = 20;
		gradientPanelCTransformacaoNatureza.setkBorderRadius(20);
		gradientPanelCTransformacaoNatureza.setBounds(10, 11, 704, 357);
		panelCTransformacaoNatureza.add(gradientPanelCTransformacaoNatureza);
		
		JPanel panelNomePersonagemNatureza = new JPanel();
		panelNomePersonagemNatureza.setLayout(null);
		panelNomePersonagemNatureza.setBackground(new Color(48, 49, 52));
		panelNomePersonagemNatureza.setBounds(10, 106, 233, 75);
		gradientPanelCTransformacaoNatureza.add(panelNomePersonagemNatureza);
		
		JLabel lblNomePersonagemNatureza = new JLabel("Nome do personagem*");
		lblNomePersonagemNatureza.setForeground(Color.WHITE);
		lblNomePersonagemNatureza.setBounds(47, 11, 142, 14);
		panelNomePersonagemNatureza.add(lblNomePersonagemNatureza);
		
		JComboBox<String> cbNomePersonagemTransformacao = new JComboBox<String>();
		cbNomePersonagemTransformacao.setBounds(47, 31, 142, 33);
		panelNomePersonagemNatureza.add(cbNomePersonagemTransformacao);
		
		JPanel panelTransformacaoNatureza = new JPanel();
		panelTransformacaoNatureza.setLayout(null);
		panelTransformacaoNatureza.setBackground(new Color(48, 49, 52));
		panelTransformacaoNatureza.setBounds(461, 106, 233, 75);
		gradientPanelCTransformacaoNatureza.add(panelTransformacaoNatureza);
		
		JLabel lblTransformaoNatureza = new JLabel("Transforma\u00E7\u00E3o da natureza*");
		lblTransformaoNatureza.setForeground(Color.WHITE);
		lblTransformaoNatureza.setBounds(47, 11, 176, 14);
		panelTransformacaoNatureza.add(lblTransformaoNatureza);
		
		JComboBox<String> cbTransformacaoNatureza = new JComboBox<String>();
		cbTransformacaoNatureza.setModel(new DefaultComboBoxModel<String>(new String[] {"Fogo", "Vento", "Raio", "Terra", "\u00C1gua", "Gelo", "Madeira", "Fus\u00E3o", "Tempestade", "Ebuli\u00E7\u00E3o", "P\u00F3", "Calor"}));
		cbTransformacaoNatureza.setBounds(47, 36, 162, 28);
		panelTransformacaoNatureza.add(cbTransformacaoNatureza);
		
		JLabel lblObrigatorio_2_2_1 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_2_2_1.setForeground(Color.WHITE);
		lblObrigatorio_2_2_1.setBounds(47, 332, 75, 14);
		gradientPanelCTransformacaoNatureza.add(lblObrigatorio_2_2_1);
		
		KButton btnAdicionarTransformacaoNatureza = new KButton();
		btnAdicionarTransformacaoNatureza.setText("Adicionar");
		btnAdicionarTransformacaoNatureza.setOpaque(false);
		btnAdicionarTransformacaoNatureza.kStartColor = new Color(254, 140, 0);
		btnAdicionarTransformacaoNatureza.setkStartColor(new Color(254, 140, 0));
		btnAdicionarTransformacaoNatureza.kPressedColor = new Color(179, 175, 168);
		btnAdicionarTransformacaoNatureza.setkPressedColor(new Color(179, 175, 168));
		btnAdicionarTransformacaoNatureza.kHoverStartColor = new Color(248, 49, 11);
		btnAdicionarTransformacaoNatureza.setkHoverStartColor(new Color(248, 49, 11));
		btnAdicionarTransformacaoNatureza.kHoverForeGround = Color.WHITE;
		btnAdicionarTransformacaoNatureza.setkHoverForeGround(Color.WHITE);
		btnAdicionarTransformacaoNatureza.kHoverEndColor = new Color(254, 126, 30);
		btnAdicionarTransformacaoNatureza.setkHoverEndColor(new Color(254, 126, 30));
		btnAdicionarTransformacaoNatureza.kEndColor = new Color(248, 54, 0);
		btnAdicionarTransformacaoNatureza.setkEndColor(new Color(248, 54, 0));
		btnAdicionarTransformacaoNatureza.setBorder(null);
		btnAdicionarTransformacaoNatureza.setBounds(509, 215, 144, 32);
		gradientPanelCTransformacaoNatureza.add(btnAdicionarTransformacaoNatureza);
		
		JLabel iconNarutoC_1_1_1 = new JLabel("");
		iconNarutoC_1_1_1.setIcon(new ImageIcon(Interface.class.getResource("/img/transformacao_natureza.png")));
		iconNarutoC_1_1_1.setBounds(304, 11, 96, 96);
		gradientPanelCTransformacaoNatureza.add(iconNarutoC_1_1_1);
		
		JPanel panelRTransformacaoNatureza = new JPanel();
		panelRTransformacaoNatureza.setLayout(null);
		panelRTransformacaoNatureza.setOpaque(false);
		panelRTransformacaoNatureza.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelRTransformacaoNatureza, "name_28502930669600");
		
		KGradientPanel gradientPanelRTransformacaoNatureza = new KGradientPanel();
		gradientPanelRTransformacaoNatureza.setLayout(null);
		gradientPanelRTransformacaoNatureza.setOpaque(false);
		gradientPanelRTransformacaoNatureza.kStartColor = new Color(48, 49, 52);
		gradientPanelRTransformacaoNatureza.setkStartColor(new Color(48, 49, 52));
		gradientPanelRTransformacaoNatureza.kEndColor = new Color(48, 49, 52);
		gradientPanelRTransformacaoNatureza.setkEndColor(new Color(48, 49, 52));
		gradientPanelRTransformacaoNatureza.kBorderRadius = 20;
		gradientPanelRTransformacaoNatureza.setkBorderRadius(20);
		gradientPanelRTransformacaoNatureza.setBounds(10, 11, 704, 357);
		panelRTransformacaoNatureza.add(gradientPanelRTransformacaoNatureza);
		
		JLabel iconTime_2_2_1 = new JLabel("");
		iconTime_2_2_1.setIcon(new ImageIcon(Interface.class.getResource("/img/transformacao_natureza.png")));
		iconTime_2_2_1.setBounds(304, 11, 96, 96);
		gradientPanelRTransformacaoNatureza.add(iconTime_2_2_1);
		
		JScrollPane scrollPaneTransformacaoNatureza = new JScrollPane();
		scrollPaneTransformacaoNatureza.setBounds(10, 112, 684, 234);
		gradientPanelRTransformacaoNatureza.add(scrollPaneTransformacaoNatureza);
		
		tableTransformacaoNatureza = new JTable();
		
		tableTransformacaoNatureza.setBorder(null);
		tableTransformacaoNatureza.setFocusable(false);
		tableTransformacaoNatureza.setSelectionBackground(new Color(130, 130, 130));
		tableTransformacaoNatureza.setBackground(new Color(130, 130, 130));
		tableTransformacaoNatureza.setGridColor(new Color(151, 151, 151));
		tableTransformacaoNatureza.setForeground(Color.WHITE);
		
		tableTransformacaoNatureza.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome do personagem", "Transforma\u00E7\u00E3o da natureza"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableTransformacaoNatureza.getColumnModel().getColumn(0).setPreferredWidth(129);
		tableTransformacaoNatureza.getColumnModel().getColumn(1).setPreferredWidth(156);
		
		tableTransformacaoNatureza.getColumnModel().getColumn(0).setHeaderRenderer(new header(scrollPaneTransformacaoNatureza));
		tableTransformacaoNatureza.getColumnModel().getColumn(1).setHeaderRenderer(new header(scrollPaneTransformacaoNatureza));
		tableTransformacaoNatureza.setDefaultRenderer(Object.class, new cell());
		
		scrollPaneTransformacaoNatureza.setViewportView(tableTransformacaoNatureza);
		
		JPanel panelDTransformacaoNatureza = new JPanel();
		panelDTransformacaoNatureza.setLayout(null);
		panelDTransformacaoNatureza.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelDTransformacaoNatureza, "name_28770525854900");
		
		KGradientPanel gradientPanelDTransformacaoNatureza_1 = new KGradientPanel();
		gradientPanelDTransformacaoNatureza_1.setLayout(null);
		gradientPanelDTransformacaoNatureza_1.setOpaque(false);
		gradientPanelDTransformacaoNatureza_1.kStartColor = new Color(48, 49, 52);
		gradientPanelDTransformacaoNatureza_1.setkStartColor(new Color(48, 49, 52));
		gradientPanelDTransformacaoNatureza_1.kEndColor = new Color(48, 49, 52);
		gradientPanelDTransformacaoNatureza_1.setkEndColor(new Color(48, 49, 52));
		gradientPanelDTransformacaoNatureza_1.kBorderRadius = 20;
		gradientPanelDTransformacaoNatureza_1.setkBorderRadius(20);
		gradientPanelDTransformacaoNatureza_1.setBounds(10, 11, 704, 357);
		panelDTransformacaoNatureza.add(gradientPanelDTransformacaoNatureza_1);
		
		JPanel panelNomePersonagemNaturezaExcluir = new JPanel();
		panelNomePersonagemNaturezaExcluir.setLayout(null);
		panelNomePersonagemNaturezaExcluir.setBackground(new Color(48, 49, 52));
		panelNomePersonagemNaturezaExcluir.setBounds(10, 106, 233, 75);
		gradientPanelDTransformacaoNatureza_1.add(panelNomePersonagemNaturezaExcluir);
		
		JLabel lblNomePersonagemNaturezaExcluir = new JLabel("Nome do personagem*");
		lblNomePersonagemNaturezaExcluir.setForeground(Color.WHITE);
		lblNomePersonagemNaturezaExcluir.setBounds(47, 11, 142, 14);
		panelNomePersonagemNaturezaExcluir.add(lblNomePersonagemNaturezaExcluir);
		
		JComboBox<String> cbNomePersonagemTransformacaoExcluir = new JComboBox<String>();
		cbNomePersonagemTransformacaoExcluir.setBounds(47, 36, 142, 28);
		panelNomePersonagemNaturezaExcluir.add(cbNomePersonagemTransformacaoExcluir);
		
		JPanel panelTransformacaoNaturezaExcluir = new JPanel();
		panelTransformacaoNaturezaExcluir.setLayout(null);
		panelTransformacaoNaturezaExcluir.setBackground(new Color(48, 49, 52));
		panelTransformacaoNaturezaExcluir.setBounds(461, 106, 233, 75);
		gradientPanelDTransformacaoNatureza_1.add(panelTransformacaoNaturezaExcluir);
		
		JLabel lblTransformaoNaturezaExcluir = new JLabel("Transforma\u00E7\u00E3o da natureza*");
		lblTransformaoNaturezaExcluir.setForeground(Color.WHITE);
		lblTransformaoNaturezaExcluir.setBounds(47, 11, 176, 14);
		panelTransformacaoNaturezaExcluir.add(lblTransformaoNaturezaExcluir);
		
		JComboBox<String> cbTransformacaoExcluir = new JComboBox<String>();
		cbTransformacaoExcluir.setModel(new DefaultComboBoxModel<String>(new String[] {"Fogo", "Vento", "Raio", "Terra", "\u00C1gua", "Gelo", "Madeira", "Fus\u00E3o", "Tempestade", "Ebuli\u00E7\u00E3o", "P\u00F3", "Calor"}));
		cbTransformacaoExcluir.setBounds(47, 36, 147, 28);
		panelTransformacaoNaturezaExcluir.add(cbTransformacaoExcluir);
		
		JLabel lblObrigatorio_2_2_1_1 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_2_2_1_1.setForeground(Color.WHITE);
		lblObrigatorio_2_2_1_1.setBounds(47, 332, 75, 14);
		gradientPanelDTransformacaoNatureza_1.add(lblObrigatorio_2_2_1_1);
		
		KButton btnOK_ExcluirTransformacaoNatureza = new KButton();
		btnOK_ExcluirTransformacaoNatureza.setText("OK");
		btnOK_ExcluirTransformacaoNatureza.setOpaque(false);
		btnOK_ExcluirTransformacaoNatureza.kStartColor = new Color(254, 140, 0);
		btnOK_ExcluirTransformacaoNatureza.setkStartColor(new Color(254, 140, 0));
		btnOK_ExcluirTransformacaoNatureza.kPressedColor = new Color(179, 175, 168);
		btnOK_ExcluirTransformacaoNatureza.setkPressedColor(new Color(179, 175, 168));
		btnOK_ExcluirTransformacaoNatureza.kHoverStartColor = new Color(248, 49, 11);
		btnOK_ExcluirTransformacaoNatureza.setkHoverStartColor(new Color(248, 49, 11));
		btnOK_ExcluirTransformacaoNatureza.kHoverForeGround = Color.WHITE;
		btnOK_ExcluirTransformacaoNatureza.setkHoverForeGround(Color.WHITE);
		btnOK_ExcluirTransformacaoNatureza.kHoverEndColor = new Color(254, 126, 30);
		btnOK_ExcluirTransformacaoNatureza.setkHoverEndColor(new Color(254, 126, 30));
		btnOK_ExcluirTransformacaoNatureza.kEndColor = new Color(248, 54, 0);
		btnOK_ExcluirTransformacaoNatureza.setkEndColor(new Color(248, 54, 0));
		btnOK_ExcluirTransformacaoNatureza.setBorder(null);
		btnOK_ExcluirTransformacaoNatureza.setBounds(509, 215, 144, 32);
		gradientPanelDTransformacaoNatureza_1.add(btnOK_ExcluirTransformacaoNatureza);
		
		JLabel iconNarutoC_1_1_1_1 = new JLabel("");
		iconNarutoC_1_1_1_1.setIcon(new ImageIcon(Interface.class.getResource("/img/transformacao_natureza.png")));
		iconNarutoC_1_1_1_1.setBounds(304, 11, 96, 96);
		gradientPanelDTransformacaoNatureza_1.add(iconNarutoC_1_1_1_1);
		
		JPanel panelCObstaculoMissao = new JPanel();
		panelCObstaculoMissao.setLayout(null);
		panelCObstaculoMissao.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelCObstaculoMissao, "name_1032506185499");
		
		KGradientPanel gradientPanelCObstaculoMissao = new KGradientPanel();
		gradientPanelCObstaculoMissao.setLayout(null);
		gradientPanelCObstaculoMissao.setOpaque(false);
		gradientPanelCObstaculoMissao.kStartColor = new Color(48, 49, 52);
		gradientPanelCObstaculoMissao.setkStartColor(new Color(48, 49, 52));
		gradientPanelCObstaculoMissao.kEndColor = new Color(48, 49, 52);
		gradientPanelCObstaculoMissao.setkEndColor(new Color(48, 49, 52));
		gradientPanelCObstaculoMissao.kBorderRadius = 20;
		gradientPanelCObstaculoMissao.setkBorderRadius(20);
		gradientPanelCObstaculoMissao.setBounds(10, 11, 704, 357);
		panelCObstaculoMissao.add(gradientPanelCObstaculoMissao);
		
		JPanel panelNumeroTimeMissao = new JPanel();
		panelNumeroTimeMissao.setLayout(null);
		panelNumeroTimeMissao.setBackground(new Color(48, 49, 52));
		panelNumeroTimeMissao.setBounds(10, 100, 233, 75);
		gradientPanelCObstaculoMissao.add(panelNumeroTimeMissao);
		
		JLabel lblNumeroTimeMissao = new JLabel("N\u00FAmero do time*");
		lblNumeroTimeMissao.setForeground(Color.WHITE);
		lblNumeroTimeMissao.setBounds(47, 11, 142, 14);
		panelNumeroTimeMissao.add(lblNumeroTimeMissao);
		
		JComboBox<Integer> cbNumeroTimeObstaculo = new JComboBox<Integer>();
		cbNumeroTimeObstaculo.setBounds(47, 36, 142, 28);
		panelNumeroTimeMissao.add(cbNumeroTimeObstaculo);
		
		JPanel panelObjetivoMissao = new JPanel();
		panelObjetivoMissao.setLayout(null);
		panelObjetivoMissao.setBackground(new Color(48, 49, 52));
		panelObjetivoMissao.setBounds(10, 186, 233, 75);
		gradientPanelCObstaculoMissao.add(panelObjetivoMissao);
		
		JLabel iconObjetivoMissao = new JLabel("");
		iconObjetivoMissao.setIcon(new ImageIcon(Interface.class.getResource("/img/cone.png")));
		iconObjetivoMissao.setBounds(47, 42, 16, 16);
		panelObjetivoMissao.add(iconObjetivoMissao);
		
		JSeparator separatorObjetivoMissao = new JSeparator();
		separatorObjetivoMissao.setBounds(47, 66, 142, 2);
		panelObjetivoMissao.add(separatorObjetivoMissao);
		
		JLabel lblObjetivoMissao = new JLabel("Objetivo*");
		lblObjetivoMissao.setForeground(Color.WHITE);
		lblObjetivoMissao.setBounds(47, 11, 142, 14);
		panelObjetivoMissao.add(lblObjetivoMissao);
		
		tfObjetivoMissao = new JTextField();
		tfObjetivoMissao.setOpaque(false);
		tfObjetivoMissao.setForeground(Color.WHITE);
		tfObjetivoMissao.setColumns(10);
		tfObjetivoMissao.setCaretColor(Color.WHITE);
		tfObjetivoMissao.setBorder(null);
		tfObjetivoMissao.setBounds(71, 42, 118, 20);
		panelObjetivoMissao.add(tfObjetivoMissao);
		
		JPanel panelEpInicioMissao = new JPanel();
		panelEpInicioMissao.setLayout(null);
		panelEpInicioMissao.setBackground(new Color(48, 49, 52));
		panelEpInicioMissao.setBounds(454, 100, 233, 75);
		gradientPanelCObstaculoMissao.add(panelEpInicioMissao);
		
		JLabel iconEpInicioMissao = new JLabel("");
		iconEpInicioMissao.setIcon(new ImageIcon(Interface.class.getResource("/img/cone.png")));
		iconEpInicioMissao.setBounds(47, 42, 16, 16);
		panelEpInicioMissao.add(iconEpInicioMissao);
		
		JSeparator separatorEpInicioMissao = new JSeparator();
		separatorEpInicioMissao.setBounds(47, 66, 142, 2);
		panelEpInicioMissao.add(separatorEpInicioMissao);
		
		JLabel lblEpInicioMissao = new JLabel("Epis\u00F3dio de in\u00EDcio*");
		lblEpInicioMissao.setForeground(Color.WHITE);
		lblEpInicioMissao.setBounds(47, 11, 142, 14);
		panelEpInicioMissao.add(lblEpInicioMissao);
		
		tfEpInicioMissao = new JTextField();
		tfEpInicioMissao.setOpaque(false);
		tfEpInicioMissao.setForeground(Color.WHITE);
		tfEpInicioMissao.setColumns(10);
		tfEpInicioMissao.setCaretColor(Color.WHITE);
		tfEpInicioMissao.setBorder(null);
		tfEpInicioMissao.setBounds(73, 42, 118, 20);
		panelEpInicioMissao.add(tfEpInicioMissao);
		
		JPanel panelEpFimMissao = new JPanel();
		panelEpFimMissao.setLayout(null);
		panelEpFimMissao.setBackground(new Color(48, 49, 52));
		panelEpFimMissao.setBounds(454, 186, 233, 75);
		gradientPanelCObstaculoMissao.add(panelEpFimMissao);
		
		JLabel iconEpFimMissao = new JLabel("");
		iconEpFimMissao.setIcon(new ImageIcon(Interface.class.getResource("/img/cone.png")));
		iconEpFimMissao.setBounds(47, 42, 16, 16);
		panelEpFimMissao.add(iconEpFimMissao);
		
		JSeparator separatorEpFimMissao = new JSeparator();
		separatorEpFimMissao.setBounds(47, 66, 142, 2);
		panelEpFimMissao.add(separatorEpFimMissao);
		
		JLabel lblEpFimMissao = new JLabel("Epis\u00F3dio de fim*");
		lblEpFimMissao.setForeground(Color.WHITE);
		lblEpFimMissao.setBounds(47, 11, 142, 14);
		panelEpFimMissao.add(lblEpFimMissao);
		
		tfEpFimMissao = new JTextField();
		tfEpFimMissao.setOpaque(false);
		tfEpFimMissao.setForeground(Color.WHITE);
		tfEpFimMissao.setColumns(10);
		tfEpFimMissao.setCaretColor(Color.WHITE);
		tfEpFimMissao.setBorder(null);
		tfEpFimMissao.setBounds(73, 42, 118, 20);
		panelEpFimMissao.add(tfEpFimMissao);
		
		JPanel panelObstaculoMissao = new JPanel();
		panelObstaculoMissao.setLayout(null);
		panelObstaculoMissao.setBackground(new Color(48, 49, 52));
		panelObstaculoMissao.setBounds(10, 272, 233, 75);
		gradientPanelCObstaculoMissao.add(panelObstaculoMissao);
		
		JLabel iconObstaculoMissao = new JLabel("");
		iconObstaculoMissao.setIcon(new ImageIcon(Interface.class.getResource("/img/cone.png")));
		iconObstaculoMissao.setBounds(47, 42, 16, 16);
		panelObstaculoMissao.add(iconObstaculoMissao);
		
		JSeparator separatorObstaculoMissao = new JSeparator();
		separatorObstaculoMissao.setBounds(47, 66, 142, 2);
		panelObstaculoMissao.add(separatorObstaculoMissao);
		
		JLabel lblObstaculoMissao = new JLabel("Obst\u00E1culo*");
		lblObstaculoMissao.setForeground(Color.WHITE);
		lblObstaculoMissao.setBounds(47, 11, 142, 14);
		panelObstaculoMissao.add(lblObstaculoMissao);
		
		tfObstaculoMissao = new JTextField();
		tfObstaculoMissao.setOpaque(false);
		tfObstaculoMissao.setForeground(Color.WHITE);
		tfObstaculoMissao.setColumns(10);
		tfObstaculoMissao.setCaretColor(Color.WHITE);
		tfObstaculoMissao.setBorder(null);
		tfObstaculoMissao.setBounds(71, 42, 118, 20);
		panelObstaculoMissao.add(tfObstaculoMissao);
		
		KButton btnAdicionarObstaculoMissao = new KButton();
		btnAdicionarObstaculoMissao.setText("Adicionar");
		btnAdicionarObstaculoMissao.setOpaque(false);
		btnAdicionarObstaculoMissao.kStartColor = new Color(254, 140, 0);
		btnAdicionarObstaculoMissao.setkStartColor(new Color(254, 140, 0));
		btnAdicionarObstaculoMissao.kPressedColor = new Color(179, 175, 168);
		btnAdicionarObstaculoMissao.setkPressedColor(new Color(179, 175, 168));
		btnAdicionarObstaculoMissao.kHoverStartColor = new Color(248, 49, 11);
		btnAdicionarObstaculoMissao.setkHoverStartColor(new Color(248, 49, 11));
		btnAdicionarObstaculoMissao.kHoverForeGround = Color.WHITE;
		btnAdicionarObstaculoMissao.setkHoverForeGround(Color.WHITE);
		btnAdicionarObstaculoMissao.kHoverEndColor = new Color(254, 126, 30);
		btnAdicionarObstaculoMissao.setkHoverEndColor(new Color(254, 126, 30));
		btnAdicionarObstaculoMissao.kEndColor = new Color(248, 54, 0);
		btnAdicionarObstaculoMissao.setkEndColor(new Color(248, 54, 0));
		btnAdicionarObstaculoMissao.setBorder(null);
		btnAdicionarObstaculoMissao.setBounds(507, 312, 144, 32);
		gradientPanelCObstaculoMissao.add(btnAdicionarObstaculoMissao);
		
		JLabel lblObrigatorio_3_1 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_3_1.setForeground(Color.WHITE);
		lblObrigatorio_3_1.setBounds(588, 287, 75, 14);
		gradientPanelCObstaculoMissao.add(lblObrigatorio_3_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Interface.class.getResource("/img/image (3) (5).png")));
		lblNewLabel_2.setBounds(284, 11, 135, 88);
		gradientPanelCObstaculoMissao.add(lblNewLabel_2);
		
		JPanel panelRObstaculoMissao = new JPanel();
		panelRObstaculoMissao.setLayout(null);
		panelRObstaculoMissao.setOpaque(false);
		panelRObstaculoMissao.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelRObstaculoMissao, "name_1844976014600");
		
		KGradientPanel gradientPanelRObstaculoMissao = new KGradientPanel();
		gradientPanelRObstaculoMissao.setLayout(null);
		gradientPanelRObstaculoMissao.setOpaque(false);
		gradientPanelRObstaculoMissao.kStartColor = new Color(48, 49, 52);
		gradientPanelRObstaculoMissao.setkStartColor(new Color(48, 49, 52));
		gradientPanelRObstaculoMissao.kEndColor = new Color(48, 49, 52);
		gradientPanelRObstaculoMissao.setkEndColor(new Color(48, 49, 52));
		gradientPanelRObstaculoMissao.kBorderRadius = 20;
		gradientPanelRObstaculoMissao.setkBorderRadius(20);
		gradientPanelRObstaculoMissao.setBounds(10, 11, 704, 357);
		panelRObstaculoMissao.add(gradientPanelRObstaculoMissao);
		
		JLabel iconTime_2_2_1_1 = new JLabel("");
		iconTime_2_2_1_1.setIcon(new ImageIcon(Interface.class.getResource("/img/image (3) (5).png")));
		iconTime_2_2_1_1.setBounds(284, 11, 135, 88);
		gradientPanelRObstaculoMissao.add(iconTime_2_2_1_1);
		
		JScrollPane scrollPaneObstaculoMissao = new JScrollPane();
		scrollPaneObstaculoMissao.setBounds(10, 112, 684, 234);
		gradientPanelRObstaculoMissao.add(scrollPaneObstaculoMissao);
		
		tableObstaculoMissao = new JTable();
		
		tableObstaculoMissao.setBorder(null);
		tableObstaculoMissao.setFocusable(false);
		tableObstaculoMissao.setSelectionBackground(new Color(130, 130, 130));
		tableObstaculoMissao.setBackground(new Color(130, 130, 130));
		tableObstaculoMissao.setGridColor(new Color(151, 151, 151));
		tableObstaculoMissao.setForeground(Color.WHITE);
		
		tableObstaculoMissao.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N\u00B0 do time", "Objetivo da miss\u00E3o", "Ep. In\u00EDcio", "Ep. Fim", "Obst\u00E1culo"
			}
		));
		tableObstaculoMissao.getColumnModel().getColumn(0).setPreferredWidth(65);
		tableObstaculoMissao.getColumnModel().getColumn(1).setPreferredWidth(251);
		tableObstaculoMissao.getColumnModel().getColumn(2).setPreferredWidth(55);
		tableObstaculoMissao.getColumnModel().getColumn(3).setPreferredWidth(47);
		tableObstaculoMissao.getColumnModel().getColumn(4).setPreferredWidth(204);
		
		tableObstaculoMissao.getColumnModel().getColumn(0).setHeaderRenderer(new header(scrollPaneObstaculoMissao));
		tableObstaculoMissao.getColumnModel().getColumn(1).setHeaderRenderer(new header(scrollPaneObstaculoMissao));
		tableObstaculoMissao.getColumnModel().getColumn(2).setHeaderRenderer(new header(scrollPaneObstaculoMissao));
		tableObstaculoMissao.getColumnModel().getColumn(3).setHeaderRenderer(new header(scrollPaneObstaculoMissao));
		tableObstaculoMissao.getColumnModel().getColumn(4).setHeaderRenderer(new header(scrollPaneObstaculoMissao));
		tableObstaculoMissao.setDefaultRenderer(Object.class, new cell());
		
		scrollPaneObstaculoMissao.setViewportView(tableObstaculoMissao);
		
		JPanel panelDObstaculoMissao = new JPanel();
		panelDObstaculoMissao.setLayout(null);
		panelDObstaculoMissao.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelDObstaculoMissao, "name_2434236914800");
		
		KGradientPanel gradientPanelDObstaculoMissao = new KGradientPanel();
		gradientPanelDObstaculoMissao.setLayout(null);
		gradientPanelDObstaculoMissao.setOpaque(false);
		gradientPanelDObstaculoMissao.kStartColor = new Color(48, 49, 52);
		gradientPanelDObstaculoMissao.setkStartColor(new Color(48, 49, 52));
		gradientPanelDObstaculoMissao.kEndColor = new Color(48, 49, 52);
		gradientPanelDObstaculoMissao.setkEndColor(new Color(48, 49, 52));
		gradientPanelDObstaculoMissao.kBorderRadius = 20;
		gradientPanelDObstaculoMissao.setkBorderRadius(20);
		gradientPanelDObstaculoMissao.setBounds(10, 11, 704, 357);
		panelDObstaculoMissao.add(gradientPanelDObstaculoMissao);
		
		JPanel panelNumeroTimeMissaoExcluir = new JPanel();
		panelNumeroTimeMissaoExcluir.setLayout(null);
		panelNumeroTimeMissaoExcluir.setBackground(new Color(48, 49, 52));
		panelNumeroTimeMissaoExcluir.setBounds(10, 100, 233, 75);
		gradientPanelDObstaculoMissao.add(panelNumeroTimeMissaoExcluir);
		
		JLabel lblNumeroTimeMissaoExcluir = new JLabel("N\u00FAmero do time*");
		lblNumeroTimeMissaoExcluir.setForeground(Color.WHITE);
		lblNumeroTimeMissaoExcluir.setBounds(47, 11, 142, 14);
		panelNumeroTimeMissaoExcluir.add(lblNumeroTimeMissaoExcluir);
		
		JComboBox<Integer> cbNumeroTimeObstaculoExcluir = new JComboBox<Integer>();
		cbNumeroTimeObstaculoExcluir.setBounds(47, 36, 142, 28);
		panelNumeroTimeMissaoExcluir.add(cbNumeroTimeObstaculoExcluir);
		
		JPanel panelObjetivoMissaoExcluir = new JPanel();
		panelObjetivoMissaoExcluir.setLayout(null);
		panelObjetivoMissaoExcluir.setBackground(new Color(48, 49, 52));
		panelObjetivoMissaoExcluir.setBounds(10, 186, 233, 75);
		gradientPanelDObstaculoMissao.add(panelObjetivoMissaoExcluir);
		
		JLabel iconObjetivoMissaoExcluir = new JLabel("");
		iconObjetivoMissaoExcluir.setIcon(new ImageIcon(Interface.class.getResource("/img/cone.png")));
		iconObjetivoMissaoExcluir.setBounds(47, 42, 16, 16);
		panelObjetivoMissaoExcluir.add(iconObjetivoMissaoExcluir);
		
		JSeparator separatorObjetivoMissaoExcluir = new JSeparator();
		separatorObjetivoMissaoExcluir.setBounds(47, 66, 142, 2);
		panelObjetivoMissaoExcluir.add(separatorObjetivoMissaoExcluir);
		
		JLabel lblObjetivoMissaoExcluir = new JLabel("Objetivo*");
		lblObjetivoMissaoExcluir.setForeground(Color.WHITE);
		lblObjetivoMissaoExcluir.setBounds(47, 11, 142, 14);
		panelObjetivoMissaoExcluir.add(lblObjetivoMissaoExcluir);
		
		tfObjetivoMissaoExcluir = new JTextField();
		tfObjetivoMissaoExcluir.setOpaque(false);
		tfObjetivoMissaoExcluir.setForeground(Color.WHITE);
		tfObjetivoMissaoExcluir.setColumns(10);
		tfObjetivoMissaoExcluir.setCaretColor(Color.WHITE);
		tfObjetivoMissaoExcluir.setBorder(null);
		tfObjetivoMissaoExcluir.setBounds(71, 42, 118, 20);
		panelObjetivoMissaoExcluir.add(tfObjetivoMissaoExcluir);
		
		JPanel panelEpInicioMissaoExcluir = new JPanel();
		panelEpInicioMissaoExcluir.setLayout(null);
		panelEpInicioMissaoExcluir.setBackground(new Color(48, 49, 52));
		panelEpInicioMissaoExcluir.setBounds(454, 100, 233, 75);
		gradientPanelDObstaculoMissao.add(panelEpInicioMissaoExcluir);
		
		JLabel iconEpInicioMissaoExcluir = new JLabel("");
		iconEpInicioMissaoExcluir.setIcon(new ImageIcon(Interface.class.getResource("/img/cone.png")));
		iconEpInicioMissaoExcluir.setBounds(47, 42, 16, 16);
		panelEpInicioMissaoExcluir.add(iconEpInicioMissaoExcluir);
		
		JSeparator separatorEpInicioMissaoExcluir = new JSeparator();
		separatorEpInicioMissaoExcluir.setBounds(47, 66, 142, 2);
		panelEpInicioMissaoExcluir.add(separatorEpInicioMissaoExcluir);
		
		JLabel lblEpInicioMissaoExcluir = new JLabel("Epis\u00F3dio de in\u00EDcio*");
		lblEpInicioMissaoExcluir.setForeground(Color.WHITE);
		lblEpInicioMissaoExcluir.setBounds(47, 11, 142, 14);
		panelEpInicioMissaoExcluir.add(lblEpInicioMissaoExcluir);
		
		tfEpInicioMissaoExcluir = new JTextField();
		tfEpInicioMissaoExcluir.setOpaque(false);
		tfEpInicioMissaoExcluir.setForeground(Color.WHITE);
		tfEpInicioMissaoExcluir.setColumns(10);
		tfEpInicioMissaoExcluir.setCaretColor(Color.WHITE);
		tfEpInicioMissaoExcluir.setBorder(null);
		tfEpInicioMissaoExcluir.setBounds(73, 42, 118, 20);
		panelEpInicioMissaoExcluir.add(tfEpInicioMissaoExcluir);
		
		JPanel panelEpFimMissaoExcluir = new JPanel();
		panelEpFimMissaoExcluir.setLayout(null);
		panelEpFimMissaoExcluir.setBackground(new Color(48, 49, 52));
		panelEpFimMissaoExcluir.setBounds(454, 186, 233, 75);
		gradientPanelDObstaculoMissao.add(panelEpFimMissaoExcluir);
		
		JLabel iconEpFimMissaoExcluir = new JLabel("");
		iconEpFimMissaoExcluir.setIcon(new ImageIcon(Interface.class.getResource("/img/cone.png")));
		iconEpFimMissaoExcluir.setBounds(47, 42, 16, 16);
		panelEpFimMissaoExcluir.add(iconEpFimMissaoExcluir);
		
		JSeparator separatorEpFimMissaoExcluir = new JSeparator();
		separatorEpFimMissaoExcluir.setBounds(47, 66, 142, 2);
		panelEpFimMissaoExcluir.add(separatorEpFimMissaoExcluir);
		
		JLabel lblEpFimMissaoExcluir = new JLabel("Epis\u00F3dio de fim*");
		lblEpFimMissaoExcluir.setForeground(Color.WHITE);
		lblEpFimMissaoExcluir.setBounds(47, 11, 142, 14);
		panelEpFimMissaoExcluir.add(lblEpFimMissaoExcluir);
		
		tfEpFimMissaoExcluir = new JTextField();
		tfEpFimMissaoExcluir.setOpaque(false);
		tfEpFimMissaoExcluir.setForeground(Color.WHITE);
		tfEpFimMissaoExcluir.setColumns(10);
		tfEpFimMissaoExcluir.setCaretColor(Color.WHITE);
		tfEpFimMissaoExcluir.setBorder(null);
		tfEpFimMissaoExcluir.setBounds(73, 42, 118, 20);
		panelEpFimMissaoExcluir.add(tfEpFimMissaoExcluir);
		
		JPanel panelObstaculoMissaoExcluir = new JPanel();
		panelObstaculoMissaoExcluir.setLayout(null);
		panelObstaculoMissaoExcluir.setBackground(new Color(48, 49, 52));
		panelObstaculoMissaoExcluir.setBounds(10, 272, 233, 75);
		gradientPanelDObstaculoMissao.add(panelObstaculoMissaoExcluir);
		
		JLabel iconObstaculoMissaoExcluir = new JLabel("");
		iconObstaculoMissaoExcluir.setIcon(new ImageIcon(Interface.class.getResource("/img/cone.png")));
		iconObstaculoMissaoExcluir.setBounds(47, 42, 16, 16);
		panelObstaculoMissaoExcluir.add(iconObstaculoMissaoExcluir);
		
		JSeparator separatorObstaculoMissaoExcluir = new JSeparator();
		separatorObstaculoMissaoExcluir.setBounds(47, 66, 142, 2);
		panelObstaculoMissaoExcluir.add(separatorObstaculoMissaoExcluir);
		
		JLabel lblObstaculoMissaoExcluir = new JLabel("Obst\u00E1culo*");
		lblObstaculoMissaoExcluir.setForeground(Color.WHITE);
		lblObstaculoMissaoExcluir.setBounds(47, 11, 142, 14);
		panelObstaculoMissaoExcluir.add(lblObstaculoMissaoExcluir);
		
		tfObstaculoMissaoExcluir = new JTextField();
		tfObstaculoMissaoExcluir.setOpaque(false);
		tfObstaculoMissaoExcluir.setForeground(Color.WHITE);
		tfObstaculoMissaoExcluir.setColumns(10);
		tfObstaculoMissaoExcluir.setCaretColor(Color.WHITE);
		tfObstaculoMissaoExcluir.setBorder(null);
		tfObstaculoMissaoExcluir.setBounds(71, 42, 118, 20);
		panelObstaculoMissaoExcluir.add(tfObstaculoMissaoExcluir);
		
		KButton btnOK_ExcluirObstaculoMissao = new KButton();
		btnOK_ExcluirObstaculoMissao.setText("OK");
		btnOK_ExcluirObstaculoMissao.setOpaque(false);
		btnOK_ExcluirObstaculoMissao.kStartColor = new Color(254, 140, 0);
		btnOK_ExcluirObstaculoMissao.setkStartColor(new Color(254, 140, 0));
		btnOK_ExcluirObstaculoMissao.kPressedColor = new Color(179, 175, 168);
		btnOK_ExcluirObstaculoMissao.setkPressedColor(new Color(179, 175, 168));
		btnOK_ExcluirObstaculoMissao.kHoverStartColor = new Color(248, 49, 11);
		btnOK_ExcluirObstaculoMissao.setkHoverStartColor(new Color(248, 49, 11));
		btnOK_ExcluirObstaculoMissao.kHoverForeGround = Color.WHITE;
		btnOK_ExcluirObstaculoMissao.setkHoverForeGround(Color.WHITE);
		btnOK_ExcluirObstaculoMissao.kHoverEndColor = new Color(254, 126, 30);
		btnOK_ExcluirObstaculoMissao.setkHoverEndColor(new Color(254, 126, 30));
		btnOK_ExcluirObstaculoMissao.kEndColor = new Color(248, 54, 0);
		btnOK_ExcluirObstaculoMissao.setkEndColor(new Color(248, 54, 0));
		btnOK_ExcluirObstaculoMissao.setBorder(null);
		btnOK_ExcluirObstaculoMissao.setBounds(507, 312, 144, 32);
		gradientPanelDObstaculoMissao.add(btnOK_ExcluirObstaculoMissao);
		
		JLabel lblObrigatorio_3_1_1 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_3_1_1.setForeground(Color.WHITE);
		lblObrigatorio_3_1_1.setBounds(588, 287, 75, 14);
		gradientPanelDObstaculoMissao.add(lblObrigatorio_3_1_1);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Interface.class.getResource("/img/image (3) (5).png")));
		lblNewLabel_3.setBounds(284, 11, 135, 88);
		gradientPanelDObstaculoMissao.add(lblNewLabel_3);
		
		JPanel panelCParticipacao = new JPanel();
		panelCParticipacao.setLayout(null);
		panelCParticipacao.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelCParticipacao, "name_10855397087700");
		
		KGradientPanel gradientPanelCParticipacao = new KGradientPanel();
		gradientPanelCParticipacao.setLayout(null);
		gradientPanelCParticipacao.setOpaque(false);
		gradientPanelCParticipacao.kStartColor = new Color(48, 49, 52);
		gradientPanelCParticipacao.setkStartColor(new Color(48, 49, 52));
		gradientPanelCParticipacao.kEndColor = new Color(48, 49, 52);
		gradientPanelCParticipacao.setkEndColor(new Color(48, 49, 52));
		gradientPanelCParticipacao.kBorderRadius = 20;
		gradientPanelCParticipacao.setkBorderRadius(20);
		gradientPanelCParticipacao.setBounds(10, 11, 704, 357);
		panelCParticipacao.add(gradientPanelCParticipacao);
		
		JPanel panelNomePersonagemParticipacao = new JPanel();
		panelNomePersonagemParticipacao.setLayout(null);
		panelNomePersonagemParticipacao.setBackground(new Color(48, 49, 52));
		panelNomePersonagemParticipacao.setBounds(10, 100, 233, 75);
		gradientPanelCParticipacao.add(panelNomePersonagemParticipacao);
		
		JLabel lblNomePersonagemParticipacao = new JLabel("Nome do personagem*");
		lblNomePersonagemParticipacao.setForeground(Color.WHITE);
		lblNomePersonagemParticipacao.setBounds(47, 11, 142, 14);
		panelNomePersonagemParticipacao.add(lblNomePersonagemParticipacao);
		
		JComboBox<String> cbNomePersonagemParticipacao = new JComboBox<String>();
		cbNomePersonagemParticipacao.setBounds(47, 36, 142, 28);
		panelNomePersonagemParticipacao.add(cbNomePersonagemParticipacao);
		
		JPanel panelNumeroTimeParticipacao = new JPanel();
		panelNumeroTimeParticipacao.setLayout(null);
		panelNumeroTimeParticipacao.setBackground(new Color(48, 49, 52));
		panelNumeroTimeParticipacao.setBounds(10, 186, 233, 75);
		gradientPanelCParticipacao.add(panelNumeroTimeParticipacao);
		
		JLabel lblNumeroTimeParticipacao = new JLabel("N\u00FAmero do time*");
		lblNumeroTimeParticipacao.setForeground(Color.WHITE);
		lblNumeroTimeParticipacao.setBounds(47, 11, 142, 14);
		panelNumeroTimeParticipacao.add(lblNumeroTimeParticipacao);
		
		JComboBox<Integer> cbNumeroTimeParticipacao = new JComboBox<Integer>();
		cbNumeroTimeParticipacao.setBounds(47, 36, 142, 28);
		panelNumeroTimeParticipacao.add(cbNumeroTimeParticipacao);
		
		JPanel panelEpIngressoParticipacao = new JPanel();
		panelEpIngressoParticipacao.setLayout(null);
		panelEpIngressoParticipacao.setBackground(new Color(48, 49, 52));
		panelEpIngressoParticipacao.setBounds(454, 100, 233, 75);
		gradientPanelCParticipacao.add(panelEpIngressoParticipacao);
		
		JLabel iconEpIngressoParticipacao = new JLabel("");
		iconEpIngressoParticipacao.setIcon(new ImageIcon(Interface.class.getResource("/img/teamwork.png")));
		iconEpIngressoParticipacao.setBounds(47, 42, 16, 16);
		panelEpIngressoParticipacao.add(iconEpIngressoParticipacao);
		
		JSeparator separatorEpIngressoParticipacao = new JSeparator();
		separatorEpIngressoParticipacao.setBounds(47, 66, 142, 2);
		panelEpIngressoParticipacao.add(separatorEpIngressoParticipacao);
		
		JLabel lblEpIngressoParticipacao = new JLabel("Epis\u00F3dio de ingresso");
		lblEpIngressoParticipacao.setForeground(Color.WHITE);
		lblEpIngressoParticipacao.setBounds(47, 11, 142, 14);
		panelEpIngressoParticipacao.add(lblEpIngressoParticipacao);
		
		tfEpIngressoParticipacao = new JTextField();
		tfEpIngressoParticipacao.setOpaque(false);
		tfEpIngressoParticipacao.setForeground(Color.WHITE);
		tfEpIngressoParticipacao.setColumns(10);
		tfEpIngressoParticipacao.setCaretColor(Color.WHITE);
		tfEpIngressoParticipacao.setBorder(null);
		tfEpIngressoParticipacao.setBounds(73, 42, 118, 20);
		panelEpIngressoParticipacao.add(tfEpIngressoParticipacao);
		
		JPanel panelEpSaidaParticipacao = new JPanel();
		panelEpSaidaParticipacao.setLayout(null);
		panelEpSaidaParticipacao.setBackground(new Color(48, 49, 52));
		panelEpSaidaParticipacao.setBounds(454, 186, 233, 75);
		gradientPanelCParticipacao.add(panelEpSaidaParticipacao);
		
		JLabel iconEpSaidaParticipacao = new JLabel("");
		iconEpSaidaParticipacao.setIcon(new ImageIcon(Interface.class.getResource("/img/teamwork.png")));
		iconEpSaidaParticipacao.setBounds(47, 42, 16, 16);
		panelEpSaidaParticipacao.add(iconEpSaidaParticipacao);
		
		JSeparator separatorEpSaidaParticipacao = new JSeparator();
		separatorEpSaidaParticipacao.setBounds(47, 66, 142, 2);
		panelEpSaidaParticipacao.add(separatorEpSaidaParticipacao);
		
		JLabel lblEpSaidaParticipacao = new JLabel("Epis\u00F3dio de sa\u00EDda");
		lblEpSaidaParticipacao.setForeground(Color.WHITE);
		lblEpSaidaParticipacao.setBounds(47, 11, 142, 14);
		panelEpSaidaParticipacao.add(lblEpSaidaParticipacao);
		
		tfEpSaidaParticipacao = new JTextField();
		tfEpSaidaParticipacao.setOpaque(false);
		tfEpSaidaParticipacao.setForeground(Color.WHITE);
		tfEpSaidaParticipacao.setColumns(10);
		tfEpSaidaParticipacao.setCaretColor(Color.WHITE);
		tfEpSaidaParticipacao.setBorder(null);
		tfEpSaidaParticipacao.setBounds(73, 42, 118, 20);
		panelEpSaidaParticipacao.add(tfEpSaidaParticipacao);
		
		JPanel panelFuncaoParticipacao = new JPanel();
		panelFuncaoParticipacao.setLayout(null);
		panelFuncaoParticipacao.setBackground(new Color(48, 49, 52));
		panelFuncaoParticipacao.setBounds(10, 272, 233, 75);
		gradientPanelCParticipacao.add(panelFuncaoParticipacao);
		
		JLabel lblFuncaoParticipacao = new JLabel("Fun\u00E7\u00E3o");
		lblFuncaoParticipacao.setForeground(Color.WHITE);
		lblFuncaoParticipacao.setBounds(47, 11, 142, 14);
		panelFuncaoParticipacao.add(lblFuncaoParticipacao);
		
		JComboBox<String> cbFuncaoParticipacao = new JComboBox<String>();
		cbFuncaoParticipacao.setBackground(Color.GRAY);
		cbFuncaoParticipacao.setForeground(Color.BLACK);
		cbFuncaoParticipacao.setModel(new DefaultComboBoxModel<String>(new String[] {"-", "Regular", "Substituto", "L\u00EDder"}));
		cbFuncaoParticipacao.setBounds(47, 36, 142, 28);
		panelFuncaoParticipacao.add(cbFuncaoParticipacao);
		
		KButton btnAdicionarParticipacao = new KButton();
		btnAdicionarParticipacao.setText("Adicionar");
		btnAdicionarParticipacao.setOpaque(false);
		btnAdicionarParticipacao.kStartColor = new Color(254, 140, 0);
		btnAdicionarParticipacao.setkStartColor(new Color(254, 140, 0));
		btnAdicionarParticipacao.kPressedColor = new Color(179, 175, 168);
		btnAdicionarParticipacao.setkPressedColor(new Color(179, 175, 168));
		btnAdicionarParticipacao.kHoverStartColor = new Color(248, 49, 11);
		btnAdicionarParticipacao.setkHoverStartColor(new Color(248, 49, 11));
		btnAdicionarParticipacao.kHoverForeGround = Color.WHITE;
		btnAdicionarParticipacao.setkHoverForeGround(Color.WHITE);
		btnAdicionarParticipacao.kHoverEndColor = new Color(254, 126, 30);
		btnAdicionarParticipacao.setkHoverEndColor(new Color(254, 126, 30));
		btnAdicionarParticipacao.kEndColor = new Color(248, 54, 0);
		btnAdicionarParticipacao.setkEndColor(new Color(248, 54, 0));
		btnAdicionarParticipacao.setBorder(null);
		btnAdicionarParticipacao.setBounds(507, 312, 144, 32);
		gradientPanelCParticipacao.add(btnAdicionarParticipacao);
		
		JLabel lblObrigatorio_3_1_2 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_3_1_2.setForeground(Color.WHITE);
		lblObrigatorio_3_1_2.setBounds(588, 287, 75, 14);
		gradientPanelCParticipacao.add(lblObrigatorio_3_1_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("");
		lblNewLabel_2_1.setIcon(new ImageIcon(Interface.class.getResource("/img/npt (1).png")));
		lblNewLabel_2_1.setBounds(306, 11, 92, 92);
		gradientPanelCParticipacao.add(lblNewLabel_2_1);
		
		JPanel panelRParticipacao = new JPanel();
		panelRParticipacao.setLayout(null);
		panelRParticipacao.setOpaque(false);
		panelRParticipacao.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelRParticipacao, "name_11331691910300");
		
		KGradientPanel gradientPanelRParticipacao = new KGradientPanel();
		gradientPanelRParticipacao.setLayout(null);
		gradientPanelRParticipacao.setOpaque(false);
		gradientPanelRParticipacao.kStartColor = new Color(48, 49, 52);
		gradientPanelRParticipacao.setkStartColor(new Color(48, 49, 52));
		gradientPanelRParticipacao.kEndColor = new Color(48, 49, 52);
		gradientPanelRParticipacao.setkEndColor(new Color(48, 49, 52));
		gradientPanelRParticipacao.kBorderRadius = 20;
		gradientPanelRParticipacao.setkBorderRadius(20);
		gradientPanelRParticipacao.setBounds(10, 11, 704, 357);
		panelRParticipacao.add(gradientPanelRParticipacao);
		
		JLabel iconTime_2_2_2 = new JLabel("");
		iconTime_2_2_2.setIcon(new ImageIcon(Interface.class.getResource("/img/npt (1).png")));
		iconTime_2_2_2.setBounds(306, 11, 92, 92);
		gradientPanelRParticipacao.add(iconTime_2_2_2);
		
		JScrollPane scrollPaneParticipacao = new JScrollPane();
		scrollPaneParticipacao.setBounds(10, 112, 684, 234);
		gradientPanelRParticipacao.add(scrollPaneParticipacao);
		
		tableParticipacao = new JTable();
		tableParticipacao.setBorder(null);
		tableParticipacao.setFocusable(false);
		tableParticipacao.setSelectionBackground(new Color(130, 130, 130));
		tableParticipacao.setBackground(new Color(130, 130, 130));
		tableParticipacao.setGridColor(new Color(151, 151, 151));
		tableParticipacao.setForeground(Color.WHITE);
		tableParticipacao.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "N\u00B0 do time", "Fun\u00E7\u00E3o", "Ep. ingresso", "Ep. sa\u00EDda"
			}
		) {
			Class<?>[] columnTypes = new Class[] {
				String.class, Integer.class, String.class, String.class, String.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableParticipacao.getColumnModel().getColumn(0).setPreferredWidth(136);
		tableParticipacao.getColumnModel().getColumn(2).setPreferredWidth(108);
		tableParticipacao.setDefaultRenderer(Object.class, new cell());
		
		scrollPaneParticipacao.setViewportView(tableParticipacao);
		
		JPanel panelUParticipacao = new JPanel();
		panelUParticipacao.setLayout(null);
		panelUParticipacao.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelUParticipacao, "name_13379450311600");
		
		KGradientPanel gradientPanelUParticipacao = new KGradientPanel();
		gradientPanelUParticipacao.setLayout(null);
		gradientPanelUParticipacao.setOpaque(false);
		gradientPanelUParticipacao.kStartColor = new Color(48, 49, 52);
		gradientPanelUParticipacao.setkStartColor(new Color(48, 49, 52));
		gradientPanelUParticipacao.kEndColor = new Color(48, 49, 52);
		gradientPanelUParticipacao.setkEndColor(new Color(48, 49, 52));
		gradientPanelUParticipacao.kBorderRadius = 20;
		gradientPanelUParticipacao.setkBorderRadius(20);
		gradientPanelUParticipacao.setBounds(10, 11, 704, 357);
		panelUParticipacao.add(gradientPanelUParticipacao);
		
		JPanel panelNomePersonagemEditarParticipacao = new JPanel();
		panelNomePersonagemEditarParticipacao.setLayout(null);
		panelNomePersonagemEditarParticipacao.setBackground(new Color(48, 49, 52));
		panelNomePersonagemEditarParticipacao.setBounds(10, 106, 233, 75);
		gradientPanelUParticipacao.add(panelNomePersonagemEditarParticipacao);
		
		JLabel lblNomePersonagemEditarParticipacao = new JLabel("Nome do personagem*");
		lblNomePersonagemEditarParticipacao.setForeground(Color.WHITE);
		lblNomePersonagemEditarParticipacao.setBounds(47, 11, 142, 14);
		panelNomePersonagemEditarParticipacao.add(lblNomePersonagemEditarParticipacao);
		
		JComboBox<String> cbNomePersonagemParticipacaoEditar = new JComboBox<String>();
		cbNomePersonagemParticipacaoEditar.setBounds(47, 36, 142, 22);
		panelNomePersonagemEditarParticipacao.add(cbNomePersonagemParticipacaoEditar);
		
		JPanel panelNumeroTimeEditarParticipacao = new JPanel();
		panelNumeroTimeEditarParticipacao.setLayout(null);
		panelNumeroTimeEditarParticipacao.setBackground(new Color(48, 49, 52));
		panelNumeroTimeEditarParticipacao.setBounds(461, 106, 233, 75);
		gradientPanelUParticipacao.add(panelNumeroTimeEditarParticipacao);
		
		JLabel lblNumeroTimeEditarParticipacao = new JLabel("N\u00FAmero do time*");
		lblNumeroTimeEditarParticipacao.setForeground(Color.WHITE);
		lblNumeroTimeEditarParticipacao.setBounds(47, 11, 176, 14);
		panelNumeroTimeEditarParticipacao.add(lblNumeroTimeEditarParticipacao);
		
		JComboBox<Integer> cbNumeroTimeParticipacaoEditar = new JComboBox<Integer>();
		cbNumeroTimeParticipacaoEditar.setBounds(47, 36, 129, 28);
		panelNumeroTimeEditarParticipacao.add(cbNumeroTimeParticipacaoEditar);
		
		JLabel lblObrigatorio_2_2_1_2 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_2_2_1_2.setForeground(Color.WHITE);
		lblObrigatorio_2_2_1_2.setBounds(47, 332, 75, 14);
		gradientPanelUParticipacao.add(lblObrigatorio_2_2_1_2);
		
		KButton btnOK_EditarParticipacao = new KButton();
		btnOK_EditarParticipacao.setText("OK");
		btnOK_EditarParticipacao.setOpaque(false);
		btnOK_EditarParticipacao.kStartColor = new Color(254, 140, 0);
		btnOK_EditarParticipacao.setkStartColor(new Color(254, 140, 0));
		btnOK_EditarParticipacao.kPressedColor = new Color(179, 175, 168);
		btnOK_EditarParticipacao.setkPressedColor(new Color(179, 175, 168));
		btnOK_EditarParticipacao.kHoverStartColor = new Color(248, 49, 11);
		btnOK_EditarParticipacao.setkHoverStartColor(new Color(248, 49, 11));
		btnOK_EditarParticipacao.kHoverForeGround = Color.WHITE;
		btnOK_EditarParticipacao.setkHoverForeGround(Color.WHITE);
		btnOK_EditarParticipacao.kHoverEndColor = new Color(254, 126, 30);
		btnOK_EditarParticipacao.setkHoverEndColor(new Color(254, 126, 30));
		btnOK_EditarParticipacao.kEndColor = new Color(248, 54, 0);
		btnOK_EditarParticipacao.setkEndColor(new Color(248, 54, 0));
		btnOK_EditarParticipacao.setBorder(null);
		btnOK_EditarParticipacao.setBounds(509, 215, 144, 32);
		gradientPanelUParticipacao.add(btnOK_EditarParticipacao);
		
		JLabel iconNarutoC_1_1_1_2 = new JLabel("");
		iconNarutoC_1_1_1_2.setIcon(new ImageIcon(Interface.class.getResource("/img/npt (1).png")));
		iconNarutoC_1_1_1_2.setBounds(306, 11, 92, 92);
		gradientPanelUParticipacao.add(iconNarutoC_1_1_1_2);
		
		JPanel panelUParticipacao_1 = new JPanel();
		panelUParticipacao_1.setLayout(null);
		panelUParticipacao_1.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelUParticipacao_1, "name_13674769852500");
		
		KGradientPanel gradientPanelUParticipacao_1 = new KGradientPanel();
		gradientPanelUParticipacao_1.setLayout(null);
		gradientPanelUParticipacao_1.setOpaque(false);
		gradientPanelUParticipacao_1.kStartColor = new Color(48, 49, 52);
		gradientPanelUParticipacao_1.setkStartColor(new Color(48, 49, 52));
		gradientPanelUParticipacao_1.kEndColor = new Color(48, 49, 52);
		gradientPanelUParticipacao_1.setkEndColor(new Color(48, 49, 52));
		gradientPanelUParticipacao_1.kBorderRadius = 20;
		gradientPanelUParticipacao_1.setkBorderRadius(20);
		gradientPanelUParticipacao_1.setBounds(10, 11, 704, 357);
		panelUParticipacao_1.add(gradientPanelUParticipacao_1);
		
		JPanel panelNomePersonagemParticipacao_1 = new JPanel();
		panelNomePersonagemParticipacao_1.setLayout(null);
		panelNomePersonagemParticipacao_1.setBackground(new Color(48, 49, 52));
		panelNomePersonagemParticipacao_1.setBounds(10, 100, 233, 75);
		gradientPanelUParticipacao_1.add(panelNomePersonagemParticipacao_1);
		
		JLabel lblNomePersonagemParticipacao_1 = new JLabel("Nome do personagem*");
		lblNomePersonagemParticipacao_1.setForeground(Color.WHITE);
		lblNomePersonagemParticipacao_1.setBounds(47, 11, 142, 14);
		panelNomePersonagemParticipacao_1.add(lblNomePersonagemParticipacao_1);
		
		JComboBox<String> cbNomePersonagemParticipacaoEditar_1 = new JComboBox<String>();
		cbNomePersonagemParticipacaoEditar_1.setBounds(47, 36, 142, 28);
		panelNomePersonagemParticipacao_1.add(cbNomePersonagemParticipacaoEditar_1);
		
		JPanel panelNumeroTimeParticipacao_1 = new JPanel();
		panelNumeroTimeParticipacao_1.setLayout(null);
		panelNumeroTimeParticipacao_1.setBackground(new Color(48, 49, 52));
		panelNumeroTimeParticipacao_1.setBounds(10, 186, 233, 75);
		gradientPanelUParticipacao_1.add(panelNumeroTimeParticipacao_1);
		
		JLabel lblNumeroTimeParticipacao_1 = new JLabel("N\u00FAmero do time*");
		lblNumeroTimeParticipacao_1.setForeground(Color.WHITE);
		lblNumeroTimeParticipacao_1.setBounds(47, 11, 142, 14);
		panelNumeroTimeParticipacao_1.add(lblNumeroTimeParticipacao_1);
		
		JComboBox<Integer> cbNumeroTimeParticipacaoEditar_1 = new JComboBox<Integer>();
		cbNumeroTimeParticipacaoEditar_1.setBounds(47, 36, 142, 28);
		panelNumeroTimeParticipacao_1.add(cbNumeroTimeParticipacaoEditar_1);
		
		JPanel panelEpIngressoParticipacao_1 = new JPanel();
		panelEpIngressoParticipacao_1.setLayout(null);
		panelEpIngressoParticipacao_1.setBackground(new Color(48, 49, 52));
		panelEpIngressoParticipacao_1.setBounds(454, 100, 233, 75);
		gradientPanelUParticipacao_1.add(panelEpIngressoParticipacao_1);
		
		JLabel iconEpIngressoParticipacao_1 = new JLabel("");
		iconEpIngressoParticipacao_1.setIcon(new ImageIcon(Interface.class.getResource("/img/teamwork.png")));
		iconEpIngressoParticipacao_1.setBounds(47, 42, 16, 16);
		panelEpIngressoParticipacao_1.add(iconEpIngressoParticipacao_1);
		
		JSeparator separatorEpIngressoParticipacao_1 = new JSeparator();
		separatorEpIngressoParticipacao_1.setBounds(47, 66, 142, 2);
		panelEpIngressoParticipacao_1.add(separatorEpIngressoParticipacao_1);
		
		JLabel lblEpIngressoParticipacao_1 = new JLabel("Epis\u00F3dio de ingresso");
		lblEpIngressoParticipacao_1.setForeground(Color.WHITE);
		lblEpIngressoParticipacao_1.setBounds(47, 11, 142, 14);
		panelEpIngressoParticipacao_1.add(lblEpIngressoParticipacao_1);
		
		tfEpIngressoEditarParticipacao_1 = new JTextField();
		tfEpIngressoEditarParticipacao_1.setOpaque(false);
		tfEpIngressoEditarParticipacao_1.setForeground(Color.WHITE);
		tfEpIngressoEditarParticipacao_1.setColumns(10);
		tfEpIngressoEditarParticipacao_1.setCaretColor(Color.WHITE);
		tfEpIngressoEditarParticipacao_1.setBorder(null);
		tfEpIngressoEditarParticipacao_1.setBounds(73, 42, 118, 20);
		panelEpIngressoParticipacao_1.add(tfEpIngressoEditarParticipacao_1);
		
		JPanel panelEpSaidaParticipacao_1 = new JPanel();
		panelEpSaidaParticipacao_1.setLayout(null);
		panelEpSaidaParticipacao_1.setBackground(new Color(48, 49, 52));
		panelEpSaidaParticipacao_1.setBounds(454, 186, 233, 75);
		gradientPanelUParticipacao_1.add(panelEpSaidaParticipacao_1);
		
		JLabel iconEpSaidaParticipacao_1 = new JLabel("");
		iconEpSaidaParticipacao_1.setIcon(new ImageIcon(Interface.class.getResource("/img/teamwork.png")));
		iconEpSaidaParticipacao_1.setBounds(47, 42, 16, 16);
		panelEpSaidaParticipacao_1.add(iconEpSaidaParticipacao_1);
		
		JSeparator separatorEpSaidaParticipacao_1 = new JSeparator();
		separatorEpSaidaParticipacao_1.setBounds(47, 66, 142, 2);
		panelEpSaidaParticipacao_1.add(separatorEpSaidaParticipacao_1);
		
		JLabel lblEpSaidaParticipacao_1 = new JLabel("Epis\u00F3dio de sa\u00EDda");
		lblEpSaidaParticipacao_1.setForeground(Color.WHITE);
		lblEpSaidaParticipacao_1.setBounds(47, 11, 142, 14);
		panelEpSaidaParticipacao_1.add(lblEpSaidaParticipacao_1);
		
		tfEpSaidaEditarParticipacao_1 = new JTextField();
		tfEpSaidaEditarParticipacao_1.setOpaque(false);
		tfEpSaidaEditarParticipacao_1.setForeground(Color.WHITE);
		tfEpSaidaEditarParticipacao_1.setColumns(10);
		tfEpSaidaEditarParticipacao_1.setCaretColor(Color.WHITE);
		tfEpSaidaEditarParticipacao_1.setBorder(null);
		tfEpSaidaEditarParticipacao_1.setBounds(73, 42, 118, 20);
		panelEpSaidaParticipacao_1.add(tfEpSaidaEditarParticipacao_1);
		
		JPanel panelFuncaoParticipacao_1 = new JPanel();
		panelFuncaoParticipacao_1.setLayout(null);
		panelFuncaoParticipacao_1.setBackground(new Color(48, 49, 52));
		panelFuncaoParticipacao_1.setBounds(10, 272, 233, 75);
		gradientPanelUParticipacao_1.add(panelFuncaoParticipacao_1);
		
		JLabel lblFuncaoParticipacao_1 = new JLabel("Fun\u00E7\u00E3o");
		lblFuncaoParticipacao_1.setForeground(Color.WHITE);
		lblFuncaoParticipacao_1.setBounds(47, 11, 142, 14);
		panelFuncaoParticipacao_1.add(lblFuncaoParticipacao_1);
		
		JComboBox<String> cbFuncaoEditarParticipacao = new JComboBox<String>();
		cbFuncaoEditarParticipacao.setBackground(Color.GRAY);
		cbFuncaoEditarParticipacao.setForeground(Color.BLACK);
		cbFuncaoEditarParticipacao.setModel(new DefaultComboBoxModel<String>(new String[] {"-", "Regular", "Substituto", "L\u00EDder"}));
		cbFuncaoEditarParticipacao.setBounds(47, 36, 142, 28);
		panelFuncaoParticipacao_1.add(cbFuncaoEditarParticipacao);
		
		KButton btnEditarParticipacao = new KButton();
		btnEditarParticipacao.setText("Editar");
		btnEditarParticipacao.setOpaque(false);
		btnEditarParticipacao.kStartColor = new Color(254, 140, 0);
		btnEditarParticipacao.setkStartColor(new Color(254, 140, 0));
		btnEditarParticipacao.kPressedColor = new Color(179, 175, 168);
		btnEditarParticipacao.setkPressedColor(new Color(179, 175, 168));
		btnEditarParticipacao.kHoverStartColor = new Color(248, 49, 11);
		btnEditarParticipacao.setkHoverStartColor(new Color(248, 49, 11));
		btnEditarParticipacao.kHoverForeGround = Color.WHITE;
		btnEditarParticipacao.setkHoverForeGround(Color.WHITE);
		btnEditarParticipacao.kHoverEndColor = new Color(254, 126, 30);
		btnEditarParticipacao.setkHoverEndColor(new Color(254, 126, 30));
		btnEditarParticipacao.kEndColor = new Color(248, 54, 0);
		btnEditarParticipacao.setkEndColor(new Color(248, 54, 0));
		btnEditarParticipacao.setBorder(null);
		btnEditarParticipacao.setBounds(507, 312, 144, 32);
		gradientPanelUParticipacao_1.add(btnEditarParticipacao);
		
		JLabel lblObrigatorio_3_1_2_1 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_3_1_2_1.setForeground(Color.WHITE);
		lblObrigatorio_3_1_2_1.setBounds(588, 287, 75, 14);
		gradientPanelUParticipacao_1.add(lblObrigatorio_3_1_2_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("");
		lblNewLabel_2_1_1.setIcon(new ImageIcon(Interface.class.getResource("/img/npt (1).png")));
		lblNewLabel_2_1_1.setBounds(306, 11, 92, 92);
		gradientPanelUParticipacao_1.add(lblNewLabel_2_1_1);
		
		JPanel panelDParticipacao = new JPanel();
		panelDParticipacao.setLayout(null);
		panelDParticipacao.setBackground(new Color(32, 33, 36));
		layeredPane.add(panelDParticipacao, "name_13920153624400");
		
		KGradientPanel gradientPanelDParticipacao = new KGradientPanel();
		gradientPanelDParticipacao.setLayout(null);
		gradientPanelDParticipacao.setOpaque(false);
		gradientPanelDParticipacao.kStartColor = new Color(48, 49, 52);
		gradientPanelDParticipacao.setkStartColor(new Color(48, 49, 52));
		gradientPanelDParticipacao.kEndColor = new Color(48, 49, 52);
		gradientPanelDParticipacao.setkEndColor(new Color(48, 49, 52));
		gradientPanelDParticipacao.kBorderRadius = 20;
		gradientPanelDParticipacao.setkBorderRadius(20);
		gradientPanelDParticipacao.setBounds(10, 11, 704, 357);
		panelDParticipacao.add(gradientPanelDParticipacao);
		
		JPanel panelNomePersonagemExcluirParticipacao = new JPanel();
		panelNomePersonagemExcluirParticipacao.setLayout(null);
		panelNomePersonagemExcluirParticipacao.setBackground(new Color(48, 49, 52));
		panelNomePersonagemExcluirParticipacao.setBounds(10, 106, 233, 75);
		gradientPanelDParticipacao.add(panelNomePersonagemExcluirParticipacao);
		
		JLabel lblNomePersonagemExcluirParticipacao = new JLabel("Nome do personagem*");
		lblNomePersonagemExcluirParticipacao.setForeground(Color.WHITE);
		lblNomePersonagemExcluirParticipacao.setBounds(47, 11, 142, 14);
		panelNomePersonagemExcluirParticipacao.add(lblNomePersonagemExcluirParticipacao);
		
		JComboBox<String> cbNomePersonagemParticipacaoExcluir = new JComboBox<String>();
		cbNomePersonagemParticipacaoExcluir.setBounds(47, 36, 142, 28);
		panelNomePersonagemExcluirParticipacao.add(cbNomePersonagemParticipacaoExcluir);
		
		JPanel panelNumeroTimeExcluirParticipacao = new JPanel();
		panelNumeroTimeExcluirParticipacao.setLayout(null);
		panelNumeroTimeExcluirParticipacao.setBackground(new Color(48, 49, 52));
		panelNumeroTimeExcluirParticipacao.setBounds(461, 106, 233, 75);
		gradientPanelDParticipacao.add(panelNumeroTimeExcluirParticipacao);
		
		JLabel lblNumeroTimeExcluirParticipacao = new JLabel("N\u00FAmero do time*");
		lblNumeroTimeExcluirParticipacao.setForeground(Color.WHITE);
		lblNumeroTimeExcluirParticipacao.setBounds(47, 11, 176, 14);
		panelNumeroTimeExcluirParticipacao.add(lblNumeroTimeExcluirParticipacao);
		
		JComboBox<Integer> cbNumeroTimeParticipacaoExcluir = new JComboBox<Integer>();
		cbNumeroTimeParticipacaoExcluir.setBounds(47, 36, 126, 28);
		panelNumeroTimeExcluirParticipacao.add(cbNumeroTimeParticipacaoExcluir);
		
		JLabel lblObrigatorio_2_2_1_2_1 = new JLabel("*Obrigat\u00F3rio");
		lblObrigatorio_2_2_1_2_1.setForeground(Color.WHITE);
		lblObrigatorio_2_2_1_2_1.setBounds(47, 332, 75, 14);
		gradientPanelDParticipacao.add(lblObrigatorio_2_2_1_2_1);
		
		KButton btnOK_ExcluirParticipacao = new KButton();
		btnOK_ExcluirParticipacao.setText("OK");
		btnOK_ExcluirParticipacao.setOpaque(false);
		btnOK_ExcluirParticipacao.kStartColor = new Color(254, 140, 0);
		btnOK_ExcluirParticipacao.setkStartColor(new Color(254, 140, 0));
		btnOK_ExcluirParticipacao.kPressedColor = new Color(179, 175, 168);
		btnOK_ExcluirParticipacao.setkPressedColor(new Color(179, 175, 168));
		btnOK_ExcluirParticipacao.kHoverStartColor = new Color(248, 49, 11);
		btnOK_ExcluirParticipacao.setkHoverStartColor(new Color(248, 49, 11));
		btnOK_ExcluirParticipacao.kHoverForeGround = Color.WHITE;
		btnOK_ExcluirParticipacao.setkHoverForeGround(Color.WHITE);
		btnOK_ExcluirParticipacao.kHoverEndColor = new Color(254, 126, 30);
		btnOK_ExcluirParticipacao.setkHoverEndColor(new Color(254, 126, 30));
		btnOK_ExcluirParticipacao.kEndColor = new Color(248, 54, 0);
		btnOK_ExcluirParticipacao.setkEndColor(new Color(248, 54, 0));
		btnOK_ExcluirParticipacao.setBorder(null);
		btnOK_ExcluirParticipacao.setBounds(509, 215, 144, 32);
		gradientPanelDParticipacao.add(btnOK_ExcluirParticipacao);
		
		JLabel iconNarutoC_1_1_1_2_1 = new JLabel("");
		iconNarutoC_1_1_1_2_1.setIcon(new ImageIcon(Interface.class.getResource("/img/npt (1).png")));
		iconNarutoC_1_1_1_2_1.setBounds(306, 11, 92, 92);
		gradientPanelDParticipacao.add(iconNarutoC_1_1_1_2_1);
	
       

		
		// ========== //
		// PERSONAGEM //
		// ========== //
		
		// === Mudar panel para ADICIONAR personagem === //
		mntmAdicionarPersonagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelCPersonagem);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});

		// === Adicionar personagem === //
		btnAdicionarPersonagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean cadastroValido = true;
				
				if (tfNome.getText().trim().equals("")) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Não é possível adicionar um personagem sem nome",
							"Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				} else if (tfNome.getText().trim().length() > 40) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Nome excede o limite de caracteres (40)", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				} else if (personagemDAO.consultarPersonagem(tfNome.getText().trim()) != null) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Personagem já existente", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				// validar data de nascimento
				if ((!checaData(tfDataNascimento.getText().trim(), "yyyy-MM-dd")) && (!tfDataNascimento.getText().trim().equals("aaaa-mm-dd"))) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Data inválida", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (StringUtils.isNumeric(tfOcupacao.getText().trim())) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Ocupação inválida", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfOcupacao.getText().trim().length() > 30) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Ocupação excede o limite de caracteres (30)",
							"Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				}
				
				if ((!eDoublePositivo(tfPeso.getText().trim())) && (!tfPeso.getText().trim().equals(""))) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Peso inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if ((!eDoublePositivo(tfAltura.getText().trim())) && (!tfAltura.getText().trim().equals(""))) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Altura inválida", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (cadastroValido) {
					String nome = WordUtils.capitalizeFully(tfNome.getText().trim());
					char sexo = 0;
					
					if (rdbtnM.isSelected()) {
						sexo = 'M';
					} else if (rdbtnF.isSelected()) {
						sexo = 'F';
					}
					
					String dataNascimento = tfDataNascimento.getText().trim();
					if (dataNascimento.equals("aaaa-mm-dd")) {
						dataNascimento = null;
					}
					String tipoSanguineo = cbTipoSanguineo.getSelectedItem().toString();
					if (tipoSanguineo.equals("-")) {
						tipoSanguineo = null;
					}
					
					double peso = 0;
					if (!tfPeso.getText().trim().equals("")) {
						peso = Double.parseDouble(tfPeso.getText().trim());
					}
					
					double altura = 0;
					if (!tfAltura.getText().trim().equals("")) {
						altura = Double.parseDouble(tfAltura.getText().trim());
					}
					
					String ocupacao = WordUtils.capitalizeFully(tfOcupacao.getText().trim(), new char[] {});
					Personagem personagem = new Personagem(nome, sexo, dataNascimento, tipoSanguineo, peso, altura,
							ocupacao);
					personagemDAO.inserirPersonagem(personagem);
					JOptionPane.showMessageDialog(null, "Personagem adicionado com sucesso", "Sucesso",
							JOptionPane.PLAIN_MESSAGE);
					
					tfNome.setText("");
					tfDataNascimento.setText("");
					tfOcupacao.setText("");
					rdbtnF.setSelected(false);
					rdbtnM.setSelected(false);
					tfPeso.setText("");
					tfAltura.setText("");
					cbTipoSanguineo.setSelectedItem("-");
				}
			}
		});

		
		// === Mudar panel para VER personagens === //
		// === Ver personagens === //
		mntmVerPersonagens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelRPersonagem);
				layeredPane.repaint();
				layeredPane.revalidate();
				DefaultTableModel model = (DefaultTableModel) tablePersonagem.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				ArrayList<Personagem> personagens = new ArrayList<>();
				personagens = personagemDAO.getPersonagens();
				for (Personagem personagem : personagens) {
					String nome = personagem.getNome();
					String dataNascimento = personagem.getDataNascimento();
					String ocupacao = personagem.getOcupacao();
					char sexo = personagem.getSexo();
					double peso = personagem.getPeso();
					double altura = personagem.getAltura();
					String tipoSanguineo = personagem.getTipoSanguineo();
					if (dataNascimento != null) {
						int ano = Integer.parseInt(dataNascimento.substring(0, 4));
						int mes = Integer.parseInt(dataNascimento.substring(5, 7));
						int dia = Integer.parseInt(dataNascimento.substring(8, 10));				
		
						LocalDate hoje = LocalDate.now();
						
				        LocalDate outraData = LocalDate.of(ano, Month.of(mes), dia);
				        long idade = ChronoUnit.YEARS.between(outraData, hoje);
									
					    if (peso == 0) {
					        if (altura == 0) {
					            model.addRow(new Object[] { nome, sexo, dataNascimento, idade, tipoSanguineo, null,
					                    null, ocupacao });
					        } else {
					            model.addRow(new Object[] { nome, sexo, dataNascimento, idade, tipoSanguineo, null,
					                    String.format("%.02f", altura), ocupacao });
					        }
					    } else {
					        if (altura != 0) {
					            model.addRow(new Object[] { nome, sexo, dataNascimento, idade, tipoSanguineo,
					                    String.format("%.01f", peso), String.format("%.02f", altura), ocupacao });
					        } else {
					            model.addRow(new Object[] { nome, sexo, dataNascimento, idade, tipoSanguineo,
					                    String.format("%.01f", peso), null, ocupacao });
					        }
					    }
					} else {
					    if (peso == 0) {
					        if (altura == 0) {
					            model.addRow(new Object[] { nome, sexo, dataNascimento, null, tipoSanguineo, null, null,
					                    ocupacao });
					        } else {
					            model.addRow(new Object[] { nome, sexo, dataNascimento, null, tipoSanguineo, null,
					                    String.format("%.02f", altura), ocupacao });
					        }
					    } else {
					        if (altura != 0) {
					            model.addRow(new Object[] { nome, sexo, dataNascimento, null, tipoSanguineo,
					                    String.format("%.01f", peso), String.format("%.02f", altura), ocupacao });
					        } else {
					            model.addRow(new Object[] { nome, sexo, dataNascimento, null, tipoSanguineo,
					                    String.format("%.01f", peso), null, ocupacao });
					        }
					    }
					}
				}
			}
		});

		
		// === Mudar panel para EDITAR personagem === //
		mntmEditarPersonagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelUPersonagem);
				layeredPane.repaint();
				layeredPane.revalidate();
				cbNomeEditar.removeAllItems();
				for (Personagem personagem : personagemDAO.getPersonagens()) {
					cbNomeEditar.addItem(personagem.getNome());
				}
			}
		});
		
		// === Dados OK para editar === //
		btnOK_EditarPersonagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelUPersonagem_1);
				layeredPane.repaint();
				layeredPane.revalidate();
				Personagem personagem = personagemDAO.consultarPersonagem(cbNomeEditar.getSelectedItem().toString());
				tfNome_1.setText(personagem.getNome());
				tfNome_1.setEnabled(false);
				if (personagem.getDataNascimento() == null) {
					tfDataNascimento_1.setText("aaaa-mm-dd");
				} else {
					tfDataNascimento_1.setText(personagem.getDataNascimento());
				}
				tfOcupacao_1.setText(personagem.getOcupacao());
				if (personagem.getSexo() == 'M') {
					rdbtnM_1.setSelected(true);
				} else if (personagem.getSexo() == 'F') {
					rdbtnF_1.setSelected(true);
				}
				tfPeso_1.setText(Double.toString(personagem.getPeso()));
				tfAltura_1.setText(Double.toString(personagem.getAltura()));
				if(personagem.getTipoSanguineo() == null) {
					cbTipoSanguineo_1.setSelectedItem("-");
				} else {
					cbTipoSanguineo_1.setSelectedItem(personagem.getTipoSanguineo());
				}
			}
		});

		// === Editar personagem === //
		btnEditarPersonagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean edicaoValida = true;
				
				// validar data de nascimento
				if ((!checaData(tfDataNascimento_1.getText().trim(), "yyyy-MM-dd")) && (!tfDataNascimento_1.getText().trim().equals("aaaa-mm-dd"))) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Data inválida", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (StringUtils.isNumeric(tfOcupacao_1.getText().trim())) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Ocupação inválida", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfOcupacao_1.getText().trim().length() > 30) {
					edicaoValida = false;
				    JOptionPane.showMessageDialog(null, "Ocupação excede o limite de caracteres (30)", "Edição não realizada", JOptionPane.WARNING_MESSAGE);
				}
				
				if ((!eDoublePositivo(tfPeso_1.getText().trim())) && (!tfPeso_1.getText().trim().equals(""))) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Peso inválido", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if ((!eDoublePositivo(tfAltura_1.getText().trim())) && (!tfAltura_1.getText().trim().equals(""))) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Altura inválida", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (edicaoValida) {
					String nome = WordUtils.capitalizeFully(tfNome_1.getText().trim());
					char sexo = 0;
					if (rdbtnM_1.isSelected()) {
						sexo = 'M';
					} else if (rdbtnF_1.isSelected()) {
						sexo = 'F';
					}
					
					String dataNascimento = tfDataNascimento_1.getText().trim();
					if (dataNascimento.equals("aaaa-mm-dd")) {
						dataNascimento = null;
					}
					
					String tipoSanguineo = cbTipoSanguineo_1.getSelectedItem().toString();
					if (tipoSanguineo.equals("-")) {
						tipoSanguineo = null;
					}
					
					double peso = 0;
					if (!tfPeso_1.getText().trim().equals("")) {
						peso = Double.parseDouble(tfPeso_1.getText().trim());
					}
					
					double altura = 0;
					if (!tfAltura_1.getText().trim().equals("")) {
						altura = Double.parseDouble(tfAltura_1.getText().trim());
					}
					String ocupacao = WordUtils.capitalizeFully(tfOcupacao_1.getText().trim(), new char[] {});
					personagemDAO.alterarPersonagem(
							new Personagem(nome, sexo, dataNascimento, tipoSanguineo, peso, altura, ocupacao));
					JOptionPane.showMessageDialog(null, "Personagem atualizado com sucesso", "Sucesso",
							JOptionPane.PLAIN_MESSAGE);
					tfNome_1.setText("");
					tfDataNascimento_1.setText("");
					tfOcupacao_1.setText("");
					rdbtnF_1.setSelected(false);
					rdbtnM_1.setSelected(false);
					tfPeso_1.setText("");
					tfAltura_1.setText("");
					cbTipoSanguineo_1.setSelectedItem("-");
					layeredPane.removeAll();
					layeredPane.add(panelUPersonagem);
					layeredPane.repaint();
					layeredPane.revalidate();
				}
			}
		});

		
		// === Mudar panel para EXCLUIR personagem === //
		mntmExcluirPersonagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelDPersonagem);
				layeredPane.repaint();
				layeredPane.revalidate();
				cbNomeExcluir.removeAllItems();
				for (Personagem personagem : personagemDAO.getPersonagens()) {
					cbNomeExcluir.addItem(personagem.getNome());
				}
			}
		});
		
		// === Excluir personagem === //
		btnOK_ExcluirPersonagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir esse personagem?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (resposta == 0) {
					if (ninjaDAO.consultarNinja(cbNomeExcluir.getSelectedItem().toString()) != null) {
						if (ninjaDAO.selecionaTransformacoesNatureza(cbNomeExcluir.getSelectedItem().toString()) != null) {
							ArrayList<String> transformacoesNatureza = new ArrayList<>();
							transformacoesNatureza = ninjaDAO.selecionaTransformacoesNatureza(cbNomeExcluir.getSelectedItem().toString());
							for(String transformacaoNatureza : transformacoesNatureza) {
								ninjaDAO.removerTransformacaoNatureza(cbNomeExcluir.getSelectedItem().toString(), transformacaoNatureza);
							}
						}
						if (ninjaDAO.selecionaParticipacoes(cbNomeExcluir.getSelectedItem().toString()) != null) {
	                        ArrayList<Integer> participacoes = new ArrayList<>();
	                        participacoes = ninjaDAO.selecionaParticipacoes(cbNomeExcluir.getSelectedItem().toString());
	                        for (int participacao : participacoes) {
	                            NPTDAO.removerNPT(NPTDAO.consultarNPT(cbNomeExcluir.getSelectedItem().toString(), participacao));
	                        }
	                    }
						ninjaDAO.removerNinja(ninjaDAO.consultarNinja(cbNomeExcluir.getSelectedItem().toString()));
					}
					if (personagemDAO.selecionaFrases(cbNomeExcluir.getSelectedItem().toString()) != null) {
						ArrayList<String> frases = new ArrayList<>();
						frases = personagemDAO.selecionaFrases(cbNomeExcluir.getSelectedItem().toString());
						for (int i = 0; i < frases.size(); i += 2) {
							String texto = frases.get(i);
							String ocasiao = frases.get(i + 1);
							personagemDAO.removerFrasePersonagem(cbNomeExcluir.getSelectedItem().toString(), texto, ocasiao);
						}
					}
					personagemDAO.removerPersonagem(personagemDAO.consultarPersonagem(cbNomeExcluir.getSelectedItem().toString()));
					JOptionPane.showMessageDialog(null, "Personagem excluído com sucesso", "Sucesso",
							JOptionPane.PLAIN_MESSAGE);
					cbNomeExcluir.removeAllItems();
					for (Personagem personagem : personagemDAO.getPersonagens()) {
						cbNomeExcluir.addItem(personagem.getNome());
					}
				}
			}
		});
	
		
		// === Ver estatísticas dos personagens === //
		btnVerEstatisticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Double> estatisticas = new ArrayList<>();
				estatisticas = consultasSQL.estatisticasPersonagens();
				double mediaPeso = Math.round(estatisticas.get(0) * 100) / 100;
				double mediaAltura = Math.round(estatisticas.get(1) * 100) / 100;
				double alturaMaxima = Math.round(estatisticas.get(2) * 100) / 100;
				double alturaMinima = Math.round(estatisticas.get(3) * 100) / 100;
				double pesoMaximo = Math.round(estatisticas.get(4) * 100) / 100;
				double pesoMinimo = Math.round(estatisticas.get(5) * 100) / 100;
				JOptionPane.showMessageDialog(null, "Média de peso: " + mediaPeso + " kg\nMédia de altura: " + mediaAltura + " m\nAltura máxima: "
						+ alturaMaxima + " m\nAltura mínima: " + alturaMinima + " m\nPeso máximo: " + pesoMaximo + " kg\nPeso mínimo: "
						+ pesoMinimo + " kg", "Estatísticas", JOptionPane.INFORMATION_MESSAGE,
		                new ImageIcon(Interface.class.getResource("/img/info.png")));
			}
		});
		
		
		
		
		// ================ //
		// FRASE PERSONAGEM //
		// ================ //
		
		// === Mudar panel para ADICIONAR frase personagem === //
		mntmAdicionarFrase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelCFrase);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				cbNomePersonagemFrase.removeAllItems();
				for (Personagem personagem : personagemDAO.getPersonagens()) {
					cbNomePersonagemFrase.addItem(personagem.getNome());
				}
			}
		});
		
		// === Adicionar frase === // 
		btnAdicionarFrase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean cadastroValido = true;
								
				if (tfTexto.getText().trim().equals("")) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Não é possível adicionar uma frase sem o texto", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				} else if (StringUtils.isNumeric(tfTexto.getText().trim())) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Texto inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfTexto.getText().trim().length() > 100) {
					cadastroValido = false;
				    JOptionPane.showMessageDialog(null, "Texto excede o limite de caracteres (100)", "Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfOcasiao.getText().trim().equals("")) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Não é possível adicionar uma frase sem a ocasião", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				} else if (StringUtils.isNumeric(tfOcasiao.getText().trim())) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Ocasião inválida", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfOcasiao.getText().trim().length() > 100) {
					cadastroValido = false;
				    JOptionPane.showMessageDialog(null, "Ocasião excede o limite de caracteres (100)", "Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				}				
				
				if (cadastroValido) {
					String nomePersonagem = cbNomePersonagemFrase.getSelectedItem().toString();
					String texto = WordUtils.capitalizeFully(tfTexto.getText().trim(), new char[] {});
					String ocasiao = WordUtils.capitalizeFully(tfOcasiao.getText().trim(), new char[] {});
					
					if (personagemDAO.consultarFrasePersonagem(nomePersonagem, texto, ocasiao) != null) {
						cadastroValido = false;
						JOptionPane.showMessageDialog(null, "Frase já existente", "Cadastro não realizado",
								JOptionPane.WARNING_MESSAGE);
					} else {
						personagemDAO.inserirFrasePersonagem(nomePersonagem, texto, ocasiao);
						JOptionPane.showMessageDialog(null, "Frase personagem adicionada com sucesso", "Sucesso",
								JOptionPane.PLAIN_MESSAGE);
						cbNomePersonagemFrase.setSelectedIndex(0);
						tfTexto.setText("");
						tfOcasiao.setText("");
					}
				}
			}
		});
		
		
		// === Mudar panel para VER frases personagens === //
		// === Ver frases personagens === //
		mntmVerFrases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelRFrase);
				layeredPane.repaint();
				layeredPane.revalidate();
				DefaultTableModel model = (DefaultTableModel) tableFrase.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				ArrayList<String> frases = new ArrayList<>();
				frases = personagemDAO.getFrasePersonagem();
				for (int i = 0; i < frases.size(); i += 3) {
					String nomePersonagem = frases.get(i);
					String texto = frases.get(i + 1);
					String ocasiao = frases.get(i + 2);
					
					model.addRow(new Object[] { nomePersonagem, texto, ocasiao });
				}
			}
		});
		
		
		// === Mudar panel para EXCLUIR frase personagem === //
		mntmExcluirFrase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelDFrase);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				cbNomePersonagemFraseExcluir.removeAllItems();
				for (String personagem : personagemDAO.selecionaNomesFrases()) {
					cbNomePersonagemFraseExcluir.addItem(personagem);
				}
			}
		});
		
		// === Excluir frase === //
		btnOK_ExcluirFrase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean exclusaoValida = true;
					
				if (tfTextoExcluir.getText().trim().equals("")) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Não é possível excluir uma frase sem o texto", "Exclusão não realizada",
							JOptionPane.WARNING_MESSAGE);
				} else if (StringUtils.isNumeric(tfTextoExcluir.getText().trim())) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Texto inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfTextoExcluir.getText().trim().length() > 100) {
					exclusaoValida = false;
				    JOptionPane.showMessageDialog(null, "Texto excede o limite de caracteres (100)", "Exclusão não realizada", JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfOcasiaoExcluir.getText().trim().equals("")) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Não é possível excluir uma frase sem a ocasião", "Exclusão não realizada",
							JOptionPane.WARNING_MESSAGE);
				} else if (StringUtils.isNumeric(tfOcasiaoExcluir.getText().trim())) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Ocasião inválida", "Exclusão não realizada",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfOcasiaoExcluir.getText().trim().length() > 100) {
					exclusaoValida = false;
				    JOptionPane.showMessageDialog(null, "Ocasião excede o limite de caracteres (100)", "Exclusão não realizada", JOptionPane.WARNING_MESSAGE);
				}				
				
				if (exclusaoValida) {
					String nomePersonagem = cbNomePersonagemFraseExcluir.getSelectedItem().toString();
					String texto = WordUtils.capitalizeFully(tfTextoExcluir.getText().trim(), new char[] {});
					String ocasiao = WordUtils.capitalizeFully(tfOcasiaoExcluir.getText().trim(), new char[] {});
					
					int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir essa frase?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if (resposta == 0) {
						if (personagemDAO.consultarFrasePersonagem(nomePersonagem, texto, ocasiao) == null) {
							exclusaoValida = false;
							JOptionPane.showMessageDialog(null, "Frase inexistente", "Exclusão não realizada",
									JOptionPane.WARNING_MESSAGE);
						} else {
							personagemDAO.removerFrasePersonagem(nomePersonagem, texto, ocasiao);
							JOptionPane.showMessageDialog(null, "Frase personagem excluída com sucesso", "Sucesso",
									JOptionPane.PLAIN_MESSAGE);
							cbNomePersonagemFraseExcluir.setSelectedIndex(0);
							tfTextoExcluir.setText("");
							tfOcasiaoExcluir.setText("");
							
							cbNomePersonagemFraseExcluir.removeAllItems();
							for (String personagem : personagemDAO.selecionaNomesFrases()) {
								cbNomePersonagemFraseExcluir.addItem(personagem);
							}
						}
					}
				}
			}
		});
		
		
		
		
		// ===== //
		// NINJA //
		// ===== //
		
		// === Mudar panel apra ADICIONAR ninja === //
		mntmAdicionarNinja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelCNinja);
				layeredPane.repaint();
				layeredPane.revalidate();
				cbNomeNinja.removeAllItems();
				
				for (Personagem personagem : personagemDAO.getPersonagens()) {
					cbNomeNinja.addItem(personagem.getNome());
				}
			}
		});

		// === Adicionar ninja === //
		btnAdicionarNinja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean cadastroValido = true;
								
				if (ninjaDAO.consultarNinja(cbNomeNinja.getSelectedItem().toString()) != null) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Ninja já existente", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (StringUtils.isNumeric(tfTitulo.getText().trim())) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Título inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfOcupacao_1.getText().trim().length() > 20) {
					cadastroValido = false;
				    JOptionPane.showMessageDialog(null, "Título excede o limite de caracteres (20)", "Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				}
				
				if (cadastroValido) {
					String nomePersonagem = cbNomeNinja.getSelectedItem().toString();
					String patente = cbPatenteNinja.getSelectedItem().toString();
					if (patente.equals("-")) {
						patente = null;
					}
					String titulo = WordUtils.capitalizeFully(tfTitulo.getText().trim(), new char[] {});
					if (titulo.equals("")) {
						titulo = null;
					}
					ninjaDAO.inserirNinja(new Ninja(nomePersonagem, patente, titulo));
					JOptionPane.showMessageDialog(null, "Ninja adicionado com sucesso.", "Sucesso",
							JOptionPane.PLAIN_MESSAGE);
					cbNomeNinja.setSelectedIndex(0);
					cbPatenteNinja.setSelectedItem("-");
					tfTitulo.setText("");
				}
			}
		});

		
		// === Mudar panel para VER ninjas === //
		// === Ver ninjas === //
		mntmVerNinjas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelRNinja);
				layeredPane.repaint();
				layeredPane.revalidate();

				DefaultTableModel model = (DefaultTableModel) tableNinja.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				ArrayList<Ninja> ninjas = new ArrayList<>();
				ninjas = ninjaDAO.getNinjas();
				for (Ninja ninja : ninjas) {
					String nomePersonagem = ninja.getNomePersonagem();
					String patente = ninja.getPatente();
					String titulo = ninja.getTitulo();
					model.addRow(new Object[] { nomePersonagem, patente, titulo });
				}
			}
		});
		
		
		// === Mudar panel para EDITAR ninja === //
		mntmEditarNinja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelUNinja);
				layeredPane.repaint();
				layeredPane.revalidate();
				cbNomePersonagemEditar.removeAllItems();
				for (Ninja ninja : ninjaDAO.getNinjas()) {
					cbNomePersonagemEditar.addItem(ninja.getNomePersonagem());
				}
				for (Personagem personagem : personagemDAO.getPersonagens()) {
					cbNomeNinjaEditar.addItem(personagem.getNome());
				}
			}
		});
		
		// === Dados OK para editar === // 
		btnOK_EditarNinja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelUNinja_1);
				layeredPane.repaint();
				layeredPane.revalidate();
				Ninja ninja = ninjaDAO.consultarNinja(cbNomePersonagemEditar.getSelectedItem().toString());
				cbNomeNinjaEditar.setSelectedItem(ninja.getNomePersonagem());
				if (ninja.getPatente() == null) {
					cbPatenteNinjaEditar.setSelectedItem("-");
				} else {
					cbPatenteNinjaEditar.setSelectedItem(ninja.getPatente());
				}
				tfTitulo_1.setText(ninja.getTitulo());
			}
		});

		// === Editar ninja === //
		btnEditarNinja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean edicaoValida = true;
								
				if (StringUtils.isNumeric(tfTitulo_1.getText().trim())) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Título inválido", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfTitulo_1.getText().trim().length() > 20) {
					edicaoValida = false;
				    JOptionPane.showMessageDialog(null, "Título excede o limite de caracteres (20)", "Edição não realizada", JOptionPane.WARNING_MESSAGE);
				}
				
				if (edicaoValida) {
					String nomePersonagem = cbNomeNinjaEditar.getSelectedItem().toString();
					String patente = cbPatenteNinjaEditar.getSelectedItem().toString();
					if (patente.equals("-")) {
						patente = null;
					}
					String titulo = WordUtils.capitalizeFully(tfTitulo_1.getText().trim(), new char[] {});
					if (titulo.equals("")) {
						titulo = null;
					}
					ninjaDAO.alterarNinja(new Ninja(nomePersonagem, patente, titulo));
					JOptionPane.showMessageDialog(null, "Ninja atualizado com sucesso.", "Sucesso",
							JOptionPane.PLAIN_MESSAGE);
					cbNomeNinjaEditar.setSelectedIndex(0);
					cbPatenteNinjaEditar.setSelectedItem("-");
					tfTitulo_1.setText("");
					layeredPane.removeAll();
					layeredPane.add(panelUNinja);
					layeredPane.repaint();
					layeredPane.revalidate();
				}
			}
		});

		
		// === Mudar panel para EXCLUIR ninja === //
		mntmExcluirNinja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelDNinja);
				layeredPane.repaint();
				layeredPane.revalidate();
				cbNomePersonagemExcluir.removeAllItems();
				for (Ninja ninja : ninjaDAO.getNinjas()) {
					cbNomePersonagemExcluir.addItem(ninja.getNomePersonagem());
				}
			}
		});
		
		// === Excluir ninja === //
		btnOK_ExcluirNinja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir esse ninja?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (resposta == 0) {
					if (ninjaDAO.selecionaTransformacoesNatureza(cbNomePersonagemExcluir.getSelectedItem().toString()) != null) {
						ArrayList<String> transformacoesNatureza = new ArrayList<>();
						transformacoesNatureza = ninjaDAO.selecionaTransformacoesNatureza(cbNomePersonagemExcluir.getSelectedItem().toString());
						for(String transformacaoNatureza : transformacoesNatureza) {
							ninjaDAO.removerTransformacaoNatureza(cbNomePersonagemExcluir.getSelectedItem().toString(), transformacaoNatureza);
						}
					}
					if (ninjaDAO.selecionaParticipacoes(cbNomePersonagemExcluir.getSelectedItem().toString()) != null) {
                        ArrayList<Integer> participacoes = new ArrayList<>();
                        participacoes = ninjaDAO.selecionaParticipacoes(cbNomePersonagemExcluir.getSelectedItem().toString());
                        for (int participacao : participacoes) {
                            NPTDAO.removerNPT(NPTDAO.consultarNPT(cbNomePersonagemExcluir.getSelectedItem().toString(), participacao));
                        }
                    }
					ninjaDAO.removerNinja(ninjaDAO.consultarNinja(cbNomePersonagemExcluir.getSelectedItem().toString()));
					JOptionPane.showMessageDialog(null, "Ninja excluído com sucesso.", "Sucesso",
							JOptionPane.PLAIN_MESSAGE);
					cbNomePersonagemExcluir.removeAllItems();
					for (Ninja ninja : ninjaDAO.getNinjas()) {
						cbNomePersonagemExcluir.addItem(ninja.getNomePersonagem());
					}
				}
			}
		});

		
		// === Selecionar ninjas por patente === //
		btnSelecionarPatente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!cbSelecionarPatente.getSelectedItem().toString().equals("-")) {
		            DefaultTableModel model = (DefaultTableModel) tableNinja.getModel();
		            while (model.getRowCount() > 0) {
		                model.removeRow(0);
		            }
		            ArrayList<Ninja> ninjas = new ArrayList<>();
		            ninjas = consultasSQL.ninjasPatente(cbSelecionarPatente.getSelectedItem().toString());
		            for (Ninja ninja : ninjas) {
		                String nomePersonagem = ninja.getNomePersonagem();
		                String patente = ninja.getPatente();
		                String titulo = ninja.getTitulo();
		                model.addRow(new Object[] { nomePersonagem, patente, titulo });
		            }
		        }
			}
		});
		
		// === Redefinir === //
		btnRedefinirNinja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) tableNinja.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				ArrayList<Ninja> ninjas = new ArrayList<>();
				ninjas = ninjaDAO.getNinjas();
				for (Ninja ninja : ninjas) {
					String nomePersonagem = ninja.getNomePersonagem();
					String patente = ninja.getPatente();
					String titulo = ninja.getTitulo();
					model.addRow(new Object[] { nomePersonagem, patente, titulo });
				}
			}
		});
		
		
		
		
		// ====================== //
		// Transformação natureza //
		// ====================== //
		
		// === Mudar panel para ADICIONAR transformação da natureza === //
		mntmAdicionarTransformacaoNatureza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelCTransformacaoNatureza);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				cbNomePersonagemTransformacao.removeAllItems();
				for (Ninja ninja : ninjaDAO.getNinjas()) {
					cbNomePersonagemTransformacao.addItem(ninja.getNomePersonagem());
				}
			}
		});
		
		// === Adicionar transformação da natureza === //
		btnAdicionarTransformacaoNatureza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean cadastroValido = true;
				
				if (cadastroValido) {
					String nomePersonagem = cbNomePersonagemTransformacao.getSelectedItem().toString();
					String transformacaoNatureza = cbTransformacaoNatureza.getSelectedItem().toString();
					
					if (ninjaDAO.consultarTransformacaoNatureza(nomePersonagem, transformacaoNatureza) != null) {
						cadastroValido = false;
						JOptionPane.showMessageDialog(null, "Transformação da natureza já existente.", "Cadastro não realizado",
								JOptionPane.WARNING_MESSAGE);
					} else {
						ninjaDAO.inserirTransformacaoNatureza(nomePersonagem, transformacaoNatureza);
						JOptionPane.showMessageDialog(null, "Transformação da natureza adicionada com sucesso.", "Sucesso",
								JOptionPane.PLAIN_MESSAGE);
						cbNomePersonagemTransformacao.setSelectedIndex(0);
						cbTransformacaoNatureza.setSelectedIndex(0);
					}
				}
			}
		});
		
		
		// === Mudar panel para VER transformações da natureza === //
		// === Ver transformações da natureza === //
		mntmVerTransformacoesNatureza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelRTransformacaoNatureza);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				DefaultTableModel model = (DefaultTableModel) tableTransformacaoNatureza.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				ArrayList<String> transformacoesNatureza = new ArrayList<>();
				transformacoesNatureza = ninjaDAO.getTransformacaoNatureza();
				for (int i = 0; i < transformacoesNatureza.size(); i += 2) {
					String nomePersonagem = transformacoesNatureza.get(i);
					String transformacaoNatureza = transformacoesNatureza.get(i + 1);
					
					model.addRow(new Object[] { nomePersonagem, transformacaoNatureza });
				}
			}
		});
		
		
		// === Mudar panel para EXCLUIR transformação da natureza === //
		mntmExcluirTransformacaoNatureza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelDTransformacaoNatureza);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				cbNomePersonagemTransformacaoExcluir.removeAllItems();
				for (String ninja : ninjaDAO.selecionaNomeTransformacoesNatureza()) {
					cbNomePersonagemTransformacaoExcluir.addItem(ninja);
				}
			}
		});
		
		// === Excluir transformação da natureza === //
		btnOK_ExcluirTransformacaoNatureza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomePersonagem = cbNomePersonagemTransformacaoExcluir.getSelectedItem().toString();
				String transformacaoNatureza = cbTransformacaoExcluir.getSelectedItem().toString();
				if (ninjaDAO.consultarTransformacaoNatureza(nomePersonagem, transformacaoNatureza) == null) {
					JOptionPane.showMessageDialog(null, "Transformação da natureza inexistente.", "Exclusão não realizada",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir essa transformação da natureza?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if (resposta == 0) {
						ninjaDAO.removerTransformacaoNatureza(nomePersonagem, transformacaoNatureza);
						JOptionPane.showMessageDialog(null, "Transformação da natureza excluída com sucesso.", "Sucesso",
								JOptionPane.PLAIN_MESSAGE);
						
						cbNomePersonagemTransformacaoExcluir.removeAllItems();
						for (String ninja : ninjaDAO.selecionaNomeTransformacoesNatureza()) {
							cbNomePersonagemTransformacaoExcluir.addItem(ninja);
						}
						
						cbNomePersonagemTransformacaoExcluir.setSelectedIndex(0);
						cbTransformacaoExcluir.setSelectedIndex(0);
					}
				}
			}
		});
		
		
		
		
		// ====== //
		// MISSÃO //
		// ====== //
		
		// === Mudar panel para ADICIONAR missão === //
		mntmAdicionarMissao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelCMissao);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				cbNumeroTimeMissao.removeAllItems();
				for (Time time : timeDAO.getTimes()) {
					cbNumeroTimeMissao.addItem(time.getNumero());
				}
			}
		});

		// === Adicionar missão === //
		btnAdicionarMissao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean cadastroValido = true;
								
				if (tfObjetivo.getText().trim().equals("")) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Não é possível cadastrar uma missão sem o objetivo",
							"Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				} else if (StringUtils.isNumeric(tfObjetivo.getText().trim())) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Objetivo inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfObjetivo.getText().trim().length() > 50) {
					cadastroValido = false;
				    JOptionPane.showMessageDialog(null, "Objetivo excede o limite de caracteres (50)", "Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfEpInicio.getText().trim().equals("")) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Não é possível cadastrar uma missão sem o episódio de início",
							"Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				} else if (!eIntPositivo(tfEpInicio.getText().trim())) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Episódio de início inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfEpFim.getText().trim().equals("")) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Não é possível cadastrar uma missão sem o episódio de fim",
							"Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				} else if (!eIntPositivo(tfEpFim.getText().trim())) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Episódio de fim inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (cadastroValido) {
					int numeroTime = Integer.parseInt(cbNumeroTimeMissao.getSelectedItem().toString());
					String objetivo = WordUtils.capitalizeFully(tfObjetivo.getText().trim(), new char[] {});
					int epInicio = Integer.parseInt(tfEpInicio.getText().trim());
					int epFim = Integer.parseInt(tfEpFim.getText().trim());
					if (epFim >= epInicio) {
						if (missaoDAO.consultarMissao(numeroTime, objetivo, epInicio, epFim) != null) {
							cadastroValido = false;
							JOptionPane.showMessageDialog(null, "Missão já existente", "Cadastro não realizado",
									JOptionPane.WARNING_MESSAGE);
						} else {
							char ranking = cbRanking.getSelectedItem().toString().charAt(0);
							if (ranking == '-') {
								ranking = 0;
							}
							String tipo = cbTipo.getSelectedItem().toString();
							if (tipo.equals("-")) {
								tipo = null;
							}
							String resultado = "";
							if (rdbtnSucesso.isSelected()) {
								resultado = "Sucesso";
							} else if (rdbtnFracasso.isSelected()) {
								resultado = "Fracasso";
							}
							Missao missao = new Missao(numeroTime, objetivo, epInicio, epFim, ranking, tipo, resultado);
							missaoDAO.inserirMissao(missao);
							JOptionPane.showMessageDialog(null, "Missão adicionada com sucesso.", "Sucesso",
									JOptionPane.PLAIN_MESSAGE);
							cbNumeroTimeMissao.setSelectedIndex(0);
							tfObjetivo.setText("");
							tfEpInicio.setText("");
							tfEpFim.setText("");
							cbRanking.setSelectedItem("-");
							cbTipo.setSelectedItem("-");
							rdbtnSucesso.setSelected(false);
							rdbtnFracasso.setSelected(false);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Episódio de início posterior ao episódio de fim",
								"Episódio de início inválido", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		
		// === Mudar panel para VER missões === //
		// === Ver missões === //
		mntmVerMissoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelRMissao);
				layeredPane.repaint();
				layeredPane.revalidate();
				DefaultTableModel model = (DefaultTableModel) tableMissao.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				ArrayList<Missao> missoes = new ArrayList<>();
				missoes = missaoDAO.getMissoes();
				for (Missao missao : missoes) {
					int numeroTime = missao.getNumeroTime();
					String objetivo = missao.getObjetivo();
					int epInicio = missao.getEpInicio();
					int epFim = missao.getEpFim();
					char ranking = missao.getRanking();
					String tipo = missao.getTipo();
					String resultado = missao.getResultado();
					model.addRow(new Object[] { numeroTime, objetivo, epInicio, epFim, ranking, tipo, resultado });
				}
			}
		});
		
		
		// === Mudar panel para EDITAR missão === //
		mntmEditarMissao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelUMissao);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				cbNumeroTimeMissaoEditar.removeAllItems();
				for (Integer missao : missaoDAO.selecionaNumeroMissao()) {
					cbNumeroTimeMissaoEditar.addItem(missao);
				}
			}
		});
		
		// === Dados OK para editar === //
		btnOK_EditarMissao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean edicaoValida = true;
				
				if (tfObjetivoEditar.getText().trim().equals("")) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Não é possível alterar uma missão sem o objetivo",
							"Edição não realizada", JOptionPane.WARNING_MESSAGE);
				} else if (StringUtils.isNumeric(tfObjetivoEditar.getText().trim())) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Objetivo inválido", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfObjetivoEditar.getText().trim().length() > 50) {
					edicaoValida = false;
				    JOptionPane.showMessageDialog(null, "Objetivo excede o limite de caracteres (50)", "Edição não realizada", JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfEpInicioEditar.getText().trim().equals("")) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Não é possível alterar uma missão sem o episódio de início",
							"Edição não realizada", JOptionPane.WARNING_MESSAGE);
				} else if (!eIntPositivo(tfEpInicioEditar.getText().trim())) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Episódio de início inválido", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfEpFimEditar.getText().trim().equals("")) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Não é possível cadastrar uma missão sem o episódio de fim",
							"Edição não realizada", JOptionPane.WARNING_MESSAGE);
				} else if (!eIntPositivo(tfEpFimEditar.getText().trim())) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Episódio de fim inválido", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (edicaoValida) {
					int numeroTime = Integer.parseInt(cbNumeroTimeMissaoEditar.getSelectedItem().toString());
					String objetivo = WordUtils.capitalizeFully(tfObjetivoEditar.getText().trim(), new char[] {});
					int epInicio = Integer.parseInt(tfEpInicioEditar.getText().trim());
					int epFim = Integer.parseInt(tfEpFimEditar.getText().trim());
					if (epFim >= epInicio) {
						if (missaoDAO.consultarMissao(numeroTime, objetivo, epInicio, epFim) != null) {
							layeredPane.removeAll();
							layeredPane.add(panelUMissao_1);
							layeredPane.repaint();
							layeredPane.revalidate();
							Missao missao = missaoDAO.consultarMissao(numeroTime, objetivo, epInicio, epFim);
							
							cbNumeroTimeMissaoEditar_1.removeAllItems();
							for (Integer missaoNumero : missaoDAO.selecionaNumeroMissao()) {
								cbNumeroTimeMissaoEditar_1.addItem(missaoNumero);
							}
							cbNumeroTimeMissaoEditar_1.setSelectedItem(Integer.toString(numeroTime));
							tfObjetivo_1.setText(objetivo);
							tfEpInicio_1.setText(Integer.toString(epInicio));
							tfEpFim_1.setText(Integer.toString(epFim));
							cbNumeroTimeMissaoEditar_1.setEnabled(false);
							tfObjetivo_1.setEnabled(false);
							tfEpInicio_1.setEnabled(false);
							tfEpFim_1.setEnabled(false);
							cbRanking_1.setSelectedItem(Character.toString(missao.getRanking()));
							if (missao.getTipo() == null) {
								cbTipo_1.setSelectedItem("-");
							} else {
								cbTipo_1.setSelectedItem(missao.getTipo());
							}
							if (missao.getResultado().equals("Sucesso")) {
								rdbtnSucesso_1.setSelected(true);
							} else if (missao.getResultado().equals("Fracasso")) {
								rdbtnFracasso_1.setSelected(true);
							}
							cbNumeroTimeMissaoEditar_1.setSelectedIndex(0);
							tfObjetivoEditar.setText("");
							tfEpInicioEditar.setText("");
							tfEpFimEditar.setText("");
						} else {
							edicaoValida = false;
							JOptionPane.showMessageDialog(null, "Missão não cadastrada", "Erro",
									JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Episódio de início posterior ao episódio de fim",
								"Episódio de início inválido", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		// === Editar missão === //
		btnEditarMissao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numeroTime = Integer.parseInt(cbNumeroTimeMissaoEditar_1.getSelectedItem().toString());
				String objetivo = WordUtils.capitalizeFully(tfObjetivo_1.getText().trim(), new char[] {});
				int epInicio = Integer.parseInt(tfEpInicio_1.getText().trim());
				int epFim = Integer.parseInt(tfEpFim_1.getText().trim());
				char ranking = cbRanking_1.getSelectedItem().toString().charAt(0);
				if (ranking == '-') {
					ranking = 0;
				}
				String tipo = cbTipo_1.getSelectedItem().toString();
				if (tipo.equals("-")) {
					tipo = null;
				}
				String resultado = "";
				if (rdbtnSucesso_1.isSelected()) {
					resultado = "Sucesso";
				} else if (rdbtnFracasso_1.isSelected()) {
					resultado = "Fracasso";
				}
				missaoDAO.alterarMissao(new Missao(numeroTime, objetivo, epInicio, epFim, ranking, tipo, resultado));
				JOptionPane.showMessageDialog(null, "Missão atualizada com sucesso.", "Sucesso",
						JOptionPane.PLAIN_MESSAGE);
				cbNumeroTimeMissaoEditar_1.setSelectedIndex(0);
				tfObjetivo_1.setText("");
				tfEpInicio_1.setText("");
				tfEpFim_1.setText("");
				cbRanking_1.setSelectedItem("-");
				cbTipo_1.setSelectedItem("-");
				rdbtnSucesso_1.setSelected(false);
				rdbtnFracasso_1.setSelected(false);
				layeredPane.removeAll();
				layeredPane.add(panelUMissao);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});

		
		// === Mudar panel para EXCLUIR missão === //
		mntmExcluirMissao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelDMissao);
				layeredPane.repaint();
				layeredPane.revalidate();
								
				cbNumeroTimeMissaoExcluir.removeAllItems();
				for (Integer missao : missaoDAO.selecionaNumeroMissao()) {
					cbNumeroTimeMissaoExcluir.addItem(missao);
				}
			}
		});
		
		// === Excluir missão === //
		btnOK_ExcluirMissao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean exclusaoValida = true;
								
				if (tfObjetivoExcluir.getText().trim().equals("")) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Não é possível excluir uma missão sem o objetivo",
							"Exclusão não realizada", JOptionPane.WARNING_MESSAGE);
				} else if (StringUtils.isNumeric(tfObjetivoExcluir.getText().trim())) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Objetivo inválido", "Exclusão não realizada",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfObjetivoExcluir.getText().trim().length() > 50) {
					exclusaoValida = false;
				    JOptionPane.showMessageDialog(null, "Objetivo excede o limite de caracteres (50)", "Exclusão não realizada", JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfEpInicioExcluir.getText().trim().equals("")) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Não é possível excluir uma missão sem o episódio de início",
							"Exclusão não realizada", JOptionPane.WARNING_MESSAGE);
				} else if (!eIntPositivo(tfEpInicioExcluir.getText().trim())) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Episódio de início inválido", "Exclusão não realizada",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfEpFimExcluir.getText().trim().equals("")) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Não é possível excluir uma missão sem o episódio de fim",
							"Exclusão não realizada", JOptionPane.WARNING_MESSAGE);
				} else if (!eIntPositivo(tfEpFimExcluir.getText().trim())) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Episódio de fim inválido", "Exclusão não realizada",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (exclusaoValida) {
					int numeroTime = Integer.parseInt(cbNumeroTimeMissaoExcluir.getSelectedItem().toString());
					String objetivo = WordUtils.capitalizeFully(tfObjetivoExcluir.getText().trim(), new char[] {});
					int epInicio = Integer.parseInt(tfEpInicioExcluir.getText().trim());
					int epFim = Integer.parseInt(tfEpFimExcluir.getText().trim());
					if (missaoDAO.consultarMissao(numeroTime, objetivo, epInicio, epFim) != null) {
						int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir essa missão?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (resposta == 0) {
							if (missaoDAO.selecionaObstaculos(numeroTime, objetivo, epInicio, epFim) != null) {
								ArrayList<String> obstaculos = new ArrayList<>();
								for (String obstaculo : obstaculos) {
									missaoDAO.removerObstaculo(numeroTime, objetivo, epInicio, epFim, obstaculo);
								}
							}
							missaoDAO.removerMissao(missaoDAO.consultarMissao(numeroTime, objetivo, epInicio, epFim));
							JOptionPane.showMessageDialog(null, "Missão excluída com sucesso.", "Sucesso",
									JOptionPane.PLAIN_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Missão não cadastrada", "Erro",
								JOptionPane.WARNING_MESSAGE);
					}
				}
				cbNumeroTimeMissaoExcluir.removeAllItems();
				for (Integer missao : missaoDAO.selecionaNumeroMissao()) {
					cbNumeroTimeMissaoExcluir.addItem(missao);
				}
				
				cbNumeroTimeMissaoExcluir.setSelectedIndex(0);
				tfObjetivoExcluir.setText("");
				tfObjetivoExcluir.setText("");
				tfEpInicioExcluir.setText("");
				tfEpFimExcluir.setText("");
			}
		});

		
		// === Selecionar missões por tipo === //
		btnSelecionarTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!cbSelecionarTipo.getSelectedItem().toString().equals("-")) {
		            DefaultTableModel model = (DefaultTableModel) tableMissao.getModel();
		            while (model.getRowCount() > 0) {
		                model.removeRow(0);
		            }
		            ArrayList<Missao> missoes = new ArrayList<>();
		            missoes = consultasSQL.missoesTipo(cbSelecionarTipo.getSelectedItem().toString());
		            for (Missao missao : missoes) {
		                int numeroTime = missao.getNumeroTime();
		                String objetivo = missao.getObjetivo();
		                int epInicio = missao.getEpInicio();
		                int epFim = missao.getEpFim();
		                char ranking = missao.getRanking();
		                String resultado = missao.getResultado();
		                model.addRow(new Object[] { numeroTime, objetivo, epInicio, epFim, ranking, cbSelecionarTipo.getSelectedItem().toString(), resultado });
		            }
		        }
			}
		});
		
		// === Selecionar missões por episódios === //
		btnSelecionarEpisodios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((!tfSelecionarEpInicio.getText().trim().equals("-")) && (!tfSelecionarEpFim.getText().trim().equals("-"))) {
		            DefaultTableModel model = (DefaultTableModel) tableMissao.getModel();
		            while (model.getRowCount() > 0) {
		                model.removeRow(0);
		            }
		            ArrayList<Missao> missoes = new ArrayList<>();
		            missoes = consultasSQL.missoesEpisodios(Integer.parseInt(tfSelecionarEpInicio.getText().trim()), Integer.parseInt(tfSelecionarEpFim.getText().trim()));
		            for (Missao missao : missoes) {
		                int numeroTime = missao.getNumeroTime();
		                String objetivo = missao.getObjetivo();
		                int epInicio = missao.getEpInicio();
		                int epFim = missao.getEpFim();
		                char ranking = missao.getRanking();
		                String resultado = missao.getResultado();
		                model.addRow(new Object[] { numeroTime, objetivo, epInicio, epFim, ranking, cbSelecionarTipo.getSelectedItem().toString(), resultado });
		            }
		        }
			}
		});
		
		// === Redefinir === //
		btnRedefinirMissao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) tableMissao.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				ArrayList<Missao> missoes = new ArrayList<>();
				missoes = missaoDAO.getMissoes();
				for (Missao missao : missoes) {
					int numeroTime = missao.getNumeroTime();
					String objetivo = missao.getObjetivo();
					int epInicio = missao.getEpInicio();
					int epFim = missao.getEpFim();
					char ranking = missao.getRanking();
					String tipo = missao.getTipo();
					String resultado = missao.getResultado();
					model.addRow(new Object[] { numeroTime, objetivo, epInicio, epFim, ranking, tipo, resultado });
				}
			}
		});
		
		
		
		
		// ================ //
		// OBSTÁCULO MISSÃO //
		// ================ //
		
		// === Mudar panel para ADICIONAR obstáculo === //
		mntmAdicionarObstaculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelCObstaculoMissao);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				cbNumeroTimeObstaculo.removeAllItems();
				for (Integer missao : missaoDAO.selecionaNumeroMissao()) {
					cbNumeroTimeObstaculo.addItem(missao);
				}
			}
		});
		
		
		// === Adicionar obstáculo === //
		btnAdicionarObstaculoMissao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean cadastroValido = true;
				
				if (tfObjetivoMissao.getText().trim().equals("")) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Não é possível cadastrar um obstáculo sem o objetivo",
							"Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				} else if (StringUtils.isNumeric(tfObjetivoMissao.getText().trim())) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Objetivo inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfObjetivoMissao.getText().trim().length() > 50) {
					cadastroValido = false;
				    JOptionPane.showMessageDialog(null, "Objetivo excede o limite de caracteres (50)", "Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfEpInicioMissao.getText().trim().equals("")) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Não é possível cadastrar um obstáculo sem o episódio de início",
							"Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				} else if (!eIntPositivo(tfEpInicioMissao.getText().trim())) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Episódio de início inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfEpFimMissao.getText().trim().equals("")) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Não é possível cadastrar um obstáculo sem o episódio de fim",
							"Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				} else if (!eIntPositivo(tfEpFimMissao.getText().trim())) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Episódio de fim inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfObstaculoMissao.getText().trim().equals("")) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Não é possível cadastrar um obstáculo sem o obstáculo da missão",
							"Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				} else if (StringUtils.isNumeric(tfObstaculoMissao.getText().trim())) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Obstáculo missão inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfObstaculoMissao.getText().trim().length() > 50) {
					cadastroValido = false;
				    JOptionPane.showMessageDialog(null, "Obstáculo excede o limite de caracteres (50)", "Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				}
				
				if (cadastroValido) {
					int numeroTime =  Integer.parseInt(cbNumeroTimeObstaculo.getSelectedItem().toString());
					String objetivo = WordUtils.capitalizeFully(tfObjetivoMissao.getText().trim(), new char[] {});
					int epInicio = Integer.parseInt(tfEpInicioMissao.getText().trim());
					int epFim = Integer.parseInt(tfEpFimMissao.getText().trim());
					String obstaculo = WordUtils.capitalizeFully(tfObstaculoMissao.getText().trim(), new char[] {});
					if (epFim >= epInicio) {
						if (missaoDAO.consultarMissao(numeroTime, objetivo, epInicio, epFim) == null) {
							cadastroValido = false;
							JOptionPane.showMessageDialog(null, "Missão inexistente", "Cadastro não realizado",
									JOptionPane.WARNING_MESSAGE);
						} else if (missaoDAO.consultarObstaculo(numeroTime, objetivo, epInicio, epFim, obstaculo) != null) {
							cadastroValido = false;
							JOptionPane.showMessageDialog(null, "Obstáculo já existente", "Cadastro não realizado",
									JOptionPane.WARNING_MESSAGE);
						} else {
							missaoDAO.inserirObstaculo(numeroTime, objetivo, epInicio, epFim, obstaculo);
							JOptionPane.showMessageDialog(null, "Obstáculo adicionado com sucesso.", "Sucesso",
									JOptionPane.PLAIN_MESSAGE);
							cbNumeroTimeObstaculo.setSelectedIndex(0);
							tfObjetivoMissao.setText("");
							tfEpInicioMissao.setText("");
							tfEpFimMissao.setText("");
							tfObstaculoMissao.setText("");
						}	
					} else {
						JOptionPane.showMessageDialog(null, "Episódio de início posterior ao episódio de fim",
								"Episódio de início inválido", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		
		// === Mudar panel para VER obstáculos === //
		// === Ver obstáculos === //
		mntmVerObstaculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelRObstaculoMissao);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				DefaultTableModel model = (DefaultTableModel) tableObstaculoMissao.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				List<Object> obstaculos = new ArrayList<>();
				obstaculos = missaoDAO.getObstaculos();
				for (int i = 0; i < obstaculos.size(); i += 5) {
					int numeroTime = (int) obstaculos.get(i);
					String objetivoMissao = (String) obstaculos.get(i + 1);
					int epInicioMissao = (int) obstaculos.get(i + 2);
					int epFimMissao = (int) obstaculos.get(i + 3);
					String obstaculo = (String) obstaculos.get(i + 4);
					model.addRow(new Object[] { numeroTime, objetivoMissao, epInicioMissao, epFimMissao, obstaculo  });
				}
			}
		});
		
		
		// === Mudar panel para EXCLUIR obstáculo === //
		mntmExcluirObstaculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelDObstaculoMissao);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				cbNumeroTimeObstaculoExcluir.removeAllItems();
				for (Integer obstaculo : missaoDAO.selecionaNumeroObstaculo()) {
					cbNumeroTimeObstaculoExcluir.addItem(obstaculo);
				}
			}
		});
		
		// === Excluir obstáculo === //
		btnOK_ExcluirObstaculoMissao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean exclusaoValida = true;
				
				if (tfObjetivoMissaoExcluir.getText().trim().equals("")) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Não é possível excluir um obstáculo sem o objetivo",
							"Exclusão não realizada", JOptionPane.WARNING_MESSAGE);
				} else if (StringUtils.isNumeric(tfObjetivoMissaoExcluir.getText().trim())) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Objetivo inválido", "Exclusão não realizada",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfObjetivoMissaoExcluir.getText().trim().length() > 50) {
					exclusaoValida = false;
				    JOptionPane.showMessageDialog(null, "Objetivo excede o limite de caracteres (50)", "Exclusão não realizada", JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfEpInicioMissaoExcluir.getText().trim().equals("")) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Não é possível excluir um obstáculo sem o episódio de início",
							"Exclusão não realizada", JOptionPane.WARNING_MESSAGE);
				} else if (!eIntPositivo(tfEpInicioMissaoExcluir.getText().trim())) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Episódio de início inválido", "Exclusão não realizada",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfEpFimMissaoExcluir.getText().trim().equals("")) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Não é possível excluir um obstáculo sem o episódio de fim",
							"Exclusão não realizada", JOptionPane.WARNING_MESSAGE);
				} else if (!eIntPositivo(tfEpFimMissaoExcluir.getText().trim())) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Episódio de fim inválido", "Exclusão não realizada",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (tfObstaculoMissaoExcluir.getText().trim().equals("")) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Não é possível excluir um obstáculo sem o obstáculo da missão",
							"Exclusão não realizada", JOptionPane.WARNING_MESSAGE);
				} else if (StringUtils.isNumeric(tfObstaculoMissaoExcluir.getText().trim())) {
					exclusaoValida = false;
					JOptionPane.showMessageDialog(null, "Obstáculo missão inválido", "Exclusão não realizada",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfObstaculoMissaoExcluir.getText().trim().length() > 50) {
					exclusaoValida = false;
				    JOptionPane.showMessageDialog(null, "Obstáculo excede o limite de caracteres (50)", "Exclusão não realizada", JOptionPane.WARNING_MESSAGE);
				}
				
				if (exclusaoValida) {
					int numeroTime = Integer.parseInt(cbNumeroTimeObstaculoExcluir.getSelectedItem().toString());
					String objetivo = WordUtils.capitalizeFully(tfObjetivoMissaoExcluir.getText().trim(), new char[] {});
					int epInicio = Integer.parseInt(tfEpInicioMissaoExcluir.getText().trim());
					int epFim = Integer.parseInt(tfEpFimMissaoExcluir.getText().trim());
					String obstaculo = WordUtils.capitalizeFully(tfObstaculoMissaoExcluir.getText().trim(), new char[] {});
					if (epFim >= epInicio) {
						if (missaoDAO.consultarObstaculo(numeroTime, objetivo, epInicio, epFim, obstaculo) == null) {
							exclusaoValida = false;
							JOptionPane.showMessageDialog(null, "Obstáculo inexistente.", "Exclusão não realizada",
									JOptionPane.WARNING_MESSAGE);
						} else {
							int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir esse obstáculo?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							
							if (resposta == 0) {
								missaoDAO.removerObstaculo(numeroTime, objetivo, epInicio, epFim, obstaculo);
								JOptionPane.showMessageDialog(null, "Obstáculo excluído com sucesso.", "Sucesso",
										JOptionPane.PLAIN_MESSAGE);
								
								cbNumeroTimeObstaculoExcluir.removeAllItems();
								for (Integer obstaculoNumero : missaoDAO.selecionaNumeroObstaculo()) {
									cbNumeroTimeObstaculoExcluir.addItem(obstaculoNumero);
								}
								
								cbNumeroTimeObstaculoExcluir.setSelectedIndex(0);
								tfObjetivoMissaoExcluir.setText("");
								tfEpInicioMissaoExcluir.setText("");
								tfEpFimMissaoExcluir.setText("");
								tfObstaculoMissaoExcluir.setText("");
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Episódio de início posterior ao episódio de fim",
								"Episódio de início inválido", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		
		
		
		// ==== //
		// TIME //
		// ==== //
		
		// === Mudar panel para ADICIONAR time === //
		mntmAdicionarTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelCTime);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});

		// === Adicionar time === //
		btnAdicionarTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean cadastroValido = true;
				
				if (tfNumero.getText().trim().equals("")) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Não é possível cadastrar um time sem o número",
							"Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				} else if (!eIntPositivo(tfNumero.getText().trim())) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Número inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				} else if (timeDAO.consultarTime(Integer.parseInt(tfNumero.getText().trim())) != null) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Time já cadastrado", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if ((!eIntPositivo(tfQuantMembros.getText().trim())) && (!tfQuantMembros.getText().trim().equals(""))) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Quantidade de membros inválida", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if ((StringUtils.isNumeric(tfNomeTime.getText().trim())) && (!tfNomeTime.getText().trim().equals(""))) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Nome inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfNomeTime.getText().trim().length() > 40) {
					cadastroValido = false;
				    JOptionPane.showMessageDialog(null, "Nome excede o limite de caracteres (40)", "Cadastro não realizado", JOptionPane.WARNING_MESSAGE);
				}
				
				if ((!eIntPositivo(tfEpCriacao.getText().trim())) && (!tfEpCriacao.getText().trim().equals(""))) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Episódio de criação inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (cadastroValido) {
					int numero = Integer.parseInt(tfNumero.getText().trim());
					int quantidadeMembros = 0;
					if (!tfQuantMembros.getText().trim().equals("")) {
						quantidadeMembros = Integer.parseInt(tfQuantMembros.getText().trim());
					}
					String nome = WordUtils.capitalizeFully(tfNomeTime.getText().trim());
					if (nome.equals("")) {
						nome = null;
					}
					int epCriacao = 0;
					if (!tfEpCriacao.getText().trim().equals("")) {
						epCriacao = Integer.parseInt(tfEpCriacao.getText().trim());
					}
					Time time = new Time(numero, quantidadeMembros, nome, epCriacao);
					timeDAO.inserirTime(time);
					JOptionPane.showMessageDialog(null, "Time adicionado com sucesso.", "Sucesso",
							JOptionPane.PLAIN_MESSAGE);
					tfNumero.setText("");
					tfQuantMembros.setText("");
					tfNomeTime.setText("");
					tfEpCriacao.setText("");
				}
			}
		});

		
		// === Mudar panel para VER times === //
		// === Ver times === //
		mntmVerTimes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelRTime);
				layeredPane.repaint();
				layeredPane.revalidate();
				DefaultTableModel model = (DefaultTableModel) tableTime.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				ArrayList<Time> times = new ArrayList<>();
				times = timeDAO.getTimes();
				for (Time time : times) {
					int numero = time.getNumero();
					int quantidadeMembros = time.getQuantidadeMembros();
					String nome = time.getNome();
					int epCriacao = time.getEpCriacao();
					int sucessos = consultasSQL.sucessosTime(numero);
					model.addRow(new Object[] { numero, quantidadeMembros, nome, epCriacao, sucessos });
				}
			}
		});

		
		// === Mudar panel para EDITAR time === //
		mntmEditarTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelUTime);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				cbNumero.removeAllItems();
				for (Time time : timeDAO.getTimes()) {
					cbNumero.addItem(Integer.toString(time.getNumero()));
				}
			}
		});

		// === Dados OK para editar === //
		btnOK_EditarTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelUTime_1);
				layeredPane.repaint();
				layeredPane.revalidate();
				Time time = timeDAO.consultarTime(Integer.parseInt(cbNumero.getSelectedItem().toString()));
				
				cbNumeroTime_1.removeAllItems();
				for (Time tN : timeDAO.getTimes()) {
					cbNumeroTime_1.addItem(tN.getNumero());
				}
				
				cbNumeroTime_1.setSelectedItem(Integer.parseInt(cbNumero.getSelectedItem().toString()));
				cbNumeroTime_1.setEnabled(false);
				
				if (time.getQuantidadeMembros() == 0) {
					tfQuantMembros_1.setText("");
				} else {
					tfQuantMembros_1.setText(Integer.toString(time.getQuantidadeMembros()));
				}
				
				tfNomeTime_1.setText(time.getNome());
				
				if (time.getEpCriacao() == 0) {
					tfEpCriacao_1.setText("");
				} else {
					tfEpCriacao_1.setText(Integer.toString(time.getEpCriacao()));
				}
			}
		});

		// === Editar time === //
		btnEditarTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean edicaoValida = true;
				
				if (!eIntPositivo(tfQuantMembros_1.getText().trim()) && (!tfQuantMembros_1.getText().trim().equals(""))) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Quantidade de membros inválida", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (StringUtils.isNumeric(tfNomeTime_1.getText().trim()) && (!tfNomeTime_1.getText().trim().equals(""))) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Nome inválido", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				} else if (tfNomeTime_1.getText().trim().length() > 40) {
					edicaoValida = false;
				    JOptionPane.showMessageDialog(null, "Nome excede o limite de caracteres (40)", "Edição não realizada", JOptionPane.WARNING_MESSAGE);
				}
				
				if (!eIntPositivo(tfEpCriacao_1.getText().trim()) && (!tfEpCriacao_1.getText().trim().equals(""))) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Episódio de criação inválido", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (edicaoValida) {
					int numero =  Integer.parseInt(cbNumeroTime_1.getSelectedItem().toString());
					
					int quantidadeMembros = 0;
					if (!tfQuantMembros_1.getText().trim().equals("")) {
						quantidadeMembros = Integer.parseInt(tfQuantMembros_1.getText().trim());
					}
					
					String nome = WordUtils.capitalizeFully(tfNomeTime_1.getText().trim());
					if (nome.equals("")) {
						nome = null;
					}
					
					int epCriacao = 0;
					if (!tfEpCriacao_1.getText().trim().equals("")) {
						epCriacao = Integer.parseInt(tfEpCriacao_1.getText().trim());
					}
					
					timeDAO.alterarTime(new Time(numero, quantidadeMembros, nome, epCriacao));
					JOptionPane.showMessageDialog(null, "Time atualizado com sucesso.", "Sucesso",
							JOptionPane.PLAIN_MESSAGE);
					cbNumeroTime_1.setSelectedIndex(0);
					tfQuantMembros_1.setText("");
					tfNomeTime_1.setText("");
					tfEpCriacao_1.setText("");
					layeredPane.removeAll();
					layeredPane.add(panelUTime);
					layeredPane.repaint();
					layeredPane.revalidate();
					cbNumero.setSelectedIndex(0);
				}
			}
		});

		
		// === Mudar panel para EXCLUIR time === //
		mntmExcluirTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelDTime);
				layeredPane.repaint();
				layeredPane.revalidate();
				cbNumeroExcluir.removeAllItems();
				for (Time time : timeDAO.getTimes()) {
					cbNumeroExcluir.addItem(Integer.toString(time.getNumero()));
				}
			}
		});

		// === Excluir time === //
		btnOK_ExcluirTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir esse time?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (resposta == 0) {
					ArrayList <Missao> missoes = new ArrayList<>();
	                missoes = missaoDAO.selecionaMissoes(Integer.parseInt(cbNumeroExcluir.getSelectedItem().toString()));
	            	for (Missao missao : missoes) {
	                	missaoDAO.removerMissao(missao);
	            	}
					timeDAO.removerTime(
							timeDAO.consultarTime(Integer.parseInt(cbNumeroExcluir.getSelectedItem().toString())));
					JOptionPane.showMessageDialog(null, "Time excluído com sucesso.", "Sucesso", JOptionPane.PLAIN_MESSAGE);
					cbNumeroExcluir.removeAllItems();
					for (Time time : timeDAO.getTimes()) {
						cbNumeroExcluir.addItem(Integer.toString(time.getNumero()));
					}
				}
			}
		});

		
		// === Selecionar times por ordem de criação === //
		btnOrdenarPorEpCriacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) tableTime.getModel();
		        while (model.getRowCount() > 0) {
		            model.removeRow(0);
		        }
		        for (Time time : consultasSQL.timesCriacao()) {
		            int numero = time.getNumero();
		            int quantMembros = time.getQuantidadeMembros();
		            String nome = time.getNome();
		            int epCriacao = time.getEpCriacao();
		            model.addRow(new Object[] { numero, quantMembros, nome, epCriacao });
		        }
			}
		});
		
		// === Redefinir === //
		btnRedefinirTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) tableTime.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				ArrayList<Time> times = new ArrayList<>();
				times = timeDAO.getTimes();
				for (Time time : times) {
					int numero = time.getNumero();
					int quantidadeMembros = time.getQuantidadeMembros();
					String nome = time.getNome();
					int epCriacao = time.getEpCriacao();
					int sucessos = consultasSQL.sucessosTime(numero);
					model.addRow(new Object[] { numero, quantidadeMembros, nome, epCriacao, sucessos });
				}
			}
		});
		
		
		
		
		// ==================== //
		// NINJA PARTICIPA TIME //
		// ==================== //
		
		// === Mudar panel para ADICIONAR participação === //
		mntmAdicionarParticipacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelCParticipacao);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				cbNomePersonagemParticipacao.removeAllItems();
				for (Ninja ninja : ninjaDAO.getNinjas()) {
					cbNomePersonagemParticipacao.addItem(ninja.getNomePersonagem());
				}
				
				cbNumeroTimeParticipacao.removeAllItems();
				for (Time time : timeDAO.getTimes()) {
					cbNumeroTimeParticipacao.addItem(time.getNumero());
				}
			}
		});
		
		// === Adicionar participação === //
		btnAdicionarParticipacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean cadastroValido = true;
								
				if ((!eIntPositivo(tfEpIngressoParticipacao.getText().trim())) && (!tfEpIngressoParticipacao.getText().trim().equals(""))) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Episódio de ingresso inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if ((!eIntPositivo(tfEpSaidaParticipacao.getText().trim())) && (!tfEpSaidaParticipacao.getText().trim().equals(""))) {
					cadastroValido = false;
					JOptionPane.showMessageDialog(null, "Episódio de saída inválido", "Cadastro não realizado",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (cadastroValido) {
					String nomePersonagem = cbNomePersonagemParticipacao.getSelectedItem().toString();
					int numeroTime = Integer.parseInt(cbNumeroTimeParticipacao.getSelectedItem().toString());
					String funcao = cbFuncaoParticipacao.getSelectedItem().toString();
					if (funcao.equals("-")) {
						funcao = null;
					}
					int epIngresso = 0;
					if (!tfEpIngressoParticipacao.getText().trim().equals("")) {
						epIngresso = Integer.parseInt(tfEpIngressoParticipacao.getText().trim());
					}
					int epSaida = 0;
					if (!tfEpSaidaParticipacao.getText().trim().equals("")) {
						epSaida = Integer.parseInt(tfEpSaidaParticipacao.getText().trim());
					}
					
					if (epIngresso <= epSaida) {
						if (NPTDAO.consultarNPT(nomePersonagem, numeroTime) != null) {
							cadastroValido = false;
							JOptionPane.showMessageDialog(null, "Participação já existente", "Cadastro não realizado",
									JOptionPane.WARNING_MESSAGE);
						} else {
							NinjaParticipaTime NPT = new NinjaParticipaTime(nomePersonagem, numeroTime, funcao, epIngresso, epSaida);
							NPTDAO.inserirNPT(NPT);
							JOptionPane.showMessageDialog(null, "Participação adicionada com sucesso.", "Sucesso",
									JOptionPane.PLAIN_MESSAGE);
							cbNomePersonagemParticipacao.setSelectedIndex(0);
							cbNumeroTimeParticipacao.setSelectedIndex(0);
							cbFuncaoParticipacao.setSelectedItem("-");
							tfEpIngressoParticipacao.setText("");
							tfEpSaidaParticipacao.setText("");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Episódio de início posterior ao episódio de fim",
								"Episódio de início inválido", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		
		// === Mudar panel para VER participações === //
		// === Ver participações === //
		mntmVerParticipacoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelRParticipacao);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				DefaultTableModel model = (DefaultTableModel) tableParticipacao.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				ArrayList<NinjaParticipaTime> NPTs = new ArrayList<>();
				NPTs = NPTDAO.getNPTs();
				for (NinjaParticipaTime NPT : NPTs) {
					String nomePersonagem = NPT.getNomePersonagem();
					int numeroTime = NPT.getNumeroTime();
					String funcao = NPT.getFuncao();
					int epIngresso = NPT.getEpIngresso();
					int epSaida = NPT.getEpSaida();
					if (epIngresso == 0) {
						if (epSaida == 0) {
							model.addRow(new Object[] { numeroTime, nomePersonagem, funcao, "", "" });
						} else {
							model.addRow(new Object[] { numeroTime, nomePersonagem, funcao, "", Integer.toString(epSaida) });
						}
					} else {
						if (epSaida != 0) {
							model.addRow(new Object[] { numeroTime, nomePersonagem, funcao,
									Integer.toString(epIngresso), Integer.toString(epSaida) });
						} else {
							model.addRow(new Object[] { numeroTime, nomePersonagem, funcao,
									Integer.toString(epIngresso), "" });
						}
					}
				}
			}
		});
		
		
		// === Mudar panel para EDITAR participação === //
		mntmEditarParticipacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelUParticipacao);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				cbNomePersonagemParticipacaoEditar.removeAllItems();
				for (String nome : NPTDAO.selecionaNomesNPT()) {
					cbNomePersonagemParticipacaoEditar.addItem(nome);
				}
				
				cbNumeroTimeParticipacaoEditar.removeAllItems();
				for (Integer numero : NPTDAO.selecionaNumeroNPT()) {
					cbNumeroTimeParticipacaoEditar.addItem(numero);
				}
			}
		});
		
		// === Dados OK para editar === //
		btnOK_EditarParticipacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomePersonagem = cbNomePersonagemParticipacaoEditar.getSelectedItem().toString();
				int numeroTime = Integer.parseInt(cbNumeroTimeParticipacaoEditar.getSelectedItem().toString());
				if (NPTDAO.consultarNPT(nomePersonagem, numeroTime) == null) {
					JOptionPane.showMessageDialog(null, "Participação inexistente", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				} else {
					NPTDAO.alterarNPT(NPTDAO.consultarNPT(nomePersonagem, numeroTime));
											
					layeredPane.removeAll();
					layeredPane.add(panelUParticipacao_1);
					layeredPane.repaint();
					layeredPane.revalidate();
					
					NinjaParticipaTime NPT = NPTDAO.consultarNPT(nomePersonagem, numeroTime);
					
					cbNomePersonagemParticipacaoEditar_1.removeAllItems();
					for (String nome : NPTDAO.selecionaNomesNPT()) {
						cbNomePersonagemParticipacaoEditar_1.addItem(nome);
					}
					
					cbNumeroTimeParticipacaoEditar_1.removeAllItems();
					for (Integer numero : NPTDAO.selecionaNumeroNPT()) {
						cbNumeroTimeParticipacaoEditar_1.addItem(numero);
					}
					
					cbNomePersonagemParticipacaoEditar_1.setSelectedItem(nomePersonagem);
					cbNumeroTimeParticipacaoEditar_1.setSelectedItem(Integer.toString(numeroTime));
					cbFuncaoParticipacao.setSelectedItem((NPT.getFuncao()));
					if (NPT.getEpIngresso() == 0) {
						tfEpIngressoEditarParticipacao_1.setText("");
                    } else {
                    	tfEpIngressoEditarParticipacao_1.setText(Integer.toString(NPT.getEpIngresso()));
                    }
                    if (NPT.getEpSaida() == 0) {
                    	tfEpSaidaEditarParticipacao_1.setText("");
                    } else {
                    	tfEpSaidaEditarParticipacao_1.setText(Integer.toString(NPT.getEpSaida()));
                    }
                    cbNomePersonagemParticipacaoEditar_1.setEnabled(false);
                    cbNumeroTimeParticipacaoEditar_1.setEnabled(false);
					
                    cbNomePersonagemParticipacaoEditar.setSelectedIndex(0);
					cbNumeroTimeParticipacaoEditar.setSelectedIndex(0);
				}
			}
		});
		
		// === Editar participação === //
		btnEditarParticipacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean edicaoValida = true;
								
				if ((!eIntPositivo(tfEpIngressoEditarParticipacao_1.getText().trim())) && (!tfEpIngressoEditarParticipacao_1.getText().trim().equals(""))) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Episódio de ingresso inválido", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				}
								
				if ((!eIntPositivo(tfEpSaidaEditarParticipacao_1.getText().trim())) && (!tfEpSaidaEditarParticipacao_1.getText().trim().equals(""))) {
					edicaoValida = false;
					JOptionPane.showMessageDialog(null, "Episódio de saída inválido", "Edição não realizada",
							JOptionPane.WARNING_MESSAGE);
				}
				
				if (edicaoValida) {
					String nomePersonagem = cbNomePersonagemParticipacaoEditar_1.getSelectedItem().toString();
					int numeroTime = Integer.parseInt(cbNumeroTimeParticipacaoEditar_1.getSelectedItem().toString());
					String funcao = cbFuncaoEditarParticipacao.getSelectedItem().toString();
					if (funcao.equals("-")) {
						funcao = null;
					}
					int epIngresso = 0;
					if (!tfEpIngressoEditarParticipacao_1.getText().trim().equals("")) {
						epIngresso = Integer.parseInt(tfEpIngressoEditarParticipacao_1.getText().trim());
					}
					int epSaida = 0;
					if (!tfEpSaidaEditarParticipacao_1.getText().trim().equals("")) {
						epSaida = Integer.parseInt(tfEpSaidaEditarParticipacao_1.getText().trim());
					}
					
					if (epIngresso <= epSaida) {
						NPTDAO.alterarNPT(new NinjaParticipaTime(nomePersonagem, numeroTime, funcao, epIngresso, epSaida));
						JOptionPane.showMessageDialog(null, "Participação atualizada com sucesso", "Sucesso",
								JOptionPane.PLAIN_MESSAGE);
						cbNomePersonagemParticipacaoEditar.setSelectedIndex(0);
						cbNumeroTimeParticipacaoEditar.setSelectedIndex(0);
						cbFuncaoEditarParticipacao.setSelectedItem("-");
						tfEpIngressoParticipacao.setText("");
						tfEpSaidaParticipacao.setText("");
						layeredPane.removeAll();
						layeredPane.add(panelUParticipacao);
						layeredPane.repaint();
						layeredPane.revalidate();
					} else {
						JOptionPane.showMessageDialog(null, "Episódio de início posterior ao episódio de fim",
								"Episódio de início inválido", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		
		// === Mudar panel para EXCLUIR participação === //
		mntmExcluirParticipacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panelDParticipacao);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				cbNomePersonagemParticipacaoExcluir.removeAllItems();
				for (String nome : NPTDAO.selecionaNomesNPT()) {
					cbNomePersonagemParticipacaoExcluir.addItem(nome);
				}
				
				cbNumeroTimeParticipacaoExcluir.removeAllItems();
				for (Integer numero : NPTDAO.selecionaNumeroNPT()) {
					cbNumeroTimeParticipacaoExcluir.addItem(numero);
				}
			}
		});
		
		// === Excluir participação === //
		btnOK_ExcluirParticipacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomePersonagem = cbNomePersonagemParticipacaoExcluir.getSelectedItem().toString();
				int numeroTime = Integer.parseInt(cbNumeroTimeParticipacaoExcluir.getSelectedItem().toString());
				if (NPTDAO.consultarNPT(nomePersonagem, numeroTime) == null) {
					JOptionPane.showMessageDialog(null, "Participação inexistente", "Exclusão não realizada",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir essa participação?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if (resposta == 0) {
						NPTDAO.removerNPT(NPTDAO.consultarNPT(nomePersonagem, numeroTime));						
						JOptionPane.showMessageDialog(null, "Participação excluída com sucesso", "Sucesso", JOptionPane.PLAIN_MESSAGE);
						cbNomePersonagemParticipacaoExcluir.setSelectedIndex(0);
						cbNumeroTimeParticipacaoExcluir.setSelectedIndex(0);
					}
				}
			}
		});
		
		
		
		
		// ============= //
		// RADIO BUTTONS //
		// ============= //
		rdbtnM.addActionListener((ActionEvent e) -> rdbtnF.setSelected(false));

		rdbtnF.addActionListener((ActionEvent e) -> rdbtnM.setSelected(false));

		rdbtnM_1.addActionListener((ActionEvent e) -> rdbtnF_1.setSelected(false));

		rdbtnF_1.addActionListener((ActionEvent e) -> rdbtnM_1.setSelected(false));

		rdbtnSucesso.addActionListener((ActionEvent e) -> rdbtnFracasso.setSelected(false));

		rdbtnFracasso.addActionListener((ActionEvent e) -> rdbtnSucesso.setSelected(false));

		rdbtnSucesso_1.addActionListener((ActionEvent e) -> rdbtnFracasso_1.setSelected(false));

		rdbtnFracasso_1.addActionListener((ActionEvent e) -> rdbtnSucesso_1.setSelected(false));
	}

	

	
	// ====================== //
	// CUSTOMIZAÇÃO DA TABELA //
	// ====================== //
	public class cell extends DefaultTableCellRenderer {
		public cell() {
		}

		@Override
		public JLabel getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			label.setBackground(new Color(130, 130, 130));
			return label;
		}
	}

	public class header extends JLabel implements TableCellRenderer {
		public header(JScrollPane scrollPane) {
			setOpaque(true);
			setBackground(Color.DARK_GRAY);
			setForeground(Color.WHITE);
			Border roundedBorder = new LineBorder(new Color(151, 151, 151), 1, true);
			scrollPane.setBorder(roundedBorder);
		}

		public header getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText(value.toString());
			return this;
		}
	}
}

