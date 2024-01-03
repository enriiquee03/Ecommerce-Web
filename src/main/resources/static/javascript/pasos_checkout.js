//funciones de los pasos para el check out
function checkout_paso_0(){
	//mostrar al usuario un formulario donde insertar la informacion de envio
	$("#contenedor").html(plantillaCheckout_1);
	$("#aceptar_paso_1").click(checkout_paso_1_aceptar); 	
}//end checkout_paso_0

function checkout_paso_1_aceptar(){
	//recoger los valores introducidos y mandarlos al servidor
	var nombre = $("#campo_nombre").val();
	var direccion = $("#campo_direccion").val();
	var provincia = $("#campo_provincia").val();
	var codigo_postal = $("#campo_codigo_postal").val();
	var email = $("#campo_email").val();
	var tipo_tarjeta = $("#tipo_tarjeta").find(":selected").val();
	var numero_tarjeta = $("#numero_tarjeta").val();
	var titular_tarjeta = $("#titular_tarjeta").val();
	var cvv = $("#cvv").val();
	var fecha_caducidad = $("#fecha_caducidad").val();
	var notas_envio = $("#campo_notas").val();
	var persona_contacto = $("#campo_pcontacto").val();
	var telefono_contacto = $("#campo_tcontacto").val();
	

	
	//mandar los valores al servicio web de pedidos
	$.post("servicioWebPedidos/paso1",
			{
				nombre: nombre,
				direccion: direccion,
				provincia: provincia,
				email : email,
				codigo_postal: codigo_postal
				
				tarjeta: tipo_tarjeta,
				numero: numero_tarjeta,
				titular: titular_tarjeta,
				cvv : cvv,
				fecha_caducidad: fecha_caducidad
				
				notas_envio: notas_envio,
				persona_contacto: persona_contacto,
				telefono_contacto: telefono_contacto
				
			}
	).done(function(res){
		//al no hacer $.getJson debo parsear lo recibido
		let resumen_pedido = res
		var html = Mustache.render(plantillaCheckout_3, resumen_pedido)
		$("#contenedor").html(html);
		$("#boton_confirmar_pedido").click(function(){
			$.ajax("servicioWebPedidos/paso3", {
				success : function(res){
					alert("respuesta del servicio web" + res)
					mostrar_zapatillas()
				}
			})
		});
	});//end done	
	
}//end checkout_paso_1_aceptar





