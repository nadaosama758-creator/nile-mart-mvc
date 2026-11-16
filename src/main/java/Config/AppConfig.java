package Config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("org.route")
//@ImportResource("/bean.xml")
@PropertySource("/application.properties")
@EnableAspectJAutoProxy
public class AppConfig {


    /*@Bean
    PromotionalPricingService promotionalPricingService(){
        return new PromotionalPricingService(BigDecimal.valueOf(10));
    }

     */

    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

   /* @Bean
    StandardPricingService standardPricing(){
        return new StandardPricingService();
    }


    @Bean
    InMemoryRepository inMemoryRepo(){
        return new InMemoryRepository();
    }

    @Bean
    ProductService productService(){
        return new ProductService(standardPricing(),inMemoryRepo());
    }

    */
}
