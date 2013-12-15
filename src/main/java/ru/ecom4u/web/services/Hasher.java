package ru.ecom4u.web.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Hasher {
	@Value("#{properties['hasher.algoritm']}")
	private String algoritm;
	
	@Value("#{properties['hasher.salt']}")
	private String salt;
	
	
}
