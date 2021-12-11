package microservies.book.socialmultiplication.domain;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MultiplicationResultAttempt {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "multiplication_id")
	private Multiplication multiplication;

	private int resultAttempt;

	private boolean correct;

	public MultiplicationResultAttempt() { }

	public MultiplicationResultAttempt(
		User user,
		Multiplication multiplication,
		int resultAttempt,
		boolean correct)
	{
		this.user = user;
		this.multiplication = multiplication;
		this.resultAttempt = resultAttempt;
		this.correct = correct;
	}

	public User getUser() {
		return user;
	}

	public Multiplication getMultiplication() {
		return multiplication;
	}

	public int getResultAttempt() {
		return resultAttempt;
	}

	public boolean isCorrect() {
		return correct;
	}

	@Override
	public String toString() {
		return "MultiplicationResultAttempt{" +
			"user=" + user +
			", multiplication=" + multiplication +
			", resultAttempt=" + resultAttempt +
			'}';
	}
}
