package com.damao.tongxunlu.module.add;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.damao.tongxunlu.BaseActivity;
import com.damao.tongxunlu.R;
import com.damao.tongxunlu.entity.CallLogNeedAllBean;
import com.damao.tongxunlu.entity.EditPhoneEntity;
import com.damao.tongxunlu.entity.FriendEntity;
import com.damao.tongxunlu.util.Util;
import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenlong on 2018/3/20.
 * 添加联系人
 */

public class AddPeopleActivity extends BaseActivity {
    public static final String PHONE_KEY = "phone_key";
    public static final String FRIEND_KEY = "friend_key";
    public static final String EDIT_KEY = "edit_key";
    @BindView(R.id.iv_more_txt)
    TextView ivMoreTxt;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.voice)
    ImageButton voice;
    @BindView(R.id.phone_panel)
    RelativeLayout phonePanel;
    @BindView(R.id.add_panel)
    LinearLayout add_panel;

    private ArrayList<View> phonePanels = new ArrayList<>();
    private FriendEntity friendEntity = null;
    private CallLogNeedAllBean callLogNeedAllBean = null;

    /**
     * 修改前的用户名
     */
    private String mName;
    /**
     * 0: 新建联系人， 1: 保存到已有联系人， 2： 编辑联系人
     */
    private int module = 0;

    /**
     * 已有的手机号
     */
    private ArrayList<EditPhoneEntity> editPhoneEntities = new ArrayList<>();
    /**
     * 待删除的手机号
     */
    private ArrayList<EditPhoneEntity> deletePhoneEntities = new ArrayList<>();
    private HashMap<View, EditPhoneEntity> datas = new HashMap<>();
    private String phoneTxt;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_people;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ImageView imageView = phonePanel.findViewById(R.id.delete);
        imageView.setVisibility(View.GONE);
        imageView.setOnClickListener(v -> {
            deletePhonePanel(phonePanel);
        });
        phonePanels.add(phonePanel);

        callLogNeedAllBean = (CallLogNeedAllBean) getIntent().getSerializableExtra(EDIT_KEY);
        friendEntity = (FriendEntity) getIntent().getSerializableExtra(FRIEND_KEY);
        phoneTxt = getIntent().getStringExtra(PHONE_KEY);
        if (null != friendEntity) {
            mName = friendEntity.getNickName();
            module = 1;
            addToOld();
        } else if (null != callLogNeedAllBean) {
            mName = callLogNeedAllBean.getRemarkName();
            module = 2;
            edit();
        } else if (!TextUtils.isEmpty(phoneTxt)) {
            module = 0;
            addToNew();
        }

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5a94b84c");
    }

    /**
     * 将手机号添加到新建联系人
     */
    private void addToNew() {
        EditText phoneEt = phonePanel.findViewById(R.id.phone_num);
        phoneEt.setText(phoneTxt);
        addPhonePanel();
    }

    /**
     * 将手机号添加到已有联系人
     */
    private void addToOld() {
        getAllPhone();
        tvTitle.setText(R.string.save_people);
        name.setText(friendEntity.getNickName());

        EditPhoneEntity editPhoneEntity = new EditPhoneEntity();
        editPhoneEntity.setPhone(phoneTxt);
        editPhoneEntities.add(editPhoneEntity);

        for (int i = 0; i < editPhoneEntities.size(); i++) {
            if (i != 0) {
                addPhonePanel();
            }
            datas.put(phonePanels.get(i), editPhoneEntities.get(i));
            View view = phonePanels.get(i);
            EditText phoneEt = view.findViewById(R.id.phone_num);
            phoneEt.setText(editPhoneEntities.get(i).getPhone());
        }
    }

    /**
     * 编辑联系人
     */
    private void edit() {
        getAllPhone();
        tvTitle.setText(R.string.edit_people);
        name.setText(callLogNeedAllBean.getRemarkName());
        if (editPhoneEntities.size() <= 0)
            return;
        for (int i = 0; i < editPhoneEntities.size(); i++) {
            if (i != 0) {
                addPhonePanel();
            }
            datas.put(phonePanels.get(i), editPhoneEntities.get(i));
            View view = phonePanels.get(i);
            EditText phoneEt = view.findViewById(R.id.phone_num);
            phoneEt.setText(editPhoneEntities.get(i).getPhone());
        }
    }

    /**
     * 添加号码
     */
    private View addPhonePanel() {
        if (phonePanels.size() == 1) {
            View view = phonePanels.get(0);
            ImageView imageView = view.findViewById(R.id.delete);
            imageView.setVisibility(View.VISIBLE);
        }
        View phonePanelTemp = LayoutInflater.from(this).inflate(R.layout.layout_phone_panel, null);
        ImageView imageView = phonePanelTemp.findViewById(R.id.delete);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(v -> {
            deletePhonePanel(phonePanelTemp);
        });
        phonePanels.add(phonePanelTemp);
        add_panel.addView(phonePanelTemp);
        return phonePanelTemp;
    }

    /**
     * 删除
     */
    private void deletePhonePanel(View phonePanelTemp) {
        add_panel.removeView(phonePanelTemp);
        phonePanels.remove(phonePanelTemp);

        EditPhoneEntity editPhoneEntity = datas.get(phonePanelTemp);
        if (null != editPhoneEntity && !TextUtils.isEmpty(editPhoneEntity.getPhone_id()))
            deletePhoneEntities.add(editPhoneEntity);
        datas.remove(phonePanelTemp);

        if (phonePanels.size() == 1) {
            View view = phonePanels.get(0);
            ImageView imageView = view.findViewById(R.id.delete);
            imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void initToolBar() {
        ivMoreTxt.setVisibility(View.VISIBLE);
        ivMoreTxt.setText(R.string.ok);
        tvTitle.setText(R.string.add_new_title);
    }

    class MyInitListener implements InitListener {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                toast("初始化失败 ");
            }

        }
    }

    /**
     * 初始化语音识别
     */
    public void initSpeech(final Context context) {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(context, new MyInitListener());
        //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 若要将UI控件用于语义理解，必须添加以下参数设置，设置之后 onResult回调返回将是语义理解
        // 结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (!isLast) {
                    //解析语音
                    String result = parseVoice(recognizerResult.getResultString());
                    name.setText(result);
                }
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        //4.显示dialog，接收语音输入
        mDialog.show();

        //获取字体所在的控件，设置为"",隐藏字体，
        TextView txt = (TextView) mDialog.getWindow().getDecorView().findViewWithTag("textlink");
        txt.setText("");
    }

    /**
     * 解析语音json
     */
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class);

        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }

    /**
     * 语音对象封装
     */
    public class Voice {

        public ArrayList<WSBean> ws;

        public class WSBean {
            public ArrayList<CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
    }

    @OnClick({R.id.iv_back_click, R.id.iv_more_txt, R.id.voice, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_click:
                finish();
                break;
            case R.id.add:
                // 添加号码
                if (phonePanels.size() > 5)
                    break;
                datas.put(addPhonePanel(), new EditPhoneEntity());
                break;
            case R.id.iv_more_txt:
                // 确定
                if (module == 0)
                    addPeople();
                else
                    editPeople();
                finish();
                break;
            case R.id.voice:
                // 语音输入
                RxPermissions rxPermissions = new RxPermissions(AddPeopleActivity.this);
                rxPermissions
                        .request(Manifest.permission.RECORD_AUDIO)
                        .subscribe(granted -> {
                            if (granted) {
                                initSpeech(this);
                            } else {
                                // Oups permission denied
                                Util.showToast(getString(R.string.audio_not_granted));
                            }
                        });

                break;
        }
    }

    /**
     * 添加
     */
    private void addPeople() {
        View view = phonePanels.get(0);
        String nameStr = name.getText().toString();
        EditText phoneEt = view.findViewById(R.id.phone_num);
        String phoneStr = phoneEt.getText().toString();

        if (TextUtils.isEmpty(nameStr)) {
            toast(getString(R.string.add_new_error1));
            return;
        }
        if (TextUtils.isEmpty(phoneStr)) {
            toast(getString(R.string.add_new_error2));
            return;
        }

        //创建一个空的ContentValues
        ContentValues values = new ContentValues();
        //首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        //往data表插入姓名数据
        values.clear();
        values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);//内容类型
        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, nameStr);//设置联系人名字
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);//向联系人URI添加联系人名字
        //往data表插入电话数据
        for (int i = 0; i < phonePanels.size(); i++) {
            values.clear();
            values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);

            EditText phoneEtTemp = phonePanels.get(i).findViewById(R.id.phone_num);
            String phoneStrTemp = phoneEtTemp.getText().toString();
            if (TextUtils.isEmpty(phoneStrTemp)) {
                continue;
            }
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneStrTemp);
            if (i == 0) {
                values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);//插入手机号码
            } else {
                values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_OTHER);//插入手机号码
            }
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
        }
    }

    /**
     * 保存到已有联系人
     */
    private void addToOldPeople() {

    }

    /**
     * 编辑
     */
    private void editPeople() {
        View view = phonePanels.get(0);
        String nameStr = name.getText().toString();
        EditText phoneEt = view.findViewById(R.id.phone_num);
        String phoneStr = phoneEt.getText().toString();

        if (TextUtils.isEmpty(nameStr)) {
            toast(getString(R.string.add_new_error1));
            return;
        }
        if (TextUtils.isEmpty(phoneStr)) {
            toast(getString(R.string.add_new_error2));
            return;
        }

        Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.Data.RAW_CONTACT_ID},
                ContactsContract.Contacts.DISPLAY_NAME + "=?", new String[]{mName}, null);
        if (null != cursor) {
            cursor.moveToFirst();
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID));
            cursor.close();

            ArrayList<ContentProviderOperation> ops = new ArrayList<>();
            ContentProviderOperation op1 = ContentProviderOperation
                    .newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(
                            ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                    + ContactsContract.Data.MIMETYPE + "=?",
                            new String[]{
                                    contactId,
                                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE})
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,// 对应data表中的data1字段
                            nameStr).build();
            ops.add(op1);

            for (Map.Entry<View, EditPhoneEntity> entry : datas.entrySet()) {
                View viewTemp = entry.getKey();
                EditPhoneEntity editPhoneEntity = entry.getValue();
                EditText phoneTemp = viewTemp.findViewById(R.id.phone_num);
                String phoneNum = phoneTemp.getText().toString();
                if (TextUtils.isEmpty(editPhoneEntity.getPhone_id())) {
                    // 添加新手机号
                    ContentProviderOperation op2 = ContentProviderOperation
                            .newInsert(ContactsContract.Data.CONTENT_URI)
//                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, Integer.valueOf(contactId))
                            .withValue(
                                    ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.Data.RAW_CONTACT_ID, contactId)
                            .withValue(ContactsContract.Data.DATA1, phoneNum)
                            .withValue(ContactsContract.Data.DATA2, "2")// data2=2即type=2，表示移动电话
                            .build();
                    ops.add(op2);
                } else {
                    // 更新旧手机号
                    ContentProviderOperation op2 = ContentProviderOperation
                            .newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(
                                    ContactsContract.Data.RAW_CONTACT_ID + "=? and "
                                            + ContactsContract.Data.MIMETYPE + "=? and "
                                            + ContactsContract.Data._ID + "=?",
                                    new String[]{contactId,
                                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                                            editPhoneEntity.getPhone_id()
                                    })
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,// 对应data表中的data1字段
                                    phoneNum).build();
                    ops.add(op2);
                }
            }

            for (int i = 0; i < deletePhoneEntities.size(); i++) {
                ops.add(ContentProviderOperation
                        .newDelete(ContactsContract.Data.CONTENT_URI)
                        .withSelection(
                                ContactsContract.Data._ID + "=? and "
                                        + ContactsContract.Data.RAW_CONTACT_ID + "=?",
                                new String[]{deletePhoneEntities.get(i).getPhone_id(),
                                        contactId})
                        .build());
            }

            try {
                getContentResolver().applyBatch(
                        ContactsContract.AUTHORITY, ops);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取该用户的所有手机号
     *
     * @return
     */
    private void getAllPhone() {
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
//取得电话本中开始一项的光标
// Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//根据传入的姓名查询通讯录，这个姓名就是上一个activity传过来的参数
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                ContactsContract.PhoneLookup.DISPLAY_NAME + "=?",
                new String[]{mName}, null);
        if (null != cursor && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                //获取联系人的ID
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //查询电话类型的数据操作
                Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null, null);
                if (phones != null) {
                    while (phones.moveToNext()) {
                        EditPhoneEntity editPhoneEntity = new EditPhoneEntity();
                        String phoneNumber = phones.getString(phones.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String id = phones.getString(phones.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone._ID));
                        //添加Phone的信息
                        editPhoneEntity.setPhone(phoneNumber.trim());
                        editPhoneEntity.setPhone_id(id);
                        editPhoneEntities.add(editPhoneEntity);
                    }
                    phones.close();
                }
            }
            cursor.close();
        }
    }
}
