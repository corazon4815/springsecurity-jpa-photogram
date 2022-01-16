package com.cos.photogram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{ //web설정파일

	@Value("${file.path}")
	private String uploadFolder;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		registry
		.addResourceHandler("/upload/**") // jsp에서 /upload/** 이런패턴이 나오면 -> 낚아채서
		.addResourceLocations("file:///"+uploadFolder) // 이게 앞에 붙어서 발동한다는뜻
		.setCachePeriod(60*10*6)
		.resourceChain(true)
		.addResolver(new PathResourceResolver());
	}
}
