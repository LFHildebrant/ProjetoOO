package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import entities.Agenda;
import entities.Compromisso;
import service.AgendaService;
import service.CompromissoService;

public class CriarCompromissoWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textTitulo;
	private JTextField textoLocal;
	private JTextField textDescricao;
	private MaskFormatter mascaraHora;
	private MaskFormatter mascaraNotificacao;
	private AgendaService agendaService;
	private CompromissoService compromissoService;
	private JComboBox comboBoxAgendas;
	private JFormattedTextField textHoraInicio;
	private JFormattedTextField textHoraTermino;
	private JDateChooser dateChooserInicio;
	private JDateChooser dateChooserTermino;
	private JTextField textNotificacao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CriarCompromissoWindow frame = new CriarCompromissoWindow();
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
	public CriarCompromissoWindow() {
		
		criarMascara();
		this.agendaService = new AgendaService();
		this.compromissoService = new CompromissoService();
		iniciarComponentes();
		buscarAgendas();
	}
	
	private void buscarAgendas() {
        try {
            List<Agenda> agendas = this.agendaService.listarTodos(LoginWindow.getUsuarioAtual().getLogin());
            for (Agenda agenda : agendas){
                this.comboBoxAgendas.addItem(agenda);
            }
        }catch(SQLException | IOException e) {
            System.out.println("Erro ao buscar agendas.");
        }
    }
	
	
	
	private void criarMascara() {
		try {
			this.mascaraHora = new MaskFormatter("##:##");
			//this.mascaraNotificacao = new MaskFormatter(""
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void criarCompromisso() {
		
		try {
			Compromisso compromisso = new Compromisso();
			compromisso.setTitulo(this.textTitulo.getText());
			compromisso.setLocal(this.textoLocal.getText());
			compromisso.setDescricao(this.textDescricao.getText());
			
			java.util.Date dataInicio = this.dateChooserInicio.getDate();
			java.util.Date dataTermino = this.dateChooserTermino.getDate();
			java.sql.Date inicioSql = new java.sql.Date(dataInicio.getTime());
			java.sql.Date terminoSql = new java.sql.Date(dataTermino.getTime());
			compromisso.setDataInicio(inicioSql);
			compromisso.setDataTermino(terminoSql);
			
			SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
			java.util.Date d = formatoHora.parse(this.textHoraInicio.getText());
			Time horaSql = new Time(d.getTime());
			java.util.Date d1 = formatoHora.parse(this.textHoraTermino.getText());
			Time horaSql1 = new Time(d1.getTime());
			compromisso.setHoraInicio(horaSql);
			compromisso.setHoraTermino(horaSql1);
			
			compromisso.setAgenda((Agenda) this.comboBoxAgendas.getSelectedItem());
			
			int minutos = Integer.parseInt(this.textNotificacao.getText());
			Timestamp timeStamp = new Timestamp(inicioSql.getTime());
			timeStamp.setTime(horaSql.getTime());
			long milissegundosParaSubtrair = timeStamp.getTime() - ( minutos * 60 * 1000 );
			Timestamp horaNotificacao = new Timestamp(milissegundosParaSubtrair);
			compromisso.setMomentoNotificacao(horaNotificacao);
			
			this.compromissoService.cadastrarCompromisso(compromisso);
			
			JOptionPane.showMessageDialog(null, "Compromisso criado com sucesso!", "CONCLUÍDO!", JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		} catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar compromisso: " + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void iniciarComponentes() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Dados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 33, 416, 230);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel labelTitulo = new JLabel("TITULO");
		labelTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelTitulo.setBounds(10, 24, 45, 13);
		panel.add(labelTitulo);
		
		textTitulo = new JTextField();
		textTitulo.setBounds(54, 22, 88, 19);
		panel.add(textTitulo);
		textTitulo.setColumns(10);
		
		JLabel labelLocal = new JLabel("LOCAL");
		labelLocal.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelLocal.setBounds(152, 24, 45, 13);
		panel.add(labelLocal);
		
		textoLocal = new JTextField();
		textoLocal.setBounds(195, 22, 88, 19);
		panel.add(textoLocal);
		textoLocal.setColumns(10);
		
		JLabel labelDescricao = new JLabel("DESCRIÇÃO");
		labelDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelDescricao.setBounds(175, 47, 82, 13);
		panel.add(labelDescricao);
		
		textDescricao = new JTextField();
		textDescricao.setBounds(20, 63, 379, 28);
		panel.add(textDescricao);
		textDescricao.setColumns(10);
		
		JLabel labelDataInicio = new JLabel("DATA DE INICIO");
		labelDataInicio.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelDataInicio.setBounds(10, 125, 82, 13);
		panel.add(labelDataInicio);
		
		JLabel labelHoraInicio = new JLabel("HORA DE INICIO");
		labelHoraInicio.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelHoraInicio.setBounds(10, 160, 96, 13);
		panel.add(labelHoraInicio);
		
		JLabel labelDataTermino = new JLabel("DATA TÉRMINO");
		labelDataTermino.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelDataTermino.setBounds(208, 125, 88, 13);
		panel.add(labelDataTermino);
		
		JLabel labelHoraTermino = new JLabel("HORA TÉRMINO");
		labelHoraTermino.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelHoraTermino.setBounds(208, 160, 88, 13);
		panel.add(labelHoraTermino);
		
		JButton btnCriar = new JButton("CRIAR");
		btnCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				criarCompromisso();
			}
		});
		btnCriar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnCriar.setBounds(158, 187, 99, 33);
		panel.add(btnCriar);
		
		this.dateChooserInicio = new JDateChooser();
		dateChooserInicio.setBounds(103, 125, 96, 19);
		panel.add(dateChooserInicio);
		
		this.dateChooserTermino = new JDateChooser();
		dateChooserTermino.setBounds(303, 125, 96, 19);
		panel.add(dateChooserTermino);
		
		this.textHoraInicio = new JFormattedTextField(this.mascaraHora);
		textHoraInicio.setBounds(103, 158, 95, 19);
		panel.add(textHoraInicio);
		
		this.textHoraTermino = new JFormattedTextField(this.mascaraHora);
		textHoraTermino.setBounds(302, 158, 97, 19);
		panel.add(textHoraTermino);
		
		JLabel labelAgenda = new JLabel("SELECIONE A AGENDA");
		labelAgenda.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelAgenda.setBounds(20, 102, 127, 13);
		panel.add(labelAgenda);
		
		this.comboBoxAgendas = new JComboBox();
		comboBoxAgendas.setBounds(158, 101, 241, 21);
		panel.add(comboBoxAgendas);
		
		JLabel labelNotificacao = new JLabel("AVISAR ");
		labelNotificacao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		labelNotificacao.setBounds(293, 25, 45, 13);
		panel.add(labelNotificacao);
		
		textNotificacao = new JTextField();
		textNotificacao.setText("min");
		textNotificacao.setBounds(343, 22, 56, 19);
		panel.add(textNotificacao);
		textNotificacao.setColumns(10);
		
		JLabel labelTituloPrincipal = new JLabel("CRIAR COMPROMISSO");
		labelTituloPrincipal.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelTituloPrincipal.setBounds(123, 10, 167, 13);
		contentPane.add(labelTituloPrincipal);
	}
}
