package ru.clevertec.ecl.spring.builder.impl;

import ru.clevertec.ecl.spring.builder.TestEntityBuilder;
import ru.clevertec.ecl.spring.entity.GiftTag;

public class GiftTagBuilder implements TestEntityBuilder<GiftTag> {
    private long id = 1;
    private long giftID = 1;
    private long tagID = 1;

    private GiftTagBuilder() {}

    public static GiftTagBuilder aGiftTag() {
        return new GiftTagBuilder();
    }

    @Override
    public GiftTag buildToEntity() {
        return GiftTag.builder()
                .id(id)
                .giftID(giftID)
                .tagID(tagID)
                .build();
    }

    public GiftTagBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public GiftTagBuilder setGiftID(long giftID) {
        this.giftID = giftID;
        return this;
    }

    public GiftTagBuilder setTagID(long tagID) {
        this.tagID = tagID;
        return this;
    }
}
