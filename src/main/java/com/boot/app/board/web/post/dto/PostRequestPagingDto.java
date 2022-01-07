package com.boot.app.board.web.post.dto;

import lombok.Data;

@Data
public class PostRequestPagingDto {
    /** 현재 페이지 번호 */
    private int currentPageNo;

    /** 페이지당 출력할 데이터 개수 */
    private int recordsPerPage;
}
