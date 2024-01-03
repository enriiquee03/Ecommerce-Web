package springboot.Entrega17Servidor.utilidades.SetUp;

import java.io.IOException;
import java.net.URL;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.Entrega17Servidor.constantes.EstadosPedido;
import springboot.Entrega17Servidor.model.Categoria;
import springboot.Entrega17Servidor.model.Pedido;
import springboot.Entrega17Servidor.model.ProductoPedido;
import springboot.Entrega17Servidor.model.Usuario;
import springboot.Entrega17Servidor.model.Zapatilla;
import springboot.Entrega17Servidor.model.SetUp.SetUp;






@Service
@Transactional
public class ServicioSetUpImpl implements ServicioSetUp{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void prepararSetUp() {

		 SetUp setUp = null;

		 try {
			 setUp = (SetUp) entityManager.createQuery("select s from SetUp s").getSingleResult();
		} catch (NoResultException e) {
			System.out.println("No se encontro un registro de SetUp");
		}


		if( setUp == null || ! setUp.isCompletado() ) {
			//si no hay nada registrado en la tabla setup
			//o el unico elemento registrado no tiene completado a true
			//pues es cuando preparo una serie de registros para poder testear la tienda

			//preparar unas categorias para la tienda
			Categoria cNike = new Categoria("Nike" , "Nike: Elegancia deportiva para tu victoria.");
			cNike.setImagenCategoria(copiarImagenBase("http://localhost:8030/ui_images/categorias/nike.jpg"));
			entityManager.persist(cNike);
			Categoria cAdidas = new Categoria("Adidas" , "Adidas: Estilo deportivo, victoria asegurada.");
			cAdidas.setImagenCategoria(copiarImagenBase("http://localhost:8030/ui_images/categorias/adidas.jpg"));
			entityManager.persist(cAdidas);
			Categoria cGucci = new Categoria("Gucci" , "Gucci: Lujo que redefine tu estilo.\"");
			cGucci.setImagenCategoria(copiarImagenBase("http://localhost:8030/ui_images/categorias/gucci.jpg"));
			entityManager.persist(cGucci);
			Categoria cNewBalance = new Categoria("NewBalance" ,  "New Balance: Equilibrio perfecto, estilo auténtico.");
			cNewBalance.setImagenCategoria(copiarImagenBase("http://localhost:8030/ui_images/categorias/new_balance.jpg"));
			entityManager.persist(cNewBalance);

			//preparo unos libros para la tienda:
			//preparo unos Zapatillas para la tienda
			Zapatilla z = new Zapatilla ("Yezzy", "Foam", 115.15, Double.parseDouble("42"), "Roja y verde");
			z.setCategoria(cAdidas);
			z.setAlta(true);
			z.setImagenPortada(copiarImagenBase("http://localhost:8030/imagenes_base/1.jpg"));
			//entityManager.persist(z);


			z = new Zapatilla ("Nike", "Dunk Hight", 399.50, Double.parseDouble("43"), "Roja y verde");
			z.setCategoria(cGucci);
			z.setAlta(true);
			//entityManager.persist(z);
			z.setImagenPortada(copiarImagenBase("http://localhost:8030/imagenes_base/2.jpg"));

			z = new Zapatilla ("Nike", "Jordan", 255.15, Double.parseDouble("45"), "Roja y verde");
			z.setCategoria(cNewBalance);
			z.setAlta(true);
			//entityManager.persist(z);
			z.setImagenPortada(copiarImagenBase("http://localhost:8030/imagenes_base/3.jpg"));

			//preparamos una serie de usuarios para la tienda
			Usuario u = new Usuario("Enrique","enriquemadruga57@gmail.com","kike2003");
			u.setAvatar(copiarImagenBase("http://localhost:8030/imagenes_base_usuarios/usuario2.jpg"));
			entityManager.persist(u);

			 u = new Usuario("Juan","juan57@gmail.com","kike2003");
			 u.setAvatar(copiarImagenBase("http://localhost:8030/imagenes_base_usuarios/usuario2.jpg"));
			 entityManager.persist(u);

			u = new Usuario("Manuel","manuel57@gmail.com","kike2003");
			u.setAvatar(copiarImagenBase("http://localhost:8030/imagenes_base_usuarios/usuario2.jpg"));
			entityManager.persist(u);



			//dejamos preparados un par de pedidos para hacer pruebas
			Pedido p = new Pedido();
			p.setNombreCompleto("N. Completo");
			p.setDireccion("Info Direccion");
			p.setProvincia("Info Provincia");
			p.setTipoTarjeta("VISA");
			p.setNumeroTarjeta("123 456 789");
			p.setTitularTarjeta("Info Titular Tarjeta");
			p.setUsuario(u);
			p.setEstado(EstadosPedido.TERMINADO);
			entityManager.persist(p);
			ProductoPedido pp1 = new ProductoPedido();
			pp1.setPedido(p);
			pp1.setZapatilla(z);
			pp1.setCantidad(2);
			entityManager.persist(pp1);

			//segundo pedido:
			p = new Pedido();
			p.setNombreCompleto("N. Completo 2");
			p.setDireccion("Info Direccion 2");
			p.setProvincia("Info Provincia 2");
			p.setTipoTarjeta("VISA");
			p.setNumeroTarjeta("123 456 789");
			p.setTitularTarjeta("Info Titular Tarjeta 2");
			p.setUsuario(u);
			p.setEstado(EstadosPedido.TERMINADO);
			entityManager.persist(p);
			pp1 = new ProductoPedido();
			pp1.setPedido(p);
			pp1.setZapatilla(z);
			pp1.setCantidad(2);
			entityManager.persist(pp1);


			//vamos a registrar 100 zapatillas de prueba para tratar paginacion
			String marca = "New Balance";
			String modelo = "Foam";
			double precio = 1.00d;
			double talla = 45.5;
			String color = "amarillo";

			for (int i = 0; i < 15; i++) {
				Zapatilla zp = new Zapatilla (marca + i, modelo + i, precio + i / 100, talla, color + i);
				if(i <= 5) {
					zp.setCategoria(cNewBalance);	
				}else if(i > 5) {
					zp.setCategoria(cAdidas);
				}else if(i > 10 && i<=15) {
					zp.setCategoria(cGucci);
				}
				zp.setImagenPortada(copiarImagenBase("http://localhost:8030/imagenes_base/2.jpg"));
				zp.setImagenDetalle_1(copiarImagenBase("http://localhost:8030/ui_images/product-detail/product-d-1.jpg"));
				zp.setImagenDetalle_2(copiarImagenBase("http://localhost:8030/ui_images/product-detail/product-d-2.jpg"));
				zp.setImagenDetalle_3(copiarImagenBase("http://localhost:8030/ui_images/product-detail/product-d-3.jpg"));
				zp.setImagenDetalle_4(copiarImagenBase("http://localhost:8030/ui_images/product-detail/product-d-4.jpg"));
				zp.setImagenDetalle_5(copiarImagenBase("http://localhost:8030/ui_images/product-detail/product-d-5.jpg"));
				zp.setAlta(true);
				entityManager.persist(zp);
			}





			//una vez realizados los registros iniciales
			//vamos a marcar el setup como completado
			SetUp registroSetUp = new SetUp();
			registroSetUp.setCompletado(true);
			entityManager.persist(registroSetUp);

		}//end comprobacion setup
	}//end prepararSetUp

	private byte[] copiarImagenBase(String ruta_origen) {
		byte [] info = null;
		try {
			URL url = new URL(ruta_origen);
			info = IOUtils.toByteArray(url);
		} catch (IOException e) {
			System.out.println("no pude copiar imagen base");
			e.printStackTrace();
		}
		return info;
	}//end copiarImagenBase

}//end class
