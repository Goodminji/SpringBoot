package tacos.web.api;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import tacos.Taco;

public class TacoResourceAssembler 
 	extends ResourceAssemblerSupport<Taco, TacoResource> {
	//ResourceAssemblerSupport 의 기본 생성자를 호출하며 TacoResource를 생성하면서 만들어지는 링크에 포함되는URL의 기본 경로를 결정하기 위해 
    // DesignTacoController를 사용한다.
  public TacoResourceAssembler() {
    super(DesignTacoController.class, TacoResource.class);
  }
  
  @Override
  protected TacoResource instantiateResource(Taco taco) { //Resource 인스턴스만 생성
    return new TacoResource(taco);
  }

  @Override
  public TacoResource toResource(Taco taco) {// Resource 인스턴스를 생성하면서 링크도 추가
    return createResourceWithId(taco.getId(), taco);
  }

}
