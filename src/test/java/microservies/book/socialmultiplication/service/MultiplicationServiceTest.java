package microservies.book.socialmultiplication.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import microservies.book.socialmultiplication.domain.Multiplication;
import microservies.book.socialmultiplication.domain.MultiplicationResultAttempt;
import microservies.book.socialmultiplication.domain.User;
import microservies.book.socialmultiplication.repository.MultiplicationResultAttemptRepository;
import microservies.book.socialmultiplication.repository.UserRepository;

class MultiplicationServiceTest {

	private MultiplicationServiceImpl multiplicationServiceImpl;

	@Mock
	private RandomGeneratorService randomGeneratorService;

	@Mock
	private MultiplicationResultAttemptRepository attemptRepository;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		multiplicationServiceImpl = new MultiplicationServiceImpl(
			randomGeneratorService,
			attemptRepository,
			userRepository
		);
	}

	@Test
	void createRandomMultiplicationTest() {
		//given
		given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

		//when
		Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

		//then
		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
		assertThat(multiplication.getResult()).isEqualTo(1500);
	}

	@Test
	void checkCorrectAttemptTest() {
		//given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt attempt =
			new MultiplicationResultAttempt(user, multiplication, 3000, false);
		MultiplicationResultAttempt verifiedAttempt =
			new MultiplicationResultAttempt(user, multiplication, 3000, true);

		given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());


		//when
		boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

		//then
		assertThat(attemptResult).isTrue();
		verify(attemptRepository).save(verifiedAttempt);
	}

	@Test
	void checkWrongAttemptTest() {
		//given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
			user, multiplication, 3010, false);

		//when
		boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

		//then
		assertThat(attemptResult).isFalse();
		// verify(attemptRepository).save(attempt);
	}

	@Test
	void retrieveStatsTest() {
		//given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("john_doe");
		MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication, 3010, false);
		MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication, 3051, false);

		List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);

		given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
		given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("john_doe")).willReturn(latestAttempts);

		//when
		List<MultiplicationResultAttempt> latestAttemptResult =
			multiplicationServiceImpl.getStatsForUser("john_doe");

		//then
		assertThat(latestAttemptResult).isEqualTo(latestAttempts);
	}

}