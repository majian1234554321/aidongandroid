package com.example.aidong.ui.discover.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;

/**
 * Created by user on 2018/1/19.
 */
public class SearchHeaderView extends RelativeLayout {

    private OnSearchListener listener;

    public SearchHeaderView(Context context) {
        this(context, null, 0);
    }

    public SearchHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private EditText etSearch;
    private TextView txtSearchTitle;

    private void initView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_search, this, true);
        etSearch = (EditText) view.findViewById(R.id.et_search);
        txtSearchTitle = (TextView) view.findViewById(R.id.txt_search_title);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                    if (listener != null) {
                        listener.onSearch(etSearch.getText().toString().trim());
                    }
                    return true;
                }

                return false;
            }
        });

    }


    public void setOnsearchListner(OnSearchListener listener) {
        this.listener = listener;
    }

    public void setSearchHint(String hint) {
        if (etSearch != null) {
            etSearch.setHint(hint);
        }
    }

    public void setTxtSearchTitle(String title) {
        txtSearchTitle.setText(title);
    }




    public void setTxtSearchTitleVisible(int gone) {
        txtSearchTitle.setVisibility(gone);
    }


    public interface OnSearchListener {
        void onSearch(String keyword);
    }
}
