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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


    @GetMapping("/users")
    private String getAll(@Param("keyword") String keyword, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
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
    private String editUsers(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User editUser = userRepository.findById(id).get();
            model.addAttribute("user", editUser);
            model.addAttribute("pageTitle", "Sửa (ID: " + id + ")");
            return "editUser";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @PostMapping("/edit/user/save")
    public String saveUpdateUser(User user, RedirectAttributes redirectAttributes, BindingResult result) {
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "Email đã tồn tại");
        } else {
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("message", "Thành Công");
            return "redirect:/users";
        }
        return "editUser";
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
