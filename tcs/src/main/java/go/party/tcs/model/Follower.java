package go.party.tcs.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Follower {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario follower;

    @ManyToOne
    private Usuario following;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Usuario getFollower() {
        return follower;
    }
    public void setFollower(Usuario follower) {
        this.follower = follower;
    }
    public Usuario getFollowing() {
        return following;
    }
    public void setFollowing(Usuario following) {
        this.following = following;
    }

}