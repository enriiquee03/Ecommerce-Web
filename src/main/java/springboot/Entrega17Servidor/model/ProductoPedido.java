package springboot.Entrega17Servidor.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class ProductoPedido {

	@ManyToOne()
	@JoinColumn(name="pedido_id")
	private Pedido pedido;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "zapatilla_id")
	private Zapatilla zapatilla;

	private int cantidad;

	@Id
	@GeneratedValue
	private int id;

	


}
