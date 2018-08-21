package com.example.aidong.ui.mine.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.R;
import com.example.aidong.adapter.mine.CartShopAdapter;
import com.example.aidong.entity.BaseBean;
import com.example.aidong.entity.GoodsBean;
import com.example.aidong.entity.ShopBean;
import com.example.aidong.entity.data.ShopData;
import com.example.aidong.http.RetrofitHelper;
import com.example.aidong.http.api.CartService;
import com.example.aidong.http.subscriber.RefreshSubscriber;
import com.example.aidong.ui.home.activity.ConfirmOrderCartActivity;
import com.example.aidong.ui.mvp.model.impl.CartModelImpl;
import com.example.aidong.ui.mvp.presenter.impl.CartPresentImpl;
import com.example.aidong.ui.mvp.view.ICartHeaderView;
import com.example.aidong.utils.FormatUtil;
import com.example.aidong.utils.GlideLoader;
import com.example.aidong.utils.ToastGlobal;
import com.example.aidong.widget.SwitcherLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * the header of cart view
 * Created by song on 2017/2/22.
 */
public class CartHeaderView2 extends RelativeLayout {
    private Context context;
    private CartPresentImpl cartPresent;
    private RecyclerView recyclerView;


    public CartHeaderView2(Context context, List<String> reBuyIds) {
        this(context, null, 0);
        this.context = context;
        initView();
    }

    public CartHeaderView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CartHeaderView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        View headerView = LayoutInflater.from(context).inflate(R.layout.header_cart_view2, this, true);

        recyclerView = headerView.findViewById(R.id.recyclerView);
        loadData();


    }

    private void loadData() {
        CartModelImpl cartModel = new CartModelImpl();
        cartModel.getCart(new RefreshSubscriber<ShopData>(context) {
            @Override
            public void onNext(ShopData shopData) {
                if (shopData != null && shopData.getCart() != null && !shopData.getCart().isEmpty()) {

                    List<ShopBean> cartList = shopData.getCart();
                    List<GoodsBean> goodsBeanList = new ArrayList<>();

                    for (int i = 0; i < cartList.size(); i++) {

                        for (int i1 = 0; i1 < cartList.get(i).getItem().size(); i1++) {
                            cartList.get(i).getItem().get(i1).TYPEVALUE = "TYPE" + i;

                            cartList.get(i).getItem().get(i1).pinkUpAddress = cartList.get(i).getPickUp().getInfo().getAddress();
                            cartList.get(i).getItem().get(i1).pinkUpId = cartList.get(i).getPickUp().getInfo().getId();
                            cartList.get(i).getItem().get(i1).pinkUpName = cartList.get(i).getPickUp().getInfo().getName();
                            cartList.get(i).getItem().get(i1).pinkUpType = cartList.get(i).getPickUp().getType();

                            goodsBeanList.add(cartList.get(i).getItem().get(i1));
                        }

                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(new CartHeaderView2Adapter(context, goodsBeanList));


                } else {

                }
            }
        });


    }


    public class CartHeaderView2Adapter extends RecyclerView.Adapter<CartHeaderView2Adapter.ViewHolder> {

        public Context context;
        public List<GoodsBean> cartList;
        String typeValue = "flag";
        boolean  singelselectflag = false;

        public CartHeaderView2Adapter(Context context, List<GoodsBean> cartList) {
            this.cartList = cartList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

            ViewHolder viewHolder = new ViewHolder(View.inflate(context, R.layout.cartheaderview2adapter, null));


            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

            GlideLoader.getInstance().displayImage(cartList.get(position).getCover(), viewHolder.cover);


            viewHolder.tv_delivery_type.setText("0".equals(cartList.get(position).pinkUpType) ? "快递" : "自提");

            if (!cartList.get(position).TYPEVALUE.equals(typeValue)) {
                viewHolder.rb_check.setText(cartList.get(position).pinkUpName);
                viewHolder.ll0.setVisibility(VISIBLE);
            } else {
                viewHolder.ll0.setVisibility(GONE);
            }
            typeValue = cartList.get(position).TYPEVALUE;

            viewHolder.name.setText(cartList.get(position).getName());
            viewHolder.count.setText(cartList.get(position).getAmount());


            ArrayList<String> specName = cartList.get(position).getSpecName();
            ArrayList<String> specValue = cartList.get(position).getSpecValue();
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < specValue.size(); i++) {
                result.append(specName.get(i)).append(":").append(specValue.get(i)).append(" ");
            }
            viewHolder.sku.setText(result);


            viewHolder.price.setText(String.format(context.getString(R.string.rmb_price_double),
                    FormatUtil.parseDouble(cartList.get(position).getPrice())));

            if (!TextUtils.isEmpty(cartList.get(position).getRecommendCode())) {
                viewHolder.code.setText(String.format(context.getString(R.string.recommend_code),
                        cartList.get(position).getRecommendCode()));
            }


            if (cartList.get(position).isSelect) {
                viewHolder.rb_check_item.setChecked(true);
            } else {
                viewHolder.rb_check_item.setChecked(false);
            }


            if (cartList.get(position).isOnline() && cartList.get(position).getStock() != 0) {

                if (FormatUtil.parseInt(cartList.get(position).getAmount()) == cartList.get(position).limit_amount) {
                    //viewHolder.count.setText(String.valueOf(cartList.get(position).getAmount()));
                    viewHolder.add.setImageResource(R.drawable.icon_add_gray);
                } else {
                    viewHolder.add.setImageResource(R.drawable.icon_add);
                }

                if (FormatUtil.parseInt(cartList.get(position).getAmount()) == 1) {
                    viewHolder.minus.setImageResource(R.drawable.icon_minus_gray);
                } else {
                    viewHolder.minus.setImageResource(R.drawable.icon_minus);
                }

            }


            viewHolder.add.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });


            viewHolder.rb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        for (int i1 = 0; i1 < cartList.size(); i1++) {
                            if (cartList.get(i1).TYPEVALUE.equals(cartList.get(position).TYPEVALUE)) {
                                cartList.get(i1).isSelect = true;

                                viewHolder.rb_check_item.setChecked(true);


                            }
                        }


                    } else {
                        for (int i1 = 0; i1 < cartList.size(); i1++) {
                            if (cartList.get(i1).TYPEVALUE.equals(cartList.get(position).TYPEVALUE)) {
                                cartList.get(i1).isSelect = false;
                                viewHolder.rb_check_item.setChecked(false);

                            }
                        }
                    }

                  //  notifyDataSetChanged();
                }
            });


            viewHolder.rb_check_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    ArrayList<GoodsBean> temp = new ArrayList<>();

                    if (isChecked) {
                        viewHolder.rb_check_item.setChecked(true);
                        cartList.get(position).isSelect = true;


                        // 找出相同的类别
                        for (int i1 = 0; i1 < cartList.size(); i1++) {
                            if (cartList.get(position).TYPEVALUE.equals(cartList.get(i1).TYPEVALUE)) {
                                temp.add(cartList.get(i1));
                            }
                        }


                        //如果里面全部是true
                        for (int i = 0; i < temp.size(); i++) {
                            if (temp.get(i).isSelect){
                                singelselectflag = true;
                            }else {
                                singelselectflag = false;
                                break;
                            }
                        }

                        if (singelselectflag) {
                            viewHolder.rb_check.setChecked(true);
                        }else {
                            viewHolder.rb_check.setChecked(false);
                        }


                    } else {
                        viewHolder.rb_check.setChecked(false);
                        viewHolder.rb_check_item.setChecked(false);
                        cartList.get(position).isSelect = false;
                    }


                }
            });


        }


        @Override
        public int getItemCount() {
            return cartList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {


            CheckBox rb_check;
            CheckBox rb_check_item;
            ImageView cover;
            TextView name;
            TextView price;
            TextView sku;
            TextView code;
            TextView count;
            ImageView minus;
            ImageView add;
            ImageView dv_sold_out;
            LinearLayout ll0;
            TextView tv_delivery_type;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                rb_check = itemView.findViewById(R.id.rb_check);

                rb_check_item = (CheckBox) itemView.findViewById(R.id.rb_check_item);
                cover = (ImageView) itemView.findViewById(R.id.dv_cover);
                name = (TextView) itemView.findViewById(R.id.tv_goods_name);

                tv_delivery_type = (TextView) itemView.findViewById(R.id.tv_delivery_type);

                price = (TextView) itemView.findViewById(R.id.tv_goods_price);
                sku = (TextView) itemView.findViewById(R.id.tv_sku);
                code = (TextView) itemView.findViewById(R.id.tv_code);
                minus = (ImageView) itemView.findViewById(R.id.iv_minus);
                count = (TextView) itemView.findViewById(R.id.tv_count);
                add = (ImageView) itemView.findViewById(R.id.iv_add);
                dv_sold_out = (ImageView) itemView.findViewById(R.id.dv_sold_out);
                ll0 = itemView.findViewById(R.id.ll0);




            }
        }

    }


}
