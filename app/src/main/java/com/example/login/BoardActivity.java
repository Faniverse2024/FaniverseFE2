package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    public static List<Post> postList;
    public static PostAdapter postAdapter;
    private ImageView backButton;
    private TextView toolbarTitle;
    private String loggedInUserName = "Logged In User"; // 예시: 실제 로그인한 사용자 이름으로 대체해야 함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        // Toolbar 제목 설정
        toolbarTitle = findViewById(R.id.toolbar_title);
        Intent intent = getIntent();
        String communityName = intent.getStringExtra("communityName");
        int communityId = intent.getIntExtra("communityId", -1); // 전달된 communityId 가져오기
        if (communityName != null) {
            toolbarTitle.setText(communityName);
        }

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티를 종료하고 이전 액티비티로 돌아갑니다.
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 예제 데이터를 데이터베이스에서 가져오도록 수정
        postList = getPostsByCommunityId(communityId); // 커뮤니티 ID에 따른 게시글 목록 가져오기

        postAdapter = new PostAdapter(this, postList, loggedInUserName);
        recyclerView.setAdapter(postAdapter);
    }

    private List<Post> getPostsByCommunityId(int communityId) {
        List<Post> posts = new ArrayList<>();

        // 데이터베이스 또는 서버에서 게시글을 가져오는 로직 추가
        // 이 부분은 데이터베이스 또는 서버와 연동하는 방식에 따라 달라집니다.

        // 예제 데이터로 대체:
        long currentTime = System.currentTimeMillis();
        if (communityId == 1) {
            posts.add(new Post(1, "username", "내용", 21, 4, currentTime - 180000, R.drawable.ic_star));
        } else if (communityId == 2) {
            posts.add(new Post(2, "username", "내용\n내용\n내용", 6, 18, currentTime - 7200000, 0));
        }

        return posts;
    }
}