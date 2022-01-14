package com.boot.app.board.web.uploadfile;

import com.boot.app.board.domain.uploadfile.UploadFile;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class UploadFileController {

    @PostMapping("/uploadAjax")
    public void uploadAjaxPost(MultipartFile[] uploadFile){

        String uploadFolder = "C:\\upload";

        //make folder
        File uploadPath = new File(uploadFolder,getFolder());
        log.info("업로드 경로:",uploadPath);

        if(uploadPath.exists() == false){
            uploadPath.mkdirs();
        }
        //


        for(MultipartFile multipartFile: uploadFile){

            log.info("upload file 이름 :",multipartFile.getOriginalFilename());
            log.info("upload file 사이즈 : ",multipartFile.getSize());

            String uploadFileName = multipartFile.getOriginalFilename();

            //IE 브라우저
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);

            File saveFile = new File(uploadPath,uploadFileName);

            try{
                multipartFile.transferTo(saveFile);
            } catch (Exception e){
                log.error(e.getMessage());
            }

        }
    }


    private String getFolder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);

        return str.replace("-",File.separator);
    }

}

