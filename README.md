# 

https://github.com/user-attachments/assets/063836e1-f26f-4b7d-aa7e-ab8eda9d9769

<br/>
<br/>

# 1. Project Overview (프로젝트 개요)

- 프로젝트 이름:    **다같이 즐기는 미니게임 플랫폼 - 같겜**  (GOD GAME)
- 프로젝트 설명:    웹에서 사람들과 만나 게임을 즐길 수 있는 종합 게임 서비스

<br/>
<br/>

# 2. Key Features (주요 기능)

- **게시판 관련 기능**:
    - 게시글 작성
    - 게시글 수정
    - 게시글 삭제
    - 게시글 댓글 작성
    - 게시글 댓글 수정
    - 게시글 댓글 삭제
- **게임 방 관련 기능**:
    - 게임 방 생성
    - 게임 방 참여
    - 게임 방 나가기
    - 게임 시작
    - 게임 방 회원간의 실시간 채팅
- 미니게임 관련 기능:
    - 캐치마인드 그림판
    - 캐치마인드 실시간 채팅
    - 캐치마인드 실시간 그림 그리기
    - 캐치마인드 채팅 속 정답 맞히기
    - 캐치마인드 라운드 시작 및 종료
- **회원 관련 기능**:
    - 회원 가입
    - 비밀 번호 변경
    - 비밀 번호 찾기
    - 로그인
    - 로그 아웃
    - 회원 탈퇴
- 랭킹 관련 기능:
    - 현재 회원 중 랭킹 표시

<br/>
<br/>

# 3.📝 관련 문서

### [📌 요구사항 정의서](https://docs.google.com/spreadsheets/d/1xIquU97u7FLaCzdQ7IQ_Gd_LWD4lePRd3PV9OQqzBtE/edit?gid=0#gid=0)

### [📌 API 명세서](https://docs.google.com/spreadsheets/d/1ha0STK_N42XSImCum-KUFX9bxTvhNb4hXmb0svQ9ovs/edit?usp=sharing)

### [📌 목업](https://www.figma.com/design/aUvTEyeOsTveeuSJIm8KqB/%EA%B0%99%EA%B2%9C?node-id=0-1&node-type=canvas&t=O5X0msGJeWmxdJZ8-0)

### [📌 ERD](https://dbdiagram.io/d/%EA%B0%99%EA%B2%9C-66f5f96e3430cb846cc5306f)

![같겜.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/73fc2512-9191-4885-94de-87e3545a39dd/c3b7800d-d2cb-4c6d-8a26-4f1e94a940e3/%EA%B0%99%EA%B2%9C.png)

<br>

<br/>
<br/>

# 4. Tasks & Responsibilities (작업 및 역할 분담)

|  |  |  |
| --- | --- | --- |
| 👑<br>   노영준 | <img src="https://github.com/user-attachments/assets/f39bd0e9-f755-4871-af99-69c3371c4141" alt="노영준" width="100"> | <ul><li>회원∙Spring Security 구현</li><li>WebSocket 통신 설정 및 구현 (BE, FE)</li><li>캐치마인드 서비스 API 구현</li></li><li>FE 캐치마인드 서비스 동작 구현  </li></ul> |
| 박원일 | <img src="https://github.com/user-attachments/assets/5e34d335-fead-47a9-89c2-e9da7dc840fe" alt="박원일" width="100"> | <ul><li>게시판∙댓글∙차단기능 구현</li><li> 게임별 기초 데이터 생성</li><li>WebSocket 및 Redis를 활용한 캐치마인드 서비스 구현</li></ul> |
| 김영진 | <img src="https://github.com/user-attachments/assets/7c176a31-3971-4268-af8b-d9068a443f2d" alt="김영진" width="100"> | <ul><li></li></ul> |
| 유정균 | <img src="https://github.com/user-attachments/assets/153721c5-8f8f-4dd2-a3f6-2dea79f96904" alt="유정균" width="100"> | <ul><li>UI/UX 구현</li><li>서버 연동</li><li>게시판 API 구현</li></ul> |

<br/>
<br/>

# 5. Technology Stack (기술 스택)

### 🔨 Front-end

| Html | JavaScript | React | CSS |
| --- | --- | --- | --- |
| <img alt="Html" src ="https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/HTML5_logo_and_wordmark.svg/440px-HTML5_logo_and_wordmark.svg.png" width="65" height="65" /> | <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/js-icon.svg" alt="icon" width="75" height="75" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/react-icon.svg" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/8e99f402-391a-45f6-9312-05be3ce887f0" alt="icon" width="65" height="65" /></div> |
| <br/> |  |  |  |

### ⛏ Back-end

| Java | Spring | Spring<br>Boot | WebSocket |
| --- | --- | --- | --- |
| <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/java-icon.svg" alt="icon" width="65" height="65" /></div> | <img alt="spring logo" src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" height="50" width="50" > | <img alt="spring-boot logo" src="https://t1.daumcdn.net/cfile/tistory/27034D4F58E660F616" width="65" height="65" > | <img alt="spring-boot logo" src="https://github.com/user-attachments/assets/9b1f252a-7243-4139-a6ae-39b155c1ce72" width="65" height="65" > |
| <br/> |  |  |  |

### ⛏ Database & Caching

| mySQL | Redis |
| --- | --- |
| <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/mysql-icon.svg" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/ee9ac300-f713-4b48-9bc2-9f5ec1774ae9" alt="icon" width="65" height="65" /></div> |
| <br/> |  |

### ⛏ Cloud & Tools

| AWS | GitHub |
| --- | --- |
| <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/aws-icon.svg" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/99385abd-987c-47bc-b63d-c949123c90e5" alt="icon" width="65" height="65" /></div> |
| <br/> |  |

# 6. Project Structure (프로젝트 구조)

<br>
<details>

<summary> 📂 프로젝트 폴더 구조</summary>

```
🏠 guild-master
├─ 📂 client
│  │─ .env.sample
│  │─ .eslintrc.json  ──────────────── ⚙️ eslint 설정 파일
│  │─ .gitignore
│  │─ .prettierrc.json ─────────────── ⚙️ prettier 설정 파일
│  │─ package-lock.json
│  │─ package.json
│  │
│  ├─ ├─ public
├─  src
│  ├─ App.css
│  ├─ App.js
│  ├─ Global.css
│  ├─ auth ─────────────────────────────── 🙋‍♂️ 로그인 전역 관리 파일
│  │  ├─ UsePersistedState.jsx
│  │  └─ index.jsx
│  ├─ component ────────────────────────── 🗂️ 각 페이지에서 사용되는 컴포넌트
│  │  ├─ GuildBoardPage
│  │  ├─ GuildListPage
│  │  ├─ HomePage
│  │  ├─ SignUpPage
│  │  ├─ ManagePage
│  │  │  ├─ ManagePlayerTab.js
│  │  │  ├─ PlayerItem.js
│  │  │  ├─ PlayerList.js
│  │  │  ├─ PlayersItem.js
│  │  │  ├─ Tab.js
│  │  │  ├─ WaitList.js
│  │  │  ├─ WaitPlayersItem.js
│  │  │  └─ memberGuildData.js
│  │  ├─ LargeModal.js ──────────────────── 🗂️ 모든 페이지에서 공통으로 사용되는 컴포넌트
│  │  ├─ Modal.js
│  │  ├─ OutPut.js
│  │  └─ RegistInput.js
│  ├─ image
│  │  ├─ loastark.png
│  │  ├─ lol.png
│  │  ├─ overwatch.png
│  │  └─ valorant.png
│  ├─ logo
│  │  ├─ fulllogo_white.png
│  │  ├─ fulllogo_white_big.png
│  │  └─ logo_white.png
│  ├─ pages ─────────────────────────────── 🗂️ 라우팅이 적용된 API를 요청하는 페이지 컴포넌트
│  │  ├─ GlobalHeader.js
│  │  ├─ GuildBoardPage.js
│  │  ├─ GuildListPage.js
│  │  ├─ HomePage.js
│  │  ├─ LandingPage.js
│  │  ├─ LoginPage.js
│  │  ├─ ManagePage.js
│  │  ├─ MyPage.js
│  │  └─ SignUpPage.js
│  │
│  ├─setupTests.js
│  ├─ index.css
│  ├─ index.js
│  └─ logo.svg
│
└─ 📂 server
   │─ .gitignore
   │─ build.gradle
   │─ gradlew
   │─ gradlew.bat
   │─ settings.gradle
   │
   ├─ 📂 gradle-wrapper
   │  ├─ gradle-wrapper.jar
   │  └─ gradle-wrapper.properties
   │
   └─ └─ src
   ├─ main
   │  └─ java
   │     └─ com
   │        └─ continewbie
   │           └─ guild_master
   │              ├─ GuildMasterApplication.java
   │              ├─ advice
   │              │  └─ GlobalExceptionAdvice.java
   │              ├─ auditable
   │              │  └─ Auditable.java
   │              ├─ auth
   │              │  ├─ controller
   │              │  │  └─ AuthController.java
   │              │  ├─ dto
   │              │  │  └─ LoginDto.java
   │              │  ├─ filter
   │              │  │  ├─ JwtAuthenticationFilter.java
   │              │  │  └─ JwtVerificationFilter.java
   │              │  ├─ handler
   │              │  │  ├─ MemberAccessDeniedHandler.java
   │              │  │  ├─ MemberAuthenticationEntryPoint.java
   │              │  │  ├─ MemberAuthenticationFailureHandler.java
   │              │  │  └─ MemberAuthenticationSuccessHandler.java
   │              │  ├─ jwt
   │              │  │  └─ JwtTokenizer.java
   │              │  ├─ service
   │              │  │  └─ AuthService.java
   │              │  ├─ userDetails
   │              │  │  └─ MemberDetailsService.java
   │              │  └─ utils
   │              │     ├─ ErrorResponse.java
   │              │     └─ JwtAuthorityUtils.java
   │              ├─ config
   │              │  └─ SecurityConfiguration.java
   │              ├─ dto
   │              │  ├─ MultiResponseDto.java
   │              │  ├─ PageInfo.java
   │              │  └─ SingleResponseDto.java
   │              ├─ errorresponse
   │              │  └─ ErrorResponse.java
   │              ├─ event
   │              │  ├─ controller
   │              │  │  └─ EventController.java
   │              │  ├─ dto
   │              │  │  └─ EventDto.java
   │              │  ├─ entity
   │              │  │  └─ Event.java
   │              │  ├─ mapper
   │              │  │  └─ EventMapper.java
   │              │  ├─ repository
   │              │  │  └─ EventRepository.java
   │              │  └─ service
   │              │     └─ EventService.java
   │              ├─ exception
   │              │  ├─ BusinessLogicException.java
   │              │  └─ ExceptionCode.java
   │              ├─ game
   │              │  ├─ controller
   │              │  │  └─ GameController.java
   │              │  ├─ dto
   │              │  │  └─ GameDto.java
   │              │  ├─ entity
   │              │  │  └─ Game.java
   │              │  ├─ mapper
   │              │  │  └─ GameMapper.java
   │              │  ├─ repository
   │              │  │  └─ GameRepository.java
   │              │  └─ service
   │              │     └─ GameService.java
   │              ├─ guild
   │              │  ├─ controller
   │              │  │  └─ GuildController.java
   │              │  ├─ dto
   │              │  │  └─ GuildDto.java
   │              │  ├─ entity
   │              │  │  └─ Guild.java
   │              │  ├─ mapper
   │              │  │  └─ GuildMapper.java
   │              │  ├─ repository
   │              │  │  └─ GuildRepository.java
   │              │  └─ service
   │              │     └─ GuildService.java
   │              ├─ helper
   │              │  └─ event
   │              │     └─ MemberRegistrationApplicationEvent.java
   │              ├─ member
   │              │  ├─ controller
   │              │  │  └─ MemberController.java
   │              │  ├─ dto
   │              │  │  └─ MemberDto.java
   │              │  ├─ entity
   │              │  │  └─ Member.java
   │              │  ├─ mapper
   │              │  │  └─ MemberMapper.java
   │              │  ├─ repository
   │              │  │  └─ MemberRepository.java
   │              │  └─ service
   │              │     └─ MemberService.java
   │              ├─ memberguild
   │              │  ├─ dto
   │              │  │  └─ MemberGuildDto.java
   │              │  ├─ entity
   │              │  │  └─ MemberGuild.java
   │              │  └─ mapper
   │              │     └─ MemberGuildMapper.java
   │              ├─ memeberevent
   │              │  ├─ dto
   │              │  │  ├─ MemberEventDto.java
   │              │  │  └─ MemberEventResponseDto.java
   │              │  ├─ entity
   │              │  │  └─ MemberEvent.java
   │              │  ├─ mapper
   │              │  │  └─ MemberEventMapper.java
   │              │  └─ repository
   │              │     └─ MemberEventRepository.java
   │              ├─ position
   │              │  ├─ dto
   │              │  │  └─ PositionDto.java
   │              │  ├─ entity
   │              │  │  └─ Position.java
   │              │  └─ repository
   │              │     └─ PositionRepository.java
   │              ├─ redis
   │              │  └─ RedisRepositoryConfig.java
   │              └─ utils
   │                 ├─ CustomBeanUtils.java
   │                 ├─ DataInitializer.java
   │                 ├─ UriCreator.java
   │                 └─ validator
   │                    ├─ InvalidEventDateException.java
   │                    ├─ NotSpace.java
   │                    └─ NotSpaceValidator.java
   └─ test
      └─ java
         └─ com
            └─ continewbie
               └─ guild_master
                  └─ GuildMasterApplicationTests.java

```

<br>
<br/>
</details>
<br>
<br/>

## 7. 구현 이미지

[제목 없음](https://www.notion.so/09d3c27a1d024613b0ba3e5c98978e45?pvs=21)

<br>
