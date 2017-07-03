package com.example.student.lab9;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;

/**
 * Created by Asus on 10.05.2017.
 */

public class CallSoap {

    public  String GetCurrencyByCountry (String UlkeAdi)
    {
        String SOAP_ADDRESS = "http://www.webservicex.net/country.asmx";
        String SOAP_ACTION = "http://www.webserviceX.NET/GetCurrencyByCountry";
        String OPERATION_NAME = "GetCurrencyByCountry";
        String WSDL_TARGET_NAMESPACE ="http://www.webserviceX.NET";

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("CountryName");
        pi.setValue(UlkeAdi);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        String response = null;

        try {
            HttpTransportSE httpTransportSE = new HttpTransportSE(SOAP_ADDRESS);
            httpTransportSE.call(SOAP_ACTION,envelope);
            response = envelope.getResponse().toString();
        }
        catch (Exception ex)
        {
            response = ex.toString();
        }

        return  response;
    }

}

