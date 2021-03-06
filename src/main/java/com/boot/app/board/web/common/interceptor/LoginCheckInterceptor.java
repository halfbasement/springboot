package com.boot.app.board.web.common.interceptor;

import com.boot.app.board.web.login.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 ={}",requestURI);

        HttpSession session = request.getSession();


        log.info(request.getHeader("X-Requested-with"));





        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
            log.info("미인증 사용자 요청");
            //로그인으로 리다이렉트
            response.sendRedirect("/login?redirectURL="+requestURI);

       /*     if(request.getHeader("X-Requested-with").contentEquals("XMLHttpRequest")){
                response.sendError(500);
            }
*/

            return false;
        }


        return true;
    }
}
