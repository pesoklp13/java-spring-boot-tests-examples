package pesoklp13.examples.tests.dummy.jpa.dummy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "dummy_model")
@GenericGenerator(
        name = "dummy_model_seq",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "sequence_name", value = "dummy_model_seq"),
        }
)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DummyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dummy_model_seq")
    private long id;

    @Column
    private String name;

    @Column
    private boolean external;

}