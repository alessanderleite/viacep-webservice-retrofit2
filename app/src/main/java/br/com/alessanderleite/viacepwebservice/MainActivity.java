package br.com.alessanderleite.viacepwebservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.alessanderleite.viacepwebservice.model.CEP;
import br.com.alessanderleite.viacepwebservice.model.SimpleCallback;
import br.com.alessanderleite.viacepwebservice.service.CEPService;

public class MainActivity extends AppCompatActivity {

    private EditText etCEP;
    private EditText etRua;
    private EditText etCidade;
    private Spinner spUFs;
    private Button btnBuscarPorCEP;
    private Button btnBuscarPorRuaCidadeEstado;
    private ProgressBar progressBar;

    private ArrayList<CEP> arrayCEPs;

    private static final String TAG = "logCEP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCEP = findViewById(R.id.et_cep);
        btnBuscarPorCEP = findViewById(R.id.btn_buscar_por_cep);
        progressBar = findViewById(R.id.progress_bar);
        etRua = findViewById(R.id.et_rua);
        etCidade = findViewById(R.id.et_cidade);
        spUFs = findViewById(R.id.sp_ufs);
        btnBuscarPorRuaCidadeEstado = findViewById(R.id.btn_buscar_por_rua_cidade_estado);

        progressBar.setVisibility(View.INVISIBLE);

        /* search for zipCode */
        btnBuscarPorCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etCEP.getText().toString().isEmpty()) {

                    progressBar.setVisibility(View.VISIBLE);
                    CEPService service = new CEPService(MainActivity.this);

                    service.getCEP(etCEP.getText().toString(), new SimpleCallback<CEP>() {
                        @Override
                        public void onResponse(CEP response) {
                            CEP cep = response;

                            Toast.makeText(
                                    getApplicationContext(),
                                    getResources().getString(R.string.toast_aviso_retorno)+cep.toString(),
                                    Toast.LENGTH_LONG).show();

                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError(String error) {
                            toast("erro onError: " + error.toString());
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                } else {
                    toast("CEP vazio!");
                }
            }
        });

        /* search for Street, City and State(UF) */
        btnBuscarPorRuaCidadeEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etCidade.getText().toString().isEmpty() &&
                        !etRua.getText().toString().isEmpty() &&
                                spUFs.getSelectedItemPosition() != 0) {

                    progressBar.setVisibility(View.VISIBLE);

                    CEPService service = new CEPService(MainActivity.this);

                    service.getCEPUFCidadeRua(spUFs.getSelectedItem().toString(),
                            etCidade.getText().toString(),
                            etRua.getText().toString(), new SimpleCallback<List<CEP>>() {
                                @Override
                                public void onResponse(List<CEP> response) {

                                    List<CEP> CEPAux = response;
                                    arrayCEPs = new ArrayList<>();

                                    for (CEP cep : CEPAux) {
                                        arrayCEPs.add(cep);
                                    }

                                    toast(getResources().getString(R.string.toast_aviso_retorno)+arrayCEPs.toString());
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onError(String error) {
                                    toast(getResources().getString(R.string.toast_erro_generico)+error);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                }
            }
        });
    }

    private void toast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }
}