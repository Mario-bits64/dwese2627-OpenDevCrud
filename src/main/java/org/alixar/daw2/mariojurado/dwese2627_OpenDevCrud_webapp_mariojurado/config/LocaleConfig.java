package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Configuración del idioma de la aplicación.
 *
 * @author Mario
 * @version 1.0
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(LocaleConfig.class);

    /**
     * Guarda el idioma en la sesión. Por defecto es español.
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.of("es"));
        logger.info("LocaleResolver configurado con el idioma predeterminado: es");
        return slr;
    }

    /**
     * Permite cambiar el idioma con el parámetro lang (?lang=es o ?lang=en).
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    /**
     * Registra el interceptor de cambio de idioma.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
