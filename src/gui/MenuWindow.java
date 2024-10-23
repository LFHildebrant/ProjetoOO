package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JDateChooser;

import entities.Agenda;
import entities.Compromisso;
import entities.Usuario;
import service.AgendaService;
import service.CompromissoService;
import service.UsuarioService;

public class MenuWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tbComp;
    private JButton btnFiltrar;
    private JButton agendasButton;
    private JButton compromissosButton;
    private JButton perfilButton;
    private JButton btSair;
    private JButton newAgendaButton;
    private JDateChooser dtFinal;
    private JDateChooser dtInicial;
    private boolean mostrarAgenda;
    private JPanel pnAgenda;
    private JPanel pnTodasAgendas;
    private AgendaService agendaServ;
    private CompromissoService compServ;
    private JLabel lbUsuario;
    private JLabel lbTitulo;
    private ButtonGroup grupoBtAgendas;
    private UsuarioWindow usuario;
    private CriarCompromissoWindow compWindow;
    private CriarAgendaWindow addAgenda;
    private LoginWindow login;
	private List<Agenda> agendas;
	private JButton btnAdicionarNovaAgenda;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MenuWindow frame = new MenuWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MenuWindow() {
    	this.compServ = new CompromissoService();
    	this.agendaServ = new AgendaService();
        inicializarComponentes();
        mostrarAgenda = true;
        btnAdicionarNovaAgenda.setVisible(false);
    }
    
    private void mostrarPnAgenda() {
        if(mostrarAgenda) {
        	mostrarAgenda = false;
        	this.pnAgenda.setVisible(true);
        	this.lbTitulo.setText("Minhas Agendas");
        	listarAgendasPainel();
        	pnTodasAgendas.setVisible(true);
        	btnAdicionarNovaAgenda.setVisible(true);
        } else {
        	mostrarAgenda = true;
        	this.pnAgenda.setVisible(false);
        	this.lbTitulo.setText("Menu");
        	pnTodasAgendas.setVisible(false);
        	btSair.setVisible(false);
        }
    }
    
    
    private void listarAgendasPainel() {
    	final int tamanhoPn = 5;
    	int valorY = 5;
    	try {
    		
			if(agendas != null) {
				agendas.clear();
				removeAgendasPainel();
			}
			agendas = agendaServ.listarTodos(LoginWindow.getUsuarioAtual().getLogin());
			grupoBtAgendas = new ButtonGroup();
			for (Agenda agenda : agendas) {
				JRadioButton novoRadioBtn = new JRadioButton(agenda.getNome());
				System.out.println(agenda.getNome() + agenda.getIdAgenda());
				novoRadioBtn.setFont(new Font("Segoe UI", Font.PLAIN, 9));
				novoRadioBtn.setBackground(new Color(171, 171, 171));
				novoRadioBtn.setBounds(6, valorY, 145, 21);
				novoRadioBtn.setName(Integer.toString(agenda.getIdAgenda()));
				pnTodasAgendas.add(novoRadioBtn);
				grupoBtAgendas.add(novoRadioBtn);
				valorY += 25;
			}
	        pnTodasAgendas.setBounds(13, 76, 157, tamanhoPn + valorY);
			
		} catch (SQLException | IOException e) {
    		JOptionPane.showMessageDialog(this, "Erro ao alimentar tabela - " + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
		}
    }
    
    private void removeAgendasPainel() {
    	Component[] components = pnTodasAgendas.getComponents();

    	for (Component component : components) {
    	    if (component instanceof JRadioButton) {
    	        pnTodasAgendas.remove(component);
    	    }
    	}
    }
    
    private int buscarAgendaSelecionada() {

    	try {
	    	for (Component comp : pnTodasAgendas.getComponents()) {
	            if (comp instanceof JRadioButton) {
	                JRadioButton radioButton = (JRadioButton) comp;
	                if (radioButton.isSelected()) {
	                    return Integer.parseInt(radioButton.getName());
	                }
	            }
	        }
    	} catch(NumberFormatException e) {
    		JOptionPane.showMessageDialog(this, "Erro ao buscar agendas - " + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
    	}
    	
    	return 0;
	}
    
    private void abrirCadastro() {
		usuario = new UsuarioWindow();
		usuario.setVisible(true);
		this.dispose();
	}
    
    private void abrirCompromisso() {
		compWindow = new CriarCompromissoWindow();
		compWindow.setVisible(true);
	}
    
    private void abrirLogin() {
		login = new LoginWindow();
		login.setVisible(true);
		this.dispose();
	}
    
    private void abrirNovaAgenda() {
    	addAgenda = new CriarAgendaWindow();
    	addAgenda.setVisible(true);
    	mostrarAgenda = false;
    	mostrarPnAgenda();
    }
    
    private void listarCompromissosAgenda() {
    	 
    	try {
    	    boolean encontrouComp = false;
    	    int idAgenda = buscarAgendaSelecionada();
    	    if(idAgenda != 0) {
    	    	
    	    	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    	    	SimpleDateFormat formatoDataHora = new SimpleDateFormat("dd/MM/yyyy | HH:mm");
    	    	
    	    	DefaultTableModel modelo = (DefaultTableModel) tbComp.getModel();
    	    	modelo.fireTableDataChanged();
    	    	modelo.setRowCount(0);
    	    	
    	    	Date dataInicial = dtInicial.getDate();
    	    	Date dataFinal = dtFinal.getDate();
    	    	
    	    	List<Compromisso> compromissos = compServ.buscarTodosCompromissos(LoginWindow.getUsuarioAtual().getLogin(), idAgenda);
    	    	
    	    	for (Compromisso comp : compromissos) {
    	    		Date dataInicio = comp.getDataInicio();
    	    		boolean incluir = true;
    	    		
    	    		if (dataInicial != null && dataInicio.before(dataInicial)) {
    	    			incluir = false;
    	    		}
    	    		
    	    		if (dataFinal != null && dataInicio.after(dataFinal)) {
    	    			incluir = false;
    	    		}
    	    		
    	    		if (incluir) {
    	    			String dataAtual = formato.format(dataInicio);
    	    			modelo.addRow(new Object[] {
    	    					dataAtual,
    	    					comp.getTitulo(),
    	    					comp.getDescricao(),
    	    					comp.getHoraInicio(),
    	    					comp.getHoraTermino(),
    	    					formato.format(comp.getDataTermino()),
    	    					comp.getLocal(),
    	    					" - ",
    	    					formatoDataHora.format(comp.getMomentoNotificacao())
    	    			});
    	    			encontrouComp = true;
    	    		}
    	    	}
    	    	
    	    	if (!encontrouComp) {
    	    		int opcao = JOptionPane.showConfirmDialog(this, "Nenhum compromisso encontrado!\n\n Deseja adicionar um novo compromisso?", "ATENÇÃO!", JOptionPane.INFORMATION_MESSAGE);
    	    		if (opcao == 0) {
    	    			abrirCompromisso();
    	    		}
    	    	}
    	    } else {
        	    JOptionPane.showMessageDialog(this, "Selecione uma agenda!", "ATENÇÃO!", JOptionPane.WARNING_MESSAGE);
    	    }

    	} catch (SQLException | IOException e) {
    	    JOptionPane.showMessageDialog(this, "Erro ao listar compromissos - " + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
    	}

    
    }
    
    private void inicializarComponentes() {
        setTitle("Menu Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(844, 528);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null); // Absolute Layout

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null); // Absolute Layout
        headerPanel.setBounds(0, 0, 830, 40);
        lbUsuario = new JLabel("Usuário");
        lbUsuario.setText(LoginWindow.getUsuarioAtual().getLogin().toUpperCase());
        lbUsuario.setForeground(new Color(255, 255, 255));
        lbUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbUsuario.setBounds(33, 6, 155, 30);
        headerPanel.add(lbUsuario);
        headerPanel.setBackground(new Color(54, 54, 54)); // Background color for better visibility

        // Menu Lateral
        JPanel pnMenu = new JPanel();
        pnMenu.setLayout(null); // Absolute Layout
        pnMenu.setBounds(0, 40, 184, 441);
        agendasButton = new JButton("Gerenciar Compromissos");
        agendasButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		abrirCompromisso();
        	}
        });
        agendasButton.setBackground(new Color(171, 171, 171));
        agendasButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
        agendasButton.setBounds(10, 10, 164, 30);
        compromissosButton = new JButton("Minhas Agendas");
        compromissosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPnAgenda();
            }
        });
        compromissosButton.setBackground(new Color(171, 171, 171));
        compromissosButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
        compromissosButton.setBounds(10, 48, 164, 30);
        perfilButton = new JButton("Meu Perfil");
        perfilButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		abrirCadastro();
        	}
        });
        perfilButton.setBackground(new Color(171, 171, 171));
        perfilButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
        perfilButton.setBounds(10, 401, 164, 30);
        pnMenu.add(agendasButton);
        pnMenu.add(compromissosButton);
        pnMenu.add(perfilButton);
        pnMenu.setBackground(new Color(171, 171, 171)); 

        JPanel pnGeral = new JPanel();
        pnGeral.setLayout(null);
        pnGeral.setBounds(188, 40, 632, 441);


        pnAgenda = new JPanel();
        pnAgenda.setVisible(false);
        pnAgenda.setLayout(null); 
        pnAgenda.setBounds(10, 42, 612, 389);
        JLabel agendaListTitle = new JLabel("Minhas Agendas");
        agendaListTitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        agendaListTitle.setBounds(10, 10, 200, 30);
        newAgendaButton = new JButton("Nova Agenda");
        newAgendaButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        newAgendaButton.setBounds(455, 10, 135, 30);

        pnGeral.add(pnAgenda);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBackground(new Color(126, 126, 126));
        separator_1.setBounds(10, 49, 592, 1);
        pnAgenda.add(separator_1);

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(null);
        panel_1.setBounds(0, 60, 612, 320);
        pnAgenda.add(panel_1);

        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		listarCompromissosAgenda();
        	}
        });
        btnFiltrar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnFiltrar.setBackground(Color.WHITE);
        btnFiltrar.setBounds(449, 10, 68, 30);
        pnAgenda.add(btnFiltrar);

        dtFinal = new JDateChooser();
        dtFinal.setDateFormatString("dd/MM/yyyy");
        dtFinal.getCalendarButton().setBackground(Color.WHITE);
        dtFinal.setBounds(287, 17, 152, 19);
        pnAgenda.add(dtFinal);

        JLabel lblDataFinal = new JLabel("Data Final");
        lblDataFinal.setForeground(new Color(54, 54, 54));
        lblDataFinal.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDataFinal.setBounds(232, 11, 55, 28);
        pnAgenda.add(lblDataFinal);

        dtInicial = new JDateChooser();
        dtInicial.setDateFormatString("dd/MM/yyyy");
        dtInicial.getCalendarButton().setBackground(Color.WHITE);
        dtInicial.setBounds(70, 17, 152, 19);
        pnAgenda.add(dtInicial);

        JLabel lblDataInicial = new JLabel("Data Inicial");
        lblDataInicial.setForeground(new Color(54, 54, 54));
        lblDataInicial.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDataInicial.setBounds(10, 11, 60, 28);
        pnAgenda.add(lblDataInicial);

        getContentPane().add(headerPanel);

        btSair = new JButton("Sair");
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	abrirLogin();
            }
        });
        btSair.setForeground(new Color(255, 255, 255));
        btSair.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btSair.setBackground(new Color(54, 54, 54));
        btSair.setBounds(713, 8, 107, 26);
        headerPanel.add(btSair);
        getContentPane().add(pnMenu);

        JSeparator separator = new JSeparator();
        separator.setBackground(new Color(54, 54, 54));
        separator.setBounds(10, 389, 164, 2);
        pnMenu.add(separator);

        pnTodasAgendas = new JPanel();
        pnTodasAgendas.setBackground(new Color(171, 171, 171));
        pnTodasAgendas.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnMenu.add(pnTodasAgendas);
        pnTodasAgendas.setLayout(null);
        getContentPane().add(pnGeral);

        lbTitulo = new JLabel("Menu");
        lbTitulo.setForeground(new Color(54, 54, 54));
        lbTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lbTitulo.setBounds(10, 10, 155, 30);
        pnGeral.add(lbTitulo);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 592, 300);
        panel_1.add(scrollPane);

        tbComp = new JTable();
        tbComp.setModel(new DefaultTableModel(
            new Object[][] {
            },
            new String[] {
                "Data", "Título", "Desc", "Hora Inicial", "Hora Final", "Data Final", "Local", "Convidados", "Notificação"
            }
        ));


        tbComp.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumn column = null;
        int[] columnWidths = {80, 100, 250, 100, 100, 80, 100, 350, 130};
        for (int i = 0; i < columnWidths.length; i++) {
            column = tbComp.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        scrollPane.setViewportView(tbComp);
        
        btnAdicionarNovaAgenda = new JButton("Adicionar Agenda");
        btnAdicionarNovaAgenda.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		abrirNovaAgenda();
        	}
        });
        btnAdicionarNovaAgenda.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnAdicionarNovaAgenda.setBackground(Color.WHITE);
        btnAdicionarNovaAgenda.setBounds(484, 10, 140, 30);
        pnGeral.add(btnAdicionarNovaAgenda);
    }
}
