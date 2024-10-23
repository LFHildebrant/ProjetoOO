package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JDateChooser;

public class AgendaWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgendaWindow frame = new AgendaWindow();
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
	public AgendaWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 808, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDataInicial = new JLabel("Data Inicial");
		lblDataInicial.setForeground(new Color(54, 54, 54));
		lblDataInicial.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblDataInicial.setBounds(10, 11, 60, 28);
		contentPane.add(lblDataInicial);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setBackground(new Color(255, 255, 255));
		dateChooser.setBounds(70, 17, 152, 19);
		contentPane.add(dateChooser);
		
		JLabel lblDataFinal = new JLabel("Data Final");
		lblDataFinal.setForeground(new Color(54, 54, 54));
		lblDataFinal.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblDataFinal.setBounds(232, 11, 55, 28);
		contentPane.add(lblDataFinal);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.getCalendarButton().setBackground(new Color(255, 255, 255));
		dateChooser_1.setBounds(287, 17, 152, 19);
		contentPane.add(dateChooser_1);
		
		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnFiltrar.setBackground(new Color(255, 255, 255));
		btnFiltrar.setBounds(449, 10, 68, 30);
		contentPane.add(btnFiltrar);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(new Color(126, 126, 126));
		separator_1.setBounds(10, 49, 592, 1);
		contentPane.add(separator_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 60, 774, 320);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 754, 300);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Data", "T\u00EDtulo", "Desc", "Hora Inicial", "Hora Final", "Local", "Convidados", "Hora Notifica\u00E7\u00E3o"
			}
		));
		

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumn column = null;
        int[] columnWidths = {80, 250, 100, 100, 100, 100, 350, 130};
        for (int i = 0; i < columnWidths.length; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
		
		scrollPane.setViewportView(table);
	}
}
