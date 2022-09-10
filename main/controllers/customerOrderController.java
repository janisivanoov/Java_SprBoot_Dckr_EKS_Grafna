@RestController
@RequestMapping("/api/v1")
public class CustomerOrderResource {

   private final Logger log = LoggerFactory.getLogger(CustomerOrderResource.class);

   private static final String ENTITY_NAME = "order";

   @Value("${spring.application.name}")
   private String applicationName;

   private final CustomerRepository customerRepository;

   public CustomerOrderResource(CustomerRepository customerRepository) {
       this.customerRepository = customerRepository;
   }

   @PostMapping("/customerOrders/{customerId}")
   public ResponseEntity<Order> createOrder(@PathVariable String customerId, @Valid @RequestBody Order order) {
       log.debug("REST request to save Order : {} for Customer ID: {}", order, customerId);
       if (customerId.isBlank()) {
           throw new ResponseStatusException(
                   HttpStatus.NOT_FOUND, "No Customer: " + ENTITY_NAME);
       }
       final Optional<Customer> customerOptional = customerRepository.findById(customerId);
       if (customerOptional.isPresent()) {
           final var customer = customerOptional.get();
           customer.addOrder(order);
           customerRepository.save(customer);
           return ResponseEntity.ok()
                   .body(order);
       } else {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Customer: " + ENTITY_NAME);
       }
   }
}