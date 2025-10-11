package com.example.tpinmobiliariappdmgn.ui.logout;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogoutViewModel extends ViewModel {

        private final MutableLiveData<String> mText;

        public LogoutViewModel() {
            mText = new MutableLiveData<>();
            mText.setValue("Logout");
        }



        public LiveData<String> getText() {
            return mText;
        }
    }

