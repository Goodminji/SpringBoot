# chapter-2

1. lombok

* lombok은 get,set 필요없이 라이브러리를 사용해서 런타임시 자동 생성해준다.
* @Date 애노테이션 사용하면 컴파일 시에 빌드명세\(pom.xml\) 정의한 Lombok 실행된다 또한 클래스의 속성들을 초기화하는 생성자는 물론 게터와 세터 등을 자동 생성.

2. @Slf4j

* logger 생성하는 애노테이션 

3. @ResquestMapping

* 다목적 요청을 처리
* @GetMapping,@PostMapping,@PutMapping,@DeleteMapping,@PatcnMapping 사용.

4. Thymeleaf

* 뷰 라이브러리 
* 요청 데이터를 나타내 요소 속성을 추가로 갖는 HTML
* ex\) th:text \(교체를 수행하는 네임스페이스 속성\)
* ex\) th:each\(컬렉션을 반복처리하여 해당 컬렉선의 각 요소를 하나씩\)

5. 유효성 검사

```text
<span class="validationError"
    th:if="${#fields.hasErrors('ccExpiration')}"
    th:errors="*{ccExpiration}">deliveryZip Error
</span>
```

  
@Notnull,,@Size 사용 @Valid DTO에 적용

6. WebMvcConfigurer

* 스프링 MVC 구성하는 메서드 정의.
* addViewControllers 메서드는 하나 이상의 뷰 컨트롤러를 등록하기 위해서 사용할 수 있는 ViewControllerRegistry를 인자로 받는다.



* [https://meetup.toast.com/posts/223](https://meetup.toast.com/posts/223) \* Validate 참고 차

