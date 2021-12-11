package microservies.book.socialmultiplication.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(of = "id")
public final class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	private String alias;

	public User() {
	}

	public User(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}
}
