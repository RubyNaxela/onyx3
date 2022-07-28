package com.rubynaxela.onyx.util;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * The {@code CachedGetter}, by design, is effectively a {@link Supplier} extended with the caching feature. Upon creation,
 * a {@code CachedGetter} is given a {@code Supplier} used to compute the cached value, and the initial hash, which is later
 * used to identify a cache and determine whether or not it is still valid. If the {@link #get} method is called with a
 * different hash than the currently stored one, the cached value is computed again using the specified {@link Supplier}.
 *
 * @param <T> the type of cached value
 */
public class CachedGetter<T> {

    private final Supplier<T> getter;
    private T value;
    private int hash;

    /**
     * Constructs a new {@code CachedGetter} with the specified getter and initial
     * hash. The getter is immediately invoked and the value returned by it is cached.
     *
     * @param getter      the value getter used to compute the cached value
     * @param initialHash the initial hash
     */
    public CachedGetter(@NotNull Supplier<T> getter, int initialHash) {
        this.getter = getter;
        this.value = getter.get();
        this.hash = initialHash;
    }

    /**
     * If the specified hash is equal to the currently stored hash, the cached value is returned. Otherwise, the getter
     * is used to compute and cache the desired value. The specified hash is stored as the current hash of this cache.
     *
     * @param hash the hash to compare with the currently stored hash
     * @return the cached value or the value returned by the getter
     */
    public T get(int hash) {
        if (hash != this.hash) {
            this.hash = hash;
            this.value = getter.get();
        }
        return this.value;
    }
}
