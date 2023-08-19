package cn.linjk.testignite.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "club")
public class Club {
    @Id
    @Column
    int clubno;

    @Column(name = "cname")
    String name;

    public int getClubno() {
        return clubno;
    }

    public void setClubno(int clubno) {
        this.clubno = clubno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Club{" +
                "clubno=" + clubno +
                ", name='" + name + '\'' +
                '}';
    }
}
