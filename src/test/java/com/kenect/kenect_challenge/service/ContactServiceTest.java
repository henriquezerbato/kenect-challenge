package com.kenect.kenect_challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kenect.kenect_challenge.client.KenectLabsClient;
import com.kenect.kenect_challenge.model.Contact;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private KenectLabsClient kenectLabsClientMock;

    @InjectMocks
    private ContactService contactService;

    @Test
    public void getAllContacts() {
        when(kenectLabsClientMock.getTotalPageCount()).thenReturn(2);

        var contactsPageOne = List.of(
                new Contact(1, "name_1", "email_1", "source_1", "1", "1"),
                new Contact(2, "name_2", "email_2", "source_2", "2", "2"));
        var contactsPageTwo = List.of(
                new Contact(3, "name_3", "email_3", "source_3", "3", "3"),
                new Contact(4, "name_4", "email_4", "source_4", "4", "4"));

        when(kenectLabsClientMock.getContactsFromPage(1)).thenReturn(contactsPageOne);
        when(kenectLabsClientMock.getContactsFromPage(2)).thenReturn(contactsPageTwo);

        var allContacts = contactService.getAllContacts();

        assertEquals(Stream.concat(contactsPageOne.stream(), contactsPageTwo.stream()).toList(), allContacts);
    }

}
