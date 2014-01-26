package com.ribdum.battlepetsandroid;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.ribdum.battlepets.ability.Ability;
import com.ribdum.battlepets.ability.AbilitySlot;
import com.ribdum.battlepets.player.Player;

public class AbilitySelectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ability_selection);

		Player player = (Player) getIntent().getExtras().get("PLAYER");

		Map<AbilitySlot, Ability> abilities = player.getActivePet()
				.getAbilities();

		createButton(R.id.ability_1, AbilitySlot.FIRST_SLOT, abilities);
		createButton(R.id.ability_2, AbilitySlot.SECOND_SLOT, abilities);
		createButton(R.id.ability_3, AbilitySlot.THIRD_SLOT, abilities);

		Button switchPetButton = (Button) findViewById(R.id.switch_pet);
		switchPetButton.setOnClickListener(new PetSelector(
				AbilitySlot.SWITCH_PET));
	}

	private void createButton(int buttonId, AbilitySlot slot,
			Map<AbilitySlot, Ability> abilities) {
		Button abilityButton = (Button) findViewById(buttonId);
		Ability ability = abilities.get(slot);
		if (ability != null) {
			if (!ability.isAvailable()) {
				abilityButton.setText("" + ability.getRemainingCooldown());
			} else {
				abilityButton.setText(ability.getName());
				abilityButton.setOnClickListener(new PetSelector(slot));
			}
		}
	}

	private class PetSelector implements View.OnClickListener {
		private AbilitySlot slot;

		public PetSelector(AbilitySlot slot) {
			this.slot = slot;
		}

		public void onClick(View view) {
			Intent intent = new Intent();
			intent.putExtra("ABILITY", slot);
			setResult(MainActivity.ABILITY_SELECTION, intent);
			finish();
		}
	}

	// private void createButton(int i, final Ability ability) {
	// RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
	// RelativeLayout.LayoutParams.WRAP_CONTENT,
	// RelativeLayout.LayoutParams.WRAP_CONTENT);
	// if (i > START_ID) {
	// params.addRule(RelativeLayout.ALIGN_RIGHT, i);
	// }
	// Button btn0 = new Button(this);
	// btn0.setId(i);
	// btn0.setText(ability.getName());
	// ((RelativeLayout) findViewById(R.id.pet_select)).addView(btn0, params);
	// btn0.setOnClickListener(new View.OnClickListener() {
	// public void onClick(View view) {
	// Toast.makeText(view.getContext(), ability.getName(),
	// Toast.LENGTH_SHORT).show();
	// }
	// });
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ability_selection, menu);
		return true;
	}

}
