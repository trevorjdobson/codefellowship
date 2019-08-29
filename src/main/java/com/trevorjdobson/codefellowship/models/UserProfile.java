package com.trevorjdobson.codefellowship.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class UserProfile implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    String password;

    String firstName;
    String lastName;
    String bio;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userProfile")
    List<Post> posts;

    @ManyToMany
    @JoinTable(
            name="friends",
            joinColumns = { @JoinColumn(name="follower") },
            inverseJoinColumns = {@JoinColumn(name="followee")}
        )
    Set<UserProfile> usersThatIFollow;

    @ManyToMany(mappedBy = "usersThatIFollow")
    Set<UserProfile> usersThatFollowMe;

    public List<Post> getPosts() {
        return posts;
    }

    public Set<UserProfile> getUsersThatIFollow() {
        return usersThatIFollow;
    }

    public Set<UserProfile> getUsersThatFollowMe() {
        return usersThatFollowMe;
    }
    public void addLike(UserProfile likee){
        usersThatIFollow.add(likee);
    }

    public UserProfile(String username, String password, String firstName, String lastName, String bio){

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
    }
    public UserProfile(){};

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public String getBio() {
        return bio;
    }
    public Long getId(){return id;}
    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
