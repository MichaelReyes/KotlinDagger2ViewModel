package tutorials.acviewmodel.details;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tutorials.acviewmodel.R;
import tutorials.acviewmodel.base.MyApplication;
import tutorials.acviewmodel.home.SelectedRepoViewModel;
import tutorials.acviewmodel.viewmodel.ViewModelFactory;

public class DetailsFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.tv_repo_name) TextView repoNameTextView;
    @BindView(R.id.tv_repo_description) TextView repoDescriptionTextView;
    @BindView(R.id.tv_forks) TextView forksTextView;
    @BindView(R.id.tv_stars) TextView starsTextView;

    private Unbinder unbinder;
    private SelectedRepoViewModel selectedRepoViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MyApplication.getApplicationComponent(context).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        selectedRepoViewModel = ViewModelProviders.of(getActivity(), viewModelFactory)
                .get(SelectedRepoViewModel.class);

        selectedRepoViewModel.restoreFromBundle(savedInstanceState);
        displayRepo();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        selectedRepoViewModel.saveToBundle(outState);
    }

    private void displayRepo(){

        selectedRepoViewModel.getSelectedRepo().observe(this, repo -> {
            repoNameTextView.setText(repo.name());
            repoDescriptionTextView.setText(repo.description());
            forksTextView.setText(String.valueOf(repo.forks()));
            starsTextView.setText(String.valueOf(repo.stars()));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
