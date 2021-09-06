package android.nguyenphuocthienan.projecmain1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.nguyenphuocthienan.projecmain1.Fragment.CartFragment;
import android.nguyenphuocthienan.projecmain1.Fragment.HomeFragment;
import android.nguyenphuocthienan.projecmain1.Fragment.ProductFragment;
import android.nguyenphuocthienan.projecmain1.Model.Drink;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int MY_REQUEST_CODE = 10;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_PRODUCT = 1;
    private static final int FRAGMENT_CART = 2;

    private int mCurrentFragment = FRAGMENT_HOME;

    private DrawerLayout mDrawerLayout;
    private ImageView imgAvatar;
    private TextView tvName, tvEmail;

    private List<Drink> listCartProduct = new ArrayList<>();

    // Đếm số sản phẩm trong giỏ hàng
    private int countProduct;

    private View viewEndAnimation;
    private ImageView viewAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add tool bar đồng thời bắt sự kiện khi click vào icon của tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUi();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // bắt đầu app vào HOME_FRAGMENT trước
        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            if (mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        } else if (id == R.id.nav_product){
            if (mCurrentFragment != FRAGMENT_PRODUCT){
                replaceFragment(new ProductFragment());
                mCurrentFragment = FRAGMENT_PRODUCT;
            }
        } else if (id == R.id.nav_cart){
            if (mCurrentFragment != FRAGMENT_CART){
                replaceFragment(new CartFragment(listCartProduct));
                mCurrentFragment = FRAGMENT_CART;
            }
        } else if (id == R.id.nav_my_profile) {
            Intent intent = new Intent(this, UsersActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_sign_out) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        // đóng drawer lại
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // Ánh xạ view
    private void initUi(){
        listCartProduct = new ArrayList<>();
        imgAvatar = findViewById(R.id.img_header);
        tvName = findViewById(R.id.username_header);
        tvEmail = findViewById(R.id.email_header);
        viewEndAnimation = findViewById(R.id.view_end_animation);
        viewAnimation = findViewById(R.id.view_animation);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    public View getViewEndAnimation() {
        return viewEndAnimation;
    }

    public ImageView getViewAnimation() {
        return viewAnimation;
    }

    // Thêm sản phẩm đã chọn vào giỏ hàng
    public void addToListCartProdct(Drink drink){
        listCartProduct.add(drink);
    }

    // Lấy ra các sản phẩm đã thêm vào giỏ hàng
    public List<Drink> getListCartProduct() {
        return listCartProduct;
    }

    // Lấy ra số lượng các sản phẩm đã thêm vào giỏ hàng
    public int getCountProduct() {
        return countProduct;
    }

    // Set lại số lượng của sản phẩm khi mua nhiều
    public void setCountForProduct(int possion, int countProduct){
        listCartProduct.get(possion).setNumProduct(countProduct);
    }

    // Set số lượng các sản phẩm trong giỏ hàng
    public void setCountProductInCart(int count){
        countProduct = count;
    }
}