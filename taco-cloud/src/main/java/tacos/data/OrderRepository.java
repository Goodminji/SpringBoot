package tacos.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tacos.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{
	/* Order save(Order order); */
	/*
	 * List<Order> findByDeliveryZip(String deliveryZip); List<Order>
	 * readOrdersbyDeliveryZipAndPlacedAtBetween(String dileverZip,Date startDate,
	 * Date endDate);
	 * 
	 * @Query("Order o where o.deliveryCity='Seattle'") List<Order>
	 * readOrdersDeliveredInSeattle();
	 */
}
