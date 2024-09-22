package com.example.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    private ImageView searchIcon;
    private ImageView detailIcon;
    private LinearLayout homeLayout;
    private LinearLayout interestLayout;
    private LinearLayout chatLayout;
    private LinearLayout communityLayout;
    private LinearLayout mypageLayout;
    private RecyclerView favoritesContainer;

    private ImageView bannerImageView;
    private int[] bannerImages = {R.drawable.ic_banner, R.drawable.ic_banner2, R.drawable.ic_banner3}; // 이미지 배열
    private int currentBannerIndex = 0;

    private static final int MAX_FAVORITES = 3; // 최대 즐겨찾기 개수
    private static final int BANNER_CHANGE_INTERVAL = 3000; // 3초 간격

    private BroadcastReceiver favoritesUpdateReceiver;
    private FavoritesAdapter favoritesAdapter;
    private Handler bannerHandler = new Handler(Looper.getMainLooper());

    private Button btnUpload;

    private Runnable bannerRunnable = new Runnable() {
        @Override
        public void run() {
            // 배너 이미지 변경
            currentBannerIndex = (currentBannerIndex + 1) % bannerImages.length;
            bannerImageView.setImageResource(bannerImages[currentBannerIndex]);

            // 3초 후 다시 실행
            bannerHandler.postDelayed(this, BANNER_CHANGE_INTERVAL);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // SharedPreferences에서 username 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("FaniversePrefs", MODE_PRIVATE);
        String userName = sharedPreferences.getString("username", null);

        /*
        // LoginActivity로부터 사용자 이름을 받아옴
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName"); */

        // 사용자 이름이 있을 경우 환영 메시지 표시
        if (userName != null) {
            Toast.makeText(this, userName + "님 환영합니다", Toast.LENGTH_SHORT).show();

            // 몇 초 후에 메시지를 사라지게 하려면 Handler를 사용
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 토스트는 자동으로 사라지므로 추가 작업이 필요하지 않음
                }
            }, 2000); // 2초 후 사라짐
        }

        // UI 요소 초기화
        initializeUIElements();

        // 클릭 이벤트 설정
        setClickListeners();

        // RecyclerView 설정
        setupRecyclerView();

        // 즐겨찾기 항목 업데이트
        updateFavoriteBoards();

        // 3초마다 배너 이미지를 변경하는 작업 시작
        bannerHandler.postDelayed(bannerRunnable, BANNER_CHANGE_INTERVAL);

        // BroadcastReceiver를 사용하여 즐겨찾기 업데이트 감지
        favoritesUpdateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateFavoriteBoards(); // 즐겨찾기 게시판 업데이트
            }
        };

        IntentFilter filter = new IntentFilter("com.example.login.UPDATE_FAVORITES");
        ContextCompat.registerReceiver(this, favoritesUpdateReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED);

        // 업로드 버튼 클릭 리스너 설정
        btnUpload.setOnClickListener(view -> showPopup(view));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (favoritesUpdateReceiver != null) {
            unregisterReceiver(favoritesUpdateReceiver);
        }

        // 배너 이미지 변경 작업 중지
        bannerHandler.removeCallbacks(bannerRunnable);
    }

    private void initializeUIElements() {
        searchIcon = findViewById(R.id.search_icon);
        detailIcon = findViewById(R.id.image_detail);
        homeLayout = findViewById(R.id.home_layout);
        interestLayout = findViewById(R.id.interest_layout);
        chatLayout = findViewById(R.id.chat_layout);
        communityLayout = findViewById(R.id.community_layout);
        mypageLayout = findViewById(R.id.mypage_layout);
        favoritesContainer = findViewById(R.id.favorites_container);
        bannerImageView = findViewById(R.id.banner_image); // 배너 ImageView 초기화
        btnUpload = findViewById(R.id.btn_upload); // 업로드 버튼 초기화
    }

    private void setClickListeners() {
        searchIcon.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        detailIcon.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, DetailPage_buyer.class);
            startActivity(intent);
        });

        homeLayout.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, HomeActivity.class)));
        interestLayout.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, InterestActivity.class)));
        chatLayout.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, ChatHome.class)));
        communityLayout.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, CommunityActivity.class)));
        mypageLayout.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, MyPage.class)));
    }

    private void openDetailPage() {
        Intent intent = new Intent(HomeActivity.this, DetailPage_buyer.class);
        startActivity(intent);
    }

    private void setupRecyclerView() {
        favoritesContainer.setLayoutManager(new LinearLayoutManager(this));
        favoritesAdapter = new FavoritesAdapter(new ArrayList<>());
        favoritesContainer.setAdapter(favoritesAdapter);
    }

    private void updateFavoriteBoards() {
        SharedPreferences prefs = getSharedPreferences("favorites", MODE_PRIVATE);
        Set<String> favoriteSet = prefs.getStringSet("favorite_communities", new HashSet<>());

        // 리스트를 ArrayList로 변환하고 최대 개수 제한
        List<String> favoriteList = new ArrayList<>(favoriteSet);
        if (favoriteList.size() > MAX_FAVORITES) {
            favoriteList = favoriteList.subList(0, MAX_FAVORITES);
        }

        // 어댑터에 데이터 업데이트
        favoritesAdapter.updateFavorites(favoriteList);
    }

    private void showPopup(View anchorView) {
        // 팝업 레이아웃을 인플레이트
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_sales_option, null);

        // PopupWindow를 생성하고 설정
        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // 팝업 위치 설정: 버튼 바로 위에 나타나도록
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);

        // 팝업 창을 측정하여 정확한 높이 값을 얻음
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupHeight = popupView.getMeasuredHeight();

        // 팝업 위치를 조정하여 + 버튼 바로 위에 나타나도록 설정
        popupWindow.showAtLocation(anchorView, 0, location[0], location[1] - popupHeight - 80);

        // 일반 판매 버튼
        Button btnGeneralSales = popupView.findViewById(R.id.btn_general_sales);
        btnGeneralSales.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, GeneralSales.class);
            startActivity(intent);
            popupWindow.dismiss(); // 팝업 닫기
        });

        // 경매 판매 버튼
        Button btnAuctionSales = popupView.findViewById(R.id.btn_auction_sales);
        btnAuctionSales.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AuctionSales.class);
            startActivity(intent);
            popupWindow.dismiss(); // 팝업 닫기
        });
    }
}
