package com.nmeunier.minitel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    String activeUser = "";
    ArrayList<String> messages = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView chatListView;

//    public class CustomListAdapter extends ArrayAdapter <String> {
//
//        private Context mContext;
//        private int id;
//        private List <String>items ;
//
//        public CustomListAdapter(Context context, int textViewResourceId , List<String> list )
//        {
//            super(context, textViewResourceId, list);
//            mContext = context;
//            id = textViewResourceId;
//            items = list ;
//        }
//
//        @Override
//        public View getView(int position, View v, ViewGroup parent)
//        {
//            View mView = v ;
//            if(mView == null){
//                LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                mView = vi.inflate(id, null);
//            }
//
//            TextView text = (TextView) mView.findViewById(R.id.textView);
//
//            if(items.get(position) != null )
//            {
//                text.setTextColor(Color.WHITE);
//                text.setText(items.get(position));
//                text.setBackgroundColor(Color.RED);
//                int color = Color.argb( 200, 255, 64, 64 );
//                text.setBackgroundColor( color );
//
//            }
//
//            return mView;
//        }
//
//    }

    public void updateChat() {
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the list view each item as text view
                TextView item = (TextView) super.getView(position,convertView,parent);

                // Set the typeface/font for the current item
                item.setTypeface(Typeface.MONOSPACE);

                // Set the list view item's text color
                String text = item.getText().toString();
                if (text.contains("]: >>")) {
                    item.setTextColor(Color.parseColor("#FF00FFF2"));
                    item.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                } else {
                    item.setTextColor(Color.parseColor("#00ff00"));
                    item.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                }

                // Set the item text style to bold
                item.setTypeface(item.getTypeface(), Typeface.BOLD);

//                // Change the item text size
//                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);

                // return the view
                return item;
            }
        };

        // Data bind the list view with array adapter items
        chatListView.setAdapter(arrayAdapter);

        ParseQuery<ParseObject> querySender = new ParseQuery<ParseObject>("Message");
        querySender.whereEqualTo("sender", ParseUser.getCurrentUser().getUsername());
        querySender.whereEqualTo("recipient", activeUser);

        ParseQuery<ParseObject> queryRecipient = new ParseQuery<ParseObject>("Message");
        queryRecipient.whereEqualTo("recipient", ParseUser.getCurrentUser().getUsername());
        queryRecipient.whereEqualTo("sender", activeUser);

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();

        queries.add(querySender);
        queries.add(queryRecipient);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        messages.clear();
                        for(ParseObject message : objects) {
                            String messageContent = message.getString("message");

                            if (!message.getString("sender").equals(ParseUser.getCurrentUser().getUsername())) {
                                messageContent = ">> " + messageContent;
                            }
                            String timestamp = message.getCreatedAt().toString().substring(3, 18);
                            messages.add("[" + timestamp + "]: " + messageContent);
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
    public void sendChat(View view) {
        final EditText chatEditText = (EditText) findViewById(R.id.chatEditText);
        ParseObject message = new ParseObject("Message");

        message.put("sender", ParseUser.getCurrentUser().getUsername());
        message.put("recipient", activeUser);
        message.put("message", chatEditText.getText().toString());

        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(ChatActivity.this, "Message successful", Toast.LENGTH_LONG).show();

                }
            }
        });
        chatEditText.setText("");
        updateChat();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        activeUser = intent.getStringExtra("username");
        setTitle("Chat with " + activeUser);
        Log.i("Info", activeUser);

        chatListView = (ListView) findViewById(R.id.chatListView);

        updateChat();
    }
}
