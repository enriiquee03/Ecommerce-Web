package springboot.Entrega17Servidor.model;

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
public class ProductoDeseo {

	@OneToOne
	@JoinColumn(referencedColumnName = "id")
	private Zapatilla zapatilla;


	@ManyToOne
	@JoinColumn(name = "deseo_id")
	private Deseo deseo;

	@Id
	@GeneratedValue
	private int id;


	private int cantidad;







}
