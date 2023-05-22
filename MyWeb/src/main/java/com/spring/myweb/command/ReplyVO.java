package com.spring.myweb.command;
/*
 CREATE TABLE freereply(
	rno INT PRIMARY KEY AUTO_INCREMENT,
    bno INT,
    
    FOREIGN KEY (bno)
    REFERENCES freeboard(bno)
    ON DELETE CASCADE, 
    ==
    ON DELETE CASCADE 참조하는 freeboard에 bno가 지워지면
    댓글도 같이 지워지게 해줌
	==
    reply VARCHAR(1000),
    reply_id VARCHAR(50),
    reply_pw varchar(50),
    reply_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_date DATETIME DEFAULT NULL
);
*/

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyVO {

	private int rno;
	private int bno;
	
	private String reply;
	private String replyId;
	private String replyPw;
	private LocalDateTime replyDate;
	private LocalDateTime updateDate;
	
}
