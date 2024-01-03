
//usando jquery pedimos el contenido de inicio.html y se lo metemos 
//a la variable plantillaInicio
$.get("plantillas_mustache/inicio.html",function(data){
	plantillaInicio = data;
});
$.get("plantillas_mustache/zapatillas.html",function(data){
	plantillaZapatillas = data;
	setTimeout(mostrar_zapatillas,500)
});
$.get("plantillas_mustache/registro.html",function(data){
	plantillaRegistro = data;
});
$.get("plantillas_mustache/identificar_usuario.html",function(data){
	plantillaIdentificarUsuario = data;
});
$.get("plantillas_mustache/carrito.html",function(data){
	plantillaCarrito = data;
});
$.get("plantillas_mustache/deseo.html",function(data){
	plantillaDeseo = data;
});
$.get("plantillas_mustache/datalles_zapatillas.html",function(data){
	plantillaDetallesZapatilla = data;
});
$.get("plantillas_mustache/check_out_1.html",function(data){
	plantillaCheckout_1 = data;
});
$.get("plantillas_mustache/check_out_2.html",function(data){
	plantillaCheckout_2 = data;
});
$.get("plantillas_mustache/check_out_3.html",function(data){
	plantillaCheckout_3 = data;
});
$.get("plantillas_mustache/check_out_4.html",function(data){
	plantillaCheckout_4 = data;
});
$.get("plantillas_mustache/usuario_pedidos.html",function(data){
	plantillaUsuarioPedidos = data;
});
