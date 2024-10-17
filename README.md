# 다같이 즐기는 미니게임 플랫폼 - 같겜 (GOD GAME)

![메인페이지](https://github.com/user-attachments/assets/c66047f1-a940-4a1f-a22e-dee6e15c4153)

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

![같겜](https://github.com/user-attachments/assets/c0691d20-0291-4a59-9ca0-775c49184cd2)

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

| Java | Spring | Spring<br>Boot |
| --- | --- | --- |
| <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/java-icon.svg" alt="icon" width="65" height="65" /></div> | <img alt="spring logo" src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" height="50" width="50" > | <img alt="spring-boot logo" src="https://t1.daumcdn.net/cfile/tistory/27034D4F58E660F616" width="65" height="65" > |
| <br/> |  |  |

### ⛏ Database & Caching

| mySQL | Redis | Redis |
| --- | --- | --- |
| <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/mysql-icon.svg" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/ee9ac300-f713-4b48-9bc2-9f5ec1774ae9" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/780ec5d5-9990-4caa-a2b7-8d0af017e424" alt="icon" width="65" height="65" /></div> |
| <br/> |  |  |

### ⛏ Cloud & Tools

| AWS | GitHub |
| --- | --- |
| <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/aws-icon.svg" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/99385abd-987c-47bc-b63d-c949123c90e5" alt="icon" width="65" height="65" /></div> |
| <br/> |  |

## 7. 구현 이미지

| 페이지(기능)                | 이미지                                                                                                                          |
| ----------------------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| 메인<br>페이지                   | ![메인페이지](https://github.com/user-attachments/assets/678eda64-dbc8-43ad-802d-b2c260858601)             |
| 본인인증                     | ![본인인증](https://github.com/user-attachments/assets/39f2b8d4-b221-4106-a121-97733ecc880c)             |
| 회원가입 예외처리        | ![회원가입 예외](https://github.com/user-attachments/assets/0f9641ef-59ca-4292-ad54-b4ba64276152)         |
| 회원가입<br>하기            | ![회원가입 하기](https://github.com/user-attachments/assets/c0406a43-6dd1-42b9-8ded-6afa97bf9fcb)           |
| 로그인<br>하기              | ![로그인-배속](https://github.com/user-attachments/assets/4b9fb1e6-bc51-4d09-86e4-aacd9af7b554) |
| 로그아웃<br>하기            | ![로그아웃](https://github.com/user-attachments/assets/d1db1d74-e06b-4665-9f12-9fd163c4ae73)              |
| 공지사항                    | ![공지사항](https://github.com/user-attachments/assets/01eefdcc-493a-4201-9b89-62c311fa399d)               |
| 친구                         | ![친구보기](https://github.com/user-attachments/assets/d809ff75-4ff2-4b52-889d-115e70f01d12)              |
| 게시판                      | ![게시판](https://github.com/user-attachments/assets/6765f7f7-8149-4ee0-9d7b-d98e8c18b54f)               |
| 게임방<br>찾기              | ![게임방 찾기](https://github.com/user-attachments/assets/24d70f86-a8ea-4ca6-b5b7-239367e72ecd)               |
| 게임과정<br>및 실시간 통신 | ![게임 과정](https://github.com/user-attachments/assets/85a6d92e-bd46-4812-b0ae-725f00e53340)               |
| 그림판<br>기능              | ![그림판 기능](https://github.com/user-attachments/assets/d288226d-dd24-4faa-b06d-d563cda547f1)               |




<br>
