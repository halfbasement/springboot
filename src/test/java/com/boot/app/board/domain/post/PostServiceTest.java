package com.boot.app.board.domain.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {
    @Autowired
    PostMapper postMapper;
    @Test
    void paging() {
        Integer postCount = postMapper.postCount();

        System.out.println("paging = " + postCount);
    }
}