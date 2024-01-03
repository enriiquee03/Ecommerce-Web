package springboot.Entrega17Servidor.controllers.imagen;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.Entrega17Servidor.servicios.ServicioCategorias;
import springboot.Entrega17Servidor.servicios.ServicioUsuarios;
import springboot.Entrega17Servidor.servicios.ServicioZapatillas;


@Controller
public class MostrarImagenProducto {

	@Autowired
	private ServicioZapatillas servicioZapatillas;
	@Autowired
	private ServicioUsuarios servicioUsuarios;
	@Autowired
	private ServicioCategorias servicioCategorias;

	@RequestMapping("mostrar_imagen")
	public void mostrar_imagen(@RequestParam("id") Integer id, 
	                           @RequestParam("campo") String campo, 
	                           HttpServletResponse response) throws IOException {
	    byte[] info = null;

	    switch (campo) {
	        case "portada":
	            info = servicioZapatillas.obtenerZapatillaPorId(id).getImagenPortada();
	            break;
	        case "detalle_1":
	            info = servicioZapatillas.obtenerZapatillaPorId(id).getImagenDetalle_1();
	            break;
	        case "detalle_2":
	        	info = servicioZapatillas.obtenerZapatillaPorId(id).getImagenDetalle_2();
	        	break;
	        case "detalle_3":
	        	info = servicioZapatillas.obtenerZapatillaPorId(id).getImagenDetalle_3();
	        	break;
	        case "detalle_4":
	        	info = servicioZapatillas.obtenerZapatillaPorId(id).getImagenDetalle_4();
	        	break;
	        case "detalle_5":
	        	info = servicioZapatillas.obtenerZapatillaPorId(id).getImagenDetalle_5();
	        	break;

	    }

	    if (info == null) {
	        return;
	    }

	    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    response.getOutputStream().write(info);
	    response.getOutputStream().close();
	}


	@RequestMapping("mostrar_imagen_usuario")
	public void mostrar_imagen_usuario(@RequestParam("id") Integer id, HttpServletResponse response ) throws IOException {
		byte[] info = servicioUsuarios.obtenerUsuarioPorId(id).getAvatar();
		if(info == null) {
			return;
		}
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(info);
		response.getOutputStream().close();
	}
	
	
	@RequestMapping("mostrar_imagen_categoria")
	public void mostrar_imagen_categoria(@RequestParam("id") Integer id, HttpServletResponse response ) throws IOException {
		byte[] info = servicioCategorias.obtenerCategoriaPorId(id).getImagenCategoria();
		if(info == null) {
			return;
		}
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(info);
		response.getOutputStream().close();
	}


}
