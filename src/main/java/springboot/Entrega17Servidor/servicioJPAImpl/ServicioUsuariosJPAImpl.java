package springboot.Entrega17Servidor.servicioJPAImpl;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.Entrega17Servidor.model.Usuario;
import springboot.Entrega17Servidor.servicios.ServicioUsuarios;

@Service
@Transactional
public class ServicioUsuariosJPAImpl implements ServicioUsuarios {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void registrarUsuario(Usuario u) {
		
		entityManager.persist(u);
	}

	@Override
	public Usuario obtenerUsuarioPorEmailYpass(String email, String pass) {
		Query query = entityManager.createQuery("select u from Usuario u where u.email = :email and u.pass = :pass");
		query.setParameter("email", email);
		query.setParameter("pass", pass);

		List<Usuario> resultado = query.getResultList();
		if(resultado.size() == 0) {
			return null;
		}else {
			return resultado.get(0);
		}
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		return entityManager.createQuery("SELECT u from Usuario u order by u.id desc").getResultList();
	}

	@Override
	public void borrarUsuarios(int id) {
		entityManager.remove(entityManager.find(Usuario.class, id));

	}

	@Override
	public Usuario obtenerUsuarioPorId(int id) {
		return entityManager.find(Usuario.class, id);
	}

	@Override
	public void editarUsuario(Usuario u) {
		if(u.getFotoSubida().getSize() == 0) {
			System.out.println("no se subio una nueva foto,tenemos que mantener la anterior");

			Usuario usuarioAnterior = entityManager.find(Usuario.class, u.getId());
			System.out.println(usuarioAnterior.getId());
			u.setAvatar(usuarioAnterior.getAvatar());
		}else {
			System.out.println("asignar la nueva foto al registro");
			try {
				u.setAvatar(u.getFotoSubida().getBytes());
			} catch (IOException e) {
				System.out.println("no pude procesar la foto subida");
				e.printStackTrace();
			}
		}
		entityManager.merge(u);

	}

}
