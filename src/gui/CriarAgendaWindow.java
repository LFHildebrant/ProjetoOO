package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import entities.Agenda;
import entities.Usuario;
import service.AgendaService;

public class CriarAgendaWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNome;
	private JTextField textDescricao;
	private AgendaService agendaService;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CriarAgendaWindow frame = new CriarAgendaWindow();
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
	public CriarAgendaWindow() {
		this.agendaService = new AgendaService();
		iniciar();
	}
	
	private void cadastrar() {
		
		try {
			Agenda agenda = new Agenda();
			
			agenda.setNome(this.textNome.getText());
			agenda.setDescricao(this.textDescricao.getText());
			agenda.setUsuario(LoginWindow.getUsuarioAtual());
			
			this.agendaService.cadastrar(agenda);
			JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!", "CONCLUÍDO!", JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		} catch (Exception e ) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar - " + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	
	private void iniciar(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 365, 284);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CRIAR AGENDA");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 10, 341, 32);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Dados", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(36, 41, 289, 159);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel labelNome = new JLabel("NOME");
		labelNome.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelNome.setBounds(23, 20, 61, 25);
		panel.add(labelNome);
		
		textNome = new JTextField();
		textNome.setBounds(23, 42, 128, 19);
		panel.add(textNome);
		textNome.setColumns(10);
		
		JLabel labelDescricao = new JLabel("DESCRIÇÃO");
		labelDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelDescricao.setBounds(23, 73, 79, 13);
		panel.add(labelDescricao);
		
		textDescricao = new JTextField();
		textDescricao.setBounds(23, 96, 249, 48);
		panel.add(textDescricao);
		textDescricao.setColumns(10);
		
		JButton btnCadastrar = new JButton("CRIAR AGENDA");
		btnCadastrar.setBounds(111, 210, 137, 27);
		contentPane.add(btnCadastrar);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrar();
			}
		});
		btnCadastrar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
	}
}
