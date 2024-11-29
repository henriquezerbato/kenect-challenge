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

import com.kenect.kenect_challenge.exception.ExternalServiceException;
import com.kenect.kenect_challenge.model.Contact;
import com.kenect.kenect_challenge.model.KenectLabContact;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Qualifier("kenect_labs_client")
public class KenectLabsClient implements PagedContactSourceClient {

    private static final String EXTERNAL_CONTACTS_URL = "https://candidate-challenge-api-489237493095.us-central1.run.app/api/v1/contacts";
    private static final String PAGE_COUNTER_HEADER_NAME = "total-pages";

    @Qualifier("kenect_labs_rest_template")
    private final RestTemplate kenectRestTemplate;

    private final ConversionService conversionService;

    public int getTotalPageCount() {
        try {
            return Optional
                    .ofNullable(kenectRestTemplate.headForHeaders(EXTERNAL_CONTACTS_URL)
                            .get(PAGE_COUNTER_HEADER_NAME))
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
                    .ofNullable(kenectRestTemplate.getForObject(EXTERNAL_CONTACTS_URL + "?page={pageNumber}",
                            KenectLabContact[].class, Map.of("pageNumber", pageNumber)))
                    .map(contacts -> Arrays.asList(contacts))
                    .orElse(List.of());
        } catch (RestClientException ex) {
            throw new ExternalServiceException("Cannot retrieve contacts from external source");
        }
    }
}
