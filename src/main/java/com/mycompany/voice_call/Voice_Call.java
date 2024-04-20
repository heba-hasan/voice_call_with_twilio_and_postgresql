/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.voice_call;

import com.twilio.Twilio;
import com.twilio.http.HttpMethod;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.sql.SQLException;

/**
 *
 * @author 20121
 */
public class Voice_Call {

    public static void main(String[] args) throws SQLException {
        Voice_Postgresql DB = new Voice_Postgresql();
        String[] data = DB.SelectfromPostgresql();
        Makecall makecal = new Makecall();
        String number = makecal.getTonumber();
        Call call = makecal.sendmessage(data[1], data[0], number, data[2]);

        System.out.println("call id is:" + call.getSid());
        System.out.println("call from:" + call.getFrom());
        System.out.println("call to:" + call.getTo());
    }
}
