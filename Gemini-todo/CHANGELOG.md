# 변경사항 기록

## 2025-07-24

### 1. 회원가입(Signup) 403 Forbidden 에러 해결

-   **문제**: 회원가입 (`/api/todos/signup`) 시 403 Forbidden 에러가 발생했습니다. `SecurityConfig.java`에서 해당 경로가 `permitAll()`로 설정되어 있었음에도 불구하고 에러가 발생하여, `JwtAuthenticationFilter`가 불필요하게 실행되면서 문제가 발생한 것으로 추정되었습니다.
-   **해결**: `src/main/java/com/example/geminitodo/filter/JwtAuthenticationFilter.java` 파일에 `shouldNotFilter` 메서드를 추가하여, `/api/todos/signup` 및 `/api/todos/login` 경로에 대해서는 `JwtAuthenticationFilter`가 동작하지 않도록 수정했습니다. 이를 통해 인증이 필요 없는 경로에 대한 불필요한 필터링을 방지하고 403 에러를 해결했습니다.

### 2. 회원가입 후 리다이렉션 문제 해결

-   **문제**: 회원가입 성공 후에도 `/api/todos/signup` 엔드포인트에 머물러 있었고, 메인 화면으로 리다이렉션되지 않았습니다. 이는 `signup.mustache`의 HTML 폼이 `@RestController`인 `WebUserApiController`의 `/api/todos/signup`으로 직접 제출되었기 때문입니다. `@RestController`는 주로 JSON/XML 데이터를 반환하며, HTML 폼 제출 후 페이지 리다이렉션을 처리하는 역할에는 적합하지 않습니다.
-   **해결**: 
    1.  `src/main/java/com/example/geminitodo/controller/TodoController.java`에 `WebUserService`를 주입하고, `@PostMapping("/signup")` 메서드를 추가하여 회원가입 폼 제출을 처리하도록 변경했습니다. 이 메서드는 `webUserService.signup()`을 호출한 후, 성공 시 `/todos/login` 페이지로 리다이렉션합니다.
    2.  `src/main/java/com/example/geminitodo/controller/WebUserApiController.java`에서 기존의 `signup` 메서드를 제거했습니다. 이제 `WebUserApiController`는 순수하게 API 요청만 처리합니다.

### 3. 모든 엔드포인트 보안 잠금 일시 해제

-   **문제**: 회원가입 및 기타 기능 테스트 중 지속적인 오류가 발생하여 디버깅을 위해 모든 엔드포인트에 대한 보안 잠금 해제가 필요했습니다.
-   **해결**: `src/main/java/com/example/geminitodo/config/SecurityConfig.java` 파일의 `securityFilterChain` 빈에서 `authorizeHttpRequests` 설정을 `auth.anyRequest().permitAll()`로 변경하여 모든 요청에 대해 인증 없이 접근을 허용하도록 수정했습니다.

### 4. 할 일(Todo) 추가 기능 UI 및 로직 구현

-   **문제**: 백엔드 API는 존재했으나, `index.mustache` 템플릿에 할 일 추가를 위한 UI 요소(폼)와 이를 처리하는 클라이언트 측 JavaScript 로직이 없어 사용자가 할 일을 추가할 수 없었습니다.
-   **해결**: `src/main/resources/templates/todos/index.mustache` 파일에 다음을 추가했습니다.
    -   새로운 할 일 항목을 입력하고 제출하기 위한 HTML 폼 (`id="addTodoForm"`).
    -   할 일 목록을 동적으로 표시할 `<ul>` 요소 (`id="todoList"`).
    -   폼 제출을 AJAX로 처리하고, 할 일 목록을 비동기적으로 불러와 업데이트하며, 할 일의 상태 변경 및 삭제 기능을 포함하는 JavaScript 코드.
    -   카테고리를 동적으로 불러와 선택할 수 있도록 하는 로직 추가.

### 5. 오늘 할 일(Today's Todo) 페이지 UI 및 로직 구현

-   **문제**: `today.mustache` 파일이 비어있어 오늘 할 일 목록을 표시하거나 추가할 수 없었습니다.
-   **해결**: `src/main/resources/templates/todos/today.mustache` 파일에 `index.mustache`와 유사한 구조의 HTML 폼과 할 일 목록을 추가했습니다. JavaScript를 통해 `/api/todos/today` 엔드포인트에서 오늘 할 일 목록을 가져오고, `/api/todos/today/task` 엔드포인트를 통해 오늘 할 일을 추가할 수 있도록 구현했습니다. 마감일은 자동으로 오늘 날짜로 설정됩니다.

### 6. 중요 할 일(Favorite Todo) 페이지 UI 및 로직 구현

-   **문제**: `favorite.mustache` 파일이 비어있어 중요 할 일 목록을 표시하거나 추가할 수 없었습니다.
-   **해결**: `src/main/resources/templates/todos/favorite.mustache` 파일에 `index.mustache`와 유사한 구조의 HTML 폼과 할 일 목록을 추가했습니다. JavaScript를 통해 `/api/todos/favorite` 엔드포인트에서 중요 할 일 목록을 가져오고, `/api/todos/favorite/task` 엔드포인트를 통해 중요 할 일을 추가할 수 있도록 구현했습니다. 중요(favorite) 체크박스는 기본적으로 체크되어 있으며 비활성화됩니다.

### 7. 예정된 할 일(Scheduled Todo) 페이지 UI 및 로직 구현

-   **문제**: `schedule.mustache` 파일이 비어있어 예정된 할 일 목록을 표시할 수 없었습니다.
-   **해결**: `src/main/resources/templates/todos/schedule.mustache` 파일에 모든 할 일 목록을 조회하여 표시하는 HTML 구조와 JavaScript 코드를 추가했습니다. 이 페이지는 `/api/todos/index` 엔드포인트를 사용하여 모든 할 일을 가져와 표시합니다.

### 8. 마이페이지(My Page) UI 구현

-   **문제**: `mypage.mustache` 파일이 비어있어 마이페이지 정보를 표시할 수 없었습니다.
-   **해결**: `src/main/resources/templates/todos/mypage.mustache` 파일에 사용자의 할 일 진행률, 개인 랭킹, 상위 10명 사용자 랭킹을 표시하는 HTML 구조를 추가했습니다. 이 정보는 `TodoController`에서 `MyPageDto` 객체를 통해 모델에 전달됩니다.

### 9. 로그인(Login) 페이지 UI 및 로직 구현

-   **문제**: `login.mustache` 파일이 비어있어 사용자 로그인 기능을 제공할 수 없었습니다.
-   **해결**: `src/main/resources/templates/todos/login.mustache` 파일에 사용자 로그인 폼을 포함하는 HTML 구조와 AJAX를 통해 `/api/todos/login` 엔드포인트로 로그인 요청을 보내는 JavaScript 코드를 추가했습니다. 로그인 성공 시 `/todos/index` 페이지로 리다이렉션됩니다.

### 10. 회원가입(Signup) 페이지 UI 개선

-   **문제**: `signup.mustache` 파일이 기본적인 HTML 폼만 가지고 있어 다른 페이지들과의 일관성이 부족하고, 회원가입 실패 시 사용자에게 에러 메시지를 명확하게 전달하기 어려웠습니다.
-   **해결**: `src/main/resources/templates/todos/signup.mustache` 파일에 다른 페이지들과 일관된 스타일을 적용하고, 회원가입 실패 시 URL 파라미터로부터 에러 메시지를 받아 표시할 수 있도록 JavaScript 로직을 추가했습니다. 또한 폼의 `action`을 `TodoController`에서 처리하도록 `/todos/signup`으로 변경했습니다.

### 11. 시작(Start) 페이지 UI 구현

-   **문제**: `start.mustache` 파일이 비어있어 애플리케이션의 시작 페이지를 제공할 수 없었습니다.
-   **해결**: `src/main/resources/templates/todos/start.mustache` 파일에 애플리케이션의 시작 페이지를 위한 HTML 구조를 추가했습니다. 이 페이지는 로그인 및 회원가입 페이지로 이동하는 링크를 제공합니다.
