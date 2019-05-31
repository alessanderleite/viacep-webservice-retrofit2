package br.com.alessanderleite.viacepwebservice.model;

public interface SimpleCallback<T> {
    void onResponse(T response);
    void onError(String error);
}
