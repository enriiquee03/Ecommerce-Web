var $productDetailElement = $('#pd-o-initiate'),
    $productDetailElementThumbnail = $('#pd-o-thumbnail');

	function initElevateZoom() {
	    var ELEVATE_ZOOM_OBJ = {
	        borderSize: 1,
	        autoWidth: true,
	        zoomWindowWidth: 540,
	        zoomWindowHeight: 540,
	        zoomWindowOffetx: 10,
	        borderColour: '#e9e9e9',
	        cursor: 'pointer'
	    };
	
	    $productDetailElement.on('init', function () {
	        $(this).closest('.slider-fouc').removeClass('slider-fouc');
	    });
	
	    $productDetailElement.slick({
	        slidesToShow: 1,
	        slidesToScroll: 1,
	        infinite: false,
	        arrows: false,
	        dots: false,
	        fade: true,
	        asNavFor: $productDetailElementThumbnail
	    });
	
	    // Inicializa el plugin elevateZoom en la primera imagen
	    $('#pd-o-initiate .slick-current img').elevateZoom(ELEVATE_ZOOM_OBJ);
	
	    $productDetailElement.lightGallery({
	        selector: '.pd-o-img-wrap',
	        download: false,
	        thumbnail: false,
	        autoplayControls: false,
	        actualSize: false,
	        hash: false,
	        share: false
	    });
	
	    $productDetailElementThumbnail.on('init', function () {
	        $(this).closest('.slider-fouc').removeAttr('class');
	    });
	
	    $productDetailElementThumbnail.slick({
	        slidesToShow: 4,
	        slidesToScroll: 1,
	        infinite: false,
	        arrows: true,
	        dots: false,
	        focusOnSelect: true,
	        asNavFor: $productDetailElement,
	        prevArrow: '<div class="pt-prev"><i class="fas fa-angle-left"></i></div>',
	        nextArrow: '<div class="pt-next"><i class="fas fa-angle-right"></i></div>',
	        responsive: [
	            {
	                breakpoint: 1200,
	                settings: {
	                    slidesToShow: 4
	                }
	            },
	            {
	                breakpoint: 992,
	                settings: {
	                    slidesToShow: 3
	                }
	            },
	            {
	                breakpoint: 576,
	                settings: {
	                    slidesToShow: 2
	                }
	            }
	        ]
	    });
	}
	
	function initZoomHandlers() {
	    $productDetailElement.on('beforeChange', function (event, slick, currentSlide, nextSlide) {
	        $('.zoomContainer').hide();
	    });
	
	    $productDetailElement.on('afterChange', function (event, slick, currentSlide) {
	        var currentImage = slick.$slides.eq(currentSlide).find('img');
	
	        currentImage.removeData('elevateZoom');
	        currentImage.elevateZoom({
	            borderSize: 1,
	            autoWidth: true,
	            zoomWindowWidth: 540,
	            zoomWindowHeight: 540,
	            zoomWindowOffetx: 10,
	            borderColour: '#e9e9e9',
	            cursor: 'pointer'
	        });
	
	        $('.zoomContainer').show();
	    });
	}
	
	function productDetailInit() {
	    if ($productDetailElement.length && $productDetailElementThumbnail.length) {
	        initElevateZoom();
	        initZoomHandlers();
	    }
	}
	
	productDetailInit();