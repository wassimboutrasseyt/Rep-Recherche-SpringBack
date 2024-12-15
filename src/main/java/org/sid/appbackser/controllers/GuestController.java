package org.sid.appbackser.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Guest")
public class GuestController extends UserController{
        // the guest can auth with guest guest since he extends User (methode 1)
        // the guest is also a non auth users whiche give us possibility to put Registration here (methode 2)
        // Registration should not be on UserController since the Registred User inherit from it and his already registred
}
