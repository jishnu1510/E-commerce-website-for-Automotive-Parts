package com.mywork.website;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.mywork.website.model.User;
import com.mywork.website.repository.UserRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
    private TestEntityManager entityManager;
     
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("jishnu1234@gmail.com");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "123456";
		String encodedPassword = encoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        user.setFirstName("Jishnu");
        user.setLastName("Nambiar");
         
        User savedUser = userRepository.save(user);
         
        User existUser = entityManager.find(User.class, savedUser.getId());
         
        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
    }
    
    @Test
    public void testFindUserByEmail() {
    	String email = "jishnu1234@gmail.com";
    	Optional<User> user = userRepository.findUserByEmail(email);
    	assertThat(user).isNotNull();
    }
}
