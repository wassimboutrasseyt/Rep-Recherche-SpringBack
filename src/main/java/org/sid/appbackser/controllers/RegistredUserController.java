package org.sid.appbackser.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registred")
public class RegistredUserController {
    // the registred user should just auth so he inherit from the usercontroller (methode 1)
    // the registred user share comune methodes with other actors (projMember, projAdmin, plaformAdmin)
    //     the comune methode accesed from /registred/profile for example
}
