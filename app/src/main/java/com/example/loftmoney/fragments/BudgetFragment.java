package com.example.loftmoney.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.loftmoney.FragmentBudgetBinding;
import com.example.loftmoney.LoftApp;
import com.example.loftmoney.R;
import com.example.loftmoney.items.ItemsAdapter;

import com.example.loftmoney.Main.AddItemActivity;

public class BudgetFragment extends Fragment {

    private static final String ARG_CURRENT_POSITION = "current_position";
    public static final String ARG_ADD_ITEM = "arg_add_item";
    public static final int REQ_CODE = 125;

    private RecyclerView itemsView;
    private int currentPosition;
    private ItemsAdapter itemsAdapter = new ItemsAdapter();
    private BudgetViewModel mainViewModel;
    private FragmentBudgetBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBudgetBinding.inflate(inflater, container,  false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentPosition = getArguments().getInt(ARG_CURRENT_POSITION);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureViewModel();
        configureRecyclerView();

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.loadData( ((LoftApp) getActivity().getApplication()).loftAPI, currentPosition,
                        getActivity().getSharedPreferences(getString(R.string.app_name), 0));
            }
        });

        binding.addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddItemActivity.class);
                intent.putExtra(AddItemActivity.ARG_POSITION, currentPosition);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        mainViewModel.loadData(((LoftApp) getActivity().getApplication()).loftAPI, currentPosition,
                getActivity().getSharedPreferences(getString(R.string.app_name), 0));
    }

    private void configureRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvItems.setLayoutManager(layoutManager);
        binding.rvItems.setAdapter(itemsAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        binding.rvItems.addItemDecoration(dividerItemDecoration);
    }

    private void configureViewModel() {
        mainViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);

        mainViewModel.itemsLiveData.observe(getViewLifecycleOwner(), moneyItems -> {
            itemsAdapter.setData(moneyItems);
        });

        mainViewModel.swipeRefresh.observe(getViewLifecycleOwner(), isRefresh -> {
            binding.swipeRefresh.setRefreshing(isRefresh);
        });

        mainViewModel.messageString.observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        });

        mainViewModel.messageInt.observe(getViewLifecycleOwner(), messageInt -> {
            Toast.makeText(getActivity(), getString(messageInt), Toast.LENGTH_LONG).show();
        });
    }

    private int getString(String messageInt) {
        return 0;
    }

    public static BudgetFragment newInstance(int position) {
        BudgetFragment budgetFragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CURRENT_POSITION, position);
        budgetFragment.setArguments(args);
        return budgetFragment;
    }
}
