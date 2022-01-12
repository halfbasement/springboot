package com.boot.app.board.domain.common;

import com.boot.app.board.web.post.dto.PageDto;
import lombok.Data;

@Data
public class PagingData {
    /** 현재 페이지 번호 */
    private int currentPageNo;

    /** 페이지당 출력할 데이터 개수 */
    private int recordsPerPage;

    private int firstPage;
    private int endPage;



    public PagingData() {
        this.currentPageNo = 1;
        this.recordsPerPage = 20;
        this.firstPage=1;
        this.endPage=5;
    }

    public int getStartPage(){
        return (currentPageNo -1)* recordsPerPage;
    }

}
