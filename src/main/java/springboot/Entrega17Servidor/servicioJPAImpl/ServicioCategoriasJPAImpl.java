package springboot.Entrega17Servidor.servicioJPAImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.Entrega17Servidor.constantesSQL.ConstantesSQL;
import springboot.Entrega17Servidor.model.Categoria;
import springboot.Entrega17Servidor.model.Usuario;
import springboot.Entrega17Servidor.servicios.ServicioCategorias;



@Service
@Transactional
public class ServicioCategoriasJPAImpl implements ServicioCategorias{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Categoria> obtenerCategoriasParaDesplegable() {
		Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_CATEGORIAS_PARA_DESPLEGABLE);

	    List<Object[]> resultList = query.getResultList();
	    List<Categoria> categorias = new ArrayList();

	    for (Object[] result : resultList) {
	        Categoria c = new Categoria();
	        c.setId((Integer) result[0]);
	        c.setNombre(result[1].toString());


	        categorias.add(c);
	    }

	    return categorias;
	}

	@Override
	public List<Categoria> obtenerCategoria() {

		return entityManager.createQuery("select c from Categoria c").getResultList();
	}

	@Override
	public void registrarCategorias(Categoria c) {
		try {
			c.setImagenCategoria(c.getFotoSubida().getBytes());
		} catch (IOException e) {
			System.out.println("no pude procesar la foto subida");
			e.printStackTrace();
		}
		entityManager.persist(c);

	}

	@Override
	public void borrarCategorias(int id) {
		entityManager.remove(entityManager.find(Categoria.class, id));

	}

	@Override
	public Categoria obtenerCategoriaPorId(int id) {
		return entityManager.find(Categoria.class, id);
	}

	@Override
	public void editarCategoria(Categoria c) {
		if(c.getFotoSubida().getSize() == 0) {
			System.out.println("no se subio una nueva foto,tenemos que mantener la anterior");

			Categoria categoriaAnterior = entityManager.find(Categoria.class, c.getId());
			System.out.println(categoriaAnterior.getId());
			c.setImagenCategoria(categoriaAnterior.getImagenCategoria());
		}else {
			System.out.println("asignar la nueva foto al registro");
			try {
				c.setImagenCategoria(c.getFotoSubida().getBytes());
			} catch (IOException e) {
				System.out.println("no pude procesar la foto subida");
				e.printStackTrace();
			}
		}
		entityManager.merge(c);

	}

}
