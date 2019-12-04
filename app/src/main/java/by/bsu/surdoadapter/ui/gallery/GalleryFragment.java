package by.bsu.surdoadapter.ui.gallery;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import by.bsu.surdoadapter.MainActivity;
import by.bsu.surdoadapter.R;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        listView = root.findViewById(R.id.listGallery);

        SQLiteDatabase sqLiteDatabase = ((MainActivity)this.getActivity()).mDb;
        String query = "SELECT path FROM lib;";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        ArrayList<String> lib = new ArrayList<>();

        int i = 0;
        while(!cursor.isAfterLast()){
            lib.add(cursor.getString(i));
            i++;
        }

        listView.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, lib));

        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}