package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editCode,editName,editMarca,editPrice,editQtd;
    Button btnClear,btnSave,btnDelete;
    ListView listViewClient;

    DataBase database = new DataBase(this);

    ArrayAdapter<String> adapter;
    ArrayList<String> list;

    InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        editCode = (EditText)findViewById(R.id.editCode);
//        editName = (EditText)findViewById(R.id.editName);
//        editPhone = (EditText)findViewById(R.id.editPhone);
//        editEmail = (EditText)findViewById(R.id.editEmail);
//
//        btnClear =  (Button)findViewById(R.id.btnClear);
//        btnSave =  (Button) findViewById(R.id.btnSave);
//        btnDelete =  (Button)findViewById(R.id.btnDelete);
//
        listViewClient = findViewById(R.id.listViewClient);
        editCode = findViewById(R.id.editCode);
        editName = findViewById(R.id.editName);
        editMarca = findViewById(R.id.editMarca);
        editPrice = findViewById(R.id.editPrice);
        editQtd = findViewById(R.id.editQtd);

        btnClear = findViewById(R.id.btnClear);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
        listProducts();

        listViewClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String content = (String) listViewClient.getItemAtPosition(i);

                Toast.makeText(MainActivity.this, "Produto Selecionado: " + content,Toast.LENGTH_LONG).show();

                String code = content.substring(0,content.indexOf("-"));
                Product p = database.selectProduct(Integer.parseInt(code));

                editCode.setText( ""+p.getCode());
                editName.setText(p.getName());
                editMarca.setText(p.getMarca());
                editPrice.setText(""+p.getPrice());
                editQtd.setText(""+p.getQdt());
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = editCode.getText().toString();
                String name = editName.getText().toString();
                String price = editPrice.getText().toString();
                String qtd = editQtd.getText().toString();
                String marca = editMarca.getText().toString();

                if(name.isEmpty()){
                    editName.setError("Obrigatório");
                }else if(price.isEmpty()){
                    editPrice.setError("Informe um valor ou 0");
                }else if(qtd.isEmpty()){
                    editQtd.setError("Informe um valor ou 0");
                }
                else{
                    if(code.isEmpty()){
                        database.addProduct(new Product(name,Double.parseDouble(price),Integer.parseInt(qtd),marca));
                        Toast.makeText(MainActivity.this,name+" Salvo", Toast.LENGTH_LONG).show();
                        listProducts();
                        clearFields();
                        hideKeyboard();
                    }else{
                        database.updateProduct(new Product(Integer.parseInt(code),name,Double.parseDouble(price),Integer.parseInt(qtd),marca));
                        Toast.makeText(MainActivity.this,name +" Editado", Toast.LENGTH_LONG).show();
                        listProducts();
                        clearFields();
                        hideKeyboard();
                    }
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = editCode.getText().toString();
                String name = editName.getText().toString();
                String price = editPrice.getText().toString();
                String qtd = editQtd.getText().toString();
                String marca = editMarca.getText().toString();
                if(code.isEmpty()){
                    listProducts();
                    clearFields();
                }else{
                    database.deleteProduct(new Product(Integer.parseInt(code),name,Double.parseDouble(price),Integer.parseInt(qtd),marca));
                    Toast.makeText(MainActivity.this,name +" Apagado", Toast.LENGTH_LONG).show();
                    listProducts();
                    clearFields();
                }
                hideKeyboard();
            }
        });
//        database.addClient(new Client("Joao","999999999","mail@mail.com"));
//        database.addClient(new Client("Claudio","11111111111","mail@mail.com"));
//        database.addClient(new Client("Marcos","93333333333","mail@mail.com"));
//        database.addClient(new Client("Laila","99444444444","mail@mail.com"));
//        database.addClient(new Client("Maria","9453453534543","mail@mail.com"));
//        Toast.makeText(MainActivity.this,"Salvo Com Sucesso", Toast.LENGTH_LONG).show();

//        Client client = new Client();
//        client.setCode(1);
//        database.deleteClient(client);
//        Toast.makeText(MainActivity.this,"Apagado Com Sucesso", Toast.LENGTH_LONG).show();

//        Client clientResponse =  database.selectClient(3);
//
//        Log.d("Client Selecionado: ",clientResponse.getName() + " " + clientResponse.getEmail());
//        database.updateClient(new Client(3,"Joao","3333","33335"));
//
//        clientResponse =  database.selectClient(3);
//        Log.d("Client Selecionado: ",clientResponse.getName() + " " + clientResponse.getEmail());

    }
    void hideKeyboard(){
        imm.hideSoftInputFromWindow(editName.getWindowToken(),0);
    }
    void clearFields(){
        editCode.setText("");
        editName.setText("");
        editMarca.setText("");
        editPrice.setText("");
        editQtd.setText("");

        editName.requestFocus();
    }
    public void listProducts() {
        List<Product> products = database.listProducts();

        list = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,list);

        listViewClient.setAdapter(adapter);

        for (Product p : products) {
//            Log.d("Client Selecionado: ", c.getCode() + " " + c.getName() + " " + c.getEmail());
            list.add(p.getCode() + "- " + p.getName() + " " + p.getPrice() + " " + p.getQdt()+ " " + p.getMarca());
            adapter.notifyDataSetChanged();
        }
    }
}