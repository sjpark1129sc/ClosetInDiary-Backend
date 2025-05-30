package me.parkseongjong.springbootdeveloper.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "onelineInfo")
    private String onelineInfo;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "public_settings")
    private String publicSettings;

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    private List<FriendRequest> sentFriendRequests;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    private List<FriendRequest> receivedFriendRequests;

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    private List<Message> receivedMessages;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Status> statuses;

    @Builder
    public User(String username, String email, String password, String name, String profilePicture, String publicSettings) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.profilePicture = (profilePicture != null) ? profilePicture : "default_profile_picture_url";
        this.publicSettings = (publicSettings != null) ? publicSettings : "private";
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
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
