# 파이널 프로젝트 : 무료 예약 취소 불가한 숙소의 양도/거래 서비스

## 🎢 서비스 실행 링크

- [서비스 실행 링크](https://dev.dhlbrqe2v28e4.amplifyapp.com/home)

## 💡 프로젝트 주제

- **숙소의 양도/거래 서비스 API 서버 개발**
- **취소 수수료 발생 숙박매물을 고객간에 거래할 수 있는 중개거래 플랫폼 제작**

## 📝 프로젝트 요약

## ⭐️ 핵심 기능

### 회원

- 사람들이 자신의 아이디를 만들어 로그인할 수 있다.
- 로그인 시간에 만료 시간을 두어 일정 시간이 지나면 서비스 사용을 위해 재로그인을 해야 한다.
- 아이디와,비밀번호를 통하여 인증을 진행하며 일치하지 않을 경우 서비스 사용을 할 수 없다.
- 회원가입시 회원의 비밀번호는 암호화하여 저장한다
- JWT 토큰을 이용하여 인증,인가를 진행한다.

### 마이페이지

- 구매내역,판매내역을 리스트 형태로 볼 수 있다.
- 내가 찜을 눌렀던 리스트들을 볼 수 있다.
- 찜 리스트 중 삭제하고 싶은 찜 리스트를 삭제할 수 있다.
- 서비스 후기에 대한 리뷰를 구매/판매내역에 달 수 있다.

### 찜

- 내가 찜을 누른 상품들은 리스트 형태로 조회할 수 있다.
- 삭제하고자 하는 찜을 삭제할 수 있다.
- 물품 조회에서 찜을 누를시 찜이 추가된다.

### 리뷰
- 마이페이지 내에 존재하는 판매/구매 내역에 리뷰를 달 수 있다.
- 판매/구매 동일하게 거래한 상품당 1개씩 리뷰를 달 수 있으며 리뷰 작성을 안했을 시 "리뷰 작성 가능" 형태로 보이며 리뷰를 구매/판매한 시점 14일이 지나면 "리뷰 기한 만료"로 표현되며 
  리뷰를 삭제시 "리뷰 삭제 완료"로 뜨며 다시 리뷰를 재작성할 수 없다.
- 작성된 리뷰는 내용을 확인할 수 있다.

### 구매내역
- 상품 구매 시 구매 내역에 추가된다.
- 구매한 상품의 상세 정보를 확인할 수 있다.

### 판매내역
- 

### 채팅
- 


## ⚙️ 프로젝트 세팅

- 자바 버전 : **17**
- 서버 : AWS EC2
- 스프링 버전 : **Spring Boot 3**
- 데이터베이스 : Mysql, Redis , MongoDB 
- 문서화 도구 : Swagger
- 채팅 : Web Socket,STOMP,Redis Pub/Sub
- 의존성
    - Data-JPA
    - Data-Redis
    - QueryDSL
    - Validation
    - Security
    - Lombok
    - jwt
    - Spring Cloud

## 🛠️ 기술 스택
#### Framework
![Spring](https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![springboot](https://img.shields.io/badge/springboot-6DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
![springsecurity](https://img.shields.io/badge/springsecurity-6DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white)

#### DB
![MongoDB](https://img.shields.io/badge/H2-%234ea94b.svg?style=for-the-badge&logo=htop&logoColor=white)
![mysql](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![redis]

## 🧑‍🤝‍🧑 조원 & 역할

| 이름  | 역할                             |
|-----|--------------------------------|
| 박건우 | 조장, 회원 도메인 개발 , 마이페이지 도메인 개발 , 찜 도메인 개발, 리뷰 도메인 개발,구매/판매 도메인 개발, 서버와 DevOps 설정관리|
| 성지운 | 채팅 도메인 개발, 채팅 인프라 구성, 검색 도메인 개발|
| 정혜민 | 채팅 도메인 개발, 판매 도메인 개발|
| 홍용현 | 구매 도메인 개발, 찜 도메인 개발, 메인 화면 개발|

## 🚀 프로젝트 일정

- **프로젝트 기간**: 12월 11일(월) ~ 1월 30일(화)

## 📐 ERD 설계도

![image](https://github.com/catchroom/BE_CatchRoom/assets/114489245/69507595-d816-42b7-8d52-c70241f19014)


## 🏗 API 서버 구조 & CI/CD 구조

![캡처](https://github.com/catchroom/BE_CatchRoom/assets/50697545/f15ab45d-4626-4e02-a85e-74bac12ed312)


