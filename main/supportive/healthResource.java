@RestController
@RequestMapping("/api/v1")
public class HealthResource {
   private final Logger log = LoggerFactory.getLogger(HealthResource.class);

   @GetMapping(
           value = "/health",
           produces = "application/json")
   public ResponseEntity<Health> getHealth() {
       log.debug("REST request to get the Health Status");
       final var health = new Health();
       health.setStatus(HealthStatus.UP);
       return ResponseEntity.ok().body(health);
   }
}
