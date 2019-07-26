-- 회원가입시 회원테이블에 아이디, 비밀번호를 insert하고, point를 100지급(default), 포인트 지급내역을 pointlog에 남김
create or replace procedure sp_insertMemberVo
(
    userid in nvarchar2,
    upwd in nvarchar2
)
is

begin
    insert into membervo(userid, upwd) values(userid, upwd); -- 회원가입
    insert into pointlog(givenwho, givenwhy) values(userid, 'A'); --  포인트 지급내역을 pointlog에 남김
end;