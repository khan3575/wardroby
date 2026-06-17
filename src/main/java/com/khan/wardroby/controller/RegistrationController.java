package com.khan.wardroby.controller;

import com.khan.wardroby.dto.UserDTO;
import com.khan.wardroby.exception.UserAlreadyExistsException;
import com.khan.wardroby.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private UserService userService;

    @Autowired
    RegistrationController(UserService userService){
        this.userService=userService;
    }

    @GetMapping()
    public String showFrom(Model model)
    {
        model.addAttribute("user", new UserDTO());
        return "registration-page";
    }

    @PostMapping
    public String submitForm(@Valid @ModelAttribute("user") UserDTO userDto , BindingResult result)
    {
        logger.debug("Attempting to register user: {}", userDto.getEmail());

        if(result.hasErrors())
        {
            return "registration-page";
        }
        if(!userDto.getPassword().equals(userDto.getConfirmPassword()))
        {
            result.rejectValue("password", "user.invalid-pass", "Password miss matched");
            return "registration-page";
        }
        try{
            userService.registerNewUser(userDto);
        }
        catch(UserAlreadyExistsException e)
        {
            logger.warn("Registration failed - user already exists: {}", userDto.getEmail());
            result.rejectValue("email", "user.exits", e.getMessage());
            return "registration-page";
        }
        return "redirect:/login?registered";
    }


}
