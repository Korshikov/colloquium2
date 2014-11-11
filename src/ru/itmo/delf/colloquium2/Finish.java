package ru.itmo.delf.colloquium2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

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


/**
 * Created by delf on 11.11.14.
 */
/**
 * Created by delf on 11.11.14.
 */
public class Finish extends Activity{

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
        String[] from = new String[]{DBAdapter.CANDIDATE_NAME,DBAdapter.CANDIDATE_VALUE};
        int[] to = new int[]{R.id.candidateRow2,R.id.candidateValue2};
        notes = new SimpleCursorAdapter(this, R.layout.finish_row, cursor, from, to);
        lvCandidates = (ListView) findViewById(R.id.list);
        lvCandidates.setAdapter(notes);
        lvCandidates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int candidateID = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.CANDIDATE_ID)));
                myDBAdapter.addVote(candidateID);
            }
        });
        (findViewById(R.id.add_button)).setVisibility(View.GONE);

        ((Button) findViewById(R.id.start_button)).setText("clear");
        (findViewById(R.id.start_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDBAdapter.clearCandidateTable();
                Intent intent = new Intent(view.getContext(), ModListActivity.class);
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
