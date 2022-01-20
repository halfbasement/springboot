package com.boot.app.board.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class Common  {


    private LocalDateTime regDate;
    private LocalDateTime modifiedDate;

}
