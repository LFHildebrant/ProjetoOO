package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import entities.Usuario;
import service.UsuarioService;

public class LoginWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JLabel LbSenha;
	private JPasswordField txtSenha;
	private JLabel lbConta;
	private JLabel lbCadastrar;
	private JButton btnNewButton;
	private UsuarioWindow cadastro;
	private final Color vermelho;
	private final Color padrao;
	private UsuarioService usuarios;
	private MenuWindow menuWindow;
	private static Usuario usuarioAtual;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
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
	public LoginWindow() {
		iniciarComponentes();
		vermelho = new Color(224, 144, 129);
		padrao = new Color(255, 255, 255);
		this.usuarios = new UsuarioService();
		
	}
	
	private void entrar() {
		try {
			if(!verificarCamposVazio()) {
				Usuario usuario = this.usuarios.buscarPorLogin(this.txtUsuario.getText());
				if(usuario != null) {
					if(usuario.getLogin().equals(this.txtUsuario.getText()) && usuario.getSenha().equals(this.txtSenha.getText())) {
						usuarioAtual = usuario;
						JOptionPane.showMessageDialog(null, "Logado com sucesso!", "CONCLUÍDO!", JOptionPane.INFORMATION_MESSAGE);
						abrirMenu();
					} else {
						JOptionPane.showMessageDialog(null, "Senha incorreta!", "ATENÇÃO!", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Usuario não encontrado!", "ATENÇÃO!", JOptionPane.WARNING_MESSAGE);										
				}
			} else {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "ATENÇÃO!", JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao fazer o login - " + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean verificarCamposVazio() {
		boolean vazio = false;
		if(txtUsuario.getText().isEmpty()) {
			vazio = true;
			txtUsuario.setBackground(vermelho);
		} else {
			txtUsuario.setBackground(padrao);
		}
		if(new String(txtSenha.getPassword()).isEmpty()) {
			vazio = true;
			txtSenha.setBackground(vermelho);
		} else {
			txtSenha.setBackground(padrao);
		}
		
		return vazio;
	}
	
	private void abrirCadastro() {
		cadastro = new UsuarioWindow();
		cadastro.setVisible(true);
		this.dispose();
	}
	
	private void abrirMenu() {
		menuWindow = new MenuWindow();
		menuWindow.setVisible(true);
		this.dispose();
	}
	
	
	public static Usuario getUsuarioAtual() {
		return usuarioAtual;
	}
	

	public void iniciarComponentes() {
		setTitle("Agenda");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 258, 262);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel LbTitulo = new JLabel("LOGIN");
		LbTitulo.setBounds(81, 10, 82, 34);
		LbTitulo.setFont(new Font("Nirmala UI", Font.PLAIN, 20));
		contentPane.add(LbTitulo);
		
		JLabel lblNewLabel = new JLabel("USUARIO");
		lblNewLabel.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		lblNewLabel.setBounds(51, 54, 59, 13);
		contentPane.add(lblNewLabel);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Nirmala UI", Font.PLAIN, 11));
		txtUsuario.setBounds(51, 70, 143, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		LbSenha = new JLabel("SENHA");
		LbSenha.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		LbSenha.setBounds(51, 100, 59, 13);
		contentPane.add(LbSenha);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(51, 116, 143, 20);
		contentPane.add(txtSenha);
		
		btnNewButton = new JButton("ENTRAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entrar();
			}
		});
		btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnNewButton.setBounds(81, 182, 85, 21);
		contentPane.add(btnNewButton);
		
		lbConta = new JLabel("Não possui uma conta?");
		lbConta.setFont(new Font("Nirmala UI", Font.PLAIN, 9));
		lbConta.setBounds(51, 142, 96, 13);
		contentPane.add(lbConta);
		
		lbCadastrar = new JLabel("Cadastrar");
		lbCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				abrirCadastro();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lbCadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		lbCadastrar.setForeground(new Color(0, 128, 255));
		lbCadastrar.setFont(new Font("Nirmala UI", Font.PLAIN, 9));
		lbCadastrar.setBounds(149, 142, 45, 13);
		contentPane.add(lbCadastrar);
	}
}
