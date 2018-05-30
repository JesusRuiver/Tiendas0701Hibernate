package primero;
// Generated 06-may-2018 17:11:48 by Hibernate Tools 3.5.0.Final

/**
 * Pedidos generated by hbm2java
 */
public class Pedidos implements java.io.Serializable {

	private PedidosId id;
	private Tiendas tiendas;
	private Articulos articulos;
	private Short unidadesPedidas;

	public Pedidos() {
	}

	public Pedidos(PedidosId id, Tiendas tiendas, Articulos articulos) {
		this.id = id;
		this.tiendas = tiendas;
		this.articulos = articulos;
	}

	public Pedidos(PedidosId id, Tiendas tiendas, Articulos articulos, Short unidadesPedidas) {
		this.id = id;
		this.tiendas = tiendas;
		this.articulos = articulos;
		this.unidadesPedidas = unidadesPedidas;
	}

	public PedidosId getId() {
		return this.id;
	}

	public void setId(PedidosId id) {
		this.id = id;
	}

	public Tiendas getTiendas() {
		return this.tiendas;
	}

	public void setTiendas(Tiendas tiendas) {
		this.tiendas = tiendas;
	}

	public Articulos getArticulos() {
		return this.articulos;
	}

	public void setArticulos(Articulos articulos) {
		this.articulos = articulos;
	}

	public Short getUnidadesPedidas() {
		return this.unidadesPedidas;
	}

	public void setUnidadesPedidas(Short unidadesPedidas) {
		this.unidadesPedidas = unidadesPedidas;
	}

}
