package com.djaphar.fragmentlab;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ContactsFragment extends Fragment {

    MainActivity mainActivity;
    ListView listViewContacts;
    HashMap<String, String> nameNumberHashMap = new HashMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        mainActivity = (MainActivity) getActivity();
        listViewContacts = rootView.findViewById(R.id.list_view_contacts);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Cursor cursor = mainActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                 new String[] {ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                         ContactsContract.CommonDataKinds.Phone.NUMBER}, null , null, null);
        mainActivity.startManagingCursor(cursor); // TODO Желательно переделать через CursorLoader

        if (Objects.requireNonNull(cursor).getCount() > 0) {
            while (cursor.moveToNext()) {
                nameNumberHashMap.put(cursor.getString(1), cursor.getString(2));
            }

            List<HashMap<String, String>> hashMapList = new ArrayList<>();
            SimpleAdapter adapter = new SimpleAdapter(this.getContext(), hashMapList, R.layout.contacts_list_view_pattern,
                    new String[]{"Name Line", "Number Line"}, new int[]{R.id.name, R.id.number});

            for (Object o : nameNumberHashMap.entrySet()) {
                HashMap<String, String> resultHashMap = new HashMap<>();
                Map.Entry pair = (Map.Entry) o;
                resultHashMap.put("Name Line", pair.getKey().toString());
                resultHashMap.put("Number Line", pair.getValue().toString());
                hashMapList.add(resultHashMap);
            }

            listViewContacts.setAdapter(adapter);
        }
    }
}