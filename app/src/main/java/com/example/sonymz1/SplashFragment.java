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

import com.example.sonymz1.Database.DatabaseUserCallback;
import com.example.sonymz1.Database.OnlineDatabase;
import com.example.sonymz1.Model.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * The splash screen. Should be a loading screen.
 *
 * @author Wendy Pau
 */
public class SplashFragment extends Fragment {
    private ChallengeViewModel vm;
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    public SplashFragment() {
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
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vm = new ViewModelProvider(requireActivity()).get(ChallengeViewModel.class);
        CheckLogin();
    }

    /**
     * Check if main user already logged in before.
     */
    private void CheckLogin() {
        if (sp == null) {
            sp = getActivity().getSharedPreferences("myPreferences", MODE_PRIVATE);
        }
        //spEditor = sp.edit();
        //spEditor.clear();
        //spEditor.commit();
        Integer userID = sp.getInt("id", -1);

        // Uncomment if you want to go to login page again.
        //userID = -1;

        // if user already exists. Set user to main user.
        if (userID != -1) {
            vm.setMainUser(userID, user -> NavHostFragment.findNavController(SplashFragment.this)
                    .navigate(R.id.action_splashFragment_to_FirstFragment));

        }
        // if not, navigate to register page
        else
            NavHostFragment.findNavController(SplashFragment.this)
                .navigate(R.id.action_splashFragment_to_LoginFragment);
    }
}