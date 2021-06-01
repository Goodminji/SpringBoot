package tacos.web.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

import tacos.Taco;

@Configuration
public class SpringDataRestConfiguration {

  @Bean // 스프링 데이터REST 가 ResourceProcessor 리소스가 반환되기 전에 리소스를 조작하는 인터페이스
  public ResourceProcessor<PagedResources<Resource<Taco>>>
    tacoProcessor(EntityLinks links) {

    return new ResourceProcessor<PagedResources<Resource<Taco>>>() {
      @Override
      public PagedResources<Resource<Taco>> process(
                          PagedResources<Resource<Taco>> resource) {
        resource.add(
            links.linkFor(Taco.class) // links 추가 HATEOAS
                 .slash("recent")
                 .withRel("recents"));
        return resource;
      }
    };
  }
   // 컨트롤러에서 PagedResources<Resource<Taco>>가 봔환이 된다면 가장 최근에 생성된 타코들의 링크를 받게 되며 /api/tacos의 요청 응답에도 해당 링크들이 포함
}
