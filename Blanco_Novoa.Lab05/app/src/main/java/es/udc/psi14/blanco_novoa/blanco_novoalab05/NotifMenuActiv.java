package es.udc.psi14.blanco_novoa.blanco_novoalab05;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class NotifMenuActiv extends Activity {
    Button butnotif, butcancel, butdialog;
    TextView tv, tv2;
    boolean apple, pear;
    int number = 0;
    String selected;
    CheckBox sonido, vibracion, led;
    RadioButton bigText, bigPicture, inBox;
    ActionBar.Tab tab0, tab1, tab2, tab3;
    Fragment fragmentTab1 = new FragmentTab1();
    Fragment fragmentTab2 = new FragmentTab2();
    Fragment fragmentTab3 = new FragmentTab3();
    Fragment mainFragment = new MainFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_notif_menu);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        tab1 = actionBar.newTab().setText("1");
        tab0 = actionBar.newTab().setText("0");
        tab2 = actionBar.newTab().setText("2");
        tab3 = actionBar.newTab().setText("3");

        tab0.setTabListener(new MiTabListener(mainFragment));
        tab1.setTabListener(new MiTabListener(fragmentTab1));
        tab2.setTabListener(new MiTabListener(fragmentTab2));
        tab3.setTabListener(new MiTabListener(fragmentTab3));

        actionBar.addTab(tab0);
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);

        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        registerForContextMenu(tv2);


        butnotif = (Button) findViewById(R.id.but_notif);
        butcancel = (Button) findViewById(R.id.but_cancel);
        butdialog = (Button) findViewById(R.id.but_dialog);

        sonido = (CheckBox) findViewById(R.id.sonido);
        vibracion = (CheckBox) findViewById(R.id.vibracion);
        led = (CheckBox) findViewById(R.id.led);

        bigText = (RadioButton) findViewById(R.id.big_text);
        bigPicture = (RadioButton) findViewById(R.id.big_picture);
        inBox = (RadioButton) findViewById(R.id.in_box);


        butnotif.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Intent resultIntent = new Intent(NotifMenuActiv.this, NotifActiv.class);
                // Activity to be launched
                PendingIntent pIntent = PendingIntent.getActivity(NotifMenuActiv.this, 457, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                Notification.Builder mBuilder = new Notification.Builder(NotifMenuActiv.this);
                mBuilder.setSmallIcon(R.drawable.ic_launcher).setContentTitle("notificacion");
                if (vibracion.isChecked()) {
                    mBuilder.setVibrate(new long[]{0, 100, 200, 300});
                }
                if (sonido.isChecked()) {
                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    mBuilder.setSound(alarmSound);
                }
                if (led.isChecked()) {
                    mBuilder.setLights(Color.BLUE, 500, 500);

                }
                if (bigText.isChecked()) {
                    mBuilder.setStyle(new Notification.BigTextStyle().bigText("Esto es el texto mas grandeeeeeeee.......\n grandeeee \n grande es el texto mas eee\nlaaaaaaaalala lorem ipsum"));
                }
                if (bigPicture.isChecked()) {
                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.linux_windows);
                    mBuilder.setStyle(new Notification.BigPictureStyle().bigPicture(bm));
                }
                if (inBox.isChecked()) {
                    mBuilder.setStyle(new Notification.InboxStyle()
                            .addLine("linea 1")
                            .addLine("linea 2222")
                            .setSummaryText("+99 more"));
                }

                mBuilder.setContentIntent(pIntent);
                Intent resultIntent2 = new Intent(NotifMenuActiv.this, NotifActiv.class);
                resultIntent2.setAction("Share");
                PendingIntent pIntent2 = PendingIntent.getActivity(NotifMenuActiv.this, 458, resultIntent2, PendingIntent.FLAG_CANCEL_CURRENT);
                mBuilder.addAction(android.R.drawable.ic_menu_share, "Share", pIntent2);

                Intent resultIntent3 = new Intent(NotifMenuActiv.this, NotifActiv.class);
                resultIntent3.setAction("agenda");
                PendingIntent pIntent3 = PendingIntent.getActivity(NotifMenuActiv.this, 459, resultIntent3, PendingIntent.FLAG_CANCEL_CURRENT);
                mBuilder.addAction(android.R.drawable.ic_menu_agenda, "agenda", pIntent3);

                Intent resultIntent4 = new Intent(NotifMenuActiv.this, NotifActiv.class);
                resultIntent4.setAction("call");
                PendingIntent pIntent4 = PendingIntent.getActivity(NotifMenuActiv.this, 460, resultIntent4, PendingIntent.FLAG_CANCEL_CURRENT);
                mBuilder.addAction(android.R.drawable.ic_menu_call, "call", pIntent4);

                mNotificationManager.notify(234,mBuilder.build());
            }
        });

        butcancel.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.cancel(234);
            }
        });

        butdialog.setOnClickListener(new Button.OnClickListener() {


            @Override
            public void onClick(final View v) {
                final CharSequence[] items = {"Ford", "opel", "Caballo"};

                AlertDialog.Builder builder = new AlertDialog.Builder(NotifMenuActiv.this);
                builder.setTitle("Coches");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        NotifMenuActiv.this.selected = items[item].toString();
                        registerForContextMenu(v);
                        openContextMenu(v);
                        unregisterForContextMenu(v);
                    }
                });
                AlertDialog alert = builder.create();

                alert.show();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getGroupId() == R.id.group1) {
            tv2.setText(item.getTitle());
        }
        else {
            Toast.makeText(getApplicationContext(), item.getTitle().toString() + " " + selected , Toast.LENGTH_LONG).show();
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        if (v == tv2) {
            inflater.inflate(R.menu.notif_context_menu, menu);
        } else {
            inflater.inflate(R.menu.notif_context_menu2, menu);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notif_menu, menu);


        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (number == 1) {
            MenuItem item = menu.findItem(R.id.one);
            item.setChecked(true);
        }
        if (number == 2) {
            MenuItem item = menu.findItem(R.id.two);
            item.setChecked(true);
        }

        if (apple) {
            MenuItem item = menu.findItem(R.id.apple);
            item.setChecked(true);
        } else {
            MenuItem item = menu.findItem(R.id.apple);
            item.setChecked(false);
        }
        if (pear) {
            MenuItem item = menu.findItem(R.id.pear);
            item.setChecked(true);
        } else {
            MenuItem item = menu.findItem(R.id.pear);
            item.setChecked(false);
        }



        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.icn:
                tv.setText("ICN search");
                if (item.getTitle().equals("Search")){
                    item.setTitle("Buscar");
                    item.setIcon(R.drawable.ic_launcher);
                }else {
                    item.setTitle("Search");
                    item.setIcon(R.drawable.ic_action_search);
                }
                return true;
            case R.id.txt:
                tv.setText("solo texto");
                return true;
            case R.id.one:
                invalidateOptionsMenu();
                tv.setText("one");
                number = 1;
                return true;
            case R.id.two:
                invalidateOptionsMenu();
                tv.setText("two");
                number = 2;
                return true;
            case R.id.apple:
                invalidateOptionsMenu();
                tv.setText("apple");
                if (apple) {
                    apple = false;
                }else {
                    apple = true;
                }

                return true;
            case R.id.pear:
                invalidateOptionsMenu();
                if (pear) {
                    pear = false;
                }else {
                    pear = true;
                }
                tv.setText("pear");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
