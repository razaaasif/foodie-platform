package com.foodie.commons.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Created on 01/06/25.
 *
 * @author : aasif.raza
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyValueDTO<K, V> {
    @NotNull
    @JsonProperty("key")
    private K key;

    @JsonProperty("value")
    private V value;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        KeyValueDTO<?, ?> that = (KeyValueDTO<?, ?>) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
