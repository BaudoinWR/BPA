package com.ribdum.battlepetsandroid;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.ribdum.battlepets.Game;
import com.ribdum.battlepets.ability.AbilitySlot;
import com.ribdum.battlepets.gameinterface.HumanPlayerInterface;
import com.ribdum.battlepets.pet.Pet;
import com.ribdum.battlepets.pet.PetSlot;
import com.ribdum.battlepets.player.HumanPlayer;

public class MainActivity extends Activity implements HumanPlayerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8884364270187371303L;
	public static final int PET_SELECTION = 1;
	public static final int ABILITY_SELECTION = 2;
	private Game game;
	private boolean ready = false;
	private Pet pet;
	private AbilitySlot abilitySlot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void startGame(View view) {
		game = new Game(this);
		new Thread(game).start();
	}

	@Override
	public Map<PetSlot, Pet> selectPetsScreen(HumanPlayer player) {
		Intent intent = new Intent(this, PetSelectionActivity.class);
		intent.putExtra("PLAYER", player);
		startActivityWaitForResult(intent, PET_SELECTION);
		HashMap<PetSlot, Pet> hashMap = new HashMap<PetSlot, Pet>();
		hashMap.put(PetSlot.FIRST_SLOT, pet);
		return hashMap;
	}

	private void startActivityWaitForResult(Intent intent, int expectedResult) {
		this.ready = false;
		startActivityForResult(intent, expectedResult);
		while (!this.ready) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public AbilitySlot selectAbilityScreen(HumanPlayer player) {
		Intent intent = new Intent(this, AbilitySelectionActivity.class);
		intent.putExtra("PLAYER", player);

		startActivityWaitForResult(intent, ABILITY_SELECTION);
		return abilitySlot;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case PET_SELECTION:
			pet = (Pet) data.getExtras().get("PET");
		case ABILITY_SELECTION:
			abilitySlot = (AbilitySlot) data.getExtras().get("ABILITY");
		}
		this.ready = true;
	}

}
