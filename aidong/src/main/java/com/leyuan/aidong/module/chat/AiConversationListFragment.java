package com.leyuan.aidong.module.chat;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.leyuan.aidong.module.chat.manager.EmMessageManager;
import com.leyuan.aidong.ui.mine.activity.EMChatActivity;
import com.leyuan.aidong.ui.mine.activity.SystemMessageActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.widget.dialog.BaseDialog;
import com.leyuan.aidong.widget.dialog.ButtonCancelListener;
import com.leyuan.aidong.widget.dialog.ButtonOkListener;
import com.leyuan.aidong.widget.dialog.DialogDoubleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/2/28.
 */

public class AiConversationListFragment extends EaseConversationListFragment {


    @Override
    public void setUpView() {
        super.setUpView();
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userId = conversationListView.getItem(position).conversationId();
                if (TextUtils.equals(userId, Constant.Chat.SYSYTEM_ID)) {
                    UiManager.activityJump(getActivity(), SystemMessageActivity.class);
                } else {
                    startActivity(new Intent(getActivity(), EMChatActivity.class).
                            putExtra(EaseConstant.EXTRA_USER_ID, userId));
                }


            }
        });


        conversationListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String userId = conversationListView.getItem(position).conversationId();
                new DialogDoubleButton(getActivity())
                        .setContentDesc("是否删除该消息")
                        .setBtnOkListener(new ButtonOkListener() {
                            @Override
                            public void onClick(BaseDialog dialog) {
                                EmMessageManager.removeMessage(userId);
                                dialog.dismiss();
                                refresh();
                            }
                        })
                        .setBtnCancelListener(new ButtonCancelListener() {
                            @Override
                            public void onClick(BaseDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show();


                return true;
            }
        });

    }

    public EaseConversationList getConversationListView() {
        return conversationListView;
    }

    @Override
    protected List<EMConversation> loadConversationList() {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0 && !TextUtils.equals(conversation.conversationId(), Constant.Chat.SYSYTEM_ID)) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            Collections.sort(sortList, new Comparator<Pair<Long, EMConversation>>() {
                @Override
                public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                    if (con1.first.equals(con2.first)) {
                        return 0;
                    } else if (con2.first.longValue() > con1.first.longValue()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }

        EMConversation systemConversation = EMClient.getInstance().chatManager().getAllConversations().get(Constant.Chat.SYSYTEM_ID);
        if (systemConversation != null) {
            list.add(0, systemConversation);
        }
        return list;
    }
}
