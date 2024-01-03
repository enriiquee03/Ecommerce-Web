package springboot.Entrega17Servidor.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginAdminController {


	@RequestMapping("loginAdmin")
	public String loginAdmin() {
		return "admin/loginAdmin";
	}


	@RequestMapping("logoutAdmin")
	public String logoutAdmin(HttpServletRequest request) {
		request.getSession().invalidate();
		return "inicio_es";
	}
}
