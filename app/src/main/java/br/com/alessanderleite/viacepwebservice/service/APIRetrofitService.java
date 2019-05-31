package br.com.alessanderleite.viacepwebservice.service;

import java.util.List;

import br.com.alessanderleite.viacepwebservice.model.CEP;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIRetrofitService {

    String BASE_URL = "https://viacep.com.br/ws/";

    /* Return the object CEP list */
    @GET("{estado}/{cidade}/{endereco}/json/")
    Call<List<CEP>> getCEPByCidadeEstadoEndereco(
            @Path("estado") String estado,
            @Path("cidade") String cidade,
            @Path("endereco") String endereco
    );

    /* Return only an object CEP */
    @GET("{CEP}/json/")
    Call<CEP> getEnderecoByCEP(@Path("CEP") String CEP);
}
