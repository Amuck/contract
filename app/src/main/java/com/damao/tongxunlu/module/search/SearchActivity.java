package com.damao.tongxunlu.module.search;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.support.v7.widget.SearchView;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.damao.tongxunlu.BaseActivity;
import com.damao.tongxunlu.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenlong on 2018/3/22.
 * 搜索
 */

public class SearchActivity extends BaseActivity {
    static final String[] PROJECTION = new String[]{ContactsContract.RawContacts._ID, ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY};

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.search)
    SearchView mSearchView;
    @BindView(R.id.search_list)
    ListView searchList;

    private Cursor mCursor;
    private SimpleCursorAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initList();

        initSearchView();
    }

    private void initList() {
         // 得到联系人名单的指针
        mCursor = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, PROJECTION, null, null, null);
        // 通过传入mCursor，将联系人名字放入listView中。
        mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, mCursor,
                new String[]{ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY}, new int[]{android.R.id.text1}, 0);
        searchList.setAdapter(mAdapter);
        searchList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(searchList.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        searchList.setOnItemClickListener((parent, view1, position, id) -> {
            Object o = mAdapter.getItem(position);
            toast(o.toString());
        });
    }

    private void initSearchView() {
        mSearchView = (SearchView) findViewById(R.id.search);
        /**
         * 默认情况下, search widget是"iconified“的，只是用一个图标 来表示它(一个放大镜),
         * 当用户按下它的时候才显示search box . 你可以调用setIconifiedByDefault(false)让search
         * box默认都被显示。 你也可以调用setIconified()让它以iconified“的形式显示。
         */
        mSearchView.setIconifiedByDefault(true);
        /**
         * 默认情况下是没提交搜索的按钮，所以用户必须在键盘上按下"enter"键来提交搜索.你可以同过setSubmitButtonEnabled(
         * true)来添加一个提交按钮（"submit" button)
         * 设置true后，右边会出现一个箭头按钮。如果用户没有输入，就不会触发提交（submit）事件
         */
        mSearchView.setSubmitButtonEnabled(true);
        /**
         * 初始是否已经是展开的状态
         * 写上此句后searchView初始展开的，也就是是可以点击输入的状态，如果不写，那么就需要点击下放大镜，才能展开出现输入框
         */
        mSearchView.onActionViewExpanded();
        // 设置search view的背景色
        mSearchView.setBackgroundColor(0x22ff00ff);
        /**
         * 默认情况下, search widget是"iconified“的，只是用一个图标 来表示它(一个放大镜),
         * 当用户按下它的时候才显示search box . 你可以调用setIconifiedByDefault(false)让search
         * box默认都被显示。 你也可以调用setIconified()让它以iconified“的形式显示。
         */
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * 输入完成后，提交时触发的方法，一般情况是点击输入法中的搜索按钮才会触发。表示现在正式提交了
             * @param query
             * @return
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (mSearchView != null) {
                    // 得到输入管理对象
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        // 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
                        imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法                    }
                        mSearchView.clearFocus(); // 不获取焦点                }
                        return true;
                    }
                }
                return false;
            }

            /**
             * 在输入时触发的方法，当字符真正显示到searchView中才触发，像是拼音，在舒服法组词的时候不会触发
             * @param newText
             * @return
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                String selection = RawContacts.DISPLAY_NAME_PRIMARY + " LIKE '%" + newText + "%' " + " OR "
                        + RawContacts.SORT_KEY_PRIMARY + " LIKE '%" + newText + "%' ";
                // String[] selectionArg = { queryText };
                mCursor = getContentResolver().query(RawContacts.CONTENT_URI, PROJECTION, selection, null, null);
                mAdapter.swapCursor(mCursor); // 交换指针，展示新的数据
                return true;
            }
        });

    }

    @Override
    public void initToolBar() {
        tvTitle.setText(R.string.search);
    }

    @OnClick(R.id.iv_back_click)
    public void onViewClicked() {
        finish();
    }
}
