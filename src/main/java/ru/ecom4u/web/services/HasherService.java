package ru.ecom4u.web.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HasherService {
	private static final String defAlgoritm = "MD5";
	private static final String defSalt = "salt";

	@Value("#{properties['hasher.algoritm']}")
	private String algoritm;

	@Value("#{properties['hasher.salt']}")
	private String salt;

	public String calculateHash(String str) {
		MessageDigest messageDigest = null;

		if (null == this.algoritm)
			this.algoritm = defAlgoritm;

		if (null == this.salt)
			this.salt = defSalt;

		try {
			messageDigest = MessageDigest.getInstance(this.algoritm);
			messageDigest.update(str.getBytes());
			messageDigest.update(this.salt.getBytes());
			byte[] hashBytes = messageDigest.digest();

			return DatatypeConverter.printBase64Binary(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			return str;
		}
	}
}
