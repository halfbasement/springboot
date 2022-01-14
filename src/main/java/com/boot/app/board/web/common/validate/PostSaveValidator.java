package com.boot.app.board.web.common.validate;

import com.boot.app.board.web.post.dto.PostSaveDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Slf4j
@Component
public class PostSaveValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
       return PostSaveDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostSaveDto dto = (PostSaveDto) target;

        if (!StringUtils.hasText(dto.getTitle())) {
            errors.rejectValue("title", "required");
        }
        if (!StringUtils.hasText(dto.getAuthor())) {
            errors.rejectValue("author", "required");
        }
        if (!StringUtils.hasText(dto.getContent())) {
            errors.rejectValue("content", "required");
        }

     /*   if (dto.getNumber() == null || dto.getNumber() < 10 || dto.getNumber() > 1000) {
            log.info("number ={}", dto.getNumber());
            errors.rejectValue("number", "range", new Object[]{10, 1000}, null);
        }

        if (dto.getNumber() == null && !StringUtils.hasText(dto.getTitle()) && !StringUtils.hasText(dto.getAuthor()) && !StringUtils.hasText(dto.getContent())) {
            errors.reject("globalError");
        }*/
    }
}
