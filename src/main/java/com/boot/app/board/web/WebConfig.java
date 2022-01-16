package com.boot.app.board.web;

import com.boot.app.board.web.common.interceptor.LogInterceptor;
import com.boot.app.board.web.common.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       /* registry.addInterceptor(new LogInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico","/error");*/

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/post","/post/{postId:^[0-9]+$}","/login","/member/add","/comment/{postId:^[0-9]+$}","/logout",
                        //file
                        "/uploadAjax","/postFile/**","/display","/download",
                        "/comment/{postId:^[0-9]+$}/addModalInfo", //인터셉터 처리 다시 해야함
                        "/css/**","/*.ico","/js/**","/error");
    }


}
