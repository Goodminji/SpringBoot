package tacos.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import tacos.Order;
import tacos.User;

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
	List<Order> findByUserOrderByPlacedAtDesc(User user,Pageable pageable);
}
