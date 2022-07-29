package com.rubynaxela.onyx.util;

public class IntPointer {

    int value;

    private IntPointer(int value) {
        this.value = value;
    }

    public static IntPointer to(int value) {
        return new IntPointer(value);
    }

    public int get() {
        return value;
    }

    public int set(int value) {
        return this.value = value;
    }

    public int incrementPostfix() {
        return this.value++;
    }

    public int incrementPrefix() {
        return ++this.value;
    }
}
