package org.sid.appbackser.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("Admin")
public class AdminController extends RegistredUserController{

}
// the admin are not a member not a guest not a project admin cause he have his owne preveliges and view
//    he should auth and he is already registred (at least 1)
// since he extends RegistredUser which extende User he can auth, see his profile, etc...
