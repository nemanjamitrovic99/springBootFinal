package com.pluralsight.conference.controllers;

import com.pluralsight.conference.models.Session;
import com.pluralsight.conference.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {

    @Autowired
    private SessionRepository sessionRepository;

//    @GetMapping("/sessions")
//    //ovo dole @ResponseBody je reklo sta god da vratimo zelim da se to pretvori u JSON
//    public @ResponseBody List<Session> getRegistrations() {
//        List<Session> registrations=sessionRepository.findAll();
//        System.out.println("ASDASDASDASDASDA");
////        System.out.println(registrations.get(1).getName());
//        return registrations;
//    }

    @GetMapping
    public List<Session> list() {
        System.out.println("Hello from controller");
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id) {
        return sessionRepository.getOne(id);
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        //Also need to check for children records before deleting
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        //because this is a PUT, we except all attributes to be passed in
        //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
        Session existingSession = sessionRepository.getOne(id);
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }
}
