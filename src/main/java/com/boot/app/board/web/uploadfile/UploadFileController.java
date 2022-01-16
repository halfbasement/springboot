package com.boot.app.board.web.uploadfile;

import com.boot.app.board.domain.uploadfile.UploadFile;
import com.boot.app.board.domain.uploadfile.UploadFileService;
import com.boot.app.board.web.uploadfile.dto.PostUploadFileDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UploadFileController {


    private final UploadFileService uploadFileService;

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<PostUploadFileDto>> uploadAjaxPost(MultipartFile[] uploadFile){

        List<PostUploadFileDto> result = new ArrayList<>();
        String uploadFolder = "C:\\upload";

        String uploadFolderPath = getFolder();

        //make folder
        File uploadPath = new File(uploadFolder,uploadFolderPath);
        log.info("업로드 경로:",uploadPath);

        if(uploadPath.exists() == false){
            uploadPath.mkdirs();
        }
        //


        for(MultipartFile multipartFile: uploadFile){

            PostUploadFileDto postUploadFileDto = new PostUploadFileDto();

            log.info("upload file 이름 :",multipartFile.getOriginalFilename());
            log.info("upload file 사이즈 : ",multipartFile.getSize());

            String uploadFileName = multipartFile.getOriginalFilename();

            //IE 브라우저
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);

            postUploadFileDto.setFileName(uploadFileName);

            //중복방지
            String uuid = UUID.randomUUID().toString();
            uploadFileName = uuid + "_" +uploadFileName;


            try{
                File saveFile = new File(uploadPath,uploadFileName);
                multipartFile.transferTo(saveFile);

                postUploadFileDto.setUuid(uuid);
                postUploadFileDto.setPath(uploadFolderPath);


               //이미지  체크
                if(checkImageType(saveFile)){

                    postUploadFileDto.setFileType(true);

                  /*  FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath,"s_"+uploadFileName));

                    Thumbnailator.createThumbnail(multipartFile.getInputStream(),thumbnail,100,100);
                    thumbnail.close();*/
                }

                result.add(postUploadFileDto);

            } catch (Exception e){
                log.error(e.getMessage());
            }

        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName){
        log.info("fileName :" + fileName);

        File file = new File("C:\\upload\\"+fileName);

        ResponseEntity<byte[]> result = null;

        try{
            HttpHeaders header = new HttpHeaders();

            header.add("Content-Type",Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);

        }catch (IOException e){
            e.printStackTrace();
        }
    return result;
    }
    //날짜 폴더생성
    private String getFolder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);

        return str.replace("-",File.separator);
    }

    //이미지 파일 여부 판단
    private boolean checkImageType(File file){
        try{
            String contentType = Files.probeContentType(file.toPath());
            if(contentType !=null){
                return contentType.startsWith("image");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }



    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(String fileName){

        Resource resource = new FileSystemResource("C:\\upload\\"+fileName);

        log.info("리소스",resource);

        String resourceName = resource.getFilename();

        HttpHeaders headers = new HttpHeaders();

        String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);

        try{
            headers.add("Content-Disposition","attachment; filename="+new String(resourceOriginalName.getBytes(StandardCharsets.UTF_8),"ISO-8859-1"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
    }

    @PostMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(String fileName,String type){
        log.info("deleteFile" + fileName);

        File file;

        try{
            file = new File("c:\\upload\\" + URLDecoder.decode(fileName,"UTF-8"));
            file.delete();

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("삭제되었습니다.",HttpStatus.OK);
    }

    @GetMapping("/postFile/{postId}")
    public ResponseEntity<List<PostUploadFileDto>> detailFile(@PathVariable Long postId){


        List<UploadFile> byPostIdFile = uploadFileService.findByPostIdFile(postId);

        List<PostUploadFileDto> result = byPostIdFile.stream()
                .map(f -> new PostUploadFileDto(f.getFileName(), f.getPath(), f.getUuid(), f.isFileType()))
                .collect(Collectors.toList());


        return new ResponseEntity<>(result,HttpStatus.OK);
    }




}

