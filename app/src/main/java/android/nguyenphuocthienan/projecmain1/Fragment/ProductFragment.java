package android.nguyenphuocthienan.projecmain1.Fragment;

import android.annotation.SuppressLint;
import android.nguyenphuocthienan.projecmain1.AdapterDrink.AddToCartAdapter;
import android.nguyenphuocthienan.projecmain1.AdapterDrink.MyAdapter;
import android.nguyenphuocthienan.projecmain1.Animation.AnimationUtil;
import android.nguyenphuocthienan.projecmain1.MainActivity;
import android.nguyenphuocthienan.projecmain1.Model.Drink;
import android.nguyenphuocthienan.projecmain1.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {
    private DecimalFormat format = new DecimalFormat("###,###,###");

    // Kiểm tra Product đã được thêm vào cart chưa
    private Boolean isAddToCart;

    // Activity
    private MainActivity mMainActivity;

    // View
    private View mView;
    private Drink detailProduct;
    private List<Drink> listCartProduct;
    private TextView tvDetailProductName,tvDetailProductPrice;
    private Button btnDetailProductBuy;
    private ImageView imgDetailProductPhoto;

    private RecyclerView rcvdrink;
    MyAdapter adapter;
    private AddToCartAdapter addToCartAdapter;


    public ProductFragment(Drink drink,List<Drink> listProduct) {
        detailProduct = drink;
        listCartProduct = listProduct;
    }


    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_product, container, false);
        mMainActivity = (MainActivity) getActivity();

        rcvdrink = (RecyclerView)mView.findViewById(R.id.recycler_drink);
        rcvdrink.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Drink> options = new FirebaseRecyclerOptions.Builder<Drink>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Products").child("Drink"), Drink.class)
                .build();

        adapter = new MyAdapter(options);
        adapter.setData(options, mMainActivity);
        rcvdrink.setAdapter(adapter);

        initItem();

//        setDataProductCartAdapter();

        return mView;
    }

    // Khởi tạo các item
    private void initItem(){
        addToCartAdapter = new AddToCartAdapter();
        isAddToCart = false;
        mMainActivity = (MainActivity) getActivity();
//        tvDetailProductName = mView.findViewById(R.id.nametext);
//        tvDetailProductPrice = mView.findViewById(R.id.pricetext);
//        imgDetailProductPhoto = mView.findViewById(R.id.img1);
//        btnDetailProductBuy = mView.findViewById(R.id.btn_add_to_cart);
        rcvdrink = mView.findViewById(R.id.recycler_drink);

        if(listCartProduct == null){
            listCartProduct = new ArrayList<>();
        }
    }

    // set data ProductCartAdapter
    private void setDataProductCartAdapter(List<Drink> listCart ){
        addToCartAdapter.setData(listCart,mMainActivity,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainActivity,RecyclerView.VERTICAL,false);
        rcvdrink.setLayoutManager(linearLayoutManager);
        rcvdrink.setAdapter(addToCartAdapter);
    }

    // set giá trị cho các item
    private void setValueItem(){
        if (detailProduct != null){
            tvDetailProductName.setText(detailProduct.getName());
            tvDetailProductPrice.setText(format.format(detailProduct.getPrice() ) + " VNĐ");
            Glide.with(getContext()).load(detailProduct.getImageUrl()).into(imgDetailProductPhoto);

            // Kiểm tra sản phẩm đã dc add vào giỏ hay chưa
            for (int i = 0;i< listCartProduct.size();i++){

                // Nếu sản  phẩm đã dc add
                if (listCartProduct.get(i).getName().equals(detailProduct.getName())){
                    isAddToCart = true;
                    btnDetailProductBuy.setText("Đã Mua");
                    btnDetailProductBuy.setBackgroundResource(R.drawable.custom_button_gray);
                    break;
                }
            }

            btnDetailProductBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAddToCart){
                        Toast.makeText(getActivity(),"Sản Phẩm này đã tồn tại trong giỏ hàng",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        isAddToCart = true;
                        btnDetailProductBuy.setText("Đã Thêm");
                        btnDetailProductBuy.setBackgroundResource(R.drawable.custom_button_gray);
//                        mMainActivity.addToListCartProdct();
                        mMainActivity.setCountProductInCart(mMainActivity.getCountProduct() + 1);
                        Toast.makeText(getActivity(),"Đã thêm sản phẩm vào giỏ hàng",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
