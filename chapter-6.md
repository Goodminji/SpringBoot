---
description: Rest 컨트롤러
---

# chapter-6

1. @RestController 

* 컨트롤러의 모든 HTTP 요청 처리 메소드에서 HTTP 응답 몸체에 직접 쓰는 값을 반환한다는것을 스프링에게 알려준다. 반환값이 뷰를 통해 HTML로 변환되지 않고 직접 HTTP 응답으로 브라우저에 전달되어 나타난다.

2. @crossOrgin - 서로 다른 도메인 간의 요청을 허용한다.

@RequestMapping\(path="/desing",produces="application/json"\)

3. @GetMapping\("/{id}"\)

```text
@GetMapping("/{id}")
public Taco tacoById(@PathVariable("id") Long id){
    Optional<Taco> optTaco = tacoRepo.findById(id);
    if( optTaco.isPresent()){
        return new ResponseEntity<>(optTaco.get(),HttpStatus.OK);//HTTP 200 OK
    }
    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);//HTTP 404(NOT FOUND)
}
```

* id부분이 플레이스 홀더이며 @PathVariable에 의해 {id} 플레이스 홀더와 대응되는 id 매게변수에 해당 요청의 실제값이 지정된다.

4,. @ PostMapping

```text
@PostMapping(consumes="application/json)// consumes는 content-type이 일치하는 요청만 처리.
@ResponseStatus(HttpStatus.CREATED)// 성공적이면서 요청의 결과로 리소스사 생성이 되면 HTTP 201(CREATED) 상태 코드.
public Taco postTaco(@RequestBody Taco taco){
    return tacoRepo.save(taco);
}
```

* @RequestBody - 요청 몸체의 JSON데이터가 TACO 객체로 변환되어 taco 매개변수와 바인된다는것.

5.@PutMapping - 데이터를 변경하는데 사용되기는 하지만 GET과 반대의 의미를 갖는다. GET은 서버로부터 클라이언트로 데이터를 전송하는 반면 PUT은 클리아언트로부터 서버로 데이터를 전송한다. PUT은 데이터 전체를 교체하는것

```text
@PutMapping("/{orderID}")
public Order putOrder(@RequestBody Order order){
    return repo.save(order);
}
```

6. @PatchMapping : 데이터의 일부만 변경하는것.

```text
@PatchMapping(path="/{orderId}",consumes="application/json")
Public Order patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch){

   Order order = repo.findById(orderId).get();
   if(patch.getDeliveryName() != null){
      order.setDeliveryName(patch.getDeliveryName());
   }
}
```

7. @DeleteMapping

```text
@DeleteMapping(path="/{orderId}")
@ResponseStatus(code=HttpStatus.NO_CONTENT)//HTTP 응답코드(204) - 데이터 삭제후 반환 데이터가 없다는것을 사용하는 응답코
Public void deleteOrder(@PathVariable("orderId") Long orderId){

   try{
     repo.deleteById(orderId)     
   }catch (EmptyResultDataAccessException e){}
}
```

\*\*\*\* 8.HATEOAS\(hypermedia as the engine of application state\)

* Json 응답에 하이퍼링크를 포함시키면 HAL\( hypertext application Language\) 이라고 한다.
* "\_links" 속성으로 하이퍼링크포함. 

```text
"_links" :{
    "recents":{
        "href" : "http://localhost:8080/design/recent"
    }
}
```

```text
Resource<Resource<Taco>> recentResource = Resource.wrap(tacos);
recentResource.add(ControllerLinkBuilder
               .linkTo(DesignTacoController.class)//기본 경로 가져오기(/desine)
              .slash("recent")//슬래시(/recent) 값을 URL에 추가
              .withRel("recents"));//Link의 관계 이

Resource<Resource<Taco>> recentResource = Resource.wrap(tacos);
recentResource.add(ControllerLinkBuilder
               .linkTo(methodOn(DesignTacoController.class).recentTacos())
               //methodOn은 컨트롤러 클래스를 인자로 받아서 recentTacos 메소드를 호출.
              .withRel("recents"));//Link의 관계 이름 
```

9. 리소스 어셈블러 생성하기

* ResourceSupport를 상속받아서 Link 객체 리스트를 관리.\(id 속성은 제외해서 DTO처럼\)
* ResourceAssembler 클래스도 생성 필요.\( 
  * ResourceAssembler  클래스를 상속 받아서 링크를 생성\)

10. @Relation - 자바로 정의된 리소스 타입 클래스 이름와 JSON 필드 이름 간의 결합도를 낮출수있다.

```text
@Relation(value="taco",collectionRelation="tacos") 
//Resource 객체에서 사용 될때 tacos라는 이름으로 지정, json에서는 taco로 참조.
public class TacoReource extends ResourceSupper{
  .....
}
```

11. 스프링 REST

* 스프링데이터 REST는 REST 엔드포인트 자동 제공\(엔티티 클래스 이름의 복수형을 사용한다\)         
* * ex\) localhost:8080/ingredients/FLTO \(GET 요청\)
* 복수형을 해결 하고 싶을 때에는 @RestResoure\(rel="tocos",path="tacos"\) 애노테이션 지정해서 관계이름과 경로를 우리가 원하는 것으로 변경 가능.
* 기본적으로 page,size,sort 제공\( ex  localhost:8080/api/tacos?size=5&page=1\)
* HATEOAS는 처음,마지막,다음,이전 페이지의 링크를 요청 응답에 제공한다.\( ResourceProcessor도 제공한다. 이것은 리소스가 반환되기 전에 리소스를 조작하는 인터페이스다\)
* sort 매개변수를 지정하여 결과 리스트를 정력 할 수 있따 \(ex localhost:8080/api/tacos?sort=createdAt,desc & page= 0&size=12\) 
* @RepositoryRestController -&gt; @responseBody애노테이션을 지정하거나 해당 메서드에서 응답 데이터를 포함하는 responseEntity를 반환 해야한다 \(spring.data.rest.base-path 속성의 값이 앞에 붙은 경로를 갖는다.\)

