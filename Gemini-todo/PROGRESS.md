# 개발 진행 상황

## 1차 구현 (2025-07-24)

### 1. 프로젝트 설정 및 의존성 추가

-   `build.gradle`
    -   Spring Web, Spring Data JPA, Mustache, Spring Security 의존성 추가
    -   MySQL Connector, Lombok, JWT 관련 라이브러리 추가
-   `src/main/resources/application.properties`
    -   데이터베이스 연결 정보 (URL, username, password) 설정
    -   JPA 및 Hibernate 설정 (ddl-auto, show-sql)
    -   JWT 비밀 키 및 만료 시간 설정

### 2. 도메인 모델(Entity) 및 DTO 구현

-   **Entity 패키지 (`domain`) 생성**
    -   `WebUser.java`: 사용자 엔티티
    -   `Category.java`: 카테고리 엔티티
    -   `Todo.java`: 할 일 엔티티
-   **DTO 패키지 (`dto`) 생성**
    -   `WebUserDto.java`
    -   `CategoryDto.java`
    -   `TodoDto.java`
    -   `MyPageDto.java`
    -   `UserRankDto.java`

### 3. 리포지토리(Repository) 구현

-   **Repository 패키지 (`repository`) 생성**
    -   `WebUserRepository.java`
    -   `CategoryRepository.java`
    -   `TodoRepository.java`

### 4. 핵심 비즈니스 로직(Service) 구현 (기본 골격)

-   **Service 패키지 (`service`) 생성**
    -   `WebUserService.java`: 회원가입 로직 구현
    -   `TodoService.java`: 기본 골격 구현
    -   `CategoryService.java`: 기본 골격 구현
    -   `MyPageService.java`: 기본 골격 구현

### 5. API 및 화면 라우팅 컨트롤러 구현 (기본 골격)

-   **Controller 패키지 (`controller`) 생성**
    -   `WebUserApiController.java`: 회원가입 API 구현
    -   `TodoApiController.java`: 기본 골격 구현
    -   `CategoryApiController.java`: 기본 골격 구현
    -   `TodoController.java`: 시작 페이지 라우팅 구현

### 6. 화면(View) 구현

-   `src/main/resources/templates/todos/start.mustache`
    -   기본 시작 페이지 템플릿 생성

## 2차 구현 (2025-07-24)

### 1. 보안 관련 클래스 구현

-   `config` 패키지 생성
    -   `SecurityConfig.java`: Spring Security 설정 (BCryptPasswordEncoder, JWT 필터 추가, 인증 예외 경로 설정)
-   `jwt` 패키지 생성
    -   `JwtUtil.java`: JWT 토큰 생성, 검증, 정보 추출 유틸리티
-   `filter` 패키지 생성
    -   `JwtAuthenticationFilter.java`: JWT 토큰 기반 인증 필터
-   `service` 패키지
    -   `UserSecurityService.java`: Spring Security `UserDetailsService` 구현

### 2. WebUser 서비스 및 API 구현

-   `WebUserService.java`
    -   `login(WebUserDto dto)`: 사용자 로그인 및 JWT 토큰 생성
    -   `logout()`: JWT 쿠키 무효화
-   `WebUserApiController.java`
    -   `POST /api/todos/login`: 로그인 API 엔드포인트
    -   `POST /api/todos/logout`: 로그아웃 API 엔드포인트

### 3. Todo 서비스 및 API 구현

-   `TodoService.java`
    -   `updateStatus(Long id, String newStatus)`: 할 일 상태 변경 (기본 구현)
    -   `editTask(Long id, TodoDto dto)`: 할 일 수정 (기본 구현)
    -   `deleteTask(Long id)`: 할 일 삭제
    -   `getTasks(Long userId, String status)`: 전체 할 일 조회
    -   `getTodayTasks(Long userId, String status)`: 오늘 할 일 조회
    -   `getFavoriteTasks(Long userId, String status)`: 중요 할 일 조회
-   `TodoApiController.java`
    -   명세된 모든 API 엔드포인트 추가 및 서비스 연결

### 4. Category 서비스 및 API 구현

-   `CategoryService.java`
    -   `addCategory(Long userId, CategoryDto dto)`: 카테고리 추가
    -   `editCategory(Long id, CategoryDto dto)`: 카테고리 수정 (기본 구현)
    -   `deleteCategory(Long id)`: 특정 카테고리 삭제
    -   `deleteAllCategories(Long userId)`: 모든 카테고리 삭제
-   `CategoryApiController.java`
    -   명세된 모든 API 엔드포인트 추가 및 서비스 연결

### 5. 화면 라우팅 및 템플릿 구현

-   `TodoController.java`
    -   명세된 모든 화면 라우팅 추가 (`/login`, `/signup`, `/index`, `/today`, `/favorite`, `/schedule`, `/mypage`)
-   `src/main/resources/templates/todos/`
    -   `login.mustache`
    -   `signup.mustache`
    -   `index.mustache`
    -   `today.mustache`
    -   `favorite.mustache`
    -   `schedule.mustache`
    -   `mypage.mustache`

## 3차 구현 (2025-07-24)

### 1. MyPageService 구현 완료

-   `MyPageService.java`
    -   `getMyPageInfo(Long userId)`: 현재 사용자의 할 일 진행률 계산 및 전체 사용자 랭킹 (상위 10명) 조회 기능 구현 완료.
-   `TodoRepository.java`
    -   `findByWebUserId(Long userId)` 메서드 추가.

### 2. Todo 엔티티 업데이트

-   `Todo.java`
    -   `updateStatus(String status)`: 할 일 상태 업데이트 메서드 추가.
    -   `updateTodo(String title, LocalDate deadline, Category category)`: 할 일 정보 업데이트 메서드 추가.
    -   `updateFavorite(boolean favorite)`: 할 일 중요도 업데이트 메서드 추가.

### 3. TodoService 업데이트

-   `TodoService.java`
    -   `updateStatus(Long id, String newStatus)`: `Todo` 엔티티의 `updateStatus` 메서드를 사용하도록 수정.
    -   `editTask(Long id, TodoDto dto)`: `Todo` 엔티티의 `updateTodo` 메서드를 사용하도록 수정.
    -   `updateFavorite(Long id)`: `Todo` 엔티티의 `updateFavorite` 메서드를 사용하도록 추가 및 수정.

### 4. TodoApiController 업데이트

-   `TodoApiController.java`
    -   `getUserId(UserDetails userDetails)`: `SecurityContextHolder.getContext().getAuthentication().getPrincipal()`을 사용하여 실제 사용자 ID를 가져오도록 수정.
    -   `updateFavorite(Long id)`: `todoService.updateFavorite(id)`를 호출하도록 수정.

### 5. TodoController 업데이트

-   `TodoController.java`
    -   `/mypage` 라우팅에서 `MyPageService.getMyPageInfo()`를 호출하여 `MyPageDto`를 모델에 추가하도록 수정.
    -   `SecurityContextHolder.getContext().getAuthentication().getPrincipal()`을 사용하여 사용자 ID를 가져오도록 수정.

### 6. View (mypage.mustache) 업데이트

-   `src/main/resources/templates/todos/mypage.mustache`
    -   `MyPageDto`의 `progress`, `myRank`, `top10Users` 정보를 표시하도록 템플릿 수정.

### 7. JWT 인증 필터 업데이트

-   `JwtAuthenticationFilter.java`
    -   `UsernamePasswordAuthenticationToken` 생성 시 `principal`을 `JwtUtil`에서 추출한 `userId`로 설정하도록 수정.

### 8. 데이터베이스 설정 업데이트

-   `src/main/resources/application.properties`
    -   `spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect` 설정 추가.
    -   데이터베이스 연결 정보 (username, password)를 애플리케이션 전용 사용자로 변경.

## 4차 구현 (2025-07-24)

### 1. CategoryService 상세 구현

-   `CategoryService.java`
    -   `editCategory(Long id, CategoryDto dto)`: `Category` 엔티티의 `updateName` 메서드를 사용하도록 수정.
    -   `getCategories(Long userId)`: 사용자별 카테고리 목록 조회 메서드 추가.

### 2. Category 엔티티 업데이트

-   `Category.java`
    -   `updateName(String name)`: 카테고리 이름 업데이트 메서드 추가.

### 3. CategoryApiController 구현 완료

-   `CategoryApiController.java`
    -   `getUserId()`: `SecurityContextHolder.getContext().getAuthentication().getPrincipal()`을 사용하여 사용자 ID를 가져오도록 수정.
    -   `addCategory`, `editCategory`, `deleteCategory`, `deleteAllCategories` 메서드에서 `getUserId()`를 사용하도록 수정.
    -   `getCategories()`: 사용자별 카테고리 목록 조회 API 엔드포인트 추가.

### 4. TodoService 카테고리 처리 로직 최종 확인

-   `TodoService.java`
    -   `addTask` 및 `editTask` 메서드의 카테고리 처리 로직이 명세서에 따라 올바르게 구현되었음을 확인.

## 5차 구현 (2025-07-24)

### 1. TodoApiController 최종 확인 및 수정

-   `TodoApiController.java`
    -   `getUserId()`: `UserDetails` 파라미터를 제거하고 `SecurityContextHolder.getContext().getAuthentication().getPrincipal()`을 직접 사용하도록 수정.
    -   `addTodayTask`: `TodoDto`의 `deadline`을 `LocalDate.now()`로 설정하도록 추가.

### 2. TodoService 최종 확인

-   `TodoService.java`
    -   `getTodayTasks`, `getFavoriteTasks` 메서드의 로직이 올바르게 구현되었음을 확인.
    -   `addTask` 메서드에서 `TodoDto`의 `deadline` 및 `favorite` 필드 처리 로직이 올바르게 구현되었음을 확인.

## 6차 구현 (2025-07-24)

### 1. WebUserApiController 및 WebUserService 최종 확인

-   `WebUserApiController.java`
    -   `signup`, `login`, `logout` 엔드포인트가 명세서에 따라 올바른 응답 및 오류 처리를 하는지 확인.
-   `WebUserService.java`
    -   `signup`, `login`, `logout` 메서드가 명세서에 따라 올바르게 동작하는지 확인.

### 2. 전반적인 오류 처리 확인

-   각 API 컨트롤러에서 발생하는 예외가 적절히 처리되고, 명세서에 명시된 HTTP 상태 코드와 응답 메시지를 반환하는지 확인.

## 7차 구현 (2025-07-24)

### 1. 최종 검증 및 빌드 확인

-   모든 컨트롤러에서 사용자 ID 추출 로직이 올바르게 구현되었음을 확인.
-   `CategoryService`의 `deleteCategory` 및 `deleteAllCategories` 메서드가 명세서에 따라 카테고리 삭제 시 연관된 할 일(Todo)도 함께 삭제하는지 확인 완료.
-   전반적인 코드 검토를 통해 명세서에 따른 모든 핵심 기능이 구현되었음을 확인.
-   IntelliJ에서 애플리케이션이 성공적으로 실행됨을 확인.

**결론: 명세서에 명시된 모든 핵심 기능이 구현되었음을 확인했습니다.**