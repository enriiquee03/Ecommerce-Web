//validaciones 

let regexp_nombre = /^[a-z áéíóúñ]{2,10}$/i;
let regexp_email = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
let regexp_pass = /^[a-z0-9áéíóúñ]{3,10}$/i;

//checkout regex
let regexp_direccion = /^[a-zA-Z0-9\s,áéíóúñÁÉÍÓÚÑ-]{2,100}$/;
let regexp_provincia = /^[a-zA-ZáéíóúñÁÉÍÓÚÑ\s]{2,50}$/;
let regexp_codigo_postal = /^\d{5}$/;
let regexp_numero_tarjeta = /^\d{16}$/;
let regexp_titular_tarjeta = /^[a-zA-ZáéíóúñÁÉÍÓÚÑ\s]{2,50}$/;
let regexp_cvv = /^\d{3}$/;
let regexp_fecha_caducidad = /^(0[1-9]|1[0-2])\/\d{2}$/;
//valoracion regex
let  regexp_valoracion = /^[A-Za-z0-9\s.,!?()-]{10,500}$/;



function validarNombre(nombre){
	if(regexp_nombre.test(nombre)){
		return true;
	}else{
		Swal.fire({
			icon: "error",
			title: "Error",
			text: "Nombre incorrecto.",
			showConfirmButton: true,
		});
		return false;
	}
}

function validarEmail(email){
	if(regexp_email.test(email)){
		return true;
	}else{
		Swal.fire({
			icon: "error",
			title: "Error",
			text: "Email incorrecto.",
			showConfirmButton: true,
		});
		return false;
	}
}

function validarPass(pass){
	if(regexp_pass.test(pass)){
		return true;
	}else{
		Swal.fire({
			icon: "error",
			title: "Error",
			text: "Contraseña incorrecta.",
			showConfirmButton: true,
		});	
		return false;
	}
}

function validarDireccion(direccion) {
    return validarCampo(direccion, regexp_direccion, "Dirección incorrecta.");
}

function validarProvincia(provincia) {
    return validarCampo(provincia, regexp_provincia, "Provincia incorrecta.");
}

function validarCodigoPostal(codigoPostal) {
    return validarCampo(codigoPostal, regexp_codigo_postal, "Código postal incorrecto.");
}
function validarNumeroTarjeta(numeroTarjeta) {
    return validarCampo(numeroTarjeta, regexp_numero_tarjeta, "Número de tarjeta incorrecto.");
}

function validarTitularTarjeta(titularTarjeta) {
    return validarCampo(titularTarjeta, regexp_titular_tarjeta, "Titular de tarjeta incorrecto.");
}

function validarCVV(cvv) {
    return validarCampo(cvv, regexp_cvv, "CVV incorrecto.");
}

function validarFechaCaducidad(fechaCaducidad) {
    return validarCampo(fechaCaducidad, regexp_fecha_caducidad, "Fecha de caducidad incorrecta.");
}

function validarCampo(valor, regex, mensajeError) {
    if (regex.test(valor)) {
        return true;
    } else {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: mensajeError,
            showConfirmButton: true,
        });
        return false;
    }
}

// Validación de notas de envío
let regexp_notas_envio = /^[a-zA-Z0-9.,\-()?!&$%ñáéíóúÁÉÍÓÚ ]{0,255}$/;

function validarNotasEnvio(notas_envio) {
    if (regexp_notas_envio.test(notas_envio)) {
        return true;
    } else {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "Notas de envío incorrectas.",
            showConfirmButton: true,
        });
        return false;
    }
}

// Validación de persona de contacto
let regexp_persona_contacto = /^[a-zA-ZñáéíóúÁÉÍÓÚ\s]{2,50}$/;

function validarPersonaContacto(persona_contacto) {
    if (regexp_persona_contacto.test(persona_contacto)) {
        return true;
    } else {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "Nombre de persona de contacto incorrecto.",
            showConfirmButton: true,
        });
        return false;
    }
}

// Validación de teléfono de contacto
let regexp_telefono_contacto = /^\d{9}$/;

function validarTelefonoContacto(telefono_contacto) {
    if (regexp_telefono_contacto.test(telefono_contacto)) {
        return true;
    } else {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "Teléfono de contacto incorrecto (debe tener 9 dígitos).",
            showConfirmButton: true,
        });
        return false;
    }
}

//validacion valoraciones: 

function validarValoracion(valoracion) {
    if (regexp_valoracion.test(valoracion)) {
        return true;
    } else {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "Descripcion de valoración incorrecta (debe tener 10 caracteres).",
            showConfirmButton: true,
        });
        return false;
    }
}