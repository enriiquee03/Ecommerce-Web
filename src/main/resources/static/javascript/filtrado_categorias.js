//filtrado por categoria en zapatillas 




           
    $("#filters a").click(function(){

         $("#filters .current").removeClass("current");
         $(this).addClass("current");

         var selector = $(this).attr("data-filter");

			   $(".isotope-container").isotope({
		        filter: selector,
		        animationOptions: {
		            duration: 100,
		            easing: 'linear',
		        },
		        transitionDuration: '0.5s'
		    })
			
			
			
         return false;
       });
       
       
  