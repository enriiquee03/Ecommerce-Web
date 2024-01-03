package springboot.Entrega17Servidor.servicios;

import java.util.List;

import springboot.Entrega17Servidor.model.Valoracion;

public interface ServicioValoracion {

	boolean usuarioComproProducto(int idProducto, int idUsuario);
	List <Valoracion> obtenerValoracionesDeZapatilla(int idProducto);
	void crearValoracion(String nombre, String email,String texto, Integer valoracion, int idProducto, int idUsuario);
	int obtenerTotalValoracionDeZapatilla(int idProducto);


}
