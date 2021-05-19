package com.dam.myvet.ui.Alertas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dam.myvet.R;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AlertasFragment extends Fragment {
    EditText destinatario,asunto,mensaje;
    Button btMandar;
    String correo,contraseña;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_alertas, container, false);

        destinatario = root.findViewById(R.id.alertaCorreoDestino);
        asunto = root.findViewById(R.id.alertaAsunto);
        mensaje = root.findViewById(R.id.alertaMensaje);
        btMandar = root.findViewById(R.id.btMandaAlerta);

        //Credenciales del correo mensajero
        correo = "myvet.dam@gmail.com";
        contraseña = "myvetdam_1";

        btMandar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Propiedades de inicio
                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "465");
                properties.put("mail.smtp.socketFactory.port", "465");
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

                //Inicializar sesión
                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(correo, contraseña);
                    }
                });

                progressDialog = ProgressDialog.show(getActivity(), "Por favor, espere",
                        "Enviando mensaje...", true, false);

                SenderAsyncTask task = new SenderAsyncTask(session, correo,
                        destinatario.getText().toString(), asunto.getText().toString(), mensaje.getText().toString());
                task.execute();
            }
        });
        return root;
    }

    /**
     * AsyncTask to send email
     */
    private class SenderAsyncTask extends AsyncTask<String, String, String> {

        private String correoOrigen, correoDestino, subject, message;
        private ProgressDialog progressDialog;
        private Session session;

        public SenderAsyncTask(Session session, String correoOrigen, String correoDestino, String asunto, String message) {
            this.session = session;
            this.correoOrigen = correoOrigen;
            this.correoDestino = correoDestino;
            this.subject = asunto;
            this.message = message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "Enviando mensaje", "Espere por favor...", true);
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Message mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(correoOrigen));
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
                mimeMessage.setSubject(subject);
                mimeMessage.setContent(message, "text/html; charset=utf-8");
                Transport.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
                return e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}