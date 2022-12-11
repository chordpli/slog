# S-LOG (Study + Log)

## 공부 정리(노트 정리)용 블로그 미니 프로젝트 입니다.

1. markdown 형식의 블로그를 제작합니다.
2. 하나의 계정한 하나의 블로그를 배정합니다.
3. 모든 글을 프로젝트 단위로 작성할 수 있습니다.
    1. 프로젝트 > 폴더 > 글 형식 입니다.
    2. 면접 스터디 > 폴더(알고리즘, 네트워크, 언어 등등) > 글 작성

## API

### Member API
| HTTP | URL                           |                     설명                     |
|:----:|:------------------------------|:------------------------------------------:|
| POST | /api/v1/members/join          |                    회원가입                    |
| GET  | /api/v1/members/key/{authKey} | memeber status 변경 / NOT_PERMITTED -> BASIC |
| POST | /api/v1/members/login         |               로그인, Token 반환                |
| POST | /api/v1/members/logout        |                    로그아웃                    |

