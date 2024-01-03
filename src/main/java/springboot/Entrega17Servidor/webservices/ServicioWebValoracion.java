package springboot.Entrega17Servidor.webservices;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.Entrega17Servidor.model.Usuario;
import springboot.Entrega17Servidor.servicios.ServicioPedidos;
import springboot.Entrega17Servidor.servicios.ServicioValoracion;



@Controller
@RestController
@RequestMapping("servicioWebValoracion/")
public class ServicioWebValoracion {

	@Autowired
	private ServicioValoracion servicioValoracion;

	@Autowired
	private ServicioPedidos servicioPedidos;

	@RequestMapping("crearValoracion")
	public String crearValoracion(@RequestParam("id") Integer idProducto,String nombre,String email,String texto,Integer valoracion, HttpServletRequest request) throws Exception{
		String respuesta = "";
		System.out.println("Que pasa usuario:  "+ request.getSession().getAttribute("usuario_identificado"));
		if(request.getSession().getAttribute("usuario_identificado") != null  ) {
			Usuario u = (Usuario)request.getSession().getAttribute("usuario_identificado");
			System.out.println("Id del producto: " + idProducto);
			if(!servicioValoracion.usuarioComproProducto(idProducto,u.getId())) {
				System.out.println("adios");
				servicioValoracion.crearValoracion( nombre, email,texto, valoracion, idProducto, u.getId());
				respuesta = "Valoracion creada con exito";
			}else {
				throw new Exception("*** NO HAS COMPRADO EL PRODUCTO ***");
			}
		}else {
			throw new Exception("*** USUARIO NO IDENTIFICADO ***");
		}

		return respuesta;
	}//end agregarZapatilla

}//end class
