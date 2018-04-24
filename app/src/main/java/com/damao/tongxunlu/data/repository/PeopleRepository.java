package com.damao.tongxunlu.data.repository;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;

import com.damao.tongxunlu.BaseApplication;
import com.damao.tongxunlu.entity.BaseEntity;
import com.damao.tongxunlu.entity.FriendEntity;
import com.damao.tongxunlu.entity.PeopleResultEntity;
import com.damao.tongxunlu.executors.RxSchedulersHelper;
import com.damao.tongxunlu.util.PinYinComparator;
import com.damao.tongxunlu.util.Pinyin4jUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Created by chenlong on 2018/3/8.
 * 联系人
 */

public class PeopleRepository {
    /**
     * 获取库Phone表字段
     **/
    private static final String[] PHONES_PROJECTION = new String[]{
            Phone.DISPLAY_NAME, Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, Phone.CONTACT_ID};
    /**
     * 联系人显示名称
     **/
    private static final int PHONES_DISPLAY_NAME = 0;

    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER = 1;

    /**
     * 头像ID
     **/
    private static final int PHONES_PHOTO_ID = 2;

    /**
     * 联系人的ID
     **/
    private static final int PHONES_CONTACT_ID = 3;

    /**
     * @return
     */
    public Flowable<PeopleResultEntity> getFriends() {
        return getData()
                .compose(RxSchedulersHelper.io_main());
    }


    private Flowable<PeopleResultEntity> getData() {
        return Flowable.create(e -> {
//            e.onNext(queryContacts());
            e.onNext(testGetAllContact());
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 获取所有联系人
     *
     * @return
     */
    private PeopleResultEntity queryContacts() {
        ContentResolver cr = BaseApplication.getAppContext().getContentResolver();
        Cursor contactCursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID}, null, null,
                ContactsContract.Contacts.SORT_KEY_PRIMARY);//在raw_contacts表中得到contactId
        ArrayList<FriendEntity> friendEntities = new ArrayList<FriendEntity>();
        if (contactCursor != null && contactCursor.getCount() > 0)
            while (contactCursor.moveToNext()) {
                long contactId = contactCursor.getLong(contactCursor
                        .getColumnIndex(ContactsContract.Contacts._ID));
                Cursor dataCursor = cr.query(ContactsContract.Data.CONTENT_URI,
                        new String[]{ContactsContract.Data.MIMETYPE,
                                ContactsContract.Data.DATA1,
                                ContactsContract.Data.DATA2,
                                ContactsContract.Data.DATA15},
                        ContactsContract.Data.RAW_CONTACT_ID + "=" + contactId,
                        null, null);
                FriendEntity entity = new FriendEntity();
                if (dataCursor != null && dataCursor.getCount() > 0) {
                    while (dataCursor.moveToNext()) {
                        String mimeType = dataCursor
                                .getString(dataCursor
                                        .getColumnIndex(ContactsContract.Data.MIMETYPE));
                        if (ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                                .equals(mimeType)) {
                            //电话号码
                            String num = dataCursor
                                    .getString(dataCursor
                                            .getColumnIndex(ContactsContract.Data.DATA1));
                            entity.getPhoneNumbers().add(num);
                        } else if (ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                                .equals(mimeType)) {
                            //邮件
                            String email = dataCursor
                                    .getString(dataCursor
                                            .getColumnIndex(ContactsContract.Data.DATA1));
                            entity.setEmail(email);
                        } else if (ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE
                                .equals(mimeType)) {
                            //即时消息
                        } else if (ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                                .equals(mimeType)) {
                            //名字
                            String name = dataCursor
                                    .getString(dataCursor
                                            .getColumnIndex(ContactsContract.Data.DATA1));
                            entity.setNickName(name);
                            entity.setAccount(name);
                        } else if (ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                                .equals(mimeType)) {
                            //头像
                            byte[] data = dataCursor
                                    .getBlob(dataCursor
                                            .getColumnIndex(ContactsContract.Data.DATA15));
                            BitmapFactory.decodeByteArray(data, 0, data.length);
                            entity.setDataHeaderUrl(data);
                        } else if (ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE
                                .equals(mimeType)) {
                            //昵称
                        }
                    }

                    String pinyin = Pinyin4jUtil.convertToFirstSpell(entity.getNickName());
                    if (Pinyin4jUtil.isPinYin(pinyin)) {
                        entity.setPinyin(pinyin);
                    } else {
                        entity.setPinyin("#");
                    }

                    friendEntities.add(entity);
                }
                if (dataCursor != null) {
                    dataCursor.close();
                    dataCursor = null;
                }
            }

        if (contactCursor != null) {
            contactCursor.close();
            contactCursor = null;
        }

        if (friendEntities.size() > 1) {
            Collections.sort(friendEntities, new PinYinComparator());
        }

        PeopleResultEntity peopleResultEntity = new PeopleResultEntity();
        peopleResultEntity.setFriends(friendEntities);
        peopleResultEntity.setCode(BaseEntity.ENTITY_RESULT_SUCCESS);
        return peopleResultEntity;
    }

    private PeopleResultEntity testGetAllContact() {
        ArrayList<FriendEntity> valueList = new ArrayList<>();
        //获取联系人信息的Uri
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //获取ContentResolver
        ContentResolver contentResolver = BaseApplication.getAppContext().getContentResolver();
        //查询数据，返回Cursor
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                //获取联系人的ID
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //获取联系人的姓名
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                ArrayList<String> phoneNumbers = new ArrayList<>();
                //查询电话类型的数据操作
                Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null, null);
                if (phones != null) {
                    while (phones.moveToNext()) {
                        String phoneNumber = phones.getString(phones.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //添加Phone的信息
                        phoneNumbers.add(phoneNumber.trim());
                    }
                    phones.close();

                    // 去重
                    Set<String> set = new LinkedHashSet<>();
                    set.addAll(phoneNumbers);
                    phoneNumbers.clear();
                    phoneNumbers.addAll(set);
                }

                FriendEntity entity = new FriendEntity();

                String pinyin = Pinyin4jUtil.convertToFirstSpell(name);
                if (Pinyin4jUtil.isPinYin(pinyin)) {
                    entity.setPinyin(pinyin);
                } else {
                    entity.setPinyin("#");
                }
                entity.setAccount(name);
                entity.setNickName(name);
                entity.setPhoneNumbers(phoneNumbers);

                valueList.add(entity);
            }

            cursor.close();
        }

        if (valueList.size() > 1) {
            Collections.sort(valueList, new PinYinComparator());
        }

        PeopleResultEntity peopleResultEntity = new PeopleResultEntity();
        peopleResultEntity.setFriends(valueList);
        peopleResultEntity.setCode(BaseEntity.ENTITY_RESULT_SUCCESS);
        return peopleResultEntity;
    }


    /**
     * 得到手机SIM卡联系人人信息
     **/
    private void getSIMContacts(Map<String, FriendEntity> con) {
        ContentResolver resolver = BaseApplication.getAppContext().getContentResolver();
        // 获取Sims卡联系人
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,
                null);

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                // 得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER);
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                // 得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME);

                FriendEntity entity;
                if (!con.containsKey(contactName)) {
                    entity = new FriendEntity();
                    con.put(contactName, entity);
                } else
                    entity = con.get(contactName);

                String input = TextUtils.isEmpty(contactName) ? phoneNumber : contactName;
                String pinyin = Pinyin4jUtil.convertToFirstSpell(input);
                if (Pinyin4jUtil.isPinYin(pinyin)) {
                    entity.setPinyin(pinyin);
                } else {
                    entity.setPinyin("#");
                }
                entity.setAccount(input);
                // Sim卡中没有联系人头像
                entity.setNickName(contactName);
                entity.setSim(true);
                entity.getPhoneNumbers().add(phoneNumber);

                if (!con.containsKey(contactName)) {
                    con.put(contactName, entity);
                }

            }

            phoneCursor.close();
        }
    }

}
