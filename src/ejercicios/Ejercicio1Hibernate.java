package ejercicios;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import primero.HibernateUtil;
import primero.Tiendas;
import primero.Ventas;
import primero.VentasId;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class Ejercicio1Hibernate extends JFrame {

	private JPanel contentPane;
	private SessionFactory sesion;
	private Session session;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable tablaVentas;
	private JScrollPane scrollPaneTabla;
	private JComboBox <Tiendas> cBoxTiendas = new JComboBox();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio1Hibernate frame = new Ejercicio1Hibernate();
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
	public Ejercicio1Hibernate() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 704, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cBoxTiendas = new JComboBox();
		
		cBoxTiendas.setBounds(27, 30, 343, 20);
		contentPane.add(cBoxTiendas);
		
		scrollPaneTabla = new JScrollPane();
		scrollPaneTabla.setBounds(10, 86, 668, 303);
		contentPane.add(scrollPaneTabla);
		
		JRadioButton rbtnVentas = new JRadioButton("Ventas");
		buttonGroup.add(rbtnVentas);
		rbtnVentas.setSelected(true);
		rbtnVentas.setBounds(37, 57, 109, 23);
		contentPane.add(rbtnVentas);
		
		JRadioButton rbtnPedidos = new JRadioButton("Pedidos");
		buttonGroup.add(rbtnPedidos);
		rbtnPedidos.setBounds(148, 56, 109, 23);
		contentPane.add(rbtnPedidos);
		
		sesion = HibernateUtil.getSessionFactory();
		
		session = sesion.openSession();

		Query q = session.createQuery("from Tiendas");

		List<Tiendas> lista = q.list();
		
		Iterator<Tiendas> it = lista.iterator();

		while (it.hasNext()) {

			Tiendas t1 = (Tiendas) it.next();

			cBoxTiendas.addItem(t1);
		}
		
		rbtnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String nif = seleccionaNif(cBoxTiendas);
				
				construirTablaVentas(nif);
				
				
			}
		});
		
		cBoxTiendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				
			}

			
		});
		
		

		

		
	}
	
	private String seleccionaNif(JComboBox cBoxTiendas) {
		
		Tiendas t1 = new Tiendas();
		
		t1 = (Tiendas) cBoxTiendas.getSelectedItem();
		
		return t1.getNif();
	}
	
	private void construirTablaVentas(String nif) {

		String titulosColumnas[] = { "NIF", "ARTICULO", "FABRICANTE", "PESO", "CATEGORIA", "FECHA VENTA",
				"UNIDADES VENDIDAS"};
		String informacionTablaVentas[][] = obtenerDatosVentas(nif);

		tablaVentas = new JTable(informacionTablaVentas, titulosColumnas);
		scrollPaneTabla.setViewportView(tablaVentas);

	}

	private String[][] obtenerDatosVentas(String nif) {

		List<Ventas> ventas;

		ventas = dameVentasParaTabla(nif);

		String matrizInfo[][] = new String[ventas.size()][7];

		Ventas vent;
		for (int i = 0; i < ventas.size(); i++) {
			vent= (Ventas) ventas.get(i);
			

			matrizInfo[i][0] = vent.getId().getNif();
			matrizInfo[i][1] = vent.getId().getArticulo();
			matrizInfo[i][2] = Integer.toString(vent.getId().getCodFabricante());
			matrizInfo[i][3] = vent.getId().getCategoria();
			matrizInfo[i][4] = Short.toString(vent.getId().getPeso());
			matrizInfo[i][5] = vent.getId().getFechaVenta().toString();
			matrizInfo[i][6] = Short.toString(vent.getUnidadesVendidas());

		}

		return matrizInfo;
	}

	private List<Ventas> dameVentasParaTabla(String nif) {
		
		String hql = "from Ventas where nif = :nif";
		
		Query q = session.createQuery(hql);

		q.setParameter("nif", (String)nif);
		
		List<Ventas> lista = q.list();
		
		
		return lista;
	}
}
