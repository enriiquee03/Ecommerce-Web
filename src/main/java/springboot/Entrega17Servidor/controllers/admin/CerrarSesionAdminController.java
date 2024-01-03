package springboot.Entrega17Servidor.controllers.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/")
public class CerrarSesionAdminController {


	@RequestMapping("cerrarSesionAdmin")
	public String cerrarSesionAdmin (HttpServletRequest request) {
		request.getSession().invalidate();
		return "inicio";
	}
}
