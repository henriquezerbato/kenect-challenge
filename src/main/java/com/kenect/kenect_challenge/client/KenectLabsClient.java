package com.kenect.kenect_challenge.client;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.kenect.kenect_challenge.config.KenectLabConfig;
import com.kenect.kenect_challenge.exception.ExternalServiceException;
import com.kenect.kenect_challenge.model.Contact;
import com.kenect.kenect_challenge.model.KenectLabContact;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Qualifier("kenect_labs_client")
public class KenectLabsClient implements PagedContactSourceClient {

    @Qualifier("kenect_labs_rest_template")
    private final RestTemplate kenectRestTemplate;

    private final ConversionService conversionService;

    private final KenectLabConfig kenectLabCredentials;

    public int getTotalPageCount() {
        try {
            return Optional
                    .ofNullable(kenectRestTemplate.headForHeaders(kenectLabCredentials.getUrl())
                            .get(kenectLabCredentials.getPageCounterHeaderName()))
                    .filter(headerValues -> !headerValues.isEmpty())
                    .map(headerValues -> Integer.valueOf(headerValues.get(0)))
                    .orElse(0);
        } catch (RestClientException exception) {
            throw new ExternalServiceException("Cannot retrieve total page count from external source");
        }
    }

    public List<Contact> getContactsFromPage(int pageNumber) {
        return retrieveContacts(pageNumber).stream()
                .map(kenectContact -> conversionService.convert(kenectContact, Contact.class))
                .toList();
    }

    private List<KenectLabContact> retrieveContacts(int pageNumber) {
        try {
            return Optional
                    .ofNullable(kenectRestTemplate.getForObject(kenectLabCredentials.getUrl() + "?page={pageNumber}",
                            KenectLabContact[].class, Map.of("pageNumber", pageNumber)))
                    .map(contacts -> Arrays.asList(contacts))
                    .orElse(List.of());
        } catch (RestClientException ex) {
            throw new ExternalServiceException("Cannot retrieve contacts from external source");
        }
    }
}
