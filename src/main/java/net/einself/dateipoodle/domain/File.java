package net.einself.dateipoodle.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class File extends PanacheEntityBase {

    @Id
    public String id;
    public String name;

}
