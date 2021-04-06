package tacos.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import tacos.Order;
import tacos.Taco;
@Repository
public class jdbcOrderRepository implements OrderRepository{
	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacoInserter;
	private ObjectMapper objectMapper;
	
	@Autowired
	public jdbcOrderRepository(JdbcTemplate jdbc) {
		this.orderInserter = new SimpleJdbcInsert(jdbc)
			.withTableName("Taco_Order")
			.usingGeneratedKeyColumns("id");
		
		this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order_Tacos");
		
		this.objectMapper = new ObjectMapper();
	}
	
	@Override
	public Order save(Order order) {
		order.setPlacedAt(new Date());
		long orderId = saveOrderDetails(order);
		
		order.setId(orderId);
		List<Taco> tacos = order.getTacos();
		
		for(Taco taco : tacos) {
			saveTacoToOrder(taco,orderId);
		}
		return order;
	}

	private long saveOrderDetails(Order order){
		//objectMapper는 order 를 맵으로 변환
		Map<String,Object> values = objectMapper.convertValue(order, Map.class);
		values.put("placedAt", order.getPlacedAt());
		//objectMapper는 Date 타입을 long 타입으로 변환하므로 한번더 해주기
		
		long orderId = orderInserter.executeAndReturnKey(values)
				.longValue();
		return orderId;
	}
	
	private void saveTacoToOrder(Taco taco,long orderId) {
		Map<String,Object> values = new HashMap<>();
		values.put("tacoOrder", orderId);
		values.put("taco", taco.getId());
		orderTacoInserter.execute(values);
	}
}
