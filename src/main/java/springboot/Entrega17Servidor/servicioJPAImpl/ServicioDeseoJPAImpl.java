package springboot.Entrega17Servidor.servicioJPAImpl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.Entrega17Servidor.constantesSQL.ConstantesSQL;
import springboot.Entrega17Servidor.model.Deseo;
import springboot.Entrega17Servidor.model.ProductoDeseo;
import springboot.Entrega17Servidor.model.Usuario;
import springboot.Entrega17Servidor.model.Zapatilla;
import springboot.Entrega17Servidor.servicios.ServicioDeseo;



@Service
@Transactional
public class ServicioDeseoJPAImpl implements ServicioDeseo {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void agregarProducto(int idUsuario, int idProducto, int cantidad) {
		Usuario uBaseDeDatos =
				entityManager.find(Usuario.class, idUsuario);

		Deseo d = uBaseDeDatos.getDeseo();

		if(d == null) {
			d = new Deseo();
			d.setUsuario(uBaseDeDatos);
			uBaseDeDatos.setDeseo(d);
			entityManager.persist(d);
		}

		boolean producto_en_deseo = false;
		//ver si el producto ya esta en el carrito, y en tal
		//caso incrementar su cantidad
		for (ProductoDeseo pd : d.getProductosDeseo()) {
			if(pd.getZapatilla().getId() == idProducto) {
				producto_en_deseo = true;
				pd.setCantidad(pd.getCantidad()+cantidad);
				entityManager.merge(pd);
			}

		}
		//si el producto no esta en el carrito, creamos un productocarrito nuevo
		if(! producto_en_deseo) {
			ProductoDeseo pd = new ProductoDeseo();
			pd.setDeseo(d);
			pd.setZapatilla(entityManager.find(Zapatilla.class, idProducto));
			pd.setCantidad(cantidad);
			entityManager.persist(pd);
		}

	}

	@Override
	public void actualizarProductoDeseo(int idUsuario, int idProducto, int cantidad) {
		// TODO Auto-generated method stub

	}

	@Override
	public void borrarProductoDeseo(int idUsuario, int idProducto) {
		Usuario u = entityManager.find(Usuario.class, idUsuario);
		Deseo d = u.getDeseo();
		if(d != null) {
			Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_BORRAR_PRODUCTO_DESEO);
			query.setParameter("deseo_id", d.getId());
			query.setParameter("zapatilla_id", idProducto);

			query.executeUpdate();
		}

	}


	@Override
	public List<Map<String, Object>> obtenerProductosDeseo(int idUsuario) {
		Usuario u = entityManager.find(Usuario.class, idUsuario);
		Deseo c = u.getDeseo();
		List<Map<String, Object>> res = null;
		if(c != null ) {
			Query query = entityManager.createNativeQuery(
					ConstantesSQL.SQL_OBTENER_PRODUCTOS_DESEO);
			query.setParameter("par_variable", c.getId());
			NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
			nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			res = nativeQuery.getResultList();
		}
		return res;
	}
	
	public String obtenerTotalDeProductosDeseoPorUsuario(int idUsuario) {
		Query q = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_TOTAL_DESEO_USUARIO);
		q.setParameter("id_usuario", idUsuario );
		String  totalPDeseo=  q.getSingleResult().toString() ;
		return totalPDeseo;
	}

}
