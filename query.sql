create table board(
    board_id number primary key
	, title varchar2(100)
	, writer varchar2(25)
	, content clob
	, created_at date default sysdate
	, hit number default 0
);

create sequence seq_board 
increment by 1
start with 1;