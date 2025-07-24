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