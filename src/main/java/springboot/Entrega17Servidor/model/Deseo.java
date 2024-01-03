package springboot.Entrega17Servidor.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
public class Deseo {

	@OneToOne
	@JoinColumn(referencedColumnName = "id")
	private Usuario usuario;

	@OneToMany(mappedBy = "deseo",cascade = CascadeType.ALL)
	private List<ProductoDeseo> productosDeseo = new ArrayList<>();

	@Id
	@GeneratedValue
	private int id;





}
