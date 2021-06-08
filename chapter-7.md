---
description: REST 서비스 사용하기
---

# chapter-7

1. 리소스 가져오기 GET

* getForObject\(\)를 사용 하여 GET

```text
public Ingredient getIngredientById(String ingredientId) {
    return rest.getForObject("http://localhost:8080/ingredient/
    {id}",Ingredient.class,ingredientId)
    
}
public Ingredient getIngredientById(String ingredientId) {
     Map<String,String> urlVariable = new HashMap<>();
     urlVariable.put("id",ingredientID)
     return rest.getForObject("http://localhost:8080/ingredient/
    {id}",Ingredient.class,urlVariable)
}


```

* getForEntity\(\)는 응답 결과를 나타내는 도메인 객체를 반환하는 대신 도메인 객체를 포함하는 ResponseEntity 객체 반환\(응답 헤더 등\) 

```text
public Ingredient getIngredientById(String ingredientId) {
    ResponseEntity<Ingredient> responseEntity =
       rest.getForEntity("http://localhost:8080/ingredients/{id}",
           Ingredient.class, ingredientId);
    return responseEntity.getBody();
}
```

2.리소스 쓰 Put

```text
  public void updateIngredient(Ingredient ingredient) {
    rest.put("http://localhost:8080/ingredients/{id}",
          ingredient, ingredient.getId());
  }
```

3. 리소스 삭제하기 Delete

```text
 public void deleteIngredient(Ingredient ingredient) {
    rest.delete("http://localhost:8080/ingredients/{id}",
        ingredient.getId());
  }
```

4. 리소스 데이터 추가하기\(POST\)

```text
 public Ingredient createIngredient(Ingredient ingredient) {
    return rest.postForObject("http://localhost:8080/ingredients",
        ingredient, Ingredient.class);
  }
```

* postForLocation\(\)은 리소스 객체 대신 새로 생성된 리소스의 URI를 반환한다.
* postForEntity\(\)는 새로 생성된 리소스의 위치와 리소스 객체 모두.

```text
public URI createIngredient(Ingredient ingredient) {
  return rest.postForLocation("http://localhost:8080/ingredients",
      ingredient, Ingredient.class);
}

 public Ingredient createIngredient(Ingredient ingredient) {
   ResponseEntity<Ingredient> responseEntity =
         rest.postForEntity("http://localhost:8080/ingredients",
                          ingredient,
                           Ingredient.class);
    log.info("New resource created at " +
              responseEntity.getHeaders().getLocation());
     return responseEntity.getBody();
 }
```

