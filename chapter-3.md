---
description: jdbd 데이터베이스 연걸
---

# chapter-3

1.@Repository

* 자동으로 클래스를 찾아서 스프링 어플리케이션 컨텍스트의 빈으로 생성

2. jdbc

* jdbc.query\(SQL,메소드- 쿼리로 생성된 결과 값의 행 개수만큼 호출되며 결과세트의 모든 행을 각각 객체로 생성하고 LIST 저장후 반환\)
* queryForObject\(SQL,메소드-하나의 객체만 반환,검색조건\)
* update\(SQL,인자값\)

@SessionAttributes

* 세션에 계속 유지가 되면서 다수 요청 가

PreparedStatementCreator

* keyholder 사용해서 ID 얻







