package com.kenect.kenect_challenge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.kenect.kenect_challenge.model.Contact;
import com.kenect.kenect_challenge.service.ContactService;

@WebMvcTest(ContactController.class)
public class ContactControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ContactService contactService;

    @Test
    public void getAllContacts() {
        
        var contacts = List.of(
                new Contact(1, "name_1", "email_1", "source_1", "1", "1"),
                new Contact(2, "name_2", "email_2", "source_2", "2", "2"));
        
        when(contactService.getAllContacts()).thenReturn(contacts);

        ObjectMapper mapper = new JsonMapper();
        try {
                mvc.perform(MockMvcRequestBuilders.get("/contacts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    var content = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Contact>>(){});
                    assertEquals(contacts, content);
                });  
        } catch (Exception e) {
                fail();
        }
        
    }

}
