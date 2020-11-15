package io.github.wonjongin.automemoirsweb;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static io.github.wonjongin.automemoirsweb.ControlHWP.createHWP;
import static io.github.wonjongin.automemoirsweb.ControlHWP.writeHWP;

@RestController
@RequestMapping(value = "/api")
public class PostController {
    @PostMapping("/automemoirs")
    public String createResult(@RequestBody Props props) throws Exception {
        createHWP("./data/"+props.getTitle()+".hwp");
        LocalDate date = LocalDate.parse(props.getDate());
        writeHWP("./data/"+props.getTitle()+".hwp", props.getTitle(), date, props.getTime(), props.getPlace(), props.getDesc());
        return "title: " + props.getTitle()
                + ", place: " + props.getPlace()
                + ", date: " + props.getDate()
                + ", time: " + props.getTime()
                + ", desc: " + props.getDesc();
    }
}
