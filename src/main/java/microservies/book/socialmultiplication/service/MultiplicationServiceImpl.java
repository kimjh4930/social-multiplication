package microservies.book.socialmultiplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;
import microservies.book.socialmultiplication.domain.Multiplication;
import microservies.book.socialmultiplication.domain.MultiplicationResultAttempt;
import microservies.book.socialmultiplication.domain.User;
import microservies.book.socialmultiplication.repository.MultiplicationResultAttemptRepository;
import microservies.book.socialmultiplication.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class MultiplicationServiceImpl implements MultiplicationService {
	private final RandomGeneratorService randomGeneratorService;
	private final MultiplicationResultAttemptRepository attemptRepository;
	private final UserRepository userRepository;

	@Override
	public Multiplication createRandomMultiplication() {
		int factorA = randomGeneratorService.generateRandomFactor();
		int factorB = randomGeneratorService.generateRandomFactor();
		return new Multiplication(factorA, factorB);
	}

	@Transactional
	@Override
	public boolean checkAttempt(MultiplicationResultAttempt resultAttempt) {
		Optional<User> user = userRepository.findByAlias(resultAttempt.getUser().getAlias());

		Assert.isTrue(!resultAttempt.isCorrect(), "채점한 채로 보낼 수 없습니다.");

		boolean correct = resultAttempt.getResultAttempt() ==
			resultAttempt.getMultiplication().getFactorA() * resultAttempt.getMultiplication().getFactorB();

		Assert.isTrue(!resultAttempt.isCorrect(), "채점한 상태로 내보낼 수 없습니다.");

		MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
			resultAttempt.getUser(),
			resultAttempt.getMultiplication(),
			resultAttempt.getResultAttempt(),
			correct
		);

		attemptRepository.save(checkedAttempt);

		return correct;
	}

	@Override
	public List<MultiplicationResultAttempt> getStatsForUser (String userAlias){
		return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
	}
}
