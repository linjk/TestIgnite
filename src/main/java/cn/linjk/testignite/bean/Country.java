package cn.linjk.testignite.bean;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class Country {
    @QuerySqlField(index = true)
    private long id;
    @QuerySqlField(index = true)
    private String name;
    private long population;

    public Country(long id, String name, long population) {
        this.id = id;
        this.name = name;
        this.population = population;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", population=" + population +
                '}';
    }
}
