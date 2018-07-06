package com.xiao.tron.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;
import org.apache.log4j.Logger;

@RestController
public class GreetingController {
    static Logger logger = Logger.getLogger(GreetingController.class);
    
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(), 
                String.format(template, name));
    }

    @RequestMapping(value = "/posting", method = RequestMethod.POST)

    public Posting posting(@RequestBody Greeting greeting) {
        logger.info("helloSpring - request");
        return new Posting(1, "descriptionExample");
    }
}