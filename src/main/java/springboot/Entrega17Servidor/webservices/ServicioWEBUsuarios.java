package springboot.Entrega17Servidor.webservices;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import springboot.Entrega17Servidor.model.Usuario;
import springboot.Entrega17Servidor.servicios.ServicioCarrito;
import springboot.Entrega17Servidor.servicios.ServicioDeseo;
import springboot.Entrega17Servidor.servicios.ServicioPedidos;
import springboot.Entrega17Servidor.servicios.ServicioUsuarios;



@Controller
@RestController
@RequestMapping("servicioWebUsuarios")
public class ServicioWEBUsuarios {

	@Autowired
	private ServicioUsuarios servicioUsuarios;
	@Autowired
	private ServicioCarrito servicioCarrito;
	@Autowired
	private ServicioDeseo servicioDeseo;
	@Autowired
	private ServicioPedidos servicioPedidos;

	@RequestMapping("registrarUsuario")
	public String registrarUsuario
	(@RequestParam Map<String, Object> formData,
			MultipartHttpServletRequest request) {

		System.out.println("recibido formData: " + formData);
		System.out.println("recibido foto: " + request.getFile("avatar"));

			Gson gson = new Gson();
			JsonElement json = gson.toJsonTree(formData);
			Usuario u = gson.fromJson(json, Usuario.class);
			
			System.out.println(json);
			try {
				u.setAvatar(request.getFile("avatar").getBytes());
			} catch (IOException e) {
				System.out.println("no pude asignar la foto al usuario");
				e.printStackTrace();
			}

			servicioUsuarios.registrarUsuario(u);



			return "usuario registrado, ya puedes identificarte";
	}//end registrarUsuario

	@RequestMapping("identificarUsuario")
	public ResponseEntity<Map<String, Object>> identificarUsuario(String email, String pass, HttpServletRequest request) {
	    Usuario u = servicioUsuarios.obtenerUsuarioPorEmailYpass(email, pass);
	    Map<String, Object> respuesta = new HashMap<>();

	    if (u != null) {
	        request.getSession().setAttribute("usuario_identificado", u);
	        respuesta.put("status", "ok");
	        respuesta.put("mensaje", u.getNombre());
	        respuesta.put("total_carrito", servicioCarrito.obtenerTotalDeProductosCarritoPorUsuario(u.getId()));
	        respuesta.put("total_deseo", servicioDeseo.obtenerTotalDeProductosDeseoPorUsuario(u.getId()));
	    } else {
	        respuesta.put("status", "error");
	        respuesta.put("mensaje", "email o pass incorrecto");
	    }

	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@RequestMapping("obtenerDatosYPedidosUsuario")
	public ResponseEntity<Map<String, Object>> obtenerDatosYPedidosUsuario(String email, String pass, HttpServletRequest request) throws Exception {
	    Usuario u = (Usuario) request.getSession().getAttribute("usuario_identificado");
	    if(request.getSession().getAttribute("usuario_identificado") != null) {
	    Map<String, Object> respuesta = new HashMap<>();
	    
	    
	    	if(u != null) {
	        respuesta.put("usuario_nombre", u.getNombre());
	        respuesta.put("usuario_email", u.getEmail());
	        respuesta.put("pedidos", servicioPedidos.obtenerPedidosDeUsuario(u.getId()));
	    	}

	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
	    }else {
	    	throw new Exception("*** USUARIO NO IDENTIFICADO ***");
	    }
	}

	@RequestMapping("logout")
	public ResponseEntity<String> logout (HttpServletRequest request){
		request.getSession().invalidate();
		String respuesta= "ok";
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}//end logout

}
