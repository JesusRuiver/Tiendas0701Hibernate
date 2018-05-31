package ejercicios;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bbdd.Conexion;
import clases.Articulo;
import clases.Tienda;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ejercicio3HibernateListaComboInsert extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio3HibernateListaComboInsert frame = new Ejercicio3HibernateListaComboInsert();
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
	public Ejercicio3HibernateListaComboInsert() {


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 34, 294, 308);
		contentPane.add(scrollPane);

		JList<String> listTiendas = new JList();

		scrollPane.setViewportView(listTiendas);

		JComboBox cboxArticulos = new JComboBox();

		cboxArticulos.setBounds(379, 34, 267, 20);
		contentPane.add(cboxArticulos);

		JSpinner spinner = new JSpinner();
		spinner.setBounds(539, 91, 107, 20);
		contentPane.add(spinner);

		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(539, 153, 107, 20);
		contentPane.add(spinner_1);

		textField = new JTextField();
		textField.setBounds(539, 223, 107, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(539, 273, 107, 20);
		contentPane.add(textField_1);

		JLabel lbPeso = new JLabel("Peso");
		lbPeso.setBounds(410, 94, 46, 14);
		contentPane.add(lbPeso);

		JLabel lbunidades = new JLabel("Unidades");
		lbunidades.setBounds(410, 156, 46, 14);
		contentPane.add(lbunidades);

		JLabel lbFecha = new JLabel("Fecha");
		lbFecha.setBounds(410, 226, 46, 14);
		contentPane.add(lbFecha);

		JLabel lbCategoria = new JLabel("Categoria");
		lbCategoria.setBounds(410, 276, 64, 14);
		contentPane.add(lbCategoria);

		JButton btnInsertarArticulo = new JButton("Insertar");
		btnInsertarArticulo.setBounds(557, 327, 89, 23);
		contentPane.add(btnInsertarArticulo);

		rellenaListaTiendas(listTiendas);

		rellenaComboBoxArticulos(cboxArticulos);

		listTiendas.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

			

			}
		});



		cboxArticulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				

			}
		});

	}

	// ------------------------------------------METODOS-----------------------------------------//

	private void rellenaComboBoxArticulos(JComboBox cboxArticulos) {
		ArrayList<Articulo> articulos = new ArrayList<Articulo>();
		
		articulos = miConexion.dameArticulos();
		
		for (int i = 0; i< articulos.size();i++){
			cboxArticulos.addItem(articulos.get(i));
		}
		
	}

	private void rellenaListaTiendas(JList listTiendas) {

		DefaultListModel<Tienda> modeloLista = new DefaultListModel<Tienda>();

		ArrayList<Tienda> datos = new ArrayList<Tienda>();

		datos = miConexion.dameTiendas();

		for (int i = 0; i < datos.size(); i++) {
			modeloLista.addElement(datos.get(i));
		}

		listTiendas.setModel(modeloLista);
	}
}
