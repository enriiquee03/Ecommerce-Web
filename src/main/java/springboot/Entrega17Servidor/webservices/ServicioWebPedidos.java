package springboot.Entrega17Servidor.webservices;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.Entrega17Servidor.datos.serviciosWeb.ResumenPedido;
import springboot.Entrega17Servidor.model.Usuario;
import springboot.Entrega17Servidor.servicios.ServicioPedidos;


@Controller
@RestController
@RequestMapping("servicioWebPedidos/")
public class ServicioWebPedidos {




	@Autowired
	private ServicioPedidos servicioPedidos;

	@RequestMapping("paso1")
	public ResumenPedido paso1(String nombre, String direccion, String provincia,String codigo_postal, String email,String tarjeta, String numero, String titular,String cvv,String fecha_caducidad,String notas_envio, String persona_contacto, String telefono_contacto, HttpServletRequest request){
		System.out.println("Entro en paso 1");
		String respuesta = "";
		//Obtenemos el usuario identificado y creamos el pedido
		Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
		servicioPedidos.procesarPaso1(nombre, direccion, provincia,codigo_postal,email, u.getId());
		servicioPedidos.procesarPaso2(titular, numero, tarjeta,cvv,fecha_caducidad, u.getId());
		servicioPedidos.procesarPaso3(notas_envio, persona_contacto, telefono_contacto, u.getId());
		ResumenPedido resumen = servicioPedidos.obtenerResumenDelPedido(u.getId());
		return resumen;
	}


	


@RequestMapping("paso3")

	public ResponseEntity<String> paso3(HttpServletRequest request){
		System.out.println("Entro en paso 3");
		String respuesta = "";
		Usuario u = (Usuario)request.getSession().getAttribute("usuario_identificado");
		servicioPedidos.confirmarPedido(u.getId());

		respuesta = "pedido completado";



		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

}
