package com.example.sqlite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionDetailsFragment extends BottomSheetDialogFragment {
    Transactions present_transaction;
    public TransactionDetailsFragment(Transactions t){
        present_transaction=t;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionDetailsFragment newInstance(String param1, String param2) {
        TransactionDetailsFragment fragment = new TransactionDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView Transaction_details_heading=view.findViewById(R.id.Transaction_Details_heading);

        ImageView success=view.findViewById(R.id.success_image);
        ImageView failed=view.findViewById(R.id.fail_image);
        if(present_transaction.getStatus().equals("Success")){
            failed.setImageAlpha(0);
            Transaction_details_heading.setBackgroundColor(getResources().getColor(R.color.success_banner));
            Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
            success.startAnimation(animation);
        }
        else{
            Transaction_details_heading.setBackgroundColor(getResources().getColor(R.color.failed_banner));
            success.setImageAlpha(0);
            Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.fadein);
            failed.startAnimation(animation);
        }
        TextView s_name=view.findViewById(R.id.s_name);
        TextView r_name=view.findViewById(R.id.r_name);
        TextView trans_amount=view.findViewById(R.id.trans_amount);
        TextView date_trans=view.findViewById(R.id.date_transaction);
        TextView sender_accno_view=view.findViewById(R.id.s_accno);
        TextView reciever_accno_view=view.findViewById(R.id.r_accno);
        TextView trans_id=view.findViewById(R.id.transid);
        s_name.setText(present_transaction.getSender_name());
        r_name.setText(present_transaction.getReceiver_name());
        sender_accno_view.setText(present_transaction.getSender_accno());
        reciever_accno_view.setText(present_transaction.getReceiver_accno());
        trans_amount.setText("Amount "+present_transaction.getAmount());
        date_trans.setText("Date of Transaction "+present_transaction.getDate());
        trans_id.setText("Transaction ID "+present_transaction.getTransaction_id());


    }
}