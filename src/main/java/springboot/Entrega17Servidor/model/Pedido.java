package springboot.Entrega17Servidor.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Pedido {

	@OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ProductoPedido> productosPedido = new ArrayList <>();

	private String estado;

	//se pide un paso 1:
	private String nombreCompleto;
	private String direccion;
	private String provincia;
	private String codigo_postal;
	private String email;

	//fin paso 1

	//paso 2:
	private String titularTarjeta;
	private String numeroTarjeta;
	private String tipoTarjeta;
	private String cvv;
	private String fecha_caducidad;
	//fin paso dos


	//paso 3:
	private String notas_envio;
	private String persona_contacto;
	private String telefono_contacto;
	//fin paso dos

	@ManyToOne(targetEntity = Usuario.class, optional = false,cascade = CascadeType.ALL)
	private Usuario usuario;



	@Id
	@GeneratedValue
	private int id;



	


}
