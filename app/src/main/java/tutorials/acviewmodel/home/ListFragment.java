package tutorials.acviewmodel.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tutorials.acviewmodel.R;
import tutorials.acviewmodel.base.MyApplication;
import tutorials.acviewmodel.details.DetailsFragment;
import tutorials.acviewmodel.model.Repo;
import tutorials.acviewmodel.viewmodel.ViewModelFactory;

/**
 * Created by TBLTechNerds on 11/29/18.
 */
public class ListFragment extends Fragment implements RepoSelectedListener {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.recycler_view)
    RecyclerView listView;
    @BindView(R.id.tv_error)
    TextView errorTextView;
    @BindView(R.id.loading_view)
    View loadingView;

    private Unbinder unbinder;
    private ListViewModel viewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MyApplication.getApplicationComponent(context).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);

        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(new RepoListAdapter(viewModel, this, this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        observeViewModel();
    }

    @Override
    public void onRepoSelected(Repo repo) {
        SelectedRepoViewModel selectedRepoViewModel =
                ViewModelProviders.of(getActivity(), viewModelFactory).get(SelectedRepoViewModel.class);

        selectedRepoViewModel.setSelectedRepo(repo);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.screen_container, new DetailsFragment())
                .addToBackStack(null).commit();
    }

    private void observeViewModel() {
        viewModel.getRepos().observe(this, repos -> {
            if (repos != null) {
                listView.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getError().observe(this, isError -> {
            //noinspection ConstantConditions
            if (isError) {
                errorTextView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                errorTextView.setText(R.string.api_error_repos);
            } else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
            }
        });

        viewModel.getLoading().observe(this, isLoading -> {
            //noinspection ConstantConditions
            loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            if (isLoading) {
                errorTextView.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
            }
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
