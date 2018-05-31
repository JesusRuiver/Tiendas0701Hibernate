package ejercicios;

import java.awt.EventQueue;
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
import primero.Pedidos;
import primero.Tiendas;
import primero.Ventas;


import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;

public class Ejercicio1Hibernate extends JFrame {

	private JPanel contentPane;
	
	private SessionFactory sesion;
	private Session session;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable tablaVentas;
	private JTable tablaPedidos;
	private JScrollPane scrollPaneTabla;
	private JComboBox<Tiendas> cboxTiendas = new JComboBox();

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
		setBounds(100, 100, 750, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cboxTiendas = new JComboBox();

		cboxTiendas.setBounds(27, 30, 343, 20);
		contentPane.add(cboxTiendas);

		scrollPaneTabla = new JScrollPane();
		scrollPaneTabla.setBounds(10, 86, 714, 334);
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
		
		JLabel lblTotalTitulo = new JLabel("Total:");
		lblTotalTitulo.setBounds(348, 442, 46, 14);
		contentPane.add(lblTotalTitulo);
		
		JLabel lbresultado = new JLabel("New label");
		lbresultado.setBounds(413, 442, 311, 14);
		contentPane.add(lbresultado);
		
		//Iniciamos sesión con HibernateUtil

		sesion = HibernateUtil.getSessionFactory();

		session = sesion.openSession();

		rellenaComboTiendas();

		rbtnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try{
					
				
				String nif = seleccionaNif(cboxTiendas);

				construirTablaVentas(nif);
				
				double resultado = sumaPrecioVenta(nif);
				
				lbresultado.setText(String.valueOf(resultado) + " € Ingresos Ventas");
				
				} catch (Exception e) {
					
					lbresultado.setText(" ");
					
				}

			}
		});

		rbtnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try{
				
				String nif = seleccionaNif(cboxTiendas);
				
				
				double resultado = sumaPrecioPedido(nif);
				
				construirTablaPedidos(nif);
				
				lbresultado.setText(String.valueOf(resultado) + "€ Coste Pedidos");
				
				} catch (Exception e) {
					
					lbresultado.setText(" ");
				}
			}
		});

		cboxTiendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (rbtnVentas.isSelected() == true) {

					try{
						
						
						String nif = seleccionaNif(cboxTiendas);

						construirTablaVentas(nif);
						
						double resultado = sumaPrecioVenta(nif);
						
						lbresultado.setText(String.valueOf(resultado) + " € Ingresos Ventas");
						
						} catch (Exception e) {
							
							lbresultado.setText(" ");
							
						}

				} else {

					try{
						
						String nif = seleccionaNif(cboxTiendas);
						
						
						double resultado = sumaPrecioPedido(nif);
						
						construirTablaPedidos(nif);
						
						lbresultado.setText(String.valueOf(resultado) + "€ Coste Pedidos");
						
						} catch (Exception e) {
							
							lbresultado.setText(" ");
						}
				}

			}

		});

	}

	public void rellenaComboTiendas() {
		
		Query q = session.createQuery("from Tiendas");

		List<Tiendas> lista = q.list();

		Iterator<Tiendas> it = lista.iterator();

		while (it.hasNext()) {

			Tiendas t1 = (Tiendas) it.next();

			cboxTiendas.addItem(t1);
		}
	}

	private String seleccionaNif(JComboBox cBoxTiendas) {

		Tiendas t1 = new Tiendas();

		t1 = (Tiendas) cBoxTiendas.getSelectedItem();

		return t1.getNif();
	}

	private void construirTablaVentas(String nif) {

		String titulosColumnas[] = { "NIF", "ARTICULO", "FABRICANTE", "PESO", "CATEGORIA", "FECHA VENTA",
				"UNIDADES VENDIDAS", "PRECIO VENTA" };
		String informacionTablaVentas[][] = obtenerDatosVentasSinNombreFabricante(nif);

		tablaVentas = new JTable(informacionTablaVentas, titulosColumnas);
		scrollPaneTabla.setViewportView(tablaVentas);

	}

	private String[][] obtenerDatosVentasSinNombreFabricante(String nif) {

		List<Ventas> ventas;

		ventas = dameVentasParaTabla(nif);

		String matrizInfo[][] = new String[ventas.size()][8];

		Ventas vent;

		for (int i = 0; i < ventas.size(); i++) {

			vent = (Ventas) ventas.get(i);

			matrizInfo[i][0] = vent.getId().getNif();
			matrizInfo[i][1] = vent.getId().getArticulo();
			matrizInfo[i][2] = vent.getArticulos().getFabricantes().getNombre();
			//matrizInfo[i][2] = Integer.toString(vent.getId().getCodFabricante());
			matrizInfo[i][3] = Short.toString(vent.getId().getPeso());
			matrizInfo[i][4] = vent.getId().getCategoria();
			matrizInfo[i][5] = vent.getId().getFechaVenta().toString();
			matrizInfo[i][6] = Short.toString(vent.getUnidadesVendidas());
			matrizInfo[i][7] = Float.toString(vent.getArticulos().getPrecioVenta());

		}

		return matrizInfo;
	}
	


	private void construirTablaPedidos(String nif) {
		String titulosColumnas[] = { "NIF", "ARTICULO", "FABRICANTE", "PESO", "CATEGORIA", "FECHA VENTA",
				"UNIDADES PEDIDAS", "PRECIO COSTO" };
		String informacionTablaPedidos[][] = obtenerDatosPedidos(nif);

		tablaPedidos = new JTable(informacionTablaPedidos, titulosColumnas);
		scrollPaneTabla.setViewportView(tablaPedidos);

	}

	private String[][] obtenerDatosPedidos(String nif) {

		List<Pedidos> pedidos;

		pedidos = damePedidosParaTabla(nif);

		String matrizInfo[][] = new String[pedidos.size()][8];

		Pedidos pedido;

		for (int i = 0; i < pedidos.size(); i++) {

			pedido = (Pedidos) pedidos.get(i);

			matrizInfo[i][0] = pedido.getId().getNif();
			matrizInfo[i][1] = pedido.getId().getArticulo();
			matrizInfo[i][2] = pedido.getArticulos().getFabricantes().getNombre();
			matrizInfo[i][3] = Short.toString(pedido.getId().getPeso());
			matrizInfo[i][4] = pedido.getId().getCategoria();
			matrizInfo[i][5] = pedido.getId().getFechaPedido().toString();
			matrizInfo[i][6] = Short.toString(pedido.getUnidadesPedidas());
			matrizInfo[i][7] = Float.toString(pedido.getArticulos().getPrecioCosto());

		}

		return matrizInfo;

	}

	private List<Ventas> dameVentasParaTabla(String nif) {

		String hql = "from Ventas where nif = :nif";
		
		/*String hql =
				 "select v.id.nif, v.id.articulo, f.nombre, v.id.categoria, v.id.fechaVenta, v.unidadesVendidas, a.precioVenta  from Ventas v, Articulos a, Fabricantes f "
				 + "where v.id.nif = :nif " +
				 "and v.id.codFabricante = a.id.codFabricante " +
				 "and v.id.categoria = a.id.categoria " + "and v.id.peso = a.id.peso "
				 + "and v.id.articulo = a.id.articulo " +
				 "and v.id.codFabricante = f.codFabricante";*/

		Query q = session.createQuery(hql);

		q.setParameter("nif", (String) nif);

		List<Ventas> lista = q.list();

		return lista;
	}

	private List<Pedidos> damePedidosParaTabla(String nif) {

		String hql = "from Pedidos where nif = :nif";

		Query q = session.createQuery(hql);

		q.setParameter("nif", (String) nif);

		List<Pedidos> lista = q.list();

		return lista;
	}
	
	public double sumaPrecioVenta(String nif) {

		String hql = "select sum(v.unidadesVendidas * a.precioVenta) "
				+ "from Ventas v, Articulos a "
				+ "where v.id.nif= :nif "
				+ "and v.id.articulo = a.id.articulo "
				+ "and v.id.categoria= a.id.categoria";

		Query q = session.createQuery(hql);
		
		q.setParameter("nif", (String) nif);
		
		Iterator it = q.iterate();
		
		double resultado = 0;
		
		while (it.hasNext()) {
			
			resultado = (double) it.next();

		}
		System.out.println(resultado);
		return resultado;
	}
	
	public double sumaPrecioPedido(String nif) {

		String hql = "select sum(p.unidadesPedidas * a.precioCosto) "
				+ "from Pedidos p, Articulos a "
				+ "where p.id.nif= :nif "
				+ "and p.id.articulo = a.id.articulo "
				+ "and p.id.categoria= a.id.categoria";

		Query q = session.createQuery(hql);
		
		q.setParameter("nif", (String) nif);
		
		Iterator it = q.iterate();
		
		double resultado = 0;
		
		while (it.hasNext()) {
			
			resultado = (double) it.next();

		}
		System.out.println(resultado);
		return resultado;
	}
}
