package com.boot.app.board.web.common.schedule;

import com.boot.app.board.domain.uploadfile.UploadFile;
import com.boot.app.board.domain.uploadfile.UploadFileMapper;
import com.boot.app.board.domain.uploadfile.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostFileSchedule {

    @Autowired
    UploadFileMapper uploadFileMapper;


    private String getYesterDayFolder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE,-1);

        String str = sdf.format(cal.getTime());

        return str.replace("-", File.separator);
    }


    @Scheduled(cron = "0 0 1 * * *") //새벽한시마다 실행
    public void PostFileDelete(){

        //어제자 파일 DB목록을 받아오고
        List<UploadFile> uploadFiles = uploadFileMapper.oldFileByPath();




        //비교를 위해 Path타입으로 변환
        List<Path> collect = uploadFiles.stream()
                .map(f -> Paths.get("C:\\upload", f.getPath(), f.getUuid() + "_" + f.getFileName()))
                .collect(Collectors.toList());


        File yesterDayFolder = Paths.get("C:\\upload",getYesterDayFolder()).toFile();

        //가져온 파일 db목록기준으로 어제자 파일목록리스트를 비교해서 없는 것들만 removeFiles로 뽑아냄
        File[] removeFiles = yesterDayFolder.listFiles(f->collect.contains(f.toPath()) == false);

        for(File file:removeFiles){
            System.out.println("파일 삭제됨"+file.getAbsolutePath());
            file.delete();

        }



    }
}
