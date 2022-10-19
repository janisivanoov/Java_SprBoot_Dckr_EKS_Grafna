@RestController
@RequestMapping("/api/v1")

public class CustomerResource {

   private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

   private static final String ENTITY_NAME = "customer";

   @Value("${spring.application.name}")
   private String applicationName;

   private final CustomerRepository customerRepository;

   public CustomerResource(CustomerRepository customerRepository) {
       this.customerRepository = customerRepository;
   }

   @PostMapping("/customers")
   public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) throws URISyntaxException {
       log.debug("REST request to save Customer : {}", customer);
       if (customer.getId() != null) {
           throw new ResponseStatusException(HttpStatus.CONFLICT, "A new customer cannot already have an ID");
       }
       final var result = customerRepository.save(customer);

       HttpHeaders headers = new HttpHeaders();
       String message = String.format("A new %s is created with identifier %s", ENTITY_NAME, customer.getId());
       headers.add("X-" + applicationName + "-alert", message);
       headers.add("X-" + applicationName + "-params", customer.getId());
       return ResponseEntity.created(new URI("/api/customers/" + result.getId())).headers(headers).body(result);
   }
}
