package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AfterSearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private EditText searchEditText;
    private ImageView backButton, filterIcon, searchButton;
    private String query;
    private LinearLayout homeLayout;
    private LinearLayout interestLayout;
    private LinearLayout chatLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftersearch);

        recyclerView = findViewById(R.id.recycler_view);
        searchEditText = findViewById(R.id.search_edit_text);
        backButton = findViewById(R.id.back_button);
        filterIcon = findViewById(R.id.filter_icon);
        searchButton = findViewById(R.id.search_icon);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);

        Intent intent = getIntent();
        query = intent.getStringExtra("QUERY");
        searchEditText.setText(query);

        if (!TextUtils.isEmpty(query)) {
            searchEditText.setText(query);
            // 검색어에 따라 데이터 로드
            loadProducts(query);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterSearchActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        filterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 필터 아이콘 클릭 시 동작 구현
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchProducts();
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    searchProducts();
                    return true;
                }
                return false;
            }
        });
        // 홈 레이아웃 클릭 이벤트 설정
        homeLayout = findViewById(R.id.home_layout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterSearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // 관심 레이아웃 클릭 이벤트 설정
        interestLayout = findViewById(R.id.interest_layout);
        interestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterSearchActivity.this, InterestActivity.class);
                startActivity(intent);
            }
        });

        // 채팅 레이아웃 클릭 이벤트 설정
        chatLayout = findViewById(R.id.chat_layout);
        chatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterSearchActivity.this,  ChatHome.class);
                startActivity(intent);
            }
        });
    }

    private void searchProducts() {
        String newQuery = searchEditText.getText().toString();
        if (!TextUtils.isEmpty(newQuery)) {
            query = newQuery;
            loadProducts(query);

            // 키보드 숨기기
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private void loadProducts(String query) {
        // 실제 앱에서는 서버 또는 데이터베이스에서 검색어에 따라 데이터를 가져옵니다.
        productList.clear();

        List<Product> allProducts = new ArrayList<>();
        allProducts.add(new Product("뉴진스 인형", "10000원"));
        allProducts.add(new Product("에스파 앨범", "20000원"));
        allProducts.add(new Product("뉴진스 민지 포카", "15000원"));
        allProducts.add(new Product("방탄 인형", "15000원"));
        allProducts.add(new Product("방탄소년단 슬로건", "15000원"));
        allProducts.add(new Product("뉴진스 가방", "15000원"));
        allProducts.add(new Product("에스파 윈터 포카", "15000원"));

        // 검색어에 따라 데이터를 필터링
        for (Product product : allProducts) {
            if (product.getName().contains(query)) {
                productList.add(product);
            }
        }
        productAdapter.notifyDataSetChanged();
    }
}
