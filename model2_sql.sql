-- ȸ�����Խ� ȸ�����̺� ���̵�, ��й�ȣ�� insert�ϰ�, point�� 100����(default), ����Ʈ ���޳����� pointlog�� ����
create or replace procedure sp_insertMemberVo
(
    userid in nvarchar2,
    upwd in nvarchar2
)
is

begin
    insert into membervo(userid, upwd) values(userid, upwd); -- ȸ������
    insert into pointlog(givenwho, givenwhy) values(userid, 'A'); --  ����Ʈ ���޳����� pointlog�� ����
end;