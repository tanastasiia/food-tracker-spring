package ua.training.foodtracker.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.training.foodtracker.entity.Role;
import ua.training.foodtracker.entity.UserDetailsImpl;

@Slf4j
@Controller
public class PageController {

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getAuthorities()
                .contains(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()));
    }

    @ModelAttribute("principal")
    public UserDetailsImpl principal() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/admin")
    public String admin() {
        log.info("admin page");
        return "admin/index";
    }

    @RequestMapping("/account")
    public String account() {
        log.info("account page");
        return "user/account";
    }

    @GetMapping("/account/change")
    public String accountChange() {
        log.info("account change page");
        return "user/account_change";
    }

    @GetMapping("/account/change/password")
    public String accountChangePassword() {
        log.info("account change password page");
        return "user/password_change";
    }

    @GetMapping(value = {"/statistics"})
    public String personList() {
        log.info("statistics page");
        return "user/statistics";
    }

    @GetMapping("/")
    public String home() {
        log.info("home page");
        return "user/index";
    }


}
