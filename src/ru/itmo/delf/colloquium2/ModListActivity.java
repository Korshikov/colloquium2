package ru.itmo.delf.colloquium2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
/**
 * Created by delf on 11.11.14.
 */
public class ModListActivity extends Activity   {

    final DBAdapter myDBAdapter = new DBAdapter(this);

    ListView lvCandidates;
    SimpleCursorAdapter notes;
    Cursor cursor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifaide_list);
        myDBAdapter.open();
        final Cursor cursor = myDBAdapter.fetchAllCandidates();
        this.cursor = cursor;
        startManagingCursor(cursor);
        String[] from = new String[]{DBAdapter.CANDIDATE_NAME};
        int[] to = new int[]{R.id.candidateRow};
        notes = new SimpleCursorAdapter(this, R.layout.modifaide_list_element, cursor, from, to);
        lvCandidates = (ListView) findViewById(R.id.list);
        lvCandidates.setAdapter(notes);
        /*lvCandidates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), MyActivity.class);
                cursor.moveToPosition(position);
                String rssURL = cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_C_URL));
                String rssTitle = cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_C_TITLE));
                String rssTime = cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_C_TIME));
                int rssID = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_ROW_ID)));
                intent.putExtra("rssURL", rssURL);
                intent.putExtra("rssTitle", rssTitle);
                intent.putExtra("rssTime", rssTime);
                intent.putExtra("rssID", "" + rssID);
                startActivity(intent);
            }
        });*/
        lvCandidates.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int candidateID = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.CANDIDATE_ID)));
                Intent intent = new Intent(view.getContext(), CandidateDialog.class);
                intent.putExtra("id", candidateID);
                intent.putExtra("name", cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.CANDIDATE_NAME)));
                startActivityForResult(intent, 200);
                cursor.requery();
                notes.notifyDataSetChanged();
                lvCandidates.invalidateViews();
                return true;
            }
        });

        (findViewById(R.id.add_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CandidateDialog.class);
                intent.putExtra("id", -1);
                startActivityForResult(intent, 200);
                cursor.requery();
                notes.notifyDataSetChanged();
                lvCandidates.invalidateViews();
            }

            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (data == null) {return;}
                cursor.requery();
                notes.notifyDataSetChanged();
                lvCandidates.invalidateViews();
            }

        });

        (findViewById(R.id.start_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Vote.class);
                startActivity(intent);
            }

        });




    }




    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {}
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
