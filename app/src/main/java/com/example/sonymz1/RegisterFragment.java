package com.example.sonymz1;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sonymz1.Database.Database;
import com.example.sonymz1.Database.DatabaseCallback;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/**
 * On first time use, the user will have to create an account for further use.
 *
 * @author Wendy Pau
 */
public class RegisterFragment extends Fragment {
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    private TextInputLayout inputLayout;
    private TextInputEditText nameEditText;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ChallengeViewModel vm = new ViewModelProvider(requireActivity()).get(ChallengeViewModel.class);
        inputLayout = view.findViewById(R.id.textInputLayout);
        nameEditText = view.findViewById(R.id.nameEdtTxt);

        sp = requireContext().getSharedPreferences("myPreferences", MODE_PRIVATE);

        view.findViewById(R.id.nextBtn).setOnClickListener(v -> {
            // check if user has written their name
            if (!Objects.requireNonNull(nameEditText.getText()).toString().equals("")){

                if (sp == null){
                    sp = requireContext().getSharedPreferences("myPreferences",MODE_PRIVATE);
                }

                // save main user id in SharedPreferences
                spEditor = sp.edit();
                vm.newMainUser(nameEditText.getText().toString(), () -> {
                    spEditor.putInt("id", Database.getInstance().getMainUser().getId());
                    spEditor.apply();

                    NavHostFragment.findNavController(RegisterFragment.this)
                            .navigate(R.id.action_loginFragment_to_FirstFragment);

                });
                view.clearFocus();

            }else inputLayout.setError("Fill in your name!");
        });
    }
}