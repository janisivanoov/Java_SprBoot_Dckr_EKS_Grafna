@Document(collection = "customer")
@Data
@NoArgsConstructor
public class Customer implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id
   private String id;
   @Field("orders")
   private Set<Order> orders = new HashSet<>();

   public Customer addOrder(Order order) {
       this.orders.add(order);
       return this;
   }
}

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id
   @NotBlank
   private String id;

   @NotBlank
   private String customerId;

   @Override
   public boolean equals(Object o) {
       if (this == o) return true;
       if (o == null || getClass() != o.getClass()) return false;
       Order order = (Order) o;
       return id.equals(order.id);
   }

   @Override
   public int hashCode() {
       return Objects.hash(id);
   }
}
