package pesoklp13.examples.tests.dummy.jpa.dummy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DummyModelRepository extends CrudRepository<DummyModel, Long> {

    List<DummyModel> findByExternal(boolean external);
}
