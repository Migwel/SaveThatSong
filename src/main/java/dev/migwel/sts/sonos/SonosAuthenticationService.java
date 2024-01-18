package dev.migwel.sts.sonos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Service
public class SonosAuthenticationService {

    private final WebClient webClient;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    private final ClientRegistration clientRegistration;

    @Autowired
    public SonosAuthenticationService(
            WebClient webClient,
            OAuth2AuthorizedClientService oAuth2AuthorizedClientService,
            ClientRegistrationRepository clientRegistrationRepository) {
        this.webClient = webClient;
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
        this.clientRegistration = clientRegistrationRepository.findByRegistrationId("sonos");
    }

    public void oauthRegister(String username) {
        OAuth2AuthorizedClient oauth2Client =
                oAuth2AuthorizedClientService.loadAuthorizedClient(
                        clientRegistration.getRegistrationId(), username);
        if (oauth2Client != null) {
            return;
        }
        webClient
                .get()
                .uri(clientRegistration.getProviderDetails().getAuthorizationUri())
                .attributes(
                        ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId(
                                clientRegistration.getRegistrationId()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
