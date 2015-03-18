package ua.kpi.dzidzoiev.booking.model;

import javax.persistence.*;

/**
 * Created by dzidzoiev on 2/21/15.
 */
@Entity
@Table(name = "Cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer population;

    @OneToOne(fetch=FetchType.LAZY, mappedBy="city")
    private CityPhoto photo;

    public City() {
    }

    public City(Integer id) {
        this.id = id;
    }

    public City(Integer id, String name, Integer population) {
        this.id = id;
        this.name = name;
        this.population = population;
    }

    public CityPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(CityPhoto photo) {
        this.photo = photo;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (!id.equals(city.id)) return false;
        if (name != null ? !name.equals(city.name) : city.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
