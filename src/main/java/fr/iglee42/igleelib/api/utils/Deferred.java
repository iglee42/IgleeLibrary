package fr.iglee42.igleelib.api.utils;

public class Deferred<T> {
    private T data;

    public T set(T val) {
        data = val;
        return val;
    }

    public T get(){
        return data;
    }
}