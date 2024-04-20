/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.voice_call;

import com.twilio.Twilio;
import com.twilio.http.HttpMethod;
import com.twilio.rest.api.v2010.account.Call;
import java.net.URI;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author 20121
 */
public class Makecall {
    public Call sendmessage(String ACCOUNT_SID,String AUTH_TOKEN,String sendTo,String Twilio_number ) throws SQLException
  {
         Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Call call = Call.creator(
                new com.twilio.type.PhoneNumber(sendTo),
                new com.twilio.type.PhoneNumber(Twilio_number),
                URI.create("https://raw.githubusercontent.com/heba-hasan/call/main/callcontent.xml"))
            .setMethod(HttpMethod.GET).setSendDigits("1234#").create();
    waitForCallCompletion(call.getSid());
        return  call;
  }
    private static void waitForCallCompletion(String callSid) throws SQLException {
                Voice_Postgresql DB = new Voice_Postgresql();

        while (true) {
            Call call = Call.fetcher(callSid).fetch();
            Call.Status callStatus = call.getStatus();
 
            System.out.println("Call SID: " + call.getSid());
            System.out.println("Call Status: " + callStatus);
 
            if (callStatus == Call.Status.COMPLETED) {
        DB.insertToDB(call.getSid(), call.getFrom(), call.getTo(), call.getDuration(), call.getDateCreated().toString(), call.getStatus().toString());
                break;
            }
 
            // Sleep for a while before checking again
            try {
                Thread.sleep(5000); // Sleep for 5 seconds (adjust as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
  public String getTonumber()
  {
       Scanner scanner = new Scanner(System.in);
 
        System.out.println("TO: ");
 
        String phone = scanner.nextLine();
        scanner.close();
        return phone;
    }
}

