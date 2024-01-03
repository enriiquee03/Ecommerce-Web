package springboot.Entrega17Servidor.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Usuario {
	
	@Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres.")
	@Pattern(regexp = "^[a-zA-Z\\s-]+$", message = "El nombre debe contener solo letras, espacios y guiones.")
    @NotEmpty(message = "El nombre no puede estar vacío.")
	private String nombre;
	
	@NotEmpty(message = "El email no puede estar vacio")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El formato del correo electrónico no es válido.")
    @NotEmpty(message = "El correo electrónico no puede estar vacío.")
	private String email;
	
	@Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    @NotEmpty(message = "La contraseña no puede estar vacía.")
	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+$", message = "La contraseña contiene caracteres no permitidos.")
	private String pass;

	@Lob
	@Column(name = "avatar")
	private byte[] avatar;

	@OneToOne(cascade = CascadeType.ALL)
	private Carrito carrito;

	@OneToOne(cascade = CascadeType.ALL)
	private Deseo deseo;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Pedido> pedidos;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Valoracion> valoraciones;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Transient // con esto decimos a hibernate que no considere este campo
	private MultipartFile fotoSubida;


	public Usuario() {
		super();
	}

	public Usuario(String nombre, String email, String pass, int id) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.pass = pass;
		this.id = id;
	}

	public Usuario(String nombre, String email, String pass) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.pass = pass;
	}






}
