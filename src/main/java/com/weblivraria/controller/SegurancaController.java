package com.weblivraria.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SegurancaController {

    @RequestMapping(value = {"/admin", "/admin/"})
    public String admin() {
        return "admin/index";
    }

    @RequestMapping("/login")
    public String login(@AuthenticationPrincipal User user) {
        if(user != null ) {
            for(GrantedAuthority grantedAuthority : user.getAuthorities() ) {
                if (grantedAuthority.getAuthority().equals("ROLE_ADMIN"))
                    return "redirect:/admin";
            }
            return "redirect:/";
        }
        return "site/login";
    }

    @RequestMapping("/cadastro")
    public String cadastro(@AuthenticationPrincipal User user) {
        if(user != null ) {
            for(GrantedAuthority grantedAuthority : user.getAuthorities() ) {
                if (grantedAuthority.getAuthority().equals("ROLE_ADMIN"))
                    return "redirect:/admin";
            }
            return "redirect:/";
        }
        return "site/register";
    }
    
    @GetMapping("/404") 
    public String acessoNegado() {
        return "403";
    }
}