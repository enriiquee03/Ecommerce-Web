package springboot.Entrega17Servidor.servicios;

import java.util.List;
import java.util.Map;

import springboot.Entrega17Servidor.model.Categoria;


public interface ServicioCategorias {

	void registrarCategorias(Categoria c);
	void borrarCategorias (int id);
	Categoria obtenerCategoriaPorId(int id);
	void editarCategoria(Categoria c);
	List<Categoria> obtenerCategoria();
	List<Categoria> obtenerCategoriasParaDesplegable();

}
