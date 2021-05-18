package com.example.sonymz1;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sonymz1.Adapters.ChallengeAdapter;
import com.example.sonymz1.Adapters.MainRecyclerAdapter;
import com.example.sonymz1.Database.Database;
import com.example.sonymz1.Model.Challenge;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Jesper
 * Fragment for the mainpage.
 */
public class FirstFragment extends Fragment {
    private ArrayList<Challenge> activeChallengeList = new ArrayList<>();
    private ArrayList<Challenge> finishedChallengeList = new ArrayList<>();
    private List<Section> sectionList = new ArrayList<>();
    private TextView  welcomeTxt;
    private ImageView medal;
    private RecyclerView recyclerView;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private ChallengeViewModel vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(getActivity()).get(ChallengeViewModel.class);
        initiateView(view);
        createChallengeList();

        view.findViewById(R.id.addChallengeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        NavHostFragment.findNavController(FirstFragment.this)
                                .navigate(R.id.action_FirstFragment_to_createChallengeFragment);
            }
        });
    }

    /**
     * Method to populate challenge lists. Separate lists for active and finished challenges.
     */
    private void createChallengeList() {
        Database.getInstance().getChallenges(vm.getMainUser().getId(), () -> {
            finishedChallengeList = new ArrayList<>();
            activeChallengeList = new ArrayList<>();
            welcomeTxt.setText("Welcome " + vm.getMainUser().getUsername());
            for (Challenge challenge:Database.getInstance().getChallenges()) {
                if(challenge.isFinished()){
                    finishedChallengeList.add(challenge);
                }else{
                    activeChallengeList.add(challenge);
                }

            }
            buildRecyclerView();
        });

    }

    /**
     * method to setup recyclerview that contains sections of active and finished challenges.
     */
    private void buildRecyclerView() {
        sectionList = new ArrayList<>();
        String sectionOneName = "Active";
        String sectionTwoName = "Finished";
        sectionList.add(new Section(sectionOneName,activeChallengeList));
        sectionList.add(new Section(sectionTwoName,finishedChallengeList));
        mainRecyclerAdapter = new MainRecyclerAdapter(sectionList, FirstFragment.this);
        mainRecyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mainRecyclerAdapter);
        recyclerView.setHasFixedSize(true);

    }

    /**
     * method to initiate views.
     *
     * @param view
     */

    private void initiateView(View view) {
        welcomeTxt = view.findViewById(R.id.welcomeText);
        recyclerView = view.findViewById(R.id.rvc_list);
        medal = view.findViewById(R.id.medal);
    }
}