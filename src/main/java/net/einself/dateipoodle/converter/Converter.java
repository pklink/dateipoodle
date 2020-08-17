package net.einself.dateipoodle.converter;

import java.util.function.Function;

public class Converter<T, U> {

    private final Function<T, U> convert;

    public Converter(final Function<T, U> convert) {
        this.convert = convert;
    }

    public final U one(final T dto) {
        return convert.apply(dto);
    }

}
