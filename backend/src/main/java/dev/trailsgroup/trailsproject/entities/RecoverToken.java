package dev.trailsgroup.trailsproject.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_recovertoken")
@EntityListeners(AuditingEntityListener.class)
public class RecoverToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String accessToken;

    @CreatedDate
    private Date createdDate;

    @JoinColumn(unique=true)
    @OneToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public RecoverToken(){
    }

    public RecoverToken(Integer id, String accessTokenUser, User user) {
        this.id = id;
        this.accessToken = accessTokenUser;
        this.user = user;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
