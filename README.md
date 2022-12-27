# FinalProject_KimEungjun_teamN

![멋사](/uploads/cba22e18f64672058ef196d4c72cf2aa/멋사.png)

# 멋사스네스 (SNS)

## 목적 

멋사SNS 개발

## 스웨거 주소
http://ec2-13-230-162-7.ap-northeast-1.compute.amazonaws.com:8080/swagger-ui/

## 개발환경 

- 에디터 : Intellij Ultimate
- 개발 툴 : SpringBoot 2.7.5
- 자바 : JAVA 11
- 빌드 : Gradle 6.8 
- 서버 : AWS EC2 사용
- 배포 : Docker
- 데이터베이스 : MySql 8.0, RDS 사용
- 필수 라이브러리 : SpringBoot Web, MySQL, Spring Data JPA, Lombok, Spring Security
- Swagger 3.0.0 사용


## User

### Join
회원가입 기능 
방법 : userName과 password를 작성하면 db에 저장

### Login
로그인 기능 
방법 : 가입된 userName과 password를 작성하면 토큰을 반환

## Post

### Get
게시물 조회기능
id를 이용해 한건만 조회하는 기능과
page형식으로 전체 list를 조회하는 기능
로그인 하지 않아도 가능
방법 : 한건만 조회는 id를 넣어주고 list 형식은 아무것도 입력하지 않아도 된다.

게시물 제목, 내용, 등록한 사람, 등록된 날짜, 수정된 날짜 표시
20개씩 나눠서 페이징 처리 되어있으며 최신순으로 정렬

### Add
게시물 등록기능
로그인 해야 이용할 수 있음
방법 : title과 body에 제목과 내용을 입력


### Put
게시물 수정기능
로그인 해야 이용할 수 있음
자신이 작성한 글만 수정할 수 있음

방법 : id를 넣어주고 title과 body에 제목과 내용을 입력

### Delete
게시물 삭제기능
로그인 해야 이용할 수 있음
자신이 작성한 글만 삭제할 수 있음

방법 : id만 넣어주면 됨

# ERD


![ERD](/uploads/025ebf46f1bae0e616bbcf179a437499/ERD.png)
