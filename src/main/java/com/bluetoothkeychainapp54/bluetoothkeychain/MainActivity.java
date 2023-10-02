package com.bluetoothkeychainapp54.bluetoothkeychain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    Button bttnOn,bttnOff;
    String adrress = null;

    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    RecyclerView recyclerView;
    ArrayList<Music> musics = new ArrayList<>();
    Context context = this;

    private MainActivity ma = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        adrress = newint.getStringExtra(DeviceListActivity.EXTRA_ADDRESS); //receive the address of the bluetooth device

        setContentView(R.layout.activity_main);

        if(adrress != null){
            new ConnectBT().execute();
        }



        Toolbar tool;
        tool = findViewById(R.id.toolbar);
        setSupportActionBar(tool);

        getSupportActionBar().setTitle("Anahtarlık");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_toys_black_24dp);
        tool.setSubtitle(" \"Artık Anahtarını Bulmak Çok Kolay!\" ");

        bttnOn = findViewById(R.id.button2);
        bttnOff = findViewById(R.id.button3);
        bttnOn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (btSocket == null){
                    msg("Uygulamayı kullananbilmek için öncelikle bluetooth bağlantısı yapmalısınız.");
                }
                turnOnBuzzer();
            }
        });

        bttnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (btSocket == null){
                    msg("Uygulamayı kullananbilmek için öncelikle bluetooth bağlantısı yapmalısınız.");
                }
                turnOffBuzzer();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);


        musics.add(new Music("Pirates of the Caribbean"));
        musics.add(new Music("Crazy Frog"));
        musics.add(new Music("Mario UnderWorld"));
        musics.add(new Music("Titanic"));


        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(musics, context ,ma);

        recyclerView.setAdapter(recyclerViewAdapter);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:

                         Intent intent = new Intent(MainActivity.this, DeviceListActivity.class);
                         startActivity(intent);


                return true;
            case R.id.item2:

                if (btSocket == null){
                    msg("Bluetooth bağlantısı yapmadan bluetooth bağlantısını kesemezsiniz. :)");
                }else{
                    Disconnect();
                    msg("Bağlantı kesildi.");
                }
                return true;
            case R.id.item3:
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(MainActivity.this,PairActivity.class);
                        startActivity(intent);
                        return true;
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
    private void turnOffBuzzer()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TF".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void turnOnBuzzer()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TO".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void Disconnect()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.close();
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    public void playMusic(String name){
        if(btSocket != null){
            switch (name){
                case "Pirates of the Caribbean":
                    try
                    {
                        btSocket.getOutputStream().write("m1".getBytes());
                        msg("Pirates of the Caribbean");
                    }
                    catch (IOException e)
                    {
                        msg("Hata");
                    }
                    break;
                case "Crazy Frog":
                    try
                    {
                        btSocket.getOutputStream().write("m2".getBytes());
                        msg("Crazy Frog");
                    }
                    catch (IOException e)
                    {
                        msg("Hata");
                    }
                    break;
                case "Mario UnderWorld":
                    try
                    {
                        btSocket.getOutputStream().write("m3".getBytes());
                        msg("Mario UnderWorld");
                    }
                    catch (IOException e)
                    {
                        msg("Hata");
                    }
                    break;
                case "Titanic":
                    try
                    {
                        btSocket.getOutputStream().write("m4".getBytes());
                        msg("Titanic");
                    }
                    catch (IOException e)
                    {
                        msg("Hata");
                    }
                    break;
                default:
                    Toast.makeText(context,"Bir hata meydana geldi.",Toast.LENGTH_LONG).show();
            }
        }else{
            msg("hata");
        }
    }



    private class ConnectBT extends AsyncTask<Void, Void, Void>
    {
        private boolean ConnectSuccess = true;
        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(MainActivity.this, "Bağlanılıyor...", "Lütfen Bekleyin!!!");
        }
        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(adrress);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }
            catch (IOException e)
            {
                Log.d("hata",e.getMessage());
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (!ConnectSuccess)
            {
                msg("Bağlantı hatası! Tekrar deneyin!.");
                finish();
            }
            else
            {
                msg("Bağlanıldı.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}

