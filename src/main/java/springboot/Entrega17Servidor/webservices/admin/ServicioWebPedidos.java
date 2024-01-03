package springboot.Entrega17Servidor.webservices.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.Entrega17Servidor.servicios.ServicioPedidos;

@Controller(value="servicioWebPedidosAdmin")
//value indica el nombre del objeto que va a gestionar spring de esta clase
//si hay dos clases con el mismo nombre y cualquier anotacion de spring
//estamos obligados a cambiar el nombre de la referencia a spring
//como hacemos arriba
@RestController
@RequestMapping("admin/servicioWebPedidos")
public class ServicioWebPedidos {

	@Autowired
	private ServicioPedidos servicioPedidos;

	@RequestMapping("actualizarEstadoPedido")
	public String actualizarEstadoPedido (@RequestParam("id") Integer id, String estado) {
		servicioPedidos.actualizarEstadoPedido(id, estado);
		return "ok";
	}

}
