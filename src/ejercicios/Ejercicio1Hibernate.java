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

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ejercicio1Hibernate extends JFrame {

	private JPanel contentPane;
	private SessionFactory sesion;
	private Session session;

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

		JComboBox cBoxTiendas = new JComboBox();
		
		cBoxTiendas.setBounds(27, 30, 343, 20);
		contentPane.add(cBoxTiendas);
		
		sesion = HibernateUtil.getSessionFactory();
		
		session = sesion.openSession();

		Query q = session.createQuery("from Tiendas");

		List<Tiendas> lista = q.list();
		
		Iterator<Tiendas> it = lista.iterator();

		while (it.hasNext()) {

			Tiendas t1 = (Tiendas) it.next();

			cBoxTiendas.addItem(t1.toString());
		}
		
		cBoxTiendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				
			}

			
		});
		
		String hql = "from Ventas where nif = :nif";
		q = session.createQuery(hql);
		

		
	}
	
	private String seleccionaNifComboTiendas(JComboBox cBoxTiendas) {
		
		Tiendas t1 = new Tiendas();
		
		t1 = (Tiendas) cBoxTiendas.getSelectedItem();
		
		return t1.getNif();
	}

}
