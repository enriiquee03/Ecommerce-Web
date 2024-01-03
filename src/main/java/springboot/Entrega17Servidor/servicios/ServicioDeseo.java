package springboot.Entrega17Servidor.servicios;

import java.util.List;
import java.util.Map;



public interface ServicioDeseo {

	void agregarProducto(int idUsuario, int idProducto, int cantidad);
	void actualizarProductoDeseo(int idUsuario, int idProducto, int cantidad );
	void borrarProductoDeseo(int idUsuario, int idProducto);
	String obtenerTotalDeProductosDeseoPorUsuario(int idUsuario);

	//operaciones para ajax
	List<Map<String, Object>> obtenerProductosDeseo(int idUsuario);

}
