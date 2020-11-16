package io.github.wonjongin.automemoirsweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static io.github.wonjongin.automemoirsweb.ControlHWP.createHWP;
import static io.github.wonjongin.automemoirsweb.ControlHWP.writeHWP;

@Controller
@RequestMapping(value = "/api")
public class PostController {
    //    @PostMapping("/automemoirs")
//    public String createResult(@RequestBody Props props) throws Exception {
//        createHWP("./data/" + props.getTitle() + ".hwp");
//        LocalDate date = LocalDate.parse(props.getDate());
//        writeHWP("./data/" + props.getTitle() + ".hwp", props.getTitle(), date, props.getTime(), props.getPlace(), props.getDesc());
//        return "title: " + props.getTitle()
//                + ", place: " + props.getPlace()
//                + ", date: " + props.getDate()
//                + ", time: " + props.getTime()
//                + ", desc: " + props.getDesc();
//    }
    @RequestMapping("/automemoirs")
    private String createResult(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        String uuid = UUID.randomUUID().toString();
        File uuidDir = new File("./data/" + uuid);
        if (!uuidDir.exists()) {
            try {
                uuidDir.mkdirs();
            } catch (Exception e) {
                e.getStackTrace();
            }

        }


        createHWP("./data/" + uuid + "/" + request.getParameter("title") + ".hwp");
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        writeHWP("./data/" + uuid + "/" + request.getParameter("title") + ".hwp", request.getParameter("title"), date, request.getParameter("time"), request.getParameter("place"), request.getParameter("desc").replace("\r\n","\n"));

        model.addAttribute("title", request.getParameter("title"));
        model.addAttribute("place", request.getParameter("place"));
        model.addAttribute("date", request.getParameter("date"));
        model.addAttribute("time", request.getParameter("time"));
        model.addAttribute("desc", request.getParameter("desc").replace("\r\n", "<br>"));
        model.addAttribute("downloadPath", "./download/" + uuid + "/" + request.getParameter("title") + ".hwp");
        // http://nanobyte.iptime.org:8080/api/download/b04379db-e369-46bb-a714-0a6cd18e6334/%EC%A0%9C%EB%AA%A9.hwp -> good ex
        // http://localhost:8080/api/data/22d6770d-1354-4d83-89cd-c7f515e42d01/%EC%A0%9C%EB%AA%A9.hwp -> bad ex
        return "result";
//        return "<h1>" + request.getParameter("title") + "</h1><br>"
//                + "장소: " + request.getParameter("place") + "<br>"
//                + "일시: " + request.getParameter("date") + "<br>"
//                + "시간: " + request.getParameter("time") + "<br>"
//                + "<a href=\"/api/download/" + uuid + "/" + request.getParameter("title") + ".hwp\">"
//                + "만들어진 파일 다운로드" + "</a>"
//                + "<p>" + request.getParameter("desc").replace("\r\n", "<br>") + "</p>";
    }

    @GetMapping("/download/{uuid}/{fileName}")
    public ResponseEntity<Resource> resouceFileDownload(@PathVariable String fileName, @PathVariable String uuid) {
        try {
            // Resource resource = resourceLoader.getResource("classpath:static/files/"+ fileName);
            // File file = resource.getFile();	//파일이 없는 경우 fileNotFoundException error가 난다.
            File file = new File("./data/" + uuid + "/" + fileName);
            String pathStr = "./data/" + uuid + "/" + fileName;
            Path path = Paths.get(pathStr);
            Resource resource = new InputStreamResource(Files.newInputStream(path));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, file.getName())    //다운 받아지는 파일 명 설정
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))    //파일 사이즈 설정
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())    //바이너리 데이터로 받아오기 설정
                    .body(resource);    //파일 넘기기
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
