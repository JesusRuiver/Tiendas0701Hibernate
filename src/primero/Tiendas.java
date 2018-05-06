package primero;
// Generated 06-may-2018 17:11:48 by Hibernate Tools 3.5.0.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Tiendas generated by hbm2java
 */
public class Tiendas implements java.io.Serializable {

	private String nif;
	private String nombre;
	private String direccion;
	private String poblacion;
	private String provincia;
	private Integer codPostal;
	private Set<Pedidos> pedidoses = new HashSet<Pedidos>(0);
	private Set<Ventas> ventases = new HashSet<Ventas>(0);

	public Tiendas() {
	}

	public Tiendas(String nif) {
		this.nif = nif;
	}

	public Tiendas(String nif, String nombre, String direccion, String poblacion, String provincia, Integer codPostal,
			Set<Pedidos> pedidoses, Set<Ventas> ventases) {
		this.nif = nif;
		this.nombre = nombre;
		this.direccion = direccion;
		this.poblacion = poblacion;
		this.provincia = provincia;
		this.codPostal = codPostal;
		this.pedidoses = pedidoses;
		this.ventases = ventases;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPoblacion() {
		return this.poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Integer getCodPostal() {
		return this.codPostal;
	}

	public void setCodPostal(Integer codPostal) {
		this.codPostal = codPostal;
	}

	public Set<Pedidos> getPedidoses() {
		return this.pedidoses;
	}

	public void setPedidoses(Set<Pedidos> pedidoses) {
		this.pedidoses = pedidoses;
	}

	public Set<Ventas> getVentases() {
		return this.ventases;
	}

	public void setVentases(Set<Ventas> ventases) {
		this.ventases = ventases;
	}

}
