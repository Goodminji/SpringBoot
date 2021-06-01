//tag::recents[]
package tacos.web.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//end::recents[]
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//tag::recents[]
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.Taco;
import tacos.data.TacoRepository;

@RestController //컨트롤러의 모든 HTTP 요청 처리 메서드에서 HTTP 응답 몸체에 직섭 쓰는 값을 반환(직접 HTTP 응답으로 브라우저 전달)
@RequestMapping(path="/design",                      // <1> /desing 경로의 요청을 처리
                produces="application/json")
@CrossOrigin(origins="*")        // <2> - 서로 다른 도메인 간의 요청을 허용한다
public class DesignTacoController {
  private TacoRepository tacoRepo;
  
  @Autowired
  EntityLinks entityLinks;

  public DesignTacoController(TacoRepository tacoRepo) {
    this.tacoRepo = tacoRepo;
  }

  @GetMapping("/recent")
  public Iterable<Taco> recentTacos() {                 //<3> /desing/recent
    PageRequest page = PageRequest.of(
            0, 12, Sort.by("createdAt").descending());
    return tacoRepo.findAll(page).getContent();
  }
  //end::recents[]

//  @GetMapping("/recenth")
//  public Resources<TacoResource> recentTacosH() {
//    PageRequest page = PageRequest.of(
//            0, 12, Sort.by("createdAt").descending());
//    List<Taco> tacos = tacoRepo.findAll(page).getContent();
//    
//    List<TacoResource> tacoResources = 
//        new TacoResourceAssembler().toResources(tacos); // tacoResource 객체를 저장한 리스트를 생성
  
//    Resources<TacoResource> recentResources = 
//        new Resources<TacoResource>(tacoResources);
  
//    recentResources.add(
//        linkTo(methodOn(DesignTacoController.class).recentTacos())
//        .withRel("recents")); recents 링크를 추가 한다.
  
//    return recentResources; (_self 링크를 갖는 타코들과, 이 타코들이 포함된 리스트 자체의 recents 링크를 갖는 타코 리스트를 생성한다.
//  }

  
  
//ControllerLinkBuilder.linkTo(DesignTacoController.class)
//.slash("recent")
//.withRel("recents"));

  
  
  
//  @GetMapping("/recenth")
//  public Resources<TacoResource> recenthTacos() {
//    PageRequest page = PageRequest.of(
//            0, 12, Sort.by("createdAt").descending());
//    List<Taco> tacos = tacoRepo.findAll(page).getContent();
//
//    List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);
//    
//    Resources<TacoResource> tacosResources = new Resources<>(tacoResources);
////    Link recentsLink = ControllerLinkBuilder
////        .linkTo(DesignTacoController.class)
////        .slash("recent")
////        .withRel("recents");
//
//    Link recentsLink = 
//        linkTo(methodOn(DesignTacoController.class).recentTacos())
//        .withRel("recents");
//    tacosResources.add(recentsLink);
//    return tacosResources;
//  }
  
  //tag::postTaco[]
  @PostMapping(consumes="application/json") // /design 의 Post 요청
  @ResponseStatus(HttpStatus.CREATED) // HTTP 201 상태코드
  public Taco postTaco(@RequestBody Taco taco) { // @RequestBody 는 json 데이터가 Taco 객체로 변환되어 바인딩 되도록
    return tacoRepo.save(taco);
  }
  //end::postTaco[]
  
  
  @GetMapping("/{id}") // /desing/{id}
  public Taco tacoById(@PathVariable("id") Long id) { // @PathVariable에 의해 {id} 플레이스 홀더와 대응되는 id 매개변수에 실제 값이 지정
    Optional<Taco> optTaco = tacoRepo.findById(id);
    if (optTaco.isPresent()) {
      return optTaco.get();
    }
    return null;
  }
  
//  @GetMapping("/{id}")
//  public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
//    Optional<Taco> optTaco = tacoRepo.findById(id);
//    if (optTaco.isPresent()) {
//      return new ResponseEntity<>(optTaco.get(), HttpStatus.OK); //HTTP 200 OK 상태코드
//    }
//    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // HTTP 404 NOT  FOUND 상태코드
//  }

  
//tag::recents[]
}
//end::recents[]

