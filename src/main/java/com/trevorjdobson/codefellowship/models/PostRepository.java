package com.trevorjdobson.codefellowship.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface PostRepository extends JpaRepository<Post, Long>  {

}
