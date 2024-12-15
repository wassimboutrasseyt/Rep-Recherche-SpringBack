package org.sid.appbackser.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team-leader")
public class ProjectAdminController extends ProjectMemberController{
    // the project admin can also be a member on other project so on that case hi will access by /member/-- 
    //     since he inherit from the member and when he is a project admin he will access from /team-leader/--
    
}
