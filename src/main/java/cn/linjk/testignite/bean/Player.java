package cn.linjk.testignite.bean;

import javax.persistence.*;

@Entity
@Table(name = "player")
public class Player {
    @Id
    @Column
    int playerno;

    @Column(name = "pname")
    String name;

    @Column
    int wages;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clubno", referencedColumnName = "clubno")
    Club club;

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
