package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ImageView backButton, searchButton;
    private LinearLayout recentSearchLayout;
    private EditText searchEditText;
    private List<String> recentSearches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backButton = findViewById(R.id.back_button);
        searchButton = findViewById(R.id.search_button);
        recentSearchLayout = findViewById(R.id.recent_search_layout);
        searchEditText = findViewById(R.id.search_edit_text);

        recentSearches = new ArrayList<>();
        recentSearches.add("최근 검색어 1");
        recentSearches.add("최근 검색어 2");
        recentSearches.add("최근 검색어 3");
        recentSearches.add("최근 검색어 4");

        addRecentSearches(recentSearches);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchEditText.getText().toString();
                if (!query.isEmpty()) {
                    Intent intent = new Intent(SearchActivity.this, AfterSearchActivity.class);
                    intent.putExtra("QUERY", query);
                    startActivity(intent);
                }
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    String query = searchEditText.getText().toString();
                    if (!query.isEmpty()) {
                        Intent intent = new Intent(SearchActivity.this, AfterSearchActivity.class);
                        intent.putExtra("QUERY", query);
                        startActivity(intent);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void addRecentSearches(List<String> searches) {
        recentSearchLayout.removeAllViews();
        for (String search : searches) {
            View searchItemView = LayoutInflater.from(this).inflate(R.layout.recent_search_item, recentSearchLayout, false);
            TextView searchTextView = searchItemView.findViewById(R.id.search_text);
            ImageView deleteIcon = searchItemView.findViewById(R.id.delete_icon);

            searchTextView.setText(search);
            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 최근 검색어 삭제
                    recentSearchLayout.removeView(searchItemView);
                    recentSearches.remove(search);
                }
            });
            recentSearchLayout.addView(searchItemView);
        }
    }
}
