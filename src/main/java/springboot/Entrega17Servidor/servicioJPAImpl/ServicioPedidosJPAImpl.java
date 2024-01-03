package springboot.Entrega17Servidor.servicioJPAImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.Entrega17Servidor.constantes.EstadosPedido;
import springboot.Entrega17Servidor.constantesSQL.ConstantesSQL;
import springboot.Entrega17Servidor.datos.serviciosWeb.ResumenPedido;
import springboot.Entrega17Servidor.model.Carrito;
import springboot.Entrega17Servidor.model.Categoria;
import springboot.Entrega17Servidor.model.Pedido;
import springboot.Entrega17Servidor.model.ProductoCarrito;
import springboot.Entrega17Servidor.model.ProductoPedido;
import springboot.Entrega17Servidor.model.Usuario;
import springboot.Entrega17Servidor.servicios.ServicioCarrito;
import springboot.Entrega17Servidor.servicios.ServicioPedidos;



@Service
@Transactional
public class ServicioPedidosJPAImpl implements ServicioPedidos {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ServicioCarrito servicioCarrito;

	@Override
	public List<Pedido> obtenerPedidos() {
		return entityManager.createQuery("select p from Pedido p order by p.id desc").getResultList();
	}

	@Override
	public Pedido obtenerPedidoPorId(int idPedido) {
		return entityManager.find(Pedido.class, idPedido);
	}

	@Override
	public void actualizarEstadoPedido(int idPedido, String estado) {
		Pedido p = entityManager.find(Pedido.class, idPedido);

		p.setEstado(estado);

		entityManager.merge(p);

	}

	@Override
	public void procesarPaso1(String nombreCompleto, String direccion, String provincia,String codigo_postal,String email, int idUsuario) {
		//Cada usuario podra tener como maximo un solo pedido en estado en "en proceso",
		//ese es el pedido donde rellenaremos los datos de envio, los de pago
		// y demas si hubiera
		//si el usuario finaliza un pedido en estado "en proceso", el estado de dicho pedido
		//pasara a ser "terminado"
		//puede haber tantos pedidos en estado terminado como se quiera


		Pedido p = obtenerPedidoActual(idUsuario);

		p.setNombreCompleto(nombreCompleto);
		p.setDireccion(direccion);
		p.setProvincia(provincia);
		p.setCodigo_postal(codigo_postal);
		p.setEmail(email);
		p.setEstado(EstadosPedido.EN_PROCESO);

		entityManager.merge(p);
	}



	@Override
	public void procesarPaso2(String titular, String numero, String tipoTarjeta,String cvv, String fecha_caducidad, int idUsuario) {
		Pedido p = obtenerPedidoActual(idUsuario);
		p.setTitularTarjeta(titular);
		p.setNumeroTarjeta(numero);
		p.setTipoTarjeta(tipoTarjeta);
		p.setCvv(cvv);
		p.setFecha_caducidad(fecha_caducidad);

		entityManager.merge(p);
	}

	@Override
	public void procesarPaso3(String notas_envio, String persona_contacto, String telefono_contacto, int idUsuario) {
		Pedido p = obtenerPedidoActual(idUsuario);
		p.setNotas_envio(notas_envio);
		p.setPersona_contacto(persona_contacto);
		p.setTelefono_contacto(telefono_contacto);

		entityManager.merge(p);
	}

	@Override
	public ResumenPedido obtenerResumenDelPedido(int idUsuario) {
		ResumenPedido resumen = new ResumenPedido();
		Pedido p = obtenerPedidoActual(idUsuario);
		resumen.setNombreCompleto(p.getNombreCompleto());
		resumen.setDireccion(p.getDireccion());
		resumen.setProvincia(p.getProvincia());
		resumen.setCodigo_postal(p.getCodigo_postal());
		resumen.setEmail(p.getEmail());
		
		
		resumen.setTipoTarjeta(p.getTipoTarjeta());
		resumen.setTitularTarjeta(p.getTitularTarjeta());
		resumen.setNumeroTarjeta(p.getNumeroTarjeta());
		resumen.setCvv(p.getCvv());
		resumen.setFecha_caducidad(p.getFecha_caducidad());
		
		resumen.setNotas_envio(p.getNotas_envio());
		resumen.setPersona_contacto(p.getPersona_contacto());
		resumen.setTelefono_contacto(p.getTelefono_contacto());

		resumen.setZapatillas(servicioCarrito.obtenerProductosCarrito(idUsuario));

		return resumen;
	}




	private Pedido obtenerPedidoActual(int idUsuario) {
		Usuario uBaseDatos = entityManager.find(Usuario.class, idUsuario);
		Object pedidoEnProceso = null;
		List<Pedido> resultadoConsulta = entityManager.createQuery(
				"select p from Pedido p where p.estado = :estado and p.usuario.id = :usuario_id ")
				.setParameter("estado", EstadosPedido.EN_PROCESO)
				.setParameter("usuario_id", uBaseDatos.getId()).getResultList();
		if(resultadoConsulta.size() == 1) {
			pedidoEnProceso = resultadoConsulta.get(0);
		}else {
			pedidoEnProceso = null;
		}
		Pedido p = null;
		if (pedidoEnProceso != null ) {
			p = (Pedido)pedidoEnProceso;
		}else {
			p = new Pedido();
			p.setUsuario(uBaseDatos);
		}
		return p;
	}//end obtenerPedidoActual

	@Override
	public void confirmarPedido(int idUsuario) {
		Pedido p = obtenerPedidoActual(idUsuario);
		Usuario uBaseDatos = entityManager.find(Usuario.class, idUsuario);

		Carrito c = uBaseDatos.getCarrito();

		if(c != null && c.getProductosCarrito().size() > 0) {
			for (ProductoCarrito pc : c.getProductosCarrito()) {
				ProductoPedido pp = new ProductoPedido();
				pp.setCantidad(pc.getCantidad());
				pp.setZapatilla(pc.getZapatilla());
				p.getProductosPedido().add(pp);
				pp.setPedido(p);
				entityManager.persist(pp);
			}
		}

		//borrar los productos del carrito del usuario
		Query query =
				entityManager.createNativeQuery(
						ConstantesSQL.SQL_BORRAR_PRODUCTOS_CARRITO);
		query.setParameter("carrito_id", c.getId());
		query.executeUpdate();
		//finalizamos pedido
		p.setEstado(EstadosPedido.TERMINADO);
		entityManager.merge(p);
	}//end confirmasPedido
	
	
	@Override
	public List<Pedido> obtenerPedidosDeUsuario(int idUsuario) {
		Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_PEDIDOS_USUARIO);
		query.setParameter("id_usuario", idUsuario);
	    List<Object[]> resultList = query.getResultList();
	    List<Pedido> pedidos = new ArrayList();
	    
	    for (Object[] result : resultList) {
	        Pedido p = new Pedido();
	        p.setId((Integer) result[0]);
	        p.setDireccion(result[3].toString());
	        p.setEstado(result[5].toString());
	        p.setNombreCompleto(result[7].toString());
	        p.setProvincia(result[11].toString());
	        p.setTitularTarjeta(result[14].toString());
	        

	        pedidos.add(p);
	    }

	    return pedidos;
	}

}
