@Document(collection = "order")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id
   private String id;

   @NotBlank
   @Field("customer_id")
   private String customerId;

   @Field("created_at")
   @CreatedDate
   private Instant createdAt;

   @Field("updated_at")
   @LastModifiedDate
   private Instant updatedAt;

   @Version
   public Integer version;

   @Field("status")
   private OrderStatus status = OrderStatus.CREATED;

   @Field("payment_status")
   private Boolean paymentStatus = Boolean.FALSE;

   @NotNull
   @Field("payment_method")
   private PaymentType paymentMethod;

   @NotNull
   @Field("payment_details")
   private String paymentDetails;

   @Field("shipping_address")
   private Address shippingAddress;

   @Field("products")
   @NotEmpty
   private Set<@Valid Product> products;