package chukuzoegwu.cleancity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register_Page_Activity extends AppCompatActivity {

    protected EditText firstNameET;
    protected EditText lastNameET;
    protected EditText emailET;
    protected EditText usernameET;
    protected EditText passwordET;
    protected EditText confirmPassET;
    protected Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__page_);

        firstNameET = findViewById(R.id.firstNameET);
        lastNameET = findViewById(R.id.lastNameET);
        emailET = findViewById(R.id.emailET);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        confirmPassET = findViewById(R.id.confirmPassET);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameET.getText().toString();
                String lastName = lastNameET.getText().toString();
                String email = emailET.getText().toString();
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                String confirmPass = confirmPassET.getText().toString();

                if (checkPass(password, confirmPass)) {
                    Account.create(firstName, lastName, email, username, password);
                    Intent startIntent = new Intent(getApplicationContext(), MapView_Activity.class);
                    startActivity(startIntent);
                }
                else {
                    passwordET.setText("");
                    confirmPassET.setText("");
                }
            }
        });
    }

    private boolean checkPass(String pass1, String pass2) {
        return pass1.equals(pass2);
    }


}
