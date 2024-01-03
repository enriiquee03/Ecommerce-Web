package springboot.Entrega17Servidor.servicios;

import java.util.List;

import springboot.Entrega17Servidor.datos.serviciosWeb.ResumenPedido;
import springboot.Entrega17Servidor.model.Pedido;


public interface ServicioPedidos {


	//para la gestion en administracion
	List<Pedido> obtenerPedidos();
	Pedido obtenerPedidoPorId(int idPedido);
	void actualizarEstadoPedido(int idPedido, String estado);
	List<Pedido> obtenerPedidosDeUsuario(int idUsuario);

	//funciones para ajax
	void procesarPaso1(String nombreCompleto, String direccion, String provincia,String codigo_postal,String email, int idUsuario);
	void procesarPaso2(String titular, String numero, String tipoTarjeta,String cvv, String fecha_caducidadint,int  idUsuario);
	void procesarPaso3(String notas_envio, String persona_contacto, String telefono_contacto,int  idUsuario);
	ResumenPedido obtenerResumenDelPedido(int idUsuario);
	void confirmarPedido(int idUsuario);
}
