@Configuration
public class ApplicationConfiguration {
   @Bean
   public RestTemplate restTemplate(RestTemplateBuilder builder) {
       return builder.build();
   }
}