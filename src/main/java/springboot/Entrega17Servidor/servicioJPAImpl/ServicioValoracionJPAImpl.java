package springboot.Entrega17Servidor.servicioJPAImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.Entrega17Servidor.constantesSQL.ConstantesSQL;
import springboot.Entrega17Servidor.model.Usuario;
import springboot.Entrega17Servidor.model.Valoracion;
import springboot.Entrega17Servidor.model.Zapatilla;
import springboot.Entrega17Servidor.servicios.ServicioValoracion;



@Service
@Transactional
public class ServicioValoracionJPAImpl implements ServicioValoracion {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void crearValoracion(String nombre,String email,String texto,Integer valoracion,int idProducto, int idUsuario) {
		System.out.println(valoracion);
		Usuario uBaseDeDatos =
				entityManager.find(Usuario.class, idUsuario);
		Zapatilla zBaseDeDatos =
				entityManager.find(Zapatilla.class, idProducto);
		Valoracion v = new Valoracion();

		v.setEmail(email);
		v.setNombre(nombre);
		v.setTexto(texto);
		v.setValoracion(valoracion);
		v.setUsuario(uBaseDeDatos);
		v.setZapatilla(zBaseDeDatos);

		entityManager.persist(v);

	}

	@Override
	public boolean usuarioComproProducto(int idProducto, int idUsuario) {
		Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_PRODUCTO_COMPRADO_POR_USUARIO);
		query.setParameter("usuario_id", idUsuario);



		return query.getResultList().isEmpty();
	}

	@Override
	public List<Valoracion> obtenerValoracionesDeZapatilla(int idProducto) {
		 Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_VALORACIONES_DE_ZAPATILLA);
		    query.setParameter("id_producto", idProducto);

		    List<Object[]> resultList = query.getResultList();
		    List<Valoracion> valoraciones = new ArrayList();

		    for (Object[] result : resultList) {
		        Valoracion valoracion = new Valoracion();
		        valoracion.setId((Integer) result[0]);
		        valoracion.setValoracion((Integer) result[1]);
		        valoracion.setEmail((String) result[2]);
		        valoracion.setNombre((String) result[3]);
		        valoracion.setTexto((String) result[4]);


		        valoraciones.add(valoracion);
		    }

		    return valoraciones;
	}
	
	@Override
	public int obtenerTotalValoracionDeZapatilla(int idProducto) {
		Query q = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_TOTAL_VALORACIONES);
		q.setParameter("idProducto", idProducto);
		int totalValoraciones =  Integer.parseInt(q.getSingleResult().toString()) ;
		return totalValoraciones;
	}



}
