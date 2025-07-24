# Todo-List 애플리케이션 명세서

## 1. 프로젝트 개요

본 문서는 **Todo-List** 애플리케이션의 기술적인 명세와 구조를 설명합니다. 이 애플리케이션은 사용자가 할 일을 효율적으로 관리할 수 있도록 돕는 웹 기반 서비스입니다. 사용자는 회원가입 및 로그인을 통해 자신만의 할 일 목록을 생성하고, 카테고리별로 관리하며, 마감일을 설정하고, 중요도를 표시할 수 있습니다.

### 1.1. 주요 기능

-   **사용자 관리**: 회원가입, 로그인, 로그아웃 기능을 통해 사용자 계정을 관리합니다.
-   **할 일(Todo) 관리**: 할 일 생성, 조회, 수정, 삭제 기능을 제공합니다.
-   **카테고리 관리**: 사용자는 자신만의 카테고리를 생성하여 할 일을 분류하고 관리할 수 있습니다.
-   **상태 관리**: 각 할 일은 '준비' 또는 '완료' 상태를 가집니다.
-   **마감일 및 중요도**: 할 일에 마감일을 설정하고, 중요도(Favorite)를 표시할 수 있습니다.
-   **마이페이지**: 자신의 할 일 진행률과 다른 사용자들과의 랭킹을 확인할 수 있습니다.

## 2. 기술 스택

`build.gradle` 파일 분석을 통해 확인된 주요 기술 스택은 다음과 같습니다.

-   **언어**: Java 17
-   **프레임워크**: Spring Boot 3.4.2
-   **데이터베이스**: MySQL (mysql-connector-java:8.0.33)
-   **데이터 접근**: Spring Data JPA
-   **템플릿 엔진**: Mustache
-   **보안**: Spring Security, JWT (jjwt:0.9.1)
-   **라이브러리**:
    -   Lombok: 코드 간소화
    -   JAXB API: XML 바인딩

## 3. 데이터베이스 스키마

애플리케이션의 주요 데이터 모델은 `WebUser`, `Category`, `Todo` 세 가지 엔티티로 구성됩니다.

### 3.1. `WebUser`

사용자 정보를 저장하는 엔티티입니다.

-   **테이블명**: `web_user`
-   **컬럼**:
    -   `id` (PK, BIGINT, AUTO_INCREMENT): 사용자 고유 ID
    -   `username` (VARCHAR, NOT NULL, UNIQUE): 사용자 이름 (로그인 시 사용)
    -   `password` (VARCHAR, NOT NULL): 암호화된 사용자 비밀번호
    -   `email` (VARCHAR, NOT NULL, UNIQUE): 사용자 이메일

### 3.2. `Category`

사용자가 생성하는 할 일 카테고리 정보를 저장하는 엔티티입니다.

-   **테이블명**: `category`
-   **컬럼**:
    -   `id` (PK, BIGINT, AUTO_INCREMENT): 카테고리 고유 ID
    -   `name` (VARCHAR, NOT NULL): 카테고리 이름
-   **관계**:
    -   `webUser` (N:1, `webuser_id`): 해당 카테고리를 소유한 사용자와의 관계

### 3.3. `Todo`

개별 할 일 정보를 저장하는 엔티티입니다.

-   **테이블명**: `todo`
-   **컬럼**:
    -   `id` (PK, BIGINT, AUTO_INCREMENT): 할 일 고유 ID
    -   `title` (VARCHAR): 할 일 제목
    -   `status` (VARCHAR): 할 일 상태 ("준비", "완료")
    -   `deadline` (DATE): 마감일
    -   `favorite` (BOOLEAN): 중요도 표시 여부
-   **관계**:
    -   `webUser` (N:1, `web_user_id`): 해당 할 일을 소유한 사용자와의 관계
    -   `category` (N:1, `category_id`): 해당 할 일이 속한 카테고리와의 관계

## 4. API 명세

### 4.1. 사용자 API (`WebUserApiController`)

-   **Base URL**: `/api/todos`

| 기능 | HTTP Method | URL | 요청 본문 (Request Body) | 응답 |
| --- | --- | --- | --- | --- |
| 회원가입 | `POST` | `/signup` | `WebUserDto` (username, password, email) | `WebUser` 객체 (성공 시), 400 Bad Request (실패 시) |
| 로그인 | `POST` | `/login` | `WebUserDto` (username, password) | JWT 토큰 (성공 시), 401 Unauthorized (실패 시) |
| 로그아웃 | `POST` | `/logout` | 없음 | "로그아웃 성공" (성공 시), 400 Bad Request (실패 시) |

### 4.2. 할 일 API (`TodoApiController`)

-   **Base URL**: `/api/todos`

| 기능 | HTTP Method | URL | 요청 본문 (Request Body) | 응답 |
| --- | --- | --- | --- | --- |
| 전체 할 일 조회 | `GET` | `/index` | 없음 | `List<Todo>` |
| 할 일 추가 | `POST` | `/index/task` | `TodoDto` | `Todo` 객체 (성공 시), 400 Bad Request (실패 시) |
| 오늘 할 일 추가 | `POST` | `/today/task` | `TodoDto` | `Todo` 객체 (성공 시), 400 Bad Request (실패 시) |
| 중요 할 일 추가 | `POST` | `/favorite/task` | `TodoDto` | `Todo` 객체 (성공 시), 400 Bad Request (실패 시) |
| 상태 변경 | `POST` | `/index/updateStatus/{id}` | `{"status": "준비" or "완료"}` | 변경된 상태 문자열 (성공 시), 400 Bad Request (실패 시) |
| 중요도 변경 | `PATCH` | `/updateFavorite/{id}` | 없음 | `Todo` 객체 (성공 시), 400 Bad Request (실패 시) |
| 할 일 수정 | `PATCH` | `/index/task/{id}` | `TodoDto` | `TodoDto` (성공 시) |
| 할 일 삭제 | `DELETE` | `/index/task/{id}` | 없음 | `TodoDto` (삭제된 할 일 정보) |

### 4.3. 카테고리 API (`CategoryApiController`)

-   **Base URL**: `/api/todos/index`

| 기능 | HTTP Method | URL | 요청 본문 (Request Body) | 응답 |
| --- | --- | --- | --- | --- |
| 카테고리 추가 | `POST` | `/category` | `CategoryDto` (name) | `Category` 객체 (성공 시), 400 Bad Request (실패 시) |
| 카테고리 수정 | `PATCH` | `/category/{id}` | `CategoryDto` (name) | `CategoryDto` (성공 시) |
| 카테고리 삭제 | `DELETE` | `/category/{id}` | 없음 | `CategoryDto` (삭제된 카테고리 정보) |
| 전체 카테고리 삭제 | `DELETE` | `/category` | 없음 | `List<CategoryDto>` (삭제된 카테고리 목록) |

## 5. 화면 라우팅 (`TodoController`, `MyPageController`)

-   **Base URL**: `/todos`

| 경로 | 설명 | 템플릿 파일 |
| --- | --- | --- |
| `/start` | 시작 페이지 | `todos/start.mustache` |
| `/login` | 로그인 페이지 | `todos/login.mustache` |
| `/signup` | 회원가입 페이지 | `todos/signup.mustache` |
| `/index` | 전체 할 일 목록 페이지 | `todos/index.mustache` |
| `/today` | 오늘 할 일 목록 페이지 | `todos/today.mustache` |
| `/favorite` | 중요 할 일 목록 페이지 | `todos/favorite.mustache` |
| `/schedule` | 예정된 할 일 페이지 | `todos/schedule.mustache` |
| `/mypage` | 마이페이지 | `todos/mypage.mustache` |

## 6. 핵심 로직 (Service)

### 6.1. `WebUserService`

-   **`signup(WebUserDto dto)`**: 사용자 이름과 이메일 중복을 확인하고, 비밀번호를 암호화하여 새로운 사용자를 등록합니다.
-   **`login(WebUserDto dto)`**: 사용자 정보와 비밀번호 일치 여부를 확인하고, 성공 시 JWT 토큰을 생성하여 반환합니다.
-   **`logout()`**: 클라이언트의 JWT 쿠키를 무효화하는 `ResponseCookie`를 생성하여 반환합니다.

### 6.2. `TodoService`

-   **`addTask(TodoDto dto)`**: `TodoDto`를 `Todo` 엔티티로 변환하여 저장합니다. 카테고리가 지정되지 않은 경우 기본 "작업" 카테고리에 할당합니다.
-   **`updateStatus(Long id, String newStatus)`**: 특정 할 일의 상태를 '준비' 또는 '완료'로 변경합니다.
-   **`editTask(Long id, TodoDto dto)`**: 특정 할 일의 제목, 카테고리, 마감일 등을 수정합니다.
-   **`deleteTask(Long id)`**: 특정 할 일을 삭제합니다.
-   **`index(String status)`**, **`today(String status)`**, **`favorite(String status)`**: 각 페이지의 요구사항에 맞게 상태별, 날짜별, 중요도별로 할 일 목록을 조회합니다.

### 6.3. `CategoryService`

-   **`categoryAdd(CategoryDto dto)`**: 새로운 카테고리를 추가합니다. 카테고리 이름 중복을 방지합니다.
-   **`categoryEdit(Long id, CategoryDto dto)`**: 카테고리 이름을 수정합니다.
-   **`categoryDelete(Long id)`**: 특정 카테고리와 해당 카테고리에 속한 모든 할 일을 함께 삭제합니다.
-   **`categoryDeleteAll()`**: 현재 로그인한 사용자의 모든 카테고리와 할 일을 삭제합니다.

### 6.4. `MyPageService`

-   **`getMyPageInfo()`**: 현재 사용자의 할 일 진행률(전체 할 일 대비 완료된 할 일의 비율)을 계산하고, 전체 사용자 중에서의 순위를 매겨 `MyPageDto`로 반환합니다. 상위 10명의 사용자 정보도 함께 제공합니다.

## 7. 인증 및 보안

-   **인증 방식**: JWT(JSON Web Token)를 사용한 토큰 기반 인증
-   **`SecurityConfig`**:
    -   `/api/todos/signup`, `/api/todos/login`, `/todos/start`, `/todos/login`, `/todos/signup` 등 특정 경로를 제외한 모든 요청은 인증을 요구하도록 설정되어 있습니다.
    -   `PasswordEncoder`로 `BCryptPasswordEncoder`를 사용하여 비밀번호를 안전하게 암호화합니다.
-   **`JwtUtil`**:
    -   JWT 토큰의 생성, 검증, 정보 추출을 담당합니다.
    -   토큰 생성 시 사용자 이름(`username`)과 ID(`userId`)를 클레임에 포함합니다.
-   **`JwtAuthenticationFilter`**:
    -   모든 요청에 대해 실행되는 필터입니다.
    -   요청 헤더의 `Authorization` 또는 쿠키의 `jwtToken`에서 JWT 토큰을 추출하여 유효성을 검증합니다.
    -   토큰이 유효하면 `SecurityContextHolder`에 인증 정보를 저장하여 해당 요청이 인증된 사용자의 요청임을 명시합니다.
-   **`UserSecurityService`**:
    -   Spring Security의 `UserDetailsService`를 구현하여, 사용자 이름으로 `UserDetails` 객체를 로드하는 역할을 합니다.

## 8. 빌드 및 실행

1.  **MySQL 데이터베이스 설정**: `src/main/resources/application.properties` 파일에 자신의 MySQL 데이터베이스 연결 정보를 입력합니다.

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/네-데이터베이스-이름
    spring.datasource.username=네-사용자-이름
    spring.datasource.password=네-비밀번호
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=update
    ```

2.  **JWT 비밀 키 설정**: 같은 `application.properties` 파일에 JWT 서명에 사용할 비밀 키와 만료 시간을 설정합니다.

    ```properties
    jwt.secret=네-jwt-비밀키
    jwt.expirationMs=86400000 # 24시간
    ```

3.  **프로젝트 빌드**: 프로젝트 루트 디렉토리에서 다음 명령어를 실행합니다.

    ```bash
    ./gradlew build
    ```

4.  **애플리케이션 실행**: 빌드가 성공하면 다음 명령어로 애플리케이션을 실행할 수 있습니다.

    ```bash
    java -jar build/libs/todo-list-0.0.1-SNAPSHOT.jar
    ```

이제 웹 브라우저에서 `http://localhost:8080/todos/start`로 접속하여 애플리케이션을 사용할 수 있습니다.
