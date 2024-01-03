package springboot.Entrega17Servidor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Valoracion {

	private String nombre;

	private String email;
	
	private String texto;

	private Integer Valoracion;


	@ManyToOne
	@JoinColumn(name = "zapatilla_id")
	private Zapatilla zapatilla;

	@ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;



	@Id
	@GeneratedValue
	private int id;



	






}
