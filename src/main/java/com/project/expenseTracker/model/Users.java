package com.project.expenseTracker.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "userProfileId")
    private UserProfileInfo userProfileInfo;

    public Users() {}

    public Users(String username, String email) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public Users(String username, String email, String password, UserProfileInfo userProfileInfo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userProfileInfo = userProfileInfo;
    }
}
