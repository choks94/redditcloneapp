package com.choks.redditclone;

import com.choks.redditclone.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class RedditcloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditcloneApplication.class, args);
	}

}
