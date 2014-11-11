package ru.itmo.delf.colloquium2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by delf on 11.11.14.
 */
public class CandidateDialog extends Activity {

    int candidateId;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifaide_list_dialog);

        final DBAdapter myDBAdapter= new DBAdapter(this);
        myDBAdapter.open();
        Intent intent = getIntent();
        candidateId = intent.getIntExtra("id", -1);
        String candidateName = intent.getStringExtra("name");
        Button button = (Button) findViewById(R.id.deleteButton);
        if(candidateId == -1)
        {
            button.setText("Cancel");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_OK, new Intent());
                    finish();
                }
            });
        }
        else
        {
            ((TextView) findViewById(R.id.candidateName)).setText(candidateName);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDBAdapter.deleteCandidate(candidateId);
                    setResult(RESULT_OK, new Intent());
                    finish();
                }
            });
        }


        (findViewById(R.id.addButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) findViewById(R.id.candidateName)).getText().toString().trim();
                if ("".equals(name)) {
                    Toast.makeText(view.getContext(), "name invalid", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("name", name);
                if(candidateId!=-1)
                {
                    myDBAdapter.editCandidate(candidateId,name);
                }else
                {
                    myDBAdapter.addCandidate(name);
                }
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

}
