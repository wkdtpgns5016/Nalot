# 날씨 빅데이터를 이용한 의류추천 웹서비스
날씨 빅데이터를 이용한 의류추천 웹서비스 구축


### 1. 개발환경
> Backend: SpringBoot</br>
> Frontend: React.js

### 2. 프로젝트 초기 환경 구축
> 1. git clone
> 2. 프로젝트 application.properties에 자신의 db 정보, api 정보를 입력
> 3. git bash에 git rm -r --cached . 입력하여 캐시삭제 후 git add .
> 4. 작업시작

### 3. 시스템 설계
#### 3-1.시스템 구조
> ![시스템구성도](./img/System%20Configuration%20Diagram.PNG) <br/><br/>
> **유저 정보 관리 모듈**
> * 회원가입 : 데이터베이스에 회원정보 저장
> * 유저정보 : 데이터베이스에 회원정보 조회
> * 정보수정 : 데이터베이스에 회원정보 수정
> * 회원탈퇴 : 데이터베이스에 회원정보 삭제
> * 유저기록 : 의류추천 기록 조회 및 삭제 <br/>
> 
> **날씨 정보 관리 모듈**  
> * 날씨 API : 기상청 동네예보 API 이용하여 실시간 날씨 정보 수집
> * 날씨 조회 : 데이터베이스에 날씨정보 조회
> * 날씨 삽입 : 데이터베이스에 날씨정보 삽입
> * 날씨 삭제 : 데이터베이스에 날씨정보 삭제 <br/>
>
> **의류 정보 관리 모듈**  
> * 의류 추천 : 기온 별 의상 추천 테이블 기반 의류추천 알고리즘
> * 의류 조회 : 데이터베이스에 의류정보 조회 <br/>
> 
> **색상 정보 관리 모듈**  
> * RGB-HSL 변환 : 색상정보 - RGB 코드와 HSL 코드 변환 알고리즘
> * 톤인톤 배합 : 한 색상에 대한 12가지 톤인톤 배색 반환
> * 톤온톤 배합 : 한 색상에 대한 12가지 톤온톤 배색 반환 <br/>
> 
> **데이터베이스**
> * 유저정보 : 유저 정보 테이블
> * 유저기록 : 선택한 의류 및 색상이 담긴 기록 정보 테이블
> * 날씨정보 : 날씨 정보 테이블
> * 의류정보 : 의류 정보 테이블
> * 유저선택 의류 : 선택한 의류 정보 테이블 <br/>
> 

#### 3-2.유스케이스
> ...

#### 3-3.ERD
> ![ERD](./img/Nalot_ERD_20210619_39_45.png) <br/>

### 4. 주요 기능 구현
#### 4-1.동네예보 API를 이용한 실시간 날씨 정보 수집 기능
> ![동네예보API](./img/동네예보API.PNG) <br/>
> serviceKey를 이용하여 동네예보 API에 대한 URL에 접근
> 
#### 4-2.날씨 별 의류 추천
> **날씨별 의류 추천 테이블** <br/>
> ![clothes_table](./img/clothes_table.PNG) <br/>
> 해당 테이블의 기온 별 level를 참고하여 데이터베이스에 조회
>
#### 4-3.RGB-HSL 변환
> ***RGB-HSL 변환 식** <br/> <br/>
> ![RGB-HSL](./img/rgb-hsl.PNG) <br/>
> 
> ***HSL-RGB 변환 식** <br/> <br/>
> ![HSL-RGB](./img/hsl-rgb.PNG) <br/>
#### 4-4.톤인톤/톤인톤 배합 알고리즘
> **톤인톤 배합** <br/>
> ![tone_in_tone](./img/tone_in_tone.PNG) <br/>
> HSL 색상 모델에서 Hue(색상) 값을 변경 <br/> <br/>
> **톤온톤 배합** <br/>
> ![tone_on_tone](./img/tone_on_tone.PNG) <br/>
> HSL 색상 모델에서 Lightness(밝기) 값을 변경
