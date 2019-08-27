package com.trevorjdobson.codefellowship.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    public UserDetails findByUsername(String username);
}
