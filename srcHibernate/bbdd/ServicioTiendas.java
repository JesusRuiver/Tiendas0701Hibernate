package bbdd;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import primero.HibernateUtil;
import primero.Tiendas;

public class ServicioTiendas {

	private Session sesion;

	public ServicioTiendas() {

		sesion = HibernateUtil.getSessionFactory().openSession();
	}

	public List<Tiendas> dameTiendas() {

		Query q = sesion.createQuery("select nombre, nif from Tiendas");

		return q.list();
	}

}
