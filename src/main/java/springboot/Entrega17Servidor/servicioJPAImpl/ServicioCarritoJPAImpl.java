package springboot.Entrega17Servidor.servicioJPAImpl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.Entrega17Servidor.constantesSQL.ConstantesSQL;
import springboot.Entrega17Servidor.model.Carrito;
import springboot.Entrega17Servidor.model.ProductoCarrito;
import springboot.Entrega17Servidor.model.Usuario;
import springboot.Entrega17Servidor.model.Zapatilla;
import springboot.Entrega17Servidor.servicios.ServicioCarrito;



@Service
@Transactional
public class ServicioCarritoJPAImpl implements ServicioCarrito {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void agregarProducto(int idUsuario, int idProducto, int cantidad) {
		Usuario uBaseDeDatos = 
				(Usuario)entityManager.find(Usuario.class, idUsuario);

		Carrito c = uBaseDeDatos.getCarrito();
		
		if(c == null) {	
			c = new Carrito();
			c.setUsuario(uBaseDeDatos);
			uBaseDeDatos.setCarrito(c);
			entityManager.persist(c);
		}
		
		boolean producto_en_carrito = false;
		//ver si el producto ya esta en el carrito, y en tal
		//caso incrementar su cantidad
		for (ProductoCarrito pc : c.getProductosCarrito()) {
			if(pc.getZapatilla().getId() == idProducto) {
				producto_en_carrito = true;
				pc.setCantidad(pc.getCantidad()+cantidad);
				entityManager.merge(pc);
			}
			
		}
		//si el producto no esta en el carrito, creamos un productocarrito nuevo
		if(! producto_en_carrito) {
			ProductoCarrito pc = new ProductoCarrito();
			pc.setCarrito(c);
			pc.setZapatilla((Zapatilla)entityManager.find(Zapatilla.class, idProducto));
			pc.setCantidad(cantidad);
			entityManager.persist(pc);
		}
		
	}

	@Override
	public void actualizarProductoCarrito(int idUsuario, int idProducto, int cantidad) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void borrarProductoCarrito(int idUsuario, int idProducto) {
		Usuario u = entityManager.find(Usuario.class, idUsuario);
		Carrito c = u.getCarrito();
		if(c != null) {
			Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_BORRAR_PRODUCTO_CARRITO);
			query.setParameter("carrito_id", c.getId());
			query.setParameter("zapatilla_id", idProducto);
			
			query.executeUpdate();
		}
		
	}


	@Override
	public List<Map<String, Object>> obtenerProductosCarrito(int idUsuario) {
		Usuario u = (Usuario)entityManager.find(Usuario.class, idUsuario);
		Carrito c = u.getCarrito();
		List<Map<String, Object>> res = null;
		if(c != null ) {
			Query query = entityManager.createNativeQuery(
					ConstantesSQL.SQL_OBTENER_PRODUCTOS_CARRITO);
			query.setParameter("par_variable", c.getId());
			NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
			nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			res = nativeQuery.getResultList(); 
		}		
		return res;
	}

	@Override
	public String obtenerTotalDeProductosCarritoPorUsuario(int idUsuario) {
		Query q = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_TOTAL_CARRITO_USUARIO);
		q.setParameter("id_usuario", idUsuario );
		String  totalPCarrito =  q.getSingleResult().toString() ;
		return totalPCarrito;
	}
	
}
