 package com.example.loftmoney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.loftmoney.databinding.ActivityAddItemBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

 public class AddItemActivity extends AppCompatActivity {

    public static final String ARG_POSITION = "arg_position";

    private ActivityAddItemBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentPosition = extras.getInt(ARG_POSITION);
        }

        binding.btnMoneyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemToInternet();
            }
        });
    }

    private void putItemToInternet() {
        String type;
        if (currentPosition == 0) {
            type = "expense";
        } else {
            type = "income";
        }
        Disposable disposable = ((LoftApp)getApplication()).loftAPI
                .putItems(binding.moneyPriceView.getText().toString(), binding.moneyNameView.getText().toString(), type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    finish();
                }, throwable -> {
                    showMessage("A write error has occurred");
                });
        compositeDisposable.add(disposable);
    }

    private void showMessage(String message) {
        Toast.makeText(this, getString(message), Toast.LENGTH_LONG).show();
    }

     private int getString(String message) {
         return 0;
     }

     @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}