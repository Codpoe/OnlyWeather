package me.codpoe.onlyweather.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.db.OnlyWeatherDB;
import me.codpoe.onlyweather.model.City;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mAddToolbar;
    private EditText mAddEdit;
    private Button mAddBtn;


    private OnlyWeatherDB db;
    private String[] mStrings;
    private ArrayAdapter<String> mArrayAdapter;

    private City mCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        findViews();


        // mAddToolbar
        mAddToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // EditText and Button
        mAddBtn.setOnClickListener(this);
    }

    /**
     * 获取引用
     */
    public void findViews() {
        mAddToolbar = (Toolbar) findViewById(R.id.add_toolbar);
        mAddEdit = (EditText) findViewById(R.id.add_edit);
        mAddBtn = (Button) findViewById(R.id.add_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                if(!TextUtils.isEmpty(mAddEdit.getText().toString())) {
                    SharedPreferences.Editor editor = getSharedPreferences("codpoe", Context.MODE_PRIVATE).edit();
                    editor.putString("add_city", mAddEdit.getText().toString());
                    editor.commit();
                    onBackPressed();
                }

        }
    }

    /**
     * 导入 china_city.db 数据库
     */
//    public void importDB() {
//        Observable.create(new Observable.OnSubscribe() {
//            @Override
//            public void call(Object o) {
//                mDBManager = new DBManager(AddActivity.this);
//                mDBManager.openDatabase();
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Object o) {
//                        queryCities();
//                    }
//                });
//    }

//    /**
//     * 查询 china_city.db 中所有的城市，并给 AutoCompleteTextView 设置 Adapter
//     */
//    public void queryCities() {
//        Observable.create(new Observable.OnSubscribe<String[]>() {
//            @Override
//            public void call(Subscriber<? super String[]> subscriber) {
//                mStrings = mDBManager.getCityName();
//                subscriber.onNext(mStrings);
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<String[]>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(String[] strings) {
//                        mArrayAdapter = new ArrayAdapter<String>(AddActivity.this, android.R.layout.simple_list_item_1, strings);
//                        mAddEdit.setAdapter(mArrayAdapter);
//                    }
//                });
//    }
//
//    public void putCityToDB() {
//        Observable.create(new Observable.OnSubscribe() {
//            @Override
//            public void call(Object o) {
//                db.saveCity(mCity);
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Object o) {
//                        onDestroy();
//                    }
//                });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mDBManager.closeDatabase();
//    }
}
