package android.nguyenphuocthienan.projecmain1.AdapterDrink;

import android.annotation.SuppressLint;
import android.nguyenphuocthienan.projecmain1.Fragment.CartFragment;
import android.nguyenphuocthienan.projecmain1.Fragment.ProductFragment;
import android.nguyenphuocthienan.projecmain1.MainActivity;
import android.nguyenphuocthienan.projecmain1.Model.Drink;
import android.nguyenphuocthienan.projecmain1.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DecimalFormat;
import java.util.List;

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.AddToCartViewHolder> {

    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    private List<Drink> listProductCart;
    private int countProduct;
    private MainActivity mMainActivity ;
    private ProductFragment productFragment;

    public void setData(List<Drink> listProductCart, MainActivity mMainActivity, ProductFragment productFragment) {
        this.listProductCart = listProductCart;
        this.mMainActivity = mMainActivity;
        this.productFragment = productFragment;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddToCartAdapter.AddToCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new AddToCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddToCartAdapter.AddToCartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Drink drink = listProductCart.get(position);
        if (drink == null){
            return;
        }
        else {
            Glide.with(holder.imgPhotoCart.getContext()).load(drink.getImageUrl()).into(holder.imgPhotoCart);
            holder.tvNameProductCart.setText(drink.getName());
            holder.tvPriceProductCart.setText( formatPrice.format(drink.getPrice())+ " VNĐ");

            holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (isAddToCart){

//                    }
//                    else {
//                        isAddToCart = true;
//                        btnDetailProductBuy.setText("Đã Thêm");
//                        btnDetailProductBuy.setBackgroundResource(R.drawable.custom_button_gray);
//                        home.addToListCartProdct(detailProduct);
//                        home.setCountProductInCart(home.getCountProduct() + 1);
//                        Toast.makeText(getActivity(),"Đã thêm sản phẩm vào giỏ hàng",Toast.LENGTH_SHORT).show();
//                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AddToCartViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhotoCart;
        TextView tvNameProductCart, tvPriceProductCart ;
        Button btnAddToCart ;

        public AddToCartViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhotoCart = itemView.findViewById(R.id.img1);
            tvNameProductCart = itemView.findViewById(R.id.nametext);
            tvPriceProductCart = itemView.findViewById(R.id.pricetext);
            btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        }
    }
}
