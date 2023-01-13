package dev.trailsgroup.trailsproject.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "tb_refreshtoken")
@EntityListeners(AuditingEntityListener.class)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String refreshToken;

    private Instant expirationDate;

    @JoinColumn()
    @OneToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public RefreshToken(){
    }

    public RefreshToken(Integer id, String refreshToken, User user, long expirationTime) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.user = user;
        this.expirationDate = Instant.ofEpochMilli(System.currentTimeMillis()+expirationTime);
    }


    public Integer getId() {
        return id;
    }

    public String getAccessToken() {
        return refreshToken;
    }

    public void setAccessToken(String accessToken) {
        this.refreshToken = accessToken;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
