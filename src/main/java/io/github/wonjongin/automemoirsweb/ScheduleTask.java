package io.github.wonjongin.automemoirsweb;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class ScheduleTask {

    @Scheduled(fixedDelay = 60000)
    public void deleteResult() throws IOException {
        System.out.println("Scheduled!");
        File dataDir = new File("./data/");
        File[] listDirs = dataDir.listFiles();
        Date time = new Date();
        String pattern = "yyyyMMddHHmm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String nowTimeStr = simpleDateFormat.format(time);
        long nowTimeLong = Long.parseLong(nowTimeStr);

        for (int i=0; i<listDirs.length; i++){
            BasicFileAttributes attributes = Files.readAttributes(Paths.get(listDirs[i].getPath()), BasicFileAttributes.class);
            FileTime fileTime = attributes.creationTime();
            Date fileTimeDate = new Date(fileTime.toMillis());
            String fileTimeStr = simpleDateFormat.format(fileTimeDate);
            long fileTimeLong = Long.parseLong(fileTimeStr);
            if (fileTimeLong< nowTimeLong-5){
                deleteFolder(listDirs[i].getPath());
            }

        }
    }

    public static void deleteFolder(String path) {

        File folder = new File(path);
        try {
            if(folder.exists()){
                File[] folder_list = folder.listFiles(); //파일리스트 얻어오기

                for (int i = 0; i < folder_list.length; i++) {
                    if(folder_list[i].isFile()) {
                        folder_list[i].delete();
                        System.out.println("파일이 삭제되었습니다.");
                    }else {
                        deleteFolder(folder_list[i].getPath()); //재귀함수호출
                        System.out.println("폴더가 삭제되었습니다.");
                    }
                    folder_list[i].delete();
                }
                folder.delete(); //폴더 삭제
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}
