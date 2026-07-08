package com.khan.wardroby.config;

import com.khan.wardroby.dto.CurrentUserView;
import com.khan.wardroby.model.Users;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributeAdvice {

    @ModelAttribute("currentUser")
    public CurrentUserView getCurrentUser(@AuthenticationPrincipal Users principal){
        if(principal == null)
        {
            return null;
        }
        return new CurrentUserView(principal.getFirstName(), principal.getLastName());

    }
}
