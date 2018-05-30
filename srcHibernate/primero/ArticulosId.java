package primero;
// Generated 06-may-2018 17:11:48 by Hibernate Tools 3.5.0.Final

/**
 * ArticulosId generated by hbm2java
 */
public class ArticulosId implements java.io.Serializable {

	private String articulo;
	private short codFabricante;
	private short peso;
	private String categoria;

	public ArticulosId() {
	}

	public ArticulosId(String articulo, short codFabricante, short peso, String categoria) {
		this.articulo = articulo;
		this.codFabricante = codFabricante;
		this.peso = peso;
		this.categoria = categoria;
	}

	public String getArticulo() {
		return this.articulo;
	}

	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}

	public short getCodFabricante() {
		return this.codFabricante;
	}

	public void setCodFabricante(short codFabricante) {
		this.codFabricante = codFabricante;
	}

	public short getPeso() {
		return this.peso;
	}

	public void setPeso(short peso) {
		this.peso = peso;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ArticulosId))
			return false;
		ArticulosId castOther = (ArticulosId) other;

		return ((this.getArticulo() == castOther.getArticulo()) || (this.getArticulo() != null
				&& castOther.getArticulo() != null && this.getArticulo().equals(castOther.getArticulo())))
				&& (this.getCodFabricante() == castOther.getCodFabricante()) && (this.getPeso() == castOther.getPeso())
				&& ((this.getCategoria() == castOther.getCategoria()) || (this.getCategoria() != null
						&& castOther.getCategoria() != null && this.getCategoria().equals(castOther.getCategoria())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getArticulo() == null ? 0 : this.getArticulo().hashCode());
		result = 37 * result + this.getCodFabricante();
		result = 37 * result + this.getPeso();
		result = 37 * result + (getCategoria() == null ? 0 : this.getCategoria().hashCode());
		return result;
	}

}
