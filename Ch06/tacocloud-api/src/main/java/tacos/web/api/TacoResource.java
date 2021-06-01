package tacos.web.api;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;
import tacos.Taco;

@Relation(value="taco", collectionRelation="tacos") 
// @Relation 스프링 HATEOAS가 결과 JSON의 필드 이름을 짓는 방법을 지정 가능.
// tacos 리소스 객체에서 사용 될때 ,, taco는 JSON에서 리소스객체가 taco로 참조.
public class TacoResource extends ResourceSupport {
// taco와 비슷한데 ResourceSupport의 서브 클래스로서 Link객체 리스트와 이것을 관리ㄴ하는 메서드를 상속받는다
// Taco의 ID 속성을 가지지 않는다.
  private static final IngredientResourceAssembler 
            ingredientAssembler = new IngredientResourceAssembler();
  
  @Getter
  private final String name;

  @Getter
  private final Date createdAt;

  @Getter
  private final List<IngredientResource> ingredients;
  
  public TacoResource(Taco taco) {
    this.name = taco.getName();
    this.createdAt = taco.getCreatedAt();
    this.ingredients = 
        ingredientAssembler.toResources(taco.getIngredients());// Taco 객체의 ingredient리스트를 IngredientResource로 반환
  }
  
}
