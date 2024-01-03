package springboot.Entrega17Servidor.servicioJPAImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import springboot.Entrega17Servidor.constantesSQL.ConstantesSQL;
import springboot.Entrega17Servidor.model.Categoria;
import springboot.Entrega17Servidor.model.Zapatilla;
import springboot.Entrega17Servidor.servicios.ServicioZapatillas;



@Service
@Transactional
public class ServicioZapatillasJPAImpl implements ServicioZapatillas {

	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public void registrarZapatilla(Zapatilla z) {
		Categoria c = entityManager.find(Categoria.class, z.getIdCategoria());
		z.setCategoria(c);
		try {
			z.setImagenPortada(z.getFotoSubida().getBytes());
			z.setImagenDetalle_1(z.getFotoSubidaProductoDetalle_1().getBytes());
			z.setImagenDetalle_2(z.getFotoSubidaProductoDetalle_2().getBytes());
			z.setImagenDetalle_3(z.getFotoSubidaProductoDetalle_3().getBytes());
			z.setImagenDetalle_4(z.getFotoSubidaProductoDetalle_4().getBytes());
			z.setImagenDetalle_5(z.getFotoSubidaProductoDetalle_5().getBytes());
		} catch (IOException e) {
			System.out.println("no pude procesar la subida de las fotos");
			e.printStackTrace();
		}
		entityManager.persist(z);

	}

	@Override
	public List<Zapatilla> obtenerZapatillas() {
		//JPQL -> lenguaje de consultas de jpa, es muy similar a sql
				//ventaja: devuelve objetos
		return entityManager.createQuery("SELECT z from Zapatilla z where z.alta = true order by z.id desc").getResultList();
	}

	@Override
	public void borrarZapatillas(int id) {
		//ya no borramos producto sino que lo damos de baja
		//entityManager.remove(entityManager.find(Zapatilla.class, id));
		Zapatilla z = entityManager.find(Zapatilla.class, id);
		z.setAlta(false);
		System.out.println(z.toString());
		entityManager.merge(z);

	}

	@Override
	public Zapatilla obtenerZapatillaPorId(int id) {
		return entityManager.find(Zapatilla.class, id);
	}

	@Override
	public void editarZapatilla(Zapatilla z) {
		Categoria c = entityManager.find(Categoria.class, z.getIdCategoria());
		z.setCategoria(c);
		z.setAlta(true);
		Zapatilla zapatillaAnterior = entityManager.find(Zapatilla.class, z.getId());

		for (int i = 0; i <= 5; i++) {
		    MultipartFile fotoSubida = getFotoSubidaPorIndice(z, i);
		    byte[] imagenDetalle = getImagenDetallePorIndice(zapatillaAnterior, i);

		    if (fotoSubida.getSize() == 0) {
		        System.out.println("No se subió una nueva foto, mantenemos la anterior");
		        setImagenDetallePorIndice(z, imagenDetalle, i);
		    } else {
		        System.out.println("Asignar la nueva foto al registro");
		        try {
		            setImagenDetallePorIndice(z, fotoSubida.getBytes(), i);
		        } catch (IOException e) {
		            System.out.println("No pude procesar la foto subida");
		            e.printStackTrace();
		        }
		    }
		}
		entityManager.merge(z);

	}

	@Override
	public List<Map<String, Object>> obtenerZapatillasParaFormarJson(String marca, int comienzo) {

		Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_ZAPATILLAS_PARA_JSON);

		NativeQueryImpl nativeQueryImpl = (NativeQueryImpl) query;

		nativeQueryImpl.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		 
		nativeQueryImpl.setParameter("marca", "%" + marca + "%");
		nativeQueryImpl.setParameter("comienzo",comienzo);
		return nativeQueryImpl.getResultList();
	}

	@Override
	public Map<String, Object> obtenerDetallesZapatilla(int idZapatilla) {
		Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_DETALLE_Zapatilla).setParameter("id", idZapatilla);

		NativeQueryImpl nativeQueryImpl = (NativeQueryImpl) query;


		nativeQueryImpl.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);



		return (Map<String, Object>)nativeQueryImpl.getResultList().get(0);
	}

	@Override
	public List<Zapatilla> obtenerZapatillasPorMarca(String marca) {
		return entityManager.createQuery("SELECT z from Zapatilla z where z.alta = true and z.marca like :marca order by z.id desc").setParameter("marca", "%" + marca + "%").getResultList();
	}

	@Override
	public List<Zapatilla> obtenerZapatillasPorMarcaYcomienzoFin(String marca, int comienzo, int resultadoPorPagina) {
		return entityManager.createQuery("SELECT z from Zapatilla z where z.alta = true and z.marca like :marca  order by z.id desc").setParameter("marca", "%" + marca + "%").setFirstResult(comienzo).setMaxResults(resultadoPorPagina).getResultList();
	}

	@Override
	public int obtenerTotalZapatillas(String marca) {
		Query q = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_TOTAL_ZAPATILLAS);
		q.setParameter("marca", "%" + marca + "%");
		int totalZapatillas =  Integer.parseInt(q.getSingleResult().toString()) ;
		return totalZapatillas;
	}
	
	
	
	//METODO DE UTILIDAD PARA OBTENER LOS CAMBIOS DE LA ZAPATILLA EN ADMINISTRACION
	private MultipartFile getFotoSubidaPorIndice(Zapatilla z, int indice) {
	    switch (indice) {
	        case 0: return z.getFotoSubida();
	        case 1: return z.getFotoSubidaProductoDetalle_1();
	        case 2: return z.getFotoSubidaProductoDetalle_2();
	        case 3: return z.getFotoSubidaProductoDetalle_3();
	        case 4: return z.getFotoSubidaProductoDetalle_4();
	        case 5: return z.getFotoSubidaProductoDetalle_5();
	        default: throw new IllegalArgumentException("Índice de foto no válido");
	    }
	}

	private byte[] getImagenDetallePorIndice(Zapatilla z, int indice) {
	    switch (indice) {
	        case 0: return z.getImagenPortada();
	        case 1: return z.getImagenDetalle_1();
	        case 2: return z.getImagenDetalle_2();
	        case 3: return z.getImagenDetalle_3();
	        case 4: return z.getImagenDetalle_4();
	        case 5: return z.getImagenDetalle_5();
	        default: throw new IllegalArgumentException("Índice de imagen no válido");
	    }
	}

	private void setImagenDetallePorIndice(Zapatilla z, byte[] imagenDetalle, int indice) {
	    switch (indice) {
	        case 0: z.setImagenPortada(imagenDetalle); break;
	        case 1: z.setImagenDetalle_1(imagenDetalle); break;
	        case 2: z.setImagenDetalle_2(imagenDetalle); break;
	        case 3: z.setImagenDetalle_3(imagenDetalle); break;
	        case 4: z.setImagenDetalle_4(imagenDetalle); break;
	        case 5: z.setImagenDetalle_5(imagenDetalle); break;
	        default: throw new IllegalArgumentException("Índice de imagen no válido");
	    }
	}



}
