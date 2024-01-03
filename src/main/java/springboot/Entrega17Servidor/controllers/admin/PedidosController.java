package springboot.Entrega17Servidor.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import springboot.Entrega17Servidor.constantes.EstadosPedido;
import springboot.Entrega17Servidor.model.Pedido;
import springboot.Entrega17Servidor.servicios.ServicioPedidos;



@Controller
@RequestMapping("admin/")
public class PedidosController {

	@Autowired
	private ServicioPedidos  serviciopedidos;




	@RequestMapping("obtenerPedidos")
	public String obtenerPedidos(Model model) {
		model.addAttribute("pedidos", serviciopedidos.obtenerPedidos());

		return "admin/pedidos";
	}

	@RequestMapping("verDetallesPedido")
	public String verDetallesPedido(String id, Model model) {
		Pedido p = serviciopedidos.obtenerPedidoPorId(Integer.parseInt(id));
		model.addAttribute("pedido", p);
		Map<String, String> estados = new HashMap<>();
		estados.put(EstadosPedido.TERMINADO, "finalizado por el usuario");
		estados.put(EstadosPedido.LISTO_PARA_ENVIAR, "listo para ser recogido por la empresa de mensajeria");
		estados.put(EstadosPedido.RECIBIDO_POR_EL_CLIENTE, "el cliente ha recibido correctamente el pedido");
		model.addAttribute("estados", estados);



		return "admin/pedidos_detalle";
	}


}
