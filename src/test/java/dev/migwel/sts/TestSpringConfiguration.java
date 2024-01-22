package dev.migwel.sts;

import dev.migwel.sts.website.WebConfig;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@TestConfiguration
@Import(value = {Application.class, WebConfig.class})
public class TestSpringConfiguration {
    @Bean
    OAuth2AuthorizedClientManager authorizedClientManager() {
        return Mockito.mock(OAuth2AuthorizedClientManager.class);
    }

    @Bean
    OAuth2AuthorizedClientService oAuth2AuthorizedClientService() {
        return Mockito.mock(OAuth2AuthorizedClientService.class);
    }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        return Mockito.mock(ClientRegistrationRepository.class);
    }
}
