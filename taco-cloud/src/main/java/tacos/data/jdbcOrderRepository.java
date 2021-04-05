package tacos.data;

import org.springframework.stereotype.Repository;

import tacos.Order;
@Repository
public class jdbcOrderRepository implements OrderRepository{
	
	
	@Override
	public Order save(Order order) {
		return null;
	}

}
