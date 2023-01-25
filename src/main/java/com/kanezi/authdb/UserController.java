package com.kanezi.authdb;

import com.kanezi.authdb.fomanticUI.Toast;
import com.kanezi.authdb.security.UsersService;
import com.kanezi.authdb.security.model.AppUser;
import io.vavr.collection.List;
import io.vavr.control.Try;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.atomic.AtomicReference;

@Controller
@Log4j2
public record UserController(
        UsersService usersService
) {

    @GetMapping("/user")
    public String userInfo(Model model) {
        model.addAttribute("message", "user controller");
        return "user";
    }


    record RegistrationForm(
            @NotBlank
            @Email
            String email,
            @NotBlank
            @Size(min = 3, max = 50, message = "{user.registration.error.username}")
            String username,
            @NotBlank
            @Size(min = 3, message = "{user.registration.error.password}")
            String password

    ) {
    }


    @GetMapping("/register")
    public String showRegistrationForm(@ModelAttribute RegistrationForm registrationForm) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid RegistrationForm registrationForm, BindingResult result,
                               RedirectAttributes attributes) {
        log.info("submitted registration: {}", registrationForm);
        attributes.addFlashAttribute(registrationForm);

        if (result.hasErrors()) {
            log.error("Register form has errors: {}", result.getAllErrors());
            return "/register";
        }

        AtomicReference<String> returnUrlRef = new AtomicReference<>();
        Try.of(() -> usersService.registerUser(registrationForm.email, registrationForm.username,
                                               registrationForm.password))
           .onSuccess(appUser -> {
               attributes.addFlashAttribute("toast", Toast.success("User created",
                                                                   String.format("User %s created!",
                                                                                 appUser.getUsername())));
               attributes.addFlashAttribute("email", appUser.getUsername());
               returnUrlRef.set("redirect:/login");
           })
           .onFailure(e -> {
               attributes.addFlashAttribute("toast", Toast.error("Error", e.getMessage()));
               returnUrlRef.set("/register");
           });


        return returnUrlRef.get();
    }
}
