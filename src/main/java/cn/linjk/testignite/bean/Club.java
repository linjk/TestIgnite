package cn.linjk.testignite.bean;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "club")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
