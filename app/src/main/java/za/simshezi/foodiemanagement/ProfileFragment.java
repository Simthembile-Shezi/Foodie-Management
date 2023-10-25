package za.simshezi.foodiemanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import za.simshezi.foodiemanagement.model.ShopModel;

public class ProfileFragment extends Fragment {
    public static final int PROFILE_REQ = 3;
    private TextView tvName, tvEmail;
    private LinearLayout layoutEducation;
    private LinearLayout layoutReports;
    private LinearLayout layoutPromotions;
    private LinearLayout layoutHelp;
    private LinearLayout layoutAbout;
    private LinearLayout layoutPrivacy;
    private LinearLayout layoutSettings;
    private ImageView imgProfile;
    private Button btnSignOut;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName = view.findViewById(R.id.tvProfileName);
        tvEmail = view.findViewById(R.id.tvProfileEmail);
        layoutEducation = view.findViewById(R.id.layoutProfileEducation);
        layoutReports = view.findViewById(R.id.layoutProfileSalesReport);
        layoutPromotions = view.findViewById(R.id.layoutProfilePromotions);
        layoutHelp = view.findViewById(R.id.layoutProfileHelp);
        layoutAbout = view.findViewById(R.id.layoutProfileAbout);
        layoutPrivacy = view.findViewById(R.id.layoutProfilePrivacy);
        layoutSettings = view.findViewById(R.id.layoutProfileSettings);
        imgProfile = view.findViewById(R.id.imgProfile);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        build();
    }

    private void build() {
        layoutEducation.setOnClickListener(onLayoutClicked(EducationActivity.class));
        layoutReports.setOnClickListener(onLayoutClicked(SalesReportActivity.class));
        layoutPromotions.setOnClickListener(onLayoutClicked(PromotionsActivity.class));
        layoutHelp.setOnClickListener(onLayoutClicked(HelpActivity.class));
        layoutAbout.setOnClickListener(onLayoutClicked(AboutActivity.class));
        layoutPrivacy.setOnClickListener(onLayoutClicked(PrivacyActivity.class));
        layoutSettings.setOnClickListener(onLayoutClicked(SettingsActivity.class));
        imgProfile.setOnClickListener(onLayoutClicked(ProfileActivity.class));
        btnSignOut.setOnClickListener((view) -> {
            Activity activity = requireActivity();
            SharedPreferences sharedpreferences = activity.getSharedPreferences("login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            activity.finishAffinity();
        });
        ShopModel shop = (ShopModel) requireActivity().getIntent().getSerializableExtra("shop");
        tvName.setText(shop.getName());
        tvEmail.setText(shop.getEmail());
    }

    @NonNull
    private View.OnClickListener onLayoutClicked(Class<?> clazz) {
        ShopModel shop = (ShopModel) requireActivity().getIntent().getSerializableExtra("shop");
        return (view -> {
            Intent intent = new Intent(getContext(), clazz);
            intent.putExtra("shop", shop);
            startActivity(intent);
        });
    }
}