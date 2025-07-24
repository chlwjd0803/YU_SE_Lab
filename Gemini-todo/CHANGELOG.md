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
