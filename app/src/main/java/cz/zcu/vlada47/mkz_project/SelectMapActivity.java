package cz.zcu.vlada47.mkz_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cz.zcu.vlada47.mkz_project.static_data.MapsInfo;

/**
 *
 */
public class SelectMapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_map);

        MapsInfo[] maps = MapsInfo.values();
        final ArrayAdapter<MapsInfo> mapListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, maps);
        ListView mapListView = (ListView) findViewById(R.id.mapList);
        mapListView.setAdapter(mapListAdapter);

        mapListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int mapId = mapListAdapter.getItem(position).getIndex();
                Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
                gameIntent.putExtra("mapId",mapId);
                startActivity(gameIntent);
                finish();
            }
        });
    }

}
