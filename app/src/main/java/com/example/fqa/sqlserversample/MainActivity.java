package com.example.fqa.sqlserversample;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fqa.sqlserversample.Models.Produto;
import com.example.fqa.sqlserversample.Connection.Querys;
import com.example.fqa.sqlserversample.Util.Log;
import com.example.fqa.sqlserversample.Util.NetworkPing;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Produto> produtos;
    private AsyncQuery asyncQuery;
    private Button btnAtualizar;
    private Log log;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            asyncQuery = new AsyncQuery();
            asyncQuery.execute();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log = new Log(this);
        log.logInfo("onCreate onProcesses");
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(onClickListener);
        log.logInfo("btnAtualizar ready");
        asyncQuery = new AsyncQuery();
        log.logInfo("Obj async created");
        log.logInfo("AsyncTask ready to go");
        asyncQuery.execute();
        log.logInfo("AsyncTask runnnig");
    }
    private void Layout(int orientation){
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) orientation = 5;
        else if (orientation == Configuration.ORIENTATION_PORTRAIT) orientation = 4;
        //TableLayout Creation
        TableLayout table = findViewById(R.id.TableLayout);
        /*-- remove all views from TableLayout --*/
        table.removeAllViews();
        log.logInfo("Table cleared");
        /*-- Insert Header Row --*/
        //table.setColumnShrinkable(0, true);
        //table.setColumnStretchable(0, true);
        table.addView(headerMeaker(table.getMeasuredWidth()));
        log.logInfo("Header Added");
        if(produtos != null) {
            log.logInfo("produtos not empty");
            for (Produto produto : produtos) {
                TableRow row = new TableRow(this);
                log.logInfo(String.format("Row to %s created", produto.getProduto()));
                for (int j = 1; j < orientation; j++) {
                    TextView label = new TextView(this);
                    log.logInfo(String.format(Locale.ENGLISH, "label %d to %s created", j, produto.getUtil(j)));
                    label.setText(String.format(" %s", produto.getUtil(j)));
                    label.setBackgroundResource(R.drawable.border_text_view);
                    row.addView(label);
                    log.logInfo(String.format(Locale.ENGLISH, "label %d added to row", j));
                }
                table.addView(row);
                log.logInfo(String.format("Row to %s added", produto.getProduto()));
            }
        }else{
            log.logInfo("produtos is empty");
            for (int i = 1; i < 4; i++) {
                TableRow row = new TableRow(this);
                log.logInfo(String.format(Locale.ENGLISH, "Row %d created",i));
                for (int j = 1; j < orientation; j++) {
                    TextView label = new TextView(this);
                    log.logInfo(String.format(Locale.ENGLISH, "label %d created",j));
                    label.setText(String.format(Locale.ENGLISH, " Label %d", j));
                    label.setGravity(Gravity.START);
                    label.setTextSize(20);
                    label.setBackgroundResource(R.drawable.border_text_view);
                    row.addView(label);
                    log.logInfo(String.format(Locale.ENGLISH, "label %d added", j));
                }
                table.addView(row);
                log.logInfo(String.format(Locale.ENGLISH, "row %d added", i));
            }
        }
    }
    private int[] size_finder(ArrayList<Produto> produtos) {
        int id_size = String.valueOf(produtos.get(0).getId()).length();
        int prod_size = String.valueOf(produtos.get(0).getProduto()).length();
        int quant_size = String.valueOf(produtos.get(0).getQuantidade()).length();
        int[] biggers = {id_size, prod_size, quant_size};
        for (Produto produto : produtos) {
            for (int i = 0; i < 3; i++) {
                if (biggers[i] < produto.getUtil(i + 1).length()) {
                    biggers[i] = produto.getUtil(i + 1).length();
                }
            }
        }
        return biggers;
    }

    private TableRow headerMeaker(int width){
        TableRow header = new TableRow(this);
        header.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        Produto attrs = new Produto();
        if(Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation) {
            for (int i = 0; i < 3; i++) {
                TextView label = new TextView(this);
                switch (i) {
                    case 0:
                        label.setWidth(15 * width / 100);
                        break;
                    case 1:
                        label.setWidth(65 * width / 100);
                        break;
                    case 2:
                        label.setWidth(2 * width / 10);
                        break;
                }
                label.setText(String.format(Locale.ENGLISH, " %s", attrs.getAtr(i + 1)));
                label.setGravity(Gravity.START);
                label.setTextSize(20);
                label.setBackgroundResource(R.drawable.border_text_view);
                header.addView(label);
            }
        }else if(Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation){
            for (int i = 0; i < 4; i++) {
                TextView label = new TextView(this);
                switch (i) {
                    case 0:
                        label.setWidth(width / 10);
                        break;
                    case 1:
                        label.setWidth(55 * width / 100);
                        break;
                    case 2:
                        label.setWidth(15 * width / 100);
                        break;
                    case 3:
                        label.setWidth(2 * width / 10);
                        break;
                }
                label.setText(String.format(Locale.ENGLISH, " %s", attrs.getAtr(i + 1)));
                label.setGravity(Gravity.START);
                label.setTextSize(20);
                label.setBackgroundResource(R.drawable.border_text_view);
                header.addView(label);
            }
        }
        return header;
    }
    class AsyncQuery extends AsyncTask<Void, Void, Void>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute(){
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Realizando o carregamento dos dados");
            dialog.setMessage("Aguarde o fim da requisição...");
            dialog.show();
            log.logInfo("ProgressDialog showed");
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //NetworkPing ping = new NetworkPing();
                //ping.networkPing(log);
                Querys querys = new Querys();
                log.logInfo("Obj querys created");
                log.logInfo("Query ready to start");
                produtos = querys.Pesquisar();
                log.logInfo("query completed");
            }
            catch (Exception ex){
                produtos = null;
                log.logErro("doInBG erro, produtos is null", ex);
                log.logInfo(String.format("Class: %s\nCause: %s\nMessage: %s\n", ex.getClass(), ex.getCause(), ex.getMessage()));
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void voids){
            Layout(getResources().getConfiguration().orientation);
            log.logInfo("Layout completed");
            dialog.dismiss();
            log.logInfo("Dialog dismiss");
            log.logInfo("Async Completed");
        }
    }
}
