package springboot.Entrega17Servidor.webservices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.Entrega17Servidor.model.Valoracion;
import springboot.Entrega17Servidor.servicios.ServicioCategorias;
import springboot.Entrega17Servidor.servicios.ServicioValoracion;
import springboot.Entrega17Servidor.servicios.ServicioZapatillas;


@Controller
@RestController
@RequestMapping("servicioWebZapatillas/")
public class ServicioWEBZapatillas {

	//como pedir una bean de spring cuyo nombre se usa en
		//varios paquetes p. ej:
		//webservicies.ServicioWebPedidos
		//webservices.adminServiciosWebPedidos
		//tenemos que usar @Qualifier

//		@Autowired
//		@Qualifier("servicioWebPedidosAdmin") // esto cogeria la versioh del paquete admin
//		private ServicioPedidos servicioPedidos;

	@Autowired
	private ServicioZapatillas servicioZapatillas;
	@Autowired
	private ServicioValoracion servicioValoracion;
	@Autowired
	private ServicioCategorias servicioCategorias;

	@RequestMapping("obtenerZapatillas")
	public ResponseEntity<Map<String, Object>>obtenerZapatillas(
			@RequestParam(name = "marca", defaultValue = "") String marca,
			@RequestParam(name = "comienzo", defaultValue = "0") Integer comienzo, 
			Model model
			){
		Map<String, Object> respuesta = new HashMap();



	    respuesta.put("zapatillas", servicioZapatillas.obtenerZapatillasParaFormarJson(marca, comienzo));
	    respuesta.put("categorias", servicioCategorias.obtenerCategoriasParaDesplegable());
	    respuesta.put("totalZapatillas", servicioZapatillas.obtenerTotalZapatillas(marca));
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	@RequestMapping("obtenerZapatillaDetalles")
	public ResponseEntity<Map<String, Object>> obtenerZapatillaDetalles(@RequestParam("id") Integer id) {
	    Map<String, Object> respuesta = new HashMap();



	    respuesta.put("detallesZapatilla", servicioZapatillas.obtenerDetallesZapatilla(id));
	    respuesta.put("valoraciones", servicioValoracion.obtenerValoracionesDeZapatilla(id));
	    respuesta.put("totalValoracion", servicioValoracion.obtenerTotalValoracionDeZapatilla(id));
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

}
