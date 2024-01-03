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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Carrito {
	
	

	@OneToOne
	@JoinColumn(referencedColumnName = "id")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "carrito",cascade = CascadeType.ALL)
	private List<ProductoCarrito> productosCarrito = new ArrayList<ProductoCarrito>();
	
	
	@Id
	@GeneratedValue
	private int id;
}
