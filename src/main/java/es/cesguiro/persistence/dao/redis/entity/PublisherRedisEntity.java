package es.cesguiro.persistence.dao.redis.entity;

import jakarta.persistence.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@RedisHash("Publisher")
public class PublisherRedisEntity implements Serializable {

    @Id
    private Long id;
    private String name;
    private String slug;

    private List<Long> bookIds;

    public PublisherRedisEntity() {

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

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }
}
