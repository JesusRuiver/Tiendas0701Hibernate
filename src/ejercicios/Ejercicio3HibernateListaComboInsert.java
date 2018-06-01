package ejercicios;

import java.awt.EventQueue;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;

import primero.Articulos;
import primero.Fabricantes;
import primero.HibernateUtil;
import primero.Tiendas;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class Ejercicio3HibernateListaComboInsert extends JFrame {

	private JPanel contentPane;
	private JTextField txtFecha;
	private JTextField txtCategoria;

	private JList<Tiendas> listTiendas = new JList<Tiendas>();
	private JComboBox <Object> cboxArticulos = new JComboBox<Object>();
	
	private JSpinner spinPeso = new JSpinner();
	private JSpinner spinUnidades = new JSpinner();

	private final ButtonGroup buttonGroup = new ButtonGroup();

	private SessionFactory sesion;
	private Session session;

	private String nifTienda = null;
	private String nombreArticulo;
	private int codFabricante;
	private int peso;
	private int unidades;
	private String fecha;
	private String categoria;

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

		//cboxArticulos = new JComboBox();

		cboxArticulos.setBounds(379, 34, 267, 20);
		contentPane.add(cboxArticulos);

		spinPeso = new JSpinner();
		spinPeso.setBounds(539, 91, 107, 20);
		contentPane.add(spinPeso);

		spinUnidades = new JSpinner();
		spinUnidades.setBounds(539, 153, 107, 20);
		contentPane.add(spinUnidades);

		txtFecha = new JTextField();
		txtFecha.setBounds(539, 223, 107, 20);
		contentPane.add(txtFecha);
		txtFecha.setColumns(10);

		txtCategoria = new JTextField();
		txtCategoria.setColumns(10);
		txtCategoria.setBounds(539, 273, 107, 20);
		contentPane.add(txtCategoria);

		JLabel lbPeso = new JLabel("Peso");
		lbPeso.setBounds(389, 94, 46, 14);
		contentPane.add(lbPeso);

		JLabel lbunidades = new JLabel("Unidades");
		lbunidades.setBounds(389, 156, 64, 14);
		contentPane.add(lbunidades);

		JLabel lbFecha = new JLabel("Fecha");
		lbFecha.setBounds(389, 226, 46, 14);
		contentPane.add(lbFecha);

		JLabel lbCategoria = new JLabel("Categoria");
		lbCategoria.setBounds(389, 276, 64, 14);
		contentPane.add(lbCategoria);

		JButton btnInsertarArticulo = new JButton("Insertar");
		btnInsertarArticulo.setBounds(557, 327, 89, 23);
		contentPane.add(btnInsertarArticulo);

		JRadioButton rbtnVentas = new JRadioButton("Ventas");
		buttonGroup.add(rbtnVentas);
		rbtnVentas.setSelected(true);
		rbtnVentas.setBounds(389, 64, 109, 23);
		contentPane.add(rbtnVentas);

		JRadioButton rbtnPedidos = new JRadioButton("Pedidos");
		buttonGroup.add(rbtnPedidos);
		rbtnPedidos.setBounds(500, 64, 109, 23);
		contentPane.add(rbtnPedidos);

		// Iniciamos sesión con HibernateUtil

		sesion = HibernateUtil.getSessionFactory();

		session = sesion.openSession();

		rellenaListaTiendas(listTiendas);

		rellenaComboBoxArticulos(cboxArticulos);

		/*
		 * listTiendas.addListSelectionListener(new ListSelectionListener() {
		 * 
		 * public void valueChanged(ListSelectionEvent e) {
		 * 
		 * Tienda tienda1 = new Tienda();
		 * 
		 * if (e.getValueIsAdjusting()) {
		 * 
		 * tienda1 = listTiendas.getSelectedValue();
		 * 
		 * nifTienda = tienda1.getNif();
		 * 
		 * System.out.println(nifTienda);
		 * 
		 * }
		 * 
		 * }
		 * 
		 * });
		 * 
		 * cboxArticulos.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent arg0) {
		 * 
		 * Articulo articulo1 = new Articulo();
		 * 
		 * articulo1 = (Articulo) cboxArticulos.getSelectedItem();
		 * 
		 * nombreArticulo = articulo1.getNombreArticulo();
		 * 
		 * codFabricante = articulo1.getCodFabricante();
		 * 
		 * } });
		 * 
		 * btnInsertarArticulo.addActionListener(new ActionListener() { public
		 * void actionPerformed(ActionEvent arg0) {
		 * 
		 * peso = (int) spinPeso.getValue();
		 * 
		 * unidades = (int) spinUnidades.getValue();
		 * 
		 * fecha = txtFecha.getText();
		 * 
		 * categoria = txtCategoria.getText();
		 * 
		 * if (rbtnVentas.isSelected() == true) {
		 * 
		 * miConexion.insertaVenta(nifTienda, nombreArticulo, codFabricante,
		 * peso, categoria, fecha, unidades);
		 * 
		 * } else {
		 * 
		 * miConexion.insertaPedido(nifTienda, nombreArticulo, codFabricante,
		 * peso, categoria, fecha, unidades); }
		 * 
		 * System.out.println(nifTienda + nombreArticulo + codFabricante + peso
		 * + unidades + fecha + categoria);
		 * 
		 * }
		 * 
		 * });
		 */

	}

	// ------------------------------------------METODOS-----------------------------------------//

	private void rellenaComboBoxArticulos(JComboBox cboxArticulos) {
		
		//String hql = "select f.nombre, a.id.articulo from Articulos a, Fabricantes f where f.codFabricante = a.id.codFabricante group by a.id.articulo";
		
		String hql = "from Articulos";

		Query q = session.createQuery(hql);
		
		List<Articulos> listaArticulos = q.list();

		Iterator <Articulos> it = listaArticulos.iterator();

		while (it.hasNext()) {
			
			Articulos articulo1 = (Articulos) it.next();

			cboxArticulos.addItem("ARTICULO: " + articulo1.getId().getArticulo() + " " + "FABRICANTE: " + articulo1.getFabricantes().getNombre());

		}

	}

	private void rellenaListaTiendas(JList listTiendas) {

		DefaultListModel<Tiendas> modeloLista = new DefaultListModel<Tiendas>();

		String hql = "from Tiendas";

		Query q = session.createQuery(hql);

		List<Tiendas> listaTiendas = q.list();

		Iterator<Tiendas> it = listaTiendas.iterator();

		while (it.hasNext()) {

			Tiendas t1 = (Tiendas) it.next();

			modeloLista.addElement(t1);
		}

		listTiendas.setModel(modeloLista);
	}
}
