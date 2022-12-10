package com.github.piranha887.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Transactional
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users-list";
    }

    @GetMapping("/new")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return showEditUserForm(model);
    }

    @GetMapping("/edit")
    public String showUpdateForm(@RequestParam long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user", user);
        return showEditUserForm(model);
    }

    @PostMapping("/save")
    public String saveUser(@Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return showEditUserForm(model);
        }
        long id = user.getId();
        if (id != 0) {
            User old = userRepository.findById(user.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
            if (!user.getPassword().equals(old.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }

        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/users";
    }

    private String showEditUserForm(Model model) {
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }
}
