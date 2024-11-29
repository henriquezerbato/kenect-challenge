package com.kenect.kenect_challenge.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.kenect.kenect_challenge.model.Contact;
import com.kenect.kenect_challenge.model.KenectLabContact;

@Component
public class KenectLabContactToContactConverter implements Converter<KenectLabContact, Contact> {

    private static final String SOURCE_NAME = "KENECT_LABS";

    @Override
    @Nullable
    public Contact convert(@NonNull KenectLabContact source) {
        return new Contact(source.getId(), source.getName(), source.getEmail(), SOURCE_NAME, source.getCreatedAt(),
                source.getUpdatedAt());
    }

}
