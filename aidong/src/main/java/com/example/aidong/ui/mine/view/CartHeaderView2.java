package com.example.aidong.ui.mine.view;

import android.content.Context;
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
import android.widget.ExpandableListView;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

/**
 * the header of cart view
 * Created by song on 2017/2/22.
 */
public class CartHeaderView2 extends RelativeLayout {
    private Context context;
    private CartPresentImpl cartPresent;
    private ExpandableListView expandablelistview;


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

        expandablelistview = headerView.findViewById(R.id.expandablelistview);
        loadData();


    }

    private void loadData() {
        CartModelImpl cartModel = new CartModelImpl();
        cartModel.getCart(new RefreshSubscriber<ShopData>(context) {
            @Override
            public void onNext(ShopData shopData) {
                if (shopData != null && shopData.getCart() != null && !shopData.getCart().isEmpty()) {

                    List<ShopBean> cartList = shopData.getCart();

                    expandablelistview.setAdapter(new CartHeaderView2Adapter(context, cartList));


                } else {
                    expandablelistview.setEmptyView(new TextView(context));
                }
            }
        });


    }


    public class CartHeaderView2Adapter extends BaseExpandableListAdapter {

        public Context context;
        public List<ShopBean> cartList;


        CheckBox check;
        ImageView cover;
        TextView name;
        TextView price;
        TextView sku;
        TextView code;
        TextView count;
        ImageView minus;
        ImageView add;
        ImageView dv_sold_out;


        public CartHeaderView2Adapter(Context context, List<ShopBean> cartList) {
            this.context = context;
            this.cartList = cartList;
        }

        @Override
        public int getGroupCount() {
            return cartList.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return cartList.get(i).getItem().size();
        }

        @Override
        public Object getGroup(int i) {
            return cartList.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return cartList.get(i).getItem().get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

            View viewGroup22 = View.inflate(context, R.layout.cartheaderview2adaptergroup, null);
            TextView tv_group = viewGroup22.findViewById(R.id.tv_group);
           CheckBox check_box = viewGroup22.findViewById(R.id.check_box);

           check_box.setText(cartList.get(i).getPickUp().getInfo().getName());
            tv_group.setText(cartList.get(i).getPickUp().getInfo().getName());
            return viewGroup22;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

            View view22 = View.inflate(context, R.layout.cartheaderview2adapterview, null);


            GoodsBean bean = cartList.get(i).getItem().get(i1);

            check = (CheckBox) view22.findViewById(R.id.rb_check);
            cover = (ImageView) view22.findViewById(R.id.dv_cover);
            name = (TextView) view22.findViewById(R.id.tv_goods_name);
            price = (TextView) view22.findViewById(R.id.tv_goods_price);
            sku = (TextView) view22.findViewById(R.id.tv_sku);
            code = (TextView) view22.findViewById(R.id.tv_code);
            minus = (ImageView) view22.findViewById(R.id.iv_minus);
            count = (TextView) view22.findViewById(R.id.tv_count);
            add = (ImageView) view22.findViewById(R.id.iv_add);
            dv_sold_out = (ImageView) view22.findViewById(R.id.dv_sold_out);


            GlideLoader.getInstance().displayImage(bean.getCover(), cover);


            add.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                   // cartList.get(i).getItem()

                    notifyDataSetChanged();
                }
            });



            return view22;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }

}
