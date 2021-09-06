package android.nguyenphuocthienan.projecmain1.Fragment;

import android.nguyenphuocthienan.projecmain1.AdapterDrink.ProductCartAdapter;
import android.nguyenphuocthienan.projecmain1.MainActivity;
import android.nguyenphuocthienan.projecmain1.Model.DetailOrder;
import android.nguyenphuocthienan.projecmain1.Model.Drink;
import android.nguyenphuocthienan.projecmain1.R;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartFragment extends Fragment {
    private int totalPrice;
    private View mView;
    private MainActivity mMainActivity;
    private DecimalFormat format;

    // Item empty cart
    private RelativeLayout rlCartEmpty,rlCart;

    // Item product cart
    private List<Drink> listCartProduct;
    private RecyclerView rcvCartProduct;
    private TextView tvCartTotalPrice;
    private EditText edtCartCustName,edtCartCustAddress,edtCartCustPhone;
    private Button btnCartOrder;

    private ProductCartAdapter productCartAdapter;

    // endregion Variable

    public CartFragment(List<Drink> listCartProduct) {
        this.listCartProduct = listCartProduct;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cart, container, false);
        mMainActivity = (MainActivity) getActivity();
        // Khởi tạo các item
        initItem();

        // Set hiển thị các view
        setVisibilityView();

        return mView;
    }


    // Khởi tạo các item
    private void initItem(){
        listCartProduct = mMainActivity.getListCartProduct();
        productCartAdapter = new ProductCartAdapter();
        rlCartEmpty = mView.findViewById(R.id.rl_cart_empty);
        rlCart = mView.findViewById(R.id.rl_cart);
        rcvCartProduct = mView.findViewById(R.id.rcv_cart_product);
        tvCartTotalPrice = mView.findViewById(R.id.tv_cart_total_price);
        edtCartCustName = mView.findViewById(R.id.edt_cart_cust_name);
        edtCartCustAddress = mView.findViewById(R.id.edt_cart_cust_address);
        edtCartCustPhone = mView.findViewById(R.id.edt_cart_cust_phone);
        btnCartOrder = mView.findViewById(R.id.btn_cart_order);
        btnCartOrder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                // Thêm dữ liệu các thông tin đã order
                addDataOrder();
            }
        });

        mMainActivity = (MainActivity) getActivity();
        format = new DecimalFormat("###,###,###");
    }

    // Set trạng thái hiển thị các view
    private void setVisibilityView(){
        if (listCartProduct.size() == 0){

            // Hiển thị giỏ hàng rỗng
            setVisibilityEmptyCart();
        }else {

            // Hiển thị giỏ hàng
            setVisibilityCart();
            setDataProductCartAdapter();
        }
    }

    // Hiển thị giỏ hàng
    private void setVisibilityCart(){
        rlCartEmpty.setVisibility(View.GONE);
        rlCart.setVisibility(View.VISIBLE);
        String total = format.format(getTotalPrice());
        tvCartTotalPrice.setText( total +" VNĐ" );
    }

    // set data ProductCartAdapter
    private void setDataProductCartAdapter(){
        productCartAdapter.setData(listCartProduct,mMainActivity,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainActivity,RecyclerView.VERTICAL,false);
        rcvCartProduct.setLayoutManager(linearLayoutManager);
        rcvCartProduct.setAdapter(productCartAdapter);
    }

    // lấy giá trị tổng tiền tất cả sản phẩm trong giỏ hàng
    private int getTotalPrice(){
        for (Drink drink : listCartProduct){
            int priceProduct = drink.getPrice() ;
            totalPrice = totalPrice +  priceProduct * drink.getNumProduct();
        }
        return totalPrice;
    }

    // Thêm dữ liệu các thông tin đã order
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addDataOrder(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Order");

        Map<String,Object> map = new HashMap<>();

        // Lấy ngày order = now
        Date date = new Date(System.currentTimeMillis());
        map.put("dateOrder", date.toString());
        map.put("Name",edtCartCustName.getText().toString());
        map.put("Address",edtCartCustAddress.getText().toString());
        map.put("Phone",edtCartCustPhone.getText().toString());

        int num = 0;
        for (Drink drink : listCartProduct){
            num = num + drink.getNumProduct();
        }
        map.put("numProduct",num);
        map.put("totalPrice",totalPrice);
        map.put("status","Đang chờ xác nhận");

        // Add thông tin order
        String odrKey = myRef.push().getKey();
        myRef.child(odrKey).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                List<DetailOrder> listDetailOrder = makeDetailOrder(odrKey);

                // Add thông tin detail order
                for (DetailOrder detailOrder : listDetailOrder){

                    myRef.child(odrKey).child("detail").child(myRef.push().getKey())
                            .setValue(detailOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(),"Đã đăng ký đơn hàng",Toast.LENGTH_SHORT).show();
                            listCartProduct.clear();
                            setVisibilityEmptyCart();
                            mMainActivity.setCountProductInCart(0);
                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Đăng ký đơn hàng thất bại",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<DetailOrder> makeDetailOrder(String odrNo){
        List<DetailOrder> listDetailOrder = new ArrayList<>();
        for (Drink product : mMainActivity.getListCartProduct()){
            DetailOrder detailOrder = new DetailOrder();
            detailOrder.setOrderNo(odrNo);
            detailOrder.setName(product.getName());
            detailOrder.setPrice(product.getPrice());
            detailOrder.setImageUrl(product.getImageUrl());
            detailOrder.setNumProduct(product.getNumProduct());
            detailOrder.setStatus("Đang chờ xác nhận");
            listDetailOrder.add(detailOrder);
        }
        return listDetailOrder;
    }

    // endregion Private menthod

    // region Public menthod

    // Hiển thị giỏ hàng rỗng
    public void setVisibilityEmptyCart(){
        rlCartEmpty.setVisibility(View.VISIBLE);
        rlCart.setVisibility(View.GONE);
    }

    // Set giá trị hiển thị tổng tiền khi tăng giảm số lượng
    // mode = 0 : giảm
    // mode = 1 : tăng
    public void setTotalPrice(int mode,int count, int priceProduct ){
        if( mode == 0){
            totalPrice = totalPrice - priceProduct * count;
        }else if (mode == 1){
            totalPrice = totalPrice + priceProduct * count;
        }

        tvCartTotalPrice.setText( format.format(totalPrice) + " VNĐ");
    }

    // Set sô lượng sản phẩm sau nhấn nút tăng giảm
    public void setCountForProduct(int possion,int countProduct){
        listCartProduct.get(possion).setNumProduct(countProduct);
    }
}
