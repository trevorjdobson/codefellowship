package com.trevorjdobson.codefellowship.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String body;
    String timeStamp;

    @ManyToOne
    UserProfile userProfile;

    public Post(String body, UserProfile userProfile){
        this. body = body;
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        this.userProfile = userProfile;
    }

    public Post(){}

    public String getBody() {
        return body;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
    public String toString(){
        return timeStamp + body;
    }
}
