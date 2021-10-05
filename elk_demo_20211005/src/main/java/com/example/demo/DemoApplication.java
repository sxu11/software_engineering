package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@AllArgsConstructor
@NoArgsConstructor
@Data
class User{
	private int id;
	private String name;
}

@SpringBootApplication
@RestController
public class DemoApplication {
	Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	private List<User> getUsers(){
		return Stream.of(new User(1, "Song"),
				new User(2, "Jane"),
				new User(3, "Rony"),
				new User(4, "Mark"))
				.collect(Collectors.toList());
	}

	@GetMapping("/getUser/{id}")
	public User getUserById(@PathVariable int id){
		List<User> users = getUsers();
		User user = users.stream().filter(u->u.getId()==id).findAny().orElse(null);
		if (user!=null){
			logger.info("user found: {}", user);
			return user;
		}else{
			try{
				throw new Exception();
			}catch (Exception e){
				e.printStackTrace();
				logger.error("User not found with ID: {}", id);
			}
			return new User();
		}
	}

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

	}

}
