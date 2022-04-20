package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editCode,editName,editPhone,editEmail;
    Button btnClear,btnSave,btnDelete;
    ListView listViewClient;

    DataBase database = new DataBase(this);

    ArrayAdapter<String> adapter;
    ArrayList<String> list;

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
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);

        btnClear = findViewById(R.id.btnClear);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);


        listClients();

        listViewClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String content = (String) listViewClient.getItemAtPosition(i);

                Toast.makeText(MainActivity.this, "Cliente Selecionado: " + content,Toast.LENGTH_LONG).show();

                String code = content.substring(0,content.indexOf("-"));
                Client cLient = database.selectClient(Integer.parseInt(code));

                editCode.setText( ""+cLient.getCode());
                editName.setText(cLient.getName());
                editPhone.setText(cLient.getPhone());
                editEmail.setText(cLient.getEmail());
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
                clearFields();
                String code = editCode.getText().toString();
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                String email = editEmail.getText().toString();

//                if(name.isEmpty()){
//                    editName.setError("Obrigatório");
//                }else{
                    if(code.isEmpty()){
                        database.addClient(new Client(name,phone,email));
                        Toast.makeText(MainActivity.this,name+" Salvo", Toast.LENGTH_LONG).show();
                        listClients();
                        clearFields();
                    }else{
                        database.updateClient(new Client(Integer.parseInt(code),name,phone,email));
                        Toast.makeText(MainActivity.this,name +" Editado", Toast.LENGTH_LONG).show();
                    }
//                }
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
    void clearFields(){
        editCode.setText("");
        editName.setText("");
        editPhone.setText("");
        editEmail.setText("");

        editName.requestFocus();
    }
    public void listClients() {
        List<Client> clients = database.listaAllClients();

        list = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,list);

        listViewClient.setAdapter(adapter);

        for (Client c : clients) {
//            Log.d("Client Selecionado: ", c.getCode() + " " + c.getName() + " " + c.getEmail());
            list.add(c.getCode() + "- " + c.getName() + " " + c.getPhone() + " " + c.getEmail()  );
            adapter.notifyDataSetChanged();
        }
    }
}