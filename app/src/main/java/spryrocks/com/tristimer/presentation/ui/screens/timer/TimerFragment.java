package spryrocks.com.tristimer.presentation.ui.screens.timer;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import spryrocks.com.tristimer.R;
import spryrocks.com.tristimer.data.entities.Discipline;
import spryrocks.com.tristimer.databinding.TimerFragmentBinding;

public class TimerFragment extends Fragment {
    private TimerViewModel viewModel;
    private Spinner spinner;
    List<Discipline> disciplines;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(TimerViewModel.class);
        viewModel.activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TimerFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.timer_fragment, container, false);
        binding.setModel(viewModel.model);

        spinner = binding.spinner;
        initializeSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Discipline discipline = disciplines.get(position);
                viewModel.setSelectDiscipline(discipline);
                viewModel.setScramble();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.activity = null;
    }

    private void initializeSpinner() {
        disciplines = viewModel.getDataForSpinner();
        List<String> data = new ArrayList<>();
        for (Discipline discipline : disciplines) {
            data.add(discipline.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.disciplines_spinner_item, data.toArray(new String[0]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }
}
