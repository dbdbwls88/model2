-- 회원 기본 테이블 
CREATE TABLE "SCOTT"."MEMBERVO" 
   (	
   	"USERID" NVARCHAR2(10) NOT NULL ENABLE, 
	"UPWD" NVARCHAR2(12) NOT NULL ENABLE, 
	"REGISTERDATE" DATE DEFAULT sysdate, 
	"ISADMIN" CHAR(2 BYTE) DEFAULT 'n', 
	"POINT" NUMBER(5,0) DEFAULT 100, 
	 CONSTRAINT "MEMBERVO_PK" PRIMARY KEY ("USERID")
  
   );
   
 -- point정책 테이블
 create table pointpolicy(
givenwhy char(1) primary key,
pointval number(3),
why nvarchar2(50));

-- point 지급 / 사용 내역 테이블
create table pointlog(
givendate date default sysdate, -- 언제
givenwho nvarchar2(10) references membervo(userid), -- 누구에게
givenwhy char(1) references pointpolicy(givenwhy)); -- 포인트를 줬다 이유. 