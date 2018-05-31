package ejercicios;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import primero.HibernateUtil;
import primero.Tiendas;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;

import org.hibernate.Query;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ejercicio3HibernateListaComboInsert extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	private JList<Tiendas> listTiendas = new JList<Tiendas>();

	private SessionFactory sesion;
	private Session session;

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

		listTiendas = new JList();

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

		// Iniciamos sesión con HibernateUtil

		sesion = HibernateUtil.getSessionFactory();

		session = sesion.openSession();

		rellenaListaTiendas(listTiendas);



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

	

	private void rellenaListaTiendas(JList listTiendas) {

		DefaultListModel <Tiendas> modeloLista = new DefaultListModel<Tiendas>();

		String hql = "from Tiendas";

		Query q = session.createQuery(hql);

		List <Tiendas> listaTiendas = q.list();

		Iterator<Tiendas> it = listaTiendas.iterator();

		while (it.hasNext()) {

			Tiendas t1 = (Tiendas) it.next();

			modeloLista.addElement(t1);
		}

		listTiendas.setModel(modeloLista);
	}
}
