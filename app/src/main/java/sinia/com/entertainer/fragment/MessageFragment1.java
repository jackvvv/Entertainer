package sinia.com.entertainer.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMConversationListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import sinia.com.entertainer.chat.EaseConversationAdapter;
import sinia.com.entertainer.chat.EaseConversationList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import sinia.com.entertainer.Constant;
import sinia.com.entertainer.R;
import sinia.com.entertainer.activity.ContentActivity;
import sinia.com.entertainer.adapter.MsgAdapter;
import sinia.com.entertainer.base.BaseFragment;
import sinia.com.entertainer.chat.ChatActivity;

/**
 * 消息界面
 * Created by byw on 2016/11/29.
 */
public class MessageFragment1 extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private View headView;
    private TextView tv_list;

    private TextView tv_sysnum;
    private TextView tv_strnum;
    private RelativeLayout rl_system;
    private RelativeLayout rl_stranger;

    protected EaseConversationList conversationListView;
    protected List<EMConversation> conversationList = new ArrayList<EMConversation>();
    private List<String> ls;
    private MsgAdapter adapter;
    protected boolean isConflict;
    private final static int MSG_REFRESH = 2;
    protected boolean hidden;
    protected InputMethodManager inputMethodManager;

    protected EMConversationListener convListener = new EMConversationListener() {

        @Override
        public void onCoversationUpdate() {
            refresh();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_msg, null);
        headView = LayoutInflater.from(getActivity()).inflate(
                R.layout.head_view, null);
        initView();
        setUpView();
        return rootView;
    }

    private void setUpView() {
        conversationList.addAll(loadConversationList());
        EaseConversationAdapter.SwipeDeleteConversationListener listener = new EaseConversationAdapter
                .SwipeDeleteConversationListener() {

            @Override
            public void deleteConversation(int position) {

            }
        };
        conversationListView.init(conversationList);
        if (listItemClickListener != null) {
            conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EMConversation conversation = conversationListView.getItem(position);
                    listItemClickListener.onListItemClicked(conversation);
                }
            });
        }
        EMClient.getInstance().addConnectionListener(connectionListener);
        conversationListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });
    }

    private void initView() {
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        conversationListView = (EaseConversationList) rootView.findViewById(R.id.list);
        tv_list = (TextView) rootView.findViewById(R.id.tv_list);
        tv_list.setOnClickListener(this);
        conversationListView.addHeaderView(headView);
        tv_sysnum = (TextView) headView.findViewById(R.id.tv_sysnum);
        tv_strnum = (TextView) headView.findViewById(R.id.tv_strnum);
        rl_system = (RelativeLayout) headView.findViewById(R.id.rl_system);
        rl_stranger = (RelativeLayout) headView.findViewById(R.id.rl_stranger);
//        adapter = new MsgAdapter(getActivity(), ls);
//        mlistview.setAdapter(adapter);
//        tv_list.setOnClickListener(this);
//        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//
//                } else {
//                    EMConversation conversation = conversationListView
//                            .getItem(position - 1);
//                    String username = conversation.conversationId();
//                    if (username.equals(EMClient.getInstance().getCurrentUser()))
//                        Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
//                    else {
//                        Intent intent = new Intent(getActivity(),
//                                ChatActivity.class);
//                        intent.putExtra(Constant.EXTRA_USER_ID, username);
//                        startActivity(intent);
//                    }
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_list:
                intent = new Intent(getActivity(), ContentActivity.class);
                startActivity(intent);
                break;
        }
    }

    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError
                    .SERVER_SERVICE_RESTRICTED) {
                isConflict = true;
            } else {
                handler.sendEmptyMessage(0);
            }
        }

        @Override
        public void onConnected() {
            handler.sendEmptyMessage(1);
        }
    };
    private EaseConversationListFragment.EaseConversationListItemClickListener listItemClickListener;

    protected Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    onConnectionDisconnected();
                    break;
                case 1:
                    onConnectionConnected();
                    break;

                case MSG_REFRESH: {
                    conversationList.clear();
                    conversationList.addAll(loadConversationList());
                    conversationListView.refresh();
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * connected to server
     */
    protected void onConnectionConnected() {
//        errorItemContainer.setVisibility(View.GONE);
    }

    /**
     * disconnected with server
     */
    protected void onConnectionDisconnected() {
//        errorItemContainer.setVisibility(View.VISIBLE);
        showToast("当前账号在另一部设备登录");
    }


    /**
     * refresh ui
     */
    public void refresh() {
        if (!handler.hasMessages(MSG_REFRESH)) {
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }

    /**
     * load conversation list
     *
     * @return +
     */
    protected List<EMConversation> loadConversationList() {
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(),
                            conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    /**
     * sort conversations according time stamp of last message
     *
     * @param conversationList
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
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
    }

    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden && !isConflict) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().removeConnectionListener(connectionListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isConflict) {
            outState.putBoolean("isConflict", true);
        }
    }

    public interface EaseConversationListItemClickListener {
        /**
         * click event for conversation list
         *
         * @param conversation -- clicked item
         */
        void onListItemClicked(EMConversation conversation);
    }

    /**
     * set conversation list item click listener
     *
     * @param listItemClickListener
     */
    public void setConversationListItemClickListener(EaseConversationListFragment
                                                             .EaseConversationListItemClickListener
                                                             listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }
}
