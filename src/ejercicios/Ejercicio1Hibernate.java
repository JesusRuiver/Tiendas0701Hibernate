package ejercicios;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import bbdd.ServicioTiendas;
import primero.HibernateUtil;
import primero.Tiendas;

import javax.swing.JComboBox;

public class Ejercicio1Hibernate extends JFrame {

	private JPanel contentPane;

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

		SessionFactory sesion = HibernateUtil.getSessionFactory();

		Session session = sesion.openSession();

		Transaction tx = session.beginTransaction();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 704, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox cBoxTiendas = new JComboBox();
		cBoxTiendas.setBounds(27, 30, 217, 20);
		contentPane.add(cBoxTiendas);
		
		

		ServicioTiendas servicioTiendas = new ServicioTiendas();
		
		List<Tiendas> tiendas = servicioTiendas.dameTiendas();
		
		Tiendas t1 = new Tiendas();
		
		for (int i = 0; i < tiendas.size();i++){
			
			cBoxTiendas.addItem(tiendas.get(i));
		}

	}

}
