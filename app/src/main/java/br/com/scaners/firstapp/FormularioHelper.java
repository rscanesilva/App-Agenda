package br.com.scaners.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RatingBar;

import br.com.scaners.firstapp.modelo.Aluno;

/**
 * Created by Scaners on 09/07/2016.
 */
public class FormularioHelper {

    private Aluno aluno;
    private EditText campoNome;
    private EditText campoEndereco;
    private EditText campoTelefone;
    private EditText campoSite;
    private RatingBar campoNota;

    public FormularioHelper(FormularioActivity activity) {
       campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
       campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
       campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
       campoSite = (EditText) activity.findViewById(R.id.formulario_site);
       campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
       aluno = new Aluno();
    }

    public Aluno getAluno() {
        aluno.setNome(this.campoNome.getText().toString());
        aluno.setEndereco(this.campoEndereco.getText().toString());
        aluno.setTelefone(this.campoTelefone.getText().toString());
        aluno.setSite(this.campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getRating()));
        return aluno;
    }


    public void carregaFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoNota.setProgress(aluno.getNota().intValue());
        campoSite.setText(aluno.getSite());
        campoTelefone.setText(aluno.getTelefone());
        this.aluno.setId(aluno.getId());
    }
}
