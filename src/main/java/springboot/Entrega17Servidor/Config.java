package springboot.Entrega17Servidor;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import springboot.Entrega17Servidor.interceptores.InterceptorAdmin;

@Component
public class Config implements WebMvcConfigurer{


	@Autowired
	private InterceptorAdmin interceptorAdmin;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptorAdmin);
		registry.addInterceptor(localeChangeInterceptor());//agregamos el interceptor que provoca el cambio de idioma definido
		//mas abajo
	}

	//configuracion para poder cambiar el idioma
	@Bean
	public LocaleResolver localeResolver ( ) {
		//aqui indicamos que la seleccion de idioma mantenga sesion

		SessionLocaleResolver localeResolser = new SessionLocaleResolver();
		localeResolser.setDefaultLocale(Locale.getDefault());
		return localeResolser;
	}


	@Bean LocaleChangeInterceptor localeChangeInterceptor () {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setIgnoreInvalidLocale(true);
		localeChangeInterceptor.setParamName("idioma");
		return localeChangeInterceptor;
	}
}
