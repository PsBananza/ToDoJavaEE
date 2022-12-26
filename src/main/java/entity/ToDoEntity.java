package entity;

import exception.LengthLimit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "todo")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS )
public class ToDoEntity {
    private Date createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean status = false;
    private String text;

    private Date updatedAt;

    public void setText(String text)throws LengthLimit {
        if (text.length() < 3 || text.length() > 160) {
            throw new LengthLimit("Введите больше 3 симвалов и меньше 160");
        }
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ToDoEntity that = (ToDoEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
