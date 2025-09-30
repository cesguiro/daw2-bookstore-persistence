package es.cesguiro.persistence.dao.jpa.entity;

import jakarta.persistence.*;

@Entity
 @Table(name = "publishers")
public class PublisherJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;

    public PublisherJpaEntity(Long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public PublisherJpaEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
