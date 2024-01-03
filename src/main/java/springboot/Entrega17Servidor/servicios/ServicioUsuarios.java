package springboot.Entrega17Servidor.servicios;


import java.util.List;

import springboot.Entrega17Servidor.model.Usuario;

public interface ServicioUsuarios {
	void registrarUsuario(Usuario u);
	Usuario obtenerUsuarioPorEmailYpass(String email, String pass);
	List<Usuario> obtenerUsuarios();
	void borrarUsuarios (int id);
	Usuario obtenerUsuarioPorId(int id);
	void editarUsuario(Usuario u);
}
