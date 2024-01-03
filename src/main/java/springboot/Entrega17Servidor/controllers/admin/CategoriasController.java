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

import springboot.Entrega17Servidor.model.Categoria;
import springboot.Entrega17Servidor.servicios.ServicioCategorias;



@Controller
@RequestMapping("admin/")
public class CategoriasController {

	@Autowired
	private ServicioCategorias servicioCategorias;


	@RequestMapping("obtenerCategorias")
	public String obtenerCategorias(Model model) {
		model.addAttribute("categorias", servicioCategorias.obtenerCategoria());
		return "admin/categorias";
	}

	@RequestMapping("registrarCategorias")
	public String registrarCategorias(Model model) {
		Categoria c = new Categoria ();
		model.addAttribute("nuevaCategoria",c);
		//formar las categorias para un desplegable


		return "admin/categorias_registro";

	}

	@RequestMapping("guardarCategoria")
	public String guardarCategoria(@ModelAttribute("nuevaCategoria") @Valid Categoria nuevaCategoria, BindingResult resultadoValidaciones, Model model, HttpServletRequest request) {
		if(resultadoValidaciones.hasErrors()) {
			return  "admin/categorias_registro";
		}
		
		servicioCategorias.registrarCategorias(nuevaCategoria);
		return "admin/categorias_registro_ok";
	}

	@RequestMapping("borrarCategoria")
	public String borrarCategoria(@RequestParam("id") Integer id, Model model) {
		servicioCategorias.borrarCategorias(id);
		return obtenerCategorias(model);
	}

	@RequestMapping("editarCategoria")
	public String editarCategoria(@RequestParam("id") Integer id, Model model) {
		Categoria categoria = servicioCategorias.obtenerCategoriaPorId(id);
		model.addAttribute("categoriaEditar", categoria);
		return "admin/categorias_editar";
	}

	@RequestMapping("guardarCambiosCategoria")
	public String guardarCambiosCategoria(@ModelAttribute("categoriaEditar") @Valid  Categoria categoriaEditar , BindingResult resultadoValidaciones, Model model, HttpServletRequest request) {
		
		if(resultadoValidaciones.hasErrors()) {
			return  "admin/categorias_editar";
		}
		
		servicioCategorias.editarCategoria(categoriaEditar);
		return obtenerCategorias(model);
	}


}//end class
