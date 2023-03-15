package com.example.thenewappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class NganhActivity extends AppCompatActivity {

    String[] items = {"Khoa học máy tính","Kỹ thuật phần mềm", "Trí tuệ nhân tạo",
            "Công nghệ thông tin", "Kỹ thuật máy tính", "An toàn thông tin", "Hệ thống thông tin",
            "Kỹ thuật cơ điện tử", "Kỹ thuật điện", "Kỹ thuật ô tô",
            "Logistics và Quản lý chuỗi cung ứng",
            "Kế toán", "Tài chính ngân hàng", "Kiểm toán",
            "Kiến trúc", "Thiết kế đồ họa", "Toán tin", "Y khoa", "Y học cổ truyền", "Dược học","Chưa là sinh viên"

    };

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nganh);


        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Đã chọn ngành học: "+item, Toast.LENGTH_SHORT).show();

            }
        });

    }
}