package microservies.book.socialmultiplication.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@EqualsAndHashCode(of = "id")
@ToString
public class Multiplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "multiplication_id")
	private Long id;

	private int factorA;

	private int factorB;

	private int result;

	protected Multiplication () {}

	public Multiplication(int factorA, int factorB) {
		this.factorA = factorA;
		this.factorB = factorB;
		this.result = factorA * factorB;
	}

	public int getFactorA() {
		return factorA;
	}

	public int getFactorB() {
		return factorB;
	}

	public int getResult() {
		return result;
	}

	@Override
	public String toString() {
		return "Multiplication{" +
			"factorA=" + factorA +
			", factorB=" + factorB +
			", result=" + result +
			'}';
	}


}
