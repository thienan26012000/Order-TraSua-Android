package android.nguyenphuocthienan.projecmain1.AdapterDrink;

import android.annotation.SuppressLint;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends FirebaseRecyclerAdapter<Drink, MyAdapter.MyViewHolder> {
    private Button btnAddToCart;
    private FirebaseRecyclerOptions<Drink> mListDrink;
    private MainActivity mMainActivity;
    private Drink drink;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(FirebaseRecyclerOptions<Drink> list,MainActivity mMainActivity) {
        this.mListDrink = list;
        this.mMainActivity = mMainActivity;
        notifyDataSetChanged();
    }

    public MyAdapter(@NonNull FirebaseRecyclerOptions<Drink> options) {
        super(options);
    }

    // interface
//    public interface IClickAddToCartListener{
//        void onClickAddToCart(ImageView imgAddToCart, Drink drink);
//    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Drink model) {
        holder.nametext.setText(model.getName());
        holder.pricetext.setText(model.getPrice() + " VND");
        holder.drink = model;
        Glide.with(holder.img1.getContext()).load(model.getImageUrl()).into(holder.img1);

        // bắt sự kiện Add to Cart
//        holder.imgAddToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!model.isAddToCart()){
//                    iClickAddToCartListener.onClickAddToCart(holder.imgAddToCart, model);
//                }
//            }
//        });


        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    if (isAddToCart){

//                    }
//                    else {
//                ImageView imageView = v.findViewById(R.id.img1);
//                String imgUrl = imageView.getTag().toString();
//                TextView tvname = v.findViewById(R.id.nametext);
//                String name = tvname.getText().toString();
//                TextView tvprice = v.findViewById(R.id.pricetext);
//                int price = Integer.parseInt(tvprice.getText().toString());
                v.findViewById(R.id.btn_add_to_cart).setBackgroundResource(R.drawable.custom_button_gray);
                mMainActivity.addToListCartProdct(holder.drink);
//                mMainActivity.setCountProductInCart(mMainActivity.getCountProduct() + 1);
                Toast.makeText(mMainActivity,"Đã thêm sản phẩm vào giỏ hàng",Toast.LENGTH_SHORT).show();
//                    }
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new MyViewHolder(view, drink);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img1;
        private TextView nametext;
        private TextView pricetext;
        private Button btnAddToCart;
        private Drink drink;

        public MyViewHolder(@NonNull View itemView, Drink drink) {
            super(itemView);

            this.drink = drink;
            img1 = itemView.findViewById(R.id.img1);
            nametext = itemView.findViewById(R.id.nametext);
            pricetext = itemView.findViewById(R.id.pricetext);
            btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        }
    }
}
