package springboot.Entrega17Servidor.controllers.admin;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.Entrega17Servidor.model.Usuario;
import springboot.Entrega17Servidor.servicios.ServicioCategorias;
import springboot.Entrega17Servidor.servicios.ServicioUsuarios;



@Controller
@RequestMapping("admin/")
public class UsuariosController {

	@Autowired //usando esto, con la interfaz es lo mismo que pedir ServicioZapatillasImpl
	private ServicioUsuarios servicioUsuarios;

	@Autowired
	private ServicioCategorias servicioCategorias ;

	@RequestMapping("obtenerUsuarios")
	public String obtenerUsuarios(Model model) {
		model.addAttribute("usuarios", servicioUsuarios.obtenerUsuarios());
		return "admin/usuarios";
	}

	@RequestMapping("registrarUsuarios")
	public String registrarUsuarios(Model model) {
		Usuario z = new Usuario();
		model.addAttribute("nuevoUsuario",z);
		//formar las categorias para un desplegable


		return "admin/usuarios_registro";

	}

	@RequestMapping("guardarUsuario")
	public String guardarUsuario(@ModelAttribute("nuevoUsuario") @Valid Usuario nuevaUsuario ,BindingResult resultadoValidaciones, Model model, HttpServletRequest request) {
		if(resultadoValidaciones.hasErrors()) {
			return  "admin/usuarios_registro";
		}
		
		servicioUsuarios.registrarUsuario(nuevaUsuario);
		return "admin/usuarios_registro_ok";
	}

	@RequestMapping("borrarUsuario")
	public String borrarUsuario(@RequestParam("id") Integer id, Model model) {
		servicioUsuarios.borrarUsuarios(id);
		return obtenerUsuarios(model);
	}

	@RequestMapping("editarUsuario")
	public String editarUsuario(@RequestParam("id") Integer id, Model model) {
		Usuario usuario = servicioUsuarios.obtenerUsuarioPorId(id);
		model.addAttribute("usuarioEditar", usuario);
		return "admin/usuarios_editar";
	}

	@RequestMapping("guardarCambiosUsuario")
	public String guardarCambiosUsuario(@ModelAttribute("usuarioEditar") @Valid  Usuario usuarioEditar,BindingResult resultadoValidaciones, Model model, HttpServletRequest request) {
		
		if(resultadoValidaciones.hasErrors()) {
			return  "admin/usuarios_editar";
		}
		
		servicioUsuarios.editarUsuario(usuarioEditar);
		return obtenerUsuarios(model);
	}


}//end class
