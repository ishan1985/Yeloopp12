package com.knickglobal.yeloopp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.broadcastReceiver.OTPListener;
import com.knickglobal.yeloopp.broadcastReceiver.SmsReceiver;
import com.knickglobal.yeloopp.sharedPreferences.App;
import com.knickglobal.yeloopp.sharedPreferences.AppConstants;
import com.knickglobal.yeloopp.utils.Common;

import java.util.concurrent.TimeUnit;

public class VerifyNumberActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Beant VerifyNumber";

    private Activity activity;

    private String number = "", status = "0";

    private FirebaseAuth auth;
    private String verificationCode;

    private EditText verifyOne, verifyTwo, verifyThree, verifyFour, verifyFive, verifySix;
    private TextView timeLeftText, numberCodeSent;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_number);

        inits();

    }

    private void inits() {

        activity = VerifyNumberActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("Login");

        Button verifyOTP = findViewById(R.id.verifyOTP);
        verifyOTP.setOnClickListener(this);

        numberCodeSent = findViewById(R.id.numberCodeSent);

        // @@@@@@@@@@@@@ Fire-base OTP @@@@@@@@@@@@@
        auth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            number = intent.getStringExtra("number");
            status = intent.getStringExtra("status");

            sendVerificationCode(number);
            numberCodeSent.setText(number);
        }

        SmsReceiver.bindListener(new OTPListener() {
            @Override
            public void onOTPReceived(String extractedOTP) {
                addCodeToEditText(extractedOTP);
                Common.showToast(activity, extractedOTP);
            }
        });

        timeLeftText = findViewById(R.id.timeLeftText);

        verifyOne = findViewById(R.id.verifyOne);
        verifyTwo = findViewById(R.id.verifyTwo);
        verifyThree = findViewById(R.id.verifyThree);
        verifyFour = findViewById(R.id.verifyFour);
        verifyFive = findViewById(R.id.verifyFive);
        verifySix = findViewById(R.id.verifySix);

        TextWatcher codeTW = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (verifyOne.getText().length() == 1) {
                    verifyTwo.requestFocus();
                }
                if (verifyTwo.getText().length() == 1) {
                    verifyThree.requestFocus();
                }
                if (verifyThree.getText().length() == 1) {
                    verifyFour.requestFocus();
                }
                if (verifyFour.getText().length() == 1) {
                    verifyFive.requestFocus();
                }
                if (verifyFive.getText().length() == 1) {
                    verifySix.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        verifyOne.addTextChangedListener(codeTW);
        verifyTwo.addTextChangedListener(codeTW);
        verifyThree.addTextChangedListener(codeTW);
        verifyFour.addTextChangedListener(codeTW);
        verifyFive.addTextChangedListener(codeTW);
        verifySix.addTextChangedListener(codeTW);

        verifyTwo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    verifyOne.requestFocus();
                }
                return false;
            }
        });
        verifyThree.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    verifyTwo.requestFocus();
                }
                return false;
            }
        });
        verifyFour.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    verifyThree.requestFocus();
                }
                return false;
            }
        });
        verifyFive.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    verifyFour.requestFocus();
                }
                return false;
            }
        });
        verifySix.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    verifyFive.requestFocus();
                }
                return false;
            }
        });

        timerFunction(1);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verifyOTP:
                validations();
                break;
        }
    }

    private void validations() {

        String verifyOneS, verifyTwoS, verifyThreeS, verifyFourS, verifyFiveS, verifySixS;

        verifyOneS = verifyOne.getText().toString();
        verifyTwoS = verifyTwo.getText().toString();
        verifyThreeS = verifyThree.getText().toString();
        verifyFourS = verifyFour.getText().toString();
        verifyFiveS = verifyFive.getText().toString();
        verifySixS = verifySix.getText().toString();

        String OTP;
        OTP = verifyOneS + verifyTwoS + verifyThreeS + verifyFourS + verifyFiveS + verifySixS;

        if (OTP.isEmpty() || OTP.length() != 6) {
            Toast.makeText(activity, "Enter valid OTP", Toast.LENGTH_SHORT).show();
        } else {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, OTP);
            SignInWithPhone(credential);
        }

    }

    private void SignInWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            goMainActivity();
                        } else {
                            Log.d(TAG, "onComplete: " + task.getException());
                            Toast.makeText(activity, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String mobile_number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobile_number, 60, TimeUnit.SECONDS,
                activity, mCallback);
    }

    private void addCodeToEditText(String otp) {
        //if i can't use String.valueOf then app crashes
        verifyOne.setText(String.valueOf(otp.charAt(0)));
        verifyTwo.setText(String.valueOf(otp.charAt(1)));
        verifyThree.setText(String.valueOf(otp.charAt(2)));
        verifyFour.setText(String.valueOf(otp.charAt(3)));
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Common.showToast(activity, "Verification Completed");
            goMainActivity();
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(activity, "invalid credentials", Toast.LENGTH_SHORT).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                Common.showToast(activity, "Too many requests");
                goMainActivity();
            }
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCode = s;
            Toast.makeText(activity, "Code sent", Toast.LENGTH_SHORT).show();
        }
    };

    private void goMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (countDownTimer != null)
                    countDownTimer.cancel();
                if (status.equalsIgnoreCase("1")) {
                    startActivity(new Intent(activity, HomeActivity.class));
                    App.getAppPreference().saveBoolean(AppConstants.IS_LOGIN, true);
                    App.getAppPreference().saveString(AppConstants.LOGIN_TYPE, "number");
                } else {
                    startActivity(new Intent(activity, ChooseThreeActivity.class)
                            .putExtra("loginType", "mobile"));
                }
                finish();
            }
        }, 2000);
    }

    public void timerFunction(int minutes) {
        final long totalTimeCountInMilliseconds = minutes * 60 * 1000;
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1000) {

            public void onTick(long millisUntilFinished) {
                timeLeftText.setText("Time left: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timeLeftText.setText("Finished");

                countDownTimer.cancel();

                SharedPreferences sp = getSharedPreferences(AppConstants.SHARED_PREFERENCES_KEY, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor3 = sp.edit();
                editor3.clear();
                editor3.apply();
                startActivity(new Intent(activity, LoginActivity.class));
                finish();

                Common.showToast(activity, "Please try again later");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }
}