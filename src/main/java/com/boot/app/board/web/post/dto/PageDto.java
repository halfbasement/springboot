package com.boot.app.board.web.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageDto {
    private int fistPage;
    private int endPage;
   // private int maxPage;



   /* public PageDto(int maxPage) {
        this.fistPage = 1;
        this.endPage = 5;
        this.maxPage = maxPage;
    }*/



}
