# S-LOG (Study + Log)

## 공부 정리(노트 정리)용 블로그 미니 프로젝트 입니다.

1. markdown 형식의 블로그를 제작합니다.
2. 하나의 계정한 하나의 블로그를 배정합니다.
3. 모든 글을 프로젝트 단위로 작성할 수 있습니다.
    1. 프로젝트 > 폴더 > 글 형식 입니다.
    2. 면접 스터디 > 폴더(알고리즘, 네트워크, 언어 등등) > 글 작성

## ERD(v0.1)
![image](https://user-images.githubusercontent.com/101695482/221416547-0b39c59c-d2cb-402a-9cd7-0af12012dc60.png)

## 프로젝트 기술스택

- 자바 : JAVA 11
- 개발 툴 : SpringBoot 2.7.8
- 필수 라이브러리 : SpringBoot Web, MySQL, Spring Data JPA, Lombok, Spring Security, JWT, Spring Validation, JaCoCo
- 빌드 : Gradle 7.6
- DB : MySql
- CLOUD : AWS EC2, AWS RDS
- CI/CD : Docker

## API

### Member API

|  HTTP  | URL                              |                     설명                     |
|:------:|:---------------------------------|:------------------------------------------:|
|  POST  | /api/v1/members/join             |                    회원가입                    |
|  GET   | /api/v1/members/key/{authKey}    | memeber status 변경 / NOT_PERMITTED -> BASIC |
|  POST  | /api/v1/members/login            |               로그인, Token 반환                |
|  PUT   | /api/v1/members/logout           |                  유저 정보 수정                  |
|  POST  | /api/v1/members/logout           |                    로그아웃                    |
| DELETE | /api/v1/members/{memberNo}/leave |                   회원 탈퇴                    |

### Project API

|  HTTP  | URL                         |        설명        | 
|:------:|:----------------------------|:----------------:|
|  POST  | /api/v1/project/            |     프로젝트 생성      |
|  GET   | /api/v1/project/            |   프로젝트 목록 불러오기   |
|  GET   | /api/v1/project/{projectNo} | 한 프로젝트 자세히 불러오기  |
|  POST  | /api/v1/project/{projectNo} | 프로젝트 공개 / 비공개 설정 |
|  PUT   | /api/v1/project/{projectNo} |     프로젝트 수정      |
| DELETE | /api/v1/project/{projectNo} |     프로젝트 삭제      |