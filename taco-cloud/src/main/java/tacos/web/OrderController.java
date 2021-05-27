package tacos.web;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
//@ConfigurationProperties(prefix="taco.orders")//구성 속성설정
//pagesize 속성 구성값을 사용 하려면 taco.orders.pageSize 이름으로 사용해야 한다.
public class OrderController {

	/*
	 * private int pageSize = 20; public void setPageSize(int pageSize) {
	 * this.pageSize = pageSize; }
	 */
	private OrderProps props;
	private OrderRepository orderRepo;
	
	public OrderController(OrderRepository orderRepo,OrderProps props) {
		this.orderRepo = orderRepo;
		this.props = props;
	}
	@GetMapping("/current")
	public String orderForm(@AuthenticationPrincipal User user, @ModelAttribute Order order) {
		if(order.getDeliveryName() == null) {
			order.setDeliveryName(user.getFullname());
		}
		
		if(order.getDeliveryStreet() == null) {
			order.setDeliveryStreet(user.getStreet());
		}
		if(order.getDeliveryCity() == null) {
			order.setDeliveryCity(user.getCity());
		}
		if(order.getDeliveryState() == null) {
			order.setDeliveryState(user.getState());
		}
		if(order.getDeliveryZip() == null) {
			order.setDeliveryZip(user.getZip());
		}
		//model.addAttribute("order",new Order());
		return "orderForm";
		
	}
	
	@PostMapping
	public String processOrder(@Valid Order order,Errors errors,SessionStatus sessionStatus
			, @AuthenticationPrincipal User user) {// @AuthenticationPrincipal 권한 셋팅
		if(errors.hasErrors()) {
			return "orderForm";
		}
		
		order.setUser(user);
		
		orderRepo.save(order);
		sessionStatus.setComplete();//세션을 재설정
		//log.info("Order submitted : " + order);
		return "redirect:/";
		
	}
	
	@GetMapping
	public String orderForUser(@AuthenticationPrincipal User user, Model model){
		//인증된 사용자의 주문들을 List에 저장
		
		//주문을 placeAt으로 내림차순 정렬(가장 최근주문부터 오래된 주문 순서로 정렬)
		Pageable pageable = PageRequest.of(0, props.getPageSize());//20개만 나오도록
		model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user,pageable));
		return "orderList";
	}
}
