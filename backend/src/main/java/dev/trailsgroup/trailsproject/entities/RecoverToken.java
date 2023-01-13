package dev.trailsgroup.trailsproject.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "tb_recovertoken")
@EntityListeners(AuditingEntityListener.class)
public class RecoverToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String recoverToken;

    private Instant expirationDate;

    @JoinColumn(unique=true)
    @OneToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public RecoverToken(){
    }

    public RecoverToken(Integer id, String recoverToken, User user, long expirationTime) {
        this.id = id;
        this.recoverToken = recoverToken;
        this.user = user;
        this.expirationDate = Instant.ofEpochMilli(System.currentTimeMillis()+expirationTime);
    }


    public Integer getId() {
        return id;
    }

    public String getRecoverToken() {
        return recoverToken;
    }

    public void setRecoverToken(String recoverToken) {
        this.recoverToken = recoverToken;
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
