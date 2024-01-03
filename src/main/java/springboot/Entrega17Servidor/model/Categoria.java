package springboot.Entrega17Servidor.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Categoria {
	
	@Size(min = 3, max = 50, message = "El nombre de la categoría debe tener entre 3 y 50 caracteres.")
    @NotEmpty(message = "El nombre de la categoría no puede estar vacío.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "El nombre de la categoría debe contener solo letras, números y espacios.")
    private String nombre;
	@NotEmpty(message = "La descripcion de la categoría no puede estar vacía.")
    @Size(max = 255, message = "La descripción de la categoría no puede tener más de 255 caracteres.")
    private String descripcion;



	@OneToMany(mappedBy = "categoria")
	private List <Zapatilla> zapatillas = new ArrayList<>();


	@Id
	@GeneratedValue
	private int id;
	
	@Lob 
	@Column(name = "imagen_categoria")
	private byte[] imagenCategoria;
	
	@Transient 
	private MultipartFile fotoSubida;
	
	
	public Categoria(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}







}
