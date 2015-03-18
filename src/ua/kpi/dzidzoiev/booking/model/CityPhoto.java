package ua.kpi.dzidzoiev.booking.model;

import javax.persistence.*;

@Entity
@Table(name = "city_photos")
public class CityPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String url;

    public CityPhoto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
