package com.example.pepperapp.Controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pepperapp.R;
import com.example.pepperapp.model.JsonParseRobotList;
import com.google.android.material.textfield.TextInputEditText;

public class EditRobotFragment extends Fragment {
    private View mView;
    private ImageButton mEditNameButton;
    private ImageButton mEditIPButton;
    private ImageButton mEditPortButton;
    private Button mSaveChanges;
    private TextInputEditText mEditNameText;
    private TextInputEditText mEditIPText;
    private TextInputEditText mEditPortText;
    private JsonParseRobotList mJsonParseRobotList;
    private View.OnClickListener mOnClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.edit_robot_fragment, container, false);
        init();
        if (mJsonParseRobotList.readJsonFile()) {
            mJsonParseRobotList.jsonToJavaObject();
            int index = getSelectedRobotPosition();
            this.mEditNameText.setText(mJsonParseRobotList.getmRobotList().get(index).getmRobotName());
            this.mEditIPText.setText(mJsonParseRobotList.getmRobotList().get(index).getmRobotIPAddress());
            this.mEditPortText.setText(mJsonParseRobotList.getmRobotList().get(index).getmRobotPort());
        }

        mEditNameButton.setOnClickListener(this.mOnClickListener);
        mEditIPButton.setOnClickListener(this.mOnClickListener);
        mEditPortButton.setOnClickListener(this.mOnClickListener);
        mSaveChanges.setOnClickListener(this.mOnClickListener);


        return mView;
    }

    public void init() {
        this.mEditNameButton = (ImageButton) mView.findViewById(R.id.edit_name_button);
        this.mEditIPButton = (ImageButton) mView.findViewById(R.id.edit_ip_button);
        this.mEditPortButton = (ImageButton) mView.findViewById(R.id.edit_port_button);
        this.mSaveChanges = (Button)mView.findViewById(R.id.save_changes_button);
        this.mEditNameText = (TextInputEditText) mView.findViewById(R.id.edit_rname);
        this.mEditIPText = (TextInputEditText) mView.findViewById(R.id.edit_rip);
        this.mEditPortText = (TextInputEditText) mView.findViewById(R.id.edit_rport);
        this.mEditIPButton.setTag("IP");
        this.mEditNameButton.setTag("NAME");
        this.mEditPortButton.setTag("PORT");
        this.mSaveChanges.setTag("SAVE");
        this.mJsonParseRobotList = new JsonParseRobotList(this.getContext());
        this.mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getTag().toString()) {
                    case "NAME":
                        mEditNameText.setEnabled(true);
                        mSaveChanges.setEnabled(true);
                        break;
                    case "IP":
                        mEditIPText.setEnabled(true);
                        mSaveChanges.setEnabled(true);
                        break;
                    case "PORT":
                        mEditPortText.setEnabled(true);
                        mSaveChanges.setEnabled(true);
                        break;
                    case "SAVE":
                        updateRobotInfo();
                        break;
                }

            }
        };
    }

    public int getSelectedRobotPosition() {
        Bundle bundle = this.getArguments();
        return bundle.getInt("selectedRobotPosition");
    }

    public void updateRobotInfo(){
        int index = this.getSelectedRobotPosition();
        this.mJsonParseRobotList.getmRobotList().get(index).setmRobotName(this.mEditNameText.getText().toString());
        this.mJsonParseRobotList.getmRobotList().get(index).setmRobotPort(this.mEditPortText.getText().toString());
        this.mJsonParseRobotList.getmRobotList().get(index).setmRobotIPAddress(this.mEditIPText.getText().toString());
        this.mJsonParseRobotList.writeToJsonFile(mJsonParseRobotList.javaObjectToJson());
    }
}
