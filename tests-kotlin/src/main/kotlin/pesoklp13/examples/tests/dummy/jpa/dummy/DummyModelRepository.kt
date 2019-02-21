package pesoklp13.examples.tests.dummy.jpa.dummy

import org.hibernate.annotations.GenericGenerator
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.*

@Repository
interface DummyModelRepository : CrudRepository<DummyModel, Long> {

    fun findByExternal(external: Boolean): List<DummyModel>

}

@Entity
@Table(name = "dummy_model")
@GenericGenerator(
    name = "dummy_model_seq",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = [org.hibernate.annotations.Parameter(name = "sequence_name", value = "dummy_model_seq")]
)
data class DummyModel(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dummy_model_seq")
    val id: Long,
    val name: String,
    val isExternal: Boolean
)