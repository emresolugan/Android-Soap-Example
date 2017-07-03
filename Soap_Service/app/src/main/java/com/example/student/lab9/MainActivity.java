package com.example.student.lab9;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView tv;
    EditText etcountrycode,etcurrency,etcurrencycode,etname,etsorgu;
    String Ulke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.btn_Sorgu);
        tv = (TextView)findViewById(R.id.textView);
        etsorgu = (EditText)findViewById(R.id.et_Sorgu);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ulke = etsorgu.getText().toString();
                AsenkronCagri as = new AsenkronCagri();
                as.execute(Ulke);
            }
        });
    }

    public class AsenkronCagri extends AsyncTask<String,Void,String>
    {
        String getValue(String tag, Element element) {
            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = nodeList.item(0);
            return node.getNodeValue();
        }


        @Override
        protected String doInBackground(String... params) {
            CallSoap cs = new CallSoap();
            String response = cs.GetCurrencyByCountry(params[0]);
            return response;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String s) {

            etcountrycode = (EditText)findViewById(R.id.et_CountryCode);
            etcurrency = (EditText)findViewById(R.id.et_Currency);
            etcurrencycode = (EditText)findViewById(R.id.et_CurrencyCode);
            etname = (EditText)findViewById(R.id.et_name);

            super.onPostExecute(s);
             tv.setText(s);
            try {
                InputStream is = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);
                Element element=doc.getDocumentElement();
                element.normalize();
                NodeList nList = doc.getElementsByTagName("Table");
                for (int i=0; i<nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                      //  Toast.makeText(MainActivity.this, getValue("Name", element2)+" "+getValue("CountryCode", element2)+" "+getValue("Currency", element2)+" "+getValue("CurrencyCode", element2), Toast.LENGTH_SHORT).show();

                        etcountrycode.setText(getValue("CountryCode", element2));
                        etname.setText(getValue("Name", element2));
                        etcurrency.setText(getValue("Currency", element2));
                        etcurrencycode.setText(getValue("CurrencyCode", element2));

                    }
                }
            } catch (Exception e) {e.printStackTrace();}


        }

    }
}
