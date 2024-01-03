package springboot.Entrega17Servidor.constantesSQL;

public class ConstantesSQL {
	public final static String SQL_OBTENER_ZAPATILLAS_PARA_JSON=
			"SELECT z.id, z.marca, z.modelo , z.colores, z.talla, z.precio,z.categoria_id, c.nombre as nombre_categoria "
			+ "FROM `zapatilla` as z, categoria as c "
			+ "WHERE z.categoria_id = c.id and z.alta = 1 and z.marca like :marca  ORDER by z.id desc limit :comienzo, 10 ;"
			;
	public static final String SQL_OBTENER_CATEGORIAS_PARA_DESPLEGABLE =
			"SELECT `id`, `nombre` FROM `categoria` ORDER BY id ASC ;";
	public static final String SQL_OBTENER_PRODUCTOS_CARRITO =
			"SELECT z.id as zapatilla_id , z.marca, z.precio as precio_zapatilla, pc.cantidad "
			+ "FROM zapatilla as z, producto_carrito as pc "
			+ "WHERE pc.zapatilla_id = z.id and pc.carrito_id = :par_variable "
			+ "ORDER BY pc.id ASC ;";

	public static final String SQL_OBTENER_PRODUCTOS_DESEO =
			"SELECT z.id as zapatilla_id , z.marca, z.precio as precio_zapatilla, z.modelo, pd.cantidad "
			+ "FROM zapatilla as z, producto_deseo as pd "
			+ "WHERE pd.zapatilla_id = z.id and pd.deseo_id = :par_variable "
			+ "ORDER BY pd.id ASC ;";
	public static final String SQL_OBTENER_DETALLE_Zapatilla =
			"SELECT z.id, z.marca, z.modelo , z.colores, z.talla, z.precio, c.nombre as categoria "
					+ "FROM `zapatilla` as z, categoria as c "
					+ "WHERE z.categoria_id = c.id and z.id = :id"
					;

	public static final String SQL_BORRAR_PRODUCTOS_CARRITO =
			"DELETE FROM producto_carrito where carrito_id = :carrito_id";

	public static final String SQL_BORRAR_PRODUCTOS_DESEO =
			"DELETE FROM producto_deseo where deseo_id = :deseo_id";

	public static final String SQL_BORRAR_PRODUCTO_CARRITO =
			"DELETE FROM producto_carrito where carrito_id = :carrito_id AND zapatilla_id = :zapatilla_id";

	public static final String SQL_BORRAR_PRODUCTO_DESEO =
			"DELETE FROM producto_deseo where deseo_id = :deseo_id AND zapatilla_id = :zapatilla_id";

	public static final String SQL_OBTENER_PRODUCTO_COMPRADO_POR_USUARIO =
			"SELECT p.id FROM  pedido p ,producto_pedido pp WHERE pp.pedido_id=p.id and p.usuario_id = :usuario_id";

	public static final String SQL_OBTENER_TOTAL_ZAPATILLAS =
			"SELECT COUNT(id) FROM zapatilla WHERE alta = 1 and  marca like :marca ;";
	public static final String SQL_OBTENER_TOTAL_VALORACIONES =
			"SELECT COUNT(id) FROM valoracion WHERE  zapatilla_id = :idProducto ;";
	public static final String SQL_OBTENER_TOTAL_CARRITO_USUARIO =
			"SELECT COUNT(pc.id) FROM carrito as c,producto_carrito as pc where pc.carrito_id = c.id and c.usuario_id = :id_usuario  ;";
	public static final String SQL_OBTENER_TOTAL_DESEO_USUARIO =
			"SELECT COUNT(pd.id) FROM deseo as d,producto_deseo as pd where pd.deseo_id = d.id and d.usuario_id = :id_usuario   ;";

	
	public static final String SQL_VALORACIONES_DE_ZAPATILLA =
			"SELECT v.id, v.valoracion, v.email , v.nombre,v.texto "
					+ "FROM `valoracion` as v "
					+ "WHERE v.zapatilla_id = :id_producto ;"
					;
	
	
	public static final String SQL_OBTENER_PEDIDOS_USUARIO =
			"SELECT  p.* FROM `pedido` p WHERE p.usuario_id = :id_usuario ;" ;

}
