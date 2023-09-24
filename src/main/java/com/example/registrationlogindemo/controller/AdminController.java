package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.UserRepository;
import com.example.registrationlogindemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserService userService;

    //    @GetMapping("/users")
//    public String listRegisteredUsers(Model model) {
//        List<UserDto> users = userService.findAllUsers();
//        model.addAttribute("users", users);
//        return "users";
//    }
//    @GetMapping("/user/new")
//    public String addUser(Model model) {
//        User user = new User();
//
//        model.addAttribute("user", user);
//        model.addAttribute("pageTitle", "Tạo Thanh Toán Thành Công");
//
//        return "register";
//    }

    @GetMapping("/users")
    private String getAll(@Param("keyword") String keyword, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null){
            return "login";
        }
        try {
            List<User> users = new ArrayList<User>();
            if (keyword == null) {
                userRepository.search(keyword).forEach(users::add);
            } else {
                userRepository.findByEmailContainingIgnoreCase(keyword).forEach(users::add);
                model.addAttribute("keyword", keyword);
            }
            model.addAttribute("users", users);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "users";
    }

    @GetMapping("/users/{id}")
    private String editTutorial(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userRepository.findById(id).get();

            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Sửa (ID: " + id + ")");

            return "register";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    private String deleteTutorial(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userRepository.deleteById(id);

            redirectAttributes.addFlashAttribute("message", "Bạn đã xóa:" + id + " merchant ");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/users";
    }
}
