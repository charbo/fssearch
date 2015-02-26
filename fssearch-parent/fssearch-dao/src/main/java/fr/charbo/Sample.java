package fr.charbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.charbo.domain.User;
import fr.charbo.repository.UserRepository;

@Component
public class Sample {

	private UserRepository userRepository;
	
	@Autowired
	public Sample(UserRepository userRepository) {
		final User user = new User();
		this.userRepository = userRepository;
		System.out.println("-----------");
		System.out.println(this.userRepository.save(user));
		System.out.println(this.userRepository.findAll().spliterator().estimateSize());
		
	}
	
}
