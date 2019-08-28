package com.trevorjdobson.codefellowship.controllers;


import com.trevorjdobson.codefellowship.models.Post;
import com.trevorjdobson.codefellowship.models.PostRepository;
import com.trevorjdobson.codefellowship.models.UserProfile;
import com.trevorjdobson.codefellowship.models.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.management.loading.PrivateClassLoader;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class UserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/users")
    public RedirectView createUser(String username,String password,String firstName, String lastName,String bio){
        UserProfile newUser = new UserProfile(username,encoder.encode(password),firstName,lastName,bio);
        userProfileRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");
    }
    @PostMapping("/post")
    public RedirectView createPost(String body,Principal p){
        UserProfile user = (UserProfile) userProfileRepository.findByUsername(p.getName());
        Post newPost = new Post(body,user);
        postRepository.save(newPost);
        return new RedirectView("/myprofile");
    }
    @GetMapping("/myprofile")
    public String getMyProfile(Principal p, Model m){
        System.out.println(p);
        UserProfile user = (UserProfile) userProfileRepository.findByUsername(p.getName());
        m.addAttribute("user", user);
        return "myprofile";
    }
    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/users/{id}")
    public String getOneAlbum(@PathVariable long id, Model m, Principal princ) {
        UserProfile p = userProfileRepository.findById(id).get();
        System.out.println(p);
        m.addAttribute("userBeingViewed", p);
        UserProfile user = (UserProfile) userProfileRepository.findByUsername(princ.getName());
        m.addAttribute("user", user);

        return "userdetails";
    }

}
