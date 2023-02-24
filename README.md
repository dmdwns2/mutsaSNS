# 멋쟁이사자처럼 백엔드스쿨2기 개인 프로젝트

# 멋사스네스 (SNS) 🦁

## 개발 목적 

회원가입, 로그인이 가능하며 게시물을 작성, 수정, 삭제, 조회 할 수 있는 멋사SNS 개발

+) 마이피드, 댓글, 알람, ApiOperation 기능 추가

<br> <br> <br>

## Swagger 주소
http://ec2-13-230-162-7.ap-northeast-1.compute.amazonaws.com:8080/swagger-ui/

<br> 

## 개발환경 

- 에디터 : Intellij Ultimate
- 개발 툴 : SpringBoot 2.7.5
- 자바 : JAVA 11
- 빌드 : Gradle 6.8 
- 서버 : AWS EC2 사용
- 배포 : Docker / Gitlab / crontab 자동배포
- 데이터베이스 : MySql 8.0, RDS 사용
- 필수 라이브러리 : SpringBoot Web, MySQL, Spring Data JPA, Lombok, Spring Security
- Swagger 3.0.0 사용


<br> <br> <br>

## 👨‍👨‍👧‍👦 User

endpoint : /api/v1/users

<br> 

> ### Join
회원가입 기능

endpoint : /api/v1/users/join [POST]

방법 : userName과 password를 작성하면 db에 저장
입력 폼 (JSON 형식)
```java 
{
    "userName" : "메시",
    "password" : "123"
}
```
리턴 (JSON 형식)
```java
{
    "resultCode": "SUCCESS",
    "result": {
        "userId": 1,
        "userName": "메시"
    }
}
```
<br> <br> <br>

> ### Login
로그인 기능 

endpoint : /api/v1/users/login [POST]

입력 폼 (JSON 형식)
```java 
{
    "userName" : "메시",
    "password" : "123"
}
```
리턴 (JSON 형식)
```java
{
  "resultCode": "SUCCESS",
  "result": {
    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6Im1lc3NpIiwiaWF0IjoxNjc3MjQ3MzY0LCJleHAiOjE2NzcyNTA5NjR9.yIczI8dH0uqUNVu2Jtd4I1bPjRWU6bwZ2j2K2fcvPKk"
  }
}
```
<br> <br> <br>

## 📋 Post

endpoint : /api/v1/posts

<br> 

> ### Get
게시물 조회기능

해당 id 조회
endpoint : /api/v1/posts/{id} [GET]

입력 1

리턴 (JSON 형식)
```java
{
  "resultCode": "SUCCESS",
  "result": {
    "id": 1,
    "title": "안녕하세요",
    "body": "반가워요",
    "userName": "messi",
    "createdAt": "2023-02-24T23:05:03.606442",
    "lastModifiedAt": "2023-02-24T23:05:03.606442"
  }
}
```
리스트 조회
endpoint : /api/v1/posts [GET]

리턴 (JSON 형식)
```java
{
  "resultCode": "SUCCESS",
  "result": {
    "content": [
      {
        "postId": 2,
        "title": "출석합니다",
        "body": "출석",
        "userName": "messi",
        "createdAt": "2023-02-24T23:06:39.901411",
        "lastModifiedAt": "2023-02-24T23:06:39.901411"
      },
      {
        "postId": 1,
        "title": "안녕하세요",
        "body": "반가워요",
        "userName": "messi",
        "createdAt": "2023-02-24T23:05:03.606442",
        "lastModifiedAt": "2023-02-24T23:05:03.606442"
      }
    ],
    "pageable": "INSTANCE",
    "totalPages": 1,
    "totalElements": 2,
    "last": true,
    "numberOfElements": 2,
    "size": 2,
    "sort": {
      "unsorted": true,
      "sorted": false,
      "empty": true
    },
    "number": 0,
    "first": true,
    "empty": false
  }
}
```
id를 이용해 한건만 조회하는 기능과
page형식으로 전체 list를 조회하는 기능
로그인 하지 않아도 가능
방법 : 한건만 조회는 id를 넣어주고 list 형식은 아무것도 입력하지 않아도 된다.

게시물 제목, 내용, 등록한 사람, 등록된 날짜, 수정된 날짜 표시
20개씩 나눠서 페이징 처리 되어있으며 최신순으로 정렬

<br> <br> <br>

> ### Add
게시물 등록기능

endpoint : /api/v1/posts [POST]

입력 폼 (JSON 형식)
```java 
{
  "body": "반가워요",
  "title": "안녕하세요"
}
```
리턴 (JSON 형식)
```java
{
  "resultCode": "SUCCESS",
  "result": {
    "postId": 1,
    "title": "안녕하세요",
    "body": "반가워요",
    "userName": "messi",
    "message": "포스트 등록 완료"
  }
}
```
로그인 해야 이용할 수 있음
방법 : title과 body에 제목과 내용을 입력


<br> <br> <br>

> ### Put
게시물 수정기능

endpoint : /api/v1/posts/{id} [PUT]

입력 폼 (JSON 형식) ID : 1
```java 
{
  "body": "수정완료",
  "title": "수정테스트"
}
```
리턴 (JSON 형식)
```java
{
  "resultCode": "SUCCESS",
  "result": {
    "postId": 1,
    "title": "수정테스트",
    "body": "수정완료",
    "userName": "messi",
    "message": "포스트 수정 완료"
  }
}
```
로그인 해야 이용할 수 있음
자신이 작성한 글만 수정할 수 있음

방법 : id를 넣어주고 title과 body에 제목과 내용을 입력

<br> <br> <br>

> ### Delete
게시물 삭제기능

endpoint : /api/v1/posts/{id} [DELETE]

입력 ID : 1

리턴 (JSON 형식)
```java
{
  "resultCode": "SUCCESS",
  "result": {
    "postId": 1,
    "message": "포스트 삭제 완료"
  }
}
```
로그인 해야 이용할 수 있음
자신이 작성한 글만 삭제할 수 있음

방법 : id만 넣어주면 됨

<br> <br> <br>
> ### MyFeed
마이피드 기능

endpoint : /api/v1/posts/my [GET]

리턴 (JSON 형식)
```java
{
  "resultCode": "SUCCESS",
  "result": {
    "content": [
      {
        "postId": 3,
        "title": "멋사",
        "body": "멋쟁이",
        "userName": "messi",
        "createdAt": "2023-02-24T23:10:54.98229",
        "lastModifiedAt": "2023-02-24T23:10:54.98229"
      },
      {
        "postId": 2,
        "title": "출석합니다",
        "body": "출석",
        "userName": "messi",
        "createdAt": "2023-02-24T23:06:39.901411",
        "lastModifiedAt": "2023-02-24T23:06:39.901411"
      }
    ],
    "pageable": "INSTANCE",
    "totalPages": 1,
    "totalElements": 2,
    "last": true,
    "numberOfElements": 2,
    "size": 2,
    "sort": {
      "unsorted": true,
      "sorted": false,
      "empty": true
    },
    "number": 0,
    "first": true,
    "empty": false
  }
}
```
로그인 해야 이용할 수 있음
자신이 작성한 글을 리스트 형식으로 보여줌

방법 : 로그인 후 사용

<br> <br> <br>

## 📝 Comment

> ### Get
댓글 조회 기능

endpoint : /api/v1/comments/{postId} [GET]

댓글 목록을 조회하는 기능

방법 : 게시물 id만 넣어주면 됨

<br> <br> <br>

> ### Add
댓글 작성 기능

endpoint : /api/v1/comments/{postId} [POST]

입력 폼 (JSON 형식) ID : 1
```java 
{
  "comment": "반가워요"
}
```
리턴 (JSON 형식)
```java
{
  "resultCode": "SUCCESS",
  "result": {
    "id": 1,
    "comment": "반가워요",
    "userName": "messi",
    "postId": 2,
    "createdAt": "2023-02-24T23:12:27.350566"
  }
}
```
로그인 해야만 사용 가능
댓글을 작성하는 기능

방법 : 게시물 id를 넣어주고 내용을 적어주면 됨 

<br> <br> <br>

<br> <br> <br>

> ### Put
댓글 수정 기능

endpoint : /api/v1/comments/{postId} [POST]

입력 폼 (JSON 형식) ID : 1 , POST ID : 2
```java 
{
  "comment": "댓글 수정 완료"
}
```
리턴 (JSON 형식)
```java
{
  "resultCode": "SUCCESS",
  "result": {
    "id": 1,
    "comment": "댓글 수정 완료",
    "userName": "messi",
    "postId": 2,
    "createdAt": "2023-02-24T23:12:27.350566",
    "modifiedAt": "2023-02-25T00:11:47.091687"
  }
}
```
로그인 해야만 사용 가능
댓글을 수정하는 기능

방법 : 게시물 id를 넣어주고 내가 작성했던 댓글 id를 입력, 내용을 적어주면 됨 

<br> <br> <br>

> ### Delete
댓글 삭제 기능

endpoint : /api/v1/comments/{postId} [DELETE]

입력 ID : 1 , POST ID : 2
리턴 (JSON 형식)
```java
{
  "resultCode": "SUCCESS",
  "result": {
    "message": "댓글 삭제 완료",
    "id": 1
  }
}
```
로그인 해야만 사용 가능
댓글을 삭제하는 기능

방법 : 삭제할 댓글 id를 넣어주면 됨

<br> <br> <br>

## ⏰ Alarm

> ### GET
알람 조회 기능

endpoint : /api/v1/comments/{postId} [POST]

로그인 해야만 사용 가능
내가 작성한 게시물에 댓글, 좋아요를 알려주는 기능

방법 : 로그인 후 실행

<br> <br> <br>

## 인증/인가 방식

> ### SpringSecurity, JWT 사용

JWT Token발급하기

JwtTokenFilter 인증 계층 추가 하기

모든 요청에 권한 부여하기

Token이 없으면 권한 부여 하지 않기

Token의 유효기간이 지났는지 확인하기

Token에서 userName꺼내서 Controller에서 사용

<br> <br> <br>

## 아키텍쳐

![스크린샷 2023-02-17 오후 4 44 51](https://user-images.githubusercontent.com/105894868/219582289-b87d24d6-bdba-4650-978f-70974b4ed978.png)



<br> <br> <br>

## ERD


![Untitledㅣ](/uploads/8d0bc5ece82cf2f1c13fb8f65d46bdbf/Untitledㅣ.png)
