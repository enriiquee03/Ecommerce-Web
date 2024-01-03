package springboot.Entrega17Servidor.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class Zapatilla {

	@Size(min = 3, max = 40, message = "marca debe tener entre 3 y 40 caracteres")
	@NotEmpty(message = "marca no puede estar vacio")
	@Pattern(regexp = "^[a-zA-Z0-9-\\s]+$", message = "La marca debe contener solo letras, números, y guiones.")
	private String marca;
	@Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "El modelo debe contener solo letras, números, espacios y guiones.")
	private String modelo;
	@NotNull(message = "Debes insertar un precio")
    @Digits (integer = 5, fraction = 2, message = "El precio debe tener como máximo 5 dígitos en la parte entera y 2 en la decimal.")
	@Min( value = 1, message = "el precio minimo es 1 euro" )
	@Max(value = 999 ,message =  "el precio maximo es 999")
	private Double precio;
    
	@NotNull(message = "Debes insertar una talla")
	@Digits(integer = 3, fraction = 2, message = "La talla debe tener como máximo 3 dígitos en la parte entera y 2 en la decimal.")
	@Min(value = 35, message = "La talla mínima permitida es 35")
	private Double talla;

	//@Pattern(regexp = "^[a-zA-Z,\\s]+$", message = "Los colores deben contener solo letras, espacios y comas.")
	private String colores;

	private boolean alta=true;

	@Lob //para tipos clob blob para almacenar un array de bytes
	@Column(name = "imagen_portada")
	private byte[] imagenPortada;
	
	@Lob //para tipos clob blob para almacenar un array de bytes
	@Column(name = "imagen_detalle_1")
	private byte[] imagenDetalle_1;
	@Lob //para tipos clob blob para almacenar un array de bytes
	@Column(name = "imagen_detalle_2")
	private byte[] imagenDetalle_2;
	@Lob //para tipos clob blob para almacenar un array de bytes
	@Column(name = "imagen_detalle_3")
	private byte[] imagenDetalle_3;
	@Lob //para tipos clob blob para almacenar un array de bytes
	@Column(name = "imagen_detalle_4")
	private byte[] imagenDetalle_4;
	@Lob //para tipos clob blob para almacenar un array de bytes
	@Column(name = "imagen_detalle_5")
	private byte[] imagenDetalle_5;

	@ManyToOne
	private Categoria categoria;


    @OneToMany(mappedBy = "zapatilla")
    private List<Valoracion> valoraciones;

	@Transient
	private int idCategoria;


	@Transient // con esto decimos a hibernate que no considere este campo
	private MultipartFile fotoSubida;
	
	@Transient 
	private MultipartFile fotoSubidaProductoDetalle_1;
	@Transient 
	private MultipartFile fotoSubidaProductoDetalle_2;
	@Transient 
	private MultipartFile fotoSubidaProductoDetalle_3;
	@Transient 
	private MultipartFile fotoSubidaProductoDetalle_4;
	@Transient 
	private MultipartFile fotoSubidaProductoDetalle_5;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;


	public Zapatilla(String marca, String modelo, Double precio, Double talla, String colores, int id) {
		this.marca = marca;
		this.modelo = modelo;
		this.precio = precio;
		this.talla = talla;
		this.colores = colores;
		this.id = id;
	}
	public Zapatilla(String marca, String modelo, Double precio, Double talla, String colores) {
		this.marca = marca;
		this.modelo = modelo;
		this.precio = precio;
		this.talla = talla;
		this.colores = colores;
	}





}
