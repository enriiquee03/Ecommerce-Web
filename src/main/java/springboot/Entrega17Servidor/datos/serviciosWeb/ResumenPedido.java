package springboot.Entrega17Servidor.datos.serviciosWeb;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.Entrega17Servidor.model.ProductoPedido;
import springboot.Entrega17Servidor.model.Usuario;

//esta es la informacion que recibira el usuario para confirmar el pedido
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResumenPedido {

	//productos que hay en el carrito
	private List<Map<String,Object>> zapatillas;

	//datos del paso 1
	private String nombreCompleto;
	private String direccion;
	private String provincia;
	private String codigo_postal;
	private String email;

	//datos del paso2
	private String titularTarjeta;
	private String numeroTarjeta;
	private String tipoTarjeta;
	private String cvv;
	private String fecha_caducidad;
	
	//paso 3:
	private String notas_envio;
	private String persona_contacto;
	private String telefono_contacto;




}
