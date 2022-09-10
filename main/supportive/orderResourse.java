@RestController
@RequestMapping("/api/v1")
public class OrderResource {
   private final Logger log = LoggerFactory.getLogger(OrderResource.class);

   private static final String ENTITY_NAME = "order";

   @Value("${spring.application.name}")
   private String applicationName;

   private final OrderRepository orderRepository;

   private final OrderService orderService;

   public OrderResource(OrderRepository orderRepository, OrderService orderService) {
       this.orderRepository = orderRepository;
       this.orderService = orderService;
   }

   @PostMapping("/orders")
   @Transactional
   public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) throws URISyntaxException {
       log.debug("REST request to save Order : {}", order);
       if (order.getId() != null) {
           throw new ResponseStatusException(HttpStatus.CONFLICT, "A new order cannot already have an ID");
       }
       final var result = orderRepository.save(order);
       orderService.createOrder(result);

       HttpHeaders headers = new HttpHeaders();
       String message = String.format("A new %s is created with identifier %s", ENTITY_NAME, result.getId().toString());
       headers.add("X-" + applicationName + "-alert", message);
       headers.add("X-" + applicationName + "-params", result.getId().toString());
       return ResponseEntity.created(new URI("/api/orders/" + result.getId())).headers(headers).body(result);
   }

}