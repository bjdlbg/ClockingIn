package com.example.a84640.clockingin.fragment;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a84640.clockingin.NfcActivity;
import com.example.a84640.clockingin.R;

/**
 * @author jixiang
 * @date 2019/3/3
 */
public class ToolsFragment extends Fragment {

    private NfcActivity mNfcActivity;
    private View rootView;
    private TextView mTextView;

    /**
     * 当fragment与activity建立联系时执行此函数
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNfcActivity = (NfcActivity) context;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_tools,container,false);
        //初始化控件
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mTextView=(TextView)rootView.findViewById(R.id.my_tools_text);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("tools", "tools fragment is open");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView(rootView);
    }



}
