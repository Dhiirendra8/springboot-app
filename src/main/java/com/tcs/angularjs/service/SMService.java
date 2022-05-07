package com.tcs.angularjs.service;

import org.springframework.stereotype.Service;



import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

@Service
public class SMService {
	
	 public  String getValue(SecretsManagerClient secretsClient,String secretName) {

	        try {
	            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()	           
	            .secretId(secretName)
	                .build();

	            GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);
	            		

	            String secret = valueResponse.secretString();
	            System.out.println(secret);
	            return secret;

	        } catch (SecretsManagerException e) {
	            System.err.println(e.awsErrorDetails().errorMessage());
//	            System.exit(1);
	            return "No Secret Found";
	        }
	    }
}
