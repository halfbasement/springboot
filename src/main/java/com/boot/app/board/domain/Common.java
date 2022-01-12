package com.boot.app.board.domain;

import com.boot.app.board.domain.common.PagingData;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class Common extends PagingData {

    private PagingData pagingData;

    private LocalDateTime regDate;
    private LocalDateTime modifiedDate;

}
