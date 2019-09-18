package com.michelle.organizeclone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.michelle.organizeclone.R;
import com.michelle.organizeclone.activity.config.ConfigFirebase;
import com.michelle.organizeclone.activity.helper.Base64Custom;
import com.michelle.organizeclone.activity.model.Movimentacao;
import com.michelle.organizeclone.activity.model.Usuario;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private TextView textViewSaudacao, textViewSaldo;
    private Double despesaTotal = 0.0, receitaTotal = 0.0, resumoUsuario = 0.0;
    private String mesAno;

    private FirebaseAuth auth = ConfigFirebase.getAuth();
    private DatabaseReference ref = ConfigFirebase.getFirebase();
    private DatabaseReference usuarioRef;
    private DatabaseReference movimentacaoRef = ConfigFirebase.getFirebase();

    private ValueEventListener valueEventListenerUsuario;
    private ValueEventListener valueEventListenerMovi;
    private List<Movimentacao> movimentacaos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Organizze");
        setSupportActionBar(toolbar);

        calendarView = findViewById(R.id.calendarView);
        textViewSaldo = findViewById(R.id.textViewSaldo);
        textViewSaudacao = findViewById(R.id.textViewSaudacao);

        configuraCalendarView();

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void recuperarMovimentacao() {
        String emailUsuario = auth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codigicarBase64(emailUsuario);

        movimentacaoRef.child("movimentacao")
                .child(idUsuario)
                .child(mesAno);

        valueEventListenerMovi = movimentacaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                movimentacaos.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Movimentacao movimentacao = dados.getValue(Movimentacao.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void recuperaResumo() {
        String emailUsuario = auth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codigicarBase64(emailUsuario);
        usuarioRef = ref.child("usuarios").child(idUsuario);

        valueEventListenerUsuario = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                despesaTotal = usuario.getDespesaTotal();
                receitaTotal = usuario.getReceitaTotal();
                resumoUsuario = receitaTotal - despesaTotal;

                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                String resultadoFormatado = decimalFormat.format(resumoUsuario);

                textViewSaudacao.setText("Olá, " + usuario.getNome());
                textViewSaldo.setText("R$ " + resultadoFormatado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_principal, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSair:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void adicionarReceita(View view) {
        startActivity(new Intent(this, ReceitasActivity.class));
    }

    public void adicionarDespesa(View view) {
        startActivity(new Intent(this, DespesasActivity.class));
    }

    public void configuraCalendarView() {
        CharSequence meses[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho",
                "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        calendarView.setTitleMonths(meses);

        CalendarDay dataAtual = calendarView.getCurrentDate();
        //uso o metodo valueOf para converter um inteiro para uma String
        mesAno = String.valueOf((dataAtual.getMonth() + 1) + "" + dataAtual.getYear());

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                mesAno = String.valueOf((date.getMonth() + 1) + "" + date.getYear());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaResumo();
        recuperarMovimentacao();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioRef.removeEventListener(valueEventListenerUsuario);
        movimentacaoRef.removeEventListener(valueEventListenerMovi);
    }
}
