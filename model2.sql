-- ȸ�� �⺻ ���̺� 
CREATE TABLE "SCOTT"."MEMBERVO" 
   (	
   	"USERID" NVARCHAR2(10) NOT NULL ENABLE, 
	"UPWD" NVARCHAR2(12) NOT NULL ENABLE, 
	"REGISTERDATE" DATE DEFAULT sysdate, 
	"ISADMIN" CHAR(2 BYTE) DEFAULT 'n', 
	"POINT" NUMBER(5,0) DEFAULT 100, 
	 CONSTRAINT "MEMBERVO_PK" PRIMARY KEY ("USERID")
  
   );
   
 -- point��å ���̺�
 create table pointpolicy(
givenwhy char(1) primary key,
pointval number(3),
why nvarchar2(50));

-- point ���� / ��� ���� ���̺�
create table pointlog(
givendate date default sysdate, -- ����
givenwho nvarchar2(10) references membervo(userid), -- ��������
givenwhy char(1) references pointpolicy(givenwhy)); -- ����Ʈ�� ��� ����. 