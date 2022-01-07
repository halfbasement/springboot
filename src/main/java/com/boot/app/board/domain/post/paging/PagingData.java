package com.boot.app.board.domain.post.paging;

import lombok.Data;

@Data
public class PagingData {
    /** 현재 페이지 번호 */
    private int currentPageNo;

    /** 페이지당 출력할 데이터 개수 */
    private int recordsPerPage;

    public PagingData() {
        this.currentPageNo = 1;
        this.recordsPerPage = 20;
    }

    public int getStartPage(){
        return (currentPageNo -1)* recordsPerPage;
    }

}