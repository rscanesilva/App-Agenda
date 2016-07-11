package br.com.scaners.firstapp.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.scaners.firstapp.util.FormularioUtil;
import br.com.scaners.firstapp.R;
import br.com.scaners.firstapp.dao.AlunoDao;
import br.com.scaners.firstapp.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {
    public static final int INTENT_CAMERA = 1;
    private FormularioUtil formularioHelper;
    private String caminhoFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        formularioHelper = new FormularioUtil(this);
        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        if (aluno != null){
            formularioHelper.carregaFormulario(aluno);
        }

        Button botaoFoto = (Button) findViewById(R.id.formulario_btn_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null)+"/"+ System.currentTimeMillis()+".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, INTENT_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == INTENT_CAMERA && (resultCode == Activity.RESULT_OK)){
            ImageView viewFoto = (ImageView) findViewById(R.id.formulario_foto);
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapFormatado = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            viewFoto.setImageBitmap(bitmapFormatado);
            viewFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_formulario_ok :
                Aluno aluno = formularioHelper.getAluno();
                AlunoDao alunoDao = new AlunoDao(this);
                if (aluno.getId() != null) {
                    alunoDao.update(aluno);
                }else {
                    alunoDao.insert(aluno);
                }
                alunoDao.close();
                Toast.makeText(FormularioActivity.this, "Aluno " +aluno.getNome()+ " cadastrado!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
