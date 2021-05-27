package tacos.web;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Component// 스프링 컴토넌트 검색에서 자동으로 스프링 애플리케이션 컨텍스트 빈 생성해준다.
@ConfigurationProperties(prefix="taco.orders")
@Data
@Validated
public class OrderProps {
	 
	@Min(value=5,message="must be between 5 and 25")
	@Max(value=25,message="must be between 5 and 25")
	private int pageSize = 20;

}
