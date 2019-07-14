package ru.maximum13.jrtest.config;

import static org.springframework.http.MediaType.*;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import ru.maximum13.jax.core.util.time.DateFormats;

/**
 * Конфигурация Spring-MVC.
 *
 * @author MAXIMUM13
 * @since 27.08.2017
 */
@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan("ru.maximum13.jrtest")
public class WebConfig implements WebMvcConfigurer {

    private static final Map<String, String> RESOURCE_MAP = new HashMap<>() {{
        put("/resources/**", "/WEB-INF/resources/");
        put("/resources/css/**", "/WEB-INF/resources/css/");
        put("/resources/js/**", "/WEB-INF/resources/js/");
        put("/resources/img/**", "/WEB-INF/resources/img/");
    }};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        RESOURCE_MAP.forEach((pathPattern, resourceLocation) ->
                registry.addResourceHandler(pathPattern).addResourceLocations(resourceLocation));
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);

        registry.viewResolver(resolver);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // equivalent to <mvc:argument-resolvers>
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(APPLICATION_JSON_UTF8);
        configurer.favorPathExtension(true);
        configurer.useRegisteredExtensionsOnly(true);
        configurer.mediaTypes(new HashMap<>() {{
            put("json", APPLICATION_JSON_UTF8);
            put("xml", APPLICATION_XML);
        }});
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // equivalent to <mvc:message-converters>
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(DateFormats.DATE_TIME_FORMAT);

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDateFormat(DateFormats.DATE_TIME_FORMAT);

        converters.addAll(Arrays.<HttpMessageConverter<?>>asList(
                new ByteArrayHttpMessageConverter(),
                new StringHttpMessageConverter(),
                new FormHttpMessageConverter(),
                new SourceHttpMessageConverter(),
                new MappingJackson2HttpMessageConverter(mapper),
                new MappingJackson2XmlHttpMessageConverter(xmlMapper)
        ));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    /*
    http://stackoverflow.com/questions/23574869/404-error-redirect-in-spring-with-java-config/31436535
    @Bean("containerCustomizer")
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> container.addErrorPages(
            new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.jsp"),
            new ErrorPage(HttpStatus.NOT_FOUND, "/405.jsp"),
            new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.jsp")
        );
    }*/
}
