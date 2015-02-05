package es.udc.psi14.blanco_novoa.blanco_novoalab11;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;


public class MultimediaActiv extends Activity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener{

    Button but_play, but_stream, but_stop, but_play_audio, but_stream_audio, but_stop_audio, but_grabar, but_parar, but_reproducir;
    VideoView video;
    MediaPlayer mPlayer;
    MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);

        but_play = (Button) findViewById(R.id.but_play);
        but_stream = (Button) findViewById(R.id.but_stream);
        but_stop = (Button) findViewById(R.id.but_stop);
        but_play_audio = (Button) findViewById(R.id.but_play_audio);
        but_stream_audio = (Button) findViewById(R.id.but_stream_audio);
        but_stop_audio = (Button) findViewById(R.id.but_stop_audio);
        but_grabar = (Button) findViewById(R.id.but_grabar);
        but_parar = (Button) findViewById(R.id.but_parar);
        but_reproducir = (Button) findViewById(R.id.but_reproducir);

        video = (VideoView) findViewById(R.id.videoView);

        mediaRecorder = new MediaRecorder();


        but_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video.setVideoPath(Environment.getExternalStorageDirectory().getPath() + "/pasillo.mp4");
                video.start();
            }
        });

        but_stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video.setVideoURI(Uri.parse("http://gac.udc.es/~cvazquez/psi/myvideo.mp4"));
                video.start();
            }
        });

        but_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video.stopPlayback();
            }
        });

        but_play_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer!=null && mPlayer.isPlaying()){//test MediaPlayer state
                    mPlayer.pause();
                    but_play_audio.setText("play audio");
                } else {
                    if (mPlayer == null) // first call: create MediaPlayer
                        mPlayer = MediaPlayer.create(getBaseContext(), R.raw.danzachina);
                    mPlayer.start(); // prepare() is called by create()
                    but_play_audio.setText("pause audio"); }
            }
        });

        but_stream_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer != null) {
                    mPlayer.reset();
                } else {
                    mPlayer = new MediaPlayer();
                }

                try {
                    mPlayer.setDataSource("http://gac.udc.es/~cvazquez/psi/danzachina.mp3");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer.setOnPreparedListener(MultimediaActiv.this);
                mPlayer.setOnCompletionListener(MultimediaActiv.this);
                mPlayer.setOnErrorListener(MultimediaActiv.this);
                mPlayer.prepareAsync();
                but_play_audio.setText("pause audio");
            }
        });

        but_stop_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer != null) {
                    mPlayer.reset();
                    mPlayer.release();
                    mPlayer = null;
                    but_play_audio.setText("Play audio");
                }
            }
        });

        but_grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getPath() + "/audio.3gp");
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                try {
                    mediaRecorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaRecorder.start();
                but_parar.setVisibility(View.VISIBLE);
                but_grabar.setVisibility(View.GONE);
            }
        });

        but_parar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                mediaRecorder.reset();
                but_parar.setVisibility(View.GONE);
                but_grabar.setVisibility(View.VISIBLE);
            }
        });

        but_reproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer != null) {
                    mPlayer.reset();
                    mPlayer.release();
                }

                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + "/audio.3gp");
                    mPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mPlayer.start();
                but_play_audio.setText("pause audio");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.multimedia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mPlayer.release();
        mPlayer = null;
        return true;
    }
}
