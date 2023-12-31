package cn.linjk.testignite.bean;

import org.apache.ignite.cache.query.annotations.QueryTextField;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "player")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Player implements Serializable {
    @Id
    @Column
    int playerno;

    @QueryTextField
    @Column(name = "pname")
    String name;

    @Column
    int wages;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clubno", referencedColumnName = "clubno")
    Club club;

    public Player() {

    }

    public Player(int playerno, String name, int wages) {
        this.playerno = playerno;
        this.name = name;
        this.wages = wages;
    }

    public int getPlayerno() {
        return playerno;
    }

    public void setPlayerno(int playerno) {
        this.playerno = playerno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWages() {
        return wages;
    }

    public void setWages(int wages) {
        this.wages = wages;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerno=" + playerno +
                ", name='" + name + '\'' +
                ", wages=" + wages +
                ", club=" + club +
                '}';
    }
}
