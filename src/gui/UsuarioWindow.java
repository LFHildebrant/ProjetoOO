package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

import entities.Agenda;
import entities.Usuario;
import service.AgendaService;
import service.UsuarioService;

public class UsuarioWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	private JTextField txtNome;
	private JTextField txtEmail;
	private JFileChooser arquivo;
	private JLabel lbFoto;
	private File arquivoSelecionado;
	private MaskFormatter mascaraData;
	private ButtonGroup grupoBotao;
	private JRadioButton rdbtnFeminino;
	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnNaoInformar;
	private LoginWindow login;
	private MenuWindow menu;
	private JPanel pnInformacoes;
	private JFormattedTextField txtDataNasc;
	private final Color vermelho;
	private final Color padrao;
	private Panel pnFoto;
	private UsuarioService usuarios;
	private File caminhoImagemUsuario;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnCadastrar;
	private JLabel lbUsuario;
	private Usuario usuarioAtual;
	private AgendaService agendaServ;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioWindow frame = new UsuarioWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UsuarioWindow() {
		usuarioAtual = LoginWindow.getUsuarioAtual();
		criarMascara();
		inicializarComponentes();
		verificarUsuarioLogado();
		vermelho = new Color(224, 144, 129);
		padrao = new Color(255, 255, 255);
		this.usuarios = new UsuarioService();
		this.agendaServ = new AgendaService();
	}
	
	private void criarMascara() {
		try {
			mascaraData = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String retornarSexo() {
		if(rdbtnFeminino.isSelected()) {
			return "Feminino";
		} else if(rdbtnMasculino.isSelected()) {
			return "Masculino";
		} else {
			return "Nao Informado";
		}
	}
	
	private void selecionaSexo(String sexo) {
		if(sexo.equals(rdbtnFeminino.getText())) {
			rdbtnFeminino.setSelected(true);
		} else if(sexo.equals(rdbtnMasculino.getText())) {
			rdbtnMasculino.setSelected(true);
		} else {
			rdbtnNaoInformar.setSelected(true);
		}
	}
	
	private void verificarUsuarioLogado() {
		if(usuarioAtual != null) {
			trazerDadosUsuario();
			this.btnEditar.setVisible(true);
			this.btnExcluir.setVisible(true);
			this.btnCadastrar.setVisible(false);
			
			this.lbUsuario.setEnabled(false);
			this.txtUsuario.setEnabled(false);
		} else {
			this.btnEditar.setVisible(false);
			this.btnExcluir.setVisible(false);
			this.btnCadastrar.setVisible(true);
			
			this.lbUsuario.setEnabled(true);
			this.txtUsuario.setEnabled(true);
		}
	}
	
	private void cadastrarUsuario() {
		try {
			if(!verificarCampos()) {
				SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
				Usuario novoUsuario = new Usuario();
				novoUsuario.setNomeCompleto(this.txtNome.getText());
				novoUsuario.setGenero(retornarSexo());
				
				novoUsuario.setDataNascimento(new java.sql.Date(formatar.parse(this.txtDataNasc.getText()).getTime()));
				novoUsuario.setEmail(this.txtEmail.getText());
				novoUsuario.setLogin(this.txtUsuario.getText());
				novoUsuario.setSenha(this.txtSenha.getText());
				copiarArquivo();
				novoUsuario.setFotoPessoal(caminhoImagemUsuario.getPath());
				
				usuarios.cadastrar(novoUsuario);
				
				JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!", "CONCLUÍDO!", JOptionPane.INFORMATION_MESSAGE);
				abrirLogin();
			} else {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "ATENÇÃO!", JOptionPane.WARNING_MESSAGE);	
			}
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(this, "Erro ao formatar a data - " + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
		} catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar - " + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
			
		}
	}

	private void trazerDadosUsuario() {
		SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
		
		this.txtNome.setText(usuarioAtual.getNomeCompleto());
		this.txtDataNasc.setText(formatar.format(usuarioAtual.getDataNascimento()));
		this.txtEmail.setText(usuarioAtual.getEmail());
		this.txtUsuario.setText(usuarioAtual.getLogin());
		this.txtSenha.setText(usuarioAtual.getSenha());
		carregarImagemUsuario(usuarioAtual.getFotoPessoal());
		selecionaSexo(usuarioAtual.getGenero());
		
	}
	
	private void editarUsuario() {
		
		try {
			if(!verificarCampos()) {			
				SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
				
				usuarioAtual.setNomeCompleto(this.txtNome.getText());
				usuarioAtual.setGenero(retornarSexo());
				
				usuarioAtual.setDataNascimento(new java.sql.Date(formatar.parse(this.txtDataNasc.getText()).getTime()));
				usuarioAtual.setEmail(this.txtEmail.getText());
				usuarioAtual.setLogin(this.txtUsuario.getText());
				usuarioAtual.setSenha(this.txtSenha.getText());
				copiarArquivo();
				usuarioAtual.setFotoPessoal(caminhoImagemUsuario.getPath());
				
				usuarios.editar(usuarioAtual);
				JOptionPane.showMessageDialog(null, "Edição realizada com sucesso!", "CONCLUÍDO!", JOptionPane.INFORMATION_MESSAGE);
				abrirMenu();
			} else {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "ATENÇÃO!", JOptionPane.WARNING_MESSAGE);	
			}
			
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(this, "Erro ao formatar a data - " + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
		}  catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao editar - " + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void excluirUsuario() {
		try {
			int opcao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir permanentemente a sua Conta?", "ATENÇÃO!", JOptionPane.INFORMATION_MESSAGE);
    		if (opcao == 0) {
    			List<Agenda> agendasUsuario = agendaServ.listarTodos(usuarioAtual.getLogin());
    			for (Agenda agenda : agendasUsuario) {
					agendaServ.excluir(agenda.getIdAgenda(), usuarioAtual.getLogin());
				}
    			usuarios.excluir(usuarioAtual.getLogin());
    			JOptionPane.showMessageDialog(null, "Conta deletada com sucesso.\n\nPor favor realize o login novamente com outra conta ou faça um novo cadastro!", "CONCLUÍDO!", JOptionPane.INFORMATION_MESSAGE);
    			abrirLogin();
    		}
			
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao excluir - " + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private void copiarArquivo() {
		caminhoImagemUsuario = new File("D:\\Eclipse\\workspace\\github\\ProjetoAtual\\src\\images\\" + arquivoSelecionado.getName());
		Path caminhoOrigem = arquivoSelecionado.toPath();
        Path caminhoDestino = caminhoImagemUsuario.toPath();
        try {
            Files.copy(caminhoOrigem, caminhoDestino, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao copiar o arquivo: " + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
        }
    
	}
	
	public void carregarImagemUsuario(String caminhoArquivo) {
	    File arquivoUsuario = new File(caminhoArquivo);
	    
	    if (arquivoUsuario.exists()) {
	        ImageIcon icone = new ImageIcon(arquivoUsuario.getAbsolutePath());
	        
	        int tamanhoMax = Math.min(lbFoto.getWidth(), lbFoto.getHeight());
	        Image imagem = icone.getImage().getScaledInstance(tamanhoMax, tamanhoMax, Image.SCALE_SMOOTH);
	        
	        ImageIcon iconeUsuario = new ImageIcon(imagem);
	        lbFoto.setIcon(iconeUsuario);
	    } else {
	        JOptionPane.showMessageDialog(this, "Arquivo não encontrado: " + caminhoArquivo, "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}

	
	public boolean verificarCampos() {
		boolean vazio = false;
		Component[] campos = pnInformacoes.getComponents();
		for (Component campo : campos) {
			if (campo instanceof JTextComponent) {
				if(((JTextComponent)campo).getText().isEmpty()) {
					((JTextComponent) campo).setBackground(vermelho);;
					vazio = true;					
				} else {
					((JTextComponent) campo).setBackground(padrao);;
	            }
			}
		}
		
		if(!rdbtnFeminino.isSelected() && !rdbtnMasculino.isSelected() && !rdbtnNaoInformar.isSelected()) {
			vazio = true;
			rdbtnFeminino.setBackground(vermelho);
			rdbtnMasculino.setBackground(vermelho);
			rdbtnNaoInformar.setBackground(vermelho);
		} else {
			rdbtnFeminino.setBackground(padrao);
			rdbtnMasculino.setBackground(padrao);
			rdbtnNaoInformar.setBackground(padrao);
		}
		
		if(usuarioAtual == null) {
			if(arquivoSelecionado == null) {
				vazio = true;
				pnFoto.setBackground(vermelho);
			} else {
				pnFoto.setBackground(new Color(223, 223, 223));
			}
		} else {
			arquivoSelecionado = new File(usuarioAtual.getFotoPessoal());
		}
		return vazio;
	}
	
	public void procurarArquivo() {
		int arquivoEscolhido = arquivo.showOpenDialog(null);
		if(arquivoEscolhido == JFileChooser.APPROVE_OPTION) {
			arquivoSelecionado = arquivo.getSelectedFile();
			ImageIcon icone = new ImageIcon(arquivoSelecionado.getAbsolutePath());
			int tamanhoMax = Math.min(lbFoto.getWidth(), lbFoto.getHeight());
			Image imagem = icone.getImage().getScaledInstance(tamanhoMax, tamanhoMax, Image.SCALE_SMOOTH);
			ImageIcon iconeUsuario = new ImageIcon(imagem);
			lbFoto.setIcon(iconeUsuario);
		}
	}
	
	private void abrirMenu() {
		menu = new MenuWindow();
		menu.setVisible(true);
		this.dispose();
	}
	
	private void abrirLogin() {
		login = new LoginWindow();
		login.setVisible(true);
		this.dispose();
	}
	
	private void inicializarComponentes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 380);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCadastro = new JLabel("USUÁRIO");
		lblCadastro.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastro.setFont(new Font("Nirmala UI", Font.PLAIN, 20));
		lblCadastro.setBounds(0, 10, 416, 34);
		contentPane.add(lblCadastro);
		
		btnCadastrar = new JButton("SALVAR");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarUsuario();
			}
		});
		btnCadastrar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnCadastrar.setBounds(149, 307, 118, 25);
		contentPane.add(btnCadastrar);
		
		pnInformacoes = new JPanel();
		pnInformacoes.setBorder(
				new TitledBorder(
						new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), 
						new Color(160, 160, 160)),
						"Informa\u00E7\u00F5es",
						TitledBorder.LEADING,
						TitledBorder.TOP, 
						new Font("Segoe UI", Font.PLAIN, 11),
						new Color(0, 0, 0))
				);
		pnInformacoes.setBounds(11, 40, 394, 254);
		contentPane.add(pnInformacoes);
		pnInformacoes.setLayout(null);
		
		arquivo = new JFileChooser();
		arquivo.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png", "gif");
        arquivo.setFileFilter(filter);
		
		pnFoto = new Panel();
		pnFoto.setBackground(new Color(223, 223, 223));
		pnFoto.setBounds(280, 44, 75, 90);
		pnInformacoes.add(pnFoto);
		pnFoto.setLayout(null);
		
		JButton btProcurar = new JButton("Procurar");
		btProcurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				procurarArquivo();
			}
		});
		btProcurar.setBounds(0, 69, 75, 21);
		pnFoto.add(btProcurar);
		btProcurar.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		
		lbFoto = new JLabel("");
		lbFoto.setBounds(0, 0, 75, 75);
		pnFoto.add(lbFoto);
		
		JLabel lblNomeCompleto = new JLabel("NOME COMPLETO");
		lblNomeCompleto.setBounds(10, 28, 112, 13);
		pnInformacoes.add(lblNomeCompleto);
		lblNomeCompleto.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		
		txtNome = new JTextField();
		txtNome.setBounds(10, 44, 238, 20);
		pnInformacoes.add(txtNome);
		txtNome.setFont(new Font("Nirmala UI", Font.PLAIN, 11));
		txtNome.setColumns(10);
		
		JLabel lblDataNascimento = new JLabel("DATA NASCIMENTO");
		lblDataNascimento.setBounds(145, 91, 119, 13);
		pnInformacoes.add(lblDataNascimento);
		lblDataNascimento.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		
		txtDataNasc = new JFormattedTextField(mascaraData);
		txtDataNasc.setBounds(145, 108, 110, 19);
		pnInformacoes.add(txtDataNasc);
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblEmail.setBounds(145, 137, 54, 13);
		pnInformacoes.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Nirmala UI", Font.PLAIN, 11));
		txtEmail.setColumns(10);
		txtEmail.setBounds(145, 153, 223, 20);
		pnInformacoes.add(txtEmail);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 74, 125, 111);
		pnInformacoes.add(panel_1);
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(
						EtchedBorder.LOWERED,
						new Color(255, 255, 255),
						new Color(160, 160, 160)), 
						"Sexo", 
						TitledBorder.LEADING,
						TitledBorder.TOP, 
						new Font("Segoe UI", Font.PLAIN, 11),
						new Color(0, 0, 0)
					)
			);
		
		rdbtnMasculino = new JRadioButton("Masculino");
		rdbtnMasculino.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		rdbtnMasculino.setBounds(6, 18, 103, 21);
		panel_1.add(rdbtnMasculino);
		
		rdbtnFeminino = new JRadioButton("Feminino");
		rdbtnFeminino.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		rdbtnFeminino.setBounds(6, 43, 103, 21);
		panel_1.add(rdbtnFeminino);
		
		rdbtnNaoInformar = new JRadioButton("Não Informar");
		rdbtnNaoInformar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		rdbtnNaoInformar.setBounds(6, 66, 103, 21);
		panel_1.add(rdbtnNaoInformar);
		
		grupoBotao = new ButtonGroup();
		grupoBotao.add(rdbtnFeminino);
		grupoBotao.add(rdbtnMasculino);
		grupoBotao.add(rdbtnNaoInformar);
		
		JLabel lblFotoPessoal = new JLabel("FOTO PESSOAL");
		lblFotoPessoal.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblFotoPessoal.setBounds(280, 28, 94, 13);
		pnInformacoes.add(lblFotoPessoal);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 192, 359, 2);
		pnInformacoes.add(separator);
		
		lbUsuario = new JLabel("USUARIO");
		lbUsuario.setBounds(10, 200, 59, 13);
		pnInformacoes.add(lbUsuario);
		lbUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(10, 216, 143, 20);
		pnInformacoes.add(txtUsuario);
		txtUsuario.setFont(new Font("Nirmala UI", Font.PLAIN, 11));
		txtUsuario.setColumns(10);
		
		JLabel LbSenha = new JLabel("SENHA");
		LbSenha.setBounds(175, 200, 59, 13);
		pnInformacoes.add(LbSenha);
		LbSenha.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(175, 216, 150, 20);
		pnInformacoes.add(txtSenha);
		
		btnExcluir = new JButton("EXCLUIR");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnExcluir.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnExcluir.setBounds(287, 307, 118, 25);
		contentPane.add(btnExcluir);
		
		btnEditar = new JButton("EDITAR");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarUsuario();
			}
		});
		btnEditar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnEditar.setBounds(10, 307, 118, 25);
		contentPane.add(btnEditar);
	}
}
