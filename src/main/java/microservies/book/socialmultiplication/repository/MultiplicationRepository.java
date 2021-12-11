package microservies.book.socialmultiplication.repository;

import org.springframework.data.repository.CrudRepository;

import microservies.book.socialmultiplication.domain.Multiplication;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
}
