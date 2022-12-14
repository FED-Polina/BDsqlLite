package fedulova.polina303.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import fedulova.polina303.database.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding = null; //инициализируем объект привязки C Sharp в файле build.gradleModule

    DB mydb; //инициализация БД

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());//создание объекта привязки
        //setContentView(R.layout.activity_main); было
        setContentView(binding.getRoot()); //стало

        mydb = new DB (this, "mybase.db", null, 1); //создание БД

        metodOtslejivaniaNajatia();//метод в код добавляем слушатели на кнопки
    }

    private void metodOtslejivaniaNajatia() {
        binding.buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = binding.editTextKey.getText().toString(); //get key and value strings
                String value = binding.editTextValue.getText().toString();
                if(mydb.do_select(key) == "empty"){ //если такой записи нет то добавляем

                    mydb.do_insert(key, value); //insert into table
                }
                else{
                    Toast.makeText(getApplicationContext(), "Запись с таким ключом уже существует", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = binding.editTextKey.getText().toString(); //get key and value strings
                String value = binding.editTextValue.getText().toString();
                mydb.do_update(key, value);
            }
        });

        binding.buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = binding.editTextKey.getText().toString(); //get key string
                String value = mydb.do_select(key); //find value for that key in table

                binding.editTextValue.setText(value); //show result
            }
        });

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = binding.editTextKey.getText().toString(); //get key and value strings
                if(mydb.do_select(key) == "empty"){ //если такой записи нет то добавляем

                    Toast.makeText(getApplicationContext(), "Запись с таким ключом не существует", Toast.LENGTH_SHORT).show();
                }
                else{
                    createDialog(key).show();
                }
            }
        });
    }

    //Метод для вывода DialogWindow
    @NonNull
    public AlertDialog createDialog(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Точно ли Вы хотите удалить запись?")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Закрываем диалоговое окно
                        mydb.do_delete(key);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Закрываем диалоговое окно
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}