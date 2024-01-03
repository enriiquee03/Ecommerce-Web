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

import springboot.Entrega17Servidor.model.Zapatilla;
import springboot.Entrega17Servidor.servicios.ServicioCategorias;
import springboot.Entrega17Servidor.servicios.ServicioZapatillas;



@Controller
@RequestMapping("admin/")
public class ZapatillasController {

	@Autowired //usando esto, con la interfaz es lo mismo que pedir ServicioZapatillasImpl
	private ServicioZapatillas servicioZapatillas;

	@Autowired
	private ServicioCategorias servicioCategorias ;

	@RequestMapping("obtenerZapatillas")
	public String obtenerZapatillas(@RequestParam(name= "marca",defaultValue = "")  String marca ,@RequestParam(name = "comienzo", defaultValue = "0")  Integer comienzo, Model model) {
		model.addAttribute("zapatillas", servicioZapatillas.obtenerZapatillasPorMarcaYcomienzoFin(marca, comienzo, 10));
		model.addAttribute("marca",marca);
		model.addAttribute("siguiente",comienzo+10);
		model.addAttribute("anterior",comienzo-10);
		model.addAttribute("total", servicioZapatillas.obtenerTotalZapatillas(marca));
		return "admin/zapatillas";
	}

	@RequestMapping("registrarZapatillas")
	public String registrarZapatillas(Model model) {
		Zapatilla z = new Zapatilla();
		z.setPrecio(Double.valueOf("1"));
		model.addAttribute("nuevaZapatilla",z);
		//formar las categorias para un desplegable
		model.addAttribute("categorias", servicioCategorias.obtenerCategoria());


		return "admin/zapatillas_registro";

	}

	@RequestMapping("guardarZapatilla")
	public String guardarZapatilla(@ModelAttribute("nuevaZapatilla") @Valid  Zapatilla nuevaZapatilla,BindingResult resultadoValidaciones, Model model, HttpServletRequest request ) {
		if(resultadoValidaciones.hasErrors()) {
			model.addAttribute("categorias", servicioCategorias.obtenerCategoria());
			return  "admin/zapatillas_registro";
		}

		servicioZapatillas.registrarZapatilla(nuevaZapatilla);
		return "admin/zapatillas_registro_ok";
	}

	@RequestMapping("borrarZapatilla")
	public String borrarZapatilla(@RequestParam("id") Integer id, Model model) {
		servicioZapatillas.borrarZapatillas(id);
		return obtenerZapatillas("",0,model);
	}

	@RequestMapping("editarZapatilla")
	public String editarZapatilla(@RequestParam("id") Integer id, Model model) {
		Zapatilla zapatilla = servicioZapatillas.obtenerZapatillaPorId(id);
		zapatilla.setIdCategoria(zapatilla.getCategoria().getId());
		model.addAttribute("zapatillaEditar", zapatilla);
		model.addAttribute("categorias", servicioCategorias.obtenerCategoria());
		return "admin/zapatillas_editar";
	}

	@RequestMapping("guardarCambiosZapatilla")
	public String guardarCambiosZapatilla(@ModelAttribute("zapatillaEditar") @Valid Zapatilla zapatillaEditar ,BindingResult resultadoValidaciones , Model model, HttpServletRequest request) {
		
		if(resultadoValidaciones.hasErrors()) {
			model.addAttribute("categorias", servicioCategorias.obtenerCategoria());
			return  "admin/zapatillas_editar";
		}
		
		servicioZapatillas.editarZapatilla(zapatillaEditar);
		return obtenerZapatillas("",0,model);
	}


}//end class
