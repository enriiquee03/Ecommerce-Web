let marca_a_buscar = "";
let comienzo_resultados = 0;
 $('#contenedor').css('display', 'none');
function mostrar_zapatillas(){

	$.getJSON("servicioWebZapatillas/obtenerZapatillas",{marca : marca_a_buscar , comienzo: comienzo_resultados }).done (function(res){
		$('#contenedor').css('display', 'block');
		console.log("Datos recibidos:", JSON.stringify(res, null, 2));
		let texto_html = "";

		texto_html = Mustache.render(plantillaZapatillas, res);
		
		
		let zapatillas = res.zapatillas;

		for(i in zapatillas){
			zapatillas[i].fecha_hora_actual = new Date();
			zapatillas[i].precio = zapatillas[i].precio.toString().replace(".", ",")
		}


		

		$("#contenedor").html(texto_html);	
		$("#marca_buscador").val(marca_a_buscar)
		$("#marca_buscador").focus()
		//indicar que hace el buscador 
			$("#image-search").click(function (e) {
			    marca_a_buscar = $("#marca_buscador").val();
			    console.log("Searching for:", marca_a_buscar);
			    comienzo_resultados = 0;
			    mostrar_zapatillas();
			});
			
			
			if(comienzo_resultados + 10<= 10){
				$("#enlace_anterior").hide();
			}else{
				$("#enlace_anterior").show();
			}

			let totalZapatillas = res.totalZapatillas;
			
			$("#total_resultados").html(totalZapatillas);

			if((comienzo_resultados + 10 ) < totalZapatillas){
				$("#enlace_siguiente").show();
			}else{
				$("#enlace_siguiente").hide();
			}


			
			$("#enlace_anterior").click(function (e){
				comienzo_resultados -= 10
				mostrar_zapatillas();
			})
			$("#enlace_siguiente").click(function (e){
				comienzo_resultados += 10
				mostrar_zapatillas();
			})

			


		$(".enlace_comprar_listado_principal").click(function(res){
			if ( nombre_login != "" ){
				var id_producto = $(this).attr("id-producto");
				$.post("servicioWebCarrito/agregarZapatilla",
						{
							id: id_producto,
							cantidad: 1
						}
				).done(function(res){
					Swal.fire({
		                    icon: "success",
		                    title: "Hecho!",
		                    text: "El producto fue agregado correctamente al carrito.",
		                    showConfirmButton: false,
		                    timer: 2000,
		                });	
					var totalCarrito = $("#carrito .count-tag").text().slice(2);
					
					totalCarrito = (parseInt(totalCarrito) + 1).toString();
					
					totalCarrito = (totalCarrito < 10) ? "0" + totalCarrito : totalCarrito;
					$("#carrito .count-tag").text(totalCarrito);	
				});
			}else{
				Swal.fire({
		                    icon: "error",
		                    title: "Error",
		                    text: "Debes identificarte para agregar producto al carrito.",
		                    showConfirmButton: false,
		                    timer: 2000,
		                });	
			}
		});
		
		$(".enlace_desear_listado_principal").click(function(res){
			if ( nombre_login != "" ){
				var id_producto = $(this).attr("id-producto");
				$.post("servicioWebDeseo/agregarZapatilla",
						{
							id: id_producto,
							cantidad: 1
						}
				).done(function(res){
					Swal.fire({
		                    icon: "success",
		                    title: "Hecho!",
		                    text: "El producto fue agregado correctamente a la lista de deseo.",
		                    showConfirmButton: false,
		                    timer: 2000,
		                });	
					var totalDeseo = $("#deseo .count-tag").text();
					
					totalDeseo = (parseInt(totalDeseo) + 1).toString();
					
					totalDeseo = (totalDeseo < 10) ? "0" + totalDeseo : totalDeseo;
					$("#deseo .count-tag").text(totalDeseo);					
				});
			}else{
				Swal.fire({
		                    icon: "error",
		                    title: "Error",
		                    text: "Debes identificarte para poder desear productos.",
		                    showConfirmButton: false,
		                    timer: 2000,
		                });	
			}
		});
		//indicar que hace el enlace ver detalles zapatillas:
$(".enlace_ver_detalles_zapatilla").click(function () {
    var id_producto = $(this).attr("id-producto");
    $.getJSON("servicioWebZapatillas/obtenerZapatillaDetalles", {
        id: id_producto
    }).done(function (res) {
        console.log("Datos recibidos:", JSON.stringify(res, null, 2));

        // Transformar la propiedad "valoracion" en un array para mostrar las estrellas
		        res.valoraciones.forEach(function (valoracion) {
		            valoracion.valoracion = Array.from({ length: valoracion.valoracion }, (_, index) => index + 1);
		        });
		
		        var html = Mustache.render(plantillaDetallesZapatilla, res);
		        $("#contenedor").html(html);
		
		        var valoracionSeleccionada = undefined;
		
		        $(".crear_review").click(function () {
		            var nombre = $("#campo_nombre").val();
		            var id_producto = $(this).attr("id-producto");
		            var email = $("#campo_email").val();
		            var texto = $("#campo_texto").val();
		            var valoracionSeleccionada = $('input[name="rate"]:checked').val();
		            
		            if (
				    !validarNombre($("#campo_nombre").val()) ||
				    !validarEmail($("#campo_email").val()) ||
				    !validarValoracion($("#campo_texto").val())
					) {
					    return;
					}
							
					$.post("servicioWebValoracion/crearValoracion", {
					    id: id_producto,
					    nombre: nombre,
					    email: email,
					    texto: texto,
					    valoracion: valoracionSeleccionada
					})
					.fail(function(){
					    Swal.fire({
					        icon: "error",
					        title: "Error",
					        text: "No estas identificado o no has comprado este producto",
					    });
					})
					.done(function (res) {
					    console.log(res);
					    alert(res);
					    Swal.fire({
					        icon: "success",
					        title: "Hecho!",
					        text: "Valoracion creada con éxito.",
					        showConfirmButton: false,
					        timer: 2000,
					    });
					});

		
		        })
		    })
		});
		
	});
}

//atencion a los enlaces de menu
$("#zapatillas").click(function(){
	$('#contenedor').css('display', 'block');
	$("#inicio-content").hide();
	mostrar_zapatillas();
	});//end zapatillas


$("#registrarme").click(function(){
	$('#contenedor').css('display', 'block');
	$("#inicio-content").hide();
	$("#contenedor").html(plantillaRegistro);
	$("#form_registro_usuario").submit(
		(e)=>{
			//valirdar form
			if(!validarNombre($("#nombre").val()) || !validarEmail($("#email").val()) || !validarPass($("#pass").val()) ){
				e.preventDefault();
				return;
			}
			let formulario = document.forms[0];
			let formData = new FormData(formulario);
			$.ajax("servicioWebUsuarios/registrarUsuario",{
				type : "POST",
				data: formData,
				cache : false,
				contentType : false,
				processData :false,
				success: (res) => {
					alert(res)
				}
			})//end ajax


			e.preventDefault();// evitamos envio de form ya que todo el cliente lo gestionamos con js	
		}
	);
	
	//ver si el usuario guardo su id y pass en una cookie y
	// en caso positivo ponerlas por defecto en los inputs correspondientes
	if(typeof(Cookies.get("email")) != undefined){
		$("#email_login").val(Cookies.get("email"));
	}
	if(typeof(Cookies.get("pass")) != undefined){
		$("#pass_login").val(Cookies.get("pass"));
	}

	$("#form_login").submit(function(e) {
		if( !validarEmail($("#email_login").val()) || !validarPass($("#pass_login").val()) ){
				e.preventDefault();
				return;
			}
		$.post("servicioWebUsuarios/identificarUsuario",
		    { 
		        email: $("#email_login").val(),
		        pass: $("#pass_login").val()
		    }
		).done(function(res){
		    alert(res.mensaje);
		    console.log(res)
		    if(res.status == "ok" ){
		        nombre_login = res.mensaje;
		        $("#mensaje_login").html("identificado como: " + nombre_login);
		        alert("identificado correctamente, ya puedes comprar productos");
		
		        if($("#recordar_datos").prop('checked')){
		            Cookies.set("email",$("#email_login").val(), {expires: 100});
		            Cookies.set("pass",$("#pass_login").val(), {expires: 100});
		        }
		
		        var totalCarrito = res.total_carrito;
		        var totalDeseo = res.total_deseo;
		        
		        totalCarrito = (totalCarrito < 10) ? "0" + totalCarrito : totalCarrito;
		        totalDeseo = (totalDeseo < 10) ? "0" + totalDeseo : totalDeseo;
		        
		        $("#carrito .count-tag").text(totalCarrito);
		        $("#deseo .count-tag").text(totalDeseo);
		    } else {
		        alert(res.status);
		    }
		});	
		e.preventDefault();
	});//end submit
	
});//end registro

		
		

$("#carrito").click(() =>{
	$('#contenedor').css('display', 'block');
	if(nombre_login != ""){
		$.getJSON("servicioWebCarrito/obtenerProductosCarrito" , (res)=>{
			if(res.length == 0){
				alert("aun no has agregado ningun producto");
			}else{
				$("#inicio-content").hide();
				var html = Mustache.render(plantillaCarrito, res);
				$("#contenedor").html(html); 
				$("#realizar_pedido").click(checkout_paso_0);
				$(".enlace_borrar_producto_carrito").click(function(e){
					let id_libro = $(this).attr("id-zapatilla")

					$.post("servicioWebCarrito/borrarProducto",
						{
							id: id_libro
						}
					).done(function(res){
						if(res == "ok"){
							$("#div-producto-"+id_libro).hide()
						}else{
							Swal.fire({
			                    icon: "error",
			                    title: "Error",
			                    text: "Error al borrar el producto",
			                });
						}
					})

					e.preventDefault();
				})
			}
		}).fail(function(){
			Swal.fire({
                    icon: "error",
                    title: "Error al acceder al carrito",
                    text: "Debes volver a indentificarte o agregar al menos 1 producto",
                });
		});// end getJson 
		
	
	}else{
		Swal.fire({
                    icon: "error",
                    title: "Error al acceder al carrito",
                    text: "Debes estar identificado para poder ver tus productos del carrito",
                });
	}
	
});//end carrito

$("#deseo").click(() =>{
	$('#contenedor').css('display', 'block');
	if(nombre_login != ""){
		$.getJSON("servicioWebDeseo/obtenerProductosDeseo" , (res)=>{
			if(res.length == 0){
				Swal.fire({
                    icon: "error",
                    title: "Error al acceder a la lista de deseos",
                    text: "Todavia no has añadido ningun producto a la lista",
                });
			}else{
				$("#inicio-content").hide();
				var html = Mustache.render(plantillaDeseo, res);
				$("#contenedor").html(html); 
				$(".enlace_borrar_producto_deseo").click(function(e){
					let id_zapatilla = $(this).attr("id-zapatilla")

					$.post("servicioWebDeseo/borrarProducto",
						{
							id: id_zapatilla
						}
					).done(function(res){
						if(res == "ok"){
							$("#div-producto-"+id_zapatilla).hide()
							var totalDeseo = $("#deseo .count-tag").text();
					
							totalDeseo = (parseInt(totalDeseo) -1).toString();
							
							totalDeseo = (totalDeseo < 10) ? "0" + totalDeseo : totalDeseo;
							$("#deseo .count-tag").text(totalDeseo);
						}else{
							Swal.fire({
			                    icon: "error",
			                    title: "Error al borrar el producto",
			                    text: "Ha ocurrido un error al borrar el producto de la lista",
			                });
						}
					})

					e.preventDefault();
				})
				$(".enlace_ver_detalles_zapatilla_deseo").click(function(e){
					let id_zapatilla = $(this).attr("id-producto")
					$.getJSON("servicioWebZapatillas/obtenerZapatillaDetalles",
						{
							id : id_zapatilla
						}
					).done(function(res){
						console.log("Datos recibidos: " + res)
						var html = Mustache.render(plantillaDetallesZapatilla , res);
						$("#contenedor").html(html);
						
					})

					e.preventDefault();
				})
				
				$(".enlace_comprar_listado_principal").click(function(res){
				if ( nombre_login != "" ){
					var id_producto = $(this).attr("id-producto");
					$.post("servicioWebCarrito/agregarZapatilla",
							{
								id: id_producto,
								cantidad: 1
							}
					).done(function(res){
						Swal.fire({
		                    icon: "success",
		                    title: "Hecho!",
		                    text: "El producto fue agregado correctamente.",
		                    showConfirmButton: false,
		                    timer: 1500,
		                });				
					});
					
					$.post("servicioWebDeseo/borrarProducto",
						{
							id: id_producto
						}
					).done(function(res){
						if(res == "ok"){
							$("#div-producto-"+id_producto).hide()
						}else{
							Swal.fire({
			                    icon: "error",
			                    title: "Error",
			                    text: "Error al borrar el producto",
			                });
						}
					})
				}else{
					Swal.fire({
	                    icon: "error",
	                    title: "Error al añadir el producto al carrito",
	                    text: "Debes estar identificado para añadir el producto",
	                });
				}
				
				e.preventDefault();
			});
			}
		}).fail(function(){
					Swal.fire({
                    icon: "error",
                    title: "Error!",
                    text: "Debes volver a indentificarte o agregar al menos 1 producto",
                });
		});// end getJson 
	
			}else{
				Swal.fire({
	                    icon: "error",
	                    title: "Error al acceder a la lista de deseos",
	                    text: "Debes estar identificado para poder ver tus deseos",
	                });
			}
});//end carrito



$("#zapatillas_buscar").click(function(res){
	$('#contenedor').css('display', 'none');
	$("#inicio-content").hide();
	mostrar_zapatillas();
})

$("#logout").click(function(){
	$('#contenedor').css('display', 'none');
	$.ajax("servicioWebUsuarios/logout",{
		success: function(res){
			if(res = "ok"){
				$("#inicio-content").hide();
				$("#contenedor").html("hasta pronto" + nombre_login);
				$("#mensaje_login").html("no estas identificado");
				nombre_login = "";
			}//end if
		}//end succes
	})//end ajax
})//end click logout

$("#mis_pedidos").click(function(){
	$('#contenedor').css('display', 'block');
	$.getJSON("servicioWebUsuarios/obtenerDatosYPedidosUsuario",{}
	).done(function(res){
		$("#inicio-content").hide();
		console.log("Datos recibidos:", JSON.stringify(res, null, 2));
		var html = Mustache.render(plantillaUsuarioPedidos , res);
		$("#contenedor").html(html);
						
		})
	.fail(function(){
		Swal.fire({
			icon: "error",
			title: "Error",
			text: "No estas identificado",
		});
	})
})//end click logout




//checkout------------------------------------------------

//funciones de los pasos para el check out
function checkout_paso_0(){
	$('#contenedor').css('display', 'block');
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
	
	if (
    !validarNombre($("#campo_nombre").val()) ||
    !validarDireccion($("#campo_direccion").val()) ||
    !validarProvincia($("#campo_provincia").val()) ||
    !validarCodigoPostal($("#campo_codigo_postal").val()) ||
    !validarEmail($("#campo_email").val()) ||
    !validarNumeroTarjeta($("#numero_tarjeta").val()) ||
    !validarTitularTarjeta($("#titular_tarjeta").val()) ||
    !validarCVV($("#cvv").val()) ||
    !validarFechaCaducidad($("#fecha_caducidad").val()) ||
    !validarPersonaContacto($("#campo_notas").val()) ||
    !validarPersonaContacto($("#campo_pcontacto").val()) ||
    !validarTelefonoContacto($("#campo_tcontacto").val())
	) {
	    return;
	}
	
	
	
	$.post("servicioWebPedidos/paso1",
			{
				nombre: nombre,
				direccion: direccion,
				provincia: provincia,
				email : email,
				codigo_postal: codigo_postal,
				
				tarjeta: tipo_tarjeta,
				numero: numero_tarjeta,
				titular: titular_tarjeta,
				cvv : cvv,
				fecha_caducidad: fecha_caducidad,
				
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
					Swal.fire({
		                    icon: "success",
		                    title: "Hecho!",
		                    text: "Pedido realizado con exito.",
		                    showConfirmButton: false,
		                    timer: 2000,
		                });	
					mostrar_zapatillas()
				}
			})
		});
	});//end done	
	
}//end checkout_paso_1_aceptar









