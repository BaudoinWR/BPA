package com.ribdum.battlepetsandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.ribdum.battlepets.pet.Wolgar;
import com.ribdum.battlepets.player.Player;

public class PetSelectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet_selection);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pet_selection, menu);
		return true;
	}

	public void validate(View view) {
		Player player = (Player) getIntent().getExtras().get("PLAYER");
		Intent intent = new Intent();
		intent.putExtra("PET", new Wolgar(player));
		setResult(1, intent);
		finish();
	}
}
