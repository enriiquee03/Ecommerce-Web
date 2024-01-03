package springboot.Entrega17Servidor.webservices;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.Entrega17Servidor.model.Usuario;
import springboot.Entrega17Servidor.servicios.ServicioCarrito;



@Controller
@RestController
@RequestMapping("servicioWebCarrito/")
public class ServicioWebCarrito {

	@Autowired
	private ServicioCarrito servicioCarrito;

	@RequestMapping("agregarZapatilla")
	public ResponseEntity<String> agregarZapatilla(String id, String cantidad, HttpServletRequest request){
		String respuesta = "";
		if(request.getSession().getAttribute("usuario_identificado") != null) {
			//esto es que el usuario se identifico correctamente
			Usuario u = (Usuario)request.getSession().getAttribute("usuario_identificado");
			//u es el usuario que meti en sesion cuando el usuario se identifico
			respuesta = " agregar producto de id: " + id + " cantidad: " + cantidad + " " +
					" al usuario de id: " + u.getId();
			servicioCarrito.agregarProducto(
					u.getId(),
					Integer.parseInt(id),
					Integer.parseInt(cantidad));
		}else {
			respuesta = "usuario no identificado, identificate para poder comprar productos";
		}

		return new ResponseEntity<>(respuesta,HttpStatus.OK);
	}//end agregarZapatilla

	@RequestMapping("obtenerProductosCarrito")
	public List<Map<String, Object>> obtenerProductosCarrito(HttpServletRequest request) throws Exception{
		//la comprobacion de si el usuario es identificado la haremos un poco mas adelante
		//usando un interceptor a parte, para no estar poniendo este if else
		//todo el rato
		System.out.println("Usuario carrito:  " + request.getSession().getAttribute("usuario_identificado"));
		if(request.getSession().getAttribute("usuario_identificado") != null) {

			return servicioCarrito.obtenerProductosCarrito(
					((Usuario)request.getSession().getAttribute("usuario_identificado")).getId());

		}else {
			throw new Exception("*** USUARIO NO IDENTIFICADO ***");
		}


	}//end obtenerProductosCarrito

	@RequestMapping("borrarProducto")
	public String borrarProducto (@RequestParam("id") Integer id, HttpServletRequest request) {
		servicioCarrito.borrarProductoCarrito(((Usuario)request.getSession().getAttribute("usuario_identificado") ).getId(), id);
		return "ok";
	}//end borrarProducto
}//end class
