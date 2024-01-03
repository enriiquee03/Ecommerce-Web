package springboot.Entrega17Servidor.servicios;

import java.util.List;
import java.util.Map;

import springboot.Entrega17Servidor.model.Zapatilla;



public interface ServicioZapatillas {

	void registrarZapatilla(Zapatilla z);
	List<Zapatilla> obtenerZapatillas();
	List<Zapatilla> obtenerZapatillasPorMarca(String marca);
	List<Zapatilla> obtenerZapatillasPorMarcaYcomienzoFin(String marca, int comienzo, int resultadoPorPagina);
	void borrarZapatillas (int id);
	int obtenerTotalZapatillas(String marca);
	Zapatilla obtenerZapatillaPorId(int id);
	void editarZapatilla(Zapatilla z);

	//Metodos para la comunicación por ajax
		List<Map<String, Object>>obtenerZapatillasParaFormarJson(String marca, int comienzo);
		Map<String, Object> obtenerDetallesZapatilla(int parseInt);
}
