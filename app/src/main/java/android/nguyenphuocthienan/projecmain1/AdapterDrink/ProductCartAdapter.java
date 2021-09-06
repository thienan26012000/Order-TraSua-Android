package android.nguyenphuocthienan.projecmain1.AdapterDrink;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import android.nguyenphuocthienan.projecmain1.Model.Drink;
import android.nguyenphuocthienan.projecmain1.Fragment.CartFragment;
import android.nguyenphuocthienan.projecmain1.MainActivity;
import android.nguyenphuocthienan.projecmain1.R;

import java.text.DecimalFormat;
import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ProductCartViewHolder> {

    private DecimalFormat formatPrice = new DecimalFormat("###,###,###");
    private List<Drink> listProductCart;
    private int countProduct;
    private MainActivity mMainActivity;
    private CartFragment cartFragment;

    public void setData(List<Drink> listProductCart, MainActivity mMainActivity,CartFragment cartFragment) {
        this.listProductCart = listProductCart;
        this.mMainActivity = mMainActivity;
        this.cartFragment = cartFragment;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row,parent,false);
        return new ProductCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Drink drink = listProductCart.get(position);
        if (drink == null){
            return;
        }
        else {
            Glide.with(holder.imgPhotoCart.getContext()).load(drink.getImageUrl()).into(holder.imgPhotoCart);
            holder.tvNameProductCart.setText(drink.getName());
            holder.tvPriceProductCart.setText( formatPrice.format(drink.getPrice())+ " VNĐ");

            int count = drink.getNumProduct();
            if(count != 0){
                holder.tvCountCart.setText(String.valueOf(count));
            }else {
                holder.tvCountCart.setText(String.valueOf(1));
            }

            holder.imgMinusCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countProduct = Integer.parseInt(holder.tvCountCart.getText().toString());
                    if (countProduct > 1){
                        countProduct--;
                        mMainActivity.setCountProductInCart(mMainActivity.getCountProduct() - 1);
                        mMainActivity.setCountForProduct(position,countProduct);
                        cartFragment.setCountForProduct(position,countProduct);
                        holder.tvCountCart.setText(String.valueOf(countProduct));
                        cartFragment.setTotalPrice(0,1,drink.getPrice());
                        notifyDataSetChanged();
                    }
                }
            });

            holder.imgPlusCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countProduct = Integer.parseInt(holder.tvCountCart.getText().toString());
                    if (countProduct < 10){
                        countProduct++;
                        mMainActivity.setCountProductInCart(mMainActivity.getCountProduct() + 1);
                        mMainActivity.setCountForProduct(position,countProduct);
                        cartFragment.setCountForProduct(position,countProduct);
                        holder.tvCountCart.setText(String.valueOf(countProduct));
                        cartFragment.setTotalPrice(1,1,drink.getPrice());
                        notifyDataSetChanged();
                    }
                }
            });

            holder.imgRemoveCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countProduct = Integer.parseInt(holder.tvCountCart.getText().toString());
                    mMainActivity.setCountProductInCart(mMainActivity.getCountProduct() - countProduct);

                    cartFragment.setTotalPrice(0,countProduct,drink.getPrice());

                    listProductCart.remove(position);
                    if (listProductCart.size() == 0){
                        cartFragment.setVisibilityEmptyCart();
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    public class ProductCartViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhotoCart;
        TextView tvNameProductCart, tvPriceProductCart,tvCountCart ;
        ImageView imgMinusCart,imgPlusCart,imgRemoveCart;

        public ProductCartViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhotoCart = itemView.findViewById(R.id.img_photo_cart);
            tvNameProductCart = itemView.findViewById(R.id.tv_name_product_cart);
            tvPriceProductCart = itemView.findViewById(R.id.tv_price_product_cart);
            imgMinusCart = itemView.findViewById(R.id.img_minus_cart);
            tvCountCart = itemView.findViewById(R.id.tv_count_cart);
            imgPlusCart = itemView.findViewById(R.id.img_plus_cart);
            imgRemoveCart = itemView.findViewById(R.id.img_remove_cart);
        }
    }

    @Override
    public int getItemCount() {
        if(listProductCart != null){
            return listProductCart.size();
        }
        return 0;
    }
}
