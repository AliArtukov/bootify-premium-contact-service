package io.bootify.bootify_premium_contact_service;

import io.bootify.bootify_premium_contact_service.security.UserRoles;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@PreAuthorize("hasAuthority('" + UserRoles.ROLE_ADMIN + "')")
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "home/index";
    }

}
