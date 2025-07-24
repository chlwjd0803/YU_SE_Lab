# 백엔드 시스템 명세서

## 1. 서론

본 문서는 입국 심사 과정을 시뮬레이션하고 관리하기 위한 백엔드 시스템의 상세 명세서입니다. 여권 및 비자 정보 관리, 그리고 OpenAI API를 활용한 AI 기반 입국 심사 챗봇 기능을 제공합니다. 개발 담당자가 시스템의 의도를 정확히 이해하고 일관된 방식으로 개발을 진행할 수 있도록 상세히 기술합니다.

## 2. 전체 아키텍처

본 백엔드 시스템은 Spring Boot 기반의 계층형 아키텍처를 따릅니다.

*   **Controller (컨트롤러)**: 클라이언트의 HTTP 요청을 받아 적절한 서비스 계층으로 전달하고, 서비스 계층의 처리 결과를 HTTP 응답으로 반환합니다. 주로 `@RestController` 어노테이션을 사용하여 RESTful API를 제공하며, `@Controller`를 사용하여 View를 반환하는 역할도 수행합니다.
*   **Service (서비스)**: 비즈니스 로직을 처리하는 핵심 계층입니다. 컨트롤러로부터 전달받은 데이터를 가공하고, 여러 리포지토리를 조합하여 복잡한 비즈니스 요구사항을 처리합니다. `@Service` 어노테이션을 사용하며, `@Transactional`을 통해 트랜잭션 관리를 수행합니다.
*   **Repository (리포지토리)**: 데이터베이스와의 상호작용을 담당합니다. Spring Data JPA의 `JpaRepository`를 상속받아 기본적인 CRUD(Create, Read, Update, Delete) 기능을 제공하며, 필요에 따라 커스텀 쿼리 메서드를 정의합니다.
*   **Entity (엔티티)**: 데이터베이스 테이블과 매핑되는 객체입니다. `@Entity` 어노테이션을 사용하며, 테이블의 스키마를 정의하고 데이터의 영속성을 관리합니다.
*   **DTO (Data Transfer Object)**: 계층 간 데이터 전송을 위한 객체입니다. 엔티티의 모든 정보를 노출하지 않고, 필요한 데이터만을 선택적으로 클라이언트에게 전달하거나 클라이언트로부터 데이터를 받을 때 사용됩니다.

## 3. API 엔드포인트

### 3.1. ImmigrationController (API)

입국 심사 챗봇 관련 API를 제공합니다.

*   **Base URL**: `/api/v1/immigration`

#### 3.1.1. `POST /chat`

AI 기반 입국 심사 챗봇과의 대화를 처리합니다. 세션 기반으로 대화 이력을 관리합니다.

*   **설명**: 사용자의 메시지를 받아 OpenAI API를 호출하고, AI의 응답을 반환합니다. 입국 심사관 역할을 하는 AI 챗봇과의 대화를 통해 입국 승인/반려를 결정합니다.
*   **요청 (Request Body)**:
    ```json
    {
        "message": "유저가 보낸 문장"
    }
    ```
*   **응답 (Response Body)**:
    *   **성공 (200 OK)**:
        *   대화가 진행 중일 경우: OpenAI API의 응답 DTO (`OpenAiResDto`)
        *   입국 심사가 종료될 경우 (AI가 `approve` 또는 `reject` 액션을 반환):
            ```json
            {
                "action": "approve" | "reject",
                "reason": "20자 이내 사유"
            }
            ```
*   **주요 로직**:
    *   세션에서 `OpenAiReqDto`를 가져와 대화 이력을 관리합니다.
    *   최초 호출 시, 시스템 프롬프트(입국 심사관 역할, 여권/비자 정보, 현재 날짜, 종료 조건)를 설정합니다.
    *   사용자 메시지를 대화 이력에 추가합니다.
    *   `ImmigrationService`를 통해 OpenAI API를 호출합니다.
    *   AI의 응답을 대화 이력에 추가하고 세션에 저장합니다.
    *   AI 응답에 "action" 필드가 포함되어 있으면 입국 심사 종료로 간주하고 세션을 무효화하며, 해당 JSON 응답을 반환합니다.
*   **오류**:
    *   `IllegalArgumentException`: 세션에 여권 정보가 없는 경우.

### 3.2. PassportController (API)

여권 정보 관리 API를 제공합니다.

*   **Base URL**: `/api/v1/passport`

#### 3.2.1. `GET /{passportNo}`

특정 여권 번호에 해당하는 여권 정보를 조회합니다.

*   **설명**: 주어진 여권 번호로 여권 정보를 검색하여 반환합니다.
*   **경로 변수**: `passportNo` (String) - 조회할 여권 번호
*   **응답 (Response Body)**:
    *   **성공 (200 OK)**: `PassportResDto` (조회된 여권 정보)
    *   **실패 (404 Not Found)**: "찾을 수 없습니다. 여권 번호를 확인해주세요."
*   **주요 로직**: `PassportService.getPassport(passportNo)` 호출.

#### 3.2.2. `GET /`

모든 여권 정보를 조회합니다.

*   **설명**: 시스템에 등록된 모든 여권 정보를 리스트 형태로 반환합니다.
*   **응답 (Response Body)**:
    *   **성공 (200 OK)**: `List<PassportResDto>` (모든 여권 정보 리스트)
*   **주요 로직**: `PassportService.getAllPassport()` 호출.

#### 3.2.3. `POST /`

새로운 여권 정보를 생성합니다.

*   **설명**: 요청 바디에 포함된 정보로 새로운 여권 레코드를 생성합니다.
*   **요청 (Request Body)**: `PassportReqDto` (생성할 여권 정보)
*   **응답 (Response Body)**:
    *   **성공 (201 Created)**: Location 헤더에 생성된 여권의 URI 포함.
    *   **실패 (400 Bad Request)**: "입력 형식이 잘못되었습니다. 확인 바랍니다."
*   **주요 로직**: `PassportService.makePassport(passportReqDto)` 호출. 이미 존재하는 여권 번호이거나 유효하지 않은 국가 코드인 경우 생성 실패.

#### 3.2.4. `PATCH /{passportNo}`

특정 여권 정보를 수정합니다.

*   **설명**: 주어진 여권 번호에 해당하는 여권 정보를 요청 바디의 내용으로 부분적으로 수정합니다.
*   **경로 변수**: `passportNo` (String) - 수정할 여권 번호
*   **요청 (Request Body)**: `PassportReqDto` (수정할 여권 정보)
*   **응답 (Response Body)**:
    *   **성공 (200 OK)**: 빈 응답 바디.
    *   **실패 (400 Bad Request)**: "수정 정보 입력을 정확히 입력해주세요."
*   **주요 로직**: `PassportService.updatePassport(passportNo, passportReqDto)` 호출. 여권 번호가 존재하지 않거나 국가 코드가 유효하지 않으면 실패.

#### 3.2.5. `DELETE /{passportNo}`

특정 여권 정보를 삭제합니다. 해당 여권에 연결된 모든 비자 정보도 함께 삭제됩니다.

*   **설명**: 주어진 여권 번호에 해당하는 여권 레코드와 관련된 모든 비자 레코드를 삭제합니다.
*   **경로 변수**: `passportNo` (String) - 삭제할 여권 번호
*   **응답 (Response Body)**:
    *   **성공 (200 OK)**: 빈 응답 바디.
    *   **실패 (404 Not Found)**: "찾을 수 없습니다. 여권 번호를 확인해주세요."
*   **주요 로직**: `PassportService.deletePassport(passportNo)` 호출.

#### 3.2.6. `POST /verify`

입국 심사 전 여권 인증을 수행합니다.

*   **설명**: 입국 심사 시작 전, 사용자가 입력한 여권 정보의 유효성을 검증합니다. 성공 시 세션에 여권 정보를 저장합니다.
*   **요청 (Request Body)**: `PassportVerifyDto` (인증할 여권 정보)
*   **응답 (Response Body)**:
    *   **성공 (200 OK)**: 빈 응답 바디.
    *   **실패 (401 Unauthorized)**: "여권 인증이 실패하였습니다."
*   **주요 로직**: `PassportService.verifyPassport(dto)` 호출.

### 3.3. VisaController (API)

비자 정보 관리 API를 제공합니다.

*   **Base URL**: `/api/v1/visa`

#### 3.3.1. `GET /{passportNo}`

특정 여권 번호에 해당하는 비자 정보를 조회합니다.

*   **설명**: 주어진 여권 번호로 발급된 모든 비자 정보를 리스트 형태로 반환합니다.
*   **경로 변수**: `passportNo` (String) - 조회할 여권 번호
*   **응답 (Response Body)**:
    *   **성공 (200 OK)**: `List<VisaResDto>` (조회된 비자 정보 리스트)
    *   **실패 (404 Not Found)**: "여권 번호가 존재하지 않습니다."
*   **주요 로직**: `VisaService.getVisa(passportNo)` 호출.

#### 3.3.2. `POST /`

새로운 비자 정보를 생성합니다.

*   **설명**: 요청 바디에 포함된 정보로 새로운 비자 레코드를 생성합니다.
*   **요청 (Request Body)**: `VisaReqDto` (생성할 비자 정보)
*   **응답 (Response Body)**:
    *   **성공 (201 Created)**: Location 헤더에 생성된 비자의 URI 포함.
    *   **실패 (400 Bad Request)**: "입력 형식이 잘못되었습니다. 확인 바랍니다."
*   **주요 로직**: `VisaService.makeVisa(visaReqDto)` 호출. 여권이 존재하지 않거나, 국가가 존재하지 않거나, 이미 해당 여권에 대해 해당 국가의 비자가 발급된 경우 생성 실패.

#### 3.3.3. `DELETE /{passportNo}/{countryCode}`

특정 여권 번호와 국가 코드에 해당하는 비자 정보를 삭제합니다.

*   **설명**: 주어진 여권 번호와 국가 코드에 해당하는 비자 레코드를 삭제합니다.
*   **경로 변수**:
    *   `passportNo` (String) - 비자가 연결된 여권 번호
    *   `countryCode` (String) - 비자가 발급된 국가 코드
*   **응답 (Response Body)**:
    *   **성공 (200 OK)**: 빈 응답 바디.
    *   **실패 (404 Not Found)**: "여권번호가 존재하지 않거나, 삭제할 비자가 없습니다."
*   **주요 로직**: `VisaService.deleteVisa(passportNo, countryCode)` 호출.

### 3.4. ViewController (View)

HTML 템플릿을 반환하는 컨트롤러입니다.

*   **Base URL**: `/`

#### 3.4.1. `GET /`

메인 페이지를 반환합니다.

*   **설명**: `index.html` 템플릿을 반환합니다.

#### 3.4.2. `GET /passport`

여권 관리 페이지를 반환합니다.

*   **설명**: `passport.html` 템플릿을 반환합니다.

#### 3.4.3. `GET /visa`

비자 관리 페이지를 반환합니다.

*   **설명**: `visa.html` 템플릿을 반환합니다.

#### 3.4.4. `GET /immigration`

입국 심사 페이지를 반환합니다.

*   **설명**: `immigration.html` 템플릿을 반환합니다.

## 4. 데이터 모델 (엔티티)

### 4.1. `Country`

국가 정보를 나타내는 엔티티입니다.

*   **필드**:
    *   `id` (Long): Primary Key, 자동 생성.
    *   `code` (String): 국가 코드 (예: "KR", "US"), Not Null, Unique.
    *   `name` (String): 국가 이름 (예: "대한민국", "미국"), Not Null, Unique.

### 4.2. `Passport`

여권 정보를 나타내는 엔티티입니다.

*   **필드**:
    *   `id` (Long): Primary Key, 자동 생성.
    *   `country` (Country): `Country` 엔티티와의 Many-to-One 관계. 여권 발급 국가.
    *   `passportNo` (String): 여권 번호 (예: "BL13AB1234"), Not Null, Unique.
    *   `fullName` (String): 여권 소지자 전체 이름, Not Null.
    *   `birthDate` (LocalDate): 생년월일, Not Null.
    *   `issueDate` (LocalDate): 발급일 (생성 시점의 날짜로 자동 설정), Not Null.
    *   `expiryDate` (LocalDate): 만료일 (발급일로부터 10년 후로 자동 설정), Not Null.
*   **메서드**:
    *   `toDto()`: `PassportResDto`로 변환.
    *   `patch(PassportReqDto)`: `passportNo`와 `fullName` 필드를 부분적으로 업데이트. `passportNo`는 "BL13[A-Z]{2}[0-9]{4}" 정규식에 맞지 않으면 업데이트되지 않음.

### 4.3. `Visa`

비자 정보를 나타내는 엔티티입니다.

*   **필드**:
    *   `id` (Long): Primary Key, 자동 생성.
    *   `passport` (Passport): `Passport` 엔티티와의 Many-to-One 관계. 비자가 발급된 여권.
    *   `country` (Country): `Country` 엔티티와의 Many-to-One 관계. 비자가 발급된 국가.
    *   `startDate` (LocalDate): 비자 유효 시작일 (생성 시점의 날짜로 자동 설정), Not Null.
    *   `endDate` (LocalDate): 비자 유효 종료일 (시작일로부터 1년 후로 자동 설정), Not Null.
*   **메서드**:
    *   `toDto()`: `VisaResDto`로 변환.

### 4.4. `ChatMessage`

OpenAI API와의 통신을 위한 메시지 객체입니다. 엔티티로 영속화되지 않습니다.

*   **필드**:
    *   `role` (String): 메시지 발신자 역할 (예: "system", "user", "assistant").
    *   `content` (String): 메시지 내용.

## 5. 서비스 로직

### 5.1. `ImmigrationService`

OpenAI API와의 통신을 담당합니다.

*   `getChatResponse(List<OpenAiReqDto.Message> history)`:
    *   주어진 대화 이력을 바탕으로 OpenAI API에 요청을 보냅니다.
    *   `application.yml`에 설정된 OpenAI API 키를 사용합니다.
    *   `gpt-4o-mini` 모델을 사용합니다.
    *   응답이 비어있거나 잘못된 경우 `IllegalArgumentException`을 발생시킵니다.

### 5.2. `PassportService`

여권 관련 비즈니스 로직을 처리합니다.

*   `getPassport(String passportNo)`: 특정 여권 번호로 여권 정보를 조회합니다. `Optional`을 사용하여 `null` 체크를 강제합니다.
*   `getAllPassport()`: 모든 여권 정보를 조회하여 `PassportResDto` 리스트로 변환하여 반환합니다. 엔티티 직접 노출을 방지합니다.
*   `makePassport(PassportReqDto passportReqDto)`:
    *   여권 번호 중복 및 유효하지 않은 국가 코드 여부를 검사합니다.
    *   `PassportReqDto`를 `Passport` 엔티티로 변환하고, `Country` 엔티티와 연결합니다.
    *   유효성 검사 실패 시 `null`을 반환합니다.
*   `deletePassport(String passportNo)`:
    *   해당 여권에 연결된 모든 비자 정보를 먼저 삭제한 후, 여권 정보를 삭제합니다. `@Transactional` 어노테이션으로 트랜잭션을 관리합니다.
*   `updatePassport(String passportNo, PassportReqDto passportReqDto)`:
    *   여권 번호가 존재하지 않으면 `EntityNotFoundException`을 발생시킵니다.
    *   국가 코드가 유효하지 않으면 `IllegalArgumentException`을 발생시킵니다.
    *   `Passport` 엔티티의 `patch` 메서드를 사용하여 정보를 업데이트하고 저장합니다. `@Transactional` 어노테이션으로 트랜잭션을 관리합니다.
*   `verifyPassport(PassportVerifyDto dto)`: 주어진 여권 번호가 데이터베이스에 존재하는지 확인합니다.

### 5.3. `VisaService`

비자 관련 비즈니스 로직을 처리합니다.

*   `getVisa(String passportNo)`: 특정 여권 번호에 해당하는 모든 비자 정보를 조회하여 `VisaResDto` 리스트로 변환하여 반환합니다. 여권이 존재하지 않으면 `null`을 반환합니다.
*   `getVisaByPassportAndCountry(String passportNo, String countryCode)`: 특정 여권 번호와 국가 코드에 해당하는 단일 비자 정보를 조회합니다. 여권이나 국가가 존재하지 않으면 `null`을 반환합니다.
*   `makeVisa(VisaReqDto visaReqDto)`:
    *   여권 및 국가 존재 여부를 확인합니다.
    *   해당 여권에 대해 이미 해당 국가의 비자가 발급되었는지 확인합니다.
    *   `VisaReqDto`를 `Visa` 엔티티로 변환하고, `Passport` 및 `Country` 엔티티와 연결합니다.
    *   유효성 검사 실패 시 `null`을 반환합니다.
*   `deleteVisa(String passportNo, String countryCode)`: 특정 여권 번호와 국가 코드에 해당하는 비자 정보를 삭제합니다. `@Transactional` 어노테이션으로 트랜잭션을 관리합니다.

## 6. 데이터베이스 상호작용 (리포지토리)

Spring Data JPA의 `JpaRepository`를 활용하여 데이터베이스와 상호작용합니다.

*   `CountryRepository`: `Country` 엔티티에 대한 CRUD 및 `findByCode(String code)` 메서드를 제공합니다.
*   `PassportRepository`: `Passport` 엔티티에 대한 CRUD 및 `findByPassportNo(String passportNo)` 메서드를 제공합니다.
*   `VisaRepository`: `Visa` 엔티티에 대한 CRUD 및 `findByPassport(Passport passport)`, `findByPassportAndCountry(Passport passport, Country country)` 메서드를 제공합니다.

## 7. DTO (Data Transfer Object)

각 DTO는 특정 목적을 위해 데이터를 캡슐화합니다.

*   `CountryDto`: 국가 코드와 이름을 포함. 엔티티를 클라이언트에 노출하지 않고 필요한 정보만 전달.
*   `OpenAiReqDto`: OpenAI API 요청을 위한 DTO. 모델명과 메시지 리스트 포함.
*   `OpenAiResDto`: OpenAI API 응답을 위한 DTO. AI의 메시지 내용 포함.
*   `PassportReqDto`: 여권 생성 및 수정 요청을 위한 DTO. `toEntity()` 메서드를 통해 `Passport` 엔티티로 변환 가능.
*   `PassportResDto`: 여권 조회 응답을 위한 DTO. 여권의 주요 정보를 포함.
*   `PassportVerifyDto`: 여권 인증 요청을 위한 DTO. 여권 번호만 포함.
*   `VisaReqDto`: 비자 생성 요청을 위한 DTO. `toEntity()` 메서드를 통해 `Visa` 엔티티로 변환 가능.
*   `VisaResDto`: 비자 조회 응답을 위한 DTO. 비자의 주요 정보를 포함.

## 8. 주요 기능/흐름

### 8.1. 여권 및 비자 관리

*   **생성**: `PassportController`의 `POST /api/v1/passport` 및 `VisaController`의 `POST /api/v1/visa`를 통해 각각 여권과 비자를 생성합니다. 유효성 검사(중복 여권 번호, 유효하지 않은 국가 코드, 이미 발급된 비자 등)가 수행됩니다.
*   **조회**:
    *   개별 여권: `PassportController`의 `GET /api/v1/passport/{passportNo}`
    *   모든 여권: `PassportController`의 `GET /api/v1/passport`
    *   여권별 비자: `VisaController`의 `GET /api/v1/visa/{passportNo}`
*   **수정**: `PassportController`의 `PATCH /api/v1/passport/{passportNo}`를 통해 여권 정보를 수정합니다.
*   **삭제**: `PassportController`의 `DELETE /api/v1/passport/{passportNo}`를 통해 여권을 삭제하면, 해당 여권에 연결된 모든 비자도 함께 삭제됩니다. `VisaController`의 `DELETE /api/v1/visa/{passportNo}/{countryCode}`를 통해 특정 비자를 삭제할 수 있습니다.

### 8.2. AI 기반 입국 심사 챗봇

1.  **여권 인증**: 사용자는 `PassportController`의 `POST /api/v1/passport/verify`를 통해 여권 정보를 인증합니다. 성공 시 해당 여권 정보가 세션에 저장됩니다.
2.  **심사 시작**: `ImmigrationController`의 `POST /api/v1/immigration/chat` 엔드포인트로 첫 메시지를 보냅니다.
3.  **시스템 프롬프트 초기화**: 백엔드는 세션에 저장된 여권 및 비자 정보를 바탕으로 AI에게 "입국 심사관" 역할을 부여하고, 현재 날짜, 그리고 심사 종료 조건(JSON 형식의 `{"action": "approve"|"reject", "reason": "..."}`)을 포함하는 시스템 프롬프트를 구성합니다.
4.  **대화 진행**: 사용자와 AI 간의 메시지가 `ImmigrationController`를 통해 OpenAI API로 전달되고, 응답이 다시 사용자에게 전달됩니다. 모든 대화 이력은 세션에 누적됩니다.
5.  **심사 종료**: AI가 응답으로 종료 조건을 포함하는 JSON(`{"action": "approve"|"reject", "reason": "..."}`)을 반환하면, 백엔드는 이를 감지하여 세션을 무효화하고 심사 결과를 클라이언트에 전달합니다.

## 9. 오류 처리

*   **HTTP 상태 코드**: 각 API 엔드포인트는 요청 처리 결과에 따라 적절한 HTTP 상태 코드(예: 200 OK, 201 Created, 400 Bad Request, 401 Unauthorized, 404 Not Found)를 반환합니다.
*   **응답 바디 메시지**: 오류 발생 시, 클라이언트가 이해할 수 있는 명확한 오류 메시지를 응답 바디에 포함하여 반환합니다.
*   **예외 처리**:
    *   `IllegalArgumentException`: 유효하지 않은 인자나 상태(예: 세션 정보 없음, 잘못된 응답)에 대해 발생합니다.
    *   `EntityNotFoundException`: 데이터베이스에서 엔티티를 찾을 수 없을 때 발생합니다.

## 10. 향후 고려사항/개선점

*   **VisaService 메서드 이름 수정**: `getVisa(String passportNo)`와 `getVisaByPassportAndCountry(String passportNo, String countryCode)`의 메서드 이름이 명확하지 않을 수 있습니다. 더 직관적인 이름으로 변경을 고려해야 합니다.
*   **VisaService 중복 코드 수정**: `getVisaByPassportAndCountry` 메서드 내에 여권 및 국가 조회 로직이 중복되어 있습니다. 공통 메서드로 분리하여 코드 중복을 줄일 수 있습니다.
*   **비자 전체 조회 기능**: 현재 비자 전체 조회 기능은 구현되어 있지 않습니다. 필요에 따라 `VisaController`에 `GET /` 엔드포인트를 추가하여 모든 비자를 조회하는 기능을 구현할 수 있습니다.
*   **비자 수정 기능**: 현재 비자 수정 기능은 구현되어 있지 않습니다. 필요에 따라 `VisaController`에 `PATCH` 또는 `PUT` 엔드포인트를 추가하여 비자 정보를 수정하는 기능을 구현할 수 있습니다.
*   **여권 번호 정규식**: `PassportReqDto`의 `toEntity()` 및 `Passport` 엔티티의 `patch()` 메서드에 하드코딩된 여권 번호 정규식(`BL13[A-Z]{2}[0-9]{4}`)을 상수로 분리하거나, 더 유연하게 관리할 수 있는 방법(예: `@Pattern` 어노테이션 활용)을 고려할 수 있습니다.
*   **국가 정보 관리**: `Country` 엔티티의 데이터는 `data.sql`을 통해 초기화되는 것으로 보입니다. 국가 정보 추가/수정/삭제를 위한 별도의 API 또는 관리 기능이 필요할 수 있습니다.
*   **세션 관리 개선**: 현재 세션에 `OpenAiReqDto`와 `PassportVerifyDto`를 직접 저장하고 있습니다. 세션 스토리지를 분리하거나, 더 견고한 세션 관리 전략(예: Redis를 이용한 분산 세션)을 고려할 수 있습니다.
*   **보안 강화**: API 인증/인가, 입력값 검증 강화, SQL Injection 방어 등 전반적인 보안 강화를 고려해야 합니다.
*   **테스트 코드 작성**: 현재 테스트 코드가 `BePracApplicationTests.java` 하나만 존재합니다. 각 컨트롤러, 서비스, 리포지토리에 대한 단위 및 통합 테스트 코드를 작성하여 코드의 안정성과 유지보수성을 높여야 합니다.
*   **로깅**: 상세한 로깅 전략을 수립하여 시스템 운영 및 문제 해결에 필요한 정보를 기록해야 합니다.
*   **API 문서화**: Swagger/OpenAPI 등을 활용하여 API 문서를 자동 생성하고 관리하면 클라이언트 개발자와의 협업이 용이해집니다.
