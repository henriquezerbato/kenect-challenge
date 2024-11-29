package com.kenect.kenect_challenge.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kenect.kenect_challenge.client.PagedContactSourceClient;
import com.kenect.kenect_challenge.model.Contact;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContactService {

    @Qualifier("kenect_labs_client")
    private final PagedContactSourceClient contactClient;

    public List<Contact> getAllContacts() {
        var totalPages = contactClient.getTotalPageCount();

        if (totalPages > 0) {
            var callers = IntStream.rangeClosed(1, totalPages)
                    .mapToObj(pageNumber -> CompletableFuture
                            .supplyAsync(() -> contactClient.getContactsFromPage(pageNumber)))
                    .toList();

            return CompletableFuture.allOf(callers.toArray(CompletableFuture[]::new))
                    .thenApply(result -> callers.stream().flatMap(cf -> cf.join().stream()).toList()).join();
        }
        return List.of();
    }

}
