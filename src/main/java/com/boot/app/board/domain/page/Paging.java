package com.boot.app.board.domain.page;

import lombok.Data;

@Data
public class Paging {

    private int startPost;
    private int endPost;


    public Paging(int pageNum) {
        this.startPost = (pageNum-1) * 20;
        this.endPost = 20;
    }

}
