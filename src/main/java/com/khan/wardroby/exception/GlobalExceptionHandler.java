package com.khan.wardroby.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleUploadSizeError(MaxUploadSizeExceededException ex, Model model)
    {
        model.addAttribute("errorMessage", ex.getMessage());
        return "add-item-form";
    }

    @ExceptionHandler({InvalidItemDefinitionException.class,ItemException.class})
    public String handleItemErrors(ItemException ex, Model model)
    {
        model.addAttribute("errorMessage", ex.getMessage());
        return "dashboard";
    }

    @ExceptionHandler({PasswordResetTokenExpiredException.class,
            InvalidPasswordResetTokenException.class,TokenUsedException.class, AuthorityNotFoundException.class})
    public String handlePasswordResetError(AuthException ex, Model model)
    {
        logger.warn("Password reset failed {}", ex.getMessage());
        model.addAttribute("errorMessage",ex.getMessage());
        return "reset-password-failed";
    }

    @ExceptionHandler(UserException.class)
    public String handleUserErrors(UserException e, Model model)
    {
        logger.warn("User related error : {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "registration-page";
    }
    @ExceptionHandler(WardrobyBaseException.class)
    public String handleGeneralAppErrors(WardrobyBaseException e, Model model)
    {
        logger.error("App related error : {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }


    @ExceptionHandler(Exception.class)
    public String handleSystemErrors(Exception e, Model model)
    {
        logger.error("Critical system error : {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }


    @ExceptionHandler(ResetTokenException.class)
    public String handleResetTokenErrors(ResetTokenException e, Model model)
    {
        logger.warn("Reset Token error : {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "login";
    }




}
