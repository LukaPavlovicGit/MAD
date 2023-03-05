package com.example.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.constants.Constants;
import com.example.raf_jira.R;
import com.example.raf_jira.databinding.FragmentLoginBinding;
import com.example.viewModels.LoginViewModel;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        init();
    }

    private void init(){
        binding.loginButton.setOnClickListener(view -> {
            String username = binding.usernameEt.getText().toString().trim();
            String email = binding.emailEt.getText().toString().trim();
            String password = binding.passwordEt.getText().toString().trim();

            if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(getActivity(), "Invalid inputs", Toast.LENGTH_SHORT).show();
                return;
            }
            if( !username.startsWith("admin") && !password.equals(Constants.USER_PASSWORD) ||
                    username.startsWith("admin") && !password.equals(Constants.ADMIN_PASSWORD) ){
                Toast.makeText(getActivity(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, MODE_PRIVATE);

            sharedPreferences
                    .edit()
                    .putBoolean(Constants.IS_LOGGED_KEY, true)
                    .putString(Constants.USERNAME_KEY, username)
                    .putString(Constants.PASSWORD_KEY, password)
                    .apply();

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_view_tag, new MainFragment());
            transaction.commit();

        });
    }

    private FragmentTransaction createTransactionWithAnimation() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        // Dodajemo animaciju kada se fragment doda
    //transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        // Dodajemo transakciju na backstack kako bi se pritisokm na back transakcija rollback-ovala
        transaction.addToBackStack(null);
        return transaction;
    }
}