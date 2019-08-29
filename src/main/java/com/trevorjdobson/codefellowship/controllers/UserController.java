package com.trevorjdobson.codefellowship.controllers;


import com.trevorjdobson.codefellowship.models.Post;
import com.trevorjdobson.codefellowship.models.PostRepository;
import com.trevorjdobson.codefellowship.models.UserProfile;
import com.trevorjdobson.codefellowship.models.UserProfileRepository;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
import java.util.List;
import java.util.Set;

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
    @PostMapping("/user/{id}/addfavorite")
    public RedirectView addFavorite(@PathVariable Long id,Long userId){
        UserProfile liker = userProfileRepository.getOne(id);
        UserProfile likee = userProfileRepository.getOne(userId);
        liker.addLike(likee);
        userProfileRepository.save(liker);
        return new RedirectView("/users");
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

        UserProfile user = (UserProfile) userProfileRepository.findByUsername(p.getName());
        m.addAttribute("user", user);
        return "myprofile";
    }
    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/users/{id}")
    public String getOneUser(@PathVariable long id, Model m, Principal princ) {
        UserProfile p = userProfileRepository.findById(id).get();

        m.addAttribute("userBeingViewed", p);
        UserProfile user = (UserProfile) userProfileRepository.findByUsername(princ.getName());
        m.addAttribute("user", user);

        return "userdetails";
    }
    @GetMapping("/users")
    public String getAllUsers(Model m, Principal princ) {
        List<UserProfile> allUsersList = userProfileRepository.findAll();
        System.out.println(allUsersList);
        m.addAttribute("allUsers", allUsersList);

        UserProfile user = (UserProfile) userProfileRepository.findByUsername(princ.getName());
        m.addAttribute("user", user);

        return "allusers";
    }
    @GetMapping("/feed")
    public String getFeed(Model m, Principal p){
        UserProfile user = (UserProfile) userProfileRepository.findByUsername(p.getName());
        ArrayList<Post> allPosts = new ArrayList<>();
        Set<UserProfile> following = user.getUsersThatIFollow();
        for(UserProfile followedUser: user.getUsersThatIFollow()){
            for(Post post: followedUser.getPosts()){
                allPosts.add(post);
            }
        }


        m.addAttribute("user", user);
        m.addAttribute("following", following);
        m.addAttribute("posts", allPosts);



        return "feed";
    }

}
