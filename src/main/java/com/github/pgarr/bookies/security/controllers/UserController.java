package com.github.pgarr.bookies.security.controllers;

import com.github.pgarr.bookies.security.dto.UserDTO;
import com.github.pgarr.bookies.security.exceptions.UserAlreadyExistException;
import com.github.pgarr.bookies.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        final UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult) {

        ModelAndView mav;
        if (bindingResult.hasErrors()) {
            mav = new ModelAndView("registration");
        } else {
            try {
                userService.registerNewUserAccount(userDTO);
                // TODO: send email for verification
                mav = new ModelAndView("successRegister");
            } catch (UserAlreadyExistException uaeEx) {
                mav = new ModelAndView("registration");
                // TODO: error locale (MessageSource)
                ObjectError error = new ObjectError("globalError", "An account for that email already exists.");
                bindingResult.addError(error);
            }
        }
        mav.addObject("user", userDTO);
        return mav;
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam("error") final Optional<String> error) {
        error.ifPresent(e -> model.addAttribute("error", e));

        return "login";
    }
}
