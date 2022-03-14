package dev.trailsgroup.trailsproject.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "tb_recovertoken")
@EntityListeners(AuditingEntityListener.class)
public class RecoverToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String accessToken;

    private Instant expirationDate;

    @JoinColumn(unique=true)
    @OneToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public RecoverToken(){
    }

    public RecoverToken(Integer id, String accessTokenUser, User user, long expirationTime) {
        this.id = id;
        this.accessToken = accessTokenUser;
        this.user = user;
        this.expirationDate = Instant.ofEpochMilli(System.currentTimeMillis()+expirationTime);
    }


    public Integer getId() {
        return id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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
