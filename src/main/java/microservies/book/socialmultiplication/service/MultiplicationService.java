package microservies.book.socialmultiplication.service;

import java.util.List;

import microservies.book.socialmultiplication.domain.Multiplication;
import microservies.book.socialmultiplication.domain.MultiplicationResultAttempt;

public interface MultiplicationService {

	Multiplication createRandomMultiplication();

	boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);

	List<MultiplicationResultAttempt> getStatsForUser (String userAlias);

}
