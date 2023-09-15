package go.party.tcs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FaviconConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
        .addResourceHandler("/images/**")
        // Defina o padrão de URL que corresponde ao seu ícone
            .addResourceLocations("classpath:/static/css/imagens/");
            // Caminho para a pasta de recursos estáticos
    }
}