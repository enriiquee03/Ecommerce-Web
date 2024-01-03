package springboot.Entrega17Servidor.servicios;

import java.util.List;
import java.util.Map;



public interface ServicioCarrito {

	void agregarProducto(int idUsuario, int idProducto, int cantidad);
	void actualizarProductoCarrito(int idUsuario, int idProducto, int cantidad );
	void borrarProductoCarrito(int idUsuario, int idProducto);
	String obtenerTotalDeProductosCarritoPorUsuario(int idUsuario);

	//operaciones para ajax
	List<Map<String, Object>> obtenerProductosCarrito(int idUsuario);

}
