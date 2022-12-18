# Auth-System-Smilegate
스마일게이트 윈터데브캠프 개인프로젝트  <br>
<br>

## 프로젝트 목표
* MicroService Architecture를 적용한 서비스를 설계하고 구현
* Auth-Server(인증서버), User-Server(유저서버) 나누어 구축
* JWT 토큰 방식 로그인 (Access Token, Refresh Token 으로 로그인 유지와 재로그인 요청)
<br>

## 사용 기술 및 환경
![spring Boot](https://img.shields.io/badge/-Spring%20Boot-brightgreen)
![gateway](https://img.shields.io/badge/Gateway-Spring%20Cloud%20Gateway-green)
![jwt](https://img.shields.io/badge/Authentication-JWT-orange)

* BE
  * Java 11
  * Spring Boot 2.7.x
  * Gradle
  * JPA
  * MySql
  
<img src="https://user-images.githubusercontent.com/58140426/208300885-65bd9767-1d04-4de1-bbe1-24da9dec987d.png"  width="700" height="370">

## Microservices
### Spring Cloud Gateway
* api-gateway-server 에서 JWT 파싱해서 각각의 리소스 접근 허가
  * 기존에는 인증서버, 유저서버에서 JWT 디코딩 기능을 각각 구현했어야함
  * JWT를 디코딩하여 유저 정보(유저Id와 Role)를 각 서비스 헤더를 통해 전달
  * AuthorizationHeaderFilter 를 통해 JWT 검증
   * ex) http://localhost:8765/user/api/v1/user/users (Gateway 서버를 통한 요청) -> AuthorizationHeaderFilter 에서 jwt 검증
  
### Eureka Server
* 로드밸런싱을 통해 부하 분산

### User-Server
* 유저 세부 정보
* 유저 전체 보기

### Auth-Server
* AccessToken 발급
* RefreshToken 발급
* 토큰 갱신
  * AT 만료된 경우 (2시간 이후 만료, 로그인 유지 요청)
    * 로그인 유지하면 RT 만료 여부 확인
      * RT 만료 안되면 AT 재발급
      * RT 만료시 재로그인 요청 코드 반환
    * 로그인 유지하지 않는다면 로그아웃
      * DB에 저장된 AT, RT delete
* 회원가입
* 로그인
  * 시큐리티와 함꼐 JWT 발행 기능 구현하기 위해 로그인 기능을 구현
  * AT, RT 반

<br>

## 기능 추가 예정
* 토큰 저장을 위한 Redis 추가
* FE: Fetch API 통해 서버와 통신
<br>

## 질문 목록 !
질문목록 노션링크 [질문목록 노션링크](https://lopsided-emperor-206.notion.site/Q-4aa987383dd84c22a59b0758b20a428b)
