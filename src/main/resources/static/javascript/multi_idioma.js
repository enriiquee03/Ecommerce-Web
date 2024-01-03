// Establecer el elemento de España como activo al cargar la página
$(document).ready(function () {
	var langValue = $(".dropdown-item.active").data("lang");
    // Cambiar la bandera en el botón
    $(".dropdown .btn")
        .html("<span class='flag-icon flag-icon-" + langValue + " me-1'></span> <span>" + langValue + "</span>");
});

// Manejar el clic en un elemento de la lista desplegable
$(document).on("click", ".dropdown-menu .dropdown-item", function (e) {
    e.preventDefault();

    if (!$(this).hasClass("flag-icon-es") && !$(this).hasClass("default")) {
        $(".dropdown-menu .dropdown-item").not(".flag-icon-es").removeClass("active");

        $(this).addClass("active");
		
	        Swal.fire({
	            icon: "success",
	            title: "Hecho!",
	            text: "El idioma ha sido cambiado correctamente.",
	            showConfirmButton: false,
	            timer: 1500,
	        });
		
        // Obtener el idioma del elemento seleccionado
        const selectedLang = $(this).data("lang");

        setTimeout(() => {
        	window.location.href = updateQueryStringParameter(window.location.href, "idioma", selectedLang);
        }, "1000");

    }
});


function updateQueryStringParameter(uri, key, value) {
    var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
    var separator = uri.indexOf("?") !== -1 ? "&" : "?";
    if (uri.match(re)) {
        return uri.replace(re, "$1" + key + "=" + value + "$2");
    } else {
        return uri + separator + key + "=" + value;
    }
}
