package edu.byu.cs.familymap.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.EventModel;
import edu.byu.cs.familymap.model.ModelContainer;
import edu.byu.cs.familymap.model.PersonModel;
import edu.byu.cs.familymap.webAccess.HttpClient;

/**
 * Fragment to handle login aspect of FamilyMap
 * Created by chris on 8/1/2016.
 */
public class LoginFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText hostEditText;
    private EditText portEditText;
    private Button loginButton;
    private String authorizationToken;
    private HttpClient client=new HttpClient();
    private MainActivity mainActivity;
    private PersonModel user;

    public LoginFragment(){

    }

    public static LoginFragment newInstance()
    {
        LoginFragment fragment=new LoginFragment();
        Bundle args=new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceData)
    {
        super.onCreate(savedInstanceData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceData)
    {
        View v=inflater.inflate(R.layout.login, container, false);

        usernameEditText=(EditText)v.findViewById(R.id.usernameEditText);
        passwordEditText=(EditText)v.findViewById(R.id.passwordEditText);
        hostEditText=(EditText)v.findViewById(R.id.hostEditText);
        portEditText=(EditText)v.findViewById(R.id.portEditText);

        loginButton=(Button)v.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loginButtonClicked();
            }
        });

        return v;
    }

    private void loginButtonClicked()
    {
        LoginTask task=new LoginTask();

        task.execute(usernameEditText.getText().toString(), passwordEditText.getText().toString(),
                hostEditText.getText().toString(), portEditText.getText().toString());
    }

    public void processLoginResult(String result)
    {
        user=new PersonModel();
        authorizationToken=null;
        try
        {
            JSONObject object=new JSONObject(result);
            authorizationToken=object.getString("Authorization");
            user.setPersonID(object.getString("personId"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if(authorizationToken!=null)
        {
            SyncDataTask task=new SyncDataTask();

            task.execute(authorizationToken);
        }
        else
        {
            Toast toast=Toast.makeText(getMainActivity(), "Login failed.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void processSyncDataResult(String[] result)
    {
        Toast toast=Toast.makeText(getMainActivity(), "", Toast.LENGTH_LONG);
        try
        {
            JSONObject people=new JSONObject(result[0]);
            JSONArray peopleArray=people.getJSONArray("data");
            for(int i=0;i<peopleArray.length();i++)
            {
                PersonModel person=new PersonModel();
                person.populateFromJSON(peopleArray.getString(i));
                ModelContainer.getSingleton().addPerson(person);
            }

            JSONObject events=new JSONObject(result[1]);
            JSONArray eventArray=events.getJSONArray("data");
            for(int i=0;i<eventArray.length();i++)
            {
                EventModel event=new EventModel();
                event.populateFromJSON(eventArray.getString(i));
                ModelContainer.getSingleton().addEvent(event);
            }
            toast.setText("Data synced. Welcome!");
            mainActivity.loggedIn();//Closes login fragment, opens map fragment
        }
        catch (Exception e)
        {
            e.printStackTrace();
            toast.setText("Sync Data failed.");
        }

        toast.show();
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public class LoginTask extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... strings)
        {
            return client.logIn(strings[0],strings[1],strings[2],strings[3]);
        }

        protected void onPostExecute(String result)
        {
            if(result!=null)
            {
                processLoginResult(result);
            }
            else
            {
                Toast toast=Toast.makeText(getMainActivity(), "Login returned null.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public class SyncDataTask extends AsyncTask<String, Integer, String[]>
    {

        @Override
        protected String[] doInBackground(String... strings)
        {
            return client.syncData(strings[0]);
        }

        protected void onPostExecute(String[] result)
        {
            processSyncDataResult(result);
        }
    }
}
