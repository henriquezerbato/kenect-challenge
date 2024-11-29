package com.kenect.kenect_challenge.client;

import java.util.List;

import com.kenect.kenect_challenge.model.Contact;

public interface PagedContactSourceClient {

    public int getTotalPageCount();

    public List<Contact> getContactsFromPage(int pageNumber);

}
