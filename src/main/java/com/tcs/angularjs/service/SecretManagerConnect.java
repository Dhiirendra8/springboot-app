package com.tcs.angularjs.service;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

//@Slf4j
@Configuration
public class SecretManagerConnect {

//	@Value("${cloud.aws.apiCallTimeoutinSeconds}")
//	private int apiCallTimeoutinSeconds;

//	@Value("${cloud.aws.roleArn}")
//	private String roleArn;
//
//	@Value("${cloud.aws.credentials.access-key}")
//	private String accessId;
//
//	@Value("${cloud.aws.credentials.secret-key}")
//	private String secretKey;
//	
	@Value("${cloud.aws.secretName}")
	private String secretName;

//	static Region defaultRegion = Region.EU_WEST_2;
	static Region defaultRegion = Region.US_EAST_1;
	
//	@Value("${proxyHost}")
	private String proxyHost = System.getenv("GLOBAL_AGENT_HTTP_PROXY");
	
//	@Value("${proxyPort}")
	private String proxyPort = System.getenv("PROXY_PORT");
	
//	@Value("${aws.region}")
//	private String awsRegion;
	
	private static final String HTTPS_PROXY_HOST = "https.proxyHost";
	private static final String HTTPS_PROXY_PORT = "https.proxyPort";
	private static final String HTTP_PROXY_HOST = "http.proxyHost";
	private static final String HTTP_PROXY_PORT = "http.proxyPort";
	private static final String HTTPS_NON_PROXY_HOSTS = "https.nonProxyHosts";
	private static final String HTTP_NON_PROXY_HOSTS = "http.nonProxyHosts";
	
	private void setEnvironmentVariables() {
//		System.setProperty("aws.region", awsRegion);
		System.setProperty(HTTPS_PROXY_HOST, proxyHost);
		System.setProperty(HTTPS_PROXY_PORT, proxyPort);
		System.setProperty(HTTP_PROXY_HOST, proxyHost);
		System.setProperty(HTTP_PROXY_PORT, proxyPort);
		System.setProperty(HTTPS_NON_PROXY_HOSTS, "*.internal");
		System.setProperty(HTTP_NON_PROXY_HOSTS, "*.internal");
	}

	private void clearEnvironmentVariables() {
		System.clearProperty(HTTPS_PROXY_HOST);
		System.clearProperty(HTTPS_PROXY_PORT);
		System.clearProperty(HTTP_PROXY_HOST);
		System.clearProperty(HTTP_PROXY_PORT);
		System.clearProperty(HTTPS_NON_PROXY_HOSTS);
		System.clearProperty(HTTP_NON_PROXY_HOSTS);
	}

//	@Cacheable(cacheNames = "smCache", key = "#keyId")
	public String connectSM(String keyId) {
//		log.info("Within Secret Manager Connection");
		
		try {
				
				setEnvironmentVariables();				
//				log.debug("Inside switchAwsACMWebToken");

				// secretDetails = SecretManagerConnect.secretManagerValues(WebIdentityTokenFileCredentialsProvider.create(),"DemoTest");
				
//				String keyStore = SecretManagerConnect.secretManagerValues(WebIdentityTokenFileCredentialsProvider.create(),
//						secretName.replace("app", keyId),keyId);
				String keyStore = SecretManagerConnect.secretManagerValues(WebIdentityTokenFileCredentialsProvider.create(),
						secretName,"");
				clearEnvironmentVariables();
				return keyStore;


		} catch (SecretsManagerException ex) {
//			log.error("Within SecretManagerConnect class-SecretsManagerException:{}", ex.getMessage());
			return null;
		} catch (AwsServiceException ex) {
//			log.error("Within SecretManagerConnect class-AwsServiceException:{}", ex.getMessage());
			return null;
		} catch (Exception ex) {
//			log.error("Within SecretManagerConnect class-Exception:{}", ex.getMessage());
			return null;
		}

	}

	public static String secretManagerValues(AwsCredentialsProvider awsCredentials, 
			String secretName, String keyId) {

		JSONObject jsonObject = null;

		try {
			
//			log.debug("First Step of SecretManager");
			
			SecretsManagerClient secretManagerClient = SecretsManagerClient.builder()
					.credentialsProvider(awsCredentials).region(defaultRegion).build();

			GetSecretValueRequest fetchSecrets = GetSecretValueRequest.builder().secretId(secretName).build();

//			log.debug("Second Step of SecretManager");

			GetSecretValueResponse secretsValue = secretManagerClient.getSecretValue(fetchSecrets);

//			log.debug(secretsValue.secretString());

			jsonObject = new JSONObject(secretsValue.secretString());
			secretManagerClient.close();
//			return jsonObject.getString(keyId);
			return jsonObject.toString();

		} catch (SecretsManagerException ex) {
//			log.error("Within SecretManagerConnect class-SecretsManagerException:{}", ex.getMessage());
			return null;
		} catch (AwsServiceException ex) {
//			log.error("Within SecretManagerConnect class-AwsServiceException:{}", ex.getMessage());
			return null;
		} catch (Exception ex) {
//			log.error("Within SecretManagerConnect class-Exception:{}", ex.getMessage());
			return null;
		}

	}
//	
//	@Bean
//	public AcmClient getInstanceForACM() {
//		
////		log.info("New connection initalization for Https");
//
//		try {
//				
//				setEnvironmentVariables();
//
////				log.info("Inside switchAwsACMWebToken for ACM");
//
//				AcmClient acmClient = AcmClient.builder()
//						.credentialsProvider(WebIdentityTokenFileCredentialsProvider.create()).region(defaultRegion)
//						.build();
//				clearEnvironmentVariables();
//				return acmClient;
//			
//		} catch (AwsServiceException ex) {
////			log.error("Within SecretManagerConnect class,getInstanceForACM-AwsServiceException:{}", ex.getMessage());
//			return null;
//		} catch (Exception ex) {
////			log.error("Within SecretManagerConnect class,getInstanceForACM-Exception:{}", ex.getMessage());
//			return null;
//		}
//
//	}


}
