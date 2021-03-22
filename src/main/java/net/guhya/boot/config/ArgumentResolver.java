package net.guhya.boot.config;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.guhya.boot.common.web.request.CustomWebArgumentResolver;

@Component
public class ArgumentResolver implements WebMvcConfigurer {

	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new CustomWebArgumentResolver());
	}
}
