package ejercicios;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.VetoableChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import primero.HibernateUtil;
import primero.Pedidos;
import primero.PedidosId;
import primero.Tiendas;
import primero.Ventas;
import primero.VentasId;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class Ejercicio2HibernateExportarImportar extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox<Tiendas> cboxTiendas;

	private SessionFactory sesion;
	private Session session;

	private List<Ventas> ventas;
	private List<Pedidos> pedidos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio2HibernateExportarImportar frame = new Ejercicio2HibernateExportarImportar();
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
	public Ejercicio2HibernateExportarImportar() {

		/*------------------------COMPONENTES DE LA VENTANA----------------------------*/

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 365, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cboxTiendas = new JComboBox();
		cboxTiendas.setBounds(42, 28, 272, 23);
		contentPane.add(cboxTiendas);

		JRadioButton rbtnVentas = new JRadioButton("Ventas");
		buttonGroup.add(rbtnVentas);
		rbtnVentas.setBounds(109, 62, 109, 23);
		rbtnVentas.setSelected(true);
		contentPane.add(rbtnVentas);

		JRadioButton rbtnPedidos = new JRadioButton("Pedidos");
		buttonGroup.add(rbtnPedidos);
		rbtnPedidos.setBounds(109, 95, 109, 23);
		contentPane.add(rbtnPedidos);

		JButton btnExportarBinarioSecuencial = new JButton("Exportar Fichero Binario");
		btnExportarBinarioSecuencial.setBounds(133, 148, 181, 23);
		contentPane.add(btnExportarBinarioSecuencial);

		JButton btnExportarXML = new JButton("Exportar Fichero XML");
		btnExportarXML.setBounds(133, 193, 181, 23);
		contentPane.add(btnExportarXML);

		JButton btnImportarBinarioSecuencial = new JButton("Importar Fichero Binario");
		btnImportarBinarioSecuencial.setBounds(133, 240, 181, 23);
		contentPane.add(btnImportarBinarioSecuencial);

		JButton btnImportarFicheroXML = new JButton("Importar Fichero XML");
		btnImportarFicheroXML.setBounds(133, 292, 181, 23);
		contentPane.add(btnImportarFicheroXML);

		// Primero rellenamos el comboBox de Tiendas con NIF

		sesion = HibernateUtil.getSessionFactory();

		session = sesion.openSession();

		rellenaComboTiendas();

		/*---------------------------------ACCIONES DE LOS BOTONES----------------------*/

		rbtnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nif = seleccionaNif(cboxTiendas);

				ventas = dameVentas(nif);

				for (Ventas v : ventas) {
					System.out.println(v);
				}

			}
		});

		rbtnPedidos.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String nif = seleccionaNif(cboxTiendas);

				pedidos = damePedidos(nif);

				for (Pedidos p : pedidos) {
					System.out.println(p);
				}

			}
		});

		cboxTiendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rbtnVentas.isSelected() == true) {

					String nif = seleccionaNif(cboxTiendas);

					ventas = dameVentas(nif);

				} else {

					String nif = seleccionaNif(cboxTiendas);

					pedidos = damePedidos(nif);

				}

			}
		});

		btnExportarBinarioSecuencial.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (rbtnVentas.isSelected() == true) {

					try {
						File fichero = new File("FicheroDatosVentas.dat");

						FileOutputStream fileout = new FileOutputStream(fichero);

						ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

						Ventas venta1;

						for (int i = 0; i < ventas.size(); i++) {

							venta1 = (Ventas) ventas.get(i);

							venta1.getId().getNif();
							venta1.getId().getArticulo();
							venta1.getArticulos().getFabricantes().getNombre();
							venta1.getId().getPeso();
							venta1.getId().getCategoria();
							venta1.getId().getFechaVenta();
							venta1.getUnidadesVendidas();

							dataOS.writeObject(venta1);

						}
						dataOS.close(); // cerrar stream

						JOptionPane.showMessageDialog(null, "Exportado Fichero Ventas");

						System.out.println("Exportado Fichero Ventas");

					} catch (Exception ex) {
						// TODO: handle exception
					}

				} else {

					try {
						File fichero = new File("FicheroDatosPedidos.dat");

						FileOutputStream fileout = new FileOutputStream(fichero);

						ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

						Pedidos pedido1;

						for (int i = 0; i < pedidos.size(); i++) {

							pedido1 = (Pedidos) pedidos.get(i);

							pedido1.getId().getNif();
							pedido1.getId().getArticulo();
							pedido1.getArticulos().getFabricantes().getNombre();
							pedido1.getId().getPeso();
							pedido1.getId().getCategoria();
							pedido1.getId().getFechaPedido();
							pedido1.getUnidadesPedidas();

							dataOS.writeObject(pedido1);

						}
						dataOS.close(); // cerrar stream

						JOptionPane.showMessageDialog(null, "Exportado Fichero Pedidos");

						System.out.println("Exportado Fichero Ventas");

					} catch (Exception ex) {
						// TODO: handle exception
					}

				}

			}
		});

		btnImportarBinarioSecuencial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rbtnVentas.isSelected() == true) {
					try {
						File fichero = new File("FicheroDatosVentas.dat");

						FileInputStream filein = new FileInputStream(fichero);

						ObjectInputStream dataIS = new ObjectInputStream(filein);

						while (true) {

							Ventas venta1;

							venta1 = (Ventas) dataIS.readObject();

							insertaVenta(venta1.getId().getNif(), venta1.getId().getArticulo(),
									venta1.getId().getCodFabricante(), venta1.getId().getPeso(),
									venta1.getId().getCategoria(), venta1.getId().getFechaVenta(),
									venta1.getUnidadesVendidas());

							System.out.println(venta1.getId().getNif() + " " + venta1.getId().getArticulo());
						}
						// Funciona pero no me deja cerrar el DataInputStream
						// dataIS.close();

					} catch (Exception ex) {

						JOptionPane.showMessageDialog(null, "Fichero Importado con exito");
					}

				} else {

					try {
						File fichero = new File("FicheroDatosPedidos.dat");

						FileInputStream filein = new FileInputStream(fichero);

						ObjectInputStream dataIS = new ObjectInputStream(filein);

						while (true) {

							Pedidos pedido1;

							pedido1 = (Pedidos) dataIS.readObject();

							insertaPedido(pedido1.getId().getNif(), pedido1.getId().getArticulo(),
									pedido1.getId().getCodFabricante(), pedido1.getId().getPeso(),
									pedido1.getId().getCategoria(), pedido1.getId().getFechaPedido(),
									pedido1.getUnidadesPedidas());

							System.out.println(pedido1.getId().getNif() + " " + pedido1.getId().getArticulo());
						}
						// Funciona pero no me deja cerrar el DataInputStream
						// dataIS.close();

					} catch (Exception ex) {

						// JOptionPane.showMessageDialog(null, "Fichero Importado con exito");
					}

				}
			}

		});

		btnExportarXML.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (rbtnVentas.isSelected() == true) {

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

					DocumentBuilder builder;
					try {
						builder = factory.newDocumentBuilder();

						DOMImplementation implementation = builder.getDOMImplementation();
						Document document = implementation.createDocument(null, "Ventas", null);
						document.setXmlVersion("1.0");

						Ventas venta1;

						for (int i = 0; i < ventas.size(); i++) {

							Element raiz = document.createElement("venta"); // nodo
																			// venta
							document.getDocumentElement().appendChild(raiz);

							venta1 = (Ventas) ventas.get(i);

							// añadir Nif
							crearElemento("nif", venta1.getId().getNif(), raiz, document);
							// añadir nombreArticulo
							crearElemento("nombreArticulo", venta1.getId().getArticulo(), raiz, document);
							// añadir codFabricante
							crearElemento("codFabricante", Short.toString(venta1.getId().getCodFabricante()), raiz,
									document);
							// añadir peso
							crearElemento("peso", Short.toString(venta1.getId().getPeso()), raiz, document);
							// añadir categoria
							crearElemento("categoria", venta1.getId().getCategoria(), raiz, document);
							// añadir fechaVenta
							crearElemento("fechaVenta", venta1.getId().getFechaVenta().toString(), raiz, document);
							// añadir unidadesVendidas
							crearElemento("unidadesVendidas", Short.toString(venta1.getUnidadesVendidas()), raiz,
									document);
						}

						Source source = new DOMSource(document);
						Result result = new StreamResult(new java.io.File("VentasXML.xml"));
						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.transform(source, result);

						JOptionPane.showMessageDialog(null, "Exportado Fichero VentasXML.xml");

					} catch (ParserConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerFactoryConfigurationError e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

					DocumentBuilder builder;
					try {
						builder = factory.newDocumentBuilder();

						DOMImplementation implementation = builder.getDOMImplementation();
						Document document = implementation.createDocument(null, "Pedidos", null);
						document.setXmlVersion("1.0");

						Pedidos pedido1;

						for (int i = 0; i < pedidos.size(); i++) {

							Element raiz = document.createElement("pedido"); // nodo
																				// venta
							document.getDocumentElement().appendChild(raiz);

							pedido1 = (Pedidos) pedidos.get(i);

							// añadir Nif
							crearElemento("nif", pedido1.getId().getNif(), raiz, document);
							// añadir nombreArticulo
							crearElemento("nombreArticulo", pedido1.getId().getArticulo(), raiz, document);
							// añadir codFabricante
							crearElemento("codFabricante", Short.toString(pedido1.getId().getCodFabricante()), raiz,
									document);
							// añadir peso
							crearElemento("peso", Short.toString(pedido1.getId().getPeso()), raiz, document);
							// añadir categoria
							crearElemento("categoria", pedido1.getId().getCategoria(), raiz, document);
							// añadir fechaVenta
							crearElemento("fechaPedido", pedido1.getId().getFechaPedido().toString(), raiz, document);
							// añadir unidadesVendidas
							crearElemento("unidadesPedidas", Short.toString(pedido1.getUnidadesPedidas()), raiz,
									document);
						}

						Source source = new DOMSource(document);
						Result result = new StreamResult(new java.io.File("PedidosXML.xml"));
						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.transform(source, result);

						JOptionPane.showMessageDialog(null, "Exportado Fichero PedidosXML.xml");

					} catch (ParserConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerFactoryConfigurationError e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}

		});

		btnImportarFicheroXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (rbtnVentas.isSelected() == true) {

					try {

						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

						DocumentBuilder builder = factory.newDocumentBuilder();
						Document documento = builder.parse(new File("VentasXML.xml"));
						documento.getDocumentElement().normalize();

						NodeList config = documento.getElementsByTagName("venta");

						for (int i = 0; i < ventas.size(); i++) {

							Node venta = config.item(i);

							if (venta.getNodeType() == Node.ELEMENT_NODE) {
								Element elemento = (Element) venta;

								String nif = elemento.getElementsByTagName("nif").item(0).getTextContent();
								String nombreArticulo = elemento.getElementsByTagName("nombreArticulo").item(0)
										.getTextContent();
								short codFabricante = Short.parseShort(
										elemento.getElementsByTagName("codFabricante").item(0).getTextContent());
								short peso = Short
										.parseShort(elemento.getElementsByTagName("peso").item(0).getTextContent());
								String categoria = elemento.getElementsByTagName("categoria").item(0).getTextContent();

								String fechaVentaString = elemento.getElementsByTagName("fechaVenta").item(0)
										.getTextContent();
								SimpleDateFormat formatoTexto = new SimpleDateFormat("yyyy-MM-dd");
								Date fechaVenta = formatoTexto.parse(fechaVentaString);

								short unidadesVendidas = Short.parseShort(
										elemento.getElementsByTagName("unidadesVendidas").item(0).getTextContent());

								insertaVenta(nif, nombreArticulo, codFabricante, peso, categoria, fechaVenta,
										unidadesVendidas);

								/*
								 * Comprobamos si esta recogiedo los datos del XML
								 */

								System.out.println("Nif: " + nif);
								System.out.println("Nombre Articulo: " + nombreArticulo);
								System.out.println("Cod Fabricante: " + codFabricante);
								System.out.println("Peso: " + peso);
								System.out.println("Categoria: " + categoria);
								System.out.println("Fecha Venta: " + fechaVenta);
								System.out.println("Unidades Vendidas: " + unidadesVendidas);

							}
						}

					} catch (Exception e) {

					}

				}
				
				if (rbtnPedidos.isSelected() == true) {
					try {

						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

						DocumentBuilder builder = factory.newDocumentBuilder();
						Document documento = builder.parse(new File("PedidosXML.xml"));
						documento.getDocumentElement().normalize();

						NodeList config = documento.getElementsByTagName("pedido");

						for (int i = 0; i < pedidos.size(); i++) {

							Node pedido = config.item(i);

							if (pedido.getNodeType() == Node.ELEMENT_NODE) {
								Element elemento = (Element) pedido;

								String nif = elemento.getElementsByTagName("nif").item(0).getTextContent();
								String nombreArticulo = elemento.getElementsByTagName("nombreArticulo").item(0)
										.getTextContent();
								short codFabricante = Short.parseShort(
										elemento.getElementsByTagName("codFabricante").item(0).getTextContent());
								short peso = Short
										.parseShort(elemento.getElementsByTagName("peso").item(0).getTextContent());
								String categoria = elemento.getElementsByTagName("categoria").item(0).getTextContent();

								String fechaPedidoString = elemento.getElementsByTagName("fechaPedido").item(0)
										.getTextContent();
								SimpleDateFormat formatoTexto = new SimpleDateFormat("yyyy-MM-dd");
								Date fechaPedido = formatoTexto.parse(fechaPedidoString);

								short unidadesPedidas = Short.parseShort(
										elemento.getElementsByTagName("unidadesPedidas").item(0).getTextContent());

								insertaPedido(nif, nombreArticulo, codFabricante, peso, categoria, fechaPedido,
										unidadesPedidas);

								/*
								 * Comprobamos si esta recogiedo los datos del XML
								 */

								System.out.println("Nif: " + nif);
								System.out.println("Nombre Articulo: " + nombreArticulo);
								System.out.println("Cod Fabricante: " + codFabricante);
								System.out.println("Peso: " + peso);
								System.out.println("Categoria: " + categoria);
								System.out.println("Fecha Pedido: " + fechaPedido);
								System.out.println("Unidades Pedidas: " + unidadesPedidas);

							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}

		});
	}

	/*-------------------------------------METODOS-----------------------------------*/

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

	private List<Ventas> dameVentas(String nif) {

		String hql = "from Ventas where nif = :nif";

		/*
		 * String hql =
		 * "select v.id.nif, v.id.articulo, f.nombre, v.id.categoria, v.id.fechaVenta, v.unidadesVendidas, a.precioVenta  from Ventas v, Articulos a, Fabricantes f "
		 * + "where v.id.nif = :nif " + "and v.id.codFabricante = a.id.codFabricante " +
		 * "and v.id.categoria = a.id.categoria " + "and v.id.peso = a.id.peso " +
		 * "and v.id.articulo = a.id.articulo " +
		 * "and v.id.codFabricante = f.codFabricante";
		 */

		Query q = session.createQuery(hql);

		q.setParameter("nif", (String) nif);

		List<Ventas> lista = q.list();

		return lista;
	}

	private List<Pedidos> damePedidos(String nif) {

		String hql = "from Pedidos where nif = :nif";

		Query q = session.createQuery(hql);

		q.setParameter("nif", (String) nif);

		List<Pedidos> lista = q.list();

		return lista;
	}

	public void insertaVenta(String nif, String nombreArticulo, short codFabricante, short peso, String categoria,
			Date fechaVenta, short unidadesVendidas) {

		Transaction tx = session.beginTransaction();

		Ventas venta = new Ventas();
		VentasId venta1 = new VentasId();

		venta1.setNif((String) nif);
		venta1.setArticulo((String) nombreArticulo);
		venta1.setCodFabricante((short) codFabricante);
		venta1.setPeso((short) peso);
		venta1.setCategoria((String) categoria);
		venta1.setFechaVenta((Date) fechaVenta);
		venta.setUnidadesVendidas((short) unidadesVendidas);
		venta.setId(venta1);

		session.save(venta);
		tx.commit();

	}

	private void insertaPedido(String nif, String nombreArticulo, short codFabricante, short peso, String categoria,
			Date fechaPedido, Short unidadesPedidas) {

		Transaction tx = session.beginTransaction();

		Pedidos pedido = new Pedidos();
		PedidosId pedido1 = new PedidosId();

		pedido1.setNif((String) nif);
		pedido1.setArticulo((String) nombreArticulo);
		pedido1.setCodFabricante((short) codFabricante);
		pedido1.setPeso((short) peso);
		pedido1.setCategoria((String) categoria);
		pedido1.setFechaPedido((Date) fechaPedido);
		pedido.setUnidadesPedidas((short) unidadesPedidas);
		pedido.setId(pedido1);

		session.save(pedido);
		tx.commit();

	}

	public void crearElemento(String dato, String valor, Element raiz, Document document) {

		Element elem = document.createElement(dato);

		Text text = document.createTextNode(valor); // damos valor

		raiz.appendChild(elem); // pegamos el elemento hijo a la raiz

		elem.appendChild(text); // pegamos el valor
	}
}
